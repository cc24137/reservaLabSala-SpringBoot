package cookiebecoInc.com.example.ReservaLabSala.repository;

import cookiebecoInc.com.example.ReservaLabSala.model.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusReservaRepository extends JpaRepository<StatusReserva, Integer> {
    Optional<StatusReserva> findByNome(String nome);
}
