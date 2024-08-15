package enigma.car_rental.controller;

import enigma.car_rental.model.UserEntity;
import enigma.car_rental.service.UserEntityService;
import enigma.car_rental.utils.PageResponseWrapper;
import enigma.car_rental.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserEntityService userEntityService;

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<UserEntity> res = userEntityService.getAll(pageable);
        PageResponseWrapper<UserEntity> result = new PageResponseWrapper<>(res);
        return Response.renderJson(
                result,
                result.getTotalElements() == 0 ? "Data Empty!" : "Data Found!",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        UserEntity result = userEntityService.getOne(id);
        return Response.renderJson(
                result,
                "Data Found!",
                HttpStatus.OK
        );
    }

    @PutMapping
    public ResponseEntity<?> update(Authentication auth, @RequestBody UserEntity request) {
        UserEntity result = userEntityService.update(auth, request);
        return Response.renderJson(
                result,
                "Data Has Been Updated!",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        userEntityService.deleteById(id);
        return Response.renderJson(
                null,
                "Data Has Been Deleted!",
                HttpStatus.OK
        );
    }

    @DeleteMapping
    public ResponseEntity<?> delete(Authentication auth) {
        userEntityService.delete(auth);
        return Response.renderJson(
                null,
                "Data Has Been Deleted!",
                HttpStatus.OK
        );
    }

    @PostMapping("/topup")
    public ResponseEntity<?> topUp(Authentication auth, @RequestBody UserEntity request) {
        UserEntity result = userEntityService.topUp(auth, request);
        return Response.renderJson(
                result,
                "Balance Has Been Updated!",
                HttpStatus.OK
        );
    }
}