-- Insert test type first
INSERT INTO tipos (id, nome_tipo) VALUES (1, 'CLIENTE');

-- Insert test user for integration tests  
INSERT INTO usuarios (id, nome, email, login, senha, data_alteracao, tipo_id) 
VALUES (1, 'João Silva', 'joao.silva@test.com', 'joaosilva', '$2a$12$encrypted_password', '2024-01-01', 1);

-- Insert address for the test user
INSERT INTO enderecos_usuarios (id, rua, bairro, complemento, numero, estado, cidade, cep, usuario_id)
VALUES (1, 'Rua dos Testes', 'Centro', 'Apto 45', 123, 'SP', 'São Paulo', '01234567', 1);
