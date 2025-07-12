-- =============================
-- Tablas independientes (sin FK)
-- =============================

-- PAIS
INSERT INTO pais (id, nombre, codigo_iso, bandera_url) VALUES
    (1, 'Argentina', 'AR', 'https://flagcdn.com/ar.svg'),
    (2, 'Brasil', 'BR', 'https://flagcdn.com/br.svg'),
    (3, 'España', 'ES', 'https://flagcdn.com/es.svg'),
    (4, 'Francia', 'FR', 'https://flagcdn.com/fr.svg'),
    (5, 'Alemania', 'DE', 'https://flagcdn.com/de.svg'),
    (6, 'Italia', 'IT', 'https://flagcdn.com/it.svg'),
    (7, 'Inglaterra', 'GB', 'https://flagcdn.com/gb.svg'),
    (8, 'México', 'MX', 'https://flagcdn.com/mx.svg'),
    (9, 'Uruguay', 'UY', 'https://flagcdn.com/uy.svg'),
    (10, 'Chile', 'CL', 'https://flagcdn.com/cl.svg'),
    (11, 'Portugal', 'PT', 'https://flagcdn.com/pt.svg'),
    (12, 'Países Bajos', 'NL', 'https://flagcdn.com/nl.svg'),
    (13, 'Estados Unidos', 'US', 'https://flagcdn.com/us.svg'),
    (14, 'Bélgica', 'BE', 'https://flagcdn.com/be.svg');

-- ESQUEMA
INSERT INTO esquema (id, esquema) VALUES
    (1, 'CUATRO_TRES_TRES'),
    (2, 'CUATRO_CUATRO_DOS'),
    (3, 'TRES_CINCO_DOS'),
    (4, 'CINCO_TRES_DOS'),
    (5, 'TRES_CUATRO_TRES');

-- ESTADIO
INSERT INTO estadio (id, nombre, capacidad, ubicacion, imagen_url) VALUES
    (1, 'Estadio Monumental', 83000, 'Buenos Aires, Argentina', 'https://upload.wikimedia.org/wikipedia/commons/8/8b/Estadio_Monumental_%28River_Plate%29.jpg'),
    (2, 'Maracanã', 78838, 'Río de Janeiro, Brasil', 'https://upload.wikimedia.org/wikipedia/commons/6/6b/Maracan%C3%A3_Stadium_2014.jpg'),
    (3, 'Camp Nou', 99354, 'Barcelona, España', 'https://upload.wikimedia.org/wikipedia/commons/2/2c/Camp_Nou_panorama.jpg'),
    (4, 'Allianz Arena', 75000, 'Múnich, Alemania', 'https://upload.wikimedia.org/wikipedia/commons/0/0e/Allianz-Arena_2010.jpg'),
    (5, 'San Siro', 80018, 'Milán, Italia', 'https://upload.wikimedia.org/wikipedia/commons/3/3e/San_Siro_Stadium_-_panoramio.jpg'),
    (6, 'Old Trafford', 74310, 'Manchester, Inglaterra', 'https://example.com/old_trafford.jpg'),
    (7, 'Anfield', 54000, 'Liverpool, Inglaterra', 'https://example.com/anfield.jpg'),
    (8, 'Parc des Princes', 47929, 'París, Francia', 'https://example.com/parc_des_princes.jpg'),
    (9, 'Allianz Stadium', 41507, 'Turín, Italia', 'https://example.com/allianz_stadium_turin.jpg'),
    (10, 'Stamford Bridge', 40834, 'Londres, Inglaterra', 'https://example.com/stamford_bridge.jpg'),
    (11, 'Estadio Azteca', 87000, 'Ciudad de México, México', 'https://example.com/estadio_azteca.jpg'),
    (12, 'Estadio Campeón del Siglo', 40000, 'Montevideo, Uruguay', 'https://example.com/penarol_estadio.jpg'),
    (13, 'Estadio Monumental David Arellano', 47347, 'Santiago, Chile', 'https://example.com/colo_colo_estadio.jpg'),
    (14, 'Cívitas Metropolitano', 68456, 'Madrid, España', 'https://example.com/metropolitano.jpg'),
    (15, 'Signal Iduna Park', 81365, 'Dortmund, Alemania', 'https://example.com/signal_iduna_park.jpg'),
    (16, 'Estádio da Luz', 65000, 'Lisboa, Portugal', 'https://example.com/estadio_da_luz.jpg'),
    (17, 'Johan Cruyff Arena', 55000, 'Ámsterdam, Países Bajos', 'https://example.com/johan_cruyff_arena.jpg'),
    (18, 'Mercedes-Benz Stadium', 71000, 'Atlanta, EE.UU.', 'https://example.com/mercedes_benz.jpg'),
    (19, 'Celtic Park', 60411, 'Glasgow, Escocia', 'https://example.com/celtic_park.jpg'),
    (20, 'King Baudouin Stadium', 50000, 'Bruselas, Bélgica', 'https://example.com/baudouin.jpg'),
    (21, 'Estadio do Dragão', 50033, 'Porto, Portugal', 'https://example.com/dragao.jpg'),
    (22, 'Amsterdam Arena', 54000, 'Ámsterdam, Países Bajos', 'https://example.com/amsterdam_arena.jpg'),
    (23, 'Yankee Stadium', 47309, 'New York, EE.UU.', 'https://example.com/yankee.jpg'),
    (24, 'Ibrox Stadium', 50817, 'Glasgow, Escocia', 'https://example.com/ibrox.jpg'),
    (25, 'Jan Breydel Stadium', 29062, 'Brugge, Bélgica', 'https://example.com/breydel.jpg');

