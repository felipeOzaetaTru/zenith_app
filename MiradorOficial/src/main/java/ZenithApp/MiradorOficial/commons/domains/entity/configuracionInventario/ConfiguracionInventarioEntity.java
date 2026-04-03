// ZenithApp/MiradorOficial/commons/domains/entity/inventory/ConfiguracionInventarioEntity.java
package ZenithApp.MiradorOficial.commons.domains.entity.configuracionInventario;

import ZenithApp.MiradorOficial.commons.domains.entity.store.EmpresaEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Builder(builderMethodName = "newInstance")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "configuracion_inventario")
public class ConfiguracionInventarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_configuracion")
    private Long idConfiguracion;

    // ── Umbrales ───────────────────────────────────────────────────────────

    @Column(name = "umbral_bajo", nullable = false)
    private Double umbralBajo;             // ej: 20 → stock <= 20 es BAJO

    @Column(name = "umbral_critico", nullable = false)
    private Double umbralCritico;          // ej: 5  → stock <= 5  es CRITICO

    // ── Relación ───────────────────────────────────────────────────────────

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false, unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EmpresaEntity empresa;         // 1 configuración por empresa

    // ── Auditoría ──────────────────────────────────────────────────────────

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist @PreUpdate
    void onSave() { this.updatedAt = LocalDateTime.now(); }
}