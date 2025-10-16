// JavaScript para consulta de paquetes - Conectado con backend
let paquetesData = [];
let paqueteActual = null;
let rutasPaqueteActual = [];

// Función para quitar tildes y normalizar texto
function quitarTildes(texto) {
  return texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

// Función para cargar paquetes desde el backend
async function cargarPaquetes() {
  try {
    console.log('Cargando paquetes desde backend...');
    const response = await fetch('/VolandoUy-WebApp/api/paquetes');
    
    if (!response.ok) {
      throw new Error(`Error HTTP: ${response.status}`);
    }
    
    const data = await response.json();
    paquetesData = data.paquetes || [];
    console.log('Paquetes cargados:', paquetesData);
    
    mostrarPaquetes(paquetesData);
    cargarFiltros(paquetesData);
  } catch (error) {
    console.error('Error cargando paquetes:', error);
    mostrarError('Error al cargar los paquetes. Por favor, intente nuevamente.');
  }
}

// Función para mostrar error al usuario
function mostrarError(mensaje) {
  const contenedor = document.getElementById('lista-paquetes');
  if (contenedor) {
    contenedor.innerHTML = `
      <div style="text-align: center; padding: 2rem; color: #eaf6fb;">
        <i class="fas fa-exclamation-triangle" style="font-size: 3rem; margin-bottom: 1rem; color: #dc3545;"></i>
        <p>${mensaje}</p>
        <button onclick="cargarPaquetes()" style="
          background: linear-gradient(135deg, #01aaf5 0%, #2298ca 100%);
          color: white;
          border: none;
          padding: 0.75rem 1.5rem;
          border-radius: 8px;
          cursor: pointer;
          margin-top: 1rem;
        ">Reintentar</button>
      </div>
    `;
  }
}

// Función para mostrar paquetes en la interfaz
function mostrarPaquetes(lista) {
  const contenedor = document.getElementById('lista-paquetes');
  const statsContainer = document.getElementById('paquetes-stats');
  
  if (statsContainer) {
    statsContainer.innerHTML = `<span class="total-paquetes">${lista.length} ${lista.length === 1 ? 'paquete encontrado' : 'paquetes encontrados'}</span>`;
  }
  
  if (!contenedor) {
    console.error('Contenedor de paquetes no encontrado');
    return;
  }
  
  contenedor.innerHTML = '';

  if (!lista || lista.length === 0) {
    contenedor.innerHTML = `
      <div style="text-align: center; padding: 2rem; color: #eaf6fb;">
        <i class="fas fa-box-open" style="font-size: 3rem; margin-bottom: 1rem; color: #01aaf5;"></i>
        <p>No se encontraron paquetes disponibles.</p>
      </div>
    `;
    return;
  }

  lista.forEach((p, idx) => {
    const card = document.createElement('div');
    card.classList.add('paquete-card');
    card.onclick = () => mostrarDetallePaquete(p);

    // Convertir imagen de byte array a base64 si existe
    let imagenSrc = '';
    if (p.foto && p.foto.length > 0) {
      try {
        const base64 = btoa(String.fromCharCode(...p.foto));
        imagenSrc = `data:image/jpeg;base64,${base64}`;
      } catch (e) {
        console.warn('Error convirtiendo imagen:', e);
        imagenSrc = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMzAwIiBoZWlnaHQ9IjIwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSIjMzI0MzRiIi8+PHRleHQgeD0iNTAlIiB5PSI1MCUiIGZvbnQtZmFtaWx5PSJBcmlhbCIgZm9udC1zaXplPSIxNCIgZmlsbD0iI2VhZjZmYiIgdGV4dC1hbmNob3I9Im1pZGRsZSIgZHk9Ii4zZW0iPkltYWdlbiBubyBkaXNwb25pYmxlPC90ZXh0Pjwvc3ZnPg==';
      }
    } else {
      imagenSrc = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMzAwIiBoZWlnaHQ9IjIwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSIjMzI0MzRiIi8+PHRleHQgeD0iNTAlIiB5PSI1MCUiIGZvbnQtZmFtaWx5PSJBcmlhbCIgZm9udC1zaXplPSIxNCIgZmlsbD0iI2VhZjZmYiIgdGV4dC1hbmNob3I9Im1pZGRsZSIgZHk9Ii4zZW0iPkltYWdlbiBubyBkaXNwb25pYmxlPC90ZXh0Pjwvc3ZnPg==';
    }

    card.innerHTML = `
      <img src="${imagenSrc}" alt="${p.nombre}">
      <h3>${p.nombre || 'Sin nombre'}</h3>
      <p>${p.descripcion || 'Sin descripción'}</p>
      <p class="descuento"><strong>Descuento:</strong> ${p.descuento || 0}%</p>
      <p class="costo"><strong>Costo total:</strong> $${p.costoTotal || 0}</p>
    `;

    contenedor.appendChild(card);
  });
}

// Función para mostrar detalle de un paquete específico
async function mostrarDetallePaquete(paquete) {
  try {
    console.log('Cargando detalle del paquete:', paquete.nombre);
    
    // Cargar detalles completos del paquete desde el backend
    const response = await fetch(`/VolandoUy-WebApp/api/paquetes/${encodeURIComponent(paquete.nombre)}`);
    
    if (!response.ok) {
      throw new Error(`Error HTTP: ${response.status}`);
    }
    
    const data = await response.json();
    const paqueteCompleto = data.paquete;
    rutasPaqueteActual = data.rutas || [];
    
    console.log('Detalle del paquete cargado:', paqueteCompleto);
    console.log('Rutas del paquete:', rutasPaqueteActual);
    
    paqueteActual = paqueteCompleto;
    mostrarDetalleEnInterfaz(paqueteCompleto);
    
  } catch (error) {
    console.error('Error cargando detalle del paquete:', error);
    mostrarError('Error al cargar los detalles del paquete. Por favor, intente nuevamente.');
  }
}

// Función para mostrar el detalle en la interfaz
function mostrarDetalleEnInterfaz(paquete) {
  const detalle = document.querySelector('.detalle-paquete');
  if (!detalle) {
    console.error('Elemento de detalle no encontrado');
    return;
  }

  document.getElementById('lista-paquetes').style.display = 'none';
  detalle.style.display = 'block';

  // Convertir imagen de byte array a base64 si existe
  let imagenSrc = '';
  if (paquete.foto && paquete.foto.length > 0) {
    try {
      const base64 = btoa(String.fromCharCode(...paquete.foto));
      imagenSrc = `data:image/jpeg;base64,${base64}`;
    } catch (e) {
      console.warn('Error convirtiendo imagen:', e);
      imagenSrc = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMzAwIiBoZWlnaHQ9IjIwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSIjMzI0MzRiIi8+PHRleHQgeD0iNTAlIiB5PSI1MCUiIGZvbnQtZmFtaWx5PSJBcmlhbCIgZm9udC1zaXplPSIxNCIgZmlsbD0iI2VhZjZmYiIgdGV4dC1hbmNob3I9Im1pZGRsZSIgZHk9Ii4zZW0iPkltYWdlbiBubyBkaXNwb25pYmxlPC90ZXh0Pjwvc3ZnPg==';
    }
  } else {
    imagenSrc = 'data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMzAwIiBoZWlnaHQ9IjIwMCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48cmVjdCB3aWR0aD0iMTAwJSIgaGVpZ2h0PSIxMDAlIiBmaWxsPSIjMzI0MzRiIi8+PHRleHQgeD0iNTAlIiB5PSI1MCUiIGZvbnQtZmFtaWx5PSJBcmlhbCIgZm9udC1zaXplPSIxNCIgZmlsbD0iI2VhZjZmYiIgdGV4dC1hbmNob3I9Im1pZGRsZSIgZHk9Ii4zZW0iPkltYWdlbiBubyBkaXNwb25pYmxlPC90ZXh0Pjwvc3ZnPg==';
  }

  document.getElementById('paquete-imagen').src = imagenSrc;
  document.getElementById('paquete-nombre').textContent = paquete.nombre || 'Sin nombre';
  document.getElementById('paquete-descripcion').textContent = paquete.descripcion || 'Sin descripción';
  document.getElementById('paquete-dias').textContent = paquete.diasValidos || 'No especificado';
  document.getElementById('paquete-descuento').textContent = paquete.descuento || 0;
  document.getElementById('paquete-fecha').textContent = paquete.fechaAlta || '';
  document.getElementById('paquete-costo').textContent = paquete.costoTotal || 0;

  const cont = document.getElementById('paquete-cantidades');
  cont.innerHTML = '';

  if (rutasPaqueteActual.length === 0) {
    cont.innerHTML = '<p>No hay rutas asociadas a este paquete.</p>';
  } else {
    rutasPaqueteActual.forEach((ruta, index) => {
      const item = document.createElement('div');
      item.classList.add('ruta-item');
      item.onclick = () => verDetalleRuta(ruta);
      item.innerHTML = `
        <h4>${ruta.nombre || ('Ruta ' + (index + 1))}</h4>
        <p><strong>Origen:</strong> ${ruta.ciudadOrigen || 'N/A'}</p>
        <p><strong>Destino:</strong> ${ruta.ciudadDestino || 'N/A'}</p>
        <p><strong>Descripción:</strong> ${ruta.descripcion || 'Sin descripción'}</p>
      `;
      cont.appendChild(item);
    });
  }

  detalle.scrollIntoView({ behavior: 'smooth' });
}

// Función para ver detalle de una ruta específica
function verDetalleRuta(ruta) {
  console.log('Solicitado detalle de ruta:', ruta);
  
  // Almacenar la ruta seleccionada en sessionStorage
  try {
    sessionStorage.setItem('rutaSeleccionada', JSON.stringify(ruta));
  } catch (e) {
    console.warn('Error almacenando ruta en sessionStorage:', e);
  }
  
  // Navegar a la vista de consulta de ruta de vuelo
  if (typeof controladorDeVista !== 'undefined' && typeof controladorDeVista.cargar === 'function') {
    controladorDeVista.cargar('consultaRutaVuelo.html');
  } else {
    // Fallback: mostrar información en un alert
    alert(`Detalle de ruta: ${ruta.nombre}\nOrigen: ${ruta.ciudadOrigen}\nDestino: ${ruta.ciudadDestino}\nDescripción: ${ruta.descripcion}`);
  }
}

// Función para cargar filtros
function cargarFiltros(data) {
  const buscador = document.getElementById('buscador-nombre');
  if (buscador) {
    buscador.removeEventListener('input', filtrar);
    buscador.addEventListener('input', filtrar);
  } else {
    // Si no está presente todavía, intentar reintentar brevemente
    setTimeout(() => cargarFiltros(data), 100);
  }
}

// Función para filtrar paquetes
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

// Función para volver a la lista de paquetes
function volverAListaPaquetes() {
  const listaPaquetes = document.getElementById('lista-paquetes');
  const detallePaquete = document.querySelector('.detalle-paquete');
  
  if (listaPaquetes) listaPaquetes.style.display = 'grid';
  if (detallePaquete) detallePaquete.style.display = 'none';
  
  paqueteActual = null;
  rutasPaqueteActual = [];
}

// Función de inicialización
function initConsultaPaquete() {
  console.log('Inicializando consulta de paquetes...');
  cargarPaquetes();
}

// Auto-inicializar cuando se carga la página
document.addEventListener('DOMContentLoaded', function() {
  console.log('DOM cargado, inicializando consulta de paquetes...');
  initConsultaPaquete();
});

// Exportar función para uso global
window.initConsultaPaquete = initConsultaPaquete;
window.volverAListaPaquetes = volverAListaPaquetes;
