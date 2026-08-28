package cookiebecoInc.com.example.ReservaLabSala.controller;

import cookiebecoInc.com.example.ReservaLabSala.controller.dto.ErroResposta;
import cookiebecoInc.com.example.ReservaLabSala.controller.dto.UsuarioDTO;
import cookiebecoInc.com.example.ReservaLabSala.exceptions.RegistroDuplicadoException;
import cookiebecoInc.com.example.ReservaLabSala.model.Usuario;
import cookiebecoInc.com.example.ReservaLabSala.service.UsuarioService;
import cookiebecoInc.com.example.ReservaLabSala.validator.UsuarioValidator;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioValidator usuarioValidator;

    public UsuarioController(UsuarioService usuarioService, UsuarioValidator usuarioValidator) {
        this.usuarioService = usuarioService;
        this.usuarioValidator = usuarioValidator;
    }

    @PostMapping
    public ResponseEntity<Object> incluirUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioDTO.mapearDadosParaEntidadeUsuario();
            usuarioService.inserirUsuario(usuario);
            return new ResponseEntity<>("Usuário cadastrado com sucesso! " + usuarioDTO, HttpStatus.CREATED);
        } catch (RegistroDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroResposta(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> pegarDadosUsuario(@PathVariable("id") Integer id) {
        Optional<Usuario> usuarioOpt = usuarioService.pegarDadosUsuarioPorId(id);
        if (usuarioOpt.isPresent()) {
            Usuario u = usuarioOpt.get();
            UsuarioDTO dto = new UsuarioDTO(u.getId(), u.getCpf(), u.getNome(), u.getDataAniversario(), u.getCelular(), u.getEmail(), u.getSenha());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirUsuario(@PathVariable("id") Integer id) {
        Optional<Usuario> usuarioOpt = usuarioService.pegarDadosUsuarioPorId(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.excluirUsuarioPorId(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarUsuario(@PathVariable("id") Integer id, @RequestBody @Valid UsuarioDTO usuarioDTO) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.pegarDadosUsuarioPorId(id);
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Usuario u = usuarioOpt.get();
            u.setCpf(usuarioDTO.cpf());
            u.setNome(usuarioDTO.nome());
            u.setDataAniversario(usuarioDTO.dataAniversario());
            u.setCelular(usuarioDTO.celular());
            u.setEmail(usuarioDTO.email());
            u.setSenha(usuarioDTO.senha());

            usuarioService.atualizarUsuario(u);
            return ResponseEntity.ok().build();
        } catch (RegistroDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroResposta(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> pesquisarPorEmailEAniversario(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "dataAniversario", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAniversario) {

        List<Usuario> resultado = usuarioService.pesquisarPorEmailEDataAniversario(email, dataAniversario);
        List<UsuarioDTO> lista = resultado.stream()
                .map(u -> new UsuarioDTO(u.getId(), u.getCpf(), u.getNome(), u.getDataAniversario(), u.getCelular(), u.getEmail(), u.getSenha()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }
}
