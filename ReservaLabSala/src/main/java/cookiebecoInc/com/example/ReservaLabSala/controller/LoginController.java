package cookiebecoInc.com.example.ReservaLabSala.controller;

import cookiebecoInc.com.example.ReservaLabSala.controller.dto.LoginDTO;
import cookiebecoInc.com.example.ReservaLabSala.controller.dto.UsuarioDTO;
import cookiebecoInc.com.example.ReservaLabSala.model.Usuario;
import cookiebecoInc.com.example.ReservaLabSala.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> autenticar(@RequestBody @Valid LoginDTO dto) {
        Usuario usuario = usuarioService.autenticar(dto.email(), dto.senha());
        UsuarioDTO resposta = new UsuarioDTO(
                usuario.getId(),
                usuario.getCpf(),
                usuario.getNome(),
                usuario.getDataAniversario(),
                usuario.getCelular(),
                usuario.getEmail(),
                usuario.getSenha()
        );
        return ResponseEntity.ok(resposta);
    }
}
