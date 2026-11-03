package co.edu.cesde.hotel.handler;

import co.edu.cesde.hotel.excepcion.*;
import co.edu.cesde.hotel.respuesta.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ClienteNoEncontradoException.class,
            HabitacionNoEncontradaException.class,
            HospedajeActivoNoEncontradoException.class,
            FacturaNoEncontradaException.class
    })
    public ResponseEntity<ErrorResponse> noEncontrado(RuntimeException ex) {
        return responder(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({
            ClienteConHospedajeActivoException.class,
            HabitacionNoDisponibleException.class
    })
    public ResponseEntity<ErrorResponse> conflicto(RuntimeException ex) {
        return responder(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(CheckOutInvalidoException.class)
    public ResponseEntity<ErrorResponse> checkOutInvalido(CheckOutInvalidoException ex) {
        return responder(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return responder(HttpStatus.BAD_REQUEST, mensaje);
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ErrorResponse> formatoInvalido(Exception ex) {
        return responder(HttpStatus.BAD_REQUEST,
                "Revisa el formato de los datos y los valores enviados");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> integridad(DataIntegrityViolationException ex) {
        return responder(HttpStatus.CONFLICT,
                "La operación incumple una restricción de la base de datos");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> errorInterno(Exception ex) {
        log.error("Error inesperado", ex);
        return responder(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno en el servidor");
    }

    private ResponseEntity<ErrorResponse> responder(HttpStatus estado, String mensaje) {
        ErrorResponse error = new ErrorResponse(
                estado.value(),
                estado.getReasonPhrase(),
                mensaje,
                LocalDateTime.now()
        );

        return ResponseEntity.status(estado).body(error);
    }
}
