package enigma.car_rental.repository;

import enigma.car_rental.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {
    @Query(value = "SELECT * FROM users ORDER BY id LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<UserEntity> findAllWithPagination(int limit, int offset);

    @Query(value = "SELECT COUNT(*) FROM users", nativeQuery = true)
    long countAllUsers();

    @Transactional
    @Query(value = "INSERT INTO users (balance, password, role, username) " +
            "VALUES (0,:#{#user.password},:#{#user.role},:#{#user.username}) " +
            "RETURNING id, username, balance, password, role", nativeQuery = true)
    UserEntity insertUser(@Param("user") UserEntity user);

    @Transactional
    @Query(value = "UPDATE users SET balance = :#{#user.balance}, password = :#{#user.password}, " +
            "role = :#{#user.role}, username = :#{#user.username}  WHERE id = :id " +
            "RETURNING id, username, balance, password, role", nativeQuery = true)
    UserEntity updateUser(@Param("user") UserEntity user, int id);

    @Transactional
    @Query(value = "UPDATE users SET balance = :balance WHERE id = :id " +
            "RETURNING id, username, balance, password, role", nativeQuery = true)
    UserEntity updateBalance(int balance, int id);

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    UserEntity findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    Optional<UserEntity> findUserById(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM users WHERE id = :id", nativeQuery = true)
    void deleteUserById(int id);
}
