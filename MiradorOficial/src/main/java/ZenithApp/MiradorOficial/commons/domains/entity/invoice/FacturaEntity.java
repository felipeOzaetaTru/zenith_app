package ZenithApp.MiradorOficial.commons.domains.entity.invoice;

import ZenithApp.MiradorOficial.commons.domains.entity.sale.SaleEntity;
import ZenithApp.MiradorOficial.commons.domains.entity.store.EmpresaEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Builder(builderMethodName = "newInstance")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(
    name = "factura",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_factura_numero_empresa",
            columnNames = {"numero_factura", "empresa_id"}
        )
    }
)
public class FacturaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;

    @Column(name = "numero_factura", nullable = false)
    private String numeroFactura;          // único por empresa (no global)

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    // ── Relaciones ─────────────────────────────────────────────────────────

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venta_id", nullable = false, unique = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private SaleEntity venta;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EmpresaEntity empresa;         // ← clave de tenant

    // ── Auditoría ──────────────────────────────────────────────────────────

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.fecha == null) this.fecha = LocalDateTime.now();
    }
}
