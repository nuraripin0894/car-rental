package enigma.car_rental.repository;

import enigma.car_rental.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query(value = "SELECT * FROM brands ORDER BY id LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Brand> findAllWithPagination(int limit, int offset);

    @Query(value = "SELECT COUNT(*) FROM brands", nativeQuery = true)
    long countAllBrands();

    @Transactional
    @Query(value = "INSERT INTO brands (name) VALUES (:name) RETURNING id, name", nativeQuery = true)
    Brand insertBrand(String name);

    @Transactional
    @Query(value = "UPDATE brands SET name = :name WHERE id = :id RETURNING id, name", nativeQuery = true)
    Brand updateBrandName(String name, int id);

    @Query(value = "SELECT * FROM brands WHERE id = :id", nativeQuery = true)
    Optional<Brand> findBrandById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM brands WHERE id = :id", nativeQuery = true)
    void deleteBrandById(int id);
}
