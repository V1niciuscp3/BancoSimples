CREATE TABLE transacao (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    origem_id BIGINT NOT NULL,
    destino_id BIGINT NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (origem_id) REFERENCES conta(id),
    FOREIGN KEY (destino_id) REFERENCES conta(id)
);