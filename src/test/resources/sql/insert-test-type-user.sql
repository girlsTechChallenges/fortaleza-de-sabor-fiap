-- Insert test type user for integration tests
INSERT INTO tipos (id, nome_tipo) 
VALUES (10, 'ADMINISTRADOR') 
ON DUPLICATE KEY UPDATE nome_tipo = 'ADMINISTRADOR';
