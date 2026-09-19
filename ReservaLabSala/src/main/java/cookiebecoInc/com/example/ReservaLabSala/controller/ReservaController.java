package cookiebecoInc.com.example.ReservaLabSala.controller;

import cookiebecoInc.com.example.ReservaLabSala.controller.dto.ReservaDTO;
import cookiebecoInc.com.example.ReservaLabSala.model.Reserva;
import cookiebecoInc.com.example.ReservaLabSala.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaDTO> criar(@RequestBody @Valid ReservaDTO dto) {
        Reserva criada = reservaService.criarReserva(dto.mapearDadosParaEntidadeReserva());
        return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDTO(criada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid ReservaDTO dto) {
        Reserva reserva = dto.mapearDadosParaEntidadeReserva();
        reserva.setId(id);
        Reserva atualizada = reservaService.salvar(reserva);
        return ResponseEntity.ok(converterParaDTO(atualizada));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Integer id) {
        reservaService.cancelarReserva(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> pesquisarReservas(
            @RequestParam(required = false) Integer statusId,
            @RequestParam(required = false) Integer usuarioId,
            @RequestParam(required = false) Integer laboratorioId,
            @RequestParam(required = false) Integer salaId,
            @RequestParam(required = false) String recursoNome,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaInicio) {

        List<ReservaDTO> resultado = reservaService.pesquisarPorFiltros(
                        statusId, usuarioId, laboratorioId, salaId, recursoNome, dataInicio, dataFim, horaInicio)
                .stream()
                .map(this::converterParaDTO)
                .toList();

        return ResponseEntity.ok(resultado);
    }

    private ReservaDTO converterParaDTO(Reserva r) {
        Integer uId = r.getUsuario() != null ? r.getUsuario().getId() : null;
        Integer stId = r.getStatusReserva() != null ? r.getStatusReserva().getId() : null;
        Integer labId = r.getLaboratorio() != null ? r.getLaboratorio().getId() : null;
        Integer sId = r.getSala() != null ? r.getSala().getId() : null;

        return new ReservaDTO(
                r.getId(),
                r.getDataInicio(),
                r.getDataFim(),
                r.getHoraInicio(),
                r.getHoraFim(),
                uId,
                stId,
                labId,
                sId
        );
    }
}