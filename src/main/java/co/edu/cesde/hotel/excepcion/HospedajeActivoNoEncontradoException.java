package co.edu.cesde.hotel.excepcion;

public class HospedajeActivoNoEncontradoException extends RuntimeException {

    public HospedajeActivoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
