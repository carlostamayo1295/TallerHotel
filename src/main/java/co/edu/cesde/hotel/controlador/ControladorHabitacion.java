package co.edu.cesde.hotel.controlador;

import co.edu.cesde.hotel.dto.HabitacionResponse;
import co.edu.cesde.hotel.entidad.TipoHabitacion;
import co.edu.cesde.hotel.servicio.ServicioHabitacion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@RequiredArgsConstructor
public class ControladorHabitacion {

    private final ServicioHabitacion servicioHabitacion;

    @GetMapping
    public ResponseEntity<List<HabitacionResponse>> consultarTodas() {
        return ResponseEntity.ok(servicioHabitacion.consultarTodas());
    }

    @GetMapping("/{numero}")
    public ResponseEntity<HabitacionResponse> consultarPorNumero(
            @PathVariable Integer numero) {
        return ResponseEntity.ok(
                servicioHabitacion.consultarPorNumero(numero));
    }

    @GetMapping("/disponibles/{tipo}")
    public ResponseEntity<HabitacionResponse> consultarDisponibilidad(
            @PathVariable TipoHabitacion tipo) {
        return ResponseEntity.ok(
                servicioHabitacion.buscarDisponiblePorTipo(tipo));
    }
}
