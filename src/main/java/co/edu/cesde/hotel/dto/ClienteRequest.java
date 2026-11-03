package co.edu.cesde.hotel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(
        @NotBlank(message = "La identificación es obligatoria")
        String identificacion,

        @NotBlank(message = "El nombre es obligatorio")
        String nombreCompleto,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no es válido")
        String correoElectronico,

        @NotBlank(message = "El teléfono es obligatorio")
        String telefono
) {}
