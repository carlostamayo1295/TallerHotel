package co.edu.cesde.hotel.excepcion;

public class ClienteNoEncontradoException extends RuntimeException {

    public ClienteNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
