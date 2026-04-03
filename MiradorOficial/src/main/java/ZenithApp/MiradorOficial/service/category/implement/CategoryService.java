package ZenithApp.MiradorOficial.service.category.implement;

import ZenithApp.MiradorOficial.commons.constans.response.GeneralResponse;
import ZenithApp.MiradorOficial.commons.constans.response.category.ICategoryResponse;
import ZenithApp.MiradorOficial.commons.converter.category.CategoryConverter;
import ZenithApp.MiradorOficial.commons.domains.dto.category.CategoryDTO;
import ZenithApp.MiradorOficial.commons.domains.entity.category.CategoryEntity;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import ZenithApp.MiradorOficial.commons.helpers.HelperMapper;
import ZenithApp.MiradorOficial.repository.category.ICategoryRepository;
import ZenithApp.MiradorOficial.service.category.ICategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Log4j2
public class CategoryService implements ICategoryService {

    private final ICategoryRepository iCategoryRepository;
    private final CategoryConverter categoryConverter;

    public CategoryService(ICategoryRepository iCategoryRepository, CategoryConverter categoryConverter) {
        this.iCategoryRepository = iCategoryRepository;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public ResponseEntity<GenericResponseDTO> createCategory(CategoryDTO categoryDTO) {
        try {
            Optional<CategoryEntity> categoryExist;
            categoryExist = iCategoryRepository.readProductForName(categoryDTO.getName());
            if(!categoryExist.isPresent()){
                CategoryEntity categoryEntity = categoryConverter.convertCategoryDTOToCategoryEntity(categoryDTO);
                iCategoryRepository.save(categoryEntity);
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(GeneralResponse.CREATE_SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }
            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(ICategoryResponse.CATEGORY_SUCCESS)
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
    public ResponseEntity<GenericResponseDTO> readCategory(Long id) {
        try {
            Optional<CategoryEntity> categoryEntity = iCategoryRepository.findById(id);
            if(categoryEntity.isPresent()){
                CategoryDTO categoryDTO = categoryConverter.convertCategoryEntityToCategoryDTO(categoryEntity.get());
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(categoryDTO)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }
            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(ICategoryResponse.CATEGORY_FAIL)
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
    public ResponseEntity<GenericResponseDTO> readAllCategory() {
        try {
            List<CategoryDTO> categoryDTOList = new ArrayList<>();
            List<CategoryEntity> categoryEntity = iCategoryRepository.findAll();
            if(!categoryEntity.isEmpty()){
                categoryEntity.forEach(userEntity -> {
                    CategoryDTO categoryDTO = categoryConverter.convertCategoryEntityToCategoryDTO(userEntity);
                    categoryDTOList.add(categoryDTO);
                });
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(categoryDTOList)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }

            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(ICategoryResponse.CATEGORY_FAIL)
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
    public ResponseEntity<GenericResponseDTO> updateCategory(CategoryDTO categoryDTO) {
        try {
            Optional<CategoryEntity> categoryEntity = iCategoryRepository.findById(categoryDTO.getId());
            log.info("CategoryDTO recibido: {}", categoryDTO); // ← agrega esto
            log.info("ID recibido: {}", categoryDTO.getId()); // ← y esto

            if (categoryEntity.isPresent()) {

                HelperMapper.modelMapper().map(categoryDTO, categoryEntity.get()); // ← solo actualiza los no null
                this.iCategoryRepository.save(categoryEntity.get());

                return new ResponseEntity<>(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(ICategoryResponse.CATEGORY_UPDATE_SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .build(), HttpStatus.OK);
            }

            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(ICategoryResponse.CATEGORY_UPDATE_FAIL)
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
    public ResponseEntity<GenericResponseDTO> deleteCategory(Long id) {
        try {
            Optional<CategoryEntity> categoryEntity = iCategoryRepository.findById(id);
            if(categoryEntity.isPresent()){
                iCategoryRepository.delete(categoryEntity.get());
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(ICategoryResponse.CATEGORY_DELETE_SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }
            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(ICategoryResponse.CATEGORY_DELETE_FAIL)
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
}

