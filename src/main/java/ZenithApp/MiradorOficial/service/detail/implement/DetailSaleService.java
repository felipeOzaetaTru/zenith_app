package ZenithApp.MiradorOficial.service.detail.implement;

import ZenithApp.MiradorOficial.commons.constans.response.GeneralResponse;
import ZenithApp.MiradorOficial.commons.constans.response.detail.IDetailResponse;
import ZenithApp.MiradorOficial.commons.converter.detail.DetailConverter;
import ZenithApp.MiradorOficial.commons.domains.dto.detailSale.DetailSaleDTO;
import ZenithApp.MiradorOficial.commons.domains.entity.detailSale.DetailSaleEntity;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import ZenithApp.MiradorOficial.commons.helpers.HelperMapper;
import ZenithApp.MiradorOficial.repository.detail.IDetailSaleRepository;
import ZenithApp.MiradorOficial.service.detail.IDetailSaleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Log4j2
public class DetailSaleService implements IDetailSaleService {

    private final IDetailSaleRepository iDetailSaleRepository;
    private final DetailConverter detailConverter;

    public DetailSaleService(IDetailSaleRepository iDetailSaleRepository, DetailConverter detailConverter) {
        this.iDetailSaleRepository = iDetailSaleRepository;
        this.detailConverter = detailConverter;
    }

    @Override
    public ResponseEntity<GenericResponseDTO> createDetailSale(DetailSaleDTO detailSaleDTO) {
        try {
            Optional<DetailSaleEntity> detailSaleExist;
            detailSaleExist = iDetailSaleRepository.findById(detailSaleDTO.getId());
            if(!detailSaleExist.isPresent()){
                DetailSaleEntity detailSaleEntity = detailConverter.convertDetailSaleDTOToDetailSaleEntity(detailSaleDTO);
                iDetailSaleRepository.save(detailSaleEntity);
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(GeneralResponse.CREATE_SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }
            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(IDetailResponse.DETAIL_SUCCESS)
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
    public ResponseEntity<GenericResponseDTO> readDetailSale(Long id) {
        try {
            Optional<DetailSaleEntity> detailSaleExist = iDetailSaleRepository.findById(id);
            if(detailSaleExist.isPresent()){
                DetailSaleDTO detailSaleDTO = detailConverter.convertDetailSaleEntityToDetailSaleDTO(detailSaleExist.get());
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(detailSaleDTO)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }
            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(IDetailResponse.DETAIL_FAIL)
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
    public ResponseEntity<GenericResponseDTO> readAllDetailSale() {
        try {
            List<DetailSaleDTO> detailSaleDTOList = new ArrayList<>();
            List<DetailSaleEntity> detailSaleEntityList = iDetailSaleRepository.findAll();
            if(!detailSaleEntityList.isEmpty()){
                detailSaleEntityList.forEach(detailSaleEntity -> {
                    DetailSaleDTO detailSaleDTO = detailConverter.convertDetailSaleEntityToDetailSaleDTO(detailSaleEntity);
                    detailSaleDTOList.add(detailSaleDTO);
                });
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(detailSaleDTOList)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }
            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(IDetailResponse.DETAIL_FAIL)
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
    public ResponseEntity<GenericResponseDTO> updateDetailService(DetailSaleDTO detailSaleDTO) {
        try {
            Optional<DetailSaleEntity> detailSaleEntity = iDetailSaleRepository.findById(detailSaleDTO.getId());
            if (detailSaleEntity.isPresent()) {

                HelperMapper.modelMapper().map(detailSaleDTO, detailSaleEntity.get()); // ← solo actualiza los no null
                iDetailSaleRepository.save(detailSaleEntity.get());
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(IDetailResponse.DETAIL_UPDATE_SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }
            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(IDetailResponse.DETAIL_UPDATE_FAIL)
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
    public ResponseEntity<GenericResponseDTO> deleteDetailService(Long id) {
        try {
            Optional<DetailSaleEntity> detailSaleEntity = iDetailSaleRepository.findById(id);
            if (detailSaleEntity.isPresent()) {
                iDetailSaleRepository.delete(detailSaleEntity.get());
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(IDetailResponse.DETAIL_UPDATE_SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }
            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(IDetailResponse.DETAIL_UPDATE_FAIL)
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