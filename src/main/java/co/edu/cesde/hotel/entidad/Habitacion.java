package co.edu.cesde.hotel.entidad;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "habitacion")
@Getter
@Setter
@NoArgsConstructor
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer numeroHabitacion;

    @Column(nullable = false)
    private Integer piso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoHabitacion tipoHabitacion;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorNoche;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoHabitacion estado;
}
