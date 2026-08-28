package cookiebecoInc.com.example.ReservaLabSala.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "status")
@Data
public class Status {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 30)
    private String nome; // "Livre", "Ocupado", "Bloqueado", "Reservado"
}
