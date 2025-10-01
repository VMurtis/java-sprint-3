package br.com.fiap.mottu.sprint3.dto.layoutsFilialDTO;

import jakarta.validation.constraints.NotBlank;

public record LayoutsFilialDto(
        @NotBlank
        String info_layout
) {
}
