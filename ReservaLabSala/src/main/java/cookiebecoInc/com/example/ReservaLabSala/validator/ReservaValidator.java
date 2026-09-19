package cookiebecoInc.com.example.ReservaLabSala.validator;

import cookiebecoInc.com.example.ReservaLabSala.exceptions.OperacaoNaoPermitidaException;
import cookiebecoInc.com.example.ReservaLabSala.model.Laboratorio;
import cookiebecoInc.com.example.ReservaLabSala.model.Reserva;
import cookiebecoInc.com.example.ReservaLabSala.model.Sala;
import cookiebecoInc.com.example.ReservaLabSala.repository.LaboratorioRepository;
import cookiebecoInc.com.example.ReservaLabSala.repository.ReservaRepository;
import cookiebecoInc.com.example.ReservaLabSala.repository.SalaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReservaValidator {

    private final LaboratorioRepository laboratorioRepository;
    private final SalaRepository salaRepository;
    private final ReservaRepository reservaRepository;

    public ReservaValidator(LaboratorioRepository laboratorioRepository,
                            SalaRepository salaRepository,
                            ReservaRepository reservaRepository) {
        this.laboratorioRepository = laboratorioRepository;
        this.salaRepository = salaRepository;
        this.reservaRepository = reservaRepository;
    }

    public void validar(Reserva reserva) {
        validarUnicidadeRecurso(reserva);

        if (!isReservaDiaria(reserva)) {
            throw new OperacaoNaoPermitidaException("A reserva deve ser iniciada e finalizada no mesmo dia");
        }

        if (!isHorarioValido(reserva)) {
            throw new OperacaoNaoPermitidaException("A hora final precisa ser posterior à hora inicial");
        }

        if (isRecursoBloqueado(reserva)) {
            throw new OperacaoNaoPermitidaException("Recurso bloqueado para manutenção ou indisponível");
        }

        validarConflitoHorarios(reserva);
    }

    public void validarCancelamento(Reserva reserva) {
        LocalDateTime inicioReserva = LocalDateTime.of(reserva.getDataInicio(), reserva.getHoraInicio());
        LocalDateTime agora = LocalDateTime.now();

        if (agora.isAfter(inicioReserva.minusHours(24))) {
            throw new OperacaoNaoPermitidaException("O cancelamento só pode ocorrer com no mínimo 24 horas de antecedência");
        }
    }

    private void validarUnicidadeRecurso(Reserva reserva) {
        boolean temLab = reserva.getLaboratorio() != null && reserva.getLaboratorio().getId() != null;
        boolean temSala = reserva.getSala() != null && reserva.getSala().getId() != null;

        if (!temLab && !temSala) {
            throw new OperacaoNaoPermitidaException("É necessário informar uma Sala ou um Laboratório para a reserva");
        }

        if (temLab && temSala) {
            throw new OperacaoNaoPermitidaException("A reserva deve ser associada a apenas um recurso (Sala ou Laboratório)");
        }
    }

    private boolean isReservaDiaria(Reserva reserva) {
        return reserva.getDataInicio() != null && reserva.getDataInicio().equals(reserva.getDataFim());
    }

    private boolean isHorarioValido(Reserva reserva) {
        return reserva.getHoraInicio() != null && reserva.getHoraFim() != null &&
                reserva.getHoraFim().isAfter(reserva.getHoraInicio());
    }

    private boolean isRecursoBloqueado(Reserva reserva) {
        if (reserva.getLaboratorio() != null && reserva.getLaboratorio().getId() != null) {
            Laboratorio lab = laboratorioRepository.findById(reserva.getLaboratorio().getId())
                    .orElseThrow(() -> new OperacaoNaoPermitidaException("Laboratório não encontrado"));
            String status = lab.getStatusRecurso() != null ? lab.getStatusRecurso().getNome() : "";
            return "Bloqueado".equalsIgnoreCase(status) || "Inativo".equalsIgnoreCase(status);
        }

        if (reserva.getSala() != null && reserva.getSala().getId() != null) {
            Sala sala = salaRepository.findById(reserva.getSala().getId())
                    .orElseThrow(() -> new OperacaoNaoPermitidaException("Sala não encontrada"));
            String status = sala.getStatusRecurso() != null ? sala.getStatusRecurso().getNome() : "";
            return "Bloqueado".equalsIgnoreCase(status) || "Inativo".equalsIgnoreCase(status);
        }

        return false;
    }

    private void validarConflitoHorarios(Reserva reserva) {
        if (reserva.getLaboratorio() != null && reserva.getLaboratorio().getId() != null) {
            List<Reserva> conflitos = reservaRepository.buscarConflitosLaboratorio(
                    reserva.getLaboratorio().getId(),
                    reserva.getDataInicio(),
                    reserva.getDataFim(),
                    reserva.getHoraInicio(),
                    reserva.getHoraFim(),
                    reserva.getId()
            );
            if (!conflitos.isEmpty()) {
                throw new OperacaoNaoPermitidaException("Já existe uma reserva para este Laboratório no horário selecionado");
            }
        }

        if (reserva.getSala() != null && reserva.getSala().getId() != null) {
            List<Reserva> conflitos = reservaRepository.buscarConflitosSala(
                    reserva.getSala().getId(),
                    reserva.getDataInicio(),
                    reserva.getDataFim(),
                    reserva.getHoraInicio(),
                    reserva.getHoraFim(),
                    reserva.getId()
            );
            if (!conflitos.isEmpty()) {
                throw new OperacaoNaoPermitidaException("Já existe uma reserva para esta Sala no horário selecionado");
            }
        }
    }
}