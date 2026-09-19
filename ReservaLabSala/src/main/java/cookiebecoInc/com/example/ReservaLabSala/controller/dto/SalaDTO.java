package cookiebecoInc.com.example.ReservaLabSala.controller.dto;

import cookiebecoInc.com.example.ReservaLabSala.model.Sala;
import cookiebecoInc.com.example.ReservaLabSala.model.StatusRecurso;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SalaDTO(
        Integer id,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 3, max = 80, message = "Quantidade de caracteres incorreta!")
        String nome,

        @NotNull(message = "Campo obrigatório")
        @Min(value = 1, message = "Valor fora do escopo")
        @Max(value = 40, message = "Valor fora do escopo")
        Integer capacidade,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 15, max = 50, message = "Quantidade de caracteres incorreta!")
        String localizacao,

        @NotNull(message = "Campo obrigatório")
        Integer statusRecursoId
) {
    public Sala mapearDadosParaEntidadeSala() {
        Sala sala = new Sala();
        sala.setId(this.id);
        sala.setNome(this.nome);
        sala.setCapacidade(this.capacidade);
        sala.setLocalizacao(this.localizacao);

        if (this.statusRecursoId != null) {
            StatusRecurso status = new StatusRecurso();
            status.setId(this.statusRecursoId);
            sala.setStatusRecurso(status);
        }

        return sala;
    }
}
