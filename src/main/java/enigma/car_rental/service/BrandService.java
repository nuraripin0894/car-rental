package enigma.car_rental.service;

import enigma.car_rental.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandService {
    Brand create(Brand request);
    Page<Brand> getAll(Pageable pageable);
    Brand getOne(Integer id);
    Brand update(Integer id, Brand request);
    void delete(Integer id);
}