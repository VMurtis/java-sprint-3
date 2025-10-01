package br.com.fiap.mottu.sprint3.entity;



import br.com.fiap.mottu.sprint3.annotations.Coluna;
import br.com.fiap.mottu.sprint3.dto.filial.FilialDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="TB_filial")
@SequenceGenerator(name = "filial_seq", sequenceName = "filial_seq", allocationSize = 1)
public class Filial {

    @Id
    @Column(name = "cd_filial")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filial_seq")
    private Long id;

    @Column(name = "filial_name", nullable = false, length = 100)
    @Coluna(nome = "filial_name", tamanho = 100, obrigatorio = true)
    private String filialName;

    @Column(name = "endereco", nullable = false, length = 100)
    @Coluna(nome = "endereco", tamanho = 100, obrigatorio = true)
    private String endereco;

    @Column(name = "contato", nullable = false, length = 100)
    @Coluna(nome = "contato", tamanho = 100, obrigatorio = true)
    private String contato;


    @Column(name = "horario_funcionamento", nullable = false, length = 100)
    @Coluna(nome = "horario_funcionamento", tamanho = 100, obrigatorio = true)
    private String horarioFuncionamento;




    @OneToOne
    @JoinColumn(name="layout_id")
    private LayoutsFilial layoutsFilial;


    @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Usuario> usuarios;


    @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Moto> motos;








    public Filial(FilialDto dto) {
        this.filialName = dto.filialName();
        this.endereco = dto.endereco();
        this.contato = dto.contato();
        this.layoutsFilial = new LayoutsFilial(dto.layoutId());
        this.usuarios = dto.usuarios();
        this.motos = dto.motos();

    }

}
