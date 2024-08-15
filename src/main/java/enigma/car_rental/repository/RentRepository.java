package enigma.car_rental.repository;

import enigma.car_rental.model.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends JpaRepository<Rent, Integer> {
    @Query(value = "SELECT * FROM rents ORDER BY id LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Rent> findAllWithPagination(int limit, int offset);

    @Query(value = "SELECT COUNT(*) FROM rents", nativeQuery = true)
    long countAllRents();

    @Transactional
    @Query(value = "INSERT INTO rents (completed, ends_at, rent_price, started_at, car_id, user_id) " +
            "VALUES (:#{#rent.completed},:#{#rent.endsAt},:#{#rent.rentPrice},:#{#rent.startedAt},:car_id,:user_id) " +
            "RETURNING id, completed, ends_at, rent_price, started_at, car_id, user_id", nativeQuery = true)
    Rent insertRent(@Param("rent") Rent rent, int user_id, int car_id);

    @Transactional
    @Query(value = "UPDATE rents SET completed = :#{#rent.completed}, ends_at = :#{#rent.endsAt}, " +
            "rent_price = :#{#rent.rentPrice}, started_at = :#{#rent.startedAt}, " +
            "car_id = :car_id, user_id = :user_id  WHERE id = :id " +
            "RETURNING id, completed, ends_at, rent_price, started_at, car_id, user_id", nativeQuery = true)
    Rent updateRent(@Param("rent") Rent rent, int id, int user_id, int car_id);

    @Query(value = "SELECT * FROM rents WHERE id = :id", nativeQuery = true)
    Optional<Rent> findRentById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM rents WHERE id = :id", nativeQuery = true)
    void deleteRentById(int id);
}
