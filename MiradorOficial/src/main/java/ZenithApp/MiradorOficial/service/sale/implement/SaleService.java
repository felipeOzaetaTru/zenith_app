package ZenithApp.MiradorOficial.service.sale.implement;

import ZenithApp.MiradorOficial.commons.constans.response.GeneralResponse;
import ZenithApp.MiradorOficial.commons.constans.response.sale.ISaleResponse;
import ZenithApp.MiradorOficial.commons.converter.sale.SaleConverter;
import ZenithApp.MiradorOficial.commons.domains.dto.detailSale.DetailSaleDTO;
import ZenithApp.MiradorOficial.commons.domains.dto.sale.SaleDTO;
import ZenithApp.MiradorOficial.commons.domains.entity.detailSale.DetailSaleEntity;
import ZenithApp.MiradorOficial.commons.domains.entity.product.ProductEntity;
import ZenithApp.MiradorOficial.commons.domains.entity.sale.SaleEntity;
import ZenithApp.MiradorOficial.commons.domains.responseDTO.GenericResponseDTO;
import ZenithApp.MiradorOficial.commons.helpers.HelperMapper;
import ZenithApp.MiradorOficial.repository.product.IProductRepository;
import ZenithApp.MiradorOficial.repository.sale.ISaleRepository;
import ZenithApp.MiradorOficial.service.sale.ISaleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Log4j2
public class SaleService implements ISaleService {

    private final ISaleRepository iSaleRepository;
    private final SaleConverter saleConverter;
    private final IProductRepository iProductRepository;

    public SaleService(ISaleRepository iSaleRepository, SaleConverter saleConverter, IProductRepository iProductRepository) {
        this.iSaleRepository = iSaleRepository;
        this.saleConverter = saleConverter;
        this.iProductRepository = iProductRepository;
    }

