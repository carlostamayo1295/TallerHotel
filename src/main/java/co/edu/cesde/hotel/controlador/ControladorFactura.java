package co.edu.cesde.hotel.controlador;

import co.edu.cesde.hotel.dto.FacturaResponse;
import co.edu.cesde.hotel.servicio.ServicioFactura;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class ControladorFactura {

    private final ServicioFactura servicioFactura;

    @GetMapping
    public ResponseEntity<List<FacturaResponse>> consultarTodas() {
        return ResponseEntity.ok(servicioFactura.consultarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponse> consultarPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(servicioFactura.consultarPorId(id));
    }
}
