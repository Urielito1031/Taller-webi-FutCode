-- db_futcodeTest.sql - Refactorizado y Consolidado

-- ** 1. SECCIÓN DE LIMPIEZA COMPLETA (Orden Inverso de Dependencias de Claves Foráneas) **
-- Es crucial que esta sección elimine datos en el orden correcto para evitar errores de FK.
-- Descomentar si tu estrategia de test requiere limpiar la DB antes de cada run.
-- DELETE FROM formacion_equipo;
-- DELETE FROM equipo_torneo;
-- DELETE FROM jugador;
-- DELETE FROM equipo;
-- DELETE FROM sobre; -- Si usas la tabla sobre en algún test
-- DELETE FROM usuario;
-- DELETE FROM club;
-- DELETE FROM pais;
-- DELETE FROM torneo;
-- DELETE FROM formato_torneo;
-- DELETE FROM esquema;


-- ** 2. INSERTS DE ENTIDADES BÁSICAS / SIN DEPENDENCIAS (O dependencias mínimas) **

-- FormatoTorneo (No duplicado, mantiene tus IDs originales)
INSERT INTO formato_torneo (id, tipo) VALUES (1, 'LIGA');

-- Esquema (Consolidado: elimina la duplicación, mantiene los esenciales)
INSERT INTO esquema (id, esquema) VALUES (1, 'CUATRO_TRES_TRES');
INSERT INTO esquema (id, esquema) VALUES (2, 'CUATRO_CUATRO_DOS');
-- Si hay más esquemas necesarios para otros tests, agregarlos aquí:
INSERT INTO esquema (id, esquema) VALUES (3, 'TRES_CINCO_DOS');
INSERT INTO esquema (id, esquema) VALUES (4, 'CINCO_TRES_DOS');
INSERT INTO esquema (id, esquema) VALUES (5, 'TRES_CUATRO_TRES');


-- Pais (Necesario para Jugador y Club)
INSERT INTO pais (id, nombre, codigo_iso) VALUES (1, 'Argentina', 'arg.png');
INSERT INTO pais (id, nombre, codigo_iso) VALUES (2, 'Brasil', 'bra.png'); -- Añadido para mayor variedad si es necesario

-- Club (Necesario para Equipo)
INSERT INTO club (id, nombre, pais_id, imagen) VALUES (1, 'Club Test Principal', 1, 'club_test_principal.png');
INSERT INTO club (id, nombre, pais_id, imagen) VALUES (2, 'Club Test Secundario', 2, 'club_test_secundario.png');


-- Usuario (Necesario para Equipo. Ajustados IDs para evitar conflictos con otros datos que puedas tener)
INSERT INTO usuario (id, email, password, rol, monedas) VALUES (1000, 'usuario_test@test.com', 'pass', 'JUGADOR', 500);


-- ** 3. INSERTS PARA TESTS DE TORNEO Y EQUIPOTORNEO (NO MODIFICADOS, MANTIENEN SU COHERENCIA)**

-- Torneo (Tal como lo tenías, sin cambios)
INSERT INTO torneo (id, nombre, descripcion, formato_torneo_id, estado) VALUES (101, 'Liga Master', 'La liga mas competitiva.', 1, 'ABIERTO');
INSERT INTO torneo (id, nombre, descripcion, formato_torneo_id, estado) VALUES (102, 'Copa Desafío', 'Torneo de eliminacion directa.', 1, 'EN_CURSO');
INSERT INTO torneo (id, nombre, descripcion, formato_torneo_id, estado) VALUES (103, 'Torneo Vacío', 'Este torneo no tiene equipos.', 1, 'ABIERTO');
INSERT INTO torneo (id, nombre, descripcion, formato_torneo_id, estado) VALUES (104, 'Torneo Finalizado', 'Torneo que ya terminó.', 1, 'FINALIZADO');

-- Equipo (Tus equipos originales para tests de torneo, IDs 201-205)
INSERT INTO equipo (id, nombre, esquema_id) VALUES (201, 'Los Invencibles', 1);
INSERT INTO equipo (id, nombre, esquema_id) VALUES (202, 'Fuerza Unida', 2);
INSERT INTO equipo (id, nombre, esquema_id) VALUES (203, 'Rayos X', 1);
INSERT INTO equipo (id, nombre, esquema_id) VALUES (204, 'Deportivo Test', 2);
INSERT INTO equipo (id, nombre, esquema_id) VALUES (205, 'Estrellas FC', 1);

