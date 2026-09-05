package cookiebecoInc.com.example.ReservaLabSala.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "Campo obrigatório")
        String email,

        @NotBlank(message = "Campo obrigatório")
        String senha
) {
}