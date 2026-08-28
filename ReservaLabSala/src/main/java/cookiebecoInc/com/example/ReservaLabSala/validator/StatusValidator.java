package cookiebecoInc.com.example.ReservaLabSala.validator;

import cookiebecoInc.com.example.ReservaLabSala.exceptions.RegistroDuplicadoException;
import cookiebecoInc.com.example.ReservaLabSala.model.Status;
import cookiebecoInc.com.example.ReservaLabSala.repository.StatusRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StatusValidator {

    private final StatusRepository statusRepository;

    public StatusValidator(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public void validar(Status status) {
        if (existeStatusCadastrado(status)) {
            throw new RegistroDuplicadoException("Status já cadastrado com este nome");
        }
    }

    private boolean existeStatusCadastrado(Status status) {
        Optional<Status> statusEncontrado = statusRepository.findByNome(status.getNome());

        if (status.getId() == null) {
            return statusEncontrado.isPresent();
        }

        return statusEncontrado.isPresent() &&
                !status.getId().equals(statusEncontrado.get().getId());
    }
}
