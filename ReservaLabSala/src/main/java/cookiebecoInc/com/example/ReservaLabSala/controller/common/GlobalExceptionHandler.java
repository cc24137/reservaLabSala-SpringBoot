package cookiebecoInc.com.example.ReservaLabSala.controller.common;

import cookiebecoInc.com.example.ReservaLabSala.controller.dto.ErroCampo;
import cookiebecoInc.com.example.ReservaLabSala.controller.dto.ErroResposta;
import cookiebecoInc.com.example.ReservaLabSala.exceptions.OperacaoNaoPermitidaException;
import cookiebecoInc.com.example.ReservaLabSala.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(),fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação de campo",
                listaErros);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleConstraintViolationException(ConstraintViolationException e) {
        List<ErroCampo> listaErros = e.getConstraintViolations()
                .stream()
                .map(cv -> new ErroCampo(
                        cv.getPropertyPath().toString(),
                        cv.getMessage()))
                .collect(Collectors.toList());

        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação de campo",
                listaErros);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleRegistroDuplicadoException(RegistroDuplicadoException e)
    {
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage());
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleOperacaoNaoPermitidaException(OperacaoNaoPermitidaException e)
    {
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ErroResposta handleResponseStatusException(ResponseStatusException e)
    {
        return new ErroResposta(e.getStatusCode().value(), e.getReason());
    }
}
