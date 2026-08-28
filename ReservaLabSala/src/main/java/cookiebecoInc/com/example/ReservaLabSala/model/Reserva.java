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

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "hora_inicio")
    private LocalTime horaInicio;

    @Column(name = "hora_fim")
    private LocalTime horaFim;

    @ManyToOne
        ( fetch = FetchType.EAGER )
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
        ( fetch = FetchType.EAGER )
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
        ( fetch = FetchType.EAGER )
    @JoinColumn(name = "laboratorio_id")
    private Laboratorio laboratorio;

    @ManyToOne
        ( fetch = FetchType.EAGER )
    @JoinColumn(name = "sala_id")
    private Sala sala;
}
