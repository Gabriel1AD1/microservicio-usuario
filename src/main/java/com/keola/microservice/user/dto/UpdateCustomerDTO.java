package com.keola.microservice.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCustomerDTO {

    @JsonProperty("name")
    @NotBlank(message = "Name cannot be null or empty")  // No puede ser null ni vacío
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")  // Limita el tamaño
    private String name;

    @JsonProperty("email")
    @NotBlank(message = "Email cannot be null or empty")  // No puede ser null ni vacío
    @Email(message = "Email should be valid")  // Debe tener un formato de correo electrónico válido
    private String email;

    @JsonProperty("address")
    @NotBlank(message = "Address cannot be null or empty")  // No puede ser null ni vacío
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")  // Limita el tamaño
    private String address;

    @JsonProperty("password")
    @NotBlank(message = "Password cannot be null or empty")  // No puede ser null ni vacío
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")  // Limita el tamaño
    private String password;
}
