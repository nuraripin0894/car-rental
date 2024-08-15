package enigma.car_rental.controller;

import enigma.car_rental.model.Car;
import enigma.car_rental.service.CarService;
import enigma.car_rental.utils.DTO.CarDTO;
import enigma.car_rental.utils.PageResponseWrapper;
import enigma.car_rental.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CarDTO request){
        Car result = carService.create(request);
        return Response.renderJson(
                result,
                "Data Has Been Created!",
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<Car> res = carService.getAll(pageable);
        PageResponseWrapper<Car> result = new PageResponseWrapper<>(res);
        return Response.renderJson(
                result,
                result.getTotalElements() == 0 ? "Data Empty!" : "Data Found!",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        Car result = carService.getOne(id);
        return Response.renderJson(
                result,
                "Data Found!",
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody CarDTO request){
        Car result = carService.update(id, request);
        return Response.renderJson(
                result,
                "Data Has Been Updated!",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        carService.delete(id);
        return Response.renderJson(
                null,
                "Data Has Been Deleted!",
                HttpStatus.OK
        );
    }
}
