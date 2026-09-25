package cookiebecoInc.com.example.ReservaLabSala.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sala")
@Data
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 80)
    private String nome;

    @Column(name = "capacidade", nullable = false)
    private Integer capacidade;

    @Column(name = "localizacao", nullable = false, length = 50)
    private String localizacao;

    @ManyToOne
    @JoinColumn(name = "status_recurso_id", nullable = false)
    private StatusRecurso statusRecurso;
}
