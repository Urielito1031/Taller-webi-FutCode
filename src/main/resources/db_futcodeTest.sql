-- db_futcodeTest.sql - Refactorizado y Consolidado para todos los tests

-- SECCIÓN DE LIMPIEZA COMPLETA (Orden Inverso de Dependencias de Claves Foráneas)
-- Usar IF EXISTS para evitar errores si las tablas no existen
DELETE FROM formacion_equipo;
DELETE FROM equipo_jugador;
DELETE FROM jugador;
DELETE FROM equipo_torneo;
DELETE FROM equipo;
DELETE FROM sobre;
DELETE FROM usuario;
-- Comentamos las tablas que pueden no existir en la DB de test
-- DELETE FROM club;
-- DELETE FROM pais;
DELETE FROM torneo;
DELETE FROM formato_torneo;
DELETE FROM esquema;


-- INSERTS DE ENTIDADES BÁSICAS / SIN DEPENDENCIAS (Dependencias minimas o nulas)
-- Estas son las tablas "raíz" o con muy pocas dependencias.

INSERT INTO formato_torneo (id, tipo) VALUES (1, 'LIGA');

INSERT INTO esquema (id, esquema) VALUES (1, 'CUATRO_TRES_TRES');
INSERT INTO esquema (id, esquema) VALUES (2, 'CUATRO_CUATRO_DOS');
INSERT INTO esquema (id, esquema) VALUES (3, 'TRES_CINCO_DOS');
INSERT INTO esquema (id, esquema) VALUES (4, 'CINCO_TRES_DOS');
INSERT INTO esquema (id, esquema) VALUES (5, 'TRES_CUATRO_TRES');


INSERT INTO pais (id, nombre, codigo_iso) VALUES (1, 'Argentina', 'AR');
INSERT INTO pais (id, nombre, codigo_iso) VALUES (2, 'Brasil', 'BR');
INSERT INTO pais (id, nombre, codigo_iso) VALUES (3, 'Francia', 'FR');
INSERT INTO pais (id, nombre, codigo_iso) VALUES (4, 'Alemania', 'DE');

INSERT INTO club (id, nombre, pais_id, imagen) VALUES (1, 'Club Test Principal', 1, 'club_test_principal.png');
INSERT INTO club (id, nombre, pais_id, imagen) VALUES (2, 'Club Test Secundario', 2, 'club_test_secundario.png');


INSERT INTO usuario (id, email, password, rol, monedas) VALUES (1000, 'usuario_test@test.com', 'pass', 'JUGADOR', 500);


-- INSERTS DE ENTIDADES CON DEPENDENCIAS BASICAS (Ej: Equipo depende de Club, Esquema, Usuario)

-- Equipos para tests de Torneo (IDs 201-205)
INSERT INTO equipo (id, nombre, esquema_id, club_id, usuario_id) VALUES (201, 'Los Invencibles', 1, 1, 1000);
INSERT INTO equipo (id, nombre, esquema_id, club_id, usuario_id) VALUES (202, 'Fuerza Unida', 2, 1, 1000);
INSERT INTO equipo (id, nombre, esquema_id, club_id, usuario_id) VALUES (203, 'Rayos X', 1, 2, 1000);
INSERT INTO equipo (id, nombre, esquema_id, club_id, usuario_id) VALUES (204, 'Deportivo Test', 2, 2, 1000);
INSERT INTO equipo (id, nombre, esquema_id, club_id, usuario_id) VALUES (205, 'Estrellas FC', 1, 1, 1000);

-- Equipos de Prueba para FormacionEquipoRepository (IDs 100-104)
INSERT INTO equipo (id, nombre, club_id, esquema_id, usuario_id) VALUES (100, 'Equipo Formacion Test', 1, 1, 1000);
INSERT INTO equipo (id, nombre, club_id, esquema_id, usuario_id) VALUES (101, 'Equipo Sin Formacion Test', 1, 2, 1000);
INSERT INTO equipo (id, nombre, club_id, esquema_id, usuario_id) VALUES (102, 'Equipo Para Eliminar Formacion', 1, 1, 1000);
INSERT INTO equipo (id, nombre, club_id, esquema_id, usuario_id) VALUES (103, 'Equipo Para Guardar Formacion', 1, 1, 1000);
INSERT INTO equipo (id, nombre, club_id, esquema_id, usuario_id) VALUES (104, 'Equipo Extra Sin Jugadores', 1, 1, 1000);


