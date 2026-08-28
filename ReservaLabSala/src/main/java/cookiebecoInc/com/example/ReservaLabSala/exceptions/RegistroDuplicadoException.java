package cookiebecoInc.com.example.ReservaLabSala.exceptions;

public class RegistroDuplicadoException extends RuntimeException {
    public RegistroDuplicadoException(String mensagem) {
        super(mensagem);
    }
}
