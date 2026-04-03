package ZenithApp.MiradorOficial.commons.domains.dto.detailSale;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailSaleDTO {
        private Long id;
        private Long product;
        private String productName;
        private Double productPrice;
        private Integer quantity;
        private Double subtotal;
        private String notes;
}
