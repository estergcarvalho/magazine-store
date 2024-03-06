CREATE TABLE IF NOT EXISTS caracteristica (
    id              SERIAL PRIMARY KEY,
    produto_id      INT NOT NULL,
    nome            VARCHAR(255) NOT NULL,
    descricao       TEXT  NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);