CREATE SEQUENCE filial_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE TB_filial (
    cd_filial BIGINT NOT NULL PRIMARY KEY,
    filial_name VARCHAR(100) NOT NULL,
    endereco VARCHAR(100) NOT NULL,
    contato VARCHAR(100) NOT NULL,
    horario_funcionamento VARCHAR(100) NOT NULL,
    layout_id BIGINT,
    CONSTRAINT fk_layout FOREIGN KEY (layout_id) REFERENCES layouts_filial(layout_id)
);

INSERT INTO TB_filial (filial_name, endereco, contato, horario_funcionamento, layout_id) VALUES
('Filial Centro', 'Rua Principal, 123', '11999999999', '08:00 - 18:00', 1);

INSERT INTO TB_filial (filial_name, endereco, contato, horario_funcionamento, layout_id) VALUES
('Filial Norte', 'Av. das Flores, 456', '11888888888', '09:00 - 19:00', 2);

INSERT INTO TB_filial (filial_name, endereco, contato, horario_funcionamento, layout_id) VALUES
('Filial Sul', 'Rua das Laranjeiras, 789', '11777777777', '07:30 - 17:30', 3);