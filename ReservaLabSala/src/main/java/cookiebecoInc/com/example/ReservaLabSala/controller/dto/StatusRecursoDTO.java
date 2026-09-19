package cookiebecoInc.com.example.ReservaLabSala.controller.dto;

import cookiebecoInc.com.example.ReservaLabSala.model.StatusRecurso;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StatusRecursoDTO(
        Integer id,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 15, max = 20, message = "Quantidade de caracteres incorreta!")
        String nome
) {
    public StatusRecurso mapearDadosParaEntidadeStatusRecurso() {
        StatusRecurso status = new StatusRecurso();
        status.setId(this.id);
        status.setNome(this.nome);
        return status;
    }
}
