package cookiebecoInc.com.example.ReservaLabSala.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reserva")
@Data
public class Reserva {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_inicio", columnDefinition = "DATE")
    private LocalDate dataInicio;

    @Column(name = "data_fim", columnDefinition = "DATE")
    private LocalDate dataFim;

    @Column(name = "hora_inicio", columnDefinition = "TIME")
    private LocalTime horaInicio;

    @Column(name = "hora_fim", columnDefinition = "TIME")
    private LocalTime horaFim;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "laboratorio_id")
    private Laboratorio laboratorio;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;
}