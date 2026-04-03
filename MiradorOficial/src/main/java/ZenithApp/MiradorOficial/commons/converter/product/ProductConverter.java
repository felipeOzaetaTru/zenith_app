package ZenithApp.MiradorOficial.commons.converter.product;

import ZenithApp.MiradorOficial.commons.constans.response.GeneralResponse;
import ZenithApp.MiradorOficial.commons.domains.dto.product.ProductDTO;
import ZenithApp.MiradorOficial.commons.domains.entity.category.CategoryEntity;
import ZenithApp.MiradorOficial.commons.domains.entity.product.ProductEntity;
import ZenithApp.MiradorOficial.commons.helpers.HelperMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
@Component
@Log4j2
public class ProductConverter {

    public ProductDTO convertProductEntityToProductDTO(ProductEntity productEntity) {
        try {
            return ProductDTO.builder()
                    .id(productEntity.getId())
                    .name(productEntity.getName())
                    .description(productEntity.getDescription())
                    .sku(productEntity.getSku())
                    .barcode(productEntity.getBarcode())
                    .category(productEntity.getCategory().getId())
                    .supplier(productEntity.getSupplier())
                    .costPrice(productEntity.getCostPrice())
                    .salePrice(productEntity.getSalePrice())
                    .stock(productEntity.getStock())
                    .minStock(productEntity.getMinStock())
                    .maxStock(productEntity.getMaxStock())
                    .location(productEntity.getLocation())
                    .weight(productEntity.getWeight())
                    .dimensions(productEntity.getDimensions())
                    .isActive(productEntity.getIsActive())
                    .isFeatured(productEntity.getIsFeatured())
                    .build();
        } catch (Exception e) {
            log.error(GeneralResponse.DOCUMENT_FAIL + e);
            return new ProductDTO();
        }
    }

    public ProductEntity convertProductDTOToProductEntity(ProductDTO productDTO, CategoryEntity category) {
        try {
            return ProductEntity.newInstance()
                    .name(productDTO.getName())
                    .description(productDTO.getDescription())
                    .sku(productDTO.getSku())
                    .barcode(productDTO.getBarcode())
                    .category(category)
                    .supplier(productDTO.getSupplier())
                    .costPrice(productDTO.getCostPrice())
                    .salePrice(productDTO.getSalePrice())
                    .stock(productDTO.getStock())
                    .minStock(productDTO.getMinStock())
                    .maxStock(productDTO.getMaxStock())
                    .location(productDTO.getLocation())
                    .weight(productDTO.getWeight())
                    .dimensions(productDTO.getDimensions())
                    .isActive(productDTO.getIsActive())
                    .isFeatured(productDTO.getIsFeatured())
                    .build();
        } catch (Exception e) {
            log.error(GeneralResponse.DOCUMENT_FAIL + e);
            return new ProductEntity();
        }
    }
}
