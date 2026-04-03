package ZenithApp.MiradorOficial.commons.domains.entity.inventory;

import ZenithApp.MiradorOficial.commons.domains.entity.configuracionInventario.ConfiguracionInventarioEntity;
import ZenithApp.MiradorOficial.commons.domains.entity.ingredient.IngredienteEntity;
import ZenithApp.MiradorOficial.commons.domains.entity.store.EmpresaEntity;
import ZenithApp.MiradorOficial.commons.domains.enums.estadoStock.EstadoStock;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Builder(builderMethodName = "newInstance")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "inventario")
public class InventarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Long idInventario;

    @Column(name = "stock_actual", nullable = false)
    private Double stockActual;

    // ── Relaciones ─────────────────────────────────────────────────────────

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingrediente_id", nullable = false, unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private IngredienteEntity ingrediente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EmpresaEntity empresa;

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
    void onUpdate() { this.updatedAt = LocalDateTime.now(); }

    // ── Lógica de estado ───────────────────────────────────────────────────

    /**
     * Calcula el estado actual del stock comparando contra
     * los umbrales de la configuración de la empresa.
     *
     * Uso: inventario.calcularEstado(config)
     */
    public EstadoStock calcularEstado(ConfiguracionInventarioEntity config) {
        if (this.stockActual <= 0)                          return EstadoStock.AGOTADO;
        if (this.stockActual <= config.getUmbralCritico())  return EstadoStock.CRITICO;
        if (this.stockActual <= config.getUmbralBajo())     return EstadoStock.BAJO;
        return EstadoStock.BIEN;
    }

    public void actualizarStock(Double cantidad) {
        this.stockActual += cantidad;
    }
}