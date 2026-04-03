package ZenithApp.MiradorOficial.commons.domains.entity.productIngredient;

import ZenithApp.MiradorOficial.commons.domains.entity.ingredient.IngredienteEntity;
import ZenithApp.MiradorOficial.commons.domains.entity.product.ProductEntity;
import ZenithApp.MiradorOficial.commons.domains.enums.unidadMedida.UnidadMedida;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Builder(builderMethodName = "newInstance")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(
    name = "producto_ingrediente",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_producto_ingrediente",
            columnNames = {"producto_id", "ingrediente_id"}
        )
    }
)
public class ProductoIngredienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cantidad", nullable = false)
    private Double cantidad;               // cuánto se usa por unidad de producto

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad", nullable = false)
    private UnidadMedida unidad;

    // ── Relaciones ─────────────────────────────────────────────────────────

   /* @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
        private ProductEntity producto;*/

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingrediente_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private IngredienteEntity ingrediente;

    // ── Auditoría ──────────────────────────────────────────────────────────

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
