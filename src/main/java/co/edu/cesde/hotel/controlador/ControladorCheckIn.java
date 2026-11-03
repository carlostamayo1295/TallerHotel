package co.edu.cesde.hotel.controlador;

import co.edu.cesde.hotel.dto.CheckInRequest;
import co.edu.cesde.hotel.dto.CheckInResponse;
import co.edu.cesde.hotel.servicio.ServicioCheckIn;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkin")
@RequiredArgsConstructor
public class ControladorCheckIn {

    private final ServicioCheckIn servicioCheckIn;

    @PostMapping
    public ResponseEntity<CheckInResponse> realizarCheckIn(
            @Valid @RequestBody CheckInRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicioCheckIn.realizarCheckIn(request));
    }
}
