package cookiebecoInc.com.example.ReservaLabSala.repository;

import cookiebecoInc.com.example.ReservaLabSala.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    @Query("SELECT r FROM Reserva r " +
            "LEFT JOIN r.laboratorio l " +
            "LEFT JOIN r.sala s " +
            "LEFT JOIN r.statusReserva st " +
            "LEFT JOIN r.usuario u " +
            "WHERE " +
            "(:statusId IS NULL OR st.id = :statusId) AND " +
            "(:usuarioId IS NULL OR u.id = :usuarioId) AND " +
            "(:laboratorioId IS NULL OR l.id = :laboratorioId) AND " +
            "(:salaId IS NULL OR s.id = :salaId) AND " +
            "(:recursoNome IS NULL OR " +
            " (l.nome IS NOT NULL AND LOWER(l.nome) LIKE LOWER(CONCAT('%', :recursoNome, '%'))) OR " +
            " (s.nome IS NOT NULL AND LOWER(s.nome) LIKE LOWER(CONCAT('%', :recursoNome, '%')))) AND " +
            "(:dataInicio IS NULL OR r.dataInicio >= :dataInicio) AND " +
            "(:dataFim IS NULL OR r.dataFim <= :dataFim) AND " +
            "(:horaInicio IS NULL OR r.horaInicio >= :horaInicio)")
    List<Reserva> pesquisarComFiltros(
            @Param("statusId") Integer statusId,
            @Param("usuarioId") Integer usuarioId,
            @Param("laboratorioId") Integer laboratorioId,
            @Param("salaId") Integer salaId,
            @Param("recursoNome") String recursoNome,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("horaInicio") LocalTime horaInicio
    );

    // Verifica sobreposição de horário para Laboratórios (ignorando reservas canceladas)
    @Query("SELECT r FROM Reserva r WHERE " +
            "r.laboratorio.id = :laboratorioId AND " +
            "(:idReserva IS NULL OR r.id != :idReserva) AND " +
            "r.statusReserva.nome != 'CANCELADA' AND " +
            "(r.dataInicio <= :dataFim AND r.dataFim >= :dataInicio) AND " +
            "(r.horaInicio < :horaFim AND r.horaFim > :horaInicio)")
    List<Reserva> buscarConflitosLaboratorio(
            @Param("laboratorioId") Integer laboratorioId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFim") LocalTime horaFim,
            @Param("idReserva") Integer idReserva
    );

    // Verifica sobreposição de horário para Salas (ignorando reservas canceladas)
    @Query("SELECT r FROM Reserva r WHERE " +
            "r.sala.id = :salaId AND " +
            "(:idReserva IS NULL OR r.id != :idReserva) AND " +
            "r.statusReserva.nome != 'CANCELADA' AND " +
            "(r.dataInicio <= :dataFim AND r.dataFim >= :dataInicio) AND " +
            "(r.horaInicio < :horaFim AND r.horaFim > :horaInicio)")
    List<Reserva> buscarConflitosSala(
            @Param("salaId") Integer salaId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFim") LocalTime horaFim,
            @Param("idReserva") Integer idReserva
    );
}