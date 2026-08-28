package cookiebecoInc.com.example.ReservaLabSala.exceptions;

public class OperacaoNaoPermitidaException extends RuntimeException {
    public OperacaoNaoPermitidaException(String mensagem) {
        super(mensagem);
    }
}