package br.com.fiap.mottu.sprint3.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioDto(
        @NotBlank
        String email,

        @NotBlank
        String cpf,

        @NotBlank
        String telefone,

        @NotBlank
        String userName,

        @NotBlank
        String passwordHash,
        @NotBlank
        Integer tipoPerfil,

        @NotNull Long filialId


) {

}