-- EquipoTorneo (Tal como lo tenías, sin cambios)
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (1, 201, 101, 1);
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (2, 202, 101, 2);
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (5, 205, 101, 3);
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (3, 203, 102, 1);
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (4, 204, 102, 2);
INSERT INTO equipo_torneo (id, equipo_id, torneo_id, posicion) VALUES (6, 201, 104, 1);


-- ** 4. DATOS ESENCIALES PARA TESTS DE FormacionEquipoRepositoryImpl **
-- Estos son los datos *mínimos* que te propuse, con IDs ajustados para no colisionar con otros si los importas juntos.

-- Equipos de Prueba para FormacionEquipoRepository (IDs 100-103 no colisionan con 200s)
INSERT INTO equipo (id, nombre, club_id, esquema_id, usuario_id) VALUES (100, 'Equipo Formacion Test', 1, 1, 1000); -- Equipo con formacion
INSERT INTO equipo (id, nombre, club_id, esquema_id, usuario_id) VALUES (101, 'Equipo Sin Formacion Test', 1, 2, 1000); -- Equipo sin formacion
INSERT INTO equipo (id, nombre, club_id, esquema_id, usuario_id) VALUES (102, 'Equipo Para Eliminar Formacion', 1, 1, 1000); -- Equipo para pruebas de eliminacion
INSERT INTO equipo (id, nombre, club_id, esquema_id, usuario_id) VALUES (103, 'Equipo Para Guardar Formacion', 1, 1, 1000); -- Equipo para pruebas de guardado/update

-- Jugadores de Prueba para FormacionEquipoRepository (IDs a partir de 500 para evitar colisiones)
INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, equipo_id, sobre_id) VALUES (500, 'Jugador FE-A', 'Apellido FE-A', NULL, 25, 1, 80.0, 0, 90.0, 1, 'NORMAL', 'ARQUERO', 100, NULL);
INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, equipo_id, sobre_id) VALUES (501, 'Jugador FE-B', 'Apellido FE-B', NULL, 26, 2, 75.0, 0, 85.0, 1, 'NORMAL', 'DEFENSOR', 100, NULL);
INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, equipo_id, sobre_id) VALUES (502, 'Jugador FE-C', 'Apellido FE-C', NULL, 27, 3, 78.0, 0, 88.0, 1, 'NORMAL', 'MEDIOCAMPISTA', 100, NULL);

INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, equipo_id, sobre_id) VALUES (503, 'Jugador FE-D', 'Apellido FE-D', NULL, 28, 4, 70.0, 0, 80.0, 1, 'NORMAL', 'DELANTERO', 102, NULL);
INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, equipo_id, sobre_id) VALUES (504, 'Jugador FE-E', 'Apellido FE-E', NULL, 29, 5, 72.0, 0, 82.0, 1, 'NORMAL', 'DEFENSOR', 102, NULL);

INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, equipo_id, sobre_id) VALUES (505, 'Jugador FE-F', 'Apellido FE-F', NULL, 30, 6, 81.0, 0, 91.0, 1, 'RARO', 'MEDIOCAMPISTA', 103, NULL);
INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, equipo_id, sobre_id) VALUES (506, 'Jugador FE-G (Sin Equipo)', 'Apellido FE-G', NULL, 24, 7, 70.0, 0, 80.0, 1, 'NORMAL', 'ARQUERO', NULL, NULL);


-- Formaciones de Equipo de Prueba (IDs a partir de 1000 para evitar colisiones)
INSERT INTO formacion_equipo (id, equipo_id, jugador_id, posicion_en_campo) VALUES (1000, 100, 500, 'ARQUERO');
INSERT INTO formacion_equipo (id, equipo_id, jugador_id, posicion_en_campo) VALUES (1001, 100, 501, 'DEFENSOR');
INSERT INTO formacion_equipo (id, equipo_id, jugador_id, posicion_en_campo) VALUES (1002, 100, 502, 'MEDIOCAMPISTA');

INSERT INTO formacion_equipo (id, equipo_id, jugador_id, posicion_en_campo) VALUES (1003, 102, 503, 'DELANTERO');
INSERT INTO formacion_equipo (id, equipo_id, jugador_id, posicion_en_campo) VALUES (1004, 102, 504, 'DEFENSOR');