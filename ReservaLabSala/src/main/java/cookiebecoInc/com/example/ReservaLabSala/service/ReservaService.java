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

        // validação da janela de 24h delegada para o validator
        reservaValidator.validarCancelamento(reserva);

        StatusReserva statusCancelado = statusReservaRepository.findByNome("Cancelada")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Status Cancelada não encontrado"));

        reserva.setStatusReserva(statusCancelado);
        reservaRepository.save(reserva);
    }

    // Executa a cada 1 minuto para verificar reservas concluídas
    @Scheduled(fixedRate = 60000)
    public void concluirReservasExpiradas() {
        StatusReserva statusConcluida = statusReservaRepository.findByNome("Concluída").orElse(null);
        if (statusConcluida == null) return;

        LocalDateTime agora = LocalDateTime.now();

        List<Reserva> reservasAtivas = reservaRepository.findByStatusReservaNome("Ativa");
        for (Reserva r : reservasAtivas) {
            LocalDateTime fimReserva = LocalDateTime.of(r.getDataFim(), r.getHoraFim());
            if (agora.isAfter(fimReserva.plusMinutes(1))) {
                r.setStatusReserva(statusConcluida);
                reservaRepository.save(r);
            }
        }
    }

    public List<Reserva> pesquisarPorFiltros(String statusNome, Integer usuarioId, Integer laboratorioId, Integer salaId, LocalDate dataInicio, LocalTime horaInicio) {
        if (statusNome != null && !statusNome.isBlank()) {
            return reservaRepository.findByStatusReservaNome(statusNome);
        }
        if (usuarioId != null) {
            return reservaRepository.findByUsuarioId(usuarioId);
        }
        if (laboratorioId != null) {
            return reservaRepository.findByLaboratorioId(laboratorioId);
        }
        if (salaId != null) {
            return reservaRepository.findBySalaId(salaId);
        }
        if (dataInicio != null) {
            return reservaRepository.findByDataInicio(dataInicio);
        }
        if (horaInicio != null) {
            return reservaRepository.findByHoraInicio(horaInicio);
        }
        return reservaRepository.findAll();
    }
}