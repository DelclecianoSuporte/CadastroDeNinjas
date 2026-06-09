package dev.java10x.CadastroDeNinjas.domain.exception;

public class NinjaNaoEncontradoException extends RuntimeException{

    public NinjaNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