-- INSERTS DE TORNEO Y EQUIPOTORNEO (Torneo depende de FormatoTorneo; EquipoTorneo depende de Equipo y Torneo)

INSERT INTO torneo (id, nombre, descripcion, formato_torneo_id, estado) VALUES (101, 'Liga Master', 'La liga mas competitiva.', 1, 'ABIERTO');
INSERT INTO torneo (id, nombre, descripcion, formato_torneo_id, estado) VALUES (102, 'Copa Desafío', 'Torneo de eliminacion directa.', 1, 'EN_CURSO');
INSERT INTO torneo (id, nombre, descripcion, formato_torneo_id, estado) VALUES (103, 'Torneo Vacío', 'Este torneo no tiene equipos.', 1, 'ABIERTO');
INSERT INTO torneo (id, nombre, descripcion, formato_torneo_id, estado) VALUES (104, 'Torneo Finalizado', 'Torneo que ya terminó.', 1, 'FINALIZADO');

INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (1, 201, 101, 1);
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (2, 202, 101, 2);
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (5, 205, 101, 3);
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (3, 203, 102, 1);
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (4, 204, 102, 2);
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (6, 201, 104, 1);


-- INSERTS DE JUGADOR Y FORMACION_EQUIPO (Jugador depende de Pais, Sobre; Formacion_Equipo depende de Jugador y Equipo)

-- Jugadores de Prueba para FormacionEquipoRepository (IDs 500-506)
INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, sobre_id) VALUES (500, 'Jugador FE-A', 'Apellido FE-A', NULL, 25, 1, 80.0, 0, 90.0, 1, 'NORMAL', 'ARQUERO', NULL);
INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, sobre_id) VALUES (501, 'Jugador FE-B', 'Apellido FE-B', NULL, 26, 2, 75.0, 0, 85.0, 1, 'NORMAL', 'DEFENSOR', NULL);
INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, sobre_id) VALUES (502, 'Jugador FE-C', 'Apellido FE-C', NULL, 27, 3, 78.0, 0, 88.0, 1, 'NORMAL', 'MEDIOCAMPISTA', NULL);
INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, sobre_id) VALUES (503, 'Jugador FE-D', 'Apellido FE-D', NULL, 28, 4, 70.0, 0, 80.0, 1, 'NORMAL', 'DELANTERO', NULL);
INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, sobre_id) VALUES (504, 'Jugador FE-E', 'Apellido FE-E', NULL, 29, 5, 72.0, 0, 82.0, 1, 'NORMAL', 'DEFENSOR', NULL);
INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, sobre_id) VALUES (505, 'Jugador FE-F', 'Apellido FE-F', NULL, 30, 6, 81.0, 0, 91.0, 1, 'RARO', 'MEDIOCAMPISTA', NULL);
INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, sobre_id) VALUES (506, 'Jugador FE-G (Sin Equipo)', 'Apellido FE-G', NULL, 24, 7, 70.0, 0, 80.0, 1, 'NORMAL', 'ARQUERO', NULL);


