package cookiebecoInc.com.example.ReservaLabSala.controller;

import cookiebecoInc.com.example.ReservaLabSala.controller.dto.ErroResposta;
import cookiebecoInc.com.example.ReservaLabSala.controller.dto.ReservaDTO;
import cookiebecoInc.com.example.ReservaLabSala.exceptions.OperacaoNaoPermitidaException;
import cookiebecoInc.com.example.ReservaLabSala.exceptions.RegistroDuplicadoException;
import cookiebecoInc.com.example.ReservaLabSala.model.*;
import cookiebecoInc.com.example.ReservaLabSala.repository.*;
import cookiebecoInc.com.example.ReservaLabSala.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final UsuarioRepository usuarioRepository;
    private final StatusRepository statusRepository;
    private final LaboratorioRepository laboratorioRepository;
    private final SalaRepository salaRepository;

    public ReservaController(
            ReservaService reservaService,
            UsuarioRepository usuarioRepository,
            StatusRepository statusRepository,
            LaboratorioRepository laboratorioRepository,
            SalaRepository salaRepository) {
        this.reservaService = reservaService;
        this.usuarioRepository = usuarioRepository;
        this.statusRepository = statusRepository;
        this.laboratorioRepository = laboratorioRepository;
        this.salaRepository = salaRepository;
    }

    @PostMapping
    public ResponseEntity<Object> incluirReserva(@RequestBody @Valid ReservaDTO reservaDTO) {
        try {
            Usuario usuario = usuarioRepository.findById(reservaDTO.usuarioId()).orElse(null);
            Status status = statusRepository.findById(reservaDTO.statusId()).orElse(null);

            Laboratorio lab = reservaDTO.laboratorioId() != null ?
                    laboratorioRepository.findById(reservaDTO.laboratorioId()).orElse(null) : null;
            Sala sala = reservaDTO.salaId() != null ?
                    salaRepository.findById(reservaDTO.salaId()).orElse(null) : null;

            if (usuario == null || status == null) {
                return ResponseEntity.badRequest().body(new ErroResposta(400, "Usuário ou Status inválidos/não encontrados."));
            }

            Reserva reserva = reservaDTO.mapearDadosParaEntidadeReserva(usuario, status, lab, sala);
            reservaService.inserirReserva(reserva);

            return new ResponseEntity<>("Reserva cadastrada com sucesso!", HttpStatus.CREATED);
        } catch (RegistroDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroResposta(400, e.getMessage()));
        } catch (OperacaoNaoPermitidaException e) {
            return ResponseEntity.badRequest().body(new ErroResposta(400, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDTO> pegarReservaPorId(@PathVariable("id") Integer id) {
        Optional<Reserva> reservaOpt = reservaService.pegarDadosReservaPorId(id);
        if (reservaOpt.isPresent()) {
            Reserva r = reservaOpt.get();
            ReservaDTO dto = new ReservaDTO(
                    r.getId(),
                    r.getDataInicio(),
                    r.getDataFim(),
                    r.getHoraInicio(),
                    r.getHoraFim(),
                    r.getUsuario().getId(),
                    r.getStatus().getId(),
                    r.getLaboratorio() != null ? r.getLaboratorio().getId() : null,
                    r.getSala() != null ? r.getSala().getId() : null
            );
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirReserva(@PathVariable("id") Integer id) {
        Optional<Reserva> reservaOpt = reservaService.pegarDadosReservaPorId(id);
        if (reservaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        reservaService.excluirReservaPorId(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ReservaDTO>> listarTodasReservas() {
        List<Reserva> resultado = reservaService.listarTodas();
        List<ReservaDTO> lista = resultado.stream()
                .map(r -> new ReservaDTO(
                        r.getId(),
                        r.getDataInicio(),
                        r.getDataFim(),
                        r.getHoraInicio(),
                        r.getHoraFim(),
                        r.getUsuario().getId(),
                        r.getStatus().getId(),
                        r.getLaboratorio() != null ? r.getLaboratorio().getId() : null,
                        r.getSala() != null ? r.getSala().getId() : null
                )).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/filtros")
    public ResponseEntity<List<ReservaDTO>> pesquisarReservas(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "usuarioId", required = false) Integer usuarioId,
            @RequestParam(value = "laboratorioId", required = false) Integer laboratorioId,
            @RequestParam(value = "salaId", required = false) Integer salaId,
            @RequestParam(value = "dataInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(value = "horaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime horaInicio) {

        List<Reserva> resultado = reservaService.pesquisarPorFiltros(
                usuarioId, status, laboratorioId, salaId, dataInicio, horaInicio);

        List<ReservaDTO> lista = resultado.stream()
                .map(r -> new ReservaDTO(
                        r.getId(),
                        r.getDataInicio(),
                        r.getDataFim(),
                        r.getHoraInicio(),
                        r.getHoraFim(),
                        r.getUsuario().getId(),
                        r.getStatus().getId(),
                        r.getLaboratorio() != null ? r.getLaboratorio().getId() : null,
                        r.getSala() != null ? r.getSala().getId() : null
                )).collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }
}
