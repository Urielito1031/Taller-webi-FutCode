-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-06-2025 a las 23:40:04
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `db_futcode`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `club`
--

CREATE TABLE `club` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `pais_id` int(11) DEFAULT NULL,
  `estadio_id` int(11) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `club`
--

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo`
--

CREATE TABLE `equipo` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `club_id` int(11) DEFAULT NULL,
  `esquema_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `equipo`
--

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo_torneo`
--

CREATE TABLE `equipo_torneo` (
  `id` int(11) NOT NULL,
  `equipo_id` int(11) NOT NULL,
  `posicion` int(11) NOT NULL,
  `partidos_jugados` int(11) NOT NULL DEFAULT 0,
  `partidos_ganados` int(11) NOT NULL DEFAULT 0,
  `partidos_empatados` int(11) NOT NULL DEFAULT 0,
  `partidos_perdidos` int(11) NOT NULL DEFAULT 0,
  `goles_a_favor` int(11) NOT NULL DEFAULT 0,
  `goles_en_contra` int(11) NOT NULL DEFAULT 0,
  `puntos` int(11) NOT NULL DEFAULT 0,
  `torneo_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `equipo_torneo`
--

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

--
-- Estructura de tabla para la tabla `esquema`
--

CREATE TABLE `esquema` (
  `id` int(11) NOT NULL,
  `esquema` enum('CUATRO_TRES_TRES','CUATRO_CUATRO_DOS','TRES_CINCO_DOS','CINCO_TRES_DOS','TRES_CUATRO_TRES') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `esquema`
--

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

CREATE TABLE `estadio` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `capacidad` int(11) DEFAULT NULL,
  `ubicacion` varchar(255) DEFAULT NULL,
  `imagen_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evento_partido`
--

CREATE TABLE `evento_partido` (
  `id` int(11) NOT NULL,
  `tipo_evento_partido` enum('TARJETA_AMARILLA','TARJETA_ROJA','LESION','EXPULSION','GOL') NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `minuto_de_partido` int(11) NOT NULL,
  `partido_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fase`
--

CREATE TABLE `fase` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `fase`
--

INSERT INTO `fase` (`id`, `nombre`) VALUES
(1, 'Fase de grupos'),
(2, 'Octavos de final'),
(3, 'Cuartos de final'),
(4, 'Semifinal'),
(5, 'Final');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `formacion_equipo`
--

CREATE TABLE `formacion_equipo` (
  `id` int(11) NOT NULL,
  `equipo_id` int(11) NOT NULL,
  `jugador_id` int(11) NOT NULL,
  `posicion_en_campo` enum('ARQUERO','DEFENSOR','MEDIOCAMPISTA','DELANTERO') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `formacion_equipo`
--

INSERT INTO `formacion_equipo` (`id`, `equipo_id`, `jugador_id`, `posicion_en_campo`) VALUES
(1527, 1, 16, 'ARQUERO'),
(1528, 1, 2, 'DEFENSOR'),
(1529, 1, 3, 'DEFENSOR'),
(1530, 1, 4, 'DEFENSOR'),
(1531, 1, 5, 'DEFENSOR'),
(1532, 1, 6, 'DEFENSOR'),
(1533, 1, 10, 'MEDIOCAMPISTA'),
(1534, 1, 118, 'MEDIOCAMPISTA'),
(1535, 1, 20, 'MEDIOCAMPISTA'),
(1536, 1, 21, 'DELANTERO'),
(1537, 1, 17, 'DELANTERO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `formato_torneo`
--

CREATE TABLE `formato_torneo` (
  `id` int(11) NOT NULL,
  `tipo` enum('LIGA','COPA') NOT NULL,
  `fase_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `formato_torneo`
--

INSERT INTO `formato_torneo` (`id`, `tipo`, `fase_id`) VALUES
(1, 'COPA', NULL),
(2, 'LIGA', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jugador`
--

CREATE TABLE `jugador` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `edad` int(11) NOT NULL,
  `numero_camiseta` int(11) NOT NULL,
  `rating` decimal(4,1) NOT NULL,
  `lesionado` tinyint(1) NOT NULL DEFAULT 0,
  `estado_fisico` decimal(5,2) NOT NULL,
  `pais_id` int(11) DEFAULT NULL,
  `rareza_jugador` enum('NORMAL','RARO','EPICO','LEYENDA') NOT NULL,
  `posicion` enum('ARQUERO','DEFENSOR','MEDIOCAMPISTA','DELANTERO') NOT NULL,
  `equipo_id` int(11) DEFAULT NULL,
  `sobre_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `jugador`
--

INSERT INTO `jugador` (`id`, `nombre`, `apellido`, `imagen`, `edad`, `numero_camiseta`, `rating`, `lesionado`, `estado_fisico`, `pais_id`, `rareza_jugador`, `posicion`, `equipo_id`, `sobre_id`) VALUES

(1, 'Emiliano', 'Martínez', 'https://futcodejugadores.free.nf/jugadores/emiliano_martinez.png', 31, 1, 88.0, 0, 95.00, 1, 'RARO', 'ARQUERO', 1, NULL),
(2, 'Cristian', 'Romero', 'https://futcodejugadores.free.nf/jugadores/cristianRomero.png', 26, 23, 87.0, 0, 90.00, 1, 'RARO', 'DEFENSOR', 1, NULL),
(3, 'Nicolás', 'Otamendi', 'https://futcodejugadores.free.nf/jugadores/otamendi.png', 36, 3, 85.0, 0, 88.00, 1, 'RARO', 'DEFENSOR', 1, NULL),
(4, 'Marcos', 'Acuña', 'https://futcodejugadores.free.nf/jugadores/acuna.png', 32, 8, 84.0, 0, 87.00, 1, 'RARO', 'DEFENSOR', 1, NULL),
(5, 'Gonzalo', 'Montiel', 'https://futcodejugadores.free.nf/jugadores/montiel.png', 27, 4, 83.0, 0, 89.00, 1, 'RARO', 'DEFENSOR', 1, NULL),
(7, 'Alexis', 'Mac Allister', 'https://futcodejugadores.free.nf/jugadores/macalister.png', 25, 5, 85.0, 0, 90.00, 1, 'RARO', 'MEDIOCAMPISTA', 1, NULL),
(8, 'Enzo', 'Fernández', 'https://futcodejugadores.free.nf/jugadores/enzoFernandez.webp', 23, 24, 87.0, 0, 92.00, 1, 'RARO', 'MEDIOCAMPISTA', 1, NULL),
(9, 'Lionel', 'Messi', 'https://tomaszfutcode.free.nf/jugadoresFutcode/messi.png', 36, 10, 94.0, 0, 89.00, 1, 'LEYENDA', 'DELANTERO', 1, NULL),
(10, 'Julián', 'Álvarez', 'https://futcodejugadores.free.nf/jugadores/julianAlvarez.png', 24, 9, 86.0, 0, 88.00, 1, 'RARO', 'DELANTERO', 1, NULL),
(11, 'Lautaro', 'Martínez', 'https://tomaszfutcode.free.nf/jugadoresFutcode/32_Lautaro_Martínez.webp', 26, 21, 88.0, 0, 90.00, 1, 'RARO', 'DELANTERO', 1, NULL),
(12, 'Cristiano', 'Ronaldo', 'https://tomaszfutcode.free.nf/jugadoresFutcode/cristianoRonaldo.png', 40, 7, 98.2, 0, 97.50, 11, 'LEYENDA', 'DELANTERO', 1, NULL),
(13, 'Kylian', 'Mbappé', 'https://tomaszfutcode.free.nf/jugadoresFutcode/mbappe.png', 26, 10, 84.3, 0, 98.70, 4, 'EPICO', 'DELANTERO', 1, NULL),
(14, 'Kevin', 'De Bruyne', 'https://tomaszfutcode.free.nf/jugadoresFutcode/deBruyne.png', 33, 17, 82.7, 0, 96.40, 14, 'EPICO', 'MEDIOCAMPISTA', 1, NULL),
(15, 'Virgil', 'van Dijk', 'https://tomaszfutcode.free.nf/jugadoresFutcode/vanDijk.png', 33, 4, 47.3, 0, 95.80, 12, 'RARO', 'DEFENSOR', 1, NULL),
(16, 'Thibaut', 'Courtois', 'https://tomaszfutcode.free.nf/jugadoresFutcode/courtois.png', 33, 1, 49.1, 0, 94.90, 14, 'RARO', 'ARQUERO', 1, NULL),
(17, 'Robert', 'Lewandowski', 'https://tomaszfutcode.free.nf/jugadoresFutcode/lewandowski.png', 36, 9, 83.6, 0, 98.20, 3, 'EPICO', 'DELANTERO', 1, NULL),
(18, 'Erling', 'Haaland', 'https://tomaszfutcode.free.nf/jugadoresFutcode/haaland.png', 24, 9, 85.2, 0, 98.80, 5, 'EPICO', 'DELANTERO', 1, NULL),
(19, 'Neymar', 'Jr', 'https://tomaszfutcode.free.nf/jugadoresFutcode/neymarJr.png', 33, 11, 80.4, 0, 97.00, 2, 'EPICO', 'MEDIOCAMPISTA', 1, NULL),
(20, 'Luka', 'Modrić', 'https://tomaszfutcode.free.nf/jugadoresFutcode/modric.png', 39, 10, 48.7, 0, 96.70, 3, 'RARO', 'MEDIOCAMPISTA', 1, NULL),
(21, 'Mohamed', 'Salah', 'https://tomaszfutcode.free.nf/jugadoresFutcode/salah.png', 32, 11, 46.2, 0, 97.20, 7, 'RARO', 'DELANTERO', 1, NULL),
(22, 'Sadio', 'Mané', 'https://tomaszfutcode.free.nf/jugadoresFutcode/mane.png', 33, 10, 45.8, 0, 96.50, 2, 'RARO', 'DELANTERO', 2, NULL),
(23, 'Casemiro', 'Carlos', 'https://tomaszfutcode.free.nf/jugadoresFutcode/casemiro.png', 32, 5, 44.3, 0, 95.90, 2, 'RARO', 'MEDIOCAMPISTA', 3, NULL),
(24, 'Joshua', 'Kimmich', 'https://tomaszfutcode.free.nf/jugadoresFutcode/kimmich.png', 30, 6, 50.0, 0, 96.00, 5, 'RARO', 'MEDIOCAMPISTA', 4, NULL),
 (25, 'Vinícius', 'Jr', 'https://tomaszfutcode.free.nf/jugadoresFutcode/17_Vinícius_Jr.png', 24, 7, 29.1, 0, 97.80, 2, 'NORMAL', 'DELANTERO', 5, NULL),
 (26, 'Bruno', 'Fernandes', 'https://tomaszfutcode.free.nf/jugadoresFutcode/16_Bruno_Fernandes.png', 30, 8, 25.7, 0, 96.10, 7, 'NORMAL', 'MEDIOCAMPISTA', 6, NULL),
 (28, 'Marc-André', 'ter Stegen', 'https://tomaszfutcode.free.nf/jugadoresFutcode/18_Marc-André_ter_Stegen.png', 33, 1, 22.4, 0, 95.20, 3, 'NORMAL', 'ARQUERO', 6, NULL),
(29, 'Alisson', 'Becker', 'https://tomaszfutcode.free.nf/jugadoresFutcode/19_Alisson_Becker.webp', 32, 1, 24.6, 0, 95.70, 2, 'NORMAL', 'ARQUERO', 7, NULL),
 (30, 'Ederson', 'Moraes', 'https://tomaszfutcode.free.nf/jugadoresFutcode/20_Ederson_Moraes.png', 31, 31, 27.8, 0, 95.40, 2, 'NORMAL', 'ARQUERO', 8, NULL),
 (31, 'Antonio', 'Rüdiger', 'https://tomaszfutcode.free.nf/jugadoresFutcode/21_Antonio_Rüdiger.webp', 31, 2, 23.5, 0, 95.10, 12, 'NORMAL', 'DEFENSOR', 9, NULL),
(32, 'Marquinhos', 'Marcos', 'https://tomaszfutcode.free.nf/jugadoresFutcode/22_Marquinhos_Marcos.png', 30, 5, 26.3, 0, 95.60, 2, 'NORMAL', 'DEFENSOR', 10, NULL),
(33, 'João', 'Cancelo', 'https://tomaszfutcode.free.nf/jugadoresFutcode/23_João_Cancelo.png', 30, 3, 21.9, 0, 95.30, 11, 'NORMAL', 'DEFENSOR', 2, NULL),
(34, 'Trent', 'Alexander-Arnold', 'https://tomaszfutcode.free.nf/jugadoresFutcode/24_Trent_Alexander-Arnold.webp', 26, 66, 30.0, 0, 95.00, 7, 'NORMAL', 'DEFENSOR', 3, NULL),
(35, 'Andrew', 'Robertson', 'https://tomaszfutcode.free.nf/jugadoresFutcode/25_Andrew_Robertson.png', 30, 26, 24.1, 0, 94.80, 7, 'NORMAL', 'DEFENSOR', 4, NULL),
(36, 'Theo', 'Hernández', 'https://tomaszfutcode.free.nf/jugadoresFutcode/26_Theo_Hernández.png', 28, 19, 28.7, 0, 94.70, 6, 'NORMAL', 'DEFENSOR', 5, NULL),
(37, 'Achraf', 'Hakimi', 'https://tomaszfutcode.free.nf/jugadoresFutcode/27_Achraf_Hakimi.webp', 26, 2, 19.2, 0, 94.60, 2, 'NORMAL', 'DEFENSOR', 6, NULL),
(38, 'Paul', 'Pogba', 'https://tomaszfutcode.free.nf/jugadoresFutcode/28_Paul_Pogba.png', 31, 6, 26.8, 0, 94.50, 4, 'NORMAL', 'MEDIOCAMPISTA', 7, NULL),
(39, 'Marco', 'Verratti', 'https://tomaszfutcode.free.nf/jugadoresFutcode/29_Marco_Verratti.png', 32, 8, 25.3, 0, 94.40, 6, 'NORMAL', 'MEDIOCAMPISTA', 8, NULL),
(40, 'Son', 'Heung-min', 'https://tomaszfutcode.free.nf/jugadoresFutcode/30_Son_Heung-min.png', 32, 7, 48.1, 0, 97.50, 13, 'RARO', 'DELANTERO', 9, NULL),
(41, 'Dusan', 'Vlahović', 'https://tomaszfutcode.free.nf/jugadoresFutcode/31_Dusan_Vlahović.webp', 24, 9, 22.7, 0, 94.30, 6, 'NORMAL', 'DELANTERO', 10, NULL),
(42, 'Phil', 'Foden', 'https://tomaszfutcode.free.nf/jugadoresFutcode/42_Phil_Foden.webp', 24, 47, 22.7, 0, 93.20, 7, 'NORMAL', 'MEDIOCAMPISTA', 2, NULL),
(43, 'Bernardo', 'Silva', 'https://tomaszfutcode.free.nf/jugadoresFutcode/43_Bernardo_Silva.png', 30, 20, 49.3, 0, 96.30, 11, 'RARO', 'MEDIOCAMPISTA', 3, NULL),
(44, 'Rafael', 'Leão', 'https://tomaszfutcode.free.nf/jugadoresFutcode/44_Rafael_Leão.webp', 25, 17, 25.8, 0, 93.10, 11, 'NORMAL', 'DELANTERO', 4, NULL),
(45, 'Hugo', 'Lloris', 'https://tomaszfutcode.free.nf/jugadoresFutcode/45_Hugo_Lloris.png', 38, 1, 19.4, 0, 93.00, 4, 'NORMAL', 'ARQUERO', 5, NULL),
(46, 'Keylor', 'Navas', 'https://tomaszfutcode.free.nf/jugadoresFutcode/46_Keylor_Navas.png', 37, 1, 28.6, 0, 92.90, 10, 'NORMAL', 'ARQUERO', 6, NULL),
(47, 'David', 'de Gea', 'https://tomaszfutcode.free.nf/jugadoresFutcode/47_David_de_Gea.png', 34, 1, 23.5, 0, 92.80, 3, 'NORMAL', 'ARQUERO', 7, NULL),
 (48, 'Mike', 'Maignan', 'https://tomaszfutcode.free.nf/jugadoresFutcode/48_Mike_Maignan.webp', 29, 16, 26.9, 0, 92.70, 4, 'NORMAL', 'ARQUERO', 8, NULL),
(49, 'Edin', 'Džeko', 'https://tomaszfutcode.free.nf/jugadoresFutcode/49_Edin_Džeko.png', 38, 9, 21.3, 0, 92.60, 6, 'NORMAL', 'DELANTERO', 9, NULL),
(50, 'Memphis', 'Depay', 'https://tomaszfutcode.free.nf/jugadoresFutcode/50_Memphis_Depay.png', 30, 9, 27.7, 0, 92.50, 12, 'NORMAL', 'DELANTERO', 10, NULL),
(51, 'Gabriel', 'Jesus', 'https://tomaszfutcode.free.nf/jugadoresFutcode/51_Gabriel_Jesus.webp', 27, 9, 24.2, 0, 92.40, 2, 'NORMAL', 'DELANTERO', 2, NULL),
(52, 'Raheem', 'Sterling', 'https://tomaszfutcode.free.nf/jugadoresFutcode/52_Raheem_Sterling.png', 29, 7, 28.8, 0, 92.30, 7, 'NORMAL', 'DELANTERO', 3, NULL),
(53, 'João', 'Félix', 'https://tomaszfutcode.free.nf/jugadoresFutcode/53_João_Félix.png', 25, 11, 20.4, 0, 92.20, 11, 'NORMAL', 'MEDIOCAMPISTA', 4, NULL),
 (54, 'Alejandro', 'Garnacho', 'https://tomaszfutcode.free.nf/jugadoresFutcode/54_Alejandro_Garnacho.png', 21, 17, 29.1, 0, 91.50, 1, 'NORMAL', 'DELANTERO', 5, NULL),
 (55, 'Francisco', 'Trincão', 'https://tomaszfutcode.free.nf/jugadoresFutcode/55_Francisco_Trincão.webp', 24, 21, 22.6, 0, 91.40, 11, 'NORMAL', 'DELANTERO', 6, NULL),
(56, 'Ansu', 'Fati', 'https://tomaszfutcode.free.nf/jugadoresFutcode/56_Ansu_Fati.png', 22, 10, 25.3, 0, 91.30, 3, 'NORMAL', 'DELANTERO', 7, NULL),
(57, 'Rodrygo', 'Goes', 'https://tomaszfutcode.free.nf/jugadoresFutcode/57_Rodrygo_Goes.png', 23, 11, 21.7, 0, 91.20, 2, 'NORMAL', 'DELANTERO', 8, NULL),
(58, 'Ferran', 'Torres', 'https://tomaszfutcode.free.nf/jugadoresFutcode/58_Ferran_Torres.png', 24, 7, 26.4, 0, 91.10, 3, 'NORMAL', 'DELANTERO', 9, NULL),
(59, 'Gonçalo', 'Ramos', 'https://tomaszfutcode.free.nf/jugadoresFutcode/59_Gonçalo_Ramos.webp', 23, 9, 23.9, 0, 91.00, 11, 'NORMAL', 'DELANTERO', 10, NULL),
(60, 'Darwin', 'Núñez', 'https://tomaszfutcode.free.nf/jugadoresFutcode/60_Darwin_Núñez.webp', 25, 19, 28.1, 0, 92.10, 9, 'NORMAL', 'DELANTERO', 11, NULL),
(81, 'Sebastien', 'Haller', 'https://tomaszfutcode.free.nf/jugadoresFutcode/61_Sebastien_Haller.png', 30, 9, 19.8, 0, 92.00, 12, 'NORMAL', 'DELANTERO', 2, NULL),
 (82, 'Victor', 'Osimhen', 'https://tomaszfutcode.free.nf/jugadoresFutcode/62_Victor_Osimhen.webp', 26, 9, 29.5, 0, 91.90, 8, 'NORMAL', 'DELANTERO', 3, NULL),
(83, 'Dušan', 'Tadić', 'https://tomaszfutcode.free.nf/jugadoresFutcode/63_Dušan_Tadić.png', 35, 10, 22.2, 0, 91.80, 13, 'NORMAL', 'MEDIOCAMPISTA', 4, NULL),
(84, 'Lucas', 'Paquetá', 'https://tomaszfutcode.free.nf/jugadoresFutcode/64_Lucas_Paquetá.png', 27, 11, 26.7, 0, 91.70, 2, 'NORMAL', 'MEDIOCAMPISTA', 5, NULL),
(85, 'James', 'Maddison', 'https://tomaszfutcode.free.nf/jugadoresFutcode/65_James_Maddison.png', 28, 10, 21.4, 0, 91.60, 7, 'NORMAL', 'MEDIOCAMPISTA', 6, NULL),
(86, 'Karim', 'Benzema', 'https://tomaszfutcode.free.nf/jugadoresFutcode/66_Karim_Benzema.png', 37, 9, 95.6, 0, 98.50, 3, 'LEYENDA', 'DELANTERO', 7, NULL),
(87, 'Harry', 'Kane', 'https://tomaszfutcode.free.nf/jugadoresFutcode/67_Harry_Kane.png', 31, 10, 81.3, 0, 98.00, 7, 'EPICO', 'DELANTERO', 8, NULL),
(88, 'Antoine', 'Griezmann', 'https://tomaszfutcode.free.nf/jugadoresFutcode/68_Antoine_Griezmann.webp', 33, 7, 46.9, 0, 97.30, 3, 'RARO', 'MEDIOCAMPISTA', 9, NULL),
(89, 'Romelu', 'Lukaku', 'https://tomaszfutcode.free.nf/jugadoresFutcode/69_Romelu_Lukaku.png', 31, 9, 43.7, 0, 96.60, 6, 'RARO', 'DELANTERO', 10, NULL),
(90, 'Paulo', 'Dybala', 'https://tomaszfutcode.free.nf/jugadoresFutcode/70_Paulo_Dybala.png', 30, 21, 45.2, 0, 96.20, 1, 'RARO', 'MEDIOCAMPISTA', 1, NULL),
 (91, 'Jadon', 'Sancho', 'https://tomaszfutcode.free.nf/jugadoresFutcode/71_Jadon_Sancho.png', 24, 25, 23.8, 0, 95.70, 7, 'NORMAL', 'DELANTERO', 2, NULL),
(92, 'Marcus', 'Rashford', 'https://tomaszfutcode.free.nf/jugadoresFutcode/72_Marcus_Rashford.webp', 26, 10, 27.1, 0, 95.50, 7, 'NORMAL', 'DELANTERO', 3, NULL),
(93, 'Jack', 'Grealish', 'https://tomaszfutcode.free.nf/jugadoresFutcode/73_Jack_Grealish.png', 28, 7, 22.5, 0, 95.20, 7, 'NORMAL', 'MEDIOCAMPISTA', 4, NULL),
(94, 'Mikel', 'Oyarzabal', 'https://tomaszfutcode.free.nf/jugadoresFutcode/74_Mikel_Oyarzabal.png', 27, 10, 28.7, 0, 94.90, 3, 'NORMAL', 'DELANTERO', 5, NULL),
(95, 'Wilfried', 'Zaha', 'https://tomaszfutcode.free.nf/jugadoresFutcode/75_Wilfried_Zaha.png', 31, 11, 25.3, 0, 94.60, 13, 'NORMAL', 'DELANTERO', 6, NULL),
(96, 'Dominic', 'Calvert-Lewin', 'https://tomaszfutcode.free.nf/jugadoresFutcode/calvert.png', 27, 9, 18.9, 0, 94.40, 7, 'NORMAL', 'DELANTERO', 7, NULL),
(97, 'Tammy', 'Abraham', 'https://tomaszfutcode.free.nf/jugadoresFutcode/77_Tammy_Abraham.webp', 26, 9, 26.4, 0, 94.20, 7, 'NORMAL', 'DELANTERO', 8, NULL),
(98, 'Olivier', 'Giroud', 'https://tomaszfutcode.free.nf/jugadoresFutcode/78_Olivier_Giroud.webp', 37, 9, 24.6, 0, 94.00, 4, 'NORMAL', 'DELANTERO', 9, NULL),
(99, 'Arkadiusz', 'Milik', 'https://tomaszfutcode.free.nf/jugadoresFutcode/79_Arkadiusz_Milik.webp', 30, 99, 27.8, 0, 93.80, 6, 'NORMAL', 'DELANTERO', 10, NULL),
 (100, 'Sebastian', 'Haller', 'https://tomaszfutcode.free.nf/jugadoresFutcode/80_Sebastian_Haller.png', 29, 22, 20.2, 0, 93.60, 12, 'NORMAL', 'DELANTERO', 11, NULL),
(101, 'Ivan', 'Toney', 'https://tomaszfutcode.free.nf/jugadoresFutcode/81_Ivan_Toney.webp', 28, 17, 29.5, 0, 93.40, 7, 'NORMAL', 'DELANTERO', 2, NULL),
(102, 'Patrik', 'Schick', 'https://tomaszfutcode.free.nf/jugadoresFutcode/82_Patrik_Schick.png', 28, 14, 23.1, 0, 93.20, 5, 'NORMAL', 'DELANTERO', 3, NULL),
(103, 'Florian', 'Wirtz', 'https://tomaszfutcode.free.nf/jugadoresFutcode/83_Florian_Wirtz.png', 21, 10, 25.7, 0, 93.00, 5, 'NORMAL', 'MEDIOCAMPISTA', 4, NULL),
(104, 'Nicolò', 'Zaniolo', 'https://tomaszfutcode.free.nf/jugadoresFutcode/84_Nicolò_Zaniolo.webp', 24, 22, 19.3, 0, 92.80, 6, 'NORMAL', 'MEDIOCAMPISTA', 5, NULL),
(105, 'Houssem', 'Aouar', 'https://tomaszfutcode.free.nf/jugadoresFutcode/85_Houssem_Aouar.png', 26, 8, 29.8, 0, 92.60, 4, 'NORMAL', 'MEDIOCAMPISTA', 6, NULL),
(106, 'James', 'Milner', 'https://tomaszfutcode.free.nf/jugadoresFutcode/86_James_Milner.png', 38, 7, 26.4, 0, 92.40, 7, 'NORMAL', 'MEDIOCAMPISTA', 7, NULL),
(107, 'Jordan', 'Henderson', 'https://tomaszfutcode.free.nf/jugadoresFutcode/87_Jordan_Henderson.png', 34, 8, 21.6, 0, 92.20, 7, 'NORMAL', 'MEDIOCAMPISTA', 8, NULL),
(108, 'Youri', 'Tielemans', 'https://tomaszfutcode.free.nf/jugadoresFutcode/88_Youri_Tielemans.webp', 27, 9, 27.1, 0, 92.00, 14, 'NORMAL', 'MEDIOCAMPISTA', 9, NULL),
(109, 'Renato', 'Sanches', 'https://tomaszfutcode.free.nf/jugadoresFutcode/89_Renato_Sanches.png', 26, 18, 23.7, 0, 91.80, 11, 'NORMAL', 'MEDIOCAMPISTA', 10, NULL),
(110, 'Weston', 'McKennie', 'https://tomaszfutcode.free.nf/jugadoresFutcode/90_Weston_McKennie.webp', 25, 16, 28.1, 0, 91.20, 13, 'NORMAL', 'MEDIOCAMPISTA', 11, NULL),
(111, 'Kalvin', 'Phillips', 'https://tomaszfutcode.free.nf/jugadoresFutcode/91_Kalvin_Phillips.png', 28, 15, 26.9, 0, 91.00, 7, 'NORMAL', 'MEDIOCAMPISTA', 12, NULL),
(112, 'Declan', 'Rice', 'https://tomaszfutcode.free.nf/jugadoresFutcode/92_Declan_Rice.webp', 25, 6, 32.7, 0, 90.80, 7, 'NORMAL', 'MEDIOCAMPISTA', 13, NULL),
(113, 'Wilfred', 'Ndidi', 'https://tomaszfutcode.free.nf/jugadoresFutcode/93_Wilfred_Ndidi.png', 27, 25, 32.5, 0, 90.60, 14, 'NORMAL', 'MEDIOCAMPISTA', 14, NULL),
(115, 'Sergio', 'Busquets', 'https://tomaszfutcode.free.nf/jugadoresFutcode/94_Sergio_Busquets.png', 36, 5, 24.3, 0, 90.40, 3, 'NORMAL', 'MEDIOCAMPISTA', 3, NULL),
(116, 'Rodrigo', 'De Paul', 'https://tomaszfutcode.free.nf/jugadoresFutcode/95_Rodrigo_De_Paul.webp', 30, 7, 22.1, 0, 90.20, 1, 'NORMAL', 'MEDIOCAMPISTA', 4, NULL),
(117, 'Lucas', 'Torreira', 'https://tomaszfutcode.free.nf/jugadoresFutcode/96_Lucas_Torreira.png', 29, 14, 21.9, 0, 90.00, 9, 'NORMAL', 'MEDIOCAMPISTA', 5, NULL),
(118, 'Leandro', 'Paredes', 'https://tomaszfutcode.free.nf/jugadoresFutcode/97_Leandro_Paredes.png', 30, 8, 51.7, 0, 59.80, 1, 'NORMAL', 'MEDIOCAMPISTA', 1, NULL),
(119, 'Matteo', 'Guendouzi', 'https://tomaszfutcode.free.nf/jugadoresFutcode/98_Matteo_Guendouzi.webp', 27, 16, 46.5, 0, 89.60, 4, 'NORMAL', 'MEDIOCAMPISTA', 7, NULL),
(120, 'Ryan', 'Gravenberch', 'https://tomaszfutcode.free.nf/jugadoresFutcode/99_Ryan_Gravenberch.png', 22, 11, 23.3, 0, 91.40, 12, 'NORMAL', 'MEDIOCAMPISTA', 8, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pais`
--

CREATE TABLE `pais` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `codigo_iso` varchar(2) NOT NULL,
  `bandera_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pais`
--

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

--
-- Estructura de tabla para la tabla `partido`
--

CREATE TABLE `partido` (
  `id` int(11) NOT NULL,
  `equipo_local_id` int(11) NOT NULL,
  `equipo_visitante_id` int(11) NOT NULL,
  `fecha_encuentro` datetime NOT NULL,
  `estado_partido` enum('SIN_JUGAR','EN_JUEGO','FINALIZADO') NOT NULL,
  `torneo_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sobre`
--

CREATE TABLE `sobre` (
  `id` int(11) NOT NULL,
  `tipo_sobre` enum('ORO','PLATA','BRONCE','ESPECIAL') NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `imagen_url` varchar(255) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `sobre`
--

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

--
-- Estructura de tabla para la tabla `torneo`
--

CREATE TABLE `torneo` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `formato_torneo_id` int(11) DEFAULT NULL,
  `estado` enum('ABIERTO','EN_CURSO','FINALIZADO') NOT NULL,
  `torneo_copa_id` bigint(20) DEFAULT NULL,
  `torneo_liga_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `torneo`
--

INSERT INTO `torneo` (`id`, `nombre`, `descripcion`, `formato_torneo_id`, `estado`, `torneo_copa_id`, `torneo_liga_id`) VALUES
(1, 'Copa Mundial 2025', 'Torneo eliminatorio con 16 equipos', 1, 'ABIERTO', NULL, NULL),
(2, 'Copa Libertadores 2025', 'Torneo internacional de clubes más prestigioso de América del Sur, con equipos argentinos como River Plate y Boca Juniors compitiendo por el título.', 1, 'ABIERTO', NULL, NULL),
(3, 'Liga Profesional Argentina 2025', 'Competencia de fútbol de primera división en Argentina, con 28 equipos luchando por el campeonato nacional.', 2, 'EN_CURSO', NULL, NULL),
(4, 'Copa Argentina 2025', 'Torneo eliminatorio que incluye equipos de todas las categorías del fútbol argentino, con gran emoción en cada fase.', 1, 'ABIERTO', NULL, NULL),
(5, 'Liga Madero 2025', 'Liga regional en la zona de Buenos Aires, destacando el talento local en un formato de puntos corridos.', 2, 'EN_CURSO', NULL, NULL),
(6, 'Copa Sudamericana 2025', 'Competencia internacional de clubes sudamericanos, con equipos argentinos buscando gloria continental.', 1, 'FINALIZADO', NULL, NULL),
(7, 'Liga Interior Argentina 2025', 'Torneo de fútbol para equipos del interior de Argentina, promoviendo el talento fuera de las grandes ciudades.', 2, 'ABIERTO', NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `torneo_copa`
--

CREATE TABLE `torneo_copa` (
  `id` int(11) NOT NULL,
  `id_fase` int(11) NOT NULL,
  `torneo_id` bigint(20) NOT NULL,
  `fase_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `torneo_liga`
--

CREATE TABLE `torneo_liga` (
  `id` int(11) NOT NULL,
  `fechas` int(11) DEFAULT 0,
  `torneo_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rol` varchar(50) NOT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT 0,
  `equipo_id` bigint(20) DEFAULT NULL,
  `monedas` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `email`, `password`, `rol`, `activo`, `equipo_id`, `monedas`) VALUES
(1, 'test@unlam.edu.ar', '$2a$10$gDIBmDa5/.1xdxJMV64qzOh44eIaRNBsqLX/z6UpuL//.EoeXANQS', 'ADMIN', 1, 1, NULL),
(4, 'urielestebanyurquina@gmail.com', '$2a$10$yXWLYL6XZvYS99xh2v9atun9rD/AhsilknimfnvrzYj90FD0S5CsK', 'USER', 1, NULL, NULL),
(5, 'ejemplo@gmail.com', '$2a$10$7UXQl8PGShG6xVhinhMmu.GHL80RA8ZGNUKh48X.DUnF00x4Sxu4.', 'USER', 1, NULL, NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `club`
--
ALTER TABLE `club`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pais_id` (`pais_id`),
  ADD KEY `estadio_id` (`estadio_id`);

--
-- Indices de la tabla `equipo`
--
ALTER TABLE `equipo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `club_id` (`club_id`),
  ADD KEY `fk_equipo_esquema` (`esquema_id`);

--
-- Indices de la tabla `equipo_torneo`
--
ALTER TABLE `equipo_torneo`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_equipo_torneo` (`equipo_id`,`torneo_id`),
  ADD KEY `torneo_id` (`torneo_id`);

--
-- Indices de la tabla `esquema`
--
ALTER TABLE `esquema`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `estadio`
--
ALTER TABLE `estadio`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `evento_partido`
--
ALTER TABLE `evento_partido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `partido_id` (`partido_id`);

--
-- Indices de la tabla `fase`
--
ALTER TABLE `fase`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `formacion_equipo`
--
ALTER TABLE `formacion_equipo`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `equipo_id` (`equipo_id`,`jugador_id`),
  ADD KEY `jugador_id` (`jugador_id`);

--
-- Indices de la tabla `formato_torneo`
--
ALTER TABLE `formato_torneo`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `jugador`
--
ALTER TABLE `jugador`
  ADD PRIMARY KEY (`id`),
  ADD KEY `pais_id` (`pais_id`),
  ADD KEY `fk_equipo_id` (`equipo_id`);

--
-- Indices de la tabla `pais`
--
ALTER TABLE `pais`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`),
  ADD UNIQUE KEY `codigo_iso` (`codigo_iso`);

--
-- Indices de la tabla `partido`
--
ALTER TABLE `partido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `equipo_local_id` (`equipo_local_id`),
  ADD KEY `equipo_visitante_id` (`equipo_visitante_id`),
  ADD KEY `torneo_id` (`torneo_id`);

--
-- Indices de la tabla `sobre`
--
ALTER TABLE `sobre`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `torneo`
--
ALTER TABLE `torneo`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_n0a32rfinot2bm27o52v22lvt` (`torneo_copa_id`),
  ADD UNIQUE KEY `UK_ncjx5dmyf938gtehdokndj5ty` (`torneo_liga_id`),
  ADD KEY `formato_torneo_id` (`formato_torneo_id`);

--
-- Indices de la tabla `torneo_copa`
--
ALTER TABLE `torneo_copa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_fase` (`id_fase`);

--
-- Indices de la tabla `torneo_liga`
--
ALTER TABLE `torneo_liga`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `club`
--
ALTER TABLE `club`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;

--
-- AUTO_INCREMENT de la tabla `equipo`
--
ALTER TABLE `equipo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- AUTO_INCREMENT de la tabla `equipo_torneo`
--
ALTER TABLE `equipo_torneo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;

--
-- AUTO_INCREMENT de la tabla `esquema`
--
ALTER TABLE `esquema`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `estadio`
--
ALTER TABLE `estadio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `evento_partido`
--
ALTER TABLE `evento_partido`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `fase`
--
ALTER TABLE `fase`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `formacion_equipo`
--
ALTER TABLE `formacion_equipo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1538;

--
-- AUTO_INCREMENT de la tabla `formato_torneo`
--
ALTER TABLE `formato_torneo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `jugador`
--
ALTER TABLE `jugador`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=121;

--
-- AUTO_INCREMENT de la tabla `pais`
--
ALTER TABLE `pais`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `partido`
--
ALTER TABLE `partido`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sobre`
--
ALTER TABLE `sobre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `torneo`
--
ALTER TABLE `torneo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `club`
--
ALTER TABLE `club`
  ADD CONSTRAINT `club_ibfk_1` FOREIGN KEY (`pais_id`) REFERENCES `pais` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `club_ibfk_2` FOREIGN KEY (`estadio_id`) REFERENCES `estadio` (`id`) ON DELETE SET NULL;

--
-- Filtros para la tabla `equipo`
--
ALTER TABLE `equipo`
  ADD CONSTRAINT `equipo_ibfk_1` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `fk_equipo_esquema` FOREIGN KEY (`esquema_id`) REFERENCES `esquema` (`id`);

--
-- Filtros para la tabla `equipo_torneo`
--
ALTER TABLE `equipo_torneo`
  ADD CONSTRAINT `equipo_torneo_ibfk_1` FOREIGN KEY (`torneo_id`) REFERENCES `torneo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_equipotorneo_equipo` FOREIGN KEY (`equipo_id`) REFERENCES `equipo` (`id`);

--
-- Filtros para la tabla `evento_partido`
--
ALTER TABLE `evento_partido`
  ADD CONSTRAINT `evento_partido_ibfk_1` FOREIGN KEY (`partido_id`) REFERENCES `partido` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `formacion_equipo`
--
ALTER TABLE `formacion_equipo`
  ADD CONSTRAINT `formacion_equipo_ibfk_1` FOREIGN KEY (`equipo_id`) REFERENCES `equipo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `formacion_equipo_ibfk_2` FOREIGN KEY (`jugador_id`) REFERENCES `jugador` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `jugador`
--
ALTER TABLE `jugador`
  ADD CONSTRAINT `fk_equipo_id` FOREIGN KEY (`equipo_id`) REFERENCES `equipo` (`id`),
  ADD CONSTRAINT `jugador_ibfk_2` FOREIGN KEY (`pais_id`) REFERENCES `pais` (`id`) ON DELETE SET NULL;

--
-- Filtros para la tabla `partido`
--
ALTER TABLE `partido`
  ADD CONSTRAINT `partido_ibfk_1` FOREIGN KEY (`equipo_local_id`) REFERENCES `equipo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `partido_ibfk_2` FOREIGN KEY (`equipo_visitante_id`) REFERENCES `equipo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `partido_ibfk_3` FOREIGN KEY (`torneo_id`) REFERENCES `torneo` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `torneo`
--
ALTER TABLE `torneo`
  ADD CONSTRAINT `torneo_ibfk_1` FOREIGN KEY (`formato_torneo_id`) REFERENCES `formato_torneo` (`id`) ON DELETE SET NULL;

--
-- Filtros para la tabla `torneo_copa`
--
ALTER TABLE `torneo_copa`
  ADD CONSTRAINT `torneo_copa_ibfk_1` FOREIGN KEY (`id`) REFERENCES `torneo` (`id`),
  ADD CONSTRAINT `torneo_copa_ibfk_2` FOREIGN KEY (`id_fase`) REFERENCES `fase` (`id`);

--
-- Filtros para la tabla `torneo_liga`
--
ALTER TABLE `torneo_liga`
  ADD CONSTRAINT `torneo_liga_ibfk_1` FOREIGN KEY (`id`) REFERENCES `torneo` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
