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
        // Validação de duplicidade antes de salvar
        salaValidator.validar(sala);

        if (sala.getStatusRecurso() != null && sala.getStatusRecurso().getId() != null) {
            var status = statusRecursoRepository.findById(sala.getStatusRecurso().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Status de recurso não encontrado"));
            sala.setStatusRecurso(status);
        }
        return salaRepository.save(sala);
    }

    public List<Sala> pesquisarPorFiltros(String nome, Integer capacidade, String localizacao, Integer statusRecursoId) {
        if (nome != null && !nome.isBlank()) {
            return salaRepository.findByNome(nome);
        }
        if (capacidade != null) {
            return salaRepository.findByCapacidade(capacidade);
        }
        if (localizacao != null && !localizacao.isBlank()) {
            return salaRepository.findByLocalizacao(localizacao);
        }
        if (statusRecursoId != null) {
            return salaRepository.findByStatusRecursoId(statusRecursoId);
        }
        return salaRepository.findAll();
    }
}
