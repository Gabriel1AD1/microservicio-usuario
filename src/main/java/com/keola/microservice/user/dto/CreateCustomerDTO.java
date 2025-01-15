package com.keola.microservice.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCustomerDTO {

    @JsonProperty("name")
    @NotBlank(message = "Name is required") // Asegura que el nombre no sea vacío
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters") // Limita el tamaño del nombre
    private String name;

    @JsonProperty("email")
    @NotBlank(message = "Email is required") // Asegura que el correo no sea vacío
    @Email(message = "Email should be valid") // Valida que el formato del correo sea correcto
    private String email;

    @JsonProperty("address")
    @NotBlank(message = "Address is required") // Asegura que la dirección no sea vacía
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters") // Limita el tamaño de la dirección
    private String address;

    @JsonProperty("password")
    @NotBlank(message = "Password is required") // Asegura que la contraseña no sea vacía
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters") // Limita el tamaño de la contraseña
    private String password;
}
