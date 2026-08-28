package cookiebecoInc.com.example.ReservaLabSala.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErroCampo {
    private String campo;
    private String erro;
}