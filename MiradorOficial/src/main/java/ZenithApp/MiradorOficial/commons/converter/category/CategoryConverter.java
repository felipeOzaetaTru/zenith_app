package ZenithApp.MiradorOficial.commons.converter.category;

import ZenithApp.MiradorOficial.commons.constans.response.GeneralResponse;
import ZenithApp.MiradorOficial.commons.domains.dto.category.CategoryDTO;
import ZenithApp.MiradorOficial.commons.domains.dto.product.ProductDTO;
import ZenithApp.MiradorOficial.commons.domains.entity.category.CategoryEntity;
import ZenithApp.MiradorOficial.commons.domains.entity.product.ProductEntity;
import ZenithApp.MiradorOficial.commons.helpers.HelperMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CategoryConverter {

    public CategoryDTO convertCategoryEntityToCategoryDTO(CategoryEntity categoryEntity) {
        try {
            return CategoryDTO.builder()
                    .id(categoryEntity.getId())
                    .name(categoryEntity.getName())
                    .slug(categoryEntity.getSlug())
                    .description(categoryEntity.getDescription())
                    .image(categoryEntity.getImage())
                    .isActive(categoryEntity.isActive())
                    .build();
        } catch (Exception e) {
            log.error(GeneralResponse.DOCUMENT_FAIL + e);
            return new CategoryDTO();
        }
    }

    public CategoryEntity convertCategoryDTOToCategoryEntity(CategoryDTO categoryDTO) {
        try {
            return CategoryEntity.newInstance()
                    .name(categoryDTO.getName())
                    .slug(categoryDTO.getSlug())
                    .description(categoryDTO.getDescription())
                    .image(categoryDTO.getImage())
                    .isActive(categoryDTO.getIsActive())
                    .build();
        } catch (Exception e) {
            log.error(GeneralResponse.DOCUMENT_FAIL + e);
            return new CategoryEntity();
        }
    }
}
