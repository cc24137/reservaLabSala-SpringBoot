package cookiebecoInc.com.example.ReservaLabSala.controller.dto;

import cookiebecoInc.com.example.ReservaLabSala.model.Status;
import jakarta.validation.constraints.NotBlank;

public record StatusDTO(
        Integer id,
        @NotBlank(message = "Campo obrigatório")
        String nome
) {
    public Status mapearDadosParaEntidadeStatus() {
        Status status = new Status();
        status.setId(this.id);
        status.setNome(this.nome);
        return status;
    }
}
