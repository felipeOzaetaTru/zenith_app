package ZenithApp.MiradorOficial.commons.converter.sale;

import ZenithApp.MiradorOficial.commons.constans.response.GeneralResponse;
import ZenithApp.MiradorOficial.commons.domains.dto.detailSale.DetailSaleDTO;
import ZenithApp.MiradorOficial.commons.domains.dto.sale.SaleDTO;
import ZenithApp.MiradorOficial.commons.domains.entity.sale.SaleEntity;
import ZenithApp.MiradorOficial.commons.helpers.HelperMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Log4j2
public class SaleConverter {

    public SaleDTO convertSaleEntityToSaleDTO(SaleEntity saleEntity) {
        try {
            return SaleDTO.builder()
                    .id(saleEntity.getId())
                    .tableNumber(saleEntity.getTableNumber())
                    .customerCount(saleEntity.getCustomerCount())
                    .waiter(saleEntity.getWaiter())
                    .notes(saleEntity.getNotes())
                    .paymentMethod(saleEntity.getPaymentMethod())
                    .subtotal(saleEntity.getSubtotal())
                    .tip(saleEntity.getTip())
                    .total(saleEntity.getTotal())
                    .items(saleEntity.getItems().stream().map(item ->
                            DetailSaleDTO.builder()
                                    .id(item.getId())
                                    .product(item.getProduct().getId())
                                    .productName(item.getProduct().getName())       // ✅
                                    .productPrice(item.getProduct().getSalePrice()) // ✅
                                    .quantity(item.getQuantity())
                                    .subtotal(item.getSubtotal())
                                    .notes(item.getNotes())
                                    .build()
                    ).collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            log.error(GeneralResponse.DOCUMENT_FAIL + e);
            return new SaleDTO();
        }
    }

    public SaleEntity convertSaleDTOToSaleEntity(SaleDTO saleDTO) {
        try {
            return SaleEntity.newInstance()
                    .id(saleDTO.getId())
                    .tableNumber(saleDTO.getTableNumber())
                    .customerCount(saleDTO.getCustomerCount())
                    .waiter(saleDTO.getWaiter())
                    .notes(saleDTO.getNotes())
                    .paymentMethod(saleDTO.getPaymentMethod())
                    .subtotal(saleDTO.getSubtotal())
                    .tip(saleDTO.getTip())
                    .total(saleDTO.getTotal())
                    .build();
        } catch (Exception e) {
            log.error(GeneralResponse.DOCUMENT_FAIL + e);
            return new SaleEntity();
        }
    }
}
