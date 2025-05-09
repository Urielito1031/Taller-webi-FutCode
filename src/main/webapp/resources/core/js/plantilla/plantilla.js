$(function() {
    // Configuración inicial - Obtiene la imagen por defecto desde el DOM.
    const defaultImgSrc = $("#field").data("default-img");
    let playersOnField = 0;

    // Generador de posiciones - Define las ubicaciones iniciales de los jugadores en el campo.
    const PositionGenerator = {
        getPositions(defensas, mediocampistas, delanteros, midfieldSplit = 0) {
            return {
                arquero: this.getArqueroPosition(),
                defensas: this.getDefensasPositions(defensas),
                mediocampistas: this.getMediocampistasPositions(mediocampistas, midfieldSplit),
                delanteros: this.getDelanterosPositions(delanteros)
            };
        },
        getArqueroPosition() { return [{ x: 10, y: 50 }]; }, // Posición fija del arquero.
        getDefensasPositions(count) {
            const positions = [];
            const defX = 25;
            for (let i = 0; i < count; i++) {
                const y = 25 + (i * 50 / (count - 1 || 1));
                positions.push({ x: defX, y: Math.min(75, Math.max(25, y)) });
            }
            return positions;
        },
        getMediocampistasPositions(count, split) {
            const positions = [];
            if (split > 0 && count >= 5) {
                const defMids = split;
                const offMids = count - defMids;
                for (let i = 0; i < defMids; i++) {
                    const y = 40 + (i * 20 / (defMids - 1 || 1));
                    positions.push({ x: 40, y: Math.min(60, Math.max(40, y)) });
                }
                const offPositions = [{ x: 55, y: 50 }, { x: 50, y: 35 }, { x: 50, y: 65 }];
                positions.push(...offPositions.slice(0, offMids));
            } else {
                const midX = 45;
                for (let i = 0; i < count; i++) {
                    const y = 25 + (i * 50 / (count - 1 || 1));
                    positions.push({ x: midX, y: Math.min(75, Math.max(25, y)) });
                }
            }
            return positions;
        },
        getDelanterosPositions(count) {
            const positions = [];
            const delX = 70;
            for (let i = 0; i < count; i++) {
                const y = 35 + (i * 40 / (count - 1 || 1));
                positions.push({ x: delX, y: Math.min(75, Math.max(35, y)) });
            }
            return positions;
        }
    };

    // Fábrica de marcadores - Crea y configura los marcadores en el campo.
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
            const number = marker.find(".marker-number").text();
            marker.find(".marker-name").text(`${marker.find(".marker-name").text().split(" ")[0]} ${number}`);
            marker.removeClass("occupied").data("occupied", false);
            $(`.player-card[data-number="${number}"]`).removeClass("disabled-card").draggable("enable");
            playersOnField--;
        }
    };

    // Gestor de formación - Genera la disposición inicial del equipo.
    const TeamFormation = {
        generate() {
            const field = $("#field");
            const esquemaTexto = field.data("esquema");
            if (!esquemaTexto) {
                console.error("Esquema no encontrado en data-esquema");
                return;
            }

            const { defensas, mediocampistas, delanteros, midfieldSplit } = this.parseFormation(esquemaTexto);
            if (!this.validateFormation(defensas, mediocampistas, delanteros)) return;

            $(".position-marker").remove();
            const positions = PositionGenerator.getPositions(defensas, mediocampistas, delanteros, midfieldSplit);
            this.createMarkers(field, positions);
        },
        parseFormation(esquemaTexto) {
            const parts = esquemaTexto.split('-').map(Number);
            return parts.length === 4
                ? { defensas: parts[0], mediocampistas: parts[1] + parts[2], delanteros: parts[3], midfieldSplit: parts[1] }
                : { defensas: parts[0], mediocampistas: parts[1], delanteros: parts[2], midfieldSplit: 0 };
        },
        validateFormation(defensas, mediocampistas, delanteros) {
            const total = defensas + mediocampistas + delanteros;
            if (total !== 10) {
                console.error(`Formación inválida: suma ${total} en lugar de 10`);
                return false;
            }
            console.log("Formación válida:", { defensas, mediocampistas, delanteros });
            return true;
        },
        createMarkers(field, positions) {
            if (positions.arquero.length) field.append(MarkerFactory.create("Arquero", 1, 88.0, defaultImgSrc, positions.arquero[0], true));
            positions.defensas.forEach((pos, i) => field.append(MarkerFactory.create("Defensor", i + 2, 87.0, defaultImgSrc, pos, true)));
            positions.mediocampistas.forEach((pos, i) => field.append(MarkerFactory.create("Mediocampista", i + positions.defensas.length + 2, 86.0, defaultImgSrc, pos, true)));
            positions.delanteros.forEach((pos, i) => field.append(MarkerFactory.create("Delantero", i + positions.defensas.length + positions.mediocampistas.length + 2, 89.0, defaultImgSrc, pos, true)));
        }
    };

    // Gestor de interacción - Maneja las acciones del usuario.
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
                        name: $card.find('.player-name').text() || "Jugador Desconocido"
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
                    if (this.playersOnField >= 10) {
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
                    if (distance < closest.distance) closest = { marker: $(this), distance };
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
            this.playersOnField++;
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
            this.playersOnField = 0;
        }
    };

    // Inicialización - Configura el juego.
    TeamFormation.generate();
    console.log("Marcadores generados:", $(".position-marker").length); // Log para depuración.
    PlayerInteraction.setupDraggablePlayers();
    PlayerInteraction.setupDroppableField();
    PlayerInteraction.setupClearFieldButton();
    $(".player-card").attr("title", "Arrastra al campo");
});