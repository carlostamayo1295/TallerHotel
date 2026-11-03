package co.edu.cesde.hotel.controlador;

import co.edu.cesde.hotel.dto.ClienteRequest;
import co.edu.cesde.hotel.dto.ClienteResponse;
import co.edu.cesde.hotel.servicio.ServicioCliente;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ControladorCliente {

    private final ServicioCliente servicioCliente;

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(
            @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicioCliente.crearCliente(request));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> consultarTodos() {
        return ResponseEntity.ok(servicioCliente.consultarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> consultarPorId(
            @PathVariable Long id) {
        return ResponseEntity.ok(servicioCliente.consultarPorId(id));
    }

    @GetMapping("/documento/{identificacion}")
    public ResponseEntity<ClienteResponse> consultarPorIdentificacion(
            @PathVariable String identificacion) {
        return ResponseEntity.ok(
                servicioCliente.consultarPorIdentificacion(identificacion));
    }
}
