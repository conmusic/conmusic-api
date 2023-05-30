INSERT INTO usuario
    (nome, email, senha, data_nascimento, cpf, telefone, tipo_usuario)
VALUES
    ('Leonardo Silva', 'leo.silva@email.com', '$2a$10$KZFxc7X3zu9ZuhIyVZPTS.pSczaLDkcQ7MgqAxcxbEKZ7GZV1xHYm', '2003-08-13', '76089439045', '11996489985', 'artista'),
    ('Chico Menezes', 'chico.menezes@email.com', '$2a$10$114OtetHCIqAzfSaQrL28uc3VVGqfxGTvksBvd9mjyfyMwTsRruRq', '1976-10-03', '80466979070', '11997889923', 'gerente');

INSERT INTO genre
    (name)
VALUES
    ('Rock'),('Pop'),('Metal'),('Samba'),('Pagode'),('Música ambiente'),
    ('Jazz'),('Eletrônica'),('MPB'),('Indie'),('Clássica');

INSERT INTO estabelecimento
    (endereco, qtd_tomada_110, qtd_tomada_220, capacidade, cidade, cnpj, razao_social, nome_fantasia, telefone, uf, cep, fk_gerente)
VALUES
    ('Travessa Francisco Sá, 50 - Jacarecanga', 2, 1, 500, 'Fortaleza', '57362133000125', 'Bar do Chico - Jacarecanga', 'Bar do Chico', '11907090808', 'CE', '60010320', 2);

INSERT INTO evento
    (nome, descricao, valor, taxa_cover, fk_estabelecimento, fk_genero)
VALUES
    ('Almocinho com Pagode', 'Bastante animação em Fortaleza!', 1750, 4, 1, 5);

INSERT INTO agenda
    (confirmado, data_inicio, data_termino, fk_evento)
VALUES
    (FALSE, '2023-06-21 20:05:36.904', '2023-06-21 22:05:36.904', 1);