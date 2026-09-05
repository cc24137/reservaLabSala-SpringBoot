package cookiebecoInc.com.example.ReservaLabSala.validator;

import cookiebecoInc.com.example.ReservaLabSala.model.Laboratorio;
import cookiebecoInc.com.example.ReservaLabSala.model.Reserva;
import cookiebecoInc.com.example.ReservaLabSala.model.Sala;
import cookiebecoInc.com.example.ReservaLabSala.repository.LaboratorioRepository;
import cookiebecoInc.com.example.ReservaLabSala.repository.SalaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ReservaValidator {

    private final LaboratorioRepository laboratorioRepository;
    private final SalaRepository salaRepository;

    public ReservaValidator(LaboratorioRepository laboratorioRepository, SalaRepository salaRepository) {
        this.laboratorioRepository = laboratorioRepository;
        this.salaRepository = salaRepository;
    }

    public void validar(Reserva reserva) {
        if (!isReservaDiaria(reserva)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "A reserva é diária/por dia");
        }

        if (!isHorarioValido(reserva)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Hora Final precisa ser maior ou igual à Hora Inicial");
        }

        if (isRecursoBloqueado(reserva)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Recurso bloqueado para manutenção");
        }
    }

    public void validarCancelamento(Reserva reserva) {
        LocalDateTime inicioReserva = LocalDateTime.of(reserva.getDataInicio(), reserva.getHoraInicio());
        LocalDateTime agora = LocalDateTime.now();

        if (Duration.between(agora, inicioReserva).toHours() < 24) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "O cancelamento só pode ocorrer até 24 horas antes do início");
        }
    }

    private boolean isReservaDiaria(Reserva reserva) {
        return reserva.getDataInicio().equals(reserva.getDataFim());
    }

    private boolean isHorarioValido(Reserva reserva) {
        return reserva.getHoraFim().isAfter(reserva.getHoraInicio());
    }

    private boolean isRecursoBloqueado(Reserva reserva) {
        if (reserva.getLaboratorio() != null && reserva.getLaboratorio().getId() != null) {
            Laboratorio lab = laboratorioRepository.findById(reserva.getLaboratorio().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Laboratório não encontrado"));
            return lab.getStatusRecurso() != null && "Bloqueado".equalsIgnoreCase(lab.getStatusRecurso().getNome());
        }

        if (reserva.getSala() != null && reserva.getSala().getId() != null) {
            Sala sala = salaRepository.findById(reserva.getSala().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Sala não encontrada"));
            return sala.getStatusRecurso() != null && "Bloqueado".equalsIgnoreCase(sala.getStatusRecurso().getNome());
        }

        return false;
    }
}
