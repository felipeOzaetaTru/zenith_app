package ZenithApp.MiradorOficial.commons.domains.dto.category;

import ZenithApp.MiradorOficial.commons.domains.dto.product.ProductDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CategoryDTO implements Serializable {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String image;
    private Boolean isActive;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<ProductDTO> products;
}
