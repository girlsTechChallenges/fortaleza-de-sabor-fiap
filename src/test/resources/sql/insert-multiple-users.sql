-- Insert test types first
INSERT INTO tipos (id, nome_tipo) VALUES 
(1, 'CLIENTE'),
(2, 'PROPRIETARIO');

-- Insert multiple test users for integration tests
INSERT INTO usuarios (id, nome, email, login, senha, data_alteracao, tipo_id) VALUES 
(1, 'João Silva', 'joao.silva@test.com', 'joaosilva', '$2a$12$encrypted_password', '2024-01-01', 1),
(2, 'Maria Santos', 'maria.santos@test.com', 'mariasantos', '$2a$12$encrypted_password', '2024-01-01', 2),
(3, 'Carlos Oliveira', 'carlos.oliveira@test.com', 'carlosoliveira', '$2a$12$encrypted_password', '2024-01-01', 1);

-- Insert addresses for the test users
INSERT INTO enderecos_usuarios (id, rua, bairro, complemento, numero, estado, cidade, cep, usuario_id) VALUES
(1, 'Rua dos Testes', 'Centro', 'Apto 45', 123, 'SP', 'São Paulo', '01234567', 1),
(2, 'Rua das Flores', 'Jardim', 'Casa', 456, 'SP', 'São Paulo', '02345678', 2),
(3, 'Rua dos Sonhos', 'Vila Nova', 'Bloco B', 789, 'SP', 'São Paulo', '03456789', 3);
