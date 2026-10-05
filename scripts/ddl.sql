CREATE TABLE categorias (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255)
);

CREATE TABLE transacoes (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    descricao VARCHAR(150) NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    data DATE NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    categoria_id BIGINT NOT NULL,

    CONSTRAINT FK_transacoes_categorias
        FOREIGN KEY (categoria_id)
        REFERENCES categorias(id)
);