-- FASE
INSERT INTO fase (id, nombre) VALUES
    (1, 'Fase de grupos'),
    (2, 'Octavos de final'),
    (3, 'Cuartos de final'),
    (4, 'Semifinal'),
    (5, 'Final');

-- FORMATO_TORNEO
INSERT INTO formato_torneo (id, tipo, fase_id) VALUES
    (1, 'COPA', NULL),
    (2, 'LIGA', NULL);

-- =============================
-- Tablas que dependen de las anteriores
-- =============================

-- TORNEO (depende de formato_torneo)
INSERT INTO torneo (id, nombre, descripcion, formato_torneo_id, estado, torneo_copa_id, torneo_liga_id) VALUES
(1, 'Liga Argentina', 'Sumergite en la pasión del fútbol local. Competí contra equipos como Boca, River y Racing en una liga desafiante. ¡Demostrá quién manda en casa y llevate 20.000 monedas!', 2, 'ABIERTO', NULL, NULL),
(2, 'Brasileirao', 'Viví la intensidad del fútbol brasileño enfrentando a Flamengo, Palmeiras y Corinthians. Ida y vuelta, ritmo frenético y 30.000 monedas para el campeón. ¿Te la bancás?', 2, 'ABIERTO', NULL, NULL),
(3, 'Premier League', 'La liga más exigente del mundo te espera. Enfrentate a gigantes como Manchester City, Chelsea y Liverpool. Jugá al límite por el título y 70.000 monedas.', 2, 'ABIERTO', NULL, NULL),
(4, 'LaLiga', 'Un clásico europeo: táctica, talento y presión. Medite con Barcelona, Real Madrid y Atlético por el dominio total y un premio de 50.000 monedas.', 2, 'ABIERTO', NULL, NULL);


-- ------------------------------------------------

-- =============================
-- DATOS MIGRADOS DE MOVLER
-- =============================

-- EQUIPOS ARGENTINOS (nuevos)
INSERT INTO equipo (id, nombre, imagen, esquema_id) VALUES
 (1000, 'Boca Juniors', 'https://imgur.com/DB9vIuL.png', 3),
(1002, 'River Plate', 'https://i.imgur.com/wU1slIe.png', 4),
(1003, 'Racing Club', 'https://i.imgur.com/DaUtieZ.png', 2),
(1004, 'Independiente', 'https://i.imgur.com/uVSAjum.png', 1),
(1005, 'San Lorenzo', 'https://i.imgur.com/8m3QOCe.png', 5),
(1006, 'Vélez Sarsfield', 'https://i.imgur.com/Vhrl2ko.png', 2),
(1007, 'Newell\s Old Boys', 'https://i.imgur.com/Mqove2Y.png', 3),
(1008, 'Rosario Central', 'https://i.imgur.com/m1A7188.png', 4),
(1009, 'Estudiantes', 'https://i.imgur.com/u8PrZAM.png', 5),
(1010, 'Gimnasia y Esgrima La Plata', 'https://i.imgur.com/YODTRNE.png', 1),
(1011, 'Banfield', 'https://i.imgur.com/6nmN6HJ.png', 2),
(1012, 'Colón', 'https://i.imgur.com/efpjd0W.png', 3),
(1013, 'Argentinos Juniors', 'https://i.imgur.com/kDpyrdm.png', 4),
(1014, 'Huracán', 'https://i.imgur.com/KgW20xf.png', 5),
(1015, 'Talleres de Córdoba', 'https://i.imgur.com/7AF8286.png', 1),
(1016, 'Platense', 'https://i.imgur.com/oPiMZ3M.png', 3);



