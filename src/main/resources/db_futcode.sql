-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-07-2025 a las 22:05:31
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
  `esquema_id` int(11) DEFAULT 1,
  `usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `equipo`
--

INSERT INTO `equipo` (`id`, `nombre`, `club_id`, `esquema_id`, `usuario_id`) VALUES
(1, 'River Plate Titulares', 1, 1, 1),
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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo_torneo`
--

CREATE TABLE `equipo_torneo` (
  `id` int(11) NOT NULL,
  `equipo_id` int(11) NOT NULL,
  `posicion` int(11) NOT NULL DEFAULT 0,
  `partidos_jugados` int(11) NOT NULL DEFAULT 0,
  `partidos_ganados` int(11) NOT NULL DEFAULT 0,
  `partidos_empatados` int(11) NOT NULL DEFAULT 0,
  `partidos_perdidos` int(11) NOT NULL DEFAULT 0,
  `goles_a_favor` int(11) NOT NULL DEFAULT 0,
  `goles_en_contra` int(11) NOT NULL DEFAULT 0,
  `puntos` int(11) NOT NULL DEFAULT 0,
  `torneo_id` int(11) DEFAULT NULL,
  `posicion_anterior` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `equipo_torneo`
--

INSERT INTO `equipo_torneo` (`id`, `equipo_id`, `posicion`, `partidos_jugados`, `partidos_ganados`, `partidos_empatados`, `partidos_perdidos`, `goles_a_favor`, `goles_en_contra`, `puntos`, `torneo_id`, `posicion_anterior`) VALUES
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
(16, 16, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0),
(17, 17, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
(18, 18, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
(19, 19, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
(20, 20, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
(21, 21, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
(22, 22, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
(23, 23, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
(24, 24, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
(25, 25, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
(26, 26, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
(27, 27, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
(28, 28, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
(29, 29, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
(30, 30, 0, 0, 0, 0, 0, 0, 0, 0, 1, NULL),
(32, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(33, 3, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(34, 4, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(35, 5, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(36, 6, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(37, 7, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(38, 8, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(39, 9, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(40, 10, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(41, 11, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(42, 12, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(43, 13, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(44, 14, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(45, 15, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(46, 16, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(47, 17, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(48, 18, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(49, 19, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(50, 20, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(51, 21, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(52, 22, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(53, 23, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(54, 24, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(55, 25, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(56, 26, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(57, 27, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(58, 28, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(59, 29, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(60, 30, 0, 0, 0, 0, 0, 0, 0, 0, 2, NULL),
(62, 2, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(63, 3, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(64, 4, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(65, 5, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(66, 6, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(67, 7, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(68, 8, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(69, 9, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(70, 10, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(71, 11, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(72, 12, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(73, 13, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(74, 14, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(75, 15, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(76, 16, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(77, 17, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(78, 18, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(79, 19, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(80, 20, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(81, 21, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(82, 22, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(83, 23, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(84, 24, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(85, 25, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(86, 26, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(87, 27, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(88, 28, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(89, 29, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(90, 30, 0, 0, 0, 0, 0, 0, 0, 0, 4, NULL),
(92, 2, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(93, 3, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(94, 4, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(95, 5, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(96, 6, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(97, 7, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(98, 8, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(99, 9, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(100, 10, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(101, 11, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(102, 12, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(103, 13, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(104, 14, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(105, 15, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(106, 16, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(107, 17, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(108, 18, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(109, 19, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(110, 20, 0, 0, 0, 0, 0, 0, 0, 0, 7, NULL),
(119, 2, 0, 0, 0, 0, 0, 0, 0, 0, NULL, NULL);

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
  `partido_id` int(11) DEFAULT NULL,
  `equipo_id` bigint(20) DEFAULT NULL,
  `jugador_id` bigint(20) DEFAULT NULL
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
-- Estructura de tabla para la tabla `fecha`
--

CREATE TABLE `fecha` (
  `id` bigint(20) NOT NULL,
  `numero` bigint(20) NOT NULL,
  `simulada` bit(1) NOT NULL,
  `torneo_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `torneo_id` int(11) DEFAULT NULL,
  `goles_local` int(11) NOT NULL,
  `goles_visitante` int(11) NOT NULL,
  `resultado` varchar(255) NOT NULL,
  `fecha_id` bigint(20) DEFAULT NULL
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
(13, 'BRONCE', 'Sobre de Bronce', 2500.00, NULL, 'sobreFutCodeBronce.png', 1),
(14, 'PLATA', 'Sobre de Plata', 5000.00, NULL, 'sobreFutCodePlata.png', 1);

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
(1, 'Liga Premier', '¡Comienza tu carrera en el fútbol local! En esta liga sencilla, cada equipo se enfrentará al resto una única vez. Asciende posiciones con cada victoria y demuestra quién manda en el barrio. ¡Hay 20.000 monedas en juego para el campeón!', 2, 'ABIERTO', NULL, NULL),
(2, 'Desafio semiprofesional', 'Un torneo ideal para forjar rivalidades y amistades. Los equipos jugarán todos contra todos a doble partido (ida y vuelta), sumando puntos para coronarse como el más regular. Prepárate para la gloria y un bote de 15.000 monedas', 2, 'ABIERTO', NULL, NULL),
(3, 'Liga Profesional Argentina 2025', 'Competencia de fútbol de primera división en Argentina, con 28 equipos luchando por el campeonato nacional.', 2, 'EN_CURSO', NULL, NULL),
(4, 'Torneo Leyendas', 'La élite regional se da cita aquí. Una competición feroz donde la táctica lo es todo. Los equipos se enfrentarán en un formato de liga a doble partido, luchando punto a punto por la supremacía. El vencedor se llevará a casa 40.000 monedas.', 2, 'ABIERTO', NULL, NULL),
(5, 'Liga Madero 2025', 'Liga regional en la zona de Buenos Aires, destacando el talento local en un formato de puntos corridos.', 2, 'EN_CURSO', NULL, NULL),
(6, 'Copa Sudamericana 2025', 'Competencia internacional de clubes sudamericanos, con equipos argentinos buscando gloria continental.', 2, 'FINALIZADO', NULL, NULL),
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
(1, 'test@unlam.edu.ar', '$2a$10$gDIBmDa5/.1xdxJMV64qzOh44eIaRNBsqLX/z6UpuL//.EoeXANQS', 'ADMIN', 1, 1, 40000);

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
-- Indices de la tabla `fecha`
--
ALTER TABLE `fecha`
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
  ADD KEY `torneo_id` (`torneo_id`),
  ADD KEY `FKti8kfserc69ri8o5rfe2cs8nx` (`fecha_id`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

--
-- AUTO_INCREMENT de la tabla `equipo_torneo`
--
ALTER TABLE `equipo_torneo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=130;

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
-- AUTO_INCREMENT de la tabla `fecha`
--
ALTER TABLE `fecha`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=62;

--
-- AUTO_INCREMENT de la tabla `formacion_equipo`
--
ALTER TABLE `formacion_equipo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1681;

--
-- AUTO_INCREMENT de la tabla `formato_torneo`
--
ALTER TABLE `formato_torneo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `jugador`
--
ALTER TABLE `jugador`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=98;

--
-- AUTO_INCREMENT de la tabla `pais`
--
ALTER TABLE `pais`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `partido`
--
ALTER TABLE `partido`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=932;

--
-- AUTO_INCREMENT de la tabla `sobre`
--
ALTER TABLE `sobre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `torneo`
--
ALTER TABLE `torneo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

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
  ADD CONSTRAINT `FK1mi0g4v6h0cgspm60xwh1lhcy` FOREIGN KEY (`equipo_id`) REFERENCES `equipo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK7i67ejgicdvtoavo5ekifakuw` FOREIGN KEY (`jugador_id`) REFERENCES `jugador` (`id`) ON DELETE CASCADE;

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
  ADD CONSTRAINT `FK1akyk92mjh1v27nu8fhufpal3` FOREIGN KEY (`torneo_id`) REFERENCES `torneo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK7d0ff294y8ul2ego1km5r0tmq` FOREIGN KEY (`equipo_local_id`) REFERENCES `equipo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKksi4gq2o9almp8a1gfnadjfu1` FOREIGN KEY (`equipo_visitante_id`) REFERENCES `equipo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKti8kfserc69ri8o5rfe2cs8nx` FOREIGN KEY (`fecha_id`) REFERENCES `fecha` (`id`);

--
-- Filtros para la tabla `torneo`
--
ALTER TABLE `torneo`
  ADD CONSTRAINT `FKfm7k6pcp9qv5s3k11hn20326b` FOREIGN KEY (`formato_torneo_id`) REFERENCES `formato_torneo` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
