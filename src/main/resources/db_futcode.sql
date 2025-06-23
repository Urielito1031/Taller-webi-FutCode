-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-06-2025 a las 03:55:40
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;




INSERT INTO `club` (`id`, `nombre`, `pais_id`, `estadio_id`, `imagen`) VALUES
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


INSERT INTO `equipo` (`id`, `nombre`, `club_id`, `esquema_id`) VALUES
(1, 'River Plate Titulares', 1, 1),
(2, 'Flamengo Juveniles', 2, 2),
(3, 'Barcelona B', 3, 3),
(4, 'Bayern Múnich Sub-20', 4, 4),
(5, 'AC Milan Primavera', 5, 5),
(6, 'Manchester United U21', 6, 4),
(7, 'Liverpool Reserves', 7, 1),
(8, 'PSG B', 8, 1),
(9, 'Juventus U19', 9, 3),
(10, 'Chelsea Academy', 10, 2),
(11, 'América Titulares', 11, 2),
(12, 'Peñarol B', 12, 5),
(13, 'Colo-Colo Proyección', 13, 3),
(14, 'Atlético Madrid Juveniles', 14, 5),
(15, 'Borussia Dortmund II', 15, 4),
(16, 'Benfica B', 16, 5),
(17, 'Ajax Sub-21', 17, 1),
(18, 'Atlanta United B', 18, 1),
(19, 'Celtic Youth', 19, 3),
(20, 'Anderlecht U21', 20, 5),
(21, 'Porto B', 21, 2),
(22, 'PSV U19', 22, 4),
(23, 'New York City FC II', 23, 5),
(24, 'Rangers Academy', 23, 4),
(25, 'Club Brugge Juveniles', 21, 1),
(26, 'Benfica Legends', 16, 2),
(27, 'Ajax Reservas', 17, 2),
(28, 'Anderlecht B', 20, 2),
(29, 'Celtic Reservas', 19, 3),
(30, 'PSV Academy', 22, 2);


