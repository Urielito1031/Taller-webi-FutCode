let descripciones = {
    descripcionBronce : 'El Starter Pack es perfecto para los nuevos entrenadores que comienzan su camino en FutCode. Su color bronceado refleja humildad, esfuerzo y crecimiento. Contiene 12 artículos básicos con potencial de sorpresa. Ideal para: Principiantes o jugadores que disfrutan del reto de construir desde abajo.',
    descripcionPlata : 'Este sobre de acabado metálico en plata ofrece una selección sólida de 12 artículos, pensados para jugadores que están en plena evolución. Con un diseño moderno y pulido, el Pro Pack simboliza progreso y consistencia. Ideal para: Quienes buscan equilibrio entre valor y rendimiento en sus recompensas.',
    descripcionOro : 'Un sobre brillante y elegante, reservado para los verdaderos estrategas del juego. El Elite Pack contiene 12 artículos cuidadosamente seleccionados, incluyendo jugadores y mejoras clave para tu equipo. Su diseño dorado representa poder, rendimiento y alto valor. Ideal para: Usuarios que buscan potenciar su plantilla con jugadores de alto nivel.',
    descripcionEspecial : 'Exclusivo, lujoso y deslumbrante. El Legendary Pack está incrustado con rubíes y diamantes, reflejando su rareza y prestigio. Incluye 12 artículos premium con alta probabilidad de contenido épico. Su fondo cristalizado lo distingue del resto. Ideal para: Coleccionistas, competidores de élite o jugadores en busca de cartas únicas.'
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
                parrafoDescripcion.textContent = descripciones.descripcionBronce;
            break;
            case 'Sobre de Plata':
                parrafoDescripcion.textContent = descripciones.descripcionPlata;
            break;
            case 'Sobre de Oro':
                parrafoDescripcion.textContent = descripciones.descripcionOro;
            break;
            case 'Sobre Especial':
                parrafoDescripcion.textContent = descripciones.descripcionEspecial;
            break;
            default:
                parrafoDescripcion.textContent = '';
        }
    });
}


