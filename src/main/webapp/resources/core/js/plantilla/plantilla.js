$(function () {
   const DEFAULT_IMAGE_SRC = $("#field").data("default-img");

    const MAX_PLAYERS_ON_FIELD = 11;
    let playersOnField = 0;

   const CONFIG = {
      // Rango de posiciones en el eje Y (vertical) para los marcadores (% del alto del campo).
      POSITION_RANGES: {
         MIN_Y: 15, // Mínimo Y para posiciones con alta densidad (4+ jugadores).
         MAX_Y: 75, // Máximo Y para posiciones con alta densidad.
         DEFAULT_MIN_Y: 20, // Mínimo Y para posiciones con menos densidad (<4 jugadores).
         DEFAULT_MAX_Y: 70, // Máximo Y para posiciones con menos densidad.
      },
      // Espaciado entre marcadores (en %).
      PADDINGS: {
         DEFAULT: 8, // Espaciado estándar para líneas con menos de 4 jugadores.
         HIGH_DENSITY: 12, // Espaciado mayor para líneas con 4 o más jugadores.
      },
      // Zonas del campo en el eje X (horizontal, en % del ancho del campo).
      FIELD_ZONES: {
         DEFENSE_START: 25, // Donde empiezan los defensores.
         MIDFIELD_DEFENSIVE_END: 40, // Fin de la zona defensiva del mediocampo.
         MIDFIELD_OFFENSIVE_END: 55, // Fin de la zona ofensiva del mediocampo.
         ATTACK_START: 70, // Donde empiezan los delanteros.
      },
      // Calificaciones predeterminadas por rol (no se usan directamente, pero podrían servir para lógica adicional).
      DEFAULT_RATINGS: {
         GOALKEEPER: 88.0,
         DEFENDER: 87.0,
         MIDFIELDER: 86.0,
         FORWARD: 89.0,
      },
   };

    const PositionGenerator = {
       getPositions(defenders, midfielders, forwards) {
         const {
            DEFENSE_START,
            MIDFIELD_DEFENSIVE_END,
            MIDFIELD_OFFENSIVE_END,
            ATTACK_START,
         } = CONFIG.FIELD_ZONES;

         return {
            goalkeeper: this.getGoalkeeperPosition(DEFENSE_START), // Posición del arquero.
            defenders: this.getPositionsForLine(defenders, DEFENSE_START), // Posiciones de los defensores.
            midfielders: this.getMidfielderPositions(midfielders, MIDFIELD_DEFENSIVE_END, MIDFIELD_OFFENSIVE_END), // Posiciones de los mediocampistas.
            forwards: this.getPositionsForLine(forwards, ATTACK_START), // Posiciones de los delanteros.
         };
      },

     getGoalkeeperPosition(defenseStart) {
         return [{ x: defenseStart * 0.4, y: 45 }];
      },

      getPositionsForLine(playerCount, xPosition) {
         if (playerCount === 0) return [];

         // Extrae los rangos y espaciados de CONFIG.
         const { MIN_Y, MAX_Y, DEFAULT_MIN_Y, DEFAULT_MAX_Y } = CONFIG.POSITION_RANGES;
         const { DEFAULT: defaultPadding, HIGH_DENSITY: highDensityPadding } = CONFIG.PADDINGS;

         // Determina el rango Y según la cantidad de jugadores (más jugadores = mayor rango).
         const yRange = playerCount >= 4 ? MAX_Y - MIN_Y : DEFAULT_MAX_Y - DEFAULT_MIN_Y;
         // Usa un espaciado mayor si hay 4 o más jugadores.
         const padding = playerCount >= 4 ? highDensityPadding : defaultPadding;
         // Calcula el paso entre jugadores (espacio disponible dividido entre ellos).
         const step = playerCount > 1 ? (yRange - padding * (playerCount - 1)) / (playerCount - 1) : 0;
         // Define el Y inicial según la densidad.
         const baseY = playerCount >= 4 ? MIN_Y : DEFAULT_MIN_Y;

         // Genera las posiciones para cada jugador.
         return Array.from({ length: playerCount }, (_, i) => {
            // Para 2 jugadores, los pone en Y=35 y Y=65 (distribuidos manualmente).
            // Para más jugadores, los distribuye uniformemente con el paso calculado.
            const y = playerCount === 2 ? (i === 0 ? 35 : 65) : baseY + i * (step + padding);
            return { x: xPosition, y: this.clampY(y, playerCount) };
         });
      },

      // Calcula las posiciones para los mediocampistas, colocándolos en el centro del mediocampo.
      // Teoría: Usa el promedio entre las zonas defensiva y ofensiva del mediocampo para centrarlos.
      getMidfielderPositions(totalMidfielders, defensiveEnd, offensiveEnd) {
         const midX = (defensiveEnd + offensiveEnd) / 2; // Calcula el centro del mediocampo.
         return this.getPositionsForLine(totalMidfielders, midX); // Usa el mismo método para distribuirlos.
      },

      // Limita las posiciones Y para que no se salgan de los rangos definidos.
      // Teoría: Esto asegura que los marcadores no se salgan del campo visualmente.
      clampY(value, playerCount = 0) {
         const { MIN_Y, MAX_Y, DEFAULT_MIN_Y, DEFAULT_MAX_Y } = CONFIG.POSITION_RANGES;
         return Math.min(playerCount >= 4 ? MAX_Y : DEFAULT_MAX_Y, Math.max(playerCount >= 4 ? MIN_Y : DEFAULT_MIN_Y, value));
      },
   };

   // Objeto que gestiona las imágenes de los marcadores en el campo.
   // Teoría: Controla si un marcador muestra una imagen genérica o la de un jugador asignado.
   const ImageManager = {
      // Establece la imagen predeterminada (genérica) en un marcador.
      // Teoría: Se usa cuando un marcador está vacío o se remueve un jugador.
      setDefaultImage($marker) {
         $marker.find(".marker-img").attr("src", DEFAULT_IMAGE_SRC).removeClass("custom-image");
      },

      // Establece la imagen de un jugador en un marcador, obteniéndola de la tarjeta del jugador.
      // Teoría: Cambia la imagen genérica por la del jugador asignado.
      setPlayerImage($marker, $card) {
         const imgSrc = $card.find(".card-img").attr("src") || DEFAULT_IMAGE_SRC; // Usa la imagen de la tarjeta o la predeterminada.
         this.setImage($marker, imgSrc, imgSrc !== DEFAULT_IMAGE_SRC); // Aplica la imagen y marca si es personalizada.
      },

      // Aplica una imagen al marcador y ajusta la clase "custom-image".
      // Teoría: La clase "custom-image" puede usarse para estilos específicos (como bordes o efectos).
      setImage($marker, src, isCustom = false) {
         $marker.find(".marker-img").attr("src", src).toggleClass("custom-image", isCustom);
      },
   };

   // Objeto que crea y gestiona los marcadores (las "fichas" de posición) en el campo.
   // Teoría: Genera los elementos HTML dinámicos para cada posición y maneja su comportamiento.
   const MarkerFactory = {
      // Crea un marcador con un ID único, posición (x, y), y rol (Arquero, Defensor, etc.).
      // Teoría: Este metodo genera el HTML dinámico para cada posición y lo prepara para la interacción.
      create({ role, position, isDefault = true }) {
         const markerId = `marker-${role.toLowerCase()}-${Date.now()}`; // ID único para evitar conflictos.
         const $marker = $(`
            <div id="${markerId}" class="position-marker ${isDefault ? "default-marker" : ""}" style="left: ${position.x}%; top: ${position.y}%;">
               <div class="marker-card">
                  <img class="marker-img" alt="${role}" />
                  <div class="marker-info">
                     <div class="marker-name"></div>
                  </div>
               </div>
            </div>
         `);
         ImageManager.setDefaultImage($marker); // Aplica la imagen predeterminada al marcador.
         this.setupMarkerBehavior($marker, isDefault); // Configura el comportamiento (doble clic para remover).
         return $marker; // Devuelve el marcador para que se añada al DOM.
      },

      // Configura el comportamiento del marcador (permite remover un jugador con doble clic).
      // Teoría: Esto permite al usuario interactuar con los marcadores para liberar una posición.
      setupMarkerBehavior($marker, isDefault) {
         if (!isDefault) return; // Solo aplica a marcadores predeterminados.

         $marker.on("dblclick", () => {
            if ($marker.data("occupied")) this.removePlayerFromMarker($marker); // Si está ocupado, remueve al jugador.
         });
      },

      removePlayerFromMarker($marker) {
         ImageManager.setDefaultImage($marker); // Vuelve a la imagen predeterminada.
         $marker.find(".marker-number").text(""); // Limpia el número.
         $marker.find(".marker-rating").text(""); // Limpia el rating.
         $marker.find(".marker-name").text(""); // Limpia el nombre.
         $marker.removeClass("occupied").data("occupied", false); // Marca el marcador como no ocupado.

         const playerId = $marker.data("player-id"); // Obtiene el ID del jugador asignado.
         const $card = this.findPlayerCard(playerId); // Busca la tarjeta correspondiente.
         this.resetPlayerCard($card); // Reactiva la tarjeta.

         $marker.removeData("player-id"); // Elimina el ID del marcador.
         playersOnField--; // Decrementa el contador de jugadores.
         console.log(`Jugador removido. playersOnField: ${playersOnField}`);
      },

       findPlayerCard(playerId) {
         return playerId ? $(`.player-card[data-player-id="${playerId}"]`) : $(); // Devuelve la tarjeta o un elemento vacío si no hay ID.
      },

      resetPlayerCard($card) {
         if (!$card.length) return; // Si no existe la tarjeta, no hace nada.

         // Restablece los estilos de la tarjeta.
         $card.removeClass("disabled-card").css({
            opacity: "1",
            cursor: "grab",
            pointerEvents: "auto",
         });
         $card.attr("title", "Arrastra al campo"); // Actualiza el tooltip.

         // Intenta reactivar la funcionalidad draggable; si falla, la reinicia.
         try {
            $card.draggable("enable");
         } catch (e) {
            $card.draggable({
               revert: "invalid",
               helper: "clone",
               cursor: "grabbing",
               opacity: 0.8,
               zIndex: 100,
            });
         }
      },
   };

    const TeamFormation = {

      initializeField() {
         const $field = $("#field"); // Selecciona el elemento #field (el campo).
         const formationText = $field.data("esquema"); // Obtiene el esquema (ej. "4-4-2") desde el atributo data-esquema.
         if (!formationText || !/^\d+-\d+(-\d+)?$/.test(formationText)) {
            console.error("Formación inválida:", formationText);
            return;
         }

         // Convierte el esquema en números (ej. "4-4-2" → 4 defensores, 4 mediocampistas, 2 delanteros).
         const { defenders, midfielders, forwards } = this.parseFormation(formationText);
         // Calcula las posiciones de los marcadores.
         const positions = PositionGenerator.getPositions(defenders, midfielders, forwards);
         this.placePlayers($field, positions);
      },

       placePlayers($field, positions) {
         const $cards = $(".player-card").not(".disabled-card"); // Selecciona las tarjetas de jugadores disponibles.
         const totalNeeded = this.getTotalPlayersNeeded(positions); // Calcula cuántos jugadores se necesitan.

         // Valida que haya suficientes jugadores disponibles.
         if ($cards.length < totalNeeded) {
            console.warn(`Se necesitan ${totalNeeded} jugadores, pero solo hay ${$cards.length}.`);
            return;
         }

         $field.find(".position-marker").remove(); // Limpia marcadores previos.
         let cardIndex = 0; // Índice para recorrer las tarjetas.

         // Coloca al arquero primero.
         this.placeGoalkeeper($field, positions.goalkeeper[0], $cards.eq(cardIndex++));
         // Coloca a los defensores, mediocampistas y delanteros, actualizando el índice.
         cardIndex = this.placeRolePlayers($field, positions.defenders, "Defensor", $cards, cardIndex);
         cardIndex = this.placeRolePlayers($field, positions.midfielders, "Mediocampista", $cards, cardIndex);
         this.placeRolePlayers($field, positions.forwards, "Delantero", $cards, cardIndex);

         // Actualiza el contador de jugadores en el campo.
         playersOnField = $field.find(".position-marker.occupied").length;
         console.log(`Inicialización completa. playersOnField: ${playersOnField}`);
      },

      getTotalPlayersNeeded(positions) {
         return 1 + positions.defenders.length + positions.midfielders.length + positions.forwards.length;
      },

      placeGoalkeeper($field, position, $card) {
         const markerConfig = { role: "Arquero", position }; // Configuración del marcador.
         const $marker = MarkerFactory.create(markerConfig); // Crea el marcador.
         $field.append($marker); // Lo agrega al campo.
         PlayerInteraction.assignPlayer($marker, $card); // Asigna el jugador.
      },

      // Coloca a los jugadores de un rol (defensores, mediocampistas, delanteros) en sus posiciones.
      // Teoría: Itera sobre las posiciones y asigna un jugador a cada marcador.
      placeRolePlayers($field, positions, role, $cards, startIndex) {
         let cardIndex = startIndex; // Índice inicial para las tarjetas.
         positions.forEach((position) => {
            const $card = $cards.eq(cardIndex); // Selecciona la tarjeta actual.
            const markerConfig = { role, position }; // Configuración del marcador.
            const $marker = MarkerFactory.create(markerConfig); // Crea el marcador.
            $field.append($marker); // Lo agrega al campo.
            PlayerInteraction.assignPlayer($marker, $card); // Asigna el jugador.
            cardIndex++; // Avanza al siguiente jugador.
         });
         return cardIndex; // Devuelve el índice actualizado.
      },

      // Convierte el esquema (ej. "4-4-2") en un objeto con el número de defensores, mediocampistas y delanteros.
      // Teoría: Esto permite usar el esquema para calcular posiciones y validar.
      parseFormation(formationText) {
         const parts = formationText.split("-").map(Number);
         return {
            defenders: parts[0],
            midfielders: parts[1],
            forwards: parts[2],
         };
      },

      // Valida que el esquema tenga exactamente 10 jugadores (más el arquero, que se asume).
      // Teoría: Asegura que el esquema sea válido para un equipo de fútbol.
      validateFormation(defenders, midfielders, forwards) {
         return defenders + midfielders + forwards === 10;
      },
   };

   // Objeto que gestiona las interacciones del usuario (drag-and-drop, limpiar campo, guardar formación).
   // Teoría: Permite al usuario interactuar con la interfaz para armar su equipo.
   const PlayerInteraction = {
      // Prepara los datos de las tarjetas de jugadores para usarlos dinámicamente.
      // Teoría: Extrae información de las tarjetas (imagen, nombre, etc.) y la almacena para el drag-and-drop.
      setupPlayerData() {
         $(".player-card").each(function () {
            const $card = $(this);
            const $img = $card.find(".card-img");
            const data = {
               imgSrc: $img.length ? $img.attr("src") : DEFAULT_IMAGE_SRC, // Imagen del jugador.
               number: $card.find(".player-number").text() || "N/A", // Número de camiseta.
               rating: $card.find(".player-rating").text() || "0", // Rating del jugador.
               name: $card.find(".player-name").text() || "Jugador Desconocido", // Nombre del jugador.
               playerId: $card.attr("data-player-id") || "", // ID del jugador.
            };
            $card.data(data); // Almacena los datos en la tarjeta.
         });
      },

      // Hace que las tarjetas de jugadores sean arrastrables con jQuery UI.
      // Teoría: Permite al usuario mover las tarjetas al campo.
      setupDraggablePlayers() {
         $(".player-card").draggable({
            revert: "invalid", // Regresa la tarjeta si no se suelta en un lugar válido.
            helper: "clone", // Muestra un clon mientras se arrastra.
            cursor: "grabbing", // Cambia el cursor a "agarrar".
            opacity: 0.8, // Reduce la opacidad del clon.
            zIndex: 100, // Asegura que el clon esté por encima de otros elementos.
            start: function (event, ui) {
               const $card = $(this);
               const $img = $card.find(".card-img");
               const data = {
                  imgSrc: $img.length ? $img.attr("src") : DEFAULT_IMAGE_SRC,
                  number: $card.find(".player-number").text() || "N/A",
                  rating: $card.find(".player-rating").text() || "0",
                  name: $card.find(".player-name").text() || "Jugador Desconocido",
                  playerId: $card.attr("data-player-id") || "",
               };
               $card.data(data); // Actualiza los datos mientras se arrastra.
               // Aplica estilos al clon para que se vea más pequeño y con sombra.
               $(ui.helper).addClass("dragging").css({
                  transform: "scale(0.8)",
                  boxShadow: "0 10px 20px rgba(0, 0, 0, 0.4)",
                  width: "200px",
               });
            },
         });
      },

      setupDroppableField() {
         $("#field").droppable({
            accept: ".player-card",
            tolerance: "touch", // Considera un soltado válido si la tarjeta toca el área.
            drop: (event, ui) => {
               if ($("#field .position-marker.occupied").length >= MAX_PLAYERS_ON_FIELD) {
                  alert("¡Máximo de 11 jugadores en campo!");
                  return;
               }
               const $markers = $("#field .position-marker.default-marker:not(.occupied)"); // Selecciona marcadores libres.
               const closest = this.findClosestMarker(event, $markers); // Encuentra el marcador más cercano.
               // Si hay un marcador cercano (a menos de 50 píxeles), asigna el jugador.
               if (closest.marker && closest.distance < 50) {
                  this.assignPlayer(closest.marker, ui.draggable);
               } else {
                  ui.draggable.draggable("option", "revert", true); // Regresa la tarjeta si no hay marcador cercano.
               }
            },
         });
      },

       findClosestMarker(event, $markers) {
         let closest = { marker: null, distance: Infinity };
         $markers.each(function () {
            const pos = $(this).position(); // Posición del marcador.
            const dropPos = {
               left: event.pageX - $("#field").offset().left, // Posición relativa del soltado.
               top: event.pageY - $("#field").offset().top,
            };
            const distance = Math.hypot(pos.left - dropPos.left, pos.top - dropPos.top); // Distancia euclidiana.
            if (distance < closest.distance) closest = { marker: $(this), distance }; // Actualiza el más cercano.
         });
         return closest;
      },

      assignPlayer($marker, $card) {
         const { imgSrc, number, rating, name, playerId } = $card.data();
         const lastName = name && typeof name === "string" ? name.split(" ").slice(-1)[0] : "Desconocido"; // Extrae el apellido.
         $marker.find(".marker-number").text(number); // Muestra el número.
         $marker.find(".marker-rating").text(rating); // Muestra el rating.
         $marker.find(".marker-name").text(`${lastName} ${number}`); // Muestra el apellido y número.
         $marker.addClass("occupied").data({ occupied: true, "player-id": playerId }); // Marca como ocupado.
         $marker.find(".marker-img").addClass("occupied"); // Aplica estilo de ocupado.
         ImageManager.setPlayerImage($marker, $card); // Aplica la imagen del jugador.
         playersOnField++; // Incrementa el contador.
         $card.addClass("disabled-card").draggable("disable"); // Desactiva la tarjeta.
         $marker.attr("title", "Doble clic para quitar"); // Agrega un tooltip.
         console.log(`Jugador asignado. playersOnField: ${playersOnField}, imgSrc: ${imgSrc}`);
      },

       setupClearFieldButton() {
         $("<button>", {
            text: "Limpiar Campo",
            class: "btn btn-danger mt-3",
            click: () => confirm("¿Quitar todos los jugadores?") && this.clearField(), // Confirma antes de limpiar.
         }).appendTo(".container"); // Agrega el botón al contenedor principal.
      },

       clearField() {
         $(".position-marker.occupied").each((_, marker) => MarkerFactory.removePlayerFromMarker($(marker))); // Remueve cada jugador.
         playersOnField = 0; // Reinicia el contador.
         console.log(`Campo limpiado. playersOnField: ${playersOnField}`);
      },

       setupFieldFormation() {
         $("#form").on("submit", function(event) {
            event.preventDefault(); // Evita el envío inmediato para procesar datos.
            const players = [];
            // Itera sobre los marcadores ocupados para recolectar sus datos.
            $("#field .position-marker.occupied").each(function() {
               const $marker = $(this);
               const role = $marker.attr("id").split("-")[1]; // Extrae el rol (ej. "arquero").
               players.push({
                  id: $marker.data("player-id"), // ID del jugador.
                  role: role.toUpperCase(), // Rol en mayúsculas (ej. "ARQUERO").
               });
            });

            // Valida que haya exactamente 11 jugadores.
            if (players.length !== MAX_PLAYERS_ON_FIELD) {
               alert("Debes tener 11 jugadores en el campo de juego para guardar la formación");
               return;
            }

            // Elimina campos previos del formulario para evitar duplicados.
            $("#form input[name^='alineacion']").remove();

            // Agrega campos ocultos al formulario por cada jugador.
            players.forEach((player, index) => {
               console.log(`Posición ${index}: ID = ${player.id}`);
               // Valida que el ID no sea nulo.
               if (!player.id) {
                  console.error(`ID de jugador es null en la posicion ${index}`);
                  alert("Por favor, asigne un jugador valido en todas las posiciones.");
                  return;
               }
               // Campo para el ID del jugador.
               $("<input>", {
                  type: "hidden",
                  name: `alineacion[${index}].jugadorId`,
                  value: player.id,
               }).appendTo("#form");
               // Campo para la posición en el campo.
               $("<input>", {
                  type: "hidden",
                  name: `alineacion[${index}].posicionEnCampo`,
                  value: player.role,
               }).appendTo("#form");
            });

            this.submit(); // Envía el formulario al servidor.
         });
      },
   };

   PlayerInteraction.setupPlayerData();
   PlayerInteraction.setupDraggablePlayers(); // Activa el drag-and-drop.
   TeamFormation.initializeField(); // Inicializa el campo con marcadores y jugadores.
   PlayerInteraction.setupDroppableField(); // Configura el campo para soltar tarjetas.
   PlayerInteraction.setupClearFieldButton(); // Agrega el botón para limpiar.
   PlayerInteraction.setupFieldFormation(); // Configura el envío del formulario.
   $(".player-card").attr("title", "Arrastra al campo"); // Agrega un tooltip a las tarjetas.
});