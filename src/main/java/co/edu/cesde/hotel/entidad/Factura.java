package co.edu.cesde.hotel.entidad;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "factura")
@Immutable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hospedaje_id", nullable = false, unique = true)
    private Hospedaje hospedaje;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaGeneracion;

    @Column(nullable = false, updatable = false)
    private Integer cantidadNoches;

    @Column(nullable = false, updatable = false, precision = 12, scale = 2)
    private BigDecimal valorNoche;

    @Column(nullable = false, updatable = false, precision = 15, scale = 2)
    private BigDecimal valorTotal;

    public Factura(Hospedaje hospedaje, int cantidadNoches, BigDecimal valorNoche) {
        this.hospedaje = hospedaje;
        this.fechaGeneracion = LocalDateTime.now();
        this.cantidadNoches = cantidadNoches;
        this.valorNoche = valorNoche;
        this.valorTotal = valorNoche.multiply(BigDecimal.valueOf(cantidadNoches));
    }
}
