package br.com.fiap.mottu.sprint3.exceptions;

public class IdNaoEncontrado extends Exception{
    public IdNaoEncontrado(){
        super("Não foi possível localizar um objeto com este Id");
    }

    public IdNaoEncontrado(String mensagem) {
        super(mensagem);
    }
}
