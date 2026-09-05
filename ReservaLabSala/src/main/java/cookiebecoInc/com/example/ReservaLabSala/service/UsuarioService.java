package cookiebecoInc.com.example.ReservaLabSala.service;

import cookiebecoInc.com.example.ReservaLabSala.model.Usuario;
import cookiebecoInc.com.example.ReservaLabSala.repository.UsuarioRepository;
import cookiebecoInc.com.example.ReservaLabSala.validator.UsuarioValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioValidator usuarioValidator) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioValidator = usuarioValidator;
    }

    public Usuario salvar(Usuario usuario) {
        usuarioValidator.validar(usuario);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Usuário não encontrado"));
    }

    public Usuario autenticar(String email, String senha) {
        return usuarioRepository.findByEmailAndSenha(email, senha)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "E-mail ou senha incorretos"));
    }

    public List<Usuario> pesquisarPorFiltros(String cpf, String nome, String email, LocalDate dataAniversario) {
        if (cpf != null && !cpf.isBlank()) {
            return usuarioRepository.findByCpf(cpf).map(List::of).orElse(List.of());
        }
        if (email != null && !email.isBlank()) {
            return usuarioRepository.findByEmail(email).map(List::of).orElse(List.of());
        }
        if (nome != null && !nome.isBlank()) {
            return usuarioRepository.findByNome(nome);
        }
        if (dataAniversario != null) {
            return usuarioRepository.findByDataAniversario(dataAniversario);
        }
        return usuarioRepository.findAll();
    }
}
