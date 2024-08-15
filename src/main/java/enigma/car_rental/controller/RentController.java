package enigma.car_rental.controller;

import enigma.car_rental.model.Rent;
import enigma.car_rental.service.RentService;
import enigma.car_rental.utils.DTO.RentDTO;
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
@RequestMapping("/rents")
@RequiredArgsConstructor
public class RentController {
    private final RentService rentService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RentDTO request){
        Rent result = rentService.create(request);
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
        Page<Rent> res = rentService.getAll(pageable);
        PageResponseWrapper<Rent> result = new PageResponseWrapper<>(res);
        return Response.renderJson(
                result,
                result.getTotalElements() == 0 ? "Data Empty!" : "Data Found!",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        Rent result = rentService.getOne(id);
        return Response.renderJson(
                result,
                "Data Found!",
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id){
        Rent result = rentService.update(id);
        return Response.renderJson(
                result,
                "Data Has Been Updated!",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        rentService.delete(id);
        return Response.renderJson(
                null,
                "Data Has Been Deleted!",
                HttpStatus.OK
        );
    }
}

