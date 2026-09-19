package cookiebecoInc.com.example.ReservaLabSala.service;

import cookiebecoInc.com.example.ReservaLabSala.model.Reserva;
import cookiebecoInc.com.example.ReservaLabSala.model.StatusReserva;
import cookiebecoInc.com.example.ReservaLabSala.repository.ReservaRepository;
import cookiebecoInc.com.example.ReservaLabSala.repository.StatusReservaRepository;
import cookiebecoInc.com.example.ReservaLabSala.validator.ReservaValidator;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final StatusReservaRepository statusReservaRepository;
    private final ReservaValidator reservaValidator;

    public ReservaService(ReservaRepository reservaRepository,
                          StatusReservaRepository statusReservaRepository,
                          ReservaValidator reservaValidator) {
        this.reservaRepository = reservaRepository;
        this.statusReservaRepository = statusReservaRepository;
        this.reservaValidator = reservaValidator;
    }

    public Reserva criarReserva(Reserva reserva) {
        reservaValidator.validar(reserva);
        return reservaRepository.save(reserva);
    }

    public void cancelarReserva(Integer reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Reserva não encontrada"));

        reservaValidator.validarCancelamento(reserva);

        StatusReserva statusCancelado = statusReservaRepository.findByNome("CANCELADA")
                .orElseGet(() -> statusReservaRepository.findByNome("Cancelada")
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Status Cancelada não encontrado")));

        reserva.setStatusReserva(statusCancelado);
        reservaRepository.save(reserva);
    }

    @Scheduled(fixedRate = 60000)
    public void concluirReservasExpiradas() {
        StatusReserva statusConcluida = statusReservaRepository.findByNome("CONCLUÍDA")
                .orElseGet(() -> statusReservaRepository.findByNome("Concluída").orElse(null));

        if (statusConcluida == null) return;

        LocalDateTime agora = LocalDateTime.now();

        List<Reserva> reservasAtivas = reservaRepository.findAll().stream()
                .filter(r -> r.getStatusReserva() != null &&
                        ("ATIVA".equalsIgnoreCase(r.getStatusReserva().getNome()) ||
                                "Ativa".equalsIgnoreCase(r.getStatusReserva().getNome())))
                .toList();

        for (Reserva r : reservasAtivas) {
            LocalDateTime fimReserva = LocalDateTime.of(r.getDataFim(), r.getHoraFim());
            if (agora.isAfter(fimReserva)) {
                r.setStatusReserva(statusConcluida);
                reservaRepository.save(r);
            }
        }
    }

    public List<Reserva> pesquisarPorFiltros(Integer statusId, Integer usuarioId, Integer laboratorioId,
                                             Integer salaId, String recursoNome, LocalDate dataInicio,
                                             LocalDate dataFim, LocalTime horaInicio) {
        return reservaRepository.pesquisarComFiltros(statusId, usuarioId, laboratorioId, salaId, recursoNome, dataInicio, dataFim, horaInicio);
    }
}