package cookiebecoInc.com.example.ReservaLabSala.controller;

import cookiebecoInc.com.example.ReservaLabSala.controller.dto.LaboratorioDTO;
import cookiebecoInc.com.example.ReservaLabSala.model.Laboratorio;
import cookiebecoInc.com.example.ReservaLabSala.service.LaboratorioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laboratorios")
public class LaboratorioController {

    private final LaboratorioService service;

    public LaboratorioController(LaboratorioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LaboratorioDTO> criar(@RequestBody @Valid LaboratorioDTO dto) {
        Laboratorio salvo = service.salvar(dto.mapearDadosParaEntidadeLaboratorio());
        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaboratorioDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid LaboratorioDTO dto) {
        Laboratorio lab = dto.mapearDadosParaEntidadeLaboratorio();
        lab.setId(id);
        Laboratorio atualizado = service.salvar(lab);
        return ResponseEntity.ok(converterParaDTO(atualizado));
    }

    @GetMapping
    public ResponseEntity<List<LaboratorioDTO>> consultar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer capacidade,
            @RequestParam(required = false) String localizacao,
            @RequestParam(required = false) Integer statusId) {

        List<LaboratorioDTO> lista = service.pesquisarPorFiltros(nome, capacidade, localizacao, statusId)
                .stream()
                .map(this::converterParaDTO)
                .toList();

        return ResponseEntity.ok(lista);
    }

    private LaboratorioDTO converterParaDTO(Laboratorio l) {
        Integer stId = l.getStatusRecurso() != null ? l.getStatusRecurso().getId() : null;
        return new LaboratorioDTO(l.getId(), l.getNome(), l.getCapacidade(), l.getLocalizacao(), stId);
    }
}
