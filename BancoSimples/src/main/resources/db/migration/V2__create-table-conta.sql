CREATE TABLE conta(
    id BIGSERIAL PRIMARY KEY,
    tipoConta VARCHAR(255),
    CONSTRAINT chk_tipoConta CHECK (tipoConta = 'corrente' OR tipoConta = 'lojista'),
    saldo DECIMAL(10,2) DEFAULT 0.00,
    usuario_id BIGINT,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
    );