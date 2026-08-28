package cookiebecoInc.com.example.ReservaLabSala.validator;

import cookiebecoInc.com.example.ReservaLabSala.exceptions.RegistroDuplicadoException;
import cookiebecoInc.com.example.ReservaLabSala.model.Reserva;
import cookiebecoInc.com.example.ReservaLabSala.repository.ReservaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservaValidator {

    private final ReservaRepository reservaRepository;

    public ReservaValidator(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public void validar(Reserva reserva) {
        if (existeReservaConflitante(reserva)) {
            throw new RegistroDuplicadoException("Já existe uma reserva cadastrada para essa mesma data e horário");
        }
    }

    private boolean existeReservaConflitante(Reserva reserva) {
        List<Reserva> reservasEncontradas = reservaRepository.findByDataInicioAndHoraInicio(
                reserva.getDataInicio(),
                reserva.getHoraInicio()
        );

        if (reservasEncontradas.isEmpty()) {
            return false;
        }

        Reserva reservaEncontrada = reservasEncontradas.get(0);

        if (reserva.getId() == null) {
            return true;
        }

        return !reserva.getId().equals(reservaEncontrada.getId());
    }
}

