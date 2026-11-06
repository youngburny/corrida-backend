package br.com.corrida.exception;

public class InscricaoNaoEncontradaException extends RuntimeException {

    public InscricaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}