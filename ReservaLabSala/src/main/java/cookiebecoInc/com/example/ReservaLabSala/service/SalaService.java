package cookiebecoInc.com.example.ReservaLabSala.service;

import cookiebecoInc.com.example.ReservaLabSala.model.Sala;
import cookiebecoInc.com.example.ReservaLabSala.repository.SalaRepository;
import cookiebecoInc.com.example.ReservaLabSala.validator.SalaValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final SalaValidator salaValidator;

    public SalaService(
            SalaRepository salaRepository,
            SalaValidator salaValidator) {
        this.salaRepository = salaRepository;
        this.salaValidator = salaValidator;
    }

    public Sala inserirSala(Sala sala) {
        salaValidator.validar(sala);
        return salaRepository.save(sala);
    }

    public Optional<Sala> pegarDadosSalaPorId(Integer id) {
        return salaRepository.findById(id);
    }

    public void excluirSalaPorId(Integer id) {
        salaRepository.deleteById(id);
    }

    public Sala atualizarSala(Sala sala) {
        if (sala.getId() == null) {
            throw new IllegalArgumentException("Não existe SALA com o ID informado.");
        }
        salaValidator.validar(sala);
        return salaRepository.save(sala);
    }

    public List<Sala> pesquisarPorNomeCapacidadeLocalizacao(
            String nome, Integer capacidade, String localizacao) {

        if (nome != null && capacidade != null && localizacao != null) {
            return salaRepository.findByNomeAndCapacidadeAndLocalizacao(nome, capacidade, localizacao);
        }
        if (nome != null) {
            return salaRepository.findByNome(nome);
        }
        if (capacidade != null) {
            return salaRepository.findByCapacidade(capacidade);
        }
        if (localizacao != null) {
            return salaRepository.findByLocalizacao(localizacao);
        }
        return salaRepository.findAll();
    }
}
