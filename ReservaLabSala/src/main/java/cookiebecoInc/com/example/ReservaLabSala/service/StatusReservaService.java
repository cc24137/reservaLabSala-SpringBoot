package cookiebecoInc.com.example.ReservaLabSala.service;

import cookiebecoInc.com.example.ReservaLabSala.model.StatusReserva;
import cookiebecoInc.com.example.ReservaLabSala.repository.StatusReservaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StatusReservaService {

    private final StatusReservaRepository repository;

    public StatusReservaService(StatusReservaRepository repository) {
        this.repository = repository;
    }

    public StatusReserva salvar(StatusReserva status) {
        return repository.save(status);
    }

    public List<StatusReserva> listarTodos() {
        return repository.findAll();
    }

    public StatusReserva buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Status de reserva não encontrado"));
    }
}
