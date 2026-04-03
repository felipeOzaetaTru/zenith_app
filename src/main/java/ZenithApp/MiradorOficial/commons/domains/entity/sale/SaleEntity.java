package ZenithApp.MiradorOficial.commons.domains.entity.sale;

import ZenithApp.MiradorOficial.commons.domains.entity.detailSale.DetailSaleEntity;
import ZenithApp.MiradorOficial.commons.domains.enums.paymentMethod.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder(builderMethodName = "newInstance")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "venta")
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "table_number", nullable = false)
    private String tableNumber;

    @Column(name = "customer_count", nullable = false)
    private Integer customerCount;

    @Column(name = "waiter", nullable = false)
    private String waiter;

    @Column(name = "notes")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "subtotal", nullable = false)
    private Double subtotal;

    @Column(name = "tip")
    private Double tip;

    @Column(name = "total", nullable = false)
    private Double total;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailSaleEntity> items = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.tip == null) this.tip = 0.0;
    }
    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}