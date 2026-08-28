package cookiebecoInc.com.example.ReservaLabSala.repository;

import cookiebecoInc.com.example.ReservaLabSala.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByDataAniversario(LocalDate dataAniversario);

    List<Usuario> findByEmailAndDataAniversario(String email, LocalDate dataAniversario);
}