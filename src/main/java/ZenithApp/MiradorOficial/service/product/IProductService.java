package ZenithApp.MiradorOficial.service.product;

import ZenithApp.MiradorOficial.commons.domains.dto.product.ProductDTO;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IProductService {
    ResponseEntity<GenericResponseDTO> createProduct(ProductDTO productDTO);
    ResponseEntity<GenericResponseDTO> readProduct(Long id);
    ResponseEntity<GenericResponseDTO> readProductForCategory(Long id);
    ResponseEntity<GenericResponseDTO> readProducts();
    ResponseEntity<GenericResponseDTO> updateProduct(ProductDTO productDTO);
    ResponseEntity<GenericResponseDTO> deleteProduct(Long id);
}
