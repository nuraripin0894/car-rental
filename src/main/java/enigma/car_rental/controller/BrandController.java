package enigma.car_rental.controller;

import enigma.car_rental.model.Brand;
import enigma.car_rental.service.BrandService;
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
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Brand request){
        Brand result = brandService.create(request);
        return Response.renderJson(
                result,
                "Data Has Been Created!",
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<Brand> res = brandService.getAll(pageable);
        PageResponseWrapper<Brand> result = new PageResponseWrapper<>(res);
        return Response.renderJson(
                result,
                result.getTotalElements() == 0 ? "Data Empty!" : "Data Found!",
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        Brand result = brandService.getOne(id);
        return Response.renderJson(
                result,
                "Data Found!",
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Brand request){
        Brand result = brandService.update(id, request);
        return Response.renderJson(
                result,
                "Data Has Been Updated!",
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        brandService.delete(id);
        return Response.renderJson(
                null,
                "Data Has Been Deleted!",
                HttpStatus.OK
        );
    }
}
