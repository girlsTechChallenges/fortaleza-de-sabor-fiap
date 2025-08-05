-- Insert test type first
INSERT INTO tipos (id, name_type) VALUES (1, 'CLIENTE');

-- Insert test user for integration tests  
INSERT INTO usuarios (id, name, email, login, password, change_date, type_id) 
VALUES (1, 'João Silva', 'joao.silva@test.com', 'joaosilva', '$2a$12$encrypted_password', '2024-01-01', 1);

-- Insert address for the test user
INSERT INTO enderecos_usuarios (id, street, district, complement, number, state, city, postCode, usuario_id)
VALUES (1, 'Street dos Testes', 'Centro', 'Apto 45', 123, 'SP', 'São Paulo', '01234567', 1);
