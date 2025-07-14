describe("FutCode", function() {
   let $field, $card, $marker;

   beforeEach(function() {
      // Configura el DOM simulado para las pruebas
      $("body").append(`
         <div id="field" class="field-area ratio ratio-16x9 ui-droppable" data-esquema="4-3-3" data-equipo-id="1" data-default-img="/spring/img/jugadores/futbolista-default.png" style="position: relative; width: 800px; height: 450px;"></div>
         <div class="player-list">
            <div class="player-item flex-shrink-0">
               <div class="player-card" data-player-id="9">
                  <div class="player-image">
                     <img src="/spring/img/jugadores/lionel_messi.png" alt="Imagen futbolista" class="card-img">
                  </div>
                  <div class="player-info p-3">
                     <div class="player-header">
                        <span class="player-number">10</span>
                        <span class="player-rating">94.0</span>
                     </div>
                     <h6 class="player-name mb-1">Lionel Messi</h6>
                     <div class="player-details">
                        <small class="d-block">DELANTERO</small>
                     </div>
                  </div>
               </div>
            </div>
         </div>
      `);

      //me pide inicializarlo por usar el metodo TeamFormation.initializeField(); pero no lo necesito
      alineacionPersistida = [
         { jugadorId: "1" }, { jugadorId: "2" }, { jugadorId: "3" }, { jugadorId: "4" }, { jugadorId: "5" },
         { jugadorId: "6" }, { jugadorId: "7" }, { jugadorId: "8" }, { jugadorId: "9" }, { jugadorId: "10" },
         { jugadorId: "11" },
      ];
      $field = $("#field");
      $card = $(".player-card");
      $marker = $('<div class="position-marker default-marker" id="marker-delantero-6" style="position: absolute; left: 70%; top: 70%; width: 50px; height: 50px;"></div>')
         .append('<img class="marker-img" alt="Delantero" />' +
            '<div class="marker-number"></div>' +
            '<div class="marker-rating"></div>' +
            '<div class="marker-name">(apellido + numeroCamiseta)</div>');

     FutCode.setPlayersOnField(0);

     FutCode.PlayerInteraction.setupPlayerData();
      FutCode.PlayerInteraction.setupDraggablePlayers();
      FutCode.PlayerInteraction.setupDroppableField();


   });

   afterEach(function() {
      $("#field").remove();
      $(".player-list").remove();
      $("#form").remove();
      FutCode.setPlayersOnField(0);
   });

   it("Valida la configuracion de los draggable de plantilla.js de forma correcta", function() {
      const opcionesArrastrable = $card.draggable("option");
      expect(opcionesArrastrable.revert).toBe("invalid");
      expect(opcionesArrastrable.helper).toBe("clone");
      expect(opcionesArrastrable.cursor).toBe("grabbing");
      expect(opcionesArrastrable.opacity).toBe(0.8);
      expect(opcionesArrastrable.zIndex).toBe(100);
   });
   it("debe validar que la formación tiene exactamente 10 jugadores de campo", function() {
      const formacionValida = FutCode.TeamFormation.validateFormation(4, 3, 3);
      expect(formacionValida).toBe(true);
      const formacionInvalida = FutCode.TeamFormation.validateFormation(4, 3, 4);
      expect(formacionInvalida).toBe(false);
   });

   it("debe asignar un jugador a un marcador y actualizar clases y datos", function() {
      $field.append($marker);

      FutCode.PlayerInteraction.assignPlayer($marker, $card);

      //previamente debe estar cargado el jugador para que no se rompa (cargado en beforeEach())
      expect($marker.hasClass("occupied")).toBe(true);
      expect($marker.data("occupied")).toBe(true);
      expect($marker.data("player-id")).toBe("9");
      expect($marker.find(".marker-number").text()).toBe("10");
      expect($marker.find(".marker-rating").text()).toBe("94.0");
      expect($marker.find(".marker-name").text()).toBe("Messi 10");
      expect($marker.find(".marker-img").hasClass("custom-image")).toBe(true);
      expect($card.hasClass("disabled-card")).toBe(true);
      expect(FutCode.getPlayersOnField()).toBe(1);
   });


   it("debe remover un jugador de un marcador y restaurar la tarjeta", function() {
      $field.append($marker);
      FutCode.PlayerInteraction.assignPlayer($marker, $card);

      spyOn(FutCode.MarkerFactory, "resetPlayerCard").and.callThrough();
      FutCode.MarkerFactory.removePlayerFromMarker($marker);

      expect($marker.hasClass("occupied")).toBe(false);
      expect($marker.data("occupied")).toBe(false);
      expect($marker.data("player-id")).toBeUndefined();
      expect($marker.find(".marker-number").text()).toBe("");
      expect($marker.find(".marker-rating").text()).toBe("");
      expect($marker.find(".marker-name").text()).toBe("");
      expect($marker.find(".marker-img").hasClass("custom-image")).toBe(false);
      expect(FutCode.MarkerFactory.resetPlayerCard).toHaveBeenCalled();
      expect(FutCode.getPlayersOnField()).toBe(0);
   });



   it("debe generar los inputs correctos al guardar una formación completa", function() {
      $field.append($marker);
      FutCode.PlayerInteraction.assignPlayer($marker, $card);

     FutCode.PlayerInteraction.setupFieldFormation();

      spyOn(HTMLFormElement.prototype, "submit").and.callFake(function() {});

      $("#form").trigger("submit");

      expect($("input[name='equipoId']").length).toBe(0);

      expect(HTMLFormElement.prototype.submit).not.toHaveBeenCalled();

   });

   it("debe inicializar el campo con marcadores para una formación 4-3-3", function() {

      //simulamos 11 jugadores, validamos que la clase player-card se encuentre
      for (let i = 1; i <= 11; i++) {
         $(".player-list").append(`
            <div class="player-item flex-shrink-0">
               <div class="player-card" data-player-id="${i}">
                 
               </div>
            </div>
         `);
      }

      FutCode.PlayerInteraction.setupPlayerData();
      FutCode.PlayerInteraction.setupDraggablePlayers();

      FutCode.TeamFormation.initializeField();

      const marcadores = $field.find(".position-marker");
      expect(marcadores.length).toBe(11);

      //este da error
      expect(marcadores.filter(".occupied").length).toBe(11);

      expect(FutCode.getPlayersOnField()).toBe(11);

      // el disabled-card valida que no se puede seleccionar debido a que las tenemos en los marcadores
      expect($(".player-card.disabled-card").length).toBe(11);
   });
});