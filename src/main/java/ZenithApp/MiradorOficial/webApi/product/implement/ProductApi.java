package ZenithApp.MiradorOficial.webApi.product.implement;
import ZenithApp.MiradorOficial.commons.constans.endPoints.product.IProductEndPoint;
import ZenithApp.MiradorOficial.commons.constans.response.GeneralResponse;
import ZenithApp.MiradorOficial.commons.domains.dto.product.ProductDTO;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import ZenithApp.MiradorOficial.service.product.implement.ProductService;
import ZenithApp.MiradorOficial.webApi.product.IProductApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(IProductEndPoint.BASE_URL)
@Tag(name = "Sistema de Gestión de Producto", description = "Crear, visualizar, eliminar y actualizar producto")
@Log4j2
public class ProductApi implements IProductApi {

    private final ProductService productService;


    public ProductApi(ProductService productService) {
        this.productService = productService;
    }


    @Override
    @Operation(summary = "Crear un nuevo producto")
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
    @PostMapping(IProductEndPoint.CREATE_PRODUCT)
    public ResponseEntity<GenericResponseDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @Override
    @Operation(summary = "Leer un producto")
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
    @GetMapping(IProductEndPoint.READ_PRODUCT)
    public ResponseEntity<GenericResponseDTO> readProduct(@PathVariable Long id) {
        return productService.readProduct(id);
    }

    @Override
    @Operation(summary = "leer un producto por su nombre")
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
    @GetMapping(IProductEndPoint.READ_PRODUCT_FOR_CATEGORY)
    public ResponseEntity<GenericResponseDTO> readProductForCategory(@PathVariable Long id) {

        return productService.readProductForCategory(id);
    }

    @Override
    @Operation(summary = "leer productos")
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
    @GetMapping(IProductEndPoint.READ_PRODUCTS)
    public ResponseEntity<GenericResponseDTO> readProducts() {
        return productService.readProducts();
    }

    @Override
    @Operation(summary = "actualizar producto")
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
    @PutMapping(IProductEndPoint.UPDATE_PRODUCT)
    public ResponseEntity<GenericResponseDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        return productService.updateProduct(productDTO);
    }

    @Override
    @Operation(summary = "actualizar producto")
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
    @DeleteMapping(IProductEndPoint.DELETE_PRODUCT)
    public ResponseEntity<GenericResponseDTO> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
