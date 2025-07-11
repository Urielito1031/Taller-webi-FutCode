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

-- CLUB (depende de pais y estadio)
INSERT INTO club (id, nombre, pais_id, estadio_id, imagen) VALUES
    (1, 'River Plate', 1, 1, 'https://upload.wikimedia.org/wikipedia/en/3/3f/Club_Atl%C3%A9tico_River_Plate_logo.svg'),
    (2, 'Flamengo', 2, 2, 'https://upload.wikimedia.org/wikipedia/en/5/5c/CR_Flamengo_logo.svg'),
    (3, 'FC Barcelona', 3, 3, 'https://upload.wikimedia.org/wikipedia/en/4/47/FC_Barcelona_%28crest%29.svg'),
    (4, 'Bayern Múnich', 5, 4, 'https://upload.wikimedia.org/wikipedia/en/1/1f/FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg'),
    (5, 'AC Milan', 6, 5, 'https://upload.wikimedia.org/wikipedia/en/d/d0/AC_Milan_logo.svg'),
    (6, 'Manchester United', 7, 6, 'https://upload.wikimedia.org/wikipedia/en/7/7a/Manchester_United_FC_crest.svg'),
    (7, 'Liverpool FC', 7, 7, 'https://upload.wikimedia.org/wikipedia/en/0/0c/Liverpool_FC.svg'),
    (8, 'Paris Saint-Germain', 4, 8, 'https://upload.wikimedia.org/wikipedia/en/a/a7/Paris_Saint-Germain_F.C..svg'),
    (9, 'Juventus', 6, 9, 'https://upload.wikimedia.org/wikipedia/en/d/d2/Juventus_FC_2017_logo.svg'),
    (10, 'Chelsea FC', 7, 10, 'https://upload.wikimedia.org/wikipedia/en/c/cc/Chelsea_FC.svg'),
    (11, 'Club América', 8, 11, 'https://upload.wikimedia.org/wikipedia/en/1/1c/Club_América_logo_%282013%29.svg'),
    (12, 'Peñarol', 9, 12, 'https://upload.wikimedia.org/wikipedia/commons/8/8b/Escudo_Actual_Pe%C3%B1arol.png'),
    (13, 'Colo-Colo', 10, 13, 'https://upload.wikimedia.org/wikipedia/en/4/45/Colo-Colo_logo.svg'),
    (14, 'Atlético de Madrid', 3, 14, 'https://upload.wikimedia.org/wikipedia/en/f/f4/Atletico_Madrid_2017_logo.svg'),
    (15, 'Borussia Dortmund', 5, 15, 'https://upload.wikimedia.org/wikipedia/commons/6/67/Borussia_Dortmund_logo.svg'),
    (16, 'SL Benfica', 11, 16, 'https://upload.wikimedia.org/wikipedia/en/8/89/Sport_Lisboa_e_Benfica_logo.svg'),
    (17, 'Ajax', 12, 17, 'https://upload.wikimedia.org/wikipedia/en/7/79/Ajax_Amsterdam.svg'),
    (18, 'Atlanta United', 13, 18, 'https://upload.wikimedia.org/wikipedia/en/f/f3/Atlanta_United_FC_logo.svg'),
    (19, 'Celtic FC', 14, 19, 'https://upload.wikimedia.org/wikipedia/en/1/11/Celtic_FC.svg'),
    (20, 'FC Porto', 11, 21, 'https://upload.wikimedia.org/wikipedia/en/f/f1/FC_Porto.svg'),
    (21, 'PSV Eindhoven', 12, 22, 'https://upload.wikimedia.org/wikipedia/en/5/55/PSV_Eindhoven.svg'),
    (22, 'New York City FC', 13, 23, 'https://upload.wikimedia.org/wikipedia/en/2/2d/New_York_City_FC.svg'),
    (23, 'Rangers FC', 14, 24, 'https://upload.wikimedia.org/wikipedia/en/0/0c/Rangers_FC.svg');

