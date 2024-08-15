package enigma.car_rental.service.implementation;

import enigma.car_rental.model.Brand;
import enigma.car_rental.model.Car;
import enigma.car_rental.repository.CarRepository;
import enigma.car_rental.service.BrandService;
import enigma.car_rental.service.CarService;
import enigma.car_rental.utils.DTO.CarDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImplementation implements CarService {
    private final CarRepository carRepository;
    private final BrandService brandService;

    @Override
    public Car create(CarDTO request) {
        if(request.getBrand_id() == null) throw new RuntimeException("brand_id empty");
        return carRepository.insertCar(request);
    }

    @Override
    public Page<Car> getAll(Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageNumber * pageSize;

        long total = carRepository.countAllCars();

        List<Car> allCars = carRepository.findAllWithPagination(pageSize, offset);
        return new PageImpl<>(allCars, pageable, total);
    }

    @Override
    public Car getOne(Integer id) {
        return carRepository.findCarById(id).orElseThrow(() ->
                new RuntimeException("Car with id " + id + " not found!"));
    }

    @Override
    public Car update(Integer id, CarDTO request) {
        if(request.getBrand_id() == null) throw new RuntimeException("brand_id empty");
        if(carRepository.findCarById(id).isEmpty()){
            throw new RuntimeException("Car with id " + id + " not found!");
        }
        else {
            Car car = this.getOne(id);
            car.setName(request.getName() != null ? request.getName() : car.getName());
            car.setBrand(request.getBrand_id() != null ? brandService.getOne(request.getBrand_id()) : car.getBrand());
            car.setAvailable(request.getAvailable() != null ? request.getAvailable() : car.getAvailable());
            car.setPrice(request.getPrice() != null ? request.getPrice() : car.getPrice());

            return carRepository.updateCar(car, id, car.getBrand().getId());
        }
    }

    @Override
    public void delete(Integer id) {
        if(carRepository.findCarById(id).isEmpty()) {
            throw new RuntimeException("Car with id " + id + " not found!");
        }
        else {
            carRepository.deleteCarById(id);
        }
    }
}
