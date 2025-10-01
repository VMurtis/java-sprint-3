
CREATE SEQUENCE moto_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE tb_moto (
    cd_moto BIGINT PRIMARY KEY DEFAULT NEXTVAL('moto_seq'),
    placa VARCHAR(100) NOT NULL,
    chassi VARCHAR(100) NOT NULL,
    iot_info VARCHAR(100) NOT NULL,
    rfid_tag VARCHAR(100) NOT NULL,
    localizacao VARCHAR(100) NOT NULL,
    status_atual VARCHAR(100) NOT NULL,
    id_filial VARCHAR(100) NOT NULL,
    tipo_modelo VARCHAR(50) NOT NULL,
    cd_user BIGINT,
    usuario_id BIGINT,
    filial_id BIGINT,
    moto_log_id BIGINT,
    CONSTRAINT fk_moto_user FOREIGN KEY (cd_user) REFERENCES TB_usuario(cd_usuario),
    CONSTRAINT fk_moto_usuario FOREIGN KEY (usuario_id) REFERENCES TB_usuario(cd_usuario),
    CONSTRAINT fk_moto_filial FOREIGN KEY (filial_id) REFERENCES TB_filial(cd_filial),
    CONSTRAINT fk_moto_log FOREIGN KEY (moto_log_id) REFERENCES TB_motolog(cd_motolog)
);

INSERT INTO TB_moto
(placa, chassi, iot_info, rfid_tag, localizacao, status_atual, id_filial, tipo_modelo, cd_user, usuario_id, filial_id)
VALUES
('ABC1234', '9BWZZZ377VT004251', 'IoT-01', 'RFID001', 'Garagem Central', 'Ativa', '1', 'ESPORTIVA', 1, 1, 1),
('DEF5678', '9BWZZZ377VT004252', 'IoT-02', 'RFID002', 'Garagem Norte', 'Manutenção', '2', 'STANDARD', 2, 2, 2),
('GHI9012', '9BWZZZ377VT004253', 'IoT-03', 'RFID003', 'Garagem Sul', 'Disponível', '3', 'CUSTOM', 3, 3, 3);

