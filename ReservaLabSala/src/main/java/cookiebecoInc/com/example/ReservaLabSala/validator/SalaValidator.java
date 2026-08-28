package cookiebecoInc.com.example.ReservaLabSala.validator;

import cookiebecoInc.com.example.ReservaLabSala.exceptions.RegistroDuplicadoException;
import cookiebecoInc.com.example.ReservaLabSala.model.Sala;
import cookiebecoInc.com.example.ReservaLabSala.repository.SalaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SalaValidator {

    private final SalaRepository salaRepository;

    public SalaValidator(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public void validar(Sala sala) {
        if (existeSalaCadastrada(sala)) {
            throw new RegistroDuplicadoException("Sala já cadastrada com estas características");
        }
    }

    private boolean existeSalaCadastrada(Sala sala) {
        List<Sala> salasEncontradas = salaRepository.findByNomeAndCapacidadeAndLocalizacao(
                sala.getNome(),
                sala.getCapacidade(),
                sala.getLocalizacao()
        );

        if (salasEncontradas.isEmpty()) {
            return false;
        }

        Sala salaEncontrada = salasEncontradas.get(0);

        if (sala.getId() == null) {
            return true;
        }

        return !sala.getId().equals(salaEncontrada.getId());
    }
}
