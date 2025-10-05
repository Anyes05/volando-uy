const paquetesURL = 'json/paquetes.json';
let paquetesData = [];
let paqueteActual = null;

function quitarTildes(texto) {
  return texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

function cargarPaquetes() {
  fetch(paquetesURL)
    .then(res => res.json())
    .then(data => {
      paquetesData = data;
      mostrarPaquetes(paquetesData);
      cargarFiltros(paquetesData);
    })
    .catch(err => console.error('Error cargando paquetes:', err));
}

function mostrarPaquetes(lista) {
  const contenedor = document.getElementById('lista-paquetes');
  const statsContainer = document.getElementById('paquetes-stats');
  
  if (statsContainer) {
    statsContainer.innerHTML = `<span class="total-paquetes">${lista.length} ${lista.length === 1 ? 'paquete encontrado' : 'paquetes encontrados'}</span>`;
  }
  
  contenedor.innerHTML = '';

  if (!lista || lista.length === 0) {
    contenedor.innerHTML = '<p>No se encontraron paquetes.</p>';
    return;
  }

  lista.forEach((p, idx) => {
    const card = document.createElement('div');
    card.classList.add('paquete-card');
    card.onclick = () => mostrarDetallePaquete(p);

    card.innerHTML = `
      <img src="${p.imagen || ''}" alt="${p.nombre}">
      <h3>${p.nombre}</h3>
      <p>${p.descripcion}</p>
      <p><strong>Descuento:</strong> ${p.descuento}%</p>
      <p><strong>Costo total:</strong> $${p.costoTotal}</p>
    `;

    contenedor.appendChild(card);
  });
}

function mostrarDetallePaquete(paquete) {
  paqueteActual = paquete;
  const detalle = document.querySelector('.detalle-paquete');

  document.getElementById('lista-paquetes').style.display = 'none';
  detalle.style.display = 'block';

  document.getElementById('paquete-imagen').src = paquete.imagen || '';
  document.getElementById('paquete-nombre').textContent = paquete.nombre;
  document.getElementById('paquete-descripcion').textContent = paquete.descripcion;
  document.getElementById('paquete-dias').textContent = paquete.diasValidos || 'No especificado';
  document.getElementById('paquete-descuento').textContent = paquete.descuento || 0;
  document.getElementById('paquete-fecha').textContent = paquete.fechaAlta || '';
  document.getElementById('paquete-costo').textContent = paquete.costoTotal || 0;

  const cont = document.getElementById('paquete-cantidades');
  cont.innerHTML = '';

  const cantidades = paquete.cantidades || [];
  if (cantidades.length === 0) {
    cont.innerHTML = '<p>No hay rutas asociadas a este paquete.</p>';
  } else {
    cantidades.forEach(c => {
      const item = document.createElement('div');
      item.classList.add('ruta-item');
      item.onclick = () => verDetalleRuta(c.rutaId);
      item.innerHTML = `
        <h4>${c.nombreRuta || ('Ruta ' + c.rutaId)}</h4>
        <p><strong>Cantidad:</strong> ${c.cantidad}</p>
        <p><strong>Tipo:</strong> ${c.tipo || 'N/A'}</p>
      `;
      cont.appendChild(item);
    });
  }

  detalle.scrollIntoView({ behavior: 'smooth' });
}

function verDetalleRuta(id) {
  // Navegar a la vista de consultaRutaVuelo y pasar el id (simplificado)
  console.log('Solicitado detalle de ruta', id);
  // Si existe una función global para abrir la vista de rutas, usarla
  if (typeof controladorDeVista !== 'undefined' && typeof controladorDeVista.cargar === 'function') {
    controladorDeVista.cargar('consultaRutaVuelo.html');
    // Se podría almacenar el id en sessionStorage para que la vista de rutas lo lea
    try { sessionStorage.setItem('rutaSeleccionadaId', String(id)); } catch(e){}
  }
}

function cargarFiltros(data) {
  // Registrar el listener de búsqueda por nombre. Buscamos el elemento cada vez
  // para ser robustos ante la carga dinámica del HTML por controladorDeVista.
  const buscador = document.getElementById('buscador-nombre');
  if (buscador) {
    buscador.removeEventListener('input', filtrar);
    buscador.addEventListener('input', filtrar);
  } else {
    // Si no está presente todavía, intentar reintentar brevemente
    setTimeout(() => cargarFiltros(data), 100);
  }
}

function filtrar() {
  const input = document.getElementById('buscador-nombre');
  const texto = input ? quitarTildes(input.value.toLowerCase()) : '';

  const filtrados = paquetesData.filter(p => {
    const nombre = quitarTildes((p.nombre || '').toLowerCase());
    const descripcion = quitarTildes((p.descripcion || '').toLowerCase());
    return texto === '' || nombre.includes(texto) || descripcion.includes(texto);
  });

  mostrarPaquetes(filtrados);
}

function volverAListaPaquetes() {
  document.getElementById('lista-paquetes').style.display = 'grid';
  document.querySelector('.detalle-paquete').style.display = 'none';
  paqueteActual = null;
}

// Event listeners para interacciones globales
document.addEventListener('click', function(e) {
  if (e.target.classList.contains('tab-btn')) {
    // no hay tabs aquí pero preservamos el patrón
  }
});

window.initConsultaPaquete = function() {
  cargarPaquetes();
};

// Auto-inicializar para desarrollo (cuando se abre la página directamente)
cargarPaquetes();
