$(function() {
    const defaultImgSrc = $("#field").data("default-img");
    let playersOnField = 0;
    const MAX_PLAYERS_ON_FIELD = 10;

    // Generador de posiciones para los jugadores en el campo
    const PositionGenerator = {
        getPositions(defenders, midfielders, forwards, midfieldSplit = 0) {
            return {
                goalkeeper: this.getGoalkeeperPosition(),
                defenders: this.getDefenderPositions(defenders),
                midfielders: this.getMidfielderPositions(midfielders, midfieldSplit),
                forwards: this.getForwardPositions(forwards)
            };
        },

        getGoalkeeperPosition() {
            return [{ x: 10, y: 50 }];
        },

        getDefenderPositions(count) {
            const positions = [];
            const x = 25;
            for (let i = 0; i < count; i++) {
                const y = 25 + (i * 50 / (count - 1 || 1));
                positions.push({ x, y: Math.min(75, Math.max(25, y)) });
            }
            return positions;
        },

        getMidfielderPositions(count, split) {
            const positions = [];
            if (split > 0 && count >= 5) {
                const defensiveMids = split;
                const offensiveMids = count - defensiveMids;
                for (let i = 0; i < defensiveMids; i++) {
                    const y = 40 + (i * 20 / (defensiveMids - 1 || 1));
                    positions.push({ x: 40, y: Math.min(60, Math.max(40, y)) });
                }
                const offensivePositions = [{ x: 55, y: 50 }, { x: 50, y: 35 }, { x: 50, y: 65 }];
                positions.push(...offensivePositions.slice(0, offensiveMids));
            } else {
                const x = 45;
                for (let i = 0; i < count; i++) {
                    const y = 25 + (i * 50 / (count - 1 || 1));
                    positions.push({ x, y: Math.min(75, Math.max(25, y)) });
                }
            }
            return positions;
        },

        getForwardPositions(count) {
            const positions = [];
            const x = 70;
            for (let i = 0; i < count; i++) {
                const y = 35 + (i * 40 / (count - 1 || 1));
                positions.push({ x, y: Math.min(75, Math.max(35, y)) });
            }
            return positions;
        }
    };

    // Fábrica de marcadores para el campo
    const MarkerFactory = {
        create(role, number, rating, imgSrc, position, isDefault) {
            const markerId = `marker-${role.toLowerCase()}-${number}-${Date.now()}`;
            const marker = $(
               `<div id="${markerId}" class="position-marker ${isDefault ? 'default-marker' : ''}" style="left: ${position.x}%; top: ${position.y}%;">
                    <div class="marker-card">
                        <img src="${imgSrc || defaultImgSrc}" alt="${role}" class="marker-img" />
                        <div class="marker-info">
                            <span class="marker-number">${number}</span>
                            <span class="marker-rating">${rating}</span>
                            <div class="marker-name">${role} ${number}</div>
                        </div>
                    </div>
                </div>`
            );
            this.setupMarkerBehavior(marker, isDefault);
            return marker;
        },

        setupMarkerBehavior(marker, isDefault) {
            if (isDefault) {
                marker.dblclick(() => {
                    if (marker.data("occupied")) {
                        this.removePlayerFromMarker(marker);
                    }
                });
            }
        },

        removePlayerFromMarker(marker) {
            marker.find(".marker-img").attr("src", defaultImgSrc);
            const number = marker.find(".marker-number").text().trim();
            marker.find(".marker-name").text(`${marker.find(".marker-name").text().split(" ")[0]} ${number}`);
            marker.removeClass("occupied").data("occupied", false);

            const playerId = marker.data("player-id");
            const $card = playerId
               ? $(`.player-card[data-player-id="${playerId}"]`)
               : $(".player-card").filter(function() {
                   return $(this).find(".player-number").text().trim() === number;
               });

            if ($card.length === 0) {
                return;
            }

            $card.removeClass("disabled-card");
            $card.removeData("imgSrc").removeData("number").removeData("rating").removeData("name");
            $card.css({ opacity: "1", cursor: "grab", pointerEvents: "auto" });
            $card.attr("title", "Arrastra al campo");

            try {
                $card.draggable("enable");
            } catch (error) {
                $card.draggable({
                    revert: "invalid",
                    helper: "clone",
                    cursor: "grabbing",
                    opacity: 0.8,
                    zIndex: 100,
                    start: function(event, ui) {
                        const $card = $(this);
                        $card.data({
                            imgSrc: $card.find('.card-img').attr('src') || defaultImgSrc,
                            number: $card.find('.player-number').text(),
                            rating: $card.find('.player-rating').text(),
                            name: $card.find('.player-name').text() || "Jugador Desconocido",
                            "player-id": $card.attr("data-player-id")
                        });
                        $(ui.helper).addClass("dragging").css({
                            transform: "scale(0.8)",
                            boxShadow: "0 10px 20px rgba(0,0,0,0.4)",
                            width: "200px"
                        });
                    }
                });
            }

            marker.removeData("player-id");
            playersOnField--;
        }
    };

    // Gestor de formación del equipo
    const TeamFormation = {
        generate() {
            const $field = $("#field");
            const formationText = $field.data("esquema");
            if (!formationText) {
                return;
            }

            const { defenders, midfielders, forwards, midfieldSplit } = this.parseFormation(formationText);
            if (!this.validateFormation(defenders, midfielders, forwards)) {
                return;
            }

            $(".position-marker").remove();
            const positions = PositionGenerator.getPositions(defenders, midfielders, forwards, midfieldSplit);
            this.createMarkers($field, positions);
        },

        parseFormation(formationText) {
            const parts = formationText.split('-').map(Number);
            return parts.length === 4
               ? { defenders: parts[0], midfielders: parts[1] + parts[2], forwards: parts[3], midfieldSplit: parts[1] }
               : { defenders: parts[0], midfielders: parts[1], forwards: parts[2], midfieldSplit: 0 };
        },

        validateFormation(defenders, midfielders, forwards) {
            const total = defenders + midfielders + forwards;
            return total === 10;
        },

        createMarkers($field, positions) {
            if (positions.goalkeeper.length) {
                $field.append(MarkerFactory.create("Arquero", 1, 88.0, defaultImgSrc, positions.goalkeeper[0], true));
            }
            positions.defenders.forEach((pos, i) => {
                $field.append(MarkerFactory.create("Defensor", i + 2, 87.0, defaultImgSrc, pos, true));
            });
            positions.midfielders.forEach((pos, i) => {
                $field.append(MarkerFactory.create("Mediocampista", i + positions.defenders.length + 2, 86.0, defaultImgSrc, pos, true));
            });
            positions.forwards.forEach((pos, i) => {
                $field.append(MarkerFactory.create("Delantero", i + positions.defenders.length + positions.midfielders.length + 2, 89.0, defaultImgSrc, pos, true));
            });
        }
    };

    // Gestor de interacciones del usuario
    const PlayerInteraction = {
        setupDraggablePlayers() {
            $(".player-card").draggable({
                revert: "invalid",
                helper: "clone",
                cursor: "grabbing",
                opacity: 0.8,
                zIndex: 100,
                start: function(event, ui) {
                    const $card = $(this);
                    $card.data({
                        imgSrc: $card.find('.card-img').attr('src') || defaultImgSrc,
                        number: $card.find('.player-number').text(),
                        rating: $card.find('.player-rating').text(),
                        name: $card.find('.player-name').text() || "Jugador Desconocido",
                        "player-id": $card.attr("data-player-id")
                    });
                    $(ui.helper).addClass("dragging").css({
                        transform: "scale(0.8)",
                        boxShadow: "0 10px 20px rgba(0,0,0,0.4)",
                        width: "200px"
                    });
                }
            });
        },

        setupDroppableField() {
            $("#field").droppable({
                accept: ".player-card",
                tolerance: "touch",
                drop: (event, ui) => {
                    if (playersOnField >= MAX_PLAYERS_ON_FIELD) {
                        alert("¡Máximo de 11 jugadores en campo!");
                        return;
                    }
                    const closest = this.findClosestMarker(event);
                    if (closest.marker && !closest.marker.data("occupied") && closest.distance < 50) {
                        this.assignPlayer(closest.marker, ui.draggable);
                    } else {
                        ui.draggable.draggable("option", "revert", true);
                    }
                }
            });
        },

        findClosestMarker(event) {
            let closest = { marker: null, distance: Infinity };
            $(".position-marker.default-marker").each(function() {
                if (!$(this).data("occupied")) {
                    const pos = $(this).position();
                    const dropPos = { left: event.pageX - $("#field").offset().left, top: event.pageY - $("#field").offset().top };
                    const distance = Math.sqrt(Math.pow(pos.left - dropPos.left, 2) + Math.pow(pos.top - dropPos.top, 2));
                    if (distance < closest.distance) {
                        closest = { marker: $(this), distance };
                    }
                }
            });
            return closest;
        },

        assignPlayer(marker, $card) {
            const data = $card.data();
            marker.find(".marker-img").attr("src", data.imgSrc);
            marker.find(".marker-number").text(data.number);
            marker.find(".marker-rating").text(data.rating);
            marker.find(".marker-name").text(data.name);
            marker.addClass("occupied").data("occupied", true);
            marker.data("player-id", $card.data("player-id"));
            playersOnField++;
            $card.addClass("disabled-card").draggable("disable");
            marker.attr("title", "Doble clic para quitar");
        },

        setupClearFieldButton() {
            $("<button>", {
                text: "Limpiar Campo",
                class: "btn btn-danger mt-3",
                click: () => confirm("¿Quitar todos los jugadores?") && this.clearField()
            }).appendTo(".container");
        },

        clearField() {
            $(".position-marker.occupied").each(function() {
                MarkerFactory.removePlayerFromMarker($(this));
            });
            playersOnField = 0;
        }
    };

    // Inicialización del juego
    TeamFormation.generate();
    PlayerInteraction.setupDraggablePlayers();
    PlayerInteraction.setupDroppableField();
    PlayerInteraction.setupClearFieldButton();
    $(".player-card").attr("title", "Arrastra al campo");
});