package cookiebecoInc.com.example.ReservaLabSala.service;

import cookiebecoInc.com.example.ReservaLabSala.model.Sala;
import cookiebecoInc.com.example.ReservaLabSala.repository.SalaRepository;
import cookiebecoInc.com.example.ReservaLabSala.repository.StatusRecursoRepository;
import cookiebecoInc.com.example.ReservaLabSala.validator.SalaValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final StatusRecursoRepository statusRecursoRepository;
    private final SalaValidator salaValidator;

    public SalaService(SalaRepository salaRepository,
                       StatusRecursoRepository statusRecursoRepository,
                       SalaValidator salaValidator) {
        this.salaRepository = salaRepository;
        this.statusRecursoRepository = statusRecursoRepository;
        this.salaValidator = salaValidator;
    }

    public Sala salvar(Sala sala) {
        salaValidator.validar(sala);

        if (sala.getStatusRecurso() != null && sala.getStatusRecurso().getId() != null) {
            var status = statusRecursoRepository.findById(sala.getStatusRecurso().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Status de recurso não encontrado"));
            sala.setStatusRecurso(status);
        }
        return salaRepository.save(sala);
    }

    public List<Sala> pesquisarPorFiltros(String nome, Integer capacidade, String localizacao, Integer statusRecursoId) {
        return salaRepository.pesquisarComFiltros(nome, capacidade, localizacao, statusRecursoId);
    }
}