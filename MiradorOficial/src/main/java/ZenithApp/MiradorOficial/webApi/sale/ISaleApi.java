package ZenithApp.MiradorOficial.webApi.sale;

import ZenithApp.MiradorOficial.commons.domains.dto.sale.SaleDTO;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ISaleApi {
    @PostMapping
    ResponseEntity<GenericResponseDTO> createSale(@RequestBody SaleDTO saleDTO);
    @GetMapping
    ResponseEntity<GenericResponseDTO> readSale(@PathVariable Long id);
    @GetMapping
    ResponseEntity<GenericResponseDTO> readAllSale();
    @PutMapping
    ResponseEntity<GenericResponseDTO> updateSale(@RequestBody SaleDTO saleDTO);
    @DeleteMapping
    ResponseEntity<GenericResponseDTO> deleteSale(@PathVariable Long id);
}
