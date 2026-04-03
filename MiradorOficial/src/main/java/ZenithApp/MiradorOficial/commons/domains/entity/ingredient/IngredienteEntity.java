package ZenithApp.MiradorOficial.commons.domains.entity.ingredient;

import ZenithApp.MiradorOficial.commons.domains.entity.category.CategoryEntity;
import ZenithApp.MiradorOficial.commons.domains.entity.inventory.InventarioEntity;
import ZenithApp.MiradorOficial.commons.domains.entity.store.EmpresaEntity;
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
    name = "ingrediente",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_ingrediente_nombre_empresa",
            columnNames = {"nombre", "empresa_id"}
        )
    }
)
public class IngredienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ingrediente")
    private Long idIngrediente;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad", nullable = false)
    private UnidadMedida unidad;           // GRAMO | MILILITRO | UNIDAD

    @Column(name = "activo", nullable = false)
    private boolean activo;

    // ── Relaciones ─────────────────────────────────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CategoryEntity categoria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EmpresaEntity empresa;         // ← clave de tenant

    @OneToOne(
        mappedBy = "ingrediente",
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY,
        orphanRemoval = true
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private InventarioEntity inventario;

    // ── Auditoría ──────────────────────────────────────────────────────────

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
