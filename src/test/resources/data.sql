-- Inserção de dados na tabela de tipos
INSERT INTO tipos (id, tipo)
VALUES
    (1, 'DONO'),
    (2, 'CLIENTE');

-- Inserção de dados na tabela de usuários
INSERT INTO usuarios (id, nome, email, login, data_alteracao, tipo, senha)
VALUES
    (1, 'João Silva', 'joao.silva@example.com', 'joaosilva', '2025-05-10', 'CLIENTE', 'senha123'),
    (2, 'Maria Oliveira', 'maria.oliveira@example.com', 'mariaoliveira', '2025-05-10', 'DONO', 'senha456'),
    (3, 'Carlos Souza', 'carlos.souza@example.com', 'carlossouza', '2025-05-10', 'CLIENTE', 'senha789');

-- Inserção de dados na tabela de endereços
INSERT INTO enderecos (id, rua, bairro, complemento, numero, estado, cidade, cep, usuario_id)
VALUES
    (1, 'Rua A', 'Bairro X', 'Apto 101', 123, 'SP', 'São Paulo', 12345678, 1),
    (2, 'Rua B', 'Bairro Y', NULL, 456, 'RJ', 'Rio de Janeiro', 87654321, 2),
    (3, 'Rua C', 'Bairro Z', 'Casa', 789, 'MG', 'Belo Horizonte', 11223344, 3);