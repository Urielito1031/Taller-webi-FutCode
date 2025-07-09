-- Inserciones para la tabla Fecha
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (1, 1, false, 1);
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (2, 2, false, 1);
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (3, 3, false, 1);
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (4, 4, false, 1);
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (5, 5, false, 1);
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (6, 6, false, 1);
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (7, 7, false, 1);
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (8, 8, false, 1);
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (9, 9, false, 1);
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (10, 10, false, 1);
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (11, 11, false, 1);
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (12, 12, false, 1);
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (13, 13, false, 1);
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (14, 14, false, 1);
INSERT INTO fecha (id, numero, simulada, torneo_id) VALUES (15, 15, false, 1);

-- Fecha 1
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (1, 16, 0, 0, 'PENDIENTE', 1);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (2, 15, 0, 0, 'PENDIENTE', 1);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (3, 14, 0, 0, 'PENDIENTE', 1);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (4, 13, 0, 0, 'PENDIENTE', 1);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (5, 12, 0, 0, 'PENDIENTE', 1);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (6, 11, 0, 0, 'PENDIENTE', 1);
-- ...

-- Fecha 2: 8 partidos (equipos distintos a los de la Fecha 1)
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (1, 3, 0, 0, 'PENDIENTE', 2);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (2, 4, 0, 0, 'PENDIENTE', 2);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (5, 7, 0, 0, 'PENDIENTE', 2);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (6, 8, 0, 0, 'PENDIENTE', 2);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (9, 11, 0, 0, 'PENDIENTE', 2);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (10, 12, 0, 0, 'PENDIENTE', 2);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (13, 15, 0, 0, 'PENDIENTE', 2);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (14, 16, 0, 0, 'PENDIENTE', 2);

-- Fecha 3: 8 partidos (equipos distintos a los de la Fecha 2)
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (1, 4, 0, 0, 'PENDIENTE', 3);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (2, 3, 0, 0, 'PENDIENTE', 3);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (5, 8, 0, 0, 'PENDIENTE', 3);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (6, 7, 0, 0, 'PENDIENTE', 3);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (9, 12, 0, 0, 'PENDIENTE', 3);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (10, 11, 0, 0, 'PENDIENTE', 3);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (13, 16, 0, 0, 'PENDIENTE', 3);
INSERT INTO partido (equipo_local_id, equipo_visitante_id, goles_local, goles_visitante, resultado, fecha_id) VALUES (14, 15, 0, 0, 'PENDIENTE', 3);