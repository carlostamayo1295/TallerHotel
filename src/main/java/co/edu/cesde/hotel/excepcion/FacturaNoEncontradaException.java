package co.edu.cesde.hotel.excepcion;

public class FacturaNoEncontradaException extends RuntimeException {

    public FacturaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