-- Jugadores para JugadorRepository (IDs a partir de 5000)
INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, sobre_id) VALUES
                                                                                                                                                                        (5000, 'Sortear', 'ArqueroRaro1', NULL, 28, 1, 80.0, 0, 90.00, 1, 'RARO', 'ARQUERO', NULL),
                                                                                                                                                                        (5001, 'Sortear', 'ArqueroRaro2', NULL, 29, 12, 81.0, 0, 91.00, 2, 'RARO', 'ARQUERO', NULL),
                                                                                                                                                                        (5002, 'Sortear', 'ArqueroRaro3', NULL, 27, 25, 79.0, 0, 89.00, 3, 'RARO', 'ARQUERO', NULL),
                                                                                                                                                                        (5003, 'Sortear', 'DelanteroEpico1', NULL, 25, 9, 88.0, 0, 95.00, 1, 'EPICO', 'DELANTERO', NULL),
                                                                                                                                                                        (5004, 'Sortear', 'DelanteroEpico2', NULL, 26, 11, 89.0, 0, 96.00, 3, 'EPICO', 'DELANTERO', NULL),
                                                                                                                                                                        (5005, 'Sortear', 'DelanteroEpico3', NULL, 24, 10, 87.0, 0, 94.00, 2, 'EPICO', 'DELANTERO', NULL),
                                                                                                                                                                        (5006, 'Sortear', 'MedioLeyenda1', NULL, 30, 8, 92.0, 0, 98.00, 1, 'LEYENDA', 'MEDIOCAMPISTA', NULL),
                                                                                                                                                                        (5007, 'Sortear', 'MedioLeyenda2', NULL, 31, 6, 93.0, 0, 99.00, 2, 'LEYENDA', 'MEDIOCAMPISTA', NULL),
                                                                                                                                                                        (5008, 'EquipoA', 'Jugador1', NULL, 25, 7, 85.0, 0, 90.00, 1, 'NORMAL', 'DELANTERO', NULL),
                                                                                                                                                                        (5009, 'EquipoA', 'Jugador2', NULL, 26, 8, 86.0, 0, 91.00, 1, 'NORMAL', 'MEDIOCAMPISTA', NULL),
                                                                                                                                                                        (5010, 'EquipoB', 'Jugador1', NULL, 27, 9, 84.0, 0, 89.00, 2, 'NORMAL', 'DEFENSOR', NULL),
                                                                                                                                                                        (5011, 'EquipoB', 'Jugador2', NULL, 28, 10, 85.0, 0, 90.00, 2, 'NORMAL', 'ARQUERO', NULL),
                                                                                                                                                                        (5012, 'Jugador', 'Individual', NULL, 22, 15, 75.0, 0, 85.00, 1, 'NORMAL', 'DEFENSOR', NULL),
                                                                                                                                                                        (5013, 'Jugador', 'A Actualizar', NULL, 23, 16, 78.0, 0, 88.00, 1, 'NORMAL', 'DELANTERO', NULL);

-- RELACIONES EQUIPO-JUGADOR (Many-to-Many)
-- Jugadores del equipo 201 (Los Invencibles)
INSERT INTO equipo_jugador (equipo_id, jugador_id) VALUES (201, 5008);
INSERT INTO equipo_jugador (equipo_id, jugador_id) VALUES (201, 5009);

-- Jugadores del equipo 202 (Fuerza Unida)
INSERT INTO equipo_jugador (equipo_id, jugador_id) VALUES (202, 5010);
INSERT INTO equipo_jugador (equipo_id, jugador_id) VALUES (202, 5011);

-- Jugadores para FormacionEquipoRepository
INSERT INTO equipo_jugador (equipo_id, jugador_id) VALUES (100, 500);
INSERT INTO equipo_jugador (equipo_id, jugador_id) VALUES (100, 501);
INSERT INTO equipo_jugador (equipo_id, jugador_id) VALUES (100, 502);
INSERT INTO equipo_jugador (equipo_id, jugador_id) VALUES (102, 503);
INSERT INTO equipo_jugador (equipo_id, jugador_id) VALUES (102, 504);
INSERT INTO equipo_jugador (equipo_id, jugador_id) VALUES (103, 505);
INSERT INTO equipo_jugador (equipo_id, jugador_id) VALUES (103, 506);

-- Formaciones de Equipo de Prueba (IDs a partir de 1000)
INSERT INTO formacion_equipo (id, equipo_id, jugador_id, posicion_en_campo) VALUES (1000, 100, 500, 'ARQUERO');
INSERT INTO formacion_equipo (id, equipo_id, jugador_id, posicion_en_campo) VALUES (1001, 100, 501, 'DEFENSOR');
INSERT INTO formacion_equipo (id, equipo_id, jugador_id, posicion_en_campo) VALUES (1002, 100, 502, 'MEDIOCAMPISTA');
INSERT INTO formacion_equipo (id, equipo_id, jugador_id, posicion_en_campo) VALUES (1003, 102, 503, 'DELANTERO');
INSERT INTO formacion_equipo (id, equipo_id, jugador_id, posicion_en_campo) VALUES (1004, 102, 504, 'DEFENSOR');
INSERT INTO formacion_equipo (id, equipo_id, jugador_id, posicion_en_campo) VALUES (1005, 103, 505, 'MEDIOCAMPISTA');
INSERT INTO formacion_equipo (id, equipo_id, jugador_id, posicion_en_campo) VALUES (1006, 103, 506, 'ARQUERO');