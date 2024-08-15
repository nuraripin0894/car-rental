package enigma.car_rental.service;

import enigma.car_rental.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface UserEntityService {
    Page<UserEntity> getAll(Pageable pageable);
    UserEntity getOne(Integer id);
    UserEntity update(Authentication auth, UserEntity request);
    void delete(Authentication auth);
    void deleteById(Integer id);
    UserEntity topUp(Authentication auth, UserEntity request);
}
