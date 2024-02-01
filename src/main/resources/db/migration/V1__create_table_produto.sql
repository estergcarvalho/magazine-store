CREATE TABLE IF NOT EXISTS produto (
    id          SERIAL PRIMARY KEY,
    nome        VARCHAR(50),
    descricao   TEXT,
    preco       DECIMAL(10, 2),
    marca       VARCHAR(20)
);