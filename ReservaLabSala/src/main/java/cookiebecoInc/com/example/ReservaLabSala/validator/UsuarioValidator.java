package cookiebecoInc.com.example.ReservaLabSala.validator;

import cookiebecoInc.com.example.ReservaLabSala.model.Usuario;
import cookiebecoInc.com.example.ReservaLabSala.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public UsuarioValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void validar(Usuario usuario) {
        if (existeCpfCadastrado(usuario)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "CPF já cadastrado");
        }
        if (existeEmailCadastrado(usuario)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "E-mail já cadastrado");
        }
    }

    private boolean existeCpfCadastrado(Usuario usuario) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByCpf(usuario.getCpf());

        if (usuario.getId() == null) {
            return usuarioEncontrado.isPresent();
        }

        return usuarioEncontrado.isPresent() && !usuario.getId().equals(usuarioEncontrado.get().getId());
    }

    private boolean existeEmailCadastrado(Usuario usuario) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuario.getId() == null) {
            return usuarioEncontrado.isPresent();
        }

        return usuarioEncontrado.isPresent() && !usuario.getId().equals(usuarioEncontrado.get().getId());
    }
}
