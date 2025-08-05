-- Insert test owner user first
INSERT INTO tipos (id, name_type) VALUES (10, 'PROPRIETARIO') ON DUPLICATE KEY UPDATE name_type = 'PROPRIETARIO';
INSERT INTO usuarios (id, name, email, login, password, change_date, type_id) 
VALUES (10, 'Maria Proprietária', 'restaurante10@test.com', 'proprietaria10', '$2a$12$encrypted_password', '2024-01-01', 10);

-- Insert test restaurant for integration tests
INSERT INTO restaurantes (id, name, tipo_cozinha, usuario_id) 
VALUES (10, 'Restaurant Teste', 'ITALIANA', 10);

-- Insert address for the test restaurant
INSERT INTO enderecos_restaurantes (id, street, district, complement, number, state, city, postCode, restaurante_id)
VALUES (1, 'Street do Restaurant', 'Centro', 'Loja 1', 100, 'SP', 'São Paulo', '01234567', 1);

-- Insert business hours for the test restaurant
INSERT INTO horarios (id, dia_semana, hora_abertura, hora_fechamento, observacao, restaurante_id)
VALUES (1, 'MONDAY', '08:00:00', '22:00:00', 'Funcionamento normal', 1);
