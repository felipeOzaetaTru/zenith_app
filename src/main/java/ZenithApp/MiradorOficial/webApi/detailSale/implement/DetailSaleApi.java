package ZenithApp.MiradorOficial.webApi.detailSale.implement;

import ZenithApp.MiradorOficial.commons.constans.endPoints.category.ICategoryEndPoint;
import ZenithApp.MiradorOficial.commons.constans.endPoints.detailSale.IDetailSaleEndPoint;
import ZenithApp.MiradorOficial.commons.constans.response.GeneralResponse;
import ZenithApp.MiradorOficial.commons.domains.dto.detailSale.DetailSaleDTO;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import ZenithApp.MiradorOficial.service.detail.implement.DetailSaleService;
import ZenithApp.MiradorOficial.webApi.detailSale.IDetailSaleApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(IDetailSaleEndPoint.BASE_URL)
@Tag(name = "Sistema de Gestión de detalles de venta", description = "Crear, visualizar, eliminar y actualizar detalles de venta")
@Log4j2
public class DetailSaleApi implements IDetailSaleApi {

    private final DetailSaleService detailSaleService;

    public DetailSaleApi(DetailSaleService detailSaleService) {
        this.detailSaleService = detailSaleService;
    }

    @Override
    @Operation(summary = "Crear un nuevo detalle de venta")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = GeneralResponse.CREATE_SUCCESS,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenericResponseDTO.class))}),
            @ApiResponse(responseCode  = "400", description = GeneralResponse.CREATE_FAIL,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode  = "404", description = GeneralResponse.NOT_FOUND,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode  = "500", description = GeneralResponse.INTERNAL_SERVER,
                    content = {@Content(mediaType = "application/json")})})
    @PostMapping(IDetailSaleEndPoint.CREATE_DETAIL)
    public ResponseEntity<GenericResponseDTO> createDetailSale(@RequestBody DetailSaleDTO detailSaleDTO) {
        return detailSaleService.createDetailSale(detailSaleDTO);
    }

    @Override
    @Operation(summary = "leer un detalle de venta")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = GeneralResponse.CREATE_SUCCESS,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenericResponseDTO.class))}),
            @ApiResponse(responseCode  = "400", description = GeneralResponse.CREATE_FAIL,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode  = "404", description = GeneralResponse.NOT_FOUND,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode  = "500", description = GeneralResponse.INTERNAL_SERVER,
                    content = {@Content(mediaType = "application/json")})})
    @GetMapping(IDetailSaleEndPoint.READ_DETAIL)
    public ResponseEntity<GenericResponseDTO> readDetailSale(@PathVariable Long id) {
        return detailSaleService.readDetailSale(id);
    }

    @Override
    @Operation(summary = "leer todos los detalle de venta")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = GeneralResponse.CREATE_SUCCESS,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenericResponseDTO.class))}),
            @ApiResponse(responseCode  = "400", description = GeneralResponse.CREATE_FAIL,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode  = "404", description = GeneralResponse.NOT_FOUND,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode  = "500", description = GeneralResponse.INTERNAL_SERVER,
                    content = {@Content(mediaType = "application/json")})})
    @GetMapping(IDetailSaleEndPoint.READ_ALL_DETAIL)
    public ResponseEntity<GenericResponseDTO> readAllDetailSale() {
        return detailSaleService.readAllDetailSale();
    }

    @Override
    @Operation(summary = "Actualizar un detalle de venta")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = GeneralResponse.CREATE_SUCCESS,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenericResponseDTO.class))}),
            @ApiResponse(responseCode  = "400", description = GeneralResponse.CREATE_FAIL,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode  = "404", description = GeneralResponse.NOT_FOUND,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode  = "500", description = GeneralResponse.INTERNAL_SERVER,
                    content = {@Content(mediaType = "application/json")})})
    @PutMapping(IDetailSaleEndPoint.CREATE_DETAIL)
    public ResponseEntity<GenericResponseDTO> updateDetailService(@RequestBody DetailSaleDTO detailSaleDTO) {
        return detailSaleService.updateDetailService(detailSaleDTO);
    }

    @Override
    @Operation(summary = "Eliminar un detalle de venta")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @ApiResponse(responseCode  = "200", description = GeneralResponse.CREATE_SUCCESS,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GenericResponseDTO.class))}),
            @ApiResponse(responseCode  = "400", description = GeneralResponse.CREATE_FAIL,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode  = "404", description = GeneralResponse.NOT_FOUND,
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode  = "500", description = GeneralResponse.INTERNAL_SERVER,
                    content = {@Content(mediaType = "application/json")})})
    @DeleteMapping(IDetailSaleEndPoint.DELETE_DETAIL)
    public ResponseEntity<GenericResponseDTO> deleteDetailService(@PathVariable Long id) {
        return detailSaleService.deleteDetailService(id);
    }
}