-- EQUIPOS BRASILEROS
INSERT INTO equipo (id, nombre, imagen, esquema_id) VALUES
    (2000, 'Atlético-MG', 'https', 3),
    (2001, 'Botafogo', 'https:', 4),
    (2002, 'Bahia', 'https:/', 2),
    (2003, 'Ceará', 'https://.free.nf//.png', 1),
    (2004, 'Corinthians', 'https://..nf//.png', 5),
    (2005, 'Cruzeiro', 'https://.free.nf//.png', 2),
    (2006, 'Flamengo', '://..nf/equipos/.png', 3),
    (2007, 'Fluminense', 'https:l.png', 4),
    (2008, 'Fortaleza', 'https://.free.nf/eq', 5),
    (2009, 'Gremio', '://.', 1),
    (2010, 'Internacional', 'https://.free.nf//banfield.png', 2),
    (2011, 'Sao Paulo', 'https://.free.nf/equipos/colon.png', 3),
    (2012, 'Palmeiras', 'https:ng', 4),
    (2013, 'Vitoria', 'https://.free.nf/equipos/huracan.png', 5),
    (2014, 'Santos', 'https://.png', 1),
    (2015, 'Vasco da Gama', 'https://nose', 3);


-- EQUIPOS PREMIER LEAGUE
INSERT INTO equipo (id, nombre, imagen, esquema_id) VALUES
(2016, 'Aston Villa', 'https', 3),
(2017, 'Arsenal', 'https:', 4),
(2018, 'Chelsea', 'https:/', 2),
(2019, 'Brighton & Hove Albion', 'https://.free.nf//.png', 1),
(2020, 'Fulham', 'https://..nf//.png', 5),
(2021, 'Leeds United', 'https://.free.nf//.png', 2),
(2022, 'Everton', '://..nf/equipos/.png', 3),
(2023, 'Liverpool', 'https:l.png', 4),
(2024, 'Manchester City', 'https://.free.nf/eq', 5),
(2025, 'Manchester United', '://.', 1),
(2026, 'Crystal Palace', 'https://.free.nf//banfield.png', 2),
(2027, 'West Ham United', 'https://.free.nf/equipos/colon.png', 3),
(2028, 'Wolverhampton Wanderers', 'https:ng', 4),
(2029, 'Newcastle United', 'https://.free.nf/equipos/huracan.png', 5),
(2030, 'AFC Bournemouth', 'https://.png', 1),
(2031, 'Tottenham Hotspur', 'https://nose', 3);

-- EQUIPOS La liga
INSERT INTO equipo (id, nombre, imagen, esquema_id) VALUES
(2032, 'Villarreal', 'https', 3),
(2033, 'Valencia', 'https:', 4),
(2034, 'Sevilla FC', 'https:/', 2),
(2035, 'Real Madrid', 'https://.free.nf//.png', 1),
(2036, 'Atlético Madrid', 'https://..nf//.png', 5),
(2037, 'Barcelona', 'https://.free.nf//.png', 2),
(2038, 'Athletic Club', '://..nf/equipos/.png', 3),
(2039, 'Celta Vigo', 'https:l.png', 4),
(2040, 'Real Sociedad', 'https://.free.nf/eq', 5),
(2041, 'Osasuna', '://.', 1),
(2042, 'Espanyol', 'https://.free.nf//banfield.png', 2),
(2043, 'Getafe', 'https://.free.nf/equipos/colon.png', 3),
(2044, 'Levante', 'https:ng', 4),
(2045, 'Real Betis', 'https://.free.nf/equipos/huracan.png', 5),
(2046, 'Elche', 'https://.png', 1),
(2047, 'Alavés', 'https://nose', 3);








-- EQUIPO_TORNEO para equipos argentinos (nuevos)
INSERT INTO equipo_torneo (id, goles_a_favor, goles_en_contra, partidos_empatados, partidos_ganados, partidos_jugados, partidos_perdidos, posicion, posicion_anterior, puntos, equipo_id, torneo_id) VALUES
(1000, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1000, 1),
(1002, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1002, 1),
(1003, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1003, 1),
(1004, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1004, 1),
(1005, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1005, 1),
(1006, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1006, 1),
(1007, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1007, 1),
(1008, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1008, 1),
(1009, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1009, 1),
(1010, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1010, 1),
(1011, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1011, 1),
(1012, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1012, 1),
(1013, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1013, 1),
(1014, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1014, 1),
(1015, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1015, 1);


-- EQUIPO_TORNEO DE EQUIPOS BRASILEROS (nuevos)
INSERT INTO equipo_torneo (id, goles_a_favor, goles_en_contra, partidos_empatados, partidos_ganados, partidos_jugados, partidos_perdidos, posicion, posicion_anterior, puntos, equipo_id, torneo_id) VALUES
(1016, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2000, 2),
(1017, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2002, 2),
(1018, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2003, 2),
(1019, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2004, 2),
(1020, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2005, 2),
(1021, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2006, 2),
(1022, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2007, 2),
(1023, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2008, 2),
(1024, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2009, 2),
(1025, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2010, 2),
(1026, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2011, 2),
(1027, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2012, 2),
(1028, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2013, 2),
(1029, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2014, 2),
(1030, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2015, 2);