    @Override
    public ResponseEntity<GenericResponseDTO> createSale(SaleDTO saleDTO) {
        try {
            if (saleDTO.getId() == null) {

                List<DetailSaleEntity> items = new ArrayList<>();

                for (DetailSaleDTO itemDTO : saleDTO.getItems()) {
                    ProductEntity product = iProductRepository.findById(itemDTO.getProduct())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + itemDTO.getProduct()));

                    // Descontar stock con query nativa
                    int updated = iProductRepository.decrementStock(product.getId(), itemDTO.getQuantity());
                    log.info("Producto ID: {}, Quantity: {}, Updated rows: {}", product.getId(), itemDTO.getQuantity(), updated);
                    if (updated == 0) {
                        throw new RuntimeException("Stock insuficiente para: " + product.getName());
                    }

                    double itemSubtotal = itemDTO.getQuantity() * product.getSalePrice();

                    DetailSaleEntity detail = DetailSaleEntity.newInstance()
                            .product(product)
                            .quantity(itemDTO.getQuantity())
                            .subtotal(itemSubtotal)
                            .notes(itemDTO.getNotes())
                            .build();

                    items.add(detail);
                }

                double subtotal = items.stream()
                        .mapToDouble(DetailSaleEntity::getSubtotal)
                        .sum();
                double tip = subtotal * 0.19;
                double total = subtotal + tip;

                SaleEntity saleEntity = SaleEntity.newInstance()
                        .tableNumber(saleDTO.getTableNumber())
                        .customerCount(saleDTO.getCustomerCount())
                        .waiter(saleDTO.getWaiter())
                        .notes(saleDTO.getNotes())
                        .paymentMethod(saleDTO.getPaymentMethod())
                        .subtotal(subtotal)
                        .tip(tip)
                        .total(total)
                        .build();

                items.forEach(item -> item.setSale(saleEntity));
                saleEntity.setItems(items);
                iSaleRepository.save(saleEntity);

                List<DetailSaleDTO> itemsResponse = items.stream().map(item -> {
                    log.info("item.getProduct(): {}", item.getProduct());
                    log.info("productName: {}", item.getProduct() != null ? item.getProduct().getName() : "PRODUCTO NULL");
                    log.info("productPrice: {}", item.getProduct() != null ? item.getProduct().getSalePrice() : "PRECIO NULL");

                    return DetailSaleDTO.builder()
                            .id(item.getId())
                            .product(item.getProduct().getId())
                            .productName(item.getProduct().getName())
                            .productPrice(item.getProduct().getSalePrice())
                            .quantity(item.getQuantity())
                            .subtotal(item.getSubtotal())
                            .notes(item.getNotes())
                            .build();
                }).collect(Collectors.toList());

                SaleDTO response = SaleDTO.builder()
                        .id(saleEntity.getId())
                        .tableNumber(saleEntity.getTableNumber())
                        .customerCount(saleEntity.getCustomerCount())
                        .waiter(saleEntity.getWaiter())
                        .notes(saleEntity.getNotes())
                        .paymentMethod(saleEntity.getPaymentMethod())
                        .subtotal(saleEntity.getSubtotal())
                        .tip(saleEntity.getTip())
                        .total(saleEntity.getTotal())
                        .items(itemsResponse)
                        .build();

                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(response)
                        .statusCode(HttpStatus.OK.value())
                        .build());

            } else {
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(ISaleResponse.SALE_SUCCESS)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build());
            }
        } catch (RuntimeException e) {
            log.warn("Advertencia en createSale: {}", e.getMessage());
            return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                    .message(e.getMessage())
                    .objectResponse(null)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .build());
        } catch (Exception e) {
            log.error(GeneralResponse.INTERNAL_SERVER + e);
            return new ResponseEntity<>(GenericResponseDTO.builder()
                    .message(GeneralResponse.INTERNAL_SERVER)
                    .objectResponse(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<GenericResponseDTO> readSale(Long id) {
        try {
            Optional<SaleEntity> saleExist = iSaleRepository.findById(id);
            if(saleExist.isPresent()){
                SaleDTO saleDTO = saleConverter.convertSaleEntityToSaleDTO(saleExist.get());
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(saleDTO)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }
            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(ISaleResponse.SALE_FAIL)
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
    public ResponseEntity<GenericResponseDTO> readAllSale() {
        try {
            List<SaleDTO> saleDTOList = new ArrayList<>();
            List<SaleEntity> saleEntityList = iSaleRepository.findAll();
            if(!saleEntityList.isEmpty()){
                saleEntityList.forEach(saleEntity -> {
                    SaleDTO saleDTO = saleConverter.convertSaleEntityToSaleDTO(saleEntity);
                    saleDTOList.add(saleDTO);
                });
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(saleDTOList)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }
            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(ISaleResponse.SALE_FAIL)
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
    public ResponseEntity<GenericResponseDTO> updateSale(SaleDTO saleDTO) {
        try {
            Optional<SaleEntity> saleExist = iSaleRepository.findById(saleDTO.getId());
            if (saleExist.isPresent()) {
                SaleEntity saleEntity = saleExist.get();

                // 1. Actualizar solo campos que no sean null
                if (saleDTO.getTableNumber() != null) saleEntity.setTableNumber(saleDTO.getTableNumber());
                if (saleDTO.getCustomerCount() != null) saleEntity.setCustomerCount(saleDTO.getCustomerCount());
                if (saleDTO.getWaiter() != null) saleEntity.setWaiter(saleDTO.getWaiter());
                if (saleDTO.getNotes() != null) saleEntity.setNotes(saleDTO.getNotes());
                if (saleDTO.getPaymentMethod() != null) saleEntity.setPaymentMethod(saleDTO.getPaymentMethod());

                // 2. Recalcular items solo si vienen en el request
                if (saleDTO.getItems() != null && !saleDTO.getItems().isEmpty()) {
                    List<DetailSaleEntity> items = saleDTO.getItems().stream().map(itemDTO -> {
                        ProductEntity product = iProductRepository.findById(itemDTO.getProduct())
                                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                        double itemSubtotal = itemDTO.getQuantity() * product.getSalePrice();

                        return DetailSaleEntity.newInstance()
                                .product(product)
                                .quantity(itemDTO.getQuantity())
                                .subtotal(itemSubtotal)
                                .notes(itemDTO.getNotes())
                                .build();

                    }).collect(Collectors.toList());

                    double subtotal = items.stream()
                            .mapToDouble(DetailSaleEntity::getSubtotal)
                            .sum();
                    double tip = subtotal * 0.19;
                    double total = subtotal + tip;

                    saleEntity.setSubtotal(subtotal);
                    saleEntity.setTip(tip);
                    saleEntity.setTotal(total);

                    saleEntity.getItems().clear();
                    items.forEach(item -> item.setSale(saleEntity));
                    saleEntity.getItems().addAll(items);
                }

                // ✅ save y return fuera del if de items
                iSaleRepository.save(saleEntity);
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(ISaleResponse.SALE_UPDATE_SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .build());

            } else {
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(ISaleResponse.SALE_UPDATE_FAIL)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build());
            }
        } catch (Exception e) {
            log.error(GeneralResponse.INTERNAL_SERVER + e);
            return new ResponseEntity<>(GenericResponseDTO.builder()
                    .message(GeneralResponse.INTERNAL_SERVER)
                    .objectResponse(null)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<GenericResponseDTO> deleteSale(Long id) {
        try {
            Optional<SaleEntity> saleEntity = iSaleRepository.findById(id);
            if(saleEntity.isPresent()){
                iSaleRepository.delete(saleEntity.get());
                return ResponseEntity.ok(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_SUCCESS)
                        .objectResponse(ISaleResponse.SALE_DELETE_SUCCESS)
                        .statusCode(HttpStatus.OK.value())
                        .build());
            }
            else{
                return ResponseEntity.badRequest().body(GenericResponseDTO.builder()
                        .message(GeneralResponse.OPERATION_FAIL)
                        .objectResponse(ISaleResponse.SALE_DELETE_FAIL)
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
