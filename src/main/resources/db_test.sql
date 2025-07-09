-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 09-07-2025 a las 05:46:03
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
-- Base de datos: `db_test`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `club`
--

CREATE TABLE `club` (
  `id` bigint(20) NOT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `nombre` varchar(100) NOT NULL,
  `estadio_id` int(11) DEFAULT NULL,
  `pais_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `club`
--

INSERT INTO `club` (`id`, `imagen`, `nombre`, `estadio_id`, `pais_id`) VALUES
(1, 'https://upload.wikimedia.org/wikipedia/en/3/3f/Club_Atl%C3%A9tico_River_Plate_logo.svg', 'River Plate', 1, 1),
(2, 'https://upload.wikimedia.org/wikipedia/en/5/5c/CR_Flamengo_logo.svg', 'Flamengo', 2, 2),
(3, 'https://upload.wikimedia.org/wikipedia/en/4/47/FC_Barcelona_%28crest%29.svg', 'FC Barcelona', 3, 3),
(4, 'https://upload.wikimedia.org/wikipedia/en/1/1f/FC_Bayern_M%C3%BCnchen_logo_%282017%29.svg', 'Bayern Múnich', 4, 5),
(5, 'https://upload.wikimedia.org/wikipedia/en/d/d0/AC_Milan_logo.svg', 'AC Milan', 5, 6),
(6, 'https://upload.wikimedia.org/wikipedia/en/7/7a/Manchester_United_FC_crest.svg', 'Manchester United', 6, 7),
(7, 'https://upload.wikimedia.org/wikipedia/en/0/0c/Liverpool_FC.svg', 'Liverpool FC', 7, 7),
(8, 'https://upload.wikimedia.org/wikipedia/en/a/a7/Paris_Saint-Germain_F.C..svg', 'Paris Saint-Germain', 8, 4),
(9, 'https://upload.wikimedia.org/wikipedia/en/d/d2/Juventus_FC_2017_logo.svg', 'Juventus', 9, 6),
(10, 'https://upload.wikimedia.org/wikipedia/en/c/cc/Chelsea_FC.svg', 'Chelsea FC', 10, 7),
(11, 'https://upload.wikimedia.org/wikipedia/en/1/1c/Club_América_logo_%282013%29.svg', 'Club América', 11, 8),
(12, 'https://upload.wikimedia.org/wikipedia/commons/8/8b/Escudo_Actual_Pe%C3%B1arol.png', 'Peñarol', 12, 9),
(13, 'https://upload.wikimedia.org/wikipedia/en/4/45/Colo-Colo_logo.svg', 'Colo-Colo', 13, 10),
(14, 'https://upload.wikimedia.org/wikipedia/en/f/f4/Atletico_Madrid_2017_logo.svg', 'Atlético de Madrid', 14, 3),
(15, 'https://upload.wikimedia.org/wikipedia/commons/6/67/Borussia_Dortmund_logo.svg', 'Borussia Dortmund', 15, 5),
(16, 'https://upload.wikimedia.org/wikipedia/en/8/89/Sport_Lisboa_e_Benfica_logo.svg', 'SL Benfica', 16, 11),
(17, 'https://upload.wikimedia.org/wikipedia/en/7/79/Ajax_Amsterdam.svg', 'Ajax', 17, 12),
(18, 'https://upload.wikimedia.org/wikipedia/en/f/f3/Atlanta_United_FC_logo.svg', 'Atlanta United', 18, 13),
(19, 'https://upload.wikimedia.org/wikipedia/en/1/11/Celtic_FC.svg', 'Celtic FC', 19, 14),
(20, 'https://upload.wikimedia.org/wikipedia/en/f/f1/FC_Porto.svg', 'FC Porto', 21, 11),
(21, 'https://upload.wikimedia.org/wikipedia/en/5/55/PSV_Eindhoven.svg', 'PSV Eindhoven', 22, 12),
(22, 'https://upload.wikimedia.org/wikipedia/en/2/2d/New_York_City_FC.svg', 'New York City FC', 23, 13),
(23, 'https://upload.wikimedia.org/wikipedia/en/0/0c/Rangers_FC.svg', 'Rangers FC', 24, 14);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo`
--

CREATE TABLE `equipo` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `club_id` bigint(20) DEFAULT NULL,
  `esquema_id` bigint(20) NOT NULL,
  `usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `equipo`
--

INSERT INTO `equipo` (`id`, `nombre`, `club_id`, `esquema_id`, `usuario_id`) VALUES
(1, 'River Plate Titulares', 1, 1, NULL),
(2, 'Flamengo Juveniles', 2, 2, NULL),
(3, 'Barcelona B', 3, 3, 1),
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
(30, 'PSV Academy', 22, 2, NULL),
(31, 'MosqueterosFC', NULL, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo_torneo`
--

CREATE TABLE `equipo_torneo` (
  `id` bigint(20) NOT NULL,
  `goles_a_favor` int(11) DEFAULT NULL,
  `goles_en_contra` int(11) DEFAULT NULL,
  `partidos_empatados` int(11) DEFAULT NULL,
  `partidos_ganados` int(11) DEFAULT NULL,
  `partidos_jugados` int(11) DEFAULT NULL,
  `partidos_perdidos` int(11) DEFAULT NULL,
  `posicion` int(11) DEFAULT NULL,
  `puntos` int(11) DEFAULT NULL,
  `equipo_id` bigint(20) DEFAULT NULL,
  `torneo_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `esquema`
--

CREATE TABLE `esquema` (
  `id` bigint(20) NOT NULL,
  `esquema` varchar(255) NOT NULL
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
  `capacidad` int(11) DEFAULT NULL,
  `imagen_url` varchar(255) DEFAULT NULL,
  `nombre` varchar(100) NOT NULL,
  `ubicacion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estadio`
--

INSERT INTO `estadio` (`id`, `capacidad`, `imagen_url`, `nombre`, `ubicacion`) VALUES
(1, 83000, 'https://upload.wikimedia.org/wikipedia/commons/8/8b/Estadio_Monumental_%28River_Plate%29.jpg', 'Estadio Monumental', 'Buenos Aires, Argentina'),
(2, 78838, 'https://upload.wikimedia.org/wikipedia/commons/6/6b/Maracan%C3%A3_Stadium_2014.jpg', 'Maracanã', 'Río de Janeiro, Brasil'),
(3, 99354, 'https://upload.wikimedia.org/wikipedia/commons/2/2c/Camp_Nou_panorama.jpg', 'Camp Nou', 'Barcelona, España'),
(4, 75000, 'https://upload.wikimedia.org/wikipedia/commons/0/0e/Allianz-Arena_2010.jpg', 'Allianz Arena', 'Múnich, Alemania'),
(5, 80018, 'https://upload.wikimedia.org/wikipedia/commons/3/3e/San_Siro_Stadium_-_panoramio.jpg', 'San Siro', 'Milán, Italia'),
(6, 74310, 'https://example.com/old_trafford.jpg', 'Old Trafford', 'Manchester, Inglaterra'),
(7, 54000, 'https://example.com/anfield.jpg', 'Anfield', 'Liverpool, Inglaterra'),
(8, 47929, 'https://example.com/parc_des_princes.jpg', 'Parc des Princes', 'París, Francia'),
(9, 41507, 'https://example.com/allianz_stadium_turin.jpg', 'Allianz Stadium', 'Turín, Italia'),
(10, 40834, 'https://example.com/stamford_bridge.jpg', 'Stamford Bridge', 'Londres, Inglaterra'),
(11, 87000, 'https://example.com/estadio_azteca.jpg', 'Estadio Azteca', 'Ciudad de México, México'),
(12, 40000, 'https://example.com/penarol_estadio.jpg', 'Estadio Campeón del Siglo', 'Montevideo, Uruguay'),
(13, 47347, 'https://example.com/colo_colo_estadio.jpg', 'Estadio Monumental David Arellano', 'Santiago, Chile'),
(14, 68456, 'https://example.com/metropolitano.jpg', 'Cívitas Metropolitano', 'Madrid, España'),
(15, 81365, 'https://example.com/signal_iduna_park.jpg', 'Signal Iduna Park', 'Dortmund, Alemania'),
(16, 65000, 'https://example.com/estadio_da_luz.jpg', 'Estádio da Luz', 'Lisboa, Portugal'),
(17, 55000, 'https://example.com/johan_cruyff_arena.jpg', 'Johan Cruyff Arena', 'Ámsterdam, Países Bajos'),
(18, 71000, 'https://example.com/mercedes_benz.jpg', 'Mercedes-Benz Stadium', 'Atlanta, EE.UU.'),
(19, 60411, 'https://example.com/celtic_park.jpg', 'Celtic Park', 'Glasgow, Escocia'),
(20, 50000, 'https://example.com/baudouin.jpg', 'King Baudouin Stadium', 'Bruselas, Bélgica'),
(21, 50033, 'https://example.com/dragao.jpg', 'Estadio do Dragão', 'Porto, Portugal'),
(22, 54000, 'https://example.com/amsterdam_arena.jpg', 'Amsterdam Arena', 'Ámsterdam, Países Bajos'),
(23, 47309, 'https://example.com/yankee.jpg', 'Yankee Stadium', 'New York, EE.UU.'),
(24, 50817, 'https://example.com/ibrox.jpg', 'Ibrox Stadium', 'Glasgow, Escocia'),
(25, 29062, 'https://example.com/breydel.jpg', 'Jan Breydel Stadium', 'Brugge, Bélgica');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evento_partido`
--

CREATE TABLE `evento_partido` (
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `minuto_de_partido` int(11) NOT NULL,
  `tipo_evento_partido` longtext NOT NULL,
  `partido_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fase`
--

CREATE TABLE `fase` (
  `id` bigint(20) NOT NULL,
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
  `numero` int(11) NOT NULL,
  `simulada` bit(1) NOT NULL,
  `torneo_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `fecha`
--

INSERT INTO `fecha` (`id`, `numero`, `simulada`, `torneo_id`) VALUES
(1, 1, b'1', 1),
(2, 2, b'1', 1),
(3, 3, b'1', 1),
(4, 4, b'0', 1),
(5, 5, b'0', 1),
(6, 6, b'0', 1),
(7, 7, b'0', 1),
(8, 8, b'0', 1),
(9, 9, b'0', 1),
(10, 10, b'0', 1),
(11, 11, b'0', 1),
(12, 12, b'0', 1),
(13, 13, b'0', 1),
(14, 14, b'0', 1),
(15, 15, b'0', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `formacion_equipo`
--

CREATE TABLE `formacion_equipo` (
  `id` bigint(20) NOT NULL,
  `posicion_en_campo` varchar(255) NOT NULL,
  `equipo_id` bigint(20) NOT NULL,
  `jugador_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `formato_torneo`
--

CREATE TABLE `formato_torneo` (
  `id` bigint(20) NOT NULL,
  `tipo` varchar(255) NOT NULL,
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
  `id` bigint(20) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `edad` int(11) NOT NULL,
  `estado_fisico` double NOT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `lesionado` bit(1) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `numero_camiseta` int(11) NOT NULL,
  `posicion` varchar(255) NOT NULL,
  `rareza_jugador` varchar(255) NOT NULL,
  `rating` double NOT NULL,
  `equipo_id` bigint(20) DEFAULT NULL,
  `pais_id` bigint(20) DEFAULT NULL,
  `sobre_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pais`
--

CREATE TABLE `pais` (
  `id` bigint(20) NOT NULL,
  `bandera_url` varchar(255) DEFAULT NULL,
  `codigo_iso` varchar(2) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pais`
--

INSERT INTO `pais` (`id`, `bandera_url`, `codigo_iso`, `nombre`) VALUES
(1, 'https://flagcdn.com/ar.svg', 'AR', 'Argentina'),
(2, 'https://flagcdn.com/br.svg', 'BR', 'Brasil'),
(3, 'https://flagcdn.com/es.svg', 'ES', 'España'),
(4, 'https://flagcdn.com/fr.svg', 'FR', 'Francia'),
(5, 'https://flagcdn.com/de.svg', 'DE', 'Alemania'),
(6, 'https://flagcdn.com/it.svg', 'IT', 'Italia'),
(7, 'https://flagcdn.com/gb.svg', 'GB', 'Inglaterra'),
(8, 'https://flagcdn.com/mx.svg', 'MX', 'México'),
(9, 'https://flagcdn.com/uy.svg', 'UY', 'Uruguay'),
(10, 'https://flagcdn.com/cl.svg', 'CL', 'Chile'),
(11, 'https://flagcdn.com/pt.svg', 'PT', 'Portugal'),
(12, 'https://flagcdn.com/nl.svg', 'NL', 'Países Bajos'),
(13, 'https://flagcdn.com/us.svg', 'US', 'Estados Unidos'),
(14, 'https://flagcdn.com/be.svg', 'BE', 'Bélgica');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `partido`
--

CREATE TABLE `partido` (
  `id` bigint(20) NOT NULL,
  `goles_local` int(11) NOT NULL,
  `goles_visitante` int(11) NOT NULL,
  `resultado` varchar(255) NOT NULL,
  `equipo_local_id` bigint(20) NOT NULL,
  `equipo_visitante_id` bigint(20) NOT NULL,
  `fecha_id` bigint(20) DEFAULT NULL,
  `estado_partido` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `partido`
--

INSERT INTO `partido` (`id`, `goles_local`, `goles_visitante`, `resultado`, `equipo_local_id`, `equipo_visitante_id`, `fecha_id`, `estado_partido`) VALUES
(10, 4, 4, 'EMPATE', 1, 16, 1, ''),
(11, 1, 3, 'VISITANTE_GANA', 2, 15, 1, ''),
(12, 3, 4, 'VISITANTE_GANA', 3, 14, 1, ''),
(13, 4, 0, 'LOCAL_GANA', 4, 13, 1, ''),
(14, 2, 2, 'EMPATE', 5, 12, 1, ''),
(15, 2, 3, 'VISITANTE_GANA', 6, 11, 1, ''),
(16, 1, 4, 'VISITANTE_GANA', 1, 3, 2, ''),
(17, 2, 1, 'LOCAL_GANA', 2, 4, 2, ''),
(18, 3, 3, 'EMPATE', 5, 7, 2, ''),
(19, 4, 0, 'LOCAL_GANA', 6, 8, 2, ''),
(20, 0, 0, 'EMPATE', 9, 11, 2, ''),
(21, 0, 1, 'VISITANTE_GANA', 10, 12, 2, ''),
(22, 2, 1, 'LOCAL_GANA', 13, 15, 2, ''),
(23, 0, 3, 'VISITANTE_GANA', 14, 16, 2, ''),
(24, 0, 1, 'VISITANTE_GANA', 1, 4, 3, ''),
(25, 1, 0, 'LOCAL_GANA', 2, 3, 3, ''),
(26, 3, 3, 'EMPATE', 5, 8, 3, ''),
(27, 2, 2, 'EMPATE', 6, 7, 3, ''),
(28, 2, 0, 'LOCAL_GANA', 9, 12, 3, ''),
(29, 0, 3, 'VISITANTE_GANA', 10, 11, 3, ''),
(30, 4, 0, 'LOCAL_GANA', 13, 16, 3, ''),
(31, 1, 4, 'VISITANTE_GANA', 14, 15, 3, '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sobre`
--

CREATE TABLE `sobre` (
  `tipo_sobre` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `imagen_url` varchar(255) DEFAULT NULL,
  `precio` double NOT NULL,
  `titulo` varchar(100) NOT NULL,
  `usuario_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `torneo`
--

CREATE TABLE `torneo` (
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `estado` varchar(255) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `formato_torneo_id` bigint(20) DEFAULT NULL,
  `torneo_copa_id` bigint(20) DEFAULT NULL,
  `torneo_liga_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `torneo`
--

INSERT INTO `torneo` (`id`, `descripcion`, `estado`, `nombre`, `formato_torneo_id`, `torneo_copa_id`, `torneo_liga_id`) VALUES
(1, 'Torneo de prueba', 'ABIERTO', 'Campeonato FutCode', 2, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `torneo_copa`
--

CREATE TABLE `torneo_copa` (
  `torneo_id` bigint(20) NOT NULL,
  `fase_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `torneo_liga`
--

CREATE TABLE `torneo_liga` (
  `torneo_id` bigint(20) NOT NULL,
  `fechas` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL,
  `activo` bit(1) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `monedas` double DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `rol` varchar(255) DEFAULT NULL,
  `equipo_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `activo`, `email`, `monedas`, `password`, `rol`, `equipo_id`) VALUES
(1, b'1', 'aaaaa@mail.com', 10000, '$2a$10$48aC.N6QgvolX3Clw4e3lO4f8TfvHC7BQXatsCJ3dEnoC13IqyY9K', 'USER', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `club`
--
ALTER TABLE `club`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKa5053fgoa15t0hx0muobx5mos` (`estadio_id`),
  ADD KEY `FKgb3m9x44yj5vtkrwa6vka93xq` (`pais_id`);

--
-- Indices de la tabla `equipo`
--
ALTER TABLE `equipo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKr5y3e0uogfjql6ec8wig1sw20` (`club_id`),
  ADD KEY `FKtb74qpnkgxcq6miahjicksvom` (`esquema_id`),
  ADD KEY `FK4ilxa10ajq8v0rv2adi5leb3s` (`usuario_id`);

--
-- Indices de la tabla `equipo_torneo`
--
ALTER TABLE `equipo_torneo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKsqkhmjrw81g3awa2wmkgv3f20` (`equipo_id`),
  ADD KEY `FKfu168st6wu5cwupu0q2e5533` (`torneo_id`);

--
-- Indices de la tabla `esquema`
--
ALTER TABLE `esquema`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `estadio`
--
ALTER TABLE `estadio`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `evento_partido`
--
ALTER TABLE `evento_partido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKouct5wkbjopino8qfu0sndgdm` (`partido_id`);

--
-- Indices de la tabla `fase`
--
ALTER TABLE `fase`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `fecha`
--
ALTER TABLE `fecha`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7uem85nmy70bk8574igbjwfgn` (`torneo_id`);

--
-- Indices de la tabla `formacion_equipo`
--
ALTER TABLE `formacion_equipo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1mi0g4v6h0cgspm60xwh1lhcy` (`equipo_id`),
  ADD KEY `FK7i67ejgicdvtoavo5ekifakuw` (`jugador_id`);

--
-- Indices de la tabla `formato_torneo`
--
ALTER TABLE `formato_torneo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7xdytyovowi9r1dp2hoalqpfq` (`fase_id`);

--
-- Indices de la tabla `jugador`
--
ALTER TABLE `jugador`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKcc4h4wqekf4kicyifbrit5sp` (`equipo_id`),
  ADD KEY `FKk6rdacuyy2m6nd0vlxijmft2k` (`pais_id`),
  ADD KEY `FKjosrmdh2ir6p8miuebs52r9ub` (`sobre_id`);

--
-- Indices de la tabla `pais`
--
ALTER TABLE `pais`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `partido`
--
ALTER TABLE `partido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7d0ff294y8ul2ego1km5r0tmq` (`equipo_local_id`),
  ADD KEY `FKksi4gq2o9almp8a1gfnadjfu1` (`equipo_visitante_id`),
  ADD KEY `FKti8kfserc69ri8o5rfe2cs8nx` (`fecha_id`);

--
-- Indices de la tabla `sobre`
--
ALTER TABLE `sobre`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKf32br6p2s0na0w0tkmgn5f0p9` (`usuario_id`);

--
-- Indices de la tabla `torneo`
--
ALTER TABLE `torneo`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_n0a32rfinot2bm27o52v22lvt` (`torneo_copa_id`),
  ADD UNIQUE KEY `UK_ncjx5dmyf938gtehdokndj5ty` (`torneo_liga_id`),
  ADD KEY `FKfm7k6pcp9qv5s3k11hn20326b` (`formato_torneo_id`);

--
-- Indices de la tabla `torneo_copa`
--
ALTER TABLE `torneo_copa`
  ADD PRIMARY KEY (`torneo_id`),
  ADD KEY `FKqav047qhnyagq50n50tmo7639` (`fase_id`);

--
-- Indices de la tabla `torneo_liga`
--
ALTER TABLE `torneo_liga`
  ADD PRIMARY KEY (`torneo_id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5ijjidlhoy9qvv6qd4asqjiu4` (`equipo_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `equipo`
--
ALTER TABLE `equipo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT de la tabla `equipo_torneo`
--
ALTER TABLE `equipo_torneo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `esquema`
--
ALTER TABLE `esquema`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `fecha`
--
ALTER TABLE `fecha`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `formacion_equipo`
--
ALTER TABLE `formacion_equipo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `formato_torneo`
--
ALTER TABLE `formato_torneo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `jugador`
--
ALTER TABLE `jugador`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `partido`
--
ALTER TABLE `partido`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT de la tabla `sobre`
--
ALTER TABLE `sobre`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `torneo`
--
ALTER TABLE `torneo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `club`
--
ALTER TABLE `club`
  ADD CONSTRAINT `FKa5053fgoa15t0hx0muobx5mos` FOREIGN KEY (`estadio_id`) REFERENCES `estadio` (`id`),
  ADD CONSTRAINT `FKgb3m9x44yj5vtkrwa6vka93xq` FOREIGN KEY (`pais_id`) REFERENCES `pais` (`id`);

--
-- Filtros para la tabla `equipo`
--
ALTER TABLE `equipo`
  ADD CONSTRAINT `FK4ilxa10ajq8v0rv2adi5leb3s` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKr5y3e0uogfjql6ec8wig1sw20` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`),
  ADD CONSTRAINT `FKtb74qpnkgxcq6miahjicksvom` FOREIGN KEY (`esquema_id`) REFERENCES `esquema` (`id`);

--
-- Filtros para la tabla `equipo_torneo`
--
ALTER TABLE `equipo_torneo`
  ADD CONSTRAINT `FKfu168st6wu5cwupu0q2e5533` FOREIGN KEY (`torneo_id`) REFERENCES `torneo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FKsqkhmjrw81g3awa2wmkgv3f20` FOREIGN KEY (`equipo_id`) REFERENCES `equipo` (`id`);

--
-- Filtros para la tabla `evento_partido`
--
ALTER TABLE `evento_partido`
  ADD CONSTRAINT `FKouct5wkbjopino8qfu0sndgdm` FOREIGN KEY (`partido_id`) REFERENCES `partido` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `fecha`
--
ALTER TABLE `fecha`
  ADD CONSTRAINT `FK7uem85nmy70bk8574igbjwfgn` FOREIGN KEY (`torneo_id`) REFERENCES `torneo` (`id`);

--
-- Filtros para la tabla `formacion_equipo`
--
ALTER TABLE `formacion_equipo`
  ADD CONSTRAINT `FK1mi0g4v6h0cgspm60xwh1lhcy` FOREIGN KEY (`equipo_id`) REFERENCES `equipo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `FK7i67ejgicdvtoavo5ekifakuw` FOREIGN KEY (`jugador_id`) REFERENCES `jugador` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `formato_torneo`
--
ALTER TABLE `formato_torneo`
  ADD CONSTRAINT `FK7xdytyovowi9r1dp2hoalqpfq` FOREIGN KEY (`fase_id`) REFERENCES `fase` (`id`);

--
-- Filtros para la tabla `jugador`
--
ALTER TABLE `jugador`
  ADD CONSTRAINT `FKcc4h4wqekf4kicyifbrit5sp` FOREIGN KEY (`equipo_id`) REFERENCES `equipo` (`id`),
  ADD CONSTRAINT `FKjosrmdh2ir6p8miuebs52r9ub` FOREIGN KEY (`sobre_id`) REFERENCES `sobre` (`id`),
  ADD CONSTRAINT `FKk6rdacuyy2m6nd0vlxijmft2k` FOREIGN KEY (`pais_id`) REFERENCES `pais` (`id`);

--
-- Filtros para la tabla `partido`
--
ALTER TABLE `partido`
  ADD CONSTRAINT `FK7d0ff294y8ul2ego1km5r0tmq` FOREIGN KEY (`equipo_local_id`) REFERENCES `equipo` (`id`),
  ADD CONSTRAINT `FKksi4gq2o9almp8a1gfnadjfu1` FOREIGN KEY (`equipo_visitante_id`) REFERENCES `equipo` (`id`),
  ADD CONSTRAINT `FKti8kfserc69ri8o5rfe2cs8nx` FOREIGN KEY (`fecha_id`) REFERENCES `fecha` (`id`);

--
-- Filtros para la tabla `sobre`
--
ALTER TABLE `sobre`
  ADD CONSTRAINT `FKf32br6p2s0na0w0tkmgn5f0p9` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `torneo`
--
ALTER TABLE `torneo`
  ADD CONSTRAINT `FK1c87w3feloua6hscp6j306war` FOREIGN KEY (`torneo_copa_id`) REFERENCES `torneo_copa` (`torneo_id`),
  ADD CONSTRAINT `FKfm7k6pcp9qv5s3k11hn20326b` FOREIGN KEY (`formato_torneo_id`) REFERENCES `formato_torneo` (`id`),
  ADD CONSTRAINT `FKoj16aurr2o3s1c4pkfmc85fof` FOREIGN KEY (`torneo_liga_id`) REFERENCES `torneo_liga` (`torneo_id`);

--
-- Filtros para la tabla `torneo_copa`
--
ALTER TABLE `torneo_copa`
  ADD CONSTRAINT `FK9xdbwdq44whfal30dq4gkyjkr` FOREIGN KEY (`torneo_id`) REFERENCES `torneo` (`id`),
  ADD CONSTRAINT `FKqav047qhnyagq50n50tmo7639` FOREIGN KEY (`fase_id`) REFERENCES `fase` (`id`);

--
-- Filtros para la tabla `torneo_liga`
--
ALTER TABLE `torneo_liga`
  ADD CONSTRAINT `FK7had505rs68ie7evtlm8belta` FOREIGN KEY (`torneo_id`) REFERENCES `torneo` (`id`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `FK5ijjidlhoy9qvv6qd4asqjiu4` FOREIGN KEY (`equipo_id`) REFERENCES `equipo` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
