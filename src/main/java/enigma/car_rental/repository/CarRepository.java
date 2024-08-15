package enigma.car_rental.repository;

import enigma.car_rental.model.Car;
import enigma.car_rental.utils.DTO.CarDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    @Query(value = "SELECT * FROM cars ORDER BY id LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Car> findAllWithPagination(int limit, int offset);

    @Query(value = "SELECT COUNT(*) FROM cars", nativeQuery = true)
    long countAllCars();

    @Transactional
    @Query(value = "INSERT INTO cars (available, name, price, brand_id) " +
            "VALUES (true,:#{#car.name},:#{#car.price},:#{#car.brand_id}) " +
            "RETURNING id, available, name, price, brand_id", nativeQuery = true)
    Car insertCar(@Param("car") CarDTO car);

    @Transactional
    @Query(value = "UPDATE cars SET available = :#{#car.available}, name = :#{#car.name}, " +
            "price = :#{#car.price}, brand_id = :brand_id WHERE id = :id " +
            "RETURNING id, available, name, price, brand_id", nativeQuery = true)
    Car updateCar(@Param("car") Car car, int id, int brand_id);

    @Query(value = "SELECT * FROM cars WHERE id = :id", nativeQuery = true)
    Optional<Car> findCarById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM cars WHERE id = :id", nativeQuery = true)
    void deleteCarById(int id);
}
