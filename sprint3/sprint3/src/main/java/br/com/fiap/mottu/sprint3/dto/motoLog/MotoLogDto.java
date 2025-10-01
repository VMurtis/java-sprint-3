package br.com.fiap.mottu.sprint3.dto.motoLog;

import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.time.LocalDateTime;

public record MotoLogDto(
        LocalDateTime data_hora,

        @NotNull
        String localizacao,

        @NotNull
        String status_atual
) {
}
