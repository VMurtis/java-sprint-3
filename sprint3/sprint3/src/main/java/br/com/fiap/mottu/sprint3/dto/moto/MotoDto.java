package br.com.fiap.mottu.sprint3.dto.moto;

import br.com.fiap.mottu.sprint3.entity.enums.TipoMoto;
import jakarta.validation.constraints.NotNull;

public record MotoDto(
        @NotNull
        String modelo,

        @NotNull
        String placa,

        @NotNull
        String chassi,


        @NotNull
        String iotInfo,

        @NotNull
        String rfidTag,

        @NotNull
        String localizacao,

        @NotNull
        String statusAtual,


        Long motoLogId,


        Long filialId,
        @NotNull
        TipoMoto tipoMoto
) {
}
