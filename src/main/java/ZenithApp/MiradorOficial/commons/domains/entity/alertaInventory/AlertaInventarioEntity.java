package ZenithApp.MiradorOficial.commons.domains.entity.alertaInventory;


import ZenithApp.MiradorOficial.commons.domains.entity.inventory.InventarioEntity;
import ZenithApp.MiradorOficial.commons.domains.entity.store.EmpresaEntity;
import ZenithApp.MiradorOficial.commons.domains.enums.tipoAlerta.TipoAlerta;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Builder(builderMethodName = "newInstance")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "alerta_inventario")
public class AlertaInventarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long idAlerta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoAlerta tipo;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "resuelta", nullable = false)
    private boolean resuelta;

    @Column(name = "mensaje")
    private String mensaje;                // descripción legible de la alerta

    // ── Relaciones ─────────────────────────────────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventario_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private InventarioEntity inventario;

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
        if (this.fecha == null) this.fecha = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ── Métodos de negocio ─────────────────────────────────────────────────

    public void marcarResuelta() {
        this.resuelta = true;
    }
}
