package ZenithApp.MiradorOficial.webApi.category;

import ZenithApp.MiradorOficial.commons.domains.dto.category.CategoryDTO;
import ZenithApp.MiradorOficial.commons.domains.dto.product.ProductDTO;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ICategoryApi {
    @PostMapping
    ResponseEntity<GenericResponseDTO> createCategory(@RequestBody CategoryDTO categoryDTO);
    @GetMapping
    ResponseEntity<GenericResponseDTO> readCategory(@PathVariable Long id);
    @GetMapping
    ResponseEntity<GenericResponseDTO> readAllCategory();
    @PutMapping
    ResponseEntity<GenericResponseDTO> updateCategory(@RequestBody CategoryDTO categoryDTO);
    @DeleteMapping
    ResponseEntity<GenericResponseDTO> deleteCategory(@PathVariable Long id);
}
