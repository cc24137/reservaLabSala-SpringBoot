package cookiebecoInc.com.example.ReservaLabSala.controller.dto;

import cookiebecoInc.com.example.ReservaLabSala.model.StatusReserva;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StatusReservaDTO(
        Integer id,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 15, max = 20, message = "Quantidade de caracteres incorreta!")
        String nome
) {
    public StatusReserva mapearDadosParaEntidadeStatusReserva() {
        StatusReserva status = new StatusReserva();
        status.setId(this.id);
        status.setNome(this.nome);
        return status;
    }
}
