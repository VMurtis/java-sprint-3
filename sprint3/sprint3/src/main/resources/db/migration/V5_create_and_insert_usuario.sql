CREATE SEQUENCE usuario_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE TB_usuario (
    cd_user BIGINT PRIMARY KEY DEFAULT NEXTVAL('usuario_seq'),
    nome_usuario VARCHAR(25) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    telefone VARCHAR(100) NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    filial_id BIGINT,
    tp_perfil INT NOT NULL,
    CONSTRAINT fk_usuario_filial FOREIGN KEY (filial_id) REFERENCES TB_filial(cd_filial)
);

INSERT INTO TB_usuario (nome_usuario, email, cpf, telefone, user_name, password_hash, filial_id, tp_perfil) VALUES
('Ana', 'anasilva@email.com', '12345678901', '11999999999', 'vinicius', 'hashsenha1', 1, 1),
('Maria', 'maria@email.com', '10987654321', '11888888888', 'maria', 'hashsenha2', 2, 2),
('Carol', 'carol@email.com', '11122233344', '11777777777', 'carol', 'hashsenha3', 3, 2);
