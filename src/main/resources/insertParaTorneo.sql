
INSERT INTO `equipo_torneo` (`id`, `goles_a_favor`, `goles_en_contra`, `partidos_empatados`, `partidos_ganados`, `partidos_jugados`, `partidos_perdidos`, `posicion`, `puntos`, `equipo_id`, `torneo_id`, `posicion_anterior`) VALUES
(1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0),
(2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 0),
(3, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 0),
(4, 0, 0, 0, 0, 0, 0, 0, 0, 4, 1, 0),
(5, 0, 0, 0, 0, 0, 0, 0, 0, 5, 1, 0),
(6, 0, 0, 0, 0, 0, 0, 0, 0, 6, 1, 0),
(7, 0, 0, 0, 0, 0, 0, 0, 0, 7, 1, 0),
(8, 0, 0, 0, 0, 0, 0, 0, 0, 8, 1, 0),
(9, 0, 0, 0, 0, 0, 0, 0, 0, 9, 1, 0),
(10, 0, 0, 0, 0, 0, 0, 0, 0, 10, 1, 0),
(11, 0, 0, 0, 0, 0, 0, 0, 0, 11, 1, 0),
(12, 0, 0, 0, 0, 0, 0, 0, 0, 12, 1, 0),
(33, 0, 0, 0, 0, 0, 0, 0, 0, 32, 1, 0),
(34, 0, 0, 0, 0, 0, 0, 0, 0, 33, 1, 0),
(35, 0, 0, 0, 0, 0, 0, 0, 0, 31, 1, 0);



INSERT INTO `formato_torneo` (`id`, `tipo`, `fase_id`) VALUES
(1, 'COPA', NULL),
(2, 'LIGA', NULL);

INSERT INTO `usuario` (`id`, `activo`, `email`, `monedas`, `password`, `rol`, `equipo_id`) VALUES
(1, b'1', 'aaaaa@mail.com', 10000, '$2a$10$48aC.N6QgvolX3Clw4e3lO4f8TfvHC7BQXatsCJ3dEnoC13IqyY9K', 'USER', 1),
(2, b'1', 'aasddrdiegoasdasda5@gmail.com', 10000, '$2a$10$gFkTGYzgmlvGb8YxKSco0O679w2LuA4Yc3L5g2ynhV.hB/0A2uIai', 'USER', 32),
(3, b'1', 'aasddrdiegoasdasdassss55555@gmail.com', 10000, '$2a$10$rVSW4.86TZm4KcYtbFpEF.fuWWQXKMbzA0V2oppPHpI2RUdaL.xXS', 'USER', 33),
(4, b'1', 'crearfecghaassdasd@gmail.com', 10000, '$2a$10$1GKLVluIdqH3YqsI/n8qD.VkCa4G5KLy8Nf/49cSeQVACYUP1ON.a', 'USER', 34);


