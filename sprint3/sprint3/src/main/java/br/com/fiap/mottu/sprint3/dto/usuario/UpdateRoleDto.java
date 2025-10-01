package br.com.fiap.mottu.sprint3.dto.usuario;

import jakarta.validation.constraints.NotNull;

public record UpdateRoleDto(
        @NotNull(message = "O tipo de perfil não pode ser nulo.")
        Integer tipoPerfil
) {
}