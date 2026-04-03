package ZenithApp.MiradorOficial.commons.domains.dto.sale;

import ZenithApp.MiradorOficial.commons.domains.dto.detailSale.DetailSaleDTO;
import ZenithApp.MiradorOficial.commons.domains.entity.detailSale.DetailSaleEntity;
import ZenithApp.MiradorOficial.commons.domains.enums.paymentMethod.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Long id;
    private String tableNumber;
    private Integer customerCount;
    private String waiter;
    private String notes;
    private PaymentMethod paymentMethod;
    private List<DetailSaleDTO> items;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double subtotal;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double tip;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double total;
}
