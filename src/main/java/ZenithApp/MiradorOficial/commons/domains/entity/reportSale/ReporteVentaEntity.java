package ZenithApp.MiradorOficial.commons.domains.entity.reportSale;

import ZenithApp.MiradorOficial.commons.domains.entity.product.ProductEntity;
import ZenithApp.MiradorOficial.commons.domains.entity.store.EmpresaEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder(builderMethodName = "newInstance")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(
    name = "reporte_venta",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_reporte_fecha_producto_empresa",
            columnNames = {"fecha", "producto_id", "empresa_id"}
        )
    }
)
public class ReporteVentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte")
    private Long idReporte;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;               // resumen del día

    @Column(name = "cantidad_vendida", nullable = false)
    private Integer cantidadVendida;

    @Column(name = "total_ingreso", nullable = false)
    private Double totalIngreso;

    // ── Relaciones ─────────────────────────────────────────────────────────

   /* @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ProductEntity producto;*/

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EmpresaEntity empresa;         // ← clave de tenant

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
