package cookiebecoInc.com.example.ReservaLabSala.controller.dto;

import cookiebecoInc.com.example.ReservaLabSala.model.Sala;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SalaDTO(
        Integer id,
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @NotNull(message = "Campo obrigatório")
        Integer capacidade,
        @NotBlank(message = "Campo obrigatório")
        String localizacao
) {
    public Sala mapearDadosParaEntidadeSala() {
        Sala sala = new Sala();
        sala.setId(this.id);
        sala.setNome(this.nome);
        sala.setCapacidade(this.capacidade);
        sala.setLocalizacao(this.localizacao);
        return sala;
    }
}
