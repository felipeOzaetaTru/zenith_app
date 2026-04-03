package ZenithApp.MiradorOficial.service.category;

import ZenithApp.MiradorOficial.commons.domains.dto.category.CategoryDTO;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {
    ResponseEntity<GenericResponseDTO> createCategory(CategoryDTO categoryDTO);
    ResponseEntity<GenericResponseDTO> readCategory(Long id);
    ResponseEntity<GenericResponseDTO> readAllCategory();
    ResponseEntity<GenericResponseDTO> updateCategory(CategoryDTO categoryDTO);
    ResponseEntity<GenericResponseDTO> deleteCategory(Long id);
}
