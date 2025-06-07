-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-06-2025 a las 16:49:36
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
(5, 'AC Milan', 6, 5, 'https://upload.wikimedia.org/wikipedia/en/d/d0/AC_Milan_logo.svg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo`
--

CREATE TABLE `equipo` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `club_id` int(11) DEFAULT NULL,
  `formacion_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo_torneo`
--

CREATE TABLE `equipo_torneo` (
  `id` int(11) NOT NULL,
  `posicion` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `nombre_imagen` varchar(255) DEFAULT NULL,
  `partidos_jugados` int(11) NOT NULL DEFAULT 0,
  `partidos_ganados` int(11) NOT NULL DEFAULT 0,
  `partidos_empatados` int(11) NOT NULL DEFAULT 0,
  `partidos_perdidos` int(11) NOT NULL DEFAULT 0,
  `goles_a_favor` int(11) NOT NULL DEFAULT 0,
  `goles_en_contra` int(11) NOT NULL DEFAULT 0,
  `puntos` int(11) NOT NULL DEFAULT 0,
  `torneo_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(5, 'San Siro', 80018, 'Milán, Italia', 'https://upload.wikimedia.org/wikipedia/commons/3/3e/San_Siro_Stadium_-_panoramio.jpg');

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
-- Estructura de tabla para la tabla `formacion`
--

CREATE TABLE `formacion` (
  `id` int(11) NOT NULL,
  `esquema` enum('CUATRO_TRES_TRES','CUATRO_CUATRO_DOS','TRES_CINCO_DOS','CINCO_TRES_DOS','TRES_CUATRO_TRES') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `formato_torneo`
--

CREATE TABLE `formato_torneo` (
  `id` int(11) NOT NULL,
  `tipo` enum('LIGA','COPA') NOT NULL,
  `rondas` int(11) DEFAULT NULL,
  `equipos_por_grupo` int(11) DEFAULT NULL,
  `equipos_que_avanzan` int(11) DEFAULT NULL,
  `tipo_grupo` tinyint(1) DEFAULT 0,
  `ida_y_vuelta` tinyint(1) DEFAULT 1,
  `puntos_por_victoria` int(11) DEFAULT 3,
  `puntos_por_empate` int(11) DEFAULT 1,
  `puntos_por_derrota` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `formato_torneo`
--

INSERT INTO `formato_torneo` (`id`, `tipo`, `rondas`, `equipos_por_grupo`, `equipos_que_avanzan`, `tipo_grupo`, `ida_y_vuelta`, `puntos_por_victoria`, `puntos_por_empate`, `puntos_por_derrota`) VALUES
(1, 'COPA', NULL, 4, 2, 0, 1, 3, 1, 0),
(2, 'LIGA', NULL, NULL, NULL, 0, 1, 3, 1, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `formato_torneo_fases`
--

CREATE TABLE `formato_torneo_fases` (
  `formato_torneo_id` int(11) NOT NULL,
  `fase` varchar(50) NOT NULL,
  `equipos` int(11) DEFAULT NULL,
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `formato_torneo_fases`
--

INSERT INTO `formato_torneo_fases` (`formato_torneo_id`, `fase`, `equipos`, `id`) VALUES
(1, 'Cuartos de final', 8, 0),
(1, 'Fase de grupos', 32, 0),
(1, 'Final', 2, 0),
(1, 'Octavos de final', 16, 0),
(1, 'Semifinales', 4, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo`
--

CREATE TABLE `grupo` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `torneo_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo_equipo`
--

CREATE TABLE `grupo_equipo` (
  `grupo_id` int(11) NOT NULL,
  `equipo_id` int(11) NOT NULL,
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupo_posicion`
--

CREATE TABLE `grupo_posicion` (
  `id` int(11) NOT NULL,
  `grupo_id` int(11) NOT NULL,
  `equipo_id` int(11) NOT NULL,
  `partidos_jugados` int(11) DEFAULT 0,
  `partidos_ganados` int(11) DEFAULT 0,
  `partidos_empatados` int(11) DEFAULT 0,
  `partidos_perdidos` int(11) DEFAULT 0,
  `goles_a_favor` int(11) DEFAULT 0,
  `goles_en_contra` int(11) DEFAULT 0,
  `puntos` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `club_id` int(11) DEFAULT NULL,
  `pais_id` int(11) DEFAULT NULL,
  `rareza_jugador` enum('NORMAL','RARO','EPICO','LEYENDA') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jugador_posiciones`
--

CREATE TABLE `jugador_posiciones` (
  `jugador_id` int(11) NOT NULL,
  `posicion_natural` enum('ARQUERO','DEFENSOR_CENTRAL','DEFENSOR_LATERAL','VOLANTE_CENTRAL','VOLANTE_LATERAL','VOLANTE_OFENSIVO','EXTREMO','DELANTERO_CENTRAL','DELANTERO','DEFENSOR','MEDIOCAMPISTA') NOT NULL,
  `posicion_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
(10, 'Chile', 'CL', 'https://flagcdn.com/cl.svg');

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
-- Estructura de tabla para la tabla `posicion_jugador`
--

CREATE TABLE `posicion_jugador` (
  `id` int(11) NOT NULL,
  `posicion_en_campo` enum('ARQUERO','DEFENSOR_CENTRAL','DEFENSOR_LATERAL','VOLANTE_CENTRAL','VOLANTE_LATERAL','VOLANTE_OFENSIVO','EXTREMO','DELANTERO_CENTRAL','DELANTERO','DEFENSOR','MEDIOCAMPISTA') NOT NULL,
  `jugador_id` int(11) NOT NULL,
  `formacion_id` int(11) NOT NULL
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
  `imagen_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `torneo`
--

CREATE TABLE `torneo` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `formato_torneo_id` int(11) DEFAULT NULL,
  `estado` enum('ABIERTO','EN_CURSO','FINALIZADO') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `torneo`
--

INSERT INTO `torneo` (`id`, `nombre`, `descripcion`, `formato_torneo_id`, `estado`) VALUES
(1, 'Copa Mundial 2025', 'Torneo eliminatorio con 16 equipos', 1, 'ABIERTO'),
(2, 'Copa Libertadores 2025', 'Torneo internacional de clubes más prestigioso de América del Sur, con equipos argentinos como River Plate y Boca Juniors compitiendo por el título.', 1, 'ABIERTO'),
(3, 'Liga Profesional Argentina 2025', 'Competencia de fútbol de primera división en Argentina, con 28 equipos luchando por el campeonato nacional.', 2, 'EN_CURSO'),
(4, 'Copa Argentina 2025', 'Torneo eliminatorio que incluye equipos de todas las categorías del fútbol argentino, con gran emoción en cada fase.', 1, 'ABIERTO'),
(5, 'Liga Madero 2025', 'Liga regional en la zona de Buenos Aires, destacando el talento local en un formato de puntos corridos.', 2, 'EN_CURSO'),
(6, 'Copa Sudamericana 2025', 'Competencia internacional de clubes sudamericanos, con equipos argentinos buscando gloria continental.', 1, 'FINALIZADO'),
(7, 'Liga Interior Argentina 2025', 'Torneo de fútbol para equipos del interior de Argentina, promoviendo el talento fuera de las grandes ciudades.', 2, 'ABIERTO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rol` varchar(50) NOT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `email`, `password`, `rol`, `activo`) VALUES
(1, 'test@unlam.edu.ar', 'test', 'ADMIN', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_sobre`
--

CREATE TABLE `usuario_sobre` (
  `Usuario_id` bigint(20) NOT NULL,
  `sobres_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  ADD KEY `formacion_id` (`formacion_id`);

--
-- Indices de la tabla `equipo_torneo`
--
ALTER TABLE `equipo_torneo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `torneo_id` (`torneo_id`);

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
-- Indices de la tabla `formacion`
--
ALTER TABLE `formacion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `formato_torneo`
--
ALTER TABLE `formato_torneo`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `formato_torneo_fases`
--
ALTER TABLE `formato_torneo_fases`
  ADD PRIMARY KEY (`formato_torneo_id`,`fase`);

--
-- Indices de la tabla `grupo`
--
ALTER TABLE `grupo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `torneo_id` (`torneo_id`);

--
-- Indices de la tabla `grupo_equipo`
--
ALTER TABLE `grupo_equipo`
  ADD PRIMARY KEY (`grupo_id`,`equipo_id`),
  ADD KEY `equipo_id` (`equipo_id`);

--
-- Indices de la tabla `grupo_posicion`
--
ALTER TABLE `grupo_posicion`
  ADD PRIMARY KEY (`id`),
  ADD KEY `grupo_id` (`grupo_id`),
  ADD KEY `equipo_id` (`equipo_id`);

--
-- Indices de la tabla `jugador`
--
ALTER TABLE `jugador`
  ADD PRIMARY KEY (`id`),
  ADD KEY `club_id` (`club_id`),
  ADD KEY `pais_id` (`pais_id`);

--
-- Indices de la tabla `jugador_posiciones`
--
ALTER TABLE `jugador_posiciones`
  ADD PRIMARY KEY (`jugador_id`,`posicion_natural`);

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
-- Indices de la tabla `posicion_jugador`
--
ALTER TABLE `posicion_jugador`
  ADD PRIMARY KEY (`id`),
  ADD KEY `jugador_id` (`jugador_id`),
  ADD KEY `formacion_id` (`formacion_id`);

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
  ADD KEY `formato_torneo_id` (`formato_torneo_id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `usuario_sobre`
--
ALTER TABLE `usuario_sobre`
  ADD UNIQUE KEY `UK_c9c9isw8x8tunow1dr5f7vhkf` (`sobres_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `club`
--
ALTER TABLE `club`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `equipo`
--
ALTER TABLE `equipo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `equipo_torneo`
--
ALTER TABLE `equipo_torneo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `estadio`
--
ALTER TABLE `estadio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `evento_partido`
--
ALTER TABLE `evento_partido`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `formacion`
--
ALTER TABLE `formacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `formato_torneo`
--
ALTER TABLE `formato_torneo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `grupo`
--
ALTER TABLE `grupo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `grupo_posicion`
--
ALTER TABLE `grupo_posicion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `jugador`
--
ALTER TABLE `jugador`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pais`
--
ALTER TABLE `pais`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `partido`
--
ALTER TABLE `partido`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `posicion_jugador`
--
ALTER TABLE `posicion_jugador`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sobre`
--
ALTER TABLE `sobre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `torneo`
--
ALTER TABLE `torneo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

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
  ADD CONSTRAINT `equipo_ibfk_2` FOREIGN KEY (`formacion_id`) REFERENCES `formacion` (`id`) ON DELETE SET NULL;

--
-- Filtros para la tabla `equipo_torneo`
--
ALTER TABLE `equipo_torneo`
  ADD CONSTRAINT `equipo_torneo_ibfk_1` FOREIGN KEY (`torneo_id`) REFERENCES `torneo` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `evento_partido`
--
ALTER TABLE `evento_partido`
  ADD CONSTRAINT `evento_partido_ibfk_1` FOREIGN KEY (`partido_id`) REFERENCES `partido` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `formato_torneo_fases`
--
ALTER TABLE `formato_torneo_fases`
  ADD CONSTRAINT `formato_torneo_fases_ibfk_1` FOREIGN KEY (`formato_torneo_id`) REFERENCES `formato_torneo` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `grupo`
--
ALTER TABLE `grupo`
  ADD CONSTRAINT `grupo_ibfk_1` FOREIGN KEY (`torneo_id`) REFERENCES `torneo` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `grupo_equipo`
--
ALTER TABLE `grupo_equipo`
  ADD CONSTRAINT `grupo_equipo_ibfk_1` FOREIGN KEY (`grupo_id`) REFERENCES `grupo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `grupo_equipo_ibfk_2` FOREIGN KEY (`equipo_id`) REFERENCES `equipo` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `grupo_posicion`
--
ALTER TABLE `grupo_posicion`
  ADD CONSTRAINT `grupo_posicion_ibfk_1` FOREIGN KEY (`grupo_id`) REFERENCES `grupo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `grupo_posicion_ibfk_2` FOREIGN KEY (`equipo_id`) REFERENCES `equipo` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `jugador`
--
ALTER TABLE `jugador`
  ADD CONSTRAINT `jugador_ibfk_1` FOREIGN KEY (`club_id`) REFERENCES `club` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `jugador_ibfk_2` FOREIGN KEY (`pais_id`) REFERENCES `pais` (`id`) ON DELETE SET NULL;

--
-- Filtros para la tabla `jugador_posiciones`
--
ALTER TABLE `jugador_posiciones`
  ADD CONSTRAINT `jugador_posiciones_ibfk_1` FOREIGN KEY (`jugador_id`) REFERENCES `jugador` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `partido`
--
ALTER TABLE `partido`
  ADD CONSTRAINT `partido_ibfk_1` FOREIGN KEY (`equipo_local_id`) REFERENCES `equipo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `partido_ibfk_2` FOREIGN KEY (`equipo_visitante_id`) REFERENCES `equipo` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `partido_ibfk_3` FOREIGN KEY (`torneo_id`) REFERENCES `torneo` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `posicion_jugador`
--
ALTER TABLE `posicion_jugador`
  ADD CONSTRAINT `posicion_jugador_ibfk_1` FOREIGN KEY (`jugador_id`) REFERENCES `jugador` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `posicion_jugador_ibfk_2` FOREIGN KEY (`formacion_id`) REFERENCES `formacion` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `torneo`
--
ALTER TABLE `torneo`
  ADD CONSTRAINT `torneo_ibfk_1` FOREIGN KEY (`formato_torneo_id`) REFERENCES `formato_torneo` (`id`) ON DELETE SET NULL;

--
-- Filtros para la tabla `usuario_sobre`
--
ALTER TABLE `usuario_sobre`
  ADD CONSTRAINT `FKdkvw82ysebsc2gy2j6t1ug04l` FOREIGN KEY (`sobres_id`) REFERENCES `sobre` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
