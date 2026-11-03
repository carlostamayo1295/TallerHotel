package co.edu.cesde.hotel.excepcion;

public class CheckOutInvalidoException extends RuntimeException {

    public CheckOutInvalidoException(String mensaje) {
        super(mensaje);
    }
}
