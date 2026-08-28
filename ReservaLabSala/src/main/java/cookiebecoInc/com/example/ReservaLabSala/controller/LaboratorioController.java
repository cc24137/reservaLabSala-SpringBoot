package cookiebecoInc.com.example.ReservaLabSala.controller;

import cookiebecoInc.com.example.ReservaLabSala.controller.dto.ErroResposta;
import cookiebecoInc.com.example.ReservaLabSala.controller.dto.LaboratorioDTO;
import cookiebecoInc.com.example.ReservaLabSala.exceptions.RegistroDuplicadoException;
import cookiebecoInc.com.example.ReservaLabSala.model.Laboratorio;
import cookiebecoInc.com.example.ReservaLabSala.service.LaboratorioService;
import cookiebecoInc.com.example.ReservaLabSala.validator.LaboratorioValidator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/laboratorios")
public class LaboratorioController {

    private final LaboratorioService laboratorioService;
    private final LaboratorioValidator laboratorioValidator;

    public LaboratorioController(LaboratorioService laboratorioService, LaboratorioValidator laboratorioValidator) {
        this.laboratorioService = laboratorioService;
        this.laboratorioValidator = laboratorioValidator;
    }

    @PostMapping
    public ResponseEntity<Object> incluirLaboratorio(@RequestBody @Valid LaboratorioDTO laboratorioDTO) {
        try {
            Laboratorio lab = laboratorioDTO.mapearDadosParaEntidadeLaboratorio();
            laboratorioService.inserirLaboratorio(lab);
            return new ResponseEntity<>("Laboratório inserido com sucesso! " + laboratorioDTO, HttpStatus.CREATED);
        } catch (RegistroDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroResposta(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaboratorioDTO> pegarDadosLaboratorio(@PathVariable("id") Integer id) {
        Optional<Laboratorio> labOpt = laboratorioService.pegarDadosLaboratorioPorId(id);
        if (labOpt.isPresent()) {
            Laboratorio l = labOpt.get();
            return ResponseEntity.ok(new LaboratorioDTO(l.getId(), l.getNome(), l.getCapacidade(), l.getLocalizacao()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirLaboratorio(@PathVariable("id") Integer id) {
        Optional<Laboratorio> labOpt = laboratorioService.pegarDadosLaboratorioPorId(id);
        if (labOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        laboratorioService.excluirLaboratorioPorId(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarLaboratorio(@PathVariable("id") Integer id, @RequestBody @Valid LaboratorioDTO laboratorioDTO) {
        try {
            Optional<Laboratorio> labOpt = laboratorioService.pegarDadosLaboratorioPorId(id);
            if (labOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Laboratorio l = labOpt.get();
            l.setNome(laboratorioDTO.nome());
            l.setCapacidade(laboratorioDTO.capacidade());
            l.setLocalizacao(laboratorioDTO.localizacao());
            laboratorioService.atualizarLaboratorio(l);
            return ResponseEntity.ok().build();
        } catch (RegistroDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroResposta(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<LaboratorioDTO>> pesquisarLaboratorios(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "capacidade", required = false) Integer capacidade,
            @RequestParam(value = "localizacao", required = false) String localizacao) {

        List<Laboratorio> resultado = laboratorioService.pesquisarPorNomeCapacidadeLocalizacao(nome, capacidade, localizacao);
        List<LaboratorioDTO> lista = resultado.stream()
                .map(l -> new LaboratorioDTO(l.getId(), l.getNome(), l.getCapacidade(), l.getLocalizacao()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }
}
