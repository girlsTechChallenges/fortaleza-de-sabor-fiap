-- Insert test types first
INSERT INTO tipos (id, name_type) VALUES 
(1, 'PROPRIETARIO');

-- Insert test owner users
INSERT INTO usuarios (id, name, email, login, password, change_date, type_id) VALUES 
(1, 'Proprietário Italiano', 'italiano@test.com', 'propitaliano', '$2a$12$encrypted_password', '2024-01-01', 1),
(2, 'Proprietário Brasileiro', 'brasileiro@test.com', 'propbrasileiro', '$2a$12$encrypted_password', '2024-01-01', 1),
(3, 'Proprietário Japonês', 'japones@test.com', 'propjapones', '$2a$12$encrypted_password', '2024-01-01', 1);

-- Insert multiple test restaurants for integration tests
INSERT INTO restaurantes (id, name, tipo_cozinha, usuario_id) VALUES
(1, 'Restaurant Italiano', 'ITALIANA', 1),
(2, 'Restaurant Brasileiro', 'BRASILEIRA', 2),
(3, 'Restaurant Japonês', 'JAPONESA', 3);

-- Insert addresses for the test restaurants
INSERT INTO enderecos_restaurantes (id, street, district, complement, number, state, city, postCode, restaurante_id) VALUES
(1, 'Street Italiana', 'Centro', 'Loja 1', 100, 'SP', 'São Paulo', '01234567', 1),
(2, 'Street Brasileira', 'Vila Madalena', 'Loja 2', 200, 'SP', 'São Paulo', '02345678', 2),
(3, 'Street Japonesa', 'Liberdade', 'Loja 3', 300, 'SP', 'São Paulo', '03456789', 3);

-- Insert business hours for the test restaurants
INSERT INTO horarios (id, dia_semana, hora_abertura, hora_fechamento, observacao, restaurante_id) VALUES
(1, 'MONDAY', '08:00:00', '22:00:00', 'Funcionamento normal', 1),
(2, 'MONDAY', '09:00:00', '23:00:00', 'Funcionamento normal', 2),
(3, 'MONDAY', '11:00:00', '21:00:00', 'Funcionamento normal', 3);
