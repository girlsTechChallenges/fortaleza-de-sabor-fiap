-- Insert test type user for integration tests
INSERT INTO tipos (id, name_type) 
VALUES (10, 'ADMINISTRADOR') 
ON DUPLICATE KEY UPDATE name_type = 'ADMINISTRADOR';
