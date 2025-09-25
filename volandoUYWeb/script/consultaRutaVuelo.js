function cargarRutas() {
  fetch('json/rutasVuelo.json')
    .then(res => res.json())
    .then(data => {
      const contenedor = document.getElementById('lista-rutas');
      if (!contenedor) return; // Evita error si el div no existe
      contenedor.innerHTML = '';

      data.forEach(ruta => {
        const card = document.createElement('article');
        card.classList.add('ruta-card');

        card.innerHTML = `
          <img src="${ruta.imagen}" alt="Imagen de ${ruta.ciudadDestino.nombre}">
          <h3>${ruta.nombre}</h3>
          <p><strong>Aerolínea:</strong> ${ruta.aerolinea.nombre}</p>
          <p><strong>Origen:</strong> ${ruta.ciudadOrigen.nombre} (${ruta.ciudadOrigen.aeropuerto})</p>
          <p><strong>Destino:</strong> ${ruta.ciudadDestino.nombre} (${ruta.ciudadDestino.aeropuerto})</p>
          <p><strong>Categorías:</strong> ${ruta.categorias.join(', ')}</p>
          <p><strong>Costo base:</strong> $${ruta.costoBase}</p>
          <button class="btn-primary">Ver detalle</button>
        `;

        contenedor.appendChild(card);
      });
    })
    .catch(err => console.error('Error cargando rutas:', err));
}

// Fin del archivo