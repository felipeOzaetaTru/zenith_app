package ZenithApp.MiradorOficial.commons.domains.dto.product;

import jakarta.persistence.Column;
import lombok.*;
import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductDTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private String sku;
    private String barcode;
    private Long category;
    private String supplier;
    private Double costPrice;
    private Double salePrice;
    private Integer stock;
    private Integer minStock;
    private Integer maxStock;
    private String location;
    private Double weight;
    private String dimensions;
    private Boolean isActive = true;
    private Boolean isFeatured = false;

}
