-- db_futcodeTest.sql

-- Opcional: Eliminar datos existentes para asegurar un estado limpio.
-- Esto es útil si hbm2ddl.auto no es 'create' o si quieres limpiar antes de los inserts
-- DELETE FROM equipo_torneo;
-- DELETE FROM equipo;
-- DELETE FROM torneo;
-- DELETE FROM formato_torneo;
-- DELETE FROM esquema;

-- Insertar datos para FormatoTorneo
INSERT INTO formato_torneo (id, tipo) VALUES (1, 'LIGA');
INSERT INTO formato_torneo (id, tipo) VALUES (2, 'ELIMINACION_DIRECTA');

-- Insertar datos para Esquema
INSERT INTO esquema (id, esquema) VALUES (1, 'CUATRO_TRES_TRES');
INSERT INTO esquema (id, esquema) VALUES (2, 'CUATRO_CUATRO_DOS');

-- Insertar datos para Torneo
INSERT INTO torneo (id, nombre, descripcion, formato_torneo_id, estado) VALUES (101, 'Liga Master', 'La liga mas competitiva.', 1, 'ABIERTO');
INSERT INTO torneo (id, nombre, descripcion, formato_torneo_id, estado) VALUES (102, 'Copa Desafío', 'Torneo de eliminacion directa.', 2, 'EN_CURSO');
INSERT INTO torneo (id, nombre, descripcion, formato_torneo_id, estado) VALUES (103, 'Torneo Vacío', 'Este torneo no tiene equipos.', 1, 'ABIERTO');
INSERT INTO torneo (id, nombre, descripcion, formato_torneo_id, estado) VALUES (104, 'Torneo Finalizado', 'Torneo que ya terminó.', 1, 'FINALIZADO');


-- Insertar datos para Equipo
INSERT INTO equipo (id, nombre, esquema_id) VALUES (201, 'Los Invencibles', 1);
INSERT INTO equipo (id, nombre, esquema_id) VALUES (202, 'Fuerza Unida', 2);
INSERT INTO equipo (id, nombre, esquema_id) VALUES (203, 'Rayos X', 1);
INSERT INTO equipo (id, nombre, esquema_id) VALUES (204, 'Deportivo Test', 2);
INSERT INTO equipo (id, nombre, esquema_id) VALUES (205, 'Estrellas FC', 1);


-- Insertar datos para EquipoTorneo
-- Liga Master (ID 101)
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (1, 201, 101, 1); -- Los Invencibles en Liga Master
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (2, 202, 101, 2); -- Fuerza Unida en Liga Master
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (5, 205, 101, 3); -- Estrellas FC en Liga Master

-- Copa Desafío (ID 102)
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (3, 203, 102, 1); -- Rayos X en Copa Desafío
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (4, 204, 102, 2); -- Deportivo Test en Copa Desafío

-- Torneo Finalizado (ID 104)
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (6, 201, 104, 1); -- Los Invencibles en Torneo Finalizado