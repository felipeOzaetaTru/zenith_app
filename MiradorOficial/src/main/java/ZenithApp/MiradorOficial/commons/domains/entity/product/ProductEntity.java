package ZenithApp.MiradorOficial.commons.domains.entity.product;

import ZenithApp.MiradorOficial.commons.domains.entity.category.CategoryEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder(builderMethodName = "newInstance")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "producto_mirador")
public class ProductEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "description")
        private String description;

        @Column(name = "sku")
        private String sku;

        @Column(name = "barcode")
        private String barcode;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id", nullable = false)
        @ToString.Exclude
        @EqualsAndHashCode.Exclude
        private CategoryEntity category;

        @Column(name = "supplier")
        private String supplier;

        @Column(name = "cost_price")
        private Double costPrice;

        @Column(name = "sale_price")
        private Double salePrice;

        @Column(name = "stock")
        private Integer stock;

        @Column(name = "min_stock")
        private Integer minStock;

        @Column(name = "max_stock")
        private Integer maxStock;

        @Column(name = "location")
        private String location;

        @Column(name = "weight")
        private Double weight;

        @Column(name = "dimensions")
        private String dimensions;

        @Builder.Default
        @Column(name = "is_active", nullable = false)
        private Boolean isActive = true;

        @Builder.Default
        @Column(name = "is_featured", nullable = false)
        private Boolean isFeatured = false;


        @PrePersist
        void onCreate() {
            if (this.isActive == null) this.isActive = true;
            if (this.isFeatured == null) this.isFeatured = false;
            if (this.stock == null) this.stock = 0;
            if (this.minStock == null) this.minStock = 0;
            if (this.maxStock == null) this.maxStock = 0;
            if (this.weight == null) this.weight = 0.0;
        }

        @PreUpdate
        void onUpdate() {
                if (this.isActive == null) this.isActive = true;
                if (this.isFeatured == null) this.isFeatured = false;
                if (this.stock == null) this.stock = 0;
                if (this.minStock == null) this.minStock = 0;
                if (this.maxStock == null) this.maxStock = 0;
                if (this.weight == null) this.weight = 0.0;
        }
}