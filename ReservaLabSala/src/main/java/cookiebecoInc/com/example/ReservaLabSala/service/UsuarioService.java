package cookiebecoInc.com.example.ReservaLabSala.service;

import cookiebecoInc.com.example.ReservaLabSala.model.Usuario;
import cookiebecoInc.com.example.ReservaLabSala.repository.UsuarioRepository;
import cookiebecoInc.com.example.ReservaLabSala.validator.UsuarioValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            UsuarioValidator usuarioValidator) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioValidator = usuarioValidator;
    }

    public Usuario inserirUsuario(Usuario usuario) {
        usuarioValidator.validar(usuario);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> pegarDadosUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public void excluirUsuarioPorId(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizarUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("Não existe USUÁRIO com o ID informado.");
        }
        usuarioValidator.validar(usuario);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> pesquisarPorEmailEDataAniversario(String email, LocalDate dataAniversario) {
        if (email != null && dataAniversario != null) {
            return usuarioRepository.findByEmailAndDataAniversario(email, dataAniversario);
        }
        if (email != null) {
            return usuarioRepository.findByEmail(email).map(List::of).orElse(List.of());
        }
        if (dataAniversario != null) {
            return usuarioRepository.findByDataAniversario(dataAniversario);
        }
        return usuarioRepository.findAll();
    }
}
