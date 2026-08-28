package cookiebecoInc.com.example.ReservaLabSala.service;

import cookiebecoInc.com.example.ReservaLabSala.exceptions.OperacaoNaoPermitidaException;
import cookiebecoInc.com.example.ReservaLabSala.model.Reserva;
import cookiebecoInc.com.example.ReservaLabSala.repository.ReservaRepository;
import cookiebecoInc.com.example.ReservaLabSala.validator.ReservaValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ReservaValidator reservaValidator;

    public ReservaService(
            ReservaRepository reservaRepository,
            ReservaValidator reservaValidator) {
        this.reservaRepository = reservaRepository;
        this.reservaValidator = reservaValidator;
    }

    public Reserva inserirReserva(Reserva reserva) {
        reservaValidator.validar(reserva);

        if (reserva.getStatus() != null && "Bloqueado".equalsIgnoreCase(reserva.getStatus().getNome())) {
            throw new OperacaoNaoPermitidaException("Não é possível realizar reserva para um recurso com status Bloqueado.");
        }

        return reservaRepository.save(reserva);
    }

    public Optional<Reserva> pegarDadosReservaPorId(Integer id) {
        return reservaRepository.findById(id);
    }

    public void excluirReservaPorId(Integer id) {
        reservaRepository.deleteById(id);
    }

    public Reserva atualizarReserva(Reserva reserva) {
        if (reserva.getId() == null) {
            throw new IllegalArgumentException("Não existe RESERVA com o ID informado.");
        }
        reservaValidator.validar(reserva);
        return reservaRepository.save(reserva);
    }

    public List<Reserva> listarTodas() {
        return reservaRepository.findAll();
    }
}
