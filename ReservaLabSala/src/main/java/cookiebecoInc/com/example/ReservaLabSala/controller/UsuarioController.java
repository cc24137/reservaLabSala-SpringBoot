package cookiebecoInc.com.example.ReservaLabSala.controller;

import cookiebecoInc.com.example.ReservaLabSala.controller.dto.LoginDTO;
import cookiebecoInc.com.example.ReservaLabSala.controller.dto.UsuarioDTO;
import cookiebecoInc.com.example.ReservaLabSala.model.Usuario;
import cookiebecoInc.com.example.ReservaLabSala.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@RequestBody @Valid UsuarioDTO dto) {
        Usuario usuario = usuarioService.salvar(dto.mapearDadosParaEntidadeUsuario());
        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDTO(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid UsuarioDTO dto) {
        Usuario usuario = dto.mapearDadosParaEntidadeUsuario();
        usuario.setId(id);
        Usuario atualizado = usuarioService.salvar(usuario);
        return ResponseEntity.ok(converterParaDTO(atualizado));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> consultar(
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAniversario) {

        List<UsuarioDTO> resultado = usuarioService.pesquisarPorFiltros(cpf, nome, email, dataAniversario)
                .stream()
                .map(this::converterParaDTO)
                .toList();

        return ResponseEntity.ok(resultado);
    }

    private UsuarioDTO converterParaDTO(Usuario u) {
        return new UsuarioDTO(u.getId(), u.getCpf(), u.getNome(), u.getDataAniversario(), u.getCelular(), u.getEmail(), u.getSenha());
    }

    @PostMapping("/autenticar")
    public ResponseEntity<Usuario> autenticar(@RequestBody LoginDTO loginDTO) {
        Usuario usuario = usuarioService.autenticar(loginDTO.email(), loginDTO.senha());
        return ResponseEntity.ok(usuario);
    }
}
