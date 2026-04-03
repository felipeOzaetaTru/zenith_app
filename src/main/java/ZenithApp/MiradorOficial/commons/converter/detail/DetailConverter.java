package ZenithApp.MiradorOficial.commons.converter.detail;

import ZenithApp.MiradorOficial.commons.constans.response.GeneralResponse;
import ZenithApp.MiradorOficial.commons.domains.dto.detailSale.DetailSaleDTO;
import ZenithApp.MiradorOficial.commons.domains.entity.detailSale.DetailSaleEntity;
import ZenithApp.MiradorOficial.commons.helpers.HelperMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class DetailConverter {
    public DetailSaleDTO convertDetailSaleEntityToDetailSaleDTO(DetailSaleEntity detailSaleEntity) {
        DetailSaleDTO detailSaleDTO = new DetailSaleDTO();
        try {
            detailSaleDTO = HelperMapper.modelMapper().map(detailSaleEntity, DetailSaleDTO.class);
        } catch (Exception e) {
            log.error(GeneralResponse.DOCUMENT_FAIL + e);
        }
        return detailSaleDTO;
    }
    public DetailSaleEntity convertDetailSaleDTOToDetailSaleEntity(DetailSaleDTO detailSaleDTO) {
        DetailSaleEntity detailSaleEntity = new DetailSaleEntity();
        try {
            detailSaleEntity = HelperMapper.modelMapper().map(detailSaleDTO, DetailSaleEntity.class);
        } catch (Exception e) {
            log.error(GeneralResponse.DOCUMENT_FAIL + e);
        }
        return detailSaleEntity;
    }
}
