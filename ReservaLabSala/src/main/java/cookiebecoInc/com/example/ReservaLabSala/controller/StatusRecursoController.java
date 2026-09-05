package cookiebecoInc.com.example.ReservaLabSala.controller;

import cookiebecoInc.com.example.ReservaLabSala.controller.dto.StatusRecursoDTO;
import cookiebecoInc.com.example.ReservaLabSala.model.StatusRecurso;
import cookiebecoInc.com.example.ReservaLabSala.service.StatusRecursoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status-recurso")
public class StatusRecursoController {

    private final StatusRecursoService service;

    public StatusRecursoController(StatusRecursoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<StatusRecursoDTO> criar(@RequestBody @Valid StatusRecursoDTO dto) {
        StatusRecurso salvo = service.salvar(dto.mapearDadosParaEntidadeStatusRecurso());
        return ResponseEntity.status(HttpStatus.CREATED).body(new StatusRecursoDTO(salvo.getId(), salvo.getNome()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusRecursoDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid StatusRecursoDTO dto) {
        StatusRecurso status = dto.mapearDadosParaEntidadeStatusRecurso();
        status.setId(id);
        StatusRecurso atualizado = service.salvar(status);
        return ResponseEntity.ok(new StatusRecursoDTO(atualizado.getId(), atualizado.getNome()));
    }

    @GetMapping
    public ResponseEntity<List<StatusRecursoDTO>> listar() {
        List<StatusRecursoDTO> lista = service.listarTodos()
                .stream()
                .map(s -> new StatusRecursoDTO(s.getId(), s.getNome()))
                .toList();
        return ResponseEntity.ok(lista);
    }
}
