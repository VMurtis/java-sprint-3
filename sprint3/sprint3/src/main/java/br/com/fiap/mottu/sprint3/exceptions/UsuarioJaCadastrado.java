package br.com.fiap.mottu.sprint3.exceptions;

public class UsuarioJaCadastrado extends Exception{

    public UsuarioJaCadastrado(){
        super("Nome de usuário já está em uso");
    }

    public UsuarioJaCadastrado(String message){
        super(message);
    }
}
