
CREATE SEQUENCE moto_log_seq START WITH 1 INCREMENT BY 1;


CREATE TABLE tb_moto_log (
    cd_log BIGINT PRIMARY KEY DEFAULT NEXTVAL('moto_log_seq'),
    horario_funcionamento TIMESTAMP NOT NULL,
    localizacao VARCHAR(100) NOT NULL,
    status_atual VARCHAR(100) NOT NULL
);

INSERT INTO TB_moto_log (horario_funcionamento, localizacao, status_atual) VALUES
('2025-10-01 08:30:00', 'Garagem Central', 'Ativa'),
('2025-10-01 09:15:00', 'Garagem Norte', 'Manutenção'),
('2025-10-01 10:45:00', 'Garagem Sul', 'Disponível');
