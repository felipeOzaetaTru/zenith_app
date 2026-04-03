package ZenithApp.MiradorOficial.commons.domains.entity.user;

import ZenithApp.MiradorOficial.commons.domains.enums.rol.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Builder(builderMethodName = "newInstance")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "usuario")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;               // era Integer

    @Column(name = "name", nullable = false)
    private String name;                // era "name"

    @Column(name = "email", nullable = false)
    // ← sin unique=true aquí, está en el @UniqueConstraint compuesto arriba
    private String email;                // era "email"

    @Column(name = "password", nullable = false)
    private String password;            // era "password"

    @Column(name = "image")
    private String image;                // era "image"

    /*@Column(name = "estado")
    private Boolean estado;  */             // ← nuevo: true=activo, false=inactivo

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Rol rol = Rol.ADMINISTRADOR;                      // era String — ahora solo acepta valores válidos

    // ── Relaciones ─────────────────────────────────────────────────────────
/*
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private EmpresaEntity empresa;        // ← clave de tenant

    // ── Auditoría ──────────────────────────────────────────────────────────

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
*/
    @PrePersist
    void onCreate() {
        if (this.rol == null) this.rol = Rol.ADMINISTRADOR;              // todo usuario nuevo nace activo
    }

}