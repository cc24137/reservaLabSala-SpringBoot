package cookiebecoInc.com.example.ReservaLabSala.repository;

import cookiebecoInc.com.example.ReservaLabSala.model.StatusRecurso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRecursoRepository extends JpaRepository<StatusRecurso, Integer> {
    Optional<StatusRecurso> findByNome(String nome);
}
