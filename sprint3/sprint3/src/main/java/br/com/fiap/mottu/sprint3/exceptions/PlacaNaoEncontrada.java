package br.com.fiap.mottu.sprint3.exceptions;

public class PlacaNaoEncontrada extends Exception{

    public PlacaNaoEncontrada(){
        super("Não foi possível localizar um objeto com esta placa");
    }

    public PlacaNaoEncontrada(String mensagem) {
        super(mensagem);
    }
}