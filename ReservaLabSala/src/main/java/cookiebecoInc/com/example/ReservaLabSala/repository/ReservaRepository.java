package cookiebecoInc.com.example.ReservaLabSala.repository;

import cookiebecoInc.com.example.ReservaLabSala.model.Reserva;
import cookiebecoInc.com.example.ReservaLabSala.model.Status;
import cookiebecoInc.com.example.ReservaLabSala.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByUsuario(Usuario usuario);

    List<Reserva> findByStatus(Status status);

    List<Reserva> findByDataInicioAndHoraInicio(LocalDate dataInicio, LocalTime horaInicio);

    List<Reserva> findByDataInicioBetween(LocalDate dataInicio, LocalDate dataFim);
}