-- Insert test owner user first
INSERT INTO tipos (id, nome_tipo) VALUES (10, 'PROPRIETARIO') ON DUPLICATE KEY UPDATE nome_tipo = 'PROPRIETARIO';
INSERT INTO usuarios (id, nome, email, login, senha, data_alteracao, tipo_id) 
VALUES (10, 'Maria Proprietária', 'restaurante10@test.com', 'proprietaria10', '$2a$12$encrypted_password', '2024-01-01', 10);

-- Insert test restaurant for integration tests
INSERT INTO restaurantes (id, name, tipo_cozinha, usuario_id) 
VALUES (10, 'Restaurante Teste', 'ITALIANA', 10);

-- Insert address for the test restaurant
INSERT INTO enderecos_restaurantes (id, rua, bairro, complemento, numero, estado, cidade, cep, restaurante_id)
VALUES (1, 'Rua do Restaurante', 'Centro', 'Loja 1', 100, 'SP', 'São Paulo', '01234567', 1);

-- Insert business hours for the test restaurant
INSERT INTO horarios (id, dia_semana, hora_abertura, hora_fechamento, observacao, restaurante_id)
VALUES (1, 'MONDAY', '08:00:00', '22:00:00', 'Funcionamento normal', 1);
