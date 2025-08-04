-- Cleanup all test data
DELETE FROM enderecos_usuarios WHERE usuario_id IN (1, 2, 3);
DELETE FROM enderecos_restaurantes WHERE restaurante_id IN (1, 2, 3);
DELETE FROM horarios WHERE restaurante_id IN (1, 2, 3);
DELETE FROM usuarios WHERE id IN (1, 2, 3);
DELETE FROM restaurantes WHERE id IN (1, 2, 3);
DELETE FROM items_cardapio WHERE id_item_cardapio IN (1, 2, 3);
DELETE FROM tipos WHERE id IN (1, 2, 3);
