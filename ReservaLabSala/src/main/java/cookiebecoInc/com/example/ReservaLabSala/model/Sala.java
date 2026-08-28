package cookiebecoInc.com.example.ReservaLabSala.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sala")
@Data
public class Sala {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", length = 80)
    private String nome;

    @Column(name = "capacidade")
    private Integer capacidade;

    @Column(name = "localizacao", length = 50)
    private String localizacao;
}
