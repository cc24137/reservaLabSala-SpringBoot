package cookiebecoInc.com.example.ReservaLabSala.repository;

import cookiebecoInc.com.example.ReservaLabSala.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByUsuarioId(Integer usuarioId);
    List<Reserva> findByStatusReservaId(Integer statusReservaId);
    List<Reserva> findByLaboratorioId(Integer laboratorioId);
    List<Reserva> findBySalaId(Integer salaId);
    List<Reserva> findByDataInicio(LocalDate dataInicio);
    List<Reserva> findByHoraInicio(LocalTime horaInicio);
    List<Reserva> findByStatusReservaNome(String nomeStatus);
}
