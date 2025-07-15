let descripciones = {
    descripcionBronce : 'El Sobre de Bronce es la puerta de entrada a FutCode. Su color representa humildad y el espíritu de superación. Incluye 5 cartas de rareza <span class="rareza-comun">Común</span>, ideales para comenzar a construir tu equipo y dar tus primeros pasos en la competencia.<br><small>Ideal para: Quienes disfrutan el desafío de crecer desde abajo y sorprenderse con cada avance.</small>',
    descripcionPlata : 'El Sobre de Plata simboliza progreso y constancia. Contiene 5 cartas de rareza <span class="rareza-raro">Rara</span>, perfectas para fortalecer tu plantilla y mantenerte competitivo en cada partido.<br><small>Ideal para: Jugadores que buscan equilibrio entre progreso y estabilidad en su equipo.</small>',
    descripcionOro : 'El Sobre de Oro es para los que buscan dar un salto de calidad. Su brillo dorado refleja ambición y alto rendimiento. Incluye 5 cartas de rareza <span class="rareza-epico">Épica</span>, con chances de obtener jugadores destacados y mejoras clave para tu club.<br><small>Ideal para: Usuarios que quieren potenciar su plantilla y dominar la cancha con talento de élite.</small>',
    descripcionEspecial : 'El Sobre Especial es el más codiciado de FutCode. Su diseño exclusivo y detalles brillantes lo distinguen del resto. Contiene 3 cartas de rareza <span class="rareza-legendario">Legendaria</span>, con grandes probabilidades de conseguir leyendas y contenido épico.<br><small>Ideal para: Coleccionistas, competidores top o quienes buscan diferenciarse con cartas únicas e irrepetibles.</small>'
}

let tituloDescripcion = document.getElementById('titulo-descripcion')
let parrafoDescripcion = document.getElementById('parrafo-descripcion')

// Delegación de eventos para máxima robustez
const sobresContainer = document.querySelector('.sobres-cards-nueva-ui');
if (sobresContainer) {
    sobresContainer.addEventListener('mouseover', function(e) {
        // Buscar el card más cercano al mouse
        let card = e.target.closest('.sobre-card-nueva-ui');
        if (!card || !sobresContainer.contains(card)) return;
        let tituloElem = card.querySelector('.sobre-titulo');
        let titulo = tituloElem ? tituloElem.textContent.trim() : '';
        tituloDescripcion.textContent = titulo;
        switch (titulo) {
            case 'Sobre de Bronce':
                parrafoDescripcion.innerHTML = descripciones.descripcionBronce;
            break;
            case 'Sobre de Plata':
                parrafoDescripcion.innerHTML = descripciones.descripcionPlata;
            break;
            case 'Sobre de Oro':
                parrafoDescripcion.innerHTML = descripciones.descripcionOro;
            break;
            case 'Sobre Especial':
                parrafoDescripcion.innerHTML = descripciones.descripcionEspecial;
            break;
            default:
                parrafoDescripcion.innerHTML = '';
        }
    });
}


