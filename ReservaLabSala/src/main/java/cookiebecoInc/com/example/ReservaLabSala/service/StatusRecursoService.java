package cookiebecoInc.com.example.ReservaLabSala.service;

import cookiebecoInc.com.example.ReservaLabSala.model.StatusRecurso;
import cookiebecoInc.com.example.ReservaLabSala.repository.StatusRecursoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StatusRecursoService {

    private final StatusRecursoRepository repository;

    public StatusRecursoService(StatusRecursoRepository repository) {
        this.repository = repository;
    }

    public StatusRecurso salvar(StatusRecurso status) {
        return repository.save(status);
    }

    public List<StatusRecurso> listarTodos() {
        return repository.findAll();
    }

    public StatusRecurso buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Status de recurso não encontrado"));
    }
}
