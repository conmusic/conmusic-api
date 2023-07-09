INSERT INTO usuario
    (nome, email, senha, data_nascimento, cpf, telefone, tipo_usuario)
VALUES
    ('Admin conmusic', 'admin@conmusic.com', '$2a$10$5yMt9HBEz.b/onB8TSrppO3kn2vTVz3BPPH25uJV5zn9s5nUJ9Xl.', '2023-03-11', '52702055052', '11996489985', 'admin'),
    ('Leonardo Silva', 'leo.silva@email.com', '$2a$10$KZFxc7X3zu9ZuhIyVZPTS.pSczaLDkcQ7MgqAxcxbEKZ7GZV1xHYm', '2003-08-13', '76089439045', '11992489985', 'artista'),
    ('Chico Menezes', 'chico.menezes@email.com', '$2a$10$114OtetHCIqAzfSaQrL28uc3VVGqfxGTvksBvd9mjyfyMwTsRruRq', '1976-10-03', '80466979070', '11997889923', 'gerente'),
    ('João Silva', 'joao.silva@example.com', '@Aa12345678', '1990-01-01', '12345678901', '987654321', 'artista'),
    ('Maria Santos', 'maria.santos@example.com', '@Aa12345678', '1995-02-02', '23456789012', '123456789', 'gerente'),
    ('Pedro Almeida', 'pedro.almeida@example.com', '@Aa12345678', '1988-03-03', '34567890123', '987654321', 'artista'),
    ('Ana Oliveira', 'ana.oliveira@example.com', '@Aa12345678', '1992-04-04', '45678901234', '123456789', 'gerente');

INSERT INTO genre
    (name)
VALUES
    ('Rock'),('Pop'),('Metal'),('Samba'),('Pagode'),('Música ambiente'),
    ('Jazz'),('Eletrônica'),('MPB'),('Indie'),('Clássica');

INSERT INTO estabelecimento
    (endereco, qtd_tomada_110, qtd_tomada_220, capacidade, cidade, cnpj, razao_social, nome_fantasia, telefone, uf, cep, fk_gerente)
VALUES
    ('Travessa Francisco Sá, 50 - Jacarecanga', 2, 1, 500, 'Fortaleza', '57362133000125', 'Bar do Chico - Jacarecanga', 'Bar do Chico', '11907090808', 'CE', '60010320', 3),
    ('Avenida Paulista, 1000 - Bela Vista', 3, 2, 1000, 'São Paulo', '12345678000123', 'Restaurante Central', 'Central Restaurante', '11987654321', 'SP', '01310000', 5),
    ('Rua da Praia, 123 - Centro', 1, 0, 200, 'Rio de Janeiro', '98765432000198', 'Pizzaria Bella Napoli', 'Bella Napoli', '2199998888', 'RJ', '20010000', 7),
    ('Rua das Flores, 789 - Jardins', 4, 2, 300, 'São Paulo', '56789012000134', 'Café Charmoso', 'Charmoso Café', '11912345678', 'SP', '01435000', 3);

INSERT INTO evento
    (nome, descricao, valor, taxa_cover, fk_estabelecimento, fk_genero)
VALUES
    ('Almocinho com Pagode', 'Bastante animação em Fortaleza!', 1750, 4, 1, 5),
    ('Festival de Jazz', 'Uma noite de jazz e improvisação', 2500, 8, 2, 7),
    ('Festa do Rock', 'Uma celebração do melhor do rock', 1200, 5, 3, 1),
    ('Noite de Samba', 'Samba de raiz para animar a todos', 1500, 3, 1, 4),
    ('Festa de Verão', 'Celebre o verão com música e diversão!', 1500, 3, 2, 4),
    ('Rave eletronica', 'Curta o ritmo contagiante', 1200, 5, 3, 8),
    ('Festival de Eletrônica', 'Uma experiência única de música eletrônica', 2000, 6, 4, 8),
    ('Noite de MPB', 'Encante-se com a música popular brasileira', 1300, 3, 1, 9);

INSERT INTO agenda
    (confirmado, data_inicio, data_termino, fk_evento)
VALUES
    (FALSE, '2023-06-21 20:05:36.904', '2023-06-21 22:05:36.904', 1),
    (TRUE, '2023-07-15 18:30:00', '2023-07-15 23:00:00', 2),
    (FALSE, '2023-08-05 19:00:00', '2023-08-05 22:00:00', 3),
    (TRUE, '2023-09-10 21:30:00', '2023-09-10 23:30:00', 4),
    (TRUE, '2023-07-30 20:00:00', '2023-07-30 23:00:00', 2),
    (FALSE, '2023-08-18 19:30:00', '2023-08-18 22:30:00', 3),
    (TRUE, '2023-09-25 18:00:00', '2023-09-25 21:00:00', 4),
    (FALSE, '2023-01-21 20:05:36', '2023-01-21 22:05:36', 1),
    (FALSE, '2023-02-21 20:05:36', '2023-02-21 22:05:36', 1),
    (FALSE, '2023-03-21 20:05:36', '2023-03-21 22:05:36', 1),
    (FALSE, '2023-04-21 20:05:36', '2023-04-21 22:05:36', 1),
    (FALSE, '2023-05-21 20:05:36', '2023-05-21 22:05:36', 1);

INSERT INTO show
    (status, valor, taxa_cover, fk_evento, fk_artista, fk_agenda)
VALUES
    (6, 100.0, 50.0, 1, 2, 1),
    (3, 200.0, 80.0, 2, 4, 2),
    (3, 400.0, 100.0, 3, 2, 3),
    (3, 700.0, 200.0, 4, 6, 4),
    (8, 300.0, 90.0, 5, 6, 5),
    (6, 400.0, 100.0, 6, 2, 6),
    (9, 500.0, 10.0, 7, 4, 7),
    (6, 600.0, 30.0, 8, 4, 2),
    (6, 100.0, 50.0, 1, 2, 12),
    (6, 100.0, 50.0, 1, 2, 12),
    (6, 100.0, 50.0, 1, 2, 11),
    (6, 100.0, 50.0, 1, 2, 11),
    (6, 100.0, 50.0, 1, 2, 11),
    (6, 100.0, 50.0, 1, 2, 10),
    (6, 100.0, 50.0, 1, 2, 9),
    (6, 100.0, 50.0, 1, 2, 9),
    (6, 100.0, 50.0, 1, 2, 8);