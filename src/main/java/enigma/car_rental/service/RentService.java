package enigma.car_rental.service;

import enigma.car_rental.model.Rent;
import enigma.car_rental.utils.DTO.RentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RentService {
    Rent create(RentDTO request);
    Page<Rent> getAll(Pageable pageable);
    Rent getOne(Integer id);
    Rent update(Integer id);
    void delete(Integer id);
}
