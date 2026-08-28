package cookiebecoInc.com.example.ReservaLabSala.controller.dto;

import cookiebecoInc.com.example.ReservaLabSala.model.Laboratorio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LaboratorioDTO(
        Integer id,
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @NotNull(message = "Campo obrigatório")
        Integer capacidade,
        @NotBlank(message = "Campo obrigatório")
        String localizacao
) {
    public Laboratorio mapearDadosParaEntidadeLaboratorio() {
        Laboratorio laboratorio = new Laboratorio();
        laboratorio.setId(this.id);
        laboratorio.setNome(this.nome);
        laboratorio.setCapacidade(this.capacidade);
        laboratorio.setLocalizacao(this.localizacao);
        return laboratorio;
    }
}