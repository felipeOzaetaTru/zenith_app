package ZenithApp.MiradorOficial.webApi.detailSale;

import ZenithApp.MiradorOficial.commons.domains.dto.detailSale.DetailSaleDTO;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface IDetailSaleApi {
    @PostMapping
    ResponseEntity<GenericResponseDTO> createDetailSale(@RequestBody DetailSaleDTO detailSaleDTO);
    @GetMapping
    ResponseEntity<GenericResponseDTO> readDetailSale(@PathVariable Long id);
    @GetMapping
    ResponseEntity<GenericResponseDTO> readAllDetailSale();
    @PutMapping
    ResponseEntity<GenericResponseDTO> updateDetailService(@RequestBody DetailSaleDTO detailSaleDTO);
    @DeleteMapping
    ResponseEntity<GenericResponseDTO> deleteDetailService(@PathVariable Long id);
}
