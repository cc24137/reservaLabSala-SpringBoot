package cookiebecoInc.com.example.ReservaLabSala.controller;

import cookiebecoInc.com.example.ReservaLabSala.controller.dto.SalaDTO;
import cookiebecoInc.com.example.ReservaLabSala.model.Sala;
import cookiebecoInc.com.example.ReservaLabSala.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaService service;

    public SalaController(SalaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SalaDTO> criar(@RequestBody @Valid SalaDTO dto) {
        Sala salva = service.salvar(dto.mapearDadosParaEntidadeSala());
        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDTO(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid SalaDTO dto) {
        Sala sala = dto.mapearDadosParaEntidadeSala();
        sala.setId(id);
        Sala atualizada = service.salvar(sala);
        return ResponseEntity.ok(converterParaDTO(atualizada));
    }

    @GetMapping
    public ResponseEntity<List<SalaDTO>> consultar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer capacidade,
            @RequestParam(required = false) String localizacao,
            @RequestParam(required = false) Integer statusId) {

        List<SalaDTO> lista = service.pesquisarPorFiltros(nome, capacidade, localizacao, statusId)
                .stream()
                .map(this::converterParaDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    private SalaDTO converterParaDTO(Sala s) {
        Integer stId = s.getStatusRecurso() != null ? s.getStatusRecurso().getId() : null;
        return new SalaDTO(s.getId(), s.getNome(), s.getCapacidade(), s.getLocalizacao(), stId);
    }
}
