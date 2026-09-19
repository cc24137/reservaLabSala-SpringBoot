package cookiebecoInc.com.example.ReservaLabSala.repository;

import cookiebecoInc.com.example.ReservaLabSala.model.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LaboratorioRepository extends JpaRepository<Laboratorio, Integer> {

    Optional<Laboratorio> findByNome(String nome);

    List<Laboratorio> findByNomeAndCapacidadeAndLocalizacao(String nome, Integer capacidade, String localizacao);

    @Query("SELECT l FROM Laboratorio l WHERE " +
            "(:nome IS NULL OR LOWER(l.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
            "(:capacidade IS NULL OR l.capacidade = :capacidade) AND " +
            "(:localizacao IS NULL OR LOWER(l.localizacao) LIKE LOWER(CONCAT('%', :localizacao, '%'))) AND " +
            "(:statusId IS NULL OR l.statusRecurso.id = :statusId)")
    List<Laboratorio> pesquisarComFiltros(
            @Param("nome") String nome,
            @Param("capacidade") Integer capacidade,
            @Param("localizacao") String localizacao,
            @Param("statusId") Integer statusId
    );
}