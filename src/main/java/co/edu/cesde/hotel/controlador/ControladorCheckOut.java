package co.edu.cesde.hotel.controlador;

import co.edu.cesde.hotel.dto.CheckOutRequest;
import co.edu.cesde.hotel.dto.FacturaResponse;
import co.edu.cesde.hotel.servicio.ServicioCheckOut;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class ControladorCheckOut {

    private final ServicioCheckOut servicioCheckOut;

    @PostMapping
    public ResponseEntity<FacturaResponse> realizarCheckOut(
            @Valid @RequestBody CheckOutRequest request) {
        return ResponseEntity.ok(
                servicioCheckOut.realizarCheckOut(request));
    }
}
