package cookiebecoInc.com.example.ReservaLabSala.validator;

import cookiebecoInc.com.example.ReservaLabSala.exceptions.RegistroDuplicadoException;
import cookiebecoInc.com.example.ReservaLabSala.model.Laboratorio;
import cookiebecoInc.com.example.ReservaLabSala.repository.LaboratorioRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LaboratorioValidator {

    private final LaboratorioRepository laboratorioRepository;

    public LaboratorioValidator(LaboratorioRepository laboratorioRepository) {
        this.laboratorioRepository = laboratorioRepository;
    }

    public void validar(Laboratorio laboratorio) {
        if (existeLaboratorioCadastrado(laboratorio)) {
            throw new RegistroDuplicadoException("Laboratório já cadastrado com estas características");
        }
    }

    private boolean existeLaboratorioCadastrado(Laboratorio laboratorio) {
        List<Laboratorio> laboratoriosEncontrados = laboratorioRepository.findByNomeAndCapacidadeAndLocalizacao(
                laboratorio.getNome(),
                laboratorio.getCapacidade(),
                laboratorio.getLocalizacao()
        );

        if (laboratoriosEncontrados.isEmpty()) {
            return false;
        }

        Laboratorio laboratorioEncontrado = laboratoriosEncontrados.get(0);

        if (laboratorio.getId() == null) {
            return true;
        }

        return !laboratorio.getId().equals(laboratorioEncontrado.getId());
    }
}
