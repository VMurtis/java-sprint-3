package br.com.fiap.mottu.sprint3.entity;


import br.com.fiap.mottu.sprint3.annotations.Coluna;
import br.com.fiap.mottu.sprint3.dto.moto.MotoDto;
import br.com.fiap.mottu.sprint3.entity.enums.TipoMoto;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="TB_moto")
@SequenceGenerator(name = "moto_seq", sequenceName = "moto_seq", allocationSize = 1)
public class Moto {

    @Id
    @Column(name="cd_moto")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "moto_seq")
    private Long idMoto;



    @Coluna(nome="placa")
    @Column(nullable = false, length = 100)
    private String placa;

    @Coluna(nome="chassi")
    @Column(nullable = false, length = 100)
    private String chassi;

    @ManyToOne
    @JoinColumn(name = "cd_user")
    private Usuario user;

    @Coluna(nome="iot_info")
    @Column(nullable = false, length = 100)
    private String iotInfo;

    @Coluna(nome="rfid_tag")
    @Column(nullable = false, length = 100)
    private String rfidTag;

    @Coluna(nome="localizacao")
    @Column(nullable = false, length = 100)
    private String localizacao;

    @Coluna(nome="status_atual")
    @Column(nullable = false, length = 100)
    private String statusAtual;

    @Coluna(nome="id_filial")
    @Column(nullable = false, length = 100)
    private String idFilial;

    @OneToOne
    @JoinColumn(name="moto_log_id")
    private MotoLog motolog;

    @ManyToOne
    @JoinColumn(name = "usuario_id")  // Chave estrangeira
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "filial_id") // nome da coluna no banco,
    private Filial filial;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_modelo", nullable = false)
    private TipoMoto tipoMoto;









    public Moto(MotoDto dto) {

        this.placa = dto.placa();
        this.chassi = dto.chassi();
        this.iotInfo = dto.iotInfo();
        this.rfidTag = dto.rfidTag();
        this.localizacao = dto.localizacao();
        this.statusAtual = dto.statusAtual();
        this.tipoMoto = dto.tipoMoto();
    }





}