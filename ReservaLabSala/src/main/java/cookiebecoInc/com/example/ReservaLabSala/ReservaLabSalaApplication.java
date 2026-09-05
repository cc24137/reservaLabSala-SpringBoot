package cookiebecoInc.com.example.ReservaLabSala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // permite a rota periodica de atualizar a situação da reserva
public class ReservaLabSalaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservaLabSalaApplication.class, args);
	}

}
