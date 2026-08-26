package cookiebecoInc.com.example.ReservaLabSala.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cpf", length = 14)
    private String cpf;

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "data_aniversario")
    private LocalDate dataAniversario;

    @Column(name = "celular", length = 20)
    private String celular;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "senha", length = 100)
    private String senha;
}
