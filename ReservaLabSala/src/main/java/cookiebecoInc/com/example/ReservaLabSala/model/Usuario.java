package cookiebecoInc.com.example.ReservaLabSala.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "nome", nullable = false, length = 80)
    private String nome;

    @Column(name = "data_aniversario", nullable = false, columnDefinition = "DATE")
    private LocalDate dataAniversario;

    @Column(name = "celular", nullable = false, length = 20)
    private String celular;

    @Column(name = "email", nullable = false, length = 80, unique = true)
    private String email;

    @Column(name = "senha", nullable = false, length = 100)
    private String senha;
}
