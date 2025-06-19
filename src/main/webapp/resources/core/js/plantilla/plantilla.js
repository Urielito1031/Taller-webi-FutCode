(function($) {
   // Módulo principal encapsulado
   const FutCode = (function() {
      // Constantes
      const DEFAULT_IMAGE_SRC = $("#field").data("default-img");
      const MAX_PLAYERS_ON_FIELD = 11;

      // Estado interno
      let playersOnField = 0;

      // Configuración del campo
      const CONFIG = {
         POSITION_RANGES: {
            MIN_Y: 15,
            MAX_Y: 75,
            DEFAULT_MIN_Y: 20,
            DEFAULT_MAX_Y: 70,
         },
         PADDINGS: {
            DEFAULT: 8,
            HIGH_DENSITY: 12,
         },
         FIELD_ZONES: {
            DEFENSE_START: 25,
            MIDFIELD_DEFENSIVE_END: 40,
            MIDFIELD_OFFENSIVE_END: 55,
            ATTACK_START: 70,
         },
         DEFAULT_RATINGS: {
            GOALKEEPER: 88.0,
            DEFENDER: 87.0,
            MIDFIELDER: 86.0,
            FORWARD: 89.0,
         },
      };

      // Genera posiciones para los marcadores en el campo
      const PositionGenerator = {
         getPositions(defenders, midfielders, forwards) {
            const { DEFENSE_START, MIDFIELD_DEFENSIVE_END, MIDFIELD_OFFENSIVE_END, ATTACK_START } = CONFIG.FIELD_ZONES;
            return {
               goalkeeper: this.getGoalkeeperPosition(DEFENSE_START),
               defenders: this.getPositionsForLine(defenders, DEFENSE_START),
               midfielders: this.getMidfielderPositions(midfielders, MIDFIELD_DEFENSIVE_END, MIDFIELD_OFFENSIVE_END),
               forwards: this.getPositionsForLine(forwards, ATTACK_START),
            };
         },
         getGoalkeeperPosition(defenseStart) {
            return [{ x: defenseStart * 0.4, y: 45 }];
         },
         getPositionsForLine(playerCount, xPosition) {
            if (playerCount === 0) return [];
            const { MIN_Y, MAX_Y, DEFAULT_MIN_Y, DEFAULT_MAX_Y } = CONFIG.POSITION_RANGES;
            const { DEFAULT: defaultPadding, HIGH_DENSITY: highDensityPadding } = CONFIG.PADDINGS;
            const yRange = playerCount >= 4 ? MAX_Y - MIN_Y : DEFAULT_MAX_Y - DEFAULT_MIN_Y;
            const padding = playerCount >= 4 ? highDensityPadding : defaultPadding;
            const step = playerCount > 1 ? (yRange - padding * (playerCount - 1)) / (playerCount - 1) : 0;
            const baseY = playerCount >= 4 ? MIN_Y : DEFAULT_MIN_Y;
            return Array.from({ length: playerCount }, (_, i) => {
               const y = playerCount === 2 ? (i === 0 ? 35 : 65) : baseY + i * (step + padding);
               return { x: xPosition, y: this.clampY(y, playerCount) };
            });
         },
         getMidfielderPositions(totalMidfielders, defensiveEnd, offensiveEnd) {
            const midX = (defensiveEnd + offensiveEnd) / 2;
            return this.getPositionsForLine(totalMidfielders, midX);
         },
         clampY(value, playerCount = 0) {
            const { MIN_Y, MAX_Y, DEFAULT_MIN_Y, DEFAULT_MAX_Y } = CONFIG.POSITION_RANGES;
            return Math.min(playerCount >= 4 ? MAX_Y : DEFAULT_MAX_Y, Math.max(playerCount >= 4 ? MIN_Y : DEFAULT_MIN_Y, value));
         },
      };

      // Gestiona las imágenes de los marcadores
      const ImageManager = {
         setDefaultImage($marker) {
            $marker.find(".marker-img").attr("src", DEFAULT_IMAGE_SRC).removeClass("custom-image");
         },
         setPlayerImage($marker, $card) {
            const imgSrc = $card.find(".card-img").attr("src") || DEFAULT_IMAGE_SRC;
            this.setImage($marker, imgSrc, imgSrc !== DEFAULT_IMAGE_SRC);
         },
         setImage($marker, src, isCustom = false) {
            $marker.find(".marker-img").attr("src", src).toggleClass("custom-image", isCustom);
         },
      };

      // Crea y gestiona marcadores en el campo
      const MarkerFactory = {
         create({ role, position, isDefault = true }) {
            const markerId = `marker-${role.toLowerCase()}-${Date.now()}`;
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
            ImageManager.setDefaultImage($marker);
            this.setupMarkerBehavior($marker, isDefault);
            return $marker;
         },
         setupMarkerBehavior($marker, isDefault) {
            if (!isDefault) return;
            $marker.on("dblclick", () => {
               if ($marker.data("occupied")) this.removePlayerFromMarker($marker);
            });
         },
         removePlayerFromMarker($marker) {
            ImageManager.setDefaultImage($marker);
            $marker.find(".marker-number").text("");
            $marker.find(".marker-rating").text("");
            $marker.find(".marker-name").text("");
            $marker.removeClass("occupied").data("occupied", false);
            const playerId = $marker.data("player-id");
            const $card = this.findPlayerCard(playerId);
            this.resetPlayerCard($card);
            $marker.removeData("player-id");
            playersOnField--;
            console.log(`Jugador removido. playersOnField: ${playersOnField}`);
         },
         findPlayerCard(playerId) {
            return playerId ? $(`.player-card[data-player-id="${playerId}"]`) : $();
         },
         resetPlayerCard($card) {
            if (!$card.length) return;
            $card.removeClass("disabled-card").css({
               opacity: "1",
               cursor: "grab",
               pointerEvents: "auto",
            });
            $card.attr("title", "Arrastra al campo");
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

      // Gestiona la formación del equipo
      const TeamFormation = {
         initializeField() {
            const $field = $("#field");
            const formationText = $field.data("esquema");
            if (!formationText || !/^\d+-\d+(-\d+)?$/.test(formationText)) {
               console.error("Formación inválida:", formationText);
               return;
            }
            const equipoId = $field.data("equipo-id");
            console.log("EquipoId inicial desde data: " + equipoId);
            const { defenders, midfielders, forwards } = this.parseFormation(formationText);
            const positions = PositionGenerator.getPositions(defenders, midfielders, forwards);
            this.placePlayers($field, positions);

            // Cargar jugadores persistidos desde alineacionPersistida
            if (alineacionPersistida && alineacionPersistida.length > 0) {
               let cardIndex = 0;
               const $cards = $(".player-card").not(".disabled-card");
               alineacionPersistida.forEach((posicion, index) => {
                  const playerId = posicion.jugadorId; // Usar jugadorId del DTO
                  if (playerId) {
                     const $card = $cards.filter(`[data-player-id="${playerId}"]`).first();
                     if ($card.length && cardIndex < positions.goalkeeper.length + positions.defenders.length + positions.midfielders.length + positions.forwards.length) {
                        const $marker = $field.find(".position-marker").eq(cardIndex);
                        PlayerInteraction.assignPlayer($marker, $card);
                        cardIndex++;
                     }
                  } else {
                     console.warn(`Posición ${index} sin jugadorId válido en alineacionPersistida`);
                  }
               });
            }
            playersOnField = $field.find(".position-marker.occupied").length;
            console.log(`Inicialización completa. playersOnField: ${playersOnField}`);
         },
         placePlayers($field, positions) {
            const $cards = $(".player-card").not(".disabled-card");
            const totalNeeded = this.getTotalPlayersNeeded(positions);
            if ($cards.length < totalNeeded) {
               console.warn(`Se necesitan ${totalNeeded} jugadores, pero solo hay ${$cards.length}.`);
               return;
            }
            $field.find(".position-marker").remove();
            let cardIndex = 0;
            this.placeGoalkeeper($field, positions.goalkeeper[0], $cards.eq(cardIndex++));
            cardIndex = this.placeRolePlayers($field, positions.defenders, "Defensor", $cards, cardIndex);
            cardIndex = this.placeRolePlayers($field, positions.midfielders, "Mediocampista", $cards, cardIndex);
            this.placeRolePlayers($field, positions.forwards, "Delantero", $cards, cardIndex);
            playersOnField = $field.find(".position-marker.occupied").length;
            console.log(`Inicialización completa. playersOnField: ${playersOnField}`);
         },
         getTotalPlayersNeeded(positions) {
            return 1 + positions.defenders.length + positions.midfielders.length + positions.forwards.length;
         },
         placeGoalkeeper($field, position, $card) {
            const markerConfig = { role: "Arquero", position };
            const $marker = MarkerFactory.create(markerConfig);
            $field.append($marker);
            PlayerInteraction.assignPlayer($marker, $card);
         },
         placeRolePlayers($field, positions, role, $cards, startIndex) {
            let cardIndex = startIndex;
            positions.forEach((position) => {
               const $card = $cards.eq(cardIndex);
               const markerConfig = { role, position };
               const $marker = MarkerFactory.create(markerConfig);
               $field.append($marker);
               PlayerInteraction.assignPlayer($marker, $card);
               cardIndex++;
            });
            return cardIndex;
         },
         parseFormation(formationText) {
            const parts = formationText.split("-").map(Number);
            return {
               defenders: parts[0],
               midfielders: parts[1],
               forwards: parts[2],
            };
         },
         validateFormation(defenders, midfielders, forwards) {
            return defenders + midfielders + forwards === 10;
         },
      };

      // Gestiona las interacciones del usuario
      const PlayerInteraction = {
         setupPlayerData() {
            $(".player-card").each(function() {
               const $card = $(this);
               const $img = $card.find(".card-img");
               const data = {
                  imgSrc: $img.length ? $img.attr("src") : DEFAULT_IMAGE_SRC,
                  number: $card.find(".player-number").text() || "N/A",
                  rating: $card.find(".player-rating").text() || "0",
                  name: $card.find(".player-name").text() || "Jugador Desconocido",
                  playerId: $card.attr("data-player-id") || "",
               };
               $card.data(data);
            });
         },
         setupDraggablePlayers() {
            $(".player-card").draggable({
               revert: "invalid",
               helper: "clone",
               cursor: "grabbing",
               opacity: 0.8,
               zIndex: 100,
               start: function(event, ui) {
                  const $card = $(this);
                  const $img = $card.find(".card-img");
                  const data = {
                     imgSrc: $img.length ? $img.attr("src") : DEFAULT_IMAGE_SRC,
                     number: $card.find(".player-number").text() || "N/A",
                     rating: $card.find(".player-rating").text() || "0",
                     name: $card.find(".player-name").text() || "Jugador Desconocido",
                     playerId: $card.attr("data-player-id") || "",
                  };
                  $card.data(data);
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
               tolerance: "touch",
               drop: (event, ui) => {

                  const $markers = $("#field .position-marker.default-marker:not(.occupied)");
                  const closest = this.findClosestMarker(event, $markers);
                  if (closest.marker && closest.distance < 80) {
                     this.assignPlayer(closest.marker, ui.draggable);
                  } else {
                     ui.draggable.draggable("option", "revert", true);
                  }
               },
            });
         },
         findClosestMarker(event, $markers) {
            let closest = { marker: null, distance: Infinity };
            $markers.each(function() {
               const pos = $(this).position();
               const dropPos = {
                  left: event.pageX - $("#field").offset().left,
                  top: event.pageY - $("#field").offset().top,
               };
               const distance = Math.hypot(pos.left - dropPos.left, pos.top - dropPos.top);
               if (distance < closest.distance) closest = { marker: $(this), distance };
            });
            return closest;
         },
         assignPlayer($marker, $card) {
            const playerId = $card.data("player-id");
            if (!playerId) {
               console.error("No se puede asignar jugador: ID de jugador no encontrado.", $card);
               return;
            }
            const imgSrc = $card.find(".card-img").attr("src") || DEFAULT_IMAGE_SRC;
            const number = $card.find(".player-number").text() || "N/A";
            const rating = $card.find(".player-rating").text() || "0";
            const name = $card.find(".player-name").text() || "Jugador Desconocido";
            const lastName = name && typeof name === "string" ? name.split(" ").slice(-1)[0] : "Desconocido";

            $marker.find(".marker-number").text(number);
            $marker.find(".marker-rating").text(rating);
            $marker.find(".marker-name").text(`${lastName} ${number}`);
            $marker.addClass("occupied").data({ occupied: true, "player-id": playerId });
            $marker.find(".marker-img").addClass("occupied");
            ImageManager.setPlayerImage($marker, $card);
            playersOnField++;
            $card.addClass("disabled-card").draggable("disable");
            $marker.attr("title", "Doble clic para quitar");
            console.log(`Jugador asignado. playersOnField: ${playersOnField}, playerId: ${playerId}`);
         },
         setupClearFieldButton() {
            $("<button>", {
               text: "Limpiar Campo",
               class: "btn btn-danger mt-3",
               click: () => confirm("¿Quitar todos los jugadores?") && this.clearField(),
            }).appendTo(".container");
         },
         clearField() {
            $(".position-marker.occupied").each((_, marker) => MarkerFactory.removePlayerFromMarker($(marker)));
            playersOnField = 0;
            console.log(`Campo limpiado. playersOnField: ${playersOnField}`);
         },
         setupFieldFormation() {
            $("#form").on("submit", function(event) {
               event.preventDefault();
               const players = [];
               $("#field .position-marker.occupied").each(function() {
                  const $marker = $(this);
                  const role = $marker.attr("id").split("-")[1];
                  players.push({
                     id: $marker.data("player-id"),
                     role: role.toUpperCase(),
                  });
               });
               if (players.length !== MAX_PLAYERS_ON_FIELD) {
                  alert("Debes tener 11 jugadores en el campo de juego para guardar la formación");
                  return;
               }
               $("#form input[name^='alineacion']").remove();
               $("#form input[name='equipoId']").remove();
               const equipoId = $("#field").data("equipo-id");
               console.log("EquipoId enviado: " + equipoId);
               $("<input>", {
                  type: "hidden",
                  name: "equipoId",
                  value: equipoId
               }).appendTo("#form");
               players.forEach((player, index) => {
                  console.log(`Posición ${index}: ID = ${player.id}`);
                  if (!player.id) {
                     alert("Por favor, asigne un jugador válido en todas las posiciones.");
                     return;
                  }
                  $("<input>", {
                     type: "hidden",
                     name: `alineacion[${index}].jugadorId`,
                     value: player.id,
                  }).appendTo("#form");
                  $("<input>", {
                     type: "hidden",
                     name: `alineacion[${index}].posicionEnCampo`,
                     value: player.role,
                  }).appendTo("#form");
               });
               this.submit();
            });
         },
      };

      // Inicialización
      function init() {
         PlayerInteraction.setupPlayerData();
         PlayerInteraction.setupDraggablePlayers();
         TeamFormation.initializeField();
         PlayerInteraction.setupDroppableField();
         PlayerInteraction.setupClearFieldButton();
         PlayerInteraction.setupFieldFormation();
         $(".player-card").attr("title", "Arrastra al campo");
      }

      // Interfaz pública para pruebas
      return {
         PositionGenerator,
         ImageManager,
         MarkerFactory,
         TeamFormation,
         PlayerInteraction,
         MAX_PLAYERS_ON_FIELD,
         CONFIG,
         DEFAULT_IMAGE_SRC,
         getPlayersOnField: () => playersOnField,
         setPlayersOnField: (value) => { playersOnField = value; },
         init
      };
   })();

   // Ejecuta la inicialización
   FutCode.init();

   // Expone FutCode globalmente solo para pruebas
   if (typeof window !== 'undefined' && typeof window.jasmine !== 'undefined') {
      window.FutCode = FutCode;
   }
})(jQuery);