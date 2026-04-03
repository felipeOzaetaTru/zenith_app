package ZenithApp.MiradorOficial.service.product.implement;

import ZenithApp.MiradorOficial.commons.constans.response.GeneralResponse;
import ZenithApp.MiradorOficial.commons.constans.response.product.IProductResponse;
import ZenithApp.MiradorOficial.commons.converter.product.ProductConverter;
import ZenithApp.MiradorOficial.commons.domains.dto.product.ProductDTO;
import ZenithApp.MiradorOficial.commons.domains.entity.category.CategoryEntity;
import ZenithApp.MiradorOficial.commons.domains.entity.product.ProductEntity;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import ZenithApp.MiradorOficial.commons.helpers.HelperMapper;
import ZenithApp.MiradorOficial.repository.category.ICategoryRepository;
import ZenithApp.MiradorOficial.repository.product.IProductRepository;
import ZenithApp.MiradorOficial.service.product.IProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Log4j2
public class ProductService implements IProductService {

    private final IProductRepository iProductRepository;
    private final ProductConverter productConverter;
    private final ICategoryRepository iCategoryRepository;

    public ProductService(IProductRepository iProductRepository, ProductConverter productConverter, ICategoryRepository iCategoryRepository) {
        this.iProductRepository = iProductRepository;
        this.productConverter = productConverter;
        this.iCategoryRepository = iCategoryRepository;
    }


    @Override
    public ResponseEntity<GenericResponseDTO> createProduct(ProductDTO productDTO) {
       try {
           Optional<ProductEntity> existeProduct;
           existeProduct = iProductRepository.readProductForName(productDTO.getName());
           if(!existeProduct.isPresent()){
               CategoryEntity category = iCategoryRepository.findById(productDTO.getCategory())
                       .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
               ProductEntity productEntity = productConverter.convertProductDTOToProductEntity(productDTO, category);
               iProductRepository.save(productEntity);
               return ResponseEntity.ok(GenericResponseDTO.builder()
                       .message(GeneralResponse.OPERATION_SUCCESS)
                       .objectResponse(GeneralResponse.CREATE_SUCCESS)
                       .statusCode(HttpStatus.OK.value())
                       .build());
           }
           else{
               return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                       .message(GeneralResponse.OPERATION_FAIL)
                       .objectResponse(IProductResponse.PRODUCT_SUCCESS)
                       .statusCode(HttpStatus.BAD_REQUEST.value())
                       .build());
           }
       }
       catch(Exception e){
           log.error(GeneralResponse.INTERNAL_SERVER + e);
           return new ResponseEntity<>(GenericResponseDTO.builder()
                   .message(GeneralResponse.INTERNAL_SERVER)
                   .objectResponse(null)
                   .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                   .build(), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @Override
    public ResponseEntity<GenericResponseDTO> readProduct(Long id) {
        try {
            Optional<ProductEntity> existeProduct = iProductRepository.findById(id);
            if(existeProduct.isPresent()){
                ProductDTO productDTO = productConverter.convertProductEntityToProductDTO(existeProduct.get());
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(productDTO)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }
            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(IProductResponse.PRODUCT_FAIL)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build());
            }
        }
        catch(Exception e){
            log.error(GeneralResponse.INTERNAL_SERVER + e);
            return new ResponseEntity<>(GenericResponseDTO.builder()
                    .message(GeneralResponse.INTERNAL_SERVER)
                    .objectResponse(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<GenericResponseDTO> readProductForCategory(Long id) {
        try {
            List<ProductDTO> productDTOList = new ArrayList<>();
            List<ProductEntity> productEntityList = iProductRepository.findByCategory_Id(id);
            if(!productEntityList.isEmpty()){
                productEntityList.forEach(userEntity -> {
                    ProductDTO productDTO = productConverter.convertProductEntityToProductDTO(userEntity);
                    productDTOList.add(productDTO);
                });
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(productDTOList)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }
            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(IProductResponse.PRODUCT_FAIL)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build());
            }
        }
        catch(Exception e){
            log.error(GeneralResponse.INTERNAL_SERVER + e);
            return new ResponseEntity<>(GenericResponseDTO.builder()
                    .message(GeneralResponse.INTERNAL_SERVER)
                    .objectResponse(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<GenericResponseDTO> readProducts() {
        try {
            List<ProductDTO> productDTOList = new ArrayList<>();
            List<ProductEntity> productEntityList = iProductRepository.findAll();
            if(!productEntityList.isEmpty()){
                productEntityList.forEach(userEntity -> {
                    ProductDTO productDTO = productConverter.convertProductEntityToProductDTO(userEntity);
                    productDTOList.add(productDTO);
                });
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(productDTOList)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }
            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(IProductResponse.PRODUCT_FAIL)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build());
            }
        }
        catch(Exception e){
            log.error(GeneralResponse.INTERNAL_SERVER + e);
            return new ResponseEntity<>(GenericResponseDTO.builder()
                    .message(GeneralResponse.INTERNAL_SERVER)
                    .objectResponse(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<GenericResponseDTO> updateProduct(ProductDTO productDTO) {
        try {
            Optional<ProductEntity> productEntity = iProductRepository.findById(productDTO.getId());
            if (productEntity.isPresent()) {

                HelperMapper.modelMapper().map(productDTO, productEntity.get()); // ← solo actualiza los no null
                this.iProductRepository.save(productEntity.get());
                return new ResponseEntity<>(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(IProductResponse.PRODUCT_UPDATE_SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .build(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(IProductResponse.PRODUCT_UPDATE_FAIL)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build(), HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error(GeneralResponse.INTERNAL_SERVER + e);
            return new ResponseEntity<>(GenericResponseDTO.builder()
                    .message(GeneralResponse.INTERNAL_SERVER)
                    .objectResponse(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<GenericResponseDTO> deleteProduct(Long id) {
        try {
            Optional<ProductEntity> productEntity = iProductRepository.findById(id);
            if(productEntity.isPresent()){
                iProductRepository.delete(productEntity.get());
                return new ResponseEntity<>(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(IProductResponse.PRODUCT_DELETE_SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .build(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(IProductResponse.PRODUCT_DELETE_FAIL)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build(), HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            log.error(GeneralResponse.INTERNAL_SERVER + e);
            return new ResponseEntity<>(GenericResponseDTO.builder()
                    .message(GeneralResponse.INTERNAL_SERVER)
                    .objectResponse(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
