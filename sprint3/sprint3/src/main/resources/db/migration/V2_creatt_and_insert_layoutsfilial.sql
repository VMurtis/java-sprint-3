CREATE SEQUENCE layoutsfilial_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE TB_layoutsfilial (
    cd_layouts_filial BIGINT PRIMARY KEY DEFAULT NEXTVAL('layoutsfilial_seq'),
    info_layout VARCHAR(100) NOT NULL
);
INSERT INTO TB_layoutsfilial (info_layout) VALUES
('Layout padrão - escritório central'),
('Layout compacto - filial de pequeno porte'),
('Layout aberto - área de atendimento ampla');
