package co.edu.cesde.hotel.excepcion;

public class HabitacionNoDisponibleException extends RuntimeException {

    public HabitacionNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}
