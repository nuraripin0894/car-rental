package enigma.car_rental.service;

import enigma.car_rental.model.Car;
import enigma.car_rental.utils.DTO.CarDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {
    Car create(CarDTO request);
    Page<Car> getAll(Pageable pageable);
    Car getOne(Integer id);
    Car update(Integer id, CarDTO request);
    void delete(Integer id);
}
