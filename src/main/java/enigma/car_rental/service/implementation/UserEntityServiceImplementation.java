package enigma.car_rental.service.implementation;

import enigma.car_rental.model.UserEntity;
import enigma.car_rental.repository.UserEntityRepository;
import enigma.car_rental.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImplementation implements UserEntityService, UserDetailsService {
    private final UserEntityRepository userEntityRepository;

    @Override
    public Page<UserEntity> getAll(Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageNumber * pageSize;

        long total = userEntityRepository.countAllUsers();

        List<UserEntity> allUsers = userEntityRepository.findAllWithPagination(pageSize, offset);
        return new PageImpl<>(allUsers, pageable, total);
    }

    @Override
    public UserEntity getOne(Integer id) {
        return userEntityRepository.findUserById(id).orElseThrow(() ->
                new RuntimeException("User with id " + id + " not found!"));
    }

    @Override
    public UserEntity update(Authentication auth, UserEntity request) {
        UserEntity user = (UserEntity) auth.getPrincipal();
        int id = user.getId();
        if(userEntityRepository.findUserById(id).isEmpty()) {
            throw new RuntimeException("User with id " + id + " not found!");
        }
        else {
            user.setUsername(request.getUsername() != null ? request.getUsername() : user.getUsername());
            user.setPassword(request.getPassword() != null ? request.getPassword() : user.getPassword());
            userEntityRepository.updateUser(user, id);
            return userEntityRepository.findUserById(id).orElseThrow(() ->
                    new RuntimeException("User with id " + id + " not found!"));
        }
    }

    @Override
    public void delete(Authentication auth) {
        UserEntity user = (UserEntity) auth.getPrincipal();
        int id = user.getId();
        if(userEntityRepository.findUserById(id).isEmpty()) {
            throw new RuntimeException("User with id " + id + " not found!");
        }
        else {
            userEntityRepository.deleteUserById(id);
        }
    }

    @Override
    public void deleteById(Integer id) {
        if(userEntityRepository.findUserById(id).isEmpty()) {
            throw new RuntimeException("User with id " + id + " not found!");
        }
        else {
            userEntityRepository.deleteUserById(id);
        }
    }

    @Override
    public UserEntity topUp(Authentication auth, UserEntity request) {
        UserEntity user = (UserEntity) auth.getPrincipal();
        int id = user.getId();
        if(userEntityRepository.findUserById(id).isEmpty()) {
            throw new RuntimeException("User with id " + id + " not found!");
        }
        else {
            return userEntityRepository.updateBalance(request.getBalance(), id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        return userEntityRepository.findByUsername(username);
    }
}
