package cookiebecoInc.com.example.ReservaLabSala.controller;

import cookiebecoInc.com.example.ReservaLabSala.controller.dto.ErroResposta;
import cookiebecoInc.com.example.ReservaLabSala.controller.dto.SalaDTO;
import cookiebecoInc.com.example.ReservaLabSala.exceptions.RegistroDuplicadoException;
import cookiebecoInc.com.example.ReservaLabSala.model.Sala;
import cookiebecoInc.com.example.ReservaLabSala.service.SalaService;
import cookiebecoInc.com.example.ReservaLabSala.validator.SalaValidator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaService salaService;
    private final SalaValidator salaValidator;

    public SalaController(SalaService salaService, SalaValidator salaValidator) {
        this.salaService = salaService;
        this.salaValidator = salaValidator;
    }

    @PostMapping
    public ResponseEntity<Object> incluirSala(@RequestBody @Valid SalaDTO salaDTO) {
        try {
            Sala sala = salaDTO.mapearDadosParaEntidadeSala();
            salaService.inserirSala(sala);
            return new ResponseEntity<>("Sala inserida com sucesso! " + salaDTO, HttpStatus.CREATED);
        } catch (RegistroDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroResposta(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> pegarDadosSala(@PathVariable("id") Integer id) {
        Optional<Sala> salaOpt = salaService.pegarDadosSalaPorId(id);
        if (salaOpt.isPresent()) {
            Sala s = salaOpt.get();
            return ResponseEntity.ok(new SalaDTO(s.getId(), s.getNome(), s.getCapacidade(), s.getLocalizacao()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirSala(@PathVariable("id") Integer id) {
        Optional<Sala> salaOpt = salaService.pegarDadosSalaPorId(id);
        if (salaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        salaService.excluirSalaPorId(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarSala(@PathVariable("id") Integer id, @RequestBody @Valid SalaDTO salaDTO) {
        try {
            Optional<Sala> salaOpt = salaService.pegarDadosSalaPorId(id);
            if (salaOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Sala s = salaOpt.get();
            s.setNome(salaDTO.nome());
            s.setCapacidade(salaDTO.capacidade());
            s.setLocalizacao(salaDTO.localizacao());
            salaService.atualizarSala(s);
            return ResponseEntity.ok().build();
        } catch (RegistroDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroResposta(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<SalaDTO>> pesquisarSalas(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "capacidade", required = false) Integer capacidade,
            @RequestParam(value = "localizacao", required = false) String localizacao) {

        List<Sala> resultado = salaService.pesquisarPorNomeCapacidadeLocalizacao(nome, capacidade, localizacao);
        List<SalaDTO> lista = resultado.stream()
                .map(s -> new SalaDTO(s.getId(), s.getNome(), s.getCapacidade(), s.getLocalizacao()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }
}
