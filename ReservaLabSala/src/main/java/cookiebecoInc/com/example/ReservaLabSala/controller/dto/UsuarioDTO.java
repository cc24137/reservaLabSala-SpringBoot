package cookiebecoInc.com.example.ReservaLabSala.controller.dto;

import cookiebecoInc.com.example.ReservaLabSala.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record UsuarioDTO(
        Integer id,
        @NotBlank(message = "Campo obrigatório")
        String cpf,
        @NotBlank(message = "Campo obrigatório")
        String nome,
        @NotNull(message = "Campo obrigatório")
        LocalDate dataAniversario,
        @NotBlank(message = "Campo obrigatório")
        String celular,
        @NotBlank(message = "Campo obrigatório")
        @Email(message = "E-mail inválido")
        String email,
        @NotBlank(message = "Campo obrigatório")
        String senha
) {
    public Usuario mapearDadosParaEntidadeUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(this.id);
        usuario.setCpf(this.cpf);
        usuario.setNome(this.nome);
        usuario.setDataAniversario(this.dataAniversario);
        usuario.setCelular(this.celular);
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);
        return usuario;
    }
}
