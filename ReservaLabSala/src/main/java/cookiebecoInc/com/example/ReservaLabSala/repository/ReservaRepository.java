package cookiebecoInc.com.example.ReservaLabSala.repository;

import cookiebecoInc.com.example.ReservaLabSala.model.Reserva;
import cookiebecoInc.com.example.ReservaLabSala.model.Status;
import cookiebecoInc.com.example.ReservaLabSala.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByUsuarioId(Integer usuarioId);
    List<Reserva> findByStatusId(Integer statusId);
    List<Reserva> findByLaboratorioId(Integer laboratorioId);
    List<Reserva> findBySalaId(Integer salaId);
    List<Reserva> findByDataInicio(LocalDate dataInicio);
    List<Reserva> findByHoraInicio(LocalTime horaInicio);
    List<Reserva> findByDataInicioAndHoraInicio(LocalDate dataInicio, LocalTime horaInicio);
    List<Reserva> findByStatusNomeIgnoreCase(String statusNome);

    List<Reserva> findByUsuarioIdAndStatusId(Integer usuarioId, String statusNome);
    List<Reserva> findByUsuarioIdAndStatusIdAndDataInicio(Integer usuarioId, String statusNome, LocalDate dataInicio);
}