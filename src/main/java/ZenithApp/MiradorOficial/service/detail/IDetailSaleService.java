package ZenithApp.MiradorOficial.service.detail;

import ZenithApp.MiradorOficial.commons.domains.dto.detailSale.DetailSaleDTO;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IDetailSaleService {
    ResponseEntity<GenericResponseDTO> createDetailSale(DetailSaleDTO detailSaleDTO);
    ResponseEntity<GenericResponseDTO> readDetailSale(Long id);
    ResponseEntity<GenericResponseDTO> readAllDetailSale();
    ResponseEntity<GenericResponseDTO> updateDetailService(DetailSaleDTO detailSaleDTO);
    ResponseEntity<GenericResponseDTO> deleteDetailService(Long id);
}
