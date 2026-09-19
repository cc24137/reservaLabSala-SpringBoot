package cookiebecoInc.com.example.ReservaLabSala.repository;

import cookiebecoInc.com.example.ReservaLabSala.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SalaRepository extends JpaRepository<Sala, Integer> {

    Optional<Sala> findByNome(String nome);

    List<Sala> findByNomeAndCapacidadeAndLocalizacao(String nome, Integer capacidade, String localizacao);

    @Query("SELECT s FROM Sala s WHERE " +
            "(:nome IS NULL OR LOWER(s.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
            "(:capacidade IS NULL OR s.capacidade = :capacidade) AND " +
            "(:localizacao IS NULL OR LOWER(s.localizacao) LIKE LOWER(CONCAT('%', :localizacao, '%'))) AND " +
            "(:statusId IS NULL OR s.statusRecurso.id = :statusId)")
    List<Sala> pesquisarComFiltros(
            @Param("nome") String nome,
            @Param("capacidade") Integer capacidade,
            @Param("localizacao") String localizacao,
            @Param("statusId") Integer statusId
    );
}