-- EQUIPO_TORNEO DE EQUIPOS INGLESES (nuevos)
INSERT INTO equipo_torneo (id, goles_a_favor, goles_en_contra, partidos_empatados, partidos_ganados, partidos_jugados, partidos_perdidos, posicion, posicion_anterior, puntos, equipo_id, torneo_id) VALUES
(1031, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2016, 3),
(1032, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2017, 3),
(1033, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2018, 3),
(1034, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2019, 3),
(1035, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2020, 3),
(1036, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2021, 3),
(1037, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2023, 3),
(1038, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2024, 3),
(1039, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2025, 3),
(1040, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2026, 3),
(1041, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2027, 3),
(1042, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2028, 3),
(1043, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2029, 3),
(1044, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2030, 3),
(1045, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2031, 3);



-- EQUIPO_TORNEO DE EQUIPOS Españoles (nuevos)
INSERT INTO equipo_torneo (id, goles_a_favor, goles_en_contra, partidos_empatados, partidos_ganados, partidos_jugados, partidos_perdidos, posicion, posicion_anterior, puntos, equipo_id, torneo_id) VALUES
(1046, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2032, 4),
(1047, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2033, 4),
(1048, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2034, 4),
(1049, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2035, 4),
(1050, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2036, 4),
(1051, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2037, 4),
(1052, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2038, 4),
(1053, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2039, 4),
(1054, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2040, 4),
(1055, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2041, 4),
(1056, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2042, 4),
(1057, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2043, 4),
(1058, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2044, 4),
(1059, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2045, 4),
(1060, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2046, 4);







-- INSERT JUGADOR

INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, sobre_id) VALUES
    (1, 'Emiliano', 'Martínez', 'https://futcodejugadores.free.nf/jugadores/emiliano_martinez.png', 31, 1, 88.0, 0, 95.00, 1, 'RARO', 'ARQUERO', NULL),
    (2, 'Cristian', 'Romero', 'https://futcodejugadores.free.nf/jugadores/cristianRomero.png', 26, 23, 87.0, 0, 90.00, 1, 'RARO', 'DEFENSOR', NULL),
    (3, 'Nicolás', 'Otamendi', 'https://futcodejugadores.free.nf/jugadores/otamendi.png', 36, 3, 85.0, 0, 88.00, 1, 'RARO', 'DEFENSOR', NULL),
    (4, 'Marcos', 'Acuña', 'https://futcodejugadores.free.nf/jugadores/acuna.png', 32, 8, 84.0, 0, 87.00, 1, 'RARO', 'DEFENSOR', NULL),
    (5, 'Gonzalo', 'Montiel', 'https://futcodejugadores.free.nf/jugadores/montiel.png', 27, 4, 83.0, 0, 89.00, 1, 'RARO', 'DEFENSOR', NULL),
    (6, 'Alexis', 'Mac Allister', 'https://futcodejugadores.free.nf/jugadores/macalister.png', 25, 5, 85.0, 0, 90.00, 1, 'RARO', 'MEDIOCAMPISTA', NULL),
    (7, 'Enzo', 'Fernández', 'https://futcodejugadores.free.nf/jugadores/enzo.png', 23, 24, 87.0, 0, 92.00, 1, 'RARO', 'MEDIOCAMPISTA', NULL),
    (8, 'Lionel', 'Messi', 'https://tomaszfutcode.free.nf/jugadoresFutcode/messi.png', 36, 10, 94.0, 0, 89.00, 1, 'LEYENDA', 'DELANTERO', NULL),
    (9, 'Julián', 'Álvarez', 'https://futcodejugadores.free.nf/jugadores/julianAlvarez.png', 24, 9, 86.0, 0, 88.00, 1, 'RARO', 'DELANTERO', NULL),
    (10, 'Lautaro', 'Martínez', 'https://futcodejugadores.free.nf/jugadores/lautaro_martinez.png', 26, 21, 88.0, 0, 90.00, 1, 'RARO', 'DELANTERO', NULL),
    (11, 'Cristiano', 'Ronaldo', 'https://tomaszfutcode.free.nf/jugadoresFutcode/cristianoRonaldo.png', 40, 7, 98.2, 0, 97.50, 11, 'LEYENDA', 'DELANTERO', NULL),
    (12, 'Kylian', 'Mbappé', 'https://tomaszfutcode.free.nf/jugadoresFutcode/mbappe.png', 26, 10, 84.3, 0, 98.70, 4, 'EPICO', 'DELANTERO', NULL),
    (13, 'Kevin', 'De Bruyne', 'https://tomaszfutcode.free.nf/jugadoresFutcode/deBruyne.png', 33, 17, 82.7, 0, 96.40, 14, 'EPICO', 'MEDIOCAMPISTA', NULL),
    (14, 'Virgil', 'van Dijk', 'https://tomaszfutcode.free.nf/jugadoresFutcode/vanDijk.png', 33, 4, 47.3, 0, 95.80, 12, 'RARO', 'DEFENSOR', NULL),
    (15, 'Thibaut', 'Courtois', 'https://tomaszfutcode.free.nf/jugadoresFutcode/courtois.png', 33, 1, 49.1, 0, 94.90, 14, 'RARO', 'ARQUERO', NULL),
    (16, 'Robert', 'Lewandowski', 'https://tomaszfutcode.free.nf/jugadoresFutcode/lewandowski.png', 36, 9, 83.6, 0, 98.20, 3, 'EPICO', 'DELANTERO', NULL),
    (17, 'Erling', 'Haaland', 'https://tomaszfutcode.free.nf/jugadoresFutcode/haaland.png', 24, 9, 85.2, 0, 98.80, 5, 'EPICO', 'DELANTERO', NULL),
    (18, 'Neymar', 'Jr', 'https://tomaszfutcode.free.nf/jugadoresFutcode/neymarJr.png', 33, 11, 80.4, 0, 97.00, 2, 'EPICO', 'MEDIOCAMPISTA', NULL),
    (19, 'Luka', 'Modrić', 'https://tomaszfutcode.free.nf/jugadoresFutcode/modric.png', 39, 10, 48.7, 0, 96.70, 3, 'RARO', 'MEDIOCAMPISTA', NULL),
    (20, 'Mohamed', 'Salah', 'https://tomaszfutcode.free.nf/jugadoresFutcode/salah.png', 32, 11, 46.2, 0, 97.20, 7, 'RARO', 'DELANTERO', NULL),
    (21, 'Sadio', 'Mané', 'https://tomaszfutcode.free.nf/jugadoresFutcode/mane.png', 33, 10, 45.8, 0, 96.50, 2, 'RARO', 'DELANTERO', NULL),
    (22, 'Casemiro', 'Carlos', 'https://tomaszfutcode.free.nf/jugadoresFutcode/casemiro.png', 32, 5, 44.3, 0, 95.90, 2, 'RARO', 'MEDIOCAMPISTA', NULL),
    (23, 'Joshua', 'Kimmich', 'https://tomaszfutcode.free.nf/jugadoresFutcode/kimmich.png', 30, 6, 50.0, 0, 96.00, 5, 'RARO', 'MEDIOCAMPISTA', NULL),
    (24, 'Vinícius', 'Jr', 'https://tomaszfutcode.free.nf/jugadoresFutcode/17_Vinícius_Jr.png', 24, 7, 29.1, 0, 97.80, 2, 'NORMAL', 'DELANTERO', NULL),
    (25, 'Bruno', 'Fernandes', 'https://tomaszfutcode.free.nf/jugadoresFutcode/16_Bruno_Fernandes.png', 30, 8, 25.7, 0, 96.10, 7, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (26, 'Marc-André', 'ter Stegen', 'https://tomaszfutcode.free.nf/jugadoresFutcode/18_Marc-André_ter_Stegen.png', 33, 1, 22.4, 0, 95.20, 3, 'NORMAL', 'ARQUERO', NULL),
    (27, 'Alisson', 'Becker', 'https://futcodejugadores.free.nf/jugadores/alisson_becker.png', 32, 1, 24.6, 0, 95.70, 2, 'NORMAL', 'ARQUERO', NULL),
    (28, 'Ederson', 'Moraes', 'https://tomaszfutcode.free.nf/jugadoresFutcode/20_Ederson_Moraes.png', 31, 31, 27.8, 0, 95.40, 2, 'NORMAL', 'ARQUERO', NULL),
    (29, 'Antonio', 'Rüdiger', 'https://futcodejugadores.free.nf/jugadores/antonio_rudiger.png', 31, 2, 23.5, 0, 95.10, 12, 'NORMAL', 'DEFENSOR', NULL),
    (30, 'Marquinhos', 'Marcos', 'https://tomaszfutcode.free.nf/jugadoresFutcode/22_Marquinhos_Marcos.png', 30, 5, 26.3, 0, 95.60, 2, 'NORMAL', 'DEFENSOR', NULL),
    (31, 'João', 'Cancelo', 'https://tomaszfutcode.free.nf/jugadoresFutcode/23_João_Cancelo.png', 30, 3, 21.9, 0, 95.30, 11, 'NORMAL', 'DEFENSOR', NULL),
    (32, 'Trent', 'Alexander-Arnold', 'https://futcodejugadores.free.nf/jugadores/trent_alexander.png', 26, 66, 30.0, 0, 95.00, 7, 'NORMAL', 'DEFENSOR', NULL),
    (33, 'Andrew', 'Robertson', 'https://tomaszfutcode.free.nf/jugadoresFutcode/25_Andrew_Robertson.png', 30, 26, 24.1, 0, 94.80, 7, 'NORMAL', 'DEFENSOR', NULL),
    (34, 'Theo', 'Hernández', 'https://tomaszfutcode.free.nf/jugadoresFutcode/26_Theo_Hernández.png', 28, 19, 28.7, 0, 94.70, 6, 'NORMAL', 'DEFENSOR', NULL),
    (35, 'Achraf', 'Hakimi', 'https://futcodejugadores.free.nf/jugadores/achraf.png', 26, 2, 19.2, 0, 94.60, 2, 'NORMAL', 'DEFENSOR', NULL),
    (36, 'Paul', 'Pogba', 'https://tomaszfutcode.free.nf/jugadoresFutcode/28_Paul_Pogba.png', 31, 6, 26.8, 0, 94.50, 4, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (37, 'Marco', 'Verratti', 'https://tomaszfutcode.free.nf/jugadoresFutcode/29_Marco_Verratti.png', 32, 8, 25.3, 0, 94.40, 6, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (38, 'Son', 'Heung-min', 'https://tomaszfutcode.free.nf/jugadoresFutcode/30_Son_Heung-min.png', 32, 7, 48.1, 0, 97.50, 13, 'RARO', 'DELANTERO', NULL),
    (39, 'Dusan', 'Vlahović', 'https://futcodejugadores.free.nf/jugadores/dusan_vlahovic.png', 24, 9, 22.7, 0, 94.30, 6, 'NORMAL', 'DELANTERO', NULL),
    (40, 'Phil', 'Foden', 'https://futcodejugadores.free.nf/jugadores/phil_foden.png', 24, 47, 22.7, 0, 93.20, 7, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (41, 'Bernardo', 'Silva', 'https://tomaszfutcode.free.nf/jugadoresFutcode/43_Bernardo_Silva.png', 30, 20, 49.3, 0, 96.30, 11, 'RARO', 'MEDIOCAMPISTA', NULL),
    (42, 'Rafael', 'Leão', 'https://futcodejugadores.free.nf/jugadores/LeaoRafael.png', 25, 17, 25.8, 0, 93.10, 11, 'NORMAL', 'DELANTERO', NULL),
    (43, 'Hugo', 'Lloris', 'https://tomaszfutcode.free.nf/jugadoresFutcode/45_Hugo_Lloris.png', 38, 1, 19.4, 0, 93.00, 4, 'NORMAL', 'ARQUERO', NULL),
    (44, 'Keylor', 'Navas', 'https://tomaszfutcode.free.nf/jugadoresFutcode/46_Keylor_Navas.png', 37, 1, 28.6, 0, 92.90, 10, 'NORMAL', 'ARQUERO', NULL),
    (45, 'David', 'de Gea', 'https://tomaszfutcode.free.nf/jugadoresFutcode/47_David_de_Gea.png', 34, 1, 23.5, 0, 92.80, 3, 'NORMAL', 'ARQUERO', NULL),
    (46, 'Mike', 'Maignan', 'https://futcodejugadores.free.nf/jugadores/mike_maignan.png', 29, 16, 26.9, 0, 92.70, 4, 'NORMAL', 'ARQUERO', NULL),
    (47, 'Edin', 'Džeko', 'https://tomaszfutcode.free.nf/jugadoresFutcode/49_Edin_Džeko.png', 38, 9, 21.3, 0, 92.60, 6, 'NORMAL', 'DELANTERO', NULL),
    (49, 'Gabriel', 'Jesus', 'https://futcodejugadores.free.nf/jugadores/Gabriel-Jesus.png', 27, 9, 24.2, 0, 92.40, 2, 'NORMAL', 'DELANTERO', NULL),
    (50, 'Raheem', 'Sterling', 'https://tomaszfutcode.free.nf/jugadoresFutcode/52_Raheem_Sterling.png', 29, 7, 28.8, 0, 92.30, 7, 'NORMAL', 'DELANTERO', NULL),
    (51, 'João', 'Félix', 'https://tomaszfutcode.free.nf/jugadoresFutcode/53_João_Félix.png', 25, 11, 20.4, 0, 92.20, 11, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (52, 'Alejandro', 'Garnacho', 'https://tomaszfutcode.free.nf/jugadoresFutcode/54_Alejandro_Garnacho.png', 21, 17, 29.1, 0, 91.50, 1, 'NORMAL', 'DELANTERO', NULL),
    (53, 'Francisco', 'Trincão', 'https://futcodejugadores.free.nf/jugadores/francisco_trincao.png', 24, 21, 22.6, 0, 91.40, 11, 'NORMAL', 'DELANTERO', NULL),
    (54, 'Ansu', 'Fati', 'https://tomaszfutcode.free.nf/jugadoresFutcode/56_Ansu_Fati.png', 22, 10, 25.3, 0, 91.30, 3, 'NORMAL', 'DELANTERO', NULL),
    (55, 'Rodrygo', 'Goes', 'https://tomaszfutcode.free.nf/jugadoresFutcode/57_Rodrygo_Goes.png', 23, 11, 21.7, 0, 91.20, 2, 'NORMAL', 'DELANTERO', NULL),
    (56, 'Ferran', 'Torres', 'https://tomaszfutcode.free.nf/jugadoresFutcode/58_Ferran_Torres.png', 24, 7, 26.4, 0, 91.10, 3, 'NORMAL', 'DELANTERO', NULL),
    (57, 'Gonçalo', 'Ramos', 'https://futcodejugadores.free.nf/jugadores/goncalo_ramos.png', 23, 9, 23.9, 0, 91.00, 11, 'NORMAL', 'DELANTERO', NULL),
    (58, 'Darwin', 'Núñez', 'https://futcodejugadores.free.nf/jugadores/darwin_nunez.png', 25, 19, 28.1, 0, 92.10, 9, 'NORMAL', 'DELANTERO', NULL),
    (59, 'Sebastien', 'Haller', 'https://tomaszfutcode.free.nf/jugadoresFutcode/61_Sebastien_Haller.png', 30, 9, 19.8, 0, 92.00, 12, 'NORMAL', 'DELANTERO', NULL),
    (60, 'Victor', 'Osimhen', 'https://futcodejugadores.free.nf/jugadores/victor_osimhen.png', 26, 9, 29.5, 0, 91.90, 8, 'NORMAL', 'DELANTERO', NULL),
    (61, 'Dušan', 'Tadić', 'https://tomaszfutcode.free.nf/jugadoresFutcode/63_Dušan_Tadić.png', 35, 10, 22.2, 0, 91.80, 13, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (62, 'Lucas', 'Paquetá', 'https://tomaszfutcode.free.nf/jugadoresFutcode/64_Lucas_Paquetá.png', 27, 11, 26.7, 0, 91.70, 2, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (63, 'James', 'Maddison', 'https://tomaszfutcode.free.nf/jugadoresFutcode/65_James_Maddison.png', 28, 10, 21.4, 0, 91.60, 7, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (64, 'Karim', 'Benzema', 'https://tomaszfutcode.free.nf/jugadoresFutcode/66_Karim_Benzema.png', 37, 9, 95.6, 0, 98.50, 3, 'LEYENDA', 'DELANTERO', NULL),
    (65, 'Harry', 'Kane', 'https://tomaszfutcode.free.nf/jugadoresFutcode/67_Harry_Kane.png', 31, 10, 81.3, 0, 98.00, 7, 'EPICO', 'DELANTERO', NULL),
    (66, 'Antoine', 'Griezmann', 'https://futcodejugadores.free.nf/jugadores/GRIEZMANN.png', 33, 7, 46.9, 0, 97.30, 3, 'RARO', 'MEDIOCAMPISTA', NULL),
    (67, 'Romelu', 'Lukaku', 'https://tomaszfutcode.free.nf/jugadoresFutcode/69_Romelu_Lukaku.png', 31, 9, 43.7, 0, 96.60, 6, 'RARO', 'DELANTERO', NULL),
    (68, 'Paulo', 'Dybala', 'https://tomaszfutcode.free.nf/jugadoresFutcode/70_Paulo_Dybala.png', 30, 21, 45.2, 0, 96.20, 1, 'RARO', 'MEDIOCAMPISTA', NULL),
    (69, 'Jadon', 'Sancho', 'https://tomaszfutcode.free.nf/jugadoresFutcode/71_Jadon_Sancho.png', 24, 25, 23.8, 0, 95.70, 7, 'NORMAL', 'DELANTERO', NULL),
    (70, 'Marcus', 'Rashford', 'https://futcodejugadores.free.nf/jugadores/Marcus-Rashford.png', 26, 10, 27.1, 0, 95.50, 7, 'NORMAL', 'DELANTERO', NULL),
    (71, 'Jack', 'Grealish', 'https://tomaszfutcode.free.nf/jugadoresFutcode/73_Jack_Grealish.png', 28, 7, 22.5, 0, 95.20, 7, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (72, 'Mikel', 'Oyarzabal', 'https://tomaszfutcode.free.nf/jugadoresFutcode/74_Mikel_Oyarzabal.png', 27, 10, 28.7, 0, 94.90, 3, 'NORMAL', 'DELANTERO', NULL),
    (73, 'Wilfried', 'Zaha', 'https://tomaszfutcode.free.nf/jugadoresFutcode/75_Wilfried_Zaha.png', 31, 11, 25.3, 0, 94.60, 13, 'NORMAL', 'DELANTERO', NULL),
    (74, 'Dominic', 'Calvert-Lewin', 'https://tomaszfutcode.free.nf/jugadoresFutcode/calvert.png', 27, 9, 18.9, 0, 94.40, 7, 'NORMAL', 'DELANTERO', NULL),
    (75, 'Tammy', 'Abraham', 'https://futcodejugadores.free.nf/jugadores/tammy_abraham.png', 26, 9, 26.4, 0, 94.20, 7, 'NORMAL', 'DELANTERO', NULL),
    (76, 'Olivier', 'Giroud', 'https://futcodejugadores.free.nf/jugadores/olivier_giroud.png', 37, 9, 24.6, 0, 94.00, 4, 'NORMAL', 'DELANTERO', NULL),
    (77, 'Arkadiusz', 'Milik', 'https://futcodejugadores.free.nf/jugadores/arkadiusz_milik.png', 30, 99, 27.8, 0, 93.80, 6, 'NORMAL', 'DELANTERO', NULL),
    (78, 'Sebastian', 'Haller', 'https://tomaszfutcode.free.nf/jugadoresFutcode/80_Sebastian_Haller.png', 29, 22, 20.2, 0, 93.60, 12, 'NORMAL', 'DELANTERO', NULL),
    (79, 'Ivan', 'Toney', 'https://futcodejugadores.free.nf/jugadores/ivan_toney.png', 28, 17, 29.5, 0, 93.40, 7, 'NORMAL', 'DELANTERO', NULL),
    (80, 'Patrik', 'Schick', 'https://tomaszfutcode.free.nf/jugadoresFutcode/82_Patrik_Schick.png', 28, 14, 23.1, 0, 93.20, 5, 'NORMAL', 'DELANTERO', NULL),
    (81, 'Florian', 'Wirtz', 'https://tomaszfutcode.free.nf/jugadoresFutcode/83_Florian_Wirtz.png', 21, 10, 25.7, 0, 93.00, 5, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (82, 'Nicolò', 'Zaniolo', 'https://futcodejugadores.free.nf/jugadores/Zaniolo.png', 24, 22, 19.3, 0, 92.80, 6, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (83, 'Houssem', 'Aouar', 'https://tomaszfutcode.free.nf/jugadoresFutcode/85_Houssem_Aouar.png', 26, 8, 29.8, 0, 92.60, 4, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (84, 'James', 'Milner', 'https://tomaszfutcode.free.nf/jugadoresFutcode/86_James_Milner.png', 38, 7, 26.4, 0, 92.40, 7, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (85, 'Jordan', 'Henderson', 'https://tomaszfutcode.free.nf/jugadoresFutcode/87_Jordan_Henderson.png', 34, 8, 21.6, 0, 92.20, 7, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (86, 'Youri', 'Tielemans', 'https://futcodejugadores.free.nf/jugadores/youri_tielemans.png', 27, 9, 27.1, 0, 92.00, 14, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (87, 'Renato', 'Sanches', 'https://tomaszfutcode.free.nf/jugadoresFutcode/89_Renato_Sanches.png', 26, 18, 23.7, 0, 91.80, 11, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (88, 'Weston', 'McKennie', 'https://futcodejugadores.free.nf/jugadores/weston_mckennie.png', 25, 16, 28.1, 0, 91.20, 13, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (89, 'Kalvin', 'Phillips', 'https://tomaszfutcode.free.nf/jugadoresFutcode/91_Kalvin_Phillips.png', 28, 15, 26.9, 0, 91.00, 7, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (90, 'Declan', 'Rice', 'https://futcodejugadores.free.nf/jugadores/declan_rice.png', 25, 6, 32.7, 0, 90.80, 7, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (91, 'Wilfred', 'Ndidi', 'https://tomaszfutcode.free.nf/jugadoresFutcode/93_Wilfred_Ndidi.png', 27, 25, 32.5, 0, 90.60, 14, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (92, 'Sergio', 'Busquets', 'https://tomaszfutcode.free.nf/jugadoresFutcode/94_Sergio_Busquets.png', 36, 5, 24.3, 0, 90.40, 3, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (93, 'Rodrigo', 'De Paul', 'https://futcodejugadores.free.nf/jugadores/de_paul.png', 30, 7, 22.1, 0, 90.20, 1, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (94, 'Lucas', 'Torreira', 'https://tomaszfutcode.free.nf/jugadoresFutcode/96_Lucas_Torreira.png', 29, 14, 21.9, 0, 90.00, 9, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (95, 'Leandro', 'Paredes', 'https://tomaszfutcode.free.nf/jugadoresFutcode/97_Leandro_Paredes.png', 30, 8, 51.7, 0, 59.80, 1, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (96, 'Matteo', 'Guendouzi', 'https://futcodejugadores.free.nf/jugadores/guen.png', 27, 16, 46.5, 0, 89.60, 4, 'NORMAL', 'MEDIOCAMPISTA', NULL),
    (97, 'Ryan', 'Gravenberch', 'https://tomaszfutcode.free.nf/jugadoresFutcode/99_Ryan_Gravenberch.png', 22, 11, 23.3, 0, 91.40, 12, 'NORMAL', 'MEDIOCAMPISTA', NULL);



