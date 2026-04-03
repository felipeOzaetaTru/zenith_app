package ZenithApp.MiradorOficial.service.sale;

import ZenithApp.MiradorOficial.commons.domains.dto.sale.SaleDTO;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ISaleService {
    ResponseEntity<GenericResponseDTO> createSale(SaleDTO saleDTO);
    ResponseEntity<GenericResponseDTO> readSale(Long id);
    ResponseEntity<GenericResponseDTO> readAllSale();
    ResponseEntity<GenericResponseDTO> updateSale(SaleDTO saleDTO);
    ResponseEntity<GenericResponseDTO> deleteSale(Long id);
}
