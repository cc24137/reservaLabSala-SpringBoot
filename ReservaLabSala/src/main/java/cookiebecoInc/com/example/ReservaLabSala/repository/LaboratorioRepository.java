package cookiebecoInc.com.example.ReservaLabSala.repository;

import cookiebecoInc.com.example.ReservaLabSala.model.Laboratorio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaboratorioRepository extends JpaRepository<Laboratorio, Integer> {

    List<Laboratorio> findByNome(String nome);

    List<Laboratorio> findByCapacidade(Integer capacidade);

    List<Laboratorio> findByLocalizacao(String localizacao);

    List<Laboratorio> findByNomeAndCapacidadeAndLocalizacao(
            String nome,
            Integer capacidade,
            String localizacao
    );
}
