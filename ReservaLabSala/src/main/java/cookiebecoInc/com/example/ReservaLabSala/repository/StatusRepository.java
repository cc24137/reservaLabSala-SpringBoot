package cookiebecoInc.com.example.ReservaLabSala.repository;

import cookiebecoInc.com.example.ReservaLabSala.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer> {

    Optional<Status> findByNome(String nome);
}
