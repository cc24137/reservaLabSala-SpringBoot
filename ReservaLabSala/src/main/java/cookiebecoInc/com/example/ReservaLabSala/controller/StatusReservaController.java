package cookiebecoInc.com.example.ReservaLabSala.controller;

import cookiebecoInc.com.example.ReservaLabSala.controller.dto.StatusReservaDTO;
import cookiebecoInc.com.example.ReservaLabSala.model.StatusReserva;
import cookiebecoInc.com.example.ReservaLabSala.service.StatusReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status-reserva")
public class StatusReservaController {

    private final StatusReservaService service;

    public StatusReservaController(StatusReservaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<StatusReservaDTO> criar(@RequestBody @Valid StatusReservaDTO dto) {
        StatusReserva salvo = service.salvar(dto.mapearDadosParaEntidadeStatusReserva());
        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusReservaDTO(salvo.getId(), salvo.getNome()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusReservaDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid StatusReservaDTO dto) {
        StatusReserva status = dto.mapearDadosParaEntidadeStatusReserva();
        status.setId(id);
        StatusReserva atualizado = service.salvar(status);
        return ResponseEntity.ok(new StatusReservaDTO(atualizado.getId(), atualizado.getNome()));
    }

    @GetMapping
    public ResponseEntity<List<StatusReservaDTO>> listar() {
        List<StatusReservaDTO> lista = service.listarTodos()
                .stream()
                .map(s -> new StatusReservaDTO(s.getId(), s.getNome()))
                .toList();
        return ResponseEntity.ok(lista);
    }
}
