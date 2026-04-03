package ZenithApp.MiradorOficial.webApi.product;

import ZenithApp.MiradorOficial.commons.domains.dto.product.ProductDTO;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IProductApi {
    @PostMapping
    ResponseEntity<GenericResponseDTO> createProduct(@RequestBody ProductDTO productDTO);
    @GetMapping
    ResponseEntity<GenericResponseDTO> readProduct(@PathVariable Long id);
    @GetMapping
    ResponseEntity<GenericResponseDTO> readProductForCategory(@PathVariable Long id);
    @GetMapping
    ResponseEntity<GenericResponseDTO> readProducts();
    @PutMapping
    ResponseEntity<GenericResponseDTO> updateProduct(@RequestBody ProductDTO productDTO);
    @DeleteMapping
    ResponseEntity<GenericResponseDTO> deleteProduct(@PathVariable Long id);
}
