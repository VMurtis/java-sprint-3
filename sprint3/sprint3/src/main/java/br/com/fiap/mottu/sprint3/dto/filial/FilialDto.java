package br.com.fiap.mottu.sprint3.dto.filial;

import br.com.fiap.mottu.sprint3.entity.Moto;
import br.com.fiap.mottu.sprint3.entity.Usuario;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record FilialDto(
        @NotNull(message = "O nome da filial é obrigatório")
        @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
        String filialName,
        @NotNull
        String endereco,
        @NotNull
        String contato,



        String horario_funcionamento,


        Long layoutId,

        List<Usuario> usuarios,

        List<Moto> motos

) {




}
