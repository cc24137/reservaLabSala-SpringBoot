package cookiebecoInc.com.example.ReservaLabSala.controller.dto;

import lombok.Data;
import java.util.List;

@Data
public class ErroResposta {
    private int status;
    private String mensagem;
    private List<ErroCampo> erros;

    public ErroResposta(int status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }

    public ErroResposta(int status, String mensagem, List<ErroCampo> erros) {
        this.status = status;
        this.mensagem = mensagem;
        this.erros = erros;
    }
}
