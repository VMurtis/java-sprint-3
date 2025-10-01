package br.com.fiap.mottu.sprint3.entity;

import br.com.fiap.mottu.sprint3.annotations.Coluna;
import br.com.fiap.mottu.sprint3.dto.motoLog.MotoLogDto;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="TB_moto_log")
@SequenceGenerator(name = "moto_log_seq", sequenceName = "moto_log_seq", allocationSize = 1)
public class MotoLog {

    @Id
    @Column(name="cd_log")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "moto_log_seq")
    private Long id;


    @Coluna(nome="data_hora")
    @Column(name = "horario_funcionamento", nullable = false)
    private LocalDateTime data_hora;


    @Coluna(nome="localizacao")
    @Column(nullable = false, length = 100)
    private String localizacao;

    @Coluna(nome="status_atual")
    @Column(nullable = false, length = 100)
    private String status_atual;




    public MotoLog(MotoLogDto dto) {
        this.data_hora = dto.data_hora();
        this.localizacao = dto.localizacao();
        this.status_atual = dto.status_atual();
    }




}