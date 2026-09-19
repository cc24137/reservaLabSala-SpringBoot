package cookiebecoInc.com.example.ReservaLabSala.repository;

import cookiebecoInc.com.example.ReservaLabSala.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmailAndSenha(String email, String senha);

    @Query("SELECT u FROM Usuario u WHERE " +
            "(:cpf IS NULL OR u.cpf = :cpf) AND " +
            "(:nome IS NULL OR LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
            "(:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:dataAniversario IS NULL OR u.dataAniversario = :dataAniversario)")
    List<Usuario> pesquisarComFiltros(
            @Param("cpf") String cpf,
            @Param("nome") String nome,
            @Param("email") String email,
            @Param("dataAniversario") LocalDate dataAniversario
    );
}