package cookiebecoInc.com.example.ReservaLabSala.service;

import cookiebecoInc.com.example.ReservaLabSala.model.Laboratorio;
import cookiebecoInc.com.example.ReservaLabSala.repository.LaboratorioRepository;
import cookiebecoInc.com.example.ReservaLabSala.repository.StatusRecursoRepository;
import cookiebecoInc.com.example.ReservaLabSala.validator.LaboratorioValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;
    private final StatusRecursoRepository statusRecursoRepository;
    private final LaboratorioValidator laboratorioValidator;

    public LaboratorioService(LaboratorioRepository laboratorioRepository,
                              StatusRecursoRepository statusRecursoRepository,
                              LaboratorioValidator laboratorioValidator) {
        this.laboratorioRepository = laboratorioRepository;
        this.statusRecursoRepository = statusRecursoRepository;
        this.laboratorioValidator = laboratorioValidator;
    }

    public Laboratorio salvar(Laboratorio laboratorio) {
        // Validação de duplicidade antes de salvar
        laboratorioValidator.validar(laboratorio);

        if (laboratorio.getStatusRecurso() != null && laboratorio.getStatusRecurso().getId() != null) {
            var status = statusRecursoRepository.findById(laboratorio.getStatusRecurso().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Status de recurso não encontrado"));
            laboratorio.setStatusRecurso(status);
        }
        return laboratorioRepository.save(laboratorio);
    }

    public List<Laboratorio> pesquisarPorFiltros(String nome, Integer capacidade, String localizacao, Integer statusRecursoId) {
        if (nome != null && !nome.isBlank()) {
            return laboratorioRepository.findByNome(nome);
        }
        if (capacidade != null) {
            return laboratorioRepository.findByCapacidade(capacidade);
        }
        if (localizacao != null && !localizacao.isBlank()) {
            return laboratorioRepository.findByLocalizacao(localizacao);
        }
        if (statusRecursoId != null) {
            return laboratorioRepository.findByStatusRecursoId(statusRecursoId);
        }
        return laboratorioRepository.findAll();
    }
}