-- TORNEO (depende de formato_torneo)
INSERT INTO torneo (id, nombre, descripcion, formato_torneo_id, estado, torneo_copa_id, torneo_liga_id) VALUES
    (1, 'Liga Premier', '¡Comienza tu carrera en el fútbol local! En esta liga sencilla, cada equipo se enfrentará al resto una única vez. Asciende posiciones con cada victoria y demuestra quién manda en el barrio. ¡Hay 20.000 monedas en juego para el campeón!', 2, 'ABIERTO', NULL, NULL),
    (2, 'Desafio semiprofesional', 'Un torneo ideal para forjar rivalidades y amistades. Los equipos jugarán todos contra todos a doble partido (ida y vuelta), sumando puntos para coronarse como el más regular. Prepárate para la gloria y un bote de 15.000 monedas', 2, 'ABIERTO', NULL, NULL),
    (3, 'Liga Profesional Argentina 2025', 'Competencia de fútbol de primera división en Argentina, con 28 equipos luchando por el campeonato nacional.', 2, 'EN_CURSO', NULL, NULL),
    (4, 'Torneo Leyendas', 'La élite regional se da cita aquí. Una competición feroz donde la táctica lo es todo. Los equipos se enfrentarán en un formato de liga a doble partido, luchando punto a punto por la supremacía. El vencedor se llevará a casa 40.000 monedas.', 2, 'ABIERTO', NULL, NULL),
    (5, 'Liga Madero 2025', 'Liga regional en la zona de Buenos Aires, destacando el talento local en un formato de puntos corridos.', 2, 'EN_CURSO', NULL, NULL),
    (6, 'Copa Sudamericana 2025', 'Competencia internacional de clubes sudamericanos, con equipos argentinos buscando gloria continental.', 2, 'FINALIZADO', NULL, NULL),
    (7, 'Liga Interior Argentina 2025', 'Torneo de fútbol para equipos del interior de Argentina, promoviendo el talento fuera de las grandes ciudades.', 2, 'ABIERTO', NULL, NULL);

-- EQUIPO (depende de club y esquema)
INSERT INTO equipo (id, nombre, club_id, esquema_id, usuario_id) VALUES
    (1, 'River Plate Titulares', 1, 1, NULL),
    (2, 'Flamengo Juveniles', 2, 2, NULL),
    (3, 'Barcelona B', 3, 3, NULL),
    (4, 'Bayern Múnich Sub-20', 4, 4, NULL),
    (5, 'AC Milan Primavera', 5, 5, NULL),
    (6, 'Manchester United U21', 6, 4, NULL),
    (7, 'Liverpool Reserves', 7, 1, NULL),
    (8, 'PSG B', 8, 1, NULL),
    (9, 'Juventus U19', 9, 3, NULL),
    (10, 'Chelsea Academy', 10, 2, NULL),
    (11, 'América Titulares', 11, 2, NULL),
    (12, 'Peñarol B', 12, 5, NULL),
    (13, 'Colo-Colo Proyección', 13, 3, NULL),
    (14, 'Atlético Madrid Juveniles', 14, 5, NULL),
    (15, 'Borussia Dortmund II', 15, 4, NULL),
    (16, 'Benfica B', 16, 5, NULL),
    (17, 'Ajax Sub-21', 17, 1, NULL),
    (18, 'Atlanta United B', 18, 1, NULL),
    (19, 'Celtic Youth', 19, 3, NULL),
    (20, 'Anderlecht U21', 20, 5, NULL),
    (21, 'Porto B', 21, 2, NULL),
    (22, 'PSV U19', 22, 4, NULL),
    (23, 'New York City FC II', 23, 5, NULL),
    (24, 'Rangers Academy', 23, 4, NULL),
    (25, 'Club Brugge Juveniles', 21, 1, NULL),
    (26, 'Benfica Legends', 16, 2, NULL),
    (27, 'Ajax Reservas', 17, 2, NULL),
    (28, 'Anderlecht B', 20, 2, NULL),
    (29, 'Celtic Reservas', 19, 3, NULL),
    (30, 'PSV Academy', 22, 2, NULL);

-- USUARIO
INSERT INTO usuario (id, email, password, rol, activo, equipo_id, monedas) VALUES
    (1, 'test@unlam.edu.ar', '$2a$10$gDIBmDa5/.1xdxJMV64qzOh44eIaRNBsqLX/z6UpuL//.EoeXANQS', 'ADMIN', 1, 1, 40000);

