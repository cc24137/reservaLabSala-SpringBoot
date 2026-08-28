package cookiebecoInc.com.example.ReservaLabSala.service;

import cookiebecoInc.com.example.ReservaLabSala.model.Laboratorio;
import cookiebecoInc.com.example.ReservaLabSala.repository.LaboratorioRepository;
import cookiebecoInc.com.example.ReservaLabSala.validator.LaboratorioValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;
    private final LaboratorioValidator laboratorioValidator;

    public LaboratorioService(
            LaboratorioRepository laboratorioRepository,
            LaboratorioValidator laboratorioValidator) {
        this.laboratorioRepository = laboratorioRepository;
        this.laboratorioValidator = laboratorioValidator;
    }

    public Laboratorio inserirLaboratorio(Laboratorio laboratorio) {
        laboratorioValidator.validar(laboratorio);
        return laboratorioRepository.save(laboratorio);
    }

    public Optional<Laboratorio> pegarDadosLaboratorioPorId(Integer id) {
        return laboratorioRepository.findById(id);
    }

    public void excluirLaboratorioPorId(Integer id) {
        laboratorioRepository.deleteById(id);
    }

    public Laboratorio atualizarLaboratorio(Laboratorio laboratorio) {
        if (laboratorio.getId() == null) {
            throw new IllegalArgumentException("Não existe LABORATÓRIO com o ID informado.");
        }
        laboratorioValidator.validar(laboratorio);
        return laboratorioRepository.save(laboratorio);
    }

    public List<Laboratorio> pesquisarPorNomeCapacidadeLocalizacao(
            String nome, Integer capacidade, String localizacao) {

        if (nome != null && capacidade != null && localizacao != null) {
            return laboratorioRepository.findByNomeAndCapacidadeAndLocalizacao(nome, capacidade, localizacao);
        }
        if (nome != null) {
            return laboratorioRepository.findByNome(nome);
        }
        if (capacidade != null) {
            return laboratorioRepository.findByCapacidade(capacidade);
        }
        if (localizacao != null) {
            return laboratorioRepository.findByLocalizacao(localizacao);
        }
        return laboratorioRepository.findAll();
    }
}
