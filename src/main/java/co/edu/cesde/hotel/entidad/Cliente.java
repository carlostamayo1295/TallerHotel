package co.edu.cesde.hotel.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String identificacion;

    @Column(nullable = false)
    private String nombreCompleto;

    @Column(nullable = false)
    private String correoElectronico;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    void registrarFecha() {
        fechaCreacion = LocalDateTime.now();
    }
}
