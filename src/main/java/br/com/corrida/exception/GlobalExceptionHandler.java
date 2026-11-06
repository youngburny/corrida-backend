package br.com.corrida.exception;

import br.com.corrida.dto.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResponse> tratarDadosInvalidos(
            IllegalArgumentException exception
    ) {
        ErroResponse erro = new ErroResponse(exception.getMessage());

        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(InscricaoNaoEncontradaException.class)
    public ResponseEntity<ErroResponse> tratarNaoEncontrado(
            InscricaoNaoEncontradaException exception
    ) {
        ErroResponse erro = new ErroResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErroResponse> tratarConflito(
            IllegalStateException exception
    ) {
        ErroResponse erro = new ErroResponse(exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> tratarJsonInvalido(
            HttpMessageNotReadableException exception
    ) {
        ErroResponse erro = new ErroResponse("Corpo da requisição inválido. Verifique os campos e seus formatos.");

        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> tratarErroInterno(
            Exception exception
    ) {
        ErroResponse erro = new ErroResponse("Ocorreu um erro interno no servidor.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}