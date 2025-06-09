package dev.rodolfo.sistema_advogados.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
