package br.com.fiap.mottu.sprint3.entity;

import br.com.fiap.mottu.sprint3.annotations.Coluna;
import br.com.fiap.mottu.sprint3.dto.layoutsFilialDTO.LayoutsFilialDto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="TB_layoutsfilial")
@SequenceGenerator(name = "layoutsfilial_seq", sequenceName = "layoutsfilial_seq", allocationSize = 1)
public class LayoutsFilial {
    @Id
    @Column(name="cd_Layouts_filial")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "layouts_seq")
    private Long id;


    @Coluna(nome="info_layout")
    @Column(nullable = false, length = 100)
    private String infoLayout;



    public LayoutsFilial(LayoutsFilialDto dto) {
        this.infoLayout = dto.info_layout();
    }

    public LayoutsFilial(Long id) {
        this.id = id;
    }


}