package co.edu.cesde.hotel.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "hospedaje")
@Getter
@Setter
@NoArgsConstructor
public class Hospedaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "habitacion_id", nullable = false)
    private Habitacion habitacion;

    @Column(nullable = false)
    private LocalDateTime fechaHoraIngreso;

    private LocalDateTime fechaHoraSalida;

    @Column(nullable = false)
    private boolean activo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    void registrarFecha() {
        fechaCreacion = LocalDateTime.now();
    }
}
