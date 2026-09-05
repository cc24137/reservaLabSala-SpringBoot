package cookiebecoInc.com.example.ReservaLabSala.controller.dto;

import cookiebecoInc.com.example.ReservaLabSala.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UsuarioDTO(
        Integer id,

        @NotBlank(message = "Campo obrigatório")
        @CPF(message = "CPF inválido")
        String cpf,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 10, max = 80, message = "Quantidade de caracteres incorreta!")
        String nome,

        @NotNull(message = "Campo obrigatório")
        LocalDate dataAniversario,

        @NotBlank(message = "Campo obrigatório")
        String celular,

        @NotBlank(message = "Campo obrigatório")
        @Email(message = "E-mail inválido")
        @Size(min = 15, max = 80, message = "Quantidade de caracteres incorreta!")
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
