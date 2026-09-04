package cookiebecoInc.com.example.ReservaLabSala.controller;

import cookiebecoInc.com.example.ReservaLabSala.controller.dto.ErroResposta;
import cookiebecoInc.com.example.ReservaLabSala.controller.dto.StatusDTO;
import cookiebecoInc.com.example.ReservaLabSala.exceptions.RegistroDuplicadoException;
import cookiebecoInc.com.example.ReservaLabSala.model.Status;
import cookiebecoInc.com.example.ReservaLabSala.repository.StatusRepository;
import cookiebecoInc.com.example.ReservaLabSala.validator.StatusValidator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/status")
public class StatusController {

    private final StatusRepository statusRepository;
    private final StatusValidator statusValidator;

    public StatusController(StatusRepository statusRepository, StatusValidator statusValidator) {
        this.statusRepository = statusRepository;
        this.statusValidator = statusValidator;
    }

    @PostMapping
    public ResponseEntity<Object> incluirStatus(@RequestBody @Valid StatusDTO statusDTO) {
        try {
            Status status = statusDTO.mapearDadosParaEntidadeStatus();
            statusValidator.validar(status);
            statusRepository.save(status);
            return new ResponseEntity<>("Status inserido com sucesso! ", HttpStatus.CREATED);
        } catch (RegistroDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroResposta(HttpStatus.CONFLICT.value(), e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusDTO> pegarStatusPorId(@PathVariable("id") Integer id) {
        Optional<Status> statusOpt = statusRepository.findById(id);
        if (statusOpt.isPresent()) {
            Status s = statusOpt.get();
            return ResponseEntity.ok(new StatusDTO(s.getId(), s.getNome()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<StatusDTO>> listarTodosStatus() {
        List<StatusDTO> lista = statusRepository.findAll().stream()
                .map(s -> new StatusDTO(s.getId(), s.getNome()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
}