INSERT INTO `equipo_torneo` (`id`, `equipo_id`, `posicion`, `partidos_jugados`, `partidos_ganados`, `partidos_empatados`, `partidos_perdidos`, `goles_a_favor`, `goles_en_contra`, `puntos`, `torneo_id`) VALUES
(1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(5, 5, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(6, 6, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(7, 7, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(9, 9, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(11, 11, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(12, 12, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(13, 13, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(14, 14, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(15, 15, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(16, 16, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(17, 17, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(18, 18, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(19, 19, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(20, 20, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(21, 21, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(22, 22, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(23, 23, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(24, 24, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(25, 25, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(26, 26, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(27, 27, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(28, 28, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(29, 29, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(30, 30, 0, 0, 0, 0, 0, 0, 0, 0, 1),
(31, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(32, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(33, 3, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(34, 4, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(35, 5, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(36, 6, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(37, 7, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(38, 8, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(39, 9, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(40, 10, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(41, 11, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(42, 12, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(43, 13, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(44, 14, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(45, 15, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(46, 16, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(47, 17, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(48, 18, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(49, 19, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(50, 20, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(51, 21, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(52, 22, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(53, 23, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(54, 24, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(55, 25, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(56, 26, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(57, 27, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(58, 28, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(59, 29, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(60, 30, 0, 0, 0, 0, 0, 0, 0, 0, 2),
(61, 1, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(62, 2, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(63, 3, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(64, 4, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(65, 5, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(66, 6, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(67, 7, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(68, 8, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(69, 9, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(70, 10, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(71, 11, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(72, 12, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(73, 13, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(74, 14, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(75, 15, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(76, 16, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(77, 17, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(78, 18, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(79, 19, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(80, 20, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(81, 21, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(82, 22, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(83, 23, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(84, 24, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(85, 25, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(86, 26, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(87, 27, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(88, 28, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(89, 29, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(90, 30, 0, 0, 0, 0, 0, 0, 0, 0, 4),
(91, 1, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(92, 2, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(93, 3, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(94, 4, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(95, 5, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(96, 6, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(97, 7, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(98, 8, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(99, 9, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(100, 10, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(101, 11, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(102, 12, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(103, 13, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(104, 14, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(105, 15, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(106, 16, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(107, 17, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(108, 18, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(109, 19, 0, 0, 0, 0, 0, 0, 0, 0, 7),
(110, 20, 0, 0, 0, 0, 0, 0, 0, 0, 7);

-- --------------------------------------------------------


INSERT INTO `esquema` (`id`, `esquema`) VALUES
(1, 'CUATRO_TRES_TRES'),
(2, 'CUATRO_CUATRO_DOS'),
(3, 'TRES_CINCO_DOS'),
(4, 'CINCO_TRES_DOS'),
(5, 'TRES_CUATRO_TRES');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estadio`
--

--
-- Volcado de datos para la tabla `estadio`
--

INSERT INTO `estadio` (`id`, `nombre`, `capacidad`, `ubicacion`, `imagen_url`) VALUES
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


INSERT INTO `fase` (`id`, `nombre`) VALUES
(1, 'Fase de grupos'),
(2, 'Octavos de final'),
(3, 'Cuartos de final'),
(4, 'Semifinal'),
(5, 'Final');

-- --------------------------------------------------------


INSERT INTO `formacion_equipo` (`id`, `equipo_id`, `jugador_id`, `posicion_en_campo`) VALUES
(1034, 1, 16, 'ARQUERO'),
(1035, 1, 5, 'DEFENSOR'),
(1036, 1, 15, 'DEFENSOR'),
(1037, 1, 4, 'DEFENSOR'),
(1038, 1, 90, 'DEFENSOR'),
(1039, 1, 19, 'DEFENSOR'),
(1040, 1, 118, 'MEDIOCAMPISTA'),
(1041, 1, 13, 'MEDIOCAMPISTA'),
(1042, 1, 20, 'MEDIOCAMPISTA'),
(1043, 1, 18, 'DELANTERO'),
(1044, 1, 21, 'DELANTERO');

-- --------------------------------------------------------

INSERT INTO `formato_torneo` (`id`, `tipo`, `fase_id`) VALUES
(1, 'COPA', NULL),
(2, 'LIGA', NULL);


INSERT INTO `jugador` (`id`, `nombre`, `apellido`, `imagen`, `edad`, `numero_camiseta`, `rating`, `lesionado`, `estado_fisico`, `pais_id`, `rareza_jugador`, `posicion`, `equipo_id`) VALUES
(1, 'Emiliano', 'Martínez', 'emiliano_martinez.png', 31, 1, 88.0, 0, 95.00, 1, 'RARO', 'ARQUERO', 1),
(2, 'Cristian', 'Romero', 'cristian_romero.png', 26, 23, 87.0, 0, 90.00, 1, 'RARO', 'DEFENSOR', 1),
(3, 'Nicolás', 'Otamendi', 'nicolas_otamendi.png', 36, 3, 85.0, 0, 88.00, 1, 'RARO', 'DEFENSOR', 1),
(4, 'Marcos', 'Acuña', 'marcos_acuna.png', 32, 8, 84.0, 0, 87.00, 1, 'RARO', 'DEFENSOR', 1),
(5, 'Gonzalo', 'Montiel', 'gonzalo_montiel.png', 27, 4, 83.0, 0, 89.00, 1, 'RARO', 'DEFENSOR', 1),
(6, 'Rodrigo', 'De Paul', 'rodrigo_depaul.png', 29, 16, 86.0, 0, 91.00, 1, 'RARO', 'MEDIOCAMPISTA', 1),
(7, 'Alexis', 'Mac Allister', 'alexis_macallister.png', 25, 5, 85.0, 0, 90.00, 1, 'RARO', 'MEDIOCAMPISTA', 1),
(8, 'Enzo', 'Fernández', 'enzo_fernandez.png', 23, 24, 87.0, 0, 92.00, 1, 'RARO', 'MEDIOCAMPISTA', 1),
(9, 'Lionel', 'Messi', 'lionel_messi.png', 36, 10, 94.0, 0, 89.00, 1, 'LEYENDA', 'DELANTERO', 1),
(10, 'Julián', 'Álvarez', 'julian_alvarez.png', 24, 9, 86.0, 0, 88.00, 1, 'RARO', 'DELANTERO', 1),
(11, 'Lautaro', 'Martínez', 'lautaro_martinez.png', 26, 21, 88.0, 0, 90.00, 1, 'RARO', 'DELANTERO', 1),
(12, 'Cristiano', 'Ronaldo', 'cristiano_ronaldo.png', 40, 7, 98.2, 0, 97.50, 11, 'LEYENDA', 'DELANTERO', 1),
(13, 'Kylian', 'Mbappé', 'mbappe.png', 26, 10, 84.3, 0, 98.70, 4, 'EPICO', 'DELANTERO', 1),
(14, 'Kevin', 'De Bruyne', 'de_bruyne.png', 33, 17, 82.7, 0, 96.40, 14, 'EPICO', 'MEDIOCAMPISTA', 1),
(15, 'Virgil', 'van Dijk', 'van_dijk.png', 33, 4, 47.3, 0, 95.80, 12, 'RARO', 'DEFENSOR', 1),
(16, 'Thibaut', 'Courtois', 'courtois.png', 33, 1, 49.1, 0, 94.90, 14, 'RARO', 'ARQUERO', 1),
(17, 'Robert', 'Lewandowski', 'lewandoswski.png', 36, 9, 83.6, 0, 98.20, 3, 'EPICO', 'DELANTERO', 1),
(18, 'Erling', 'Haaland', 'haaland.png', 24, 9, 85.2, 0, 98.80, 5, 'EPICO', 'DELANTERO', 1),
(19, 'Neymar', 'Jr', 'neymar.png', 33, 11, 80.4, 0, 97.00, 2, 'EPICO', 'MEDIOCAMPISTA', 1),
(20, 'Luka', 'Modrić', 'modric.png', 39, 10, 48.7, 0, 96.70, 3, 'RARO', 'MEDIOCAMPISTA', 1),
(21, 'Mohamed', 'Salah', 'salah.png', 32, 11, 46.2, 0, 97.20, 7, 'RARO', 'DELANTERO', 1),
(22, 'Sadio', 'Mané', NULL, 33, 10, 45.8, 0, 96.50, 2, 'RARO', 'DELANTERO', 2),
(23, 'Casemiro', 'Carlos', NULL, 32, 5, 44.3, 0, 95.90, 2, 'RARO', 'MEDIOCAMPISTA', 3),
(24, 'Joshua', 'Kimmich', NULL, 30, 6, 50.0, 0, 96.00, 5, 'RARO', 'MEDIOCAMPISTA', 4),
(25, 'Vinícius', 'Jr', 'https://assets.laliga.com/squad/2024/t186/p246333/1024x1024/p246333_t186_2024_1_003_000.png', 24, 7, 29.1, 0, 97.80, 2, 'NORMAL', 'DELANTERO', 5),
(26, 'Bruno', 'Fernandes', 'https://assets.manutd.com/AssetPicker/images/0/0/20/88/1333344/8-Bruno-Fernandes1719822565555.png', 30, 8, 25.7, 0, 96.10, 7, 'NORMAL', 'MEDIOCAMPISTA', 6),
(27, 'Vinícius', 'Jr', 'https://assets.laliga.com/squad/2024/t186/p246333/1024x1024/p246333_t186_2024_1_003_000.png', 24, 7, 29.1, 0, 97.80, 2, 'NORMAL', 'DELANTERO', 5),
(28, 'Marc-André', 'ter Stegen', 'https://www.fcbarcelona.com/photo-resources/2024/10/13/04c3b761-ccbd-498f-b988-b67ed1693906/01-Ter_Stegen-M.png?width=670&height=790', 33, 1, 22.4, 0, 95.20, 3, 'NORMAL', 'ARQUERO', 6),
(29, 'Alisson', 'Becker', 'https://img.uefa.com/imgml/TP/players/1/2025/cutoff/250099867.webp', 32, 1, 24.6, 0, 95.70, 2, 'NORMAL', 'ARQUERO', 7),
(30, 'Ederson', 'Moraes', 'https://media.futbolfantasy.com/thumb/400x400/v202506010013/uploads/images/jugadores/ficha/2229.png', 31, 31, 27.8, 0, 95.40, 2, 'NORMAL', 'ARQUERO', 8),
(31, 'Antonio', 'Rüdiger', 'https://img.uefa.com/imgml/TP/players/1/2025/cutoff/250028211.webp', 31, 2, 23.5, 0, 95.10, 12, 'NORMAL', 'DEFENSOR', 9),
(32, 'Marquinhos', 'Marcos', 'https://assets.sorare.com/playerpicture/2525a7e8-cfd0-4921-bcc9-f1f162f91751/picture/squared-90979b1e8ac945fed8caf6d24e65d35e.png', 30, 5, 26.3, 0, 95.60, 2, 'NORMAL', 'DEFENSOR', 10),
(33, 'João', 'Cancelo', 'https://static-files.saudi-pro-league.pulselive.com/players/headshot/p121145.png', 30, 3, 21.9, 0, 95.30, 11, 'NORMAL', 'DEFENSOR', 2),
(34, 'Trent', 'Alexander-Arnold', 'https://img.uefa.com/imgml/TP/players/1/2025/cutoff/250076357.webp', 26, 66, 30.0, 0, 95.00, 7, 'NORMAL', 'DEFENSOR', 3),
(35, 'Andrew', 'Robertson', 'https://backend.liverpoolfc.com/sites/default/files/styles/xs/public/2024-06/andy-robertson-body-shot-202425.png?itok=GGyTJcJK', 30, 26, 24.1, 0, 94.80, 7, 'NORMAL', 'DEFENSOR', 4),
(36, 'Theo', 'Hernández', 'https://assets-eu-01.kc-usercontent.com/1293c890-579f-01b7-8480-902cca7de55e/12daf100-2931-4851-a437-a1707ec03a0c/TheoHernandez-Large.png', 28, 19, 28.7, 0, 94.70, 6, 'NORMAL', 'DEFENSOR', 5),
(37, 'Achraf', 'Hakimi', 'https://img.uefa.com/imgml/TP/players/1/2025/cutoff/250088061.webp', 26, 2, 19.2, 0, 94.60, 2, 'NORMAL', 'DEFENSOR', 6),
(38, 'Paul', 'Pogba', 'https://i0.wp.com/metrodailyng.com/wp-content/uploads/2022/05/Paul-Pogba.png?fit=500%2C500&ssl=1', 31, 6, 26.8, 0, 94.50, 4, 'NORMAL', 'MEDIOCAMPISTA', 7),
(39, 'Marco', 'Verratti', 'https://b.fssta.com/uploads/application/soccer/headshots/4065.png', 32, 8, 25.3, 0, 94.40, 6, 'NORMAL', 'MEDIOCAMPISTA', 8),
(40, 'Son', 'Heung-min', 'https://www.tottenhamhotspur.com/media/ds3asinl/firstteam_profiles_heungminson_202425_1.png?anchor=center&mode=crop&quality=90&width=500', 32, 7, 48.1, 0, 97.50, 13, 'RARO', 'DELANTERO', 9),
(41, 'Dusan', 'Vlahović', 'https://i.namu.wiki/i/QF-S5Eo5jYc5iYWL_x6YxMWZU7b4ecQyJKgvzU0l3Oh21JWE4lAKPtv4xWDuzUnFHMpQWVi_kGjrC3gA6ghqEA.webp', 24, 9, 22.7, 0, 94.30, 6, 'NORMAL', 'DELANTERO', 10),
(42, 'Phil', 'Foden', 'https://img.uefa.com/imgml/TP/players/3/2024/cutoff/250101534.webp', 24, 47, 22.7, 0, 93.20, 7, 'NORMAL', 'MEDIOCAMPISTA', 2),
(43, 'Bernardo', 'Silva', 'https://www.pngplay.com/wp-content/uploads/13/Bernardo-Silva-PNG-HD-Quality.png', 30, 20, 49.3, 0, 96.30, 11, 'RARO', 'MEDIOCAMPISTA', 3),
(44, 'Rafael', 'Leão', 'https://img.uefa.com/imgml/TP/players/3/2024/cutoff/250089228.webp', 25, 17, 25.8, 0, 93.10, 11, 'NORMAL', 'DELANTERO', 4),
(45, 'Hugo', 'Lloris', 'https://tottenham24.pl/upload/squad/firstteam_profiles_202223_hugolloris.png', 38, 1, 19.4, 0, 93.00, 4, 'NORMAL', 'ARQUERO', 5),
(46, 'Keylor', 'Navas', 'https://assets.sorare.com/playerpicture/e04945d8-d2da-492d-ae7b-2fdb9ac40ac5/picture/squared-cf67e2f875d52cf6491dab38156c5e54.png', 37, 1, 28.6, 0, 92.90, 10, 'NORMAL', 'ARQUERO', 6),
(47, 'David', 'de Gea', 'https://media.futbolfantasy.com/thumb/400x400/v202504060244/uploads/images/jugadores/ficha/1374.png', 34, 1, 23.5, 0, 92.80, 3, 'NORMAL', 'ARQUERO', 7),
(48, 'Mike', 'Maignan', 'https://img.uefa.com/imgml/TP/players/3/2024/cutoff/250042780.webp', 29, 16, 26.9, 0, 92.70, 4, 'NORMAL', 'ARQUERO', 8),
(49, 'Edin', 'Džeko', 'https://assets.sorare.com/playerpicture/062943c2-f400-410e-b5d6-f58f1b0b3fad/picture/squared-d297ea80c13d63fe6d5ec8f2eda3a5be.png', 38, 9, 21.3, 0, 92.60, 6, 'NORMAL', 'DELANTERO', 9),
(50, 'Memphis', 'Depay', 'https://img-estaticos.atleticodemadrid.com/system/fotos/15047/destacado_600x600/FICHA_WEB_MEMPHIS.png?1674242301', 30, 9, 27.7, 0, 92.50, 12, 'NORMAL', 'DELANTERO', 10),
(51, 'Gabriel', 'Jesus', 'https://i.namu.wiki/i/n0wy-5FNkCnEVfnSiO22Hd2XvdK2ELY79mryUQ5k6H_--bbQXiVfZ4F9DAtkNYBH3WlG6-KVsM6oSsCinshn5Q.webp', 27, 9, 24.2, 0, 92.40, 2, 'NORMAL', 'DELANTERO', 2),
(52, 'Raheem', 'Sterling', 'https://img.chelseafc.com/image/upload/f_auto,h_860,q_50/editorial/people/first-team/2024-25/Sterling_raheem_profile_2024-25_avatar-removebg.png', 29, 7, 28.8, 0, 92.30, 7, 'NORMAL', 'DELANTERO', 3),
(53, 'João', 'Félix', 'https://img-estaticos.atleticodemadrid.com/system/fotos/9648/destacado_600x600/BUSTO_0020_7_JOAO.png?1601656495', 25, 11, 20.4, 0, 92.20, 11, 'NORMAL', 'MEDIOCAMPISTA', 4),
(54, 'Alejandro', 'Garnacho', 'https://assets.sorare.com/playerpicture/492c866b-6304-4276-ae8e-9cd7416a9fb4/picture/squared-aa22c94782e88e5045b011926849f5f8.png', 21, 17, 29.1, 0, 91.50, 1, 'NORMAL', 'DELANTERO', 5),
(55, 'Francisco', 'Trincão', 'https://img.uefa.com/imgml/TP/players/1/2025/cutoff/250112122.webp', 24, 21, 22.6, 0, 91.40, 11, 'NORMAL', 'DELANTERO', 6),
(56, 'Ansu', 'Fati', 'https://www.fcbarcelona.com/photo-resources/2024/10/13/a21d9ed5-ab8a-4705-ae9a-a6bd14e9dbba/10-Ansu_Fati-M.png?width=670&height=790', 22, 10, 25.3, 0, 91.30, 3, 'NORMAL', 'DELANTERO', 7),
(57, 'Rodrygo', 'Goes', 'https://assets.laliga.com/squad/2024/t186/p440077/2048x2225/p440077_t186_2024_1_001_000.png', 23, 11, 21.7, 0, 91.20, 2, 'NORMAL', 'DELANTERO', 8),
(58, 'Ferran', 'Torres', 'https://www.fcbarcelona.com/photo-resources/2024/10/13/1edefda1-8229-4232-ace9-2f35bf4d3000/07-Ferran_Torres-M.png?width=670&height=790', 24, 7, 26.4, 0, 91.10, 3, 'NORMAL', 'DELANTERO', 9),
(59, 'Gonçalo', 'Ramos', 'https://img.uefa.com/imgml/TP/players/1/2025/cutoff/250116654.webp', 23, 9, 23.9, 0, 91.00, 11, 'NORMAL', 'DELANTERO', 10),
(60, 'Darwin', 'Núñez', 'https://backend.liverpoolfc.com/sites/default/files/styles/lg/public/2024-06/darwin-nunez-profile-body-shot-202425.webp?itok=uzKb0DTv', 25, 19, 28.1, 0, 92.10, 9, 'NORMAL', 'DELANTERO', 11),
(81, 'Sebastien', 'Haller', 'https://assets.bundesliga.com/player/dfl-obj-0027ro-dfl-clu-000007-dfl-sea-0001k8.png', 30, 9, 19.8, 0, 92.00, 12, 'NORMAL', 'DELANTERO', 2),
(82, 'Victor', 'Osimhen', 'https://i.namu.wiki/i/lQTUfyqkT53_YJBUZS3-bCqBABmLlkDeXmm_Lm92pvxNTgFxeNMhIBdyHuR6MWlGJ03t1PMiPpZNp4FHOtkOFg.webp', 26, 9, 29.5, 0, 91.90, 8, 'NORMAL', 'DELANTERO', 3),
(83, 'Dušan', 'Tadić', 'https://assets.sorare.com/playerpicture/94d5af28-d7a9-4b22-bacf-7120238c1a47/picture/squared-690163e914fda82349dc8c95a34bb468.png', 35, 10, 22.2, 0, 91.80, 13, 'NORMAL', 'MEDIOCAMPISTA', 4),
(84, 'Lucas', 'Paquetá', 'https://cdn.whufc.com/sites/default/files/2024-08/paquetaprofile.png', 27, 11, 26.7, 0, 91.70, 2, 'NORMAL', 'MEDIOCAMPISTA', 5),
(85, 'James', 'Maddison', 'https://www.tottenhamhotspur.com/media/xstpwh2h/firstteam_profiles_jamesmaddison_202425_1.png?anchor=center&mode=crop&quality=90&width=500', 28, 10, 21.4, 0, 91.60, 7, 'NORMAL', 'MEDIOCAMPISTA', 6),
(86, 'Karim', 'Benzema', 'https://assets.sorare.com/player/9097ab4a-d503-4924-8186-5d09ab121faa/picture/squared-bb7d7ae8fa37b00193fe929f891e0dfd.png', 37, 9, 95.6, 0, 98.50, 3, 'LEYENDA', 'DELANTERO', 7),
(87, 'Harry', 'Kane', 'https://assets.bundesliga.com/player/dfl-obj-j00zz3-dfl-clu-00000g-dfl-sea-0001k8.png', 31, 10, 81.3, 0, 98.00, 7, 'EPICO', 'DELANTERO', 8),
(88, 'Antoine', 'Griezmann', 'https://img.uefa.com/imgml/TP/players/1/2025/cutoff/250019498.webp', 33, 7, 46.9, 0, 97.30, 3, 'RARO', 'MEDIOCAMPISTA', 9),
(89, 'Romelu', 'Lukaku', 'https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/2a0c257d-1e26-439e-8561-e67976a7a2e4/demiybf-545fb1f5-1709-4210-beb9-78af40b7fedf.png', 31, 9, 43.7, 0, 96.60, 6, 'RARO', 'DELANTERO', 10),
(90, 'Paulo', 'Dybala', 'dybala.png', 30, 21, 45.2, 0, 96.20, 1, 'RARO', 'MEDIOCAMPISTA', 1),
(91, 'Jadon', 'Sancho', 'https://img.chelseafc.com/image/upload/f_auto,h_860,q_50/editorial/people/first-team/2024-25/With%20LN/Sancho/Mens_3333x5000_Avatar_Sancho_SF_Home_24_25_RGB.png', 24, 25, 23.8, 0, 95.70, 7, 'NORMAL', 'DELANTERO', 2),
(92, 'Marcus', 'Rashford', 'https://img.uefa.com/imgml/TP/players/1/2025/cutoff/250088246.webp', 26, 10, 27.1, 0, 95.50, 7, 'NORMAL', 'DELANTERO', 3),
(93, 'Jack', 'Grealish', 'https://web.uat.mancity.com/meta/media/qdlbnbg2/jack-grealish.png', 28, 7, 22.5, 0, 95.20, 7, 'NORMAL', 'MEDIOCAMPISTA', 4),
(94, 'Mikel', 'Oyarzabal', 'https://cdn.realsociedad.eus//Uploads/jugadores/_2_oyarzabal.png', 27, 10, 28.7, 0, 94.90, 3, 'NORMAL', 'DELANTERO', 5),
(95, 'Wilfried', 'Zaha', 'https://www.playmakerstats.com/img/jogadores/87/408287_med__20170602084114_wilfried_zaha.png', 31, 11, 25.3, 0, 94.60, 13, 'NORMAL', 'DELANTERO', 6),
(96, 'Dominic', 'Calvert-Lewin', 'https://www.thefa.com/-/media/www-thefa-com/images/england/men-senior/player-profiles/dominic-calvert-lewin/723-dcl.ashx', 27, 9, 18.9, 0, 94.40, 7, 'NORMAL', 'DELANTERO', 7),
(97, 'Tammy', 'Abraham', 'https://img.uefa.com/imgml/TP/players/1/2025/cutoff/250067629.webp', 26, 9, 26.4, 0, 94.20, 7, 'NORMAL', 'DELANTERO', 8),
(98, 'Olivier', 'Giroud', 'https://img.uefa.com/imgml/TP/players/3/2024/cutoff/250020851.webp', 37, 9, 24.6, 0, 94.00, 4, 'NORMAL', 'DELANTERO', 9),
(99, 'Arkadiusz', 'Milik', 'https://store.juventus.com/images/juventus/players/25.webp?v=1728556315', 30, 99, 27.8, 0, 93.80, 6, 'NORMAL', 'DELANTERO', 10),
(100, 'Sebastian', 'Haller', 'https://assets.bundesliga.com/player/dfl-obj-0027ro-dfl-clu-000007-dfl-sea-0001k8.png', 29, 22, 20.2, 0, 93.60, 12, 'NORMAL', 'DELANTERO', 11),
(101, 'Ivan', 'Toney', 'https://img.uefa.com/imgml/TP/players/3/2024/cutoff/250178523.webp', 28, 17, 29.5, 0, 93.40, 7, 'NORMAL', 'DELANTERO', 2),
(102, 'Patrik', 'Schick', 'https://assets.bundesliga.com/player/dfl-obj-002g6u-dfl-clu-00000b-dfl-sea-0001k8-body.png', 28, 14, 23.1, 0, 93.20, 5, 'NORMAL', 'DELANTERO', 3),
(103, 'Florian', 'Wirtz', 'https://assets.bundesliga.com/player/dfl-obj-002gbk-dfl-clu-00000b-dfl-sea-0001k8.png', 21, 10, 25.7, 0, 93.00, 5, 'NORMAL', 'MEDIOCAMPISTA', 4),
(104, 'Nicolò', 'Zaniolo', 'https://img.uefa.com/imgml/TP/players/1/2025/cutoff/250107324.webp', 24, 22, 19.3, 0, 92.80, 6, 'NORMAL', 'MEDIOCAMPISTA', 5),
(105, 'Houssem', 'Aouar', 'https://static-files.saudi-pro-league.pulselive.com/players/headshot/p231961.png', 26, 8, 29.8, 0, 92.60, 4, 'NORMAL', 'MEDIOCAMPISTA', 6),
(106, 'James', 'Milner', 'https://assets.sorare.com/playerpicture/25f4f99f-be41-44f5-b597-37865ca679ca/picture/squared-49bf3fcc17f4e3648d65ab2fea534c27.png', 38, 7, 26.4, 0, 92.40, 7, 'NORMAL', 'MEDIOCAMPISTA', 7),
(107, 'Jordan', 'Henderson', 'https://media02.tr.beinsports.com/img/players/P1786.png', 34, 8, 21.6, 0, 92.20, 7, 'NORMAL', 'MEDIOCAMPISTA', 8),
(108, 'Youri', 'Tielemans', 'https://img.uefa.com/imgml/TP/players/1/2025/cutoff/250064006.webp', 27, 9, 27.1, 0, 92.00, 14, 'NORMAL', 'MEDIOCAMPISTA', 9),
(109, 'Renato', 'Sanches', 'https://www.ligaportugal.pt/backoffice/assets/Corposanches_d929f48e18.png', 26, 18, 23.7, 0, 91.80, 11, 'NORMAL', 'MEDIOCAMPISTA', 10),
(110, 'Weston', 'McKennie', 'https://store.juventus.com/images/juventus/players/16.webp?v=1728556315', 25, 16, 28.1, 0, 91.20, 13, 'NORMAL', 'MEDIOCAMPISTA', 11),
(111, 'Kalvin', 'Phillips', 'https://www.mancity.com/meta/media/cfqhq11u/kalvin-philips.png', 28, 15, 26.9, 0, 91.00, 7, 'NORMAL', 'MEDIOCAMPISTA', 12),
(112, 'Declan', 'Rice', 'https://i.namu.wiki/i/xzfhYOM5m1PPb5Ho7zzWfC4zR7HkL2QT2Q_tyLKSD2JI8UEhi8SGhmJJ4xkuv2rRhbRmJ1EpGqGYtWxtcYAaIQ.webp', 25, 6, 32.7, 0, 90.80, 7, 'NORMAL', 'MEDIOCAMPISTA', 13),
(113, 'Wilfred', 'Ndidi', 'https://www.sportball.es/wp-content/uploads/2020/02/Plantilla-del-Leicester-City-2019-2020-Wilfred-Ndidi.png', 27, 25, 32.5, 0, 90.60, 14, 'NORMAL', 'MEDIOCAMPISTA', 14),
(114, 'Wilfred', 'Ndidi', 'https://www.sportball.es/wp-content/uploads/2020/02/Plantilla-del-Leicester-City-2019-2020-Wilfred-Ndidi.png', 27, 25, 32.5, 0, 90.60, 14, 'NORMAL', 'MEDIOCAMPISTA', 2),
(115, 'Sergio', 'Busquets', 'https://media.futbolfantasy.com/thumb/400x400/v202505312348/uploads/images/jugadores/ficha/88.png', 36, 5, 24.3, 0, 90.40, 3, 'NORMAL', 'MEDIOCAMPISTA', 3),
(116, 'Rodrigo', 'De Paul', 'https://img.uefa.com/imgml/TP/players/1/2025/cutoff/250086037.webp', 30, 7, 22.1, 0, 90.20, 1, 'NORMAL', 'MEDIOCAMPISTA', 4),
(117, 'Lucas', 'Torreira', 'https://assets.sorare.com/playerpicture/13ad07e7-9c5a-4119-9671-59e6a7e3ad6b/picture/squared-d82e9ef03207b747ddcdf8d8bc3db8d7.png', 29, 14, 21.9, 0, 90.00, 9, 'NORMAL', 'MEDIOCAMPISTA', 5),
(118, 'Leandro', 'Paredes', 'paredes.png', 30, 8, 51.7, 0, 59.80, 1, 'NORMAL', 'MEDIOCAMPISTA', 1),
(119, 'Matteo', 'Guendouzi', 'https://www.laziostylestore.com/images/lazio/players/84.webp?v=1737671000', 27, 16, 46.5, 0, 89.60, 4, 'NORMAL', 'MEDIOCAMPISTA', 7),
(120, 'Ryan', 'Gravenberch', 'https://backend.liverpoolfc.com/sites/default/files/styles/lg/public/2024-06/ryan-gravenberch-profile-action-shot-202425.png?itok=QBDlSmYh', 22, 11, 23.3, 0, 91.40, 12, 'NORMAL', 'MEDIOCAMPISTA', 8);


INSERT INTO `pais` (`id`, `nombre`, `codigo_iso`, `bandera_url`) VALUES
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

-- --------------------------------------------------------


INSERT INTO `sobre` (`id`, `tipo_sobre`, `titulo`, `precio`, `descripcion`, `imagen_url`, `usuario_id`) VALUES
(1, 'BRONCE', 'aa', 1.00, NULL, NULL, 1),
(2, 'PLATA', 'aa', 1.00, NULL, NULL, 1),
(3, 'ESPECIAL', 'aa', 1.00, NULL, NULL, 1),
(4, 'ORO', 'aa', 1.00, NULL, NULL, 1),
(5, 'BRONCE', 'aa', 1.00, NULL, NULL, 1),
(6, 'ESPECIAL', 'Sobre de Especial', 10000.00, NULL, 'sobreFutCodeEspecial.png', 1),
(7, 'BRONCE', 'Sobre de Bronce', 2500.00, NULL, 'sobreFutCodeBronce.png', 1),
(8, 'ESPECIAL', 'Sobre de Especial', 10000.00, NULL, 'sobreFutCodeEspecial.png', 1),
(9, 'ORO', 'Sobre de Oro', 7500.00, NULL, 'sobreFutCodeOro.png', 1),
(10, 'PLATA', 'Sobre de Plata', 5000.00, NULL, 'sobreFutCodePlata.png', 1);

-- --------------------------------------------------------


INSERT INTO `torneo` (`id`, `nombre`, `descripcion`, `formato_torneo_id`, `estado`, `torneo_copa_id`, `torneo_liga_id`) VALUES
(1, 'Copa Mundial 2025', 'Torneo eliminatorio con 16 equipos', 1, 'ABIERTO', NULL, NULL),
(2, 'Copa Libertadores 2025', 'Torneo internacional de clubes más prestigioso de América del Sur, con equipos argentinos como River Plate y Boca Juniors compitiendo por el título.', 1, 'ABIERTO', NULL, NULL),
(3, 'Liga Profesional Argentina 2025', 'Competencia de fútbol de primera división en Argentina, con 28 equipos luchando por el campeonato nacional.', 2, 'EN_CURSO', NULL, NULL),
(4, 'Copa Argentina 2025', 'Torneo eliminatorio que incluye equipos de todas las categorías del fútbol argentino, con gran emoción en cada fase.', 1, 'ABIERTO', NULL, NULL),
(5, 'Liga Madero 2025', 'Liga regional en la zona de Buenos Aires, destacando el talento local en un formato de puntos corridos.', 2, 'EN_CURSO', NULL, NULL),
(6, 'Copa Sudamericana 2025', 'Competencia internacional de clubes sudamericanos, con equipos argentinos buscando gloria continental.', 1, 'FINALIZADO', NULL, NULL),
(7, 'Liga Interior Argentina 2025', 'Torneo de fútbol para equipos del interior de Argentina, promoviendo el talento fuera de las grandes ciudades.', 2, 'ABIERTO', NULL, NULL);



INSERT INTO `usuario` (`id`, `email`, `password`, `rol`, `activo`, `equipo_id`) VALUES
(1, 'test@unlam.edu.ar', '$2a$10$gDIBmDa5/.1xdxJMV64qzOh44eIaRNBsqLX/z6UpuL//.EoeXANQS', 'ADMIN', 1, 1);


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
