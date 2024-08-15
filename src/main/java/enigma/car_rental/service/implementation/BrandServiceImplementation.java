package enigma.car_rental.service.implementation;

import enigma.car_rental.model.Brand;
import enigma.car_rental.repository.BrandRepository;
import enigma.car_rental.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImplementation implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public Brand create(Brand request) {
        return brandRepository.insertBrand(request.getName());
    }

    @Override
    public Page<Brand> getAll(Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int offset = pageNumber * pageSize;

        long total = brandRepository.countAllBrands();

        List<Brand> allBrands = brandRepository.findAllWithPagination(pageSize, offset);
        return new PageImpl<>(allBrands, pageable, total);
    }

    @Override
    public Brand getOne(Integer id) {
        return brandRepository.findBrandById(id).orElseThrow(() ->
                new RuntimeException("Brand with id " + id + " not found!"));
    }

    @Override
    public Brand update(Integer id, Brand request) {
        if(brandRepository.findBrandById(id).isEmpty()) {
            throw new RuntimeException("Brand with id " + id + " not found!");
        }
        else {
            return brandRepository.updateBrandName(request.getName(), id);
        }
    }

    @Override
    public void delete(Integer id) {
        if(brandRepository.findBrandById(id).isEmpty()) {
            throw new RuntimeException("Brand with id " + id + " not found!");
        }
        else {
            brandRepository.deleteBrandById(id);
        }
    }
}
