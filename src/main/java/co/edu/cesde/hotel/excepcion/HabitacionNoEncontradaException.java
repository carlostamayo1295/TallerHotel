package co.edu.cesde.hotel.excepcion;

public class HabitacionNoEncontradaException extends RuntimeException {

    public HabitacionNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
