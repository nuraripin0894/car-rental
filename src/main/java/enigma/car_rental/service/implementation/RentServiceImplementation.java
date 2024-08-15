package enigma.car_rental.service.implementation;

import enigma.car_rental.model.Car;
import enigma.car_rental.model.Rent;
import enigma.car_rental.model.UserEntity;
import enigma.car_rental.repository.RentRepository;
import enigma.car_rental.service.CarService;
import enigma.car_rental.service.RentService;
import enigma.car_rental.service.UserEntityService;
import enigma.car_rental.utils.DTO.RentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RentServiceImplementation implements RentService {
    private final RentRepository rentRepository;
    private final CarService carService;
    private final UserEntityService userService;

    @Override
    public Rent create(RentDTO request) {
        if(request.getUser_id() == null) throw new RuntimeException("user_id empty");
        if(request.getCar_id() == null) throw new RuntimeException("car_id empty");
        Car car = carService.getOne(request.getCar_id());
        UserEntity user = userService.getOne(request.getUser_id());
        Rent newRent = new Rent();

        if(!car.getAvailable()) throw new RuntimeException("Car is not available for rent!");

        car.setAvailable(false);

        newRent.setCar(car);
        newRent.setUser(user);
        newRent.setCompleted(false);

        // Hitung price * hari pinjam
        Date startedTemp = request.getStarted_at();
        Date endsTemp = request.getEnds_at();
        long diff = endsTemp.getTime() - startedTemp.getTime();
        long daysDifference = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        int rentPrice = (int) (car.getPrice() * daysDifference);

        // Down payment 1/2 from rent price
        user.setBalance(user.getBalance() - rentPrice / 2);

        newRent.setStartedAt(request.getStarted_at());
        newRent.setEndsAt(request.getEnds_at());
        newRent.setRentPrice(rentPrice);

        return rentRepository.insertRent(newRent, request.getUser_id(), request.getCar_id());
    }

    @Override
    public Page<Rent> getAll(Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageNumber * pageSize;

        long total = rentRepository.countAllRents();

        List<Rent> allRents = rentRepository.findAllWithPagination(pageSize, offset);
        return new PageImpl<>(allRents, pageable, total);
    }

    @Override
    public Rent getOne(Integer id) {
        return rentRepository.findRentById(id).orElseThrow(() ->
                new RuntimeException("Rent with id " + id + " not found!"));
    }

    @Override
    public Rent update(Integer id) {
        Rent rent = rentRepository.findRentById(id).orElseThrow(() ->
                    new RuntimeException("Rent with id " + id + " not found!"));
        UserEntity user = userService.getOne(rent.getUser().getId());
        Car car = carService.getOne(rent.getCar().getId());

        Date endsTemp = rent.getEndsAt();
        Date completeDate = new Date();
        long diff = endsTemp.getTime() - completeDate.getTime();
        long daysDifference = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        int rentPrice = (int) (car.getPrice() * daysDifference);

        // Jika lewat batas akhir denda 10% dari harga rental
        if (daysDifference < 0) {
            rentPrice += rentPrice * 10 / 100;
        }

        user.setBalance(user.getBalance() - rentPrice / 2);
        car.setAvailable(true);
        rent.setCompleted(true);

        return rentRepository.updateRent(rent, id, user.getId(), car.getId());
    }

    @Override
    public void delete(Integer id) {
        Rent temp = rentRepository.findRentById(id).orElseThrow(() ->
                new RuntimeException("Rent with id " + id + " not found!"));
        Car car = carService.getOne(temp.getCar().getId());
        car.setAvailable(true);
        rentRepository.deleteRentById(id);
    }
}
