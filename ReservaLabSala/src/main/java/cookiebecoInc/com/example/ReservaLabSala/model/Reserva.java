package cookiebecoInc.com.example.ReservaLabSala.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reserva")
@Data
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_inicio", nullable = false, columnDefinition = "DATE")
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false, columnDefinition = "DATE")
    private LocalDate dataFim;

    @JdbcTypeCode(SqlTypes.TIME)
    @Column(name = "hora_inicio", nullable = false, columnDefinition = "TIME")
    private LocalTime horaInicio;

    @JdbcTypeCode(SqlTypes.TIME)
    @Column(name = "hora_fim", nullable = false, columnDefinition = "TIME")
    private LocalTime horaFim;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "status_reserva_id", nullable = false)
    private StatusReserva statusReserva;

    @ManyToOne
    @JoinColumn(name = "laboratorio_id")
    private Laboratorio laboratorio;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;
}