-- EQUIPO_TORNEO (depende de equipo y torneo, todos los torneo_id deben ser válidos y no NULL)
INSERT INTO equipo_torneo (id, equipo_id, posicion, partidos_jugados, partidos_ganados, partidos_empatados, partidos_perdidos, goles_a_favor, goles_en_contra, puntos, torneo_id, posicion_anterior) VALUES
    (2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
    (3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
    (4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
    (5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
    (6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
    (7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
    (8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
    (9, 9, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
    (10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
    (11, 11, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
    (12, 12, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
    (13, 13, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
    (14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
    (15, 15, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
    (32, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0),
    (33, 3, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0),
    (34, 4, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0),
    (35, 5, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0),
    (36, 6, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0),
    (37, 7, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0),
    (38, 8, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0),
    (39, 9, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0),
    (40, 10, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0),
    (41, 11, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0),
    (42, 12, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0),
    (43, 13, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0),
    (44, 14, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0),
    (45, 15, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0);

-- El resto de los inserts de equipo_torneo deben ser revisados y completados con torneo_id válido si se agregan más torneos.


-- INSERT JUGADOR

INSERT INTO jugador (id, nombre, apellido, imagen, edad, numero_camiseta, rating, lesionado, estado_fisico, pais_id, rareza_jugador, posicion, equipo_id, sobre_id) VALUES
                                                                                                                                                                                                      (1, 'Emiliano', 'Martínez', 'https://futcodejugadores.free.nf/jugadores/emiliano_martinez.png', 31, 1, 88.0, 0, 95.00, 1, 'RARO', 'ARQUERO', 1, NULL),
                                                                                                                                                                                                      (2, 'Cristian', 'Romero', 'https://futcodejugadores.free.nf/jugadores/cristianRomero.png', 26, 23, 87.0, 0, 90.00, 1, 'RARO', 'DEFENSOR', 1, NULL),
                                                                                                                                                                                                      (3, 'Nicolás', 'Otamendi', 'https://futcodejugadores.free.nf/jugadores/otamendi.png', 36, 3, 85.0, 0, 88.00, 1, 'RARO', 'DEFENSOR', 1, NULL),
                                                                                                                                                                                                      (4, 'Marcos', 'Acuña', 'https://futcodejugadores.free.nf/jugadores/acuna.png', 32, 8, 84.0, 0, 87.00, 1, 'RARO', 'DEFENSOR', 1, NULL),
                                                                                                                                                                                                      (5, 'Gonzalo', 'Montiel', 'https://futcodejugadores.free.nf/jugadores/montiel.png', 27, 4, 83.0, 0, 89.00, 1, 'RARO', 'DEFENSOR', 1, NULL),
                                                                                                                                                                                                      (6, 'Alexis', 'Mac Allister', 'https://futcodejugadores.free.nf/jugadores/macalister.png', 25, 5, 85.0, 0, 90.00, 1, 'RARO', 'MEDIOCAMPISTA', 1, NULL),
                                                                                                                                                                                                      (7, 'Enzo', 'Fernández', 'https://futcodejugadores.free.nf/jugadores/enzo.png', 23, 24, 87.0, 0, 92.00, 1, 'RARO', 'MEDIOCAMPISTA', 1, NULL),
                                                                                                                                                                                                      (8, 'Lionel', 'Messi', 'https://tomaszfutcode.free.nf/jugadoresFutcode/messi.png', 36, 10, 94.0, 0, 89.00, 1, 'LEYENDA', 'DELANTERO', 1, NULL),
                                                                                                                                                                                                      (9, 'Julián', 'Álvarez', 'https://futcodejugadores.free.nf/jugadores/julianAlvarez.png', 24, 9, 86.0, 0, 88.00, 1, 'RARO', 'DELANTERO', 1, NULL),
                                                                                                                                                                                                      (10, 'Lautaro', 'Martínez', 'https://futcodejugadores.free.nf/jugadores/lautaro_martinez.png', 26, 21, 88.0, 0, 90.00, 1, 'RARO', 'DELANTERO', 1, NULL),
                                                                                                                                                                                                      (11, 'Cristiano', 'Ronaldo', 'https://tomaszfutcode.free.nf/jugadoresFutcode/cristianoRonaldo.png', 40, 7, 98.2, 0, 97.50, 11, 'LEYENDA', 'DELANTERO', 1, NULL),
                                                                                                                                                                                                      (12, 'Kylian', 'Mbappé', 'https://tomaszfutcode.free.nf/jugadoresFutcode/mbappe.png', 26, 10, 84.3, 0, 98.70, 4, 'EPICO', 'DELANTERO', 1, NULL),
                                                                                                                                                                                                      (13, 'Kevin', 'De Bruyne', 'https://tomaszfutcode.free.nf/jugadoresFutcode/deBruyne.png', 33, 17, 82.7, 0, 96.40, 14, 'EPICO', 'MEDIOCAMPISTA', 1, NULL),
                                                                                                                                                                                                      (14, 'Virgil', 'van Dijk', 'https://tomaszfutcode.free.nf/jugadoresFutcode/vanDijk.png', 33, 4, 47.3, 0, 95.80, 12, 'RARO', 'DEFENSOR', 1, NULL),
                                                                                                                                                                                                      (15, 'Thibaut', 'Courtois', 'https://tomaszfutcode.free.nf/jugadoresFutcode/courtois.png', 33, 1, 49.1, 0, 94.90, 14, 'RARO', 'ARQUERO', 1, NULL),
                                                                                                                                                                                                      (16, 'Robert', 'Lewandowski', 'https://tomaszfutcode.free.nf/jugadoresFutcode/lewandowski.png', 36, 9, 83.6, 0, 98.20, 3, 'EPICO', 'DELANTERO', 1, NULL),
                                                                                                                                                                                                      (17, 'Erling', 'Haaland', 'https://tomaszfutcode.free.nf/jugadoresFutcode/haaland.png', 24, 9, 85.2, 0, 98.80, 5, 'EPICO', 'DELANTERO', 1, NULL),
                                                                                                                                                                                                      (18, 'Neymar', 'Jr', 'https://tomaszfutcode.free.nf/jugadoresFutcode/neymarJr.png', 33, 11, 80.4, 0, 97.00, 2, 'EPICO', 'MEDIOCAMPISTA', 1, NULL),
                                                                                                                                                                                                      (19, 'Luka', 'Modrić', 'https://tomaszfutcode.free.nf/jugadoresFutcode/modric.png', 39, 10, 48.7, 0, 96.70, 3, 'RARO', 'MEDIOCAMPISTA', 1, NULL),
                                                                                                                                                                                                      (20, 'Mohamed', 'Salah', 'https://tomaszfutcode.free.nf/jugadoresFutcode/salah.png', 32, 11, 46.2, 0, 97.20, 7, 'RARO', 'DELANTERO', 1, NULL),
                                                                                                                                                                                                      (21, 'Sadio', 'Mané', 'https://tomaszfutcode.free.nf/jugadoresFutcode/mane.png', 33, 10, 45.8, 0, 96.50, 2, 'RARO', 'DELANTERO', 1, NULL),
                                                                                                                                                                                                      (22, 'Casemiro', 'Carlos', 'https://tomaszfutcode.free.nf/jugadoresFutcode/casemiro.png', 32, 5, 44.3, 0, 95.90, 2, 'RARO', 'MEDIOCAMPISTA', 3, NULL),
                                                                                                                                                                                                      (23, 'Joshua', 'Kimmich', 'https://tomaszfutcode.free.nf/jugadoresFutcode/kimmich.png', 30, 6, 50.0, 0, 96.00, 5, 'RARO', 'MEDIOCAMPISTA', 4, NULL),
                                                                                                                                                                                                      (24, 'Vinícius', 'Jr', 'https://tomaszfutcode.free.nf/jugadoresFutcode/17_Vinícius_Jr.png', 24, 7, 29.1, 0, 97.80, 2, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (25, 'Bruno', 'Fernandes', 'https://tomaszfutcode.free.nf/jugadoresFutcode/16_Bruno_Fernandes.png', 30, 8, 25.7, 0, 96.10, 7, 'NORMAL', 'MEDIOCAMPISTA', NULL, NULL),
                                                                                                                                                                                                      (26, 'Marc-André', 'ter Stegen', 'https://tomaszfutcode.free.nf/jugadoresFutcode/18_Marc-André_ter_Stegen.png', 33, 1, 22.4, 0, 95.20, 3, 'NORMAL', 'ARQUERO', NULL, NULL),
                                                                                                                                                                                                      (27, 'Alisson', 'Becker', 'https://futcodejugadores.free.nf/jugadores/alisson_becker.png', 32, 1, 24.6, 0, 95.70, 2, 'NORMAL', 'ARQUERO', NULL, NULL),
                                                                                                                                                                                                      (28, 'Ederson', 'Moraes', 'https://tomaszfutcode.free.nf/jugadoresFutcode/20_Ederson_Moraes.png', 31, 31, 27.8, 0, 95.40, 2, 'NORMAL', 'ARQUERO', 8, NULL),
                                                                                                                                                                                                      (29, 'Antonio', 'Rüdiger', 'https://futcodejugadores.free.nf/jugadores/antonio_rudiger.png', 31, 2, 23.5, 0, 95.10, 12, 'NORMAL', 'DEFENSOR', NULL, NULL),
                                                                                                                                                                                                      (30, 'Marquinhos', 'Marcos', 'https://tomaszfutcode.free.nf/jugadoresFutcode/22_Marquinhos_Marcos.png', 30, 5, 26.3, 0, 95.60, 2, 'NORMAL', 'DEFENSOR', NULL, NULL),
                                                                                                                                                                                                      (31, 'João', 'Cancelo', 'https://tomaszfutcode.free.nf/jugadoresFutcode/23_João_Cancelo.png', 30, 3, 21.9, 0, 95.30, 11, 'NORMAL', 'DEFENSOR', NULL, NULL),
                                                                                                                                                                                                      (32, 'Trent', 'Alexander-Arnold', 'https://futcodejugadores.free.nf/jugadores/trent_alexander.png', 26, 66, 30.0, 0, 95.00, 7, 'NORMAL', 'DEFENSOR', NULL, NULL),
                                                                                                                                                                                                      (33, 'Andrew', 'Robertson', 'https://tomaszfutcode.free.nf/jugadoresFutcode/25_Andrew_Robertson.png', 30, 26, 24.1, 0, 94.80, 7, 'NORMAL', 'DEFENSOR', NULL, NULL),
                                                                                                                                                                                                      (34, 'Theo', 'Hernández', 'https://tomaszfutcode.free.nf/jugadoresFutcode/26_Theo_Hernández.png', 28, 19, 28.7, 0, 94.70, 6, 'NORMAL', 'DEFENSOR', NULL, NULL),
                                                                                                                                                                                                      (35, 'Achraf', 'Hakimi', 'https://futcodejugadores.free.nf/jugadores/achraf.png', 26, 2, 19.2, 0, 94.60, 2, 'NORMAL', 'DEFENSOR', NULL, NULL),
                                                                                                                                                                                                      (36, 'Paul', 'Pogba', 'https://tomaszfutcode.free.nf/jugadoresFutcode/28_Paul_Pogba.png', 31, 6, 26.8, 0, 94.50, 4, 'NORMAL', 'MEDIOCAMPISTA', 7, NULL),
                                                                                                                                                                                                      (37, 'Marco', 'Verratti', 'https://tomaszfutcode.free.nf/jugadoresFutcode/29_Marco_Verratti.png', 32, 8, 25.3, 0, 94.40, 6, 'NORMAL', 'MEDIOCAMPISTA', 8, NULL),
                                                                                                                                                                                                      (38, 'Son', 'Heung-min', 'https://tomaszfutcode.free.nf/jugadoresFutcode/30_Son_Heung-min.png', 32, 7, 48.1, 0, 97.50, 13, 'RARO', 'DELANTERO', 9, NULL),
                                                                                                                                                                                                      (39, 'Dusan', 'Vlahović', 'https://futcodejugadores.free.nf/jugadores/dusan_vlahovic.png', 24, 9, 22.7, 0, 94.30, 6, 'NORMAL', 'DELANTERO', 10, NULL),
                                                                                                                                                                                                      (40, 'Phil', 'Foden', 'https://futcodejugadores.free.nf/jugadores/phil_foden.png', 24, 47, 22.7, 0, 93.20, 7, 'NORMAL', 'MEDIOCAMPISTA', 2, NULL),
                                                                                                                                                                                                      (41, 'Bernardo', 'Silva', 'https://tomaszfutcode.free.nf/jugadoresFutcode/43_Bernardo_Silva.png', 30, 20, 49.3, 0, 96.30, 11, 'RARO', 'MEDIOCAMPISTA', 3, NULL),
                                                                                                                                                                                                      (42, 'Rafael', 'Leão', 'https://futcodejugadores.free.nf/jugadores/LeaoRafael.png', 25, 17, 25.8, 0, 93.10, 11, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (43, 'Hugo', 'Lloris', 'https://tomaszfutcode.free.nf/jugadoresFutcode/45_Hugo_Lloris.png', 38, 1, 19.4, 0, 93.00, 4, 'NORMAL', 'ARQUERO', 5, NULL),
                                                                                                                                                                                                      (44, 'Keylor', 'Navas', 'https://tomaszfutcode.free.nf/jugadoresFutcode/46_Keylor_Navas.png', 37, 1, 28.6, 0, 92.90, 10, 'NORMAL', 'ARQUERO', NULL, NULL),
                                                                                                                                                                                                      (45, 'David', 'de Gea', 'https://tomaszfutcode.free.nf/jugadoresFutcode/47_David_de_Gea.png', 34, 1, 23.5, 0, 92.80, 3, 'NORMAL', 'ARQUERO', 7, NULL),
                                                                                                                                                                                                      (46, 'Mike', 'Maignan', 'https://futcodejugadores.free.nf/jugadores/mike_maignan.png', 29, 16, 26.9, 0, 92.70, 4, 'NORMAL', 'ARQUERO', NULL, NULL),
                                                                                                                                                                                                      (47, 'Edin', 'Džeko', 'https://tomaszfutcode.free.nf/jugadoresFutcode/49_Edin_Džeko.png', 38, 9, 21.3, 0, 92.60, 6, 'NORMAL', 'DELANTERO', 9, NULL),
                                                                                                                                                                                                      (49, 'Gabriel', 'Jesus', 'https://futcodejugadores.free.nf/jugadores/Gabriel-Jesus.png', 27, 9, 24.2, 0, 92.40, 2, 'NORMAL', 'DELANTERO', 2, NULL),
                                                                                                                                                                                                      (50, 'Raheem', 'Sterling', 'https://tomaszfutcode.free.nf/jugadoresFutcode/52_Raheem_Sterling.png', 29, 7, 28.8, 0, 92.30, 7, 'NORMAL', 'DELANTERO', 3, NULL),
                                                                                                                                                                                                      (51, 'João', 'Félix', 'https://tomaszfutcode.free.nf/jugadoresFutcode/53_João_Félix.png', 25, 11, 20.4, 0, 92.20, 11, 'NORMAL', 'MEDIOCAMPISTA', NULL, NULL),
                                                                                                                                                                                                      (52, 'Alejandro', 'Garnacho', 'https://tomaszfutcode.free.nf/jugadoresFutcode/54_Alejandro_Garnacho.png', 21, 17, 29.1, 0, 91.50, 1, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (53, 'Francisco', 'Trincão', 'https://futcodejugadores.free.nf/jugadores/francisco_trincao.png', 24, 21, 22.6, 0, 91.40, 11, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (54, 'Ansu', 'Fati', 'https://tomaszfutcode.free.nf/jugadoresFutcode/56_Ansu_Fati.png', 22, 10, 25.3, 0, 91.30, 3, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (55, 'Rodrygo', 'Goes', 'https://tomaszfutcode.free.nf/jugadoresFutcode/57_Rodrygo_Goes.png', 23, 11, 21.7, 0, 91.20, 2, 'NORMAL', 'DELANTERO', 8, NULL),
                                                                                                                                                                                                      (56, 'Ferran', 'Torres', 'https://tomaszfutcode.free.nf/jugadoresFutcode/58_Ferran_Torres.png', 24, 7, 26.4, 0, 91.10, 3, 'NORMAL', 'DELANTERO', 9, NULL),
                                                                                                                                                                                                      (57, 'Gonçalo', 'Ramos', 'https://futcodejugadores.free.nf/jugadores/goncalo_ramos.png', 23, 9, 23.9, 0, 91.00, 11, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (58, 'Darwin', 'Núñez', 'https://futcodejugadores.free.nf/jugadores/darwin_nunez.png', 25, 19, 28.1, 0, 92.10, 9, 'NORMAL', 'DELANTERO', 11, NULL),
                                                                                                                                                                                                      (59, 'Sebastien', 'Haller', 'https://tomaszfutcode.free.nf/jugadoresFutcode/61_Sebastien_Haller.png', 30, 9, 19.8, 0, 92.00, 12, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (60, 'Victor', 'Osimhen', 'https://futcodejugadores.free.nf/jugadores/victor_osimhen.png', 26, 9, 29.5, 0, 91.90, 8, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (61, 'Dušan', 'Tadić', 'https://tomaszfutcode.free.nf/jugadoresFutcode/63_Dušan_Tadić.png', 35, 10, 22.2, 0, 91.80, 13, 'NORMAL', 'MEDIOCAMPISTA', 4, NULL),
                                                                                                                                                                                                      (62, 'Lucas', 'Paquetá', 'https://tomaszfutcode.free.nf/jugadoresFutcode/64_Lucas_Paquetá.png', 27, 11, 26.7, 0, 91.70, 2, 'NORMAL', 'MEDIOCAMPISTA', NULL, NULL),
                                                                                                                                                                                                      (63, 'James', 'Maddison', 'https://tomaszfutcode.free.nf/jugadoresFutcode/65_James_Maddison.png', 28, 10, 21.4, 0, 91.60, 7, 'NORMAL', 'MEDIOCAMPISTA', 6, NULL),
                                                                                                                                                                                                      (64, 'Karim', 'Benzema', 'https://tomaszfutcode.free.nf/jugadoresFutcode/66_Karim_Benzema.png', 37, 9, 95.6, 0, 98.50, 3, 'LEYENDA', 'DELANTERO', 7, NULL),
                                                                                                                                                                                                      (65, 'Harry', 'Kane', 'https://tomaszfutcode.free.nf/jugadoresFutcode/67_Harry_Kane.png', 31, 10, 81.3, 0, 98.00, 7, 'EPICO', 'DELANTERO', 8, NULL),
                                                                                                                                                                                                      (66, 'Antoine', 'Griezmann', 'https://futcodejugadores.free.nf/jugadores/GRIEZMANN.png', 33, 7, 46.9, 0, 97.30, 3, 'RARO', 'MEDIOCAMPISTA', 9, NULL),
                                                                                                                                                                                                      (67, 'Romelu', 'Lukaku', 'https://tomaszfutcode.free.nf/jugadoresFutcode/69_Romelu_Lukaku.png', 31, 9, 43.7, 0, 96.60, 6, 'RARO', 'DELANTERO', 10, NULL),
                                                                                                                                                                                                      (68, 'Paulo', 'Dybala', 'https://tomaszfutcode.free.nf/jugadoresFutcode/70_Paulo_Dybala.png', 30, 21, 45.2, 0, 96.20, 1, 'RARO', 'MEDIOCAMPISTA', 1, NULL),
                                                                                                                                                                                                      (69, 'Jadon', 'Sancho', 'https://tomaszfutcode.free.nf/jugadoresFutcode/71_Jadon_Sancho.png', 24, 25, 23.8, 0, 95.70, 7, 'NORMAL', 'DELANTERO', 2, NULL),
                                                                                                                                                                                                      (70, 'Marcus', 'Rashford', 'https://futcodejugadores.free.nf/jugadores/Marcus-Rashford.png', 26, 10, 27.1, 0, 95.50, 7, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (71, 'Jack', 'Grealish', 'https://tomaszfutcode.free.nf/jugadoresFutcode/73_Jack_Grealish.png', 28, 7, 22.5, 0, 95.20, 7, 'NORMAL', 'MEDIOCAMPISTA', NULL, NULL),
                                                                                                                                                                                                      (72, 'Mikel', 'Oyarzabal', 'https://tomaszfutcode.free.nf/jugadoresFutcode/74_Mikel_Oyarzabal.png', 27, 10, 28.7, 0, 94.90, 3, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (73, 'Wilfried', 'Zaha', 'https://tomaszfutcode.free.nf/jugadoresFutcode/75_Wilfried_Zaha.png', 31, 11, 25.3, 0, 94.60, 13, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (74, 'Dominic', 'Calvert-Lewin', 'https://tomaszfutcode.free.nf/jugadoresFutcode/calvert.png', 27, 9, 18.9, 0, 94.40, 7, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (75, 'Tammy', 'Abraham', 'https://futcodejugadores.free.nf/jugadores/tammy_abraham.png', 26, 9, 26.4, 0, 94.20, 7, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (76, 'Olivier', 'Giroud', 'https://futcodejugadores.free.nf/jugadores/olivier_giroud.png', 37, 9, 24.6, 0, 94.00, 4, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (77, 'Arkadiusz', 'Milik', 'https://futcodejugadores.free.nf/jugadores/arkadiusz_milik.png', 30, 99, 27.8, 0, 93.80, 6, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (78, 'Sebastian', 'Haller', 'https://tomaszfutcode.free.nf/jugadoresFutcode/80_Sebastian_Haller.png', 29, 22, 20.2, 0, 93.60, 12, 'NORMAL', 'DELANTERO', 11, NULL),
                                                                                                                                                                                                      (79, 'Ivan', 'Toney', 'https://futcodejugadores.free.nf/jugadores/ivan_toney.png', 28, 17, 29.5, 0, 93.40, 7, 'NORMAL', 'DELANTERO', 2, NULL),
                                                                                                                                                                                                      (80, 'Patrik', 'Schick', 'https://tomaszfutcode.free.nf/jugadoresFutcode/82_Patrik_Schick.png', 28, 14, 23.1, 0, 93.20, 5, 'NORMAL', 'DELANTERO', NULL, NULL),
                                                                                                                                                                                                      (81, 'Florian', 'Wirtz', 'https://tomaszfutcode.free.nf/jugadoresFutcode/83_Florian_Wirtz.png', 21, 10, 25.7, 0, 93.00, 5, 'NORMAL', 'MEDIOCAMPISTA', 4, NULL),
                                                                                                                                                                                                      (82, 'Nicolò', 'Zaniolo', 'https://futcodejugadores.free.nf/jugadores/Zaniolo.png', 24, 22, 19.3, 0, 92.80, 6, 'NORMAL', 'MEDIOCAMPISTA', NULL, NULL),
                                                                                                                                                                                                      (83, 'Houssem', 'Aouar', 'https://tomaszfutcode.free.nf/jugadoresFutcode/85_Houssem_Aouar.png', 26, 8, 29.8, 0, 92.60, 4, 'NORMAL', 'MEDIOCAMPISTA', NULL, NULL),
                                                                                                                                                                                                      (84, 'James', 'Milner', 'https://tomaszfutcode.free.nf/jugadoresFutcode/86_James_Milner.png', 38, 7, 26.4, 0, 92.40, 7, 'NORMAL', 'MEDIOCAMPISTA', 7, NULL),
                                                                                                                                                                                                      (85, 'Jordan', 'Henderson', 'https://tomaszfutcode.free.nf/jugadoresFutcode/87_Jordan_Henderson.png', 34, 8, 21.6, 0, 92.20, 7, 'NORMAL', 'MEDIOCAMPISTA', 8, NULL),
                                                                                                                                                                                                      (86, 'Youri', 'Tielemans', 'https://futcodejugadores.free.nf/jugadores/youri_tielemans.png', 27, 9, 27.1, 0, 92.00, 14, 'NORMAL', 'MEDIOCAMPISTA', NULL, NULL),
                                                                                                                                                                                                      (87, 'Renato', 'Sanches', 'https://tomaszfutcode.free.nf/jugadoresFutcode/89_Renato_Sanches.png', 26, 18, 23.7, 0, 91.80, 11, 'NORMAL', 'MEDIOCAMPISTA', NULL, NULL),
                                                                                                                                                                                                      (88, 'Weston', 'McKennie', 'https://futcodejugadores.free.nf/jugadores/weston_mckennie.png', 25, 16, 28.1, 0, 91.20, 13, 'NORMAL', 'MEDIOCAMPISTA', NULL, NULL),
                                                                                                                                                                                                      (89, 'Kalvin', 'Phillips', 'https://tomaszfutcode.free.nf/jugadoresFutcode/91_Kalvin_Phillips.png', 28, 15, 26.9, 0, 91.00, 7, 'NORMAL', 'MEDIOCAMPISTA', 12, NULL),
                                                                                                                                                                                                      (90, 'Declan', 'Rice', 'https://futcodejugadores.free.nf/jugadores/declan_rice.png', 25, 6, 32.7, 0, 90.80, 7, 'NORMAL', 'MEDIOCAMPISTA', NULL, NULL),
                                                                                                                                                                                                      (91, 'Wilfred', 'Ndidi', 'https://tomaszfutcode.free.nf/jugadoresFutcode/93_Wilfred_Ndidi.png', 27, 25, 32.5, 0, 90.60, 14, 'NORMAL', 'MEDIOCAMPISTA', NULL, NULL),
                                                                                                                                                                                                      (92, 'Sergio', 'Busquets', 'https://tomaszfutcode.free.nf/jugadoresFutcode/94_Sergio_Busquets.png', 36, 5, 24.3, 0, 90.40, 3, 'NORMAL', 'MEDIOCAMPISTA', 3, NULL),
                                                                                                                                                                                                      (93, 'Rodrigo', 'De Paul', 'https://futcodejugadores.free.nf/jugadores/de_paul.png', 30, 7, 22.1, 0, 90.20, 1, 'NORMAL', 'MEDIOCAMPISTA', 4, NULL),
                                                                                                                                                                                                      (94, 'Lucas', 'Torreira', 'https://tomaszfutcode.free.nf/jugadoresFutcode/96_Lucas_Torreira.png', 29, 14, 21.9, 0, 90.00, 9, 'NORMAL', 'MEDIOCAMPISTA', 5, NULL),
                                                                                                                                                                                                      (95, 'Leandro', 'Paredes', 'https://tomaszfutcode.free.nf/jugadoresFutcode/97_Leandro_Paredes.png', 30, 8, 51.7, 0, 59.80, 1, 'NORMAL', 'MEDIOCAMPISTA', NULL, NULL),
                                                                                                                                                                                                      (96, 'Matteo', 'Guendouzi', 'https://futcodejugadores.free.nf/jugadores/guen.png', 27, 16, 46.5, 0, 89.60, 4, 'NORMAL', 'MEDIOCAMPISTA', 7, NULL),
                                                                                                                                                                                                      (97, 'Ryan', 'Gravenberch', 'https://tomaszfutcode.free.nf/jugadoresFutcode/99_Ryan_Gravenberch.png', 22, 11, 23.3, 0, 91.40, 12, 'NORMAL', 'MEDIOCAMPISTA', NULL, NULL);


-- INSERT FORMACION_EQUIPO
INSERT INTO formacion_equipo (id, equipo_id, jugador_id, posicion_en_campo) VALUES
                                                                                          (1670, 1, 1, 'ARQUERO'),
                                                                                          (1671, 1, 2, 'DEFENSOR'),
                                                                                          (1672, 1, 3, 'DEFENSOR'),
                                                                                          (1673, 1, 4, 'DEFENSOR'),
                                                                                          (1674, 1, 6, 'MEDIOCAMPISTA'),
                                                                                          (1675, 1, 10, 'MEDIOCAMPISTA'),
                                                                                          (1676, 1, 15, 'MEDIOCAMPISTA'),
                                                                                          (1677, 1, 17, 'MEDIOCAMPISTA'),
                                                                                          (1678, 1, 20, 'DELANTERO'),
                                                                                          (1679, 1, 21, 'DELANTERO'),
                                                                                          (1680, 1, 68, 'DELANTERO');


-- INSERT SOBRE
INSERT INTO sobre (id, tipo_sobre, titulo, precio, descripcion, imagen_url, usuario_id) VALUES
                                                                                                            (13, 'BRONCE', 'Sobre de Bronce', 2500.00, NULL, 'sobreFutCodeBronce.png', 1),
                                                                                                            (14, 'PLATA', 'Sobre de Plata', 5000.00, NULL, 'sobreFutCodePlata.png', 1);

-- ------------------------------------------------
