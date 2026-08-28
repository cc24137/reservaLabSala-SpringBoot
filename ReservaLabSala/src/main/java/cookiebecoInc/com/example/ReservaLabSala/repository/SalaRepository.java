package cookiebecoInc.com.example.ReservaLabSala.repository;

import cookiebecoInc.com.example.ReservaLabSala.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, Integer> {

    List<Sala> findByNome(String nome);

    List<Sala> findByCapacidade(Integer capacidade);

    List<Sala> findByLocalizacao(String localizacao);

    List<Sala> findByNomeAndCapacidadeAndLocalizacao(
            String nome,
            Integer capacidade,
            String localizacao
    );
}
