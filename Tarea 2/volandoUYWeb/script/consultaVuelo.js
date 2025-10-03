/* consultavuelo.js
   Flujo:
   - carga vuelos desde "vuelos.json"
   - llena select de aerolineas
   - muestra vuelos por ruta
   - muestra detalle vuelo + reservas condicionales
*/

// Simulación de sesión: definir aquí el tipo de usuario y nombre logueado
// Puedes cambiar estos valores desde la consola o usando los botones de prueba agregados abajo
let tipoUsuario = 'anonimo'; // 'anonimo' | 'aerolinea' | 'cliente'
let usuarioLogueado = '';

// Mapeo de código de aerolínea a nombre real (se carga desde usuarios.json)
let AEROLINEA_NOMBRES = {};


// Permite funcionar tanto en carga directa como dinámica (dentro de #main-content)
function getRoot() {
  return document.getElementById('main-content') || document;
}

function getEl(sel) {
  return getRoot().querySelector(sel);
}

let selectAerolinea, listaRutas, listaVuelos, detalleVueloDiv, vueloImg, vueloInfo, reservasDiv, listaReservas, detalleReservaDiv, detalleReservaInfo;

function asignarDomElements() {
  selectAerolinea = getEl('#selectAerolinea');
  listaRutas = getEl('#listaRutas');
  listaVuelos = getEl('#listaVuelos');
  detalleVueloDiv = getEl('#detalle-vuelo');
  vueloImg = getEl('#vuelo-img');
  vueloInfo = getEl('#vuelo-info');
  reservasDiv = getEl('#reservas');
  listaReservas = getEl('#lista-reservas');
  detalleReservaDiv = getEl('#detalle-reserva');
  detalleReservaInfo = getEl('#detalleReserva');
  // Asignar listener de cambio cada vez que se reasignan los elementos
  if (selectAerolinea) {
    selectAerolinea.onchange = () => {
      const id = selectAerolinea.value;
      clearAfterAerolinea();
      if (!id) {
        listaRutas.innerHTML = 'Elija una aerolínea';
        return;
      }
      const aero = DATA && DATA.aerolineas ? DATA.aerolineas.find(x => x.id === id) : null;
      if (!aero || !aero.rutas || aero.rutas.length === 0) {
        listaRutas.innerHTML = 'Sin rutas';
        return;
      }
      listaRutas.innerHTML = '';
      aero.rutas.forEach(r => {
        const pill = document.createElement('div');
        pill.className = 'ruta-pill';
        pill.textContent = r.nombre;
        pill.dataset.rutaId = r.id;
        pill.addEventListener('click', () => {
          Array.from(listaRutas.children).forEach(c => c.classList.remove('active'));
          pill.classList.add('active');
          mostrarVuelos(r);
        });
        listaRutas.appendChild(pill);
      });
    };
  }
}


// Encapsular toda la inicialización en una función global
window.initConsultaVuelo = function() {
  asignarDomElements();
  // Volver a asignar listeners y cargar datos
  // Primero cargar usuarios.json para obtener nombres de aerolínea
  fetch('json/usuarios.json')
    .then(r => r.json())
    .then(usuarios => {
      // Mapear código (ej: LA) a nombre real (ej: LATAM Uruguay)
      AEROLINEA_NOMBRES = {};
      usuarios.forEach(u => {
        if (u.tipo === 'aerolinea') {
          let cod = '';
          if (u.nickname && /^[A-Z]{2,4}/.test(u.nickname)) {
            cod = u.nickname.match(/[A-Z]{2,4}/)[0];
          } else if (u.nombre && /^[A-Z]{2,4}/.test(u.nombre.replace(/\s/g, ''))) {
            cod = u.nombre.replace(/\s/g, '').match(/[A-Z]{2,4}/)[0];
          }
          if (cod) AEROLINEA_NOMBRES[cod] = u.nombre;
        }
      });
      cargarVuelos();
    })
    .catch(() => {
      cargarVuelos();
    });


};

// Si la página se carga directamente, inicializar automáticamente
if (!window.__consultaVueloAutoInitDone) {
  window.__consultaVueloAutoInitDone = true;
  if (document.getElementById('selectAerolinea')) {
    window.initConsultaVuelo();
  }
}




let DATA = null;


// Ya no hay listeners para controles de usuario/nombre. Cambia tipoUsuario/usuarioLogueado arriba para simular sesión.




function cargarVuelos() {
  // Reasignar elementos DOM por si la vista fue recargada dinámicamente
  asignarDomElements();
  fetch('json/vuelos.json')
    .then(r => {
      if (!r.ok) throw new Error('No se pudo cargar JSON');
      return r.json();
    })
    .then(json => {
      DATA = agruparPorAerolinea(json);
      populateAerolineas();
      agregarControlesPrueba();
    })
    .catch(err => {
      console.warn('Fetch falló, usando datos embebidos. Error:', err);
      DATA = agruparPorAerolinea(fallbackData());
      populateAerolineas();
      agregarControlesPrueba();
    });
}

// Agrupa vuelos por aerolínea y rutas
function agruparPorAerolinea(vuelos) {
  const aerolineas = {};
  vuelos.forEach(v => {
    // Extraer código aerolínea (antes del primer número)
    const match = v.nombre.match(/^Vuelo\s*([A-Z]+)[0-9]+/);
    const codigo = match ? match[1] : 'OTRA';
    // Usar nombre real si está en el mapeo
    const nombreAero = AEROLINEA_NOMBRES[codigo] || codigo;
    if (!aerolineas[codigo]) aerolineas[codigo] = { id: codigo, nombre: nombreAero, rutas: {} };
    // Agrupar por ruta
    if (!aerolineas[codigo].rutas[v.ruta]) aerolineas[codigo].rutas[v.ruta] = { id: v.ruta, nombre: v.ruta, vuelos: [] };
    aerolineas[codigo].rutas[v.ruta].vuelos.push(v);
  });
  // Convertir rutas a array
  return {
    aerolineas: Object.values(aerolineas).map(a => ({
      id: a.id,
      nombre: a.nombre,
      rutas: Object.values(a.rutas)
    }))
  };
}

/* Poblamos select de aerolineas */
function populateAerolineas() {
  selectAerolinea.innerHTML = '<option value="">-- Elegir aerolínea --</option>';
  if (!DATA || !DATA.aerolineas) return;
  DATA.aerolineas.forEach(a => {
    const opt = document.createElement('option');
    opt.value = a.id;
    opt.textContent = a.nombre;
    selectAerolinea.appendChild(opt);
  });
}



/* mostrar vuelos de una ruta */
function mostrarVuelos(ruta) {
  listaVuelos.innerHTML = '';
  detalleVueloDiv.style.display = 'none';
  reservasDiv.style.display = 'none';
  detalleReservaDiv.style.display = 'none';

  if (!ruta.vuelos || ruta.vuelos.length === 0) {
    listaVuelos.innerHTML = '<p>No hay vuelos para esta ruta.</p>';
    return;
  }
  ruta.vuelos.forEach(v => {
    const card = document.createElement('div');
    card.className = 'vuelo-card';
    const html = `
      <img src="${v.imagen}" alt="${v.nombre}">
      <div class="card-body">
        <h4>${v.nombre}</h4>
        <p>${v.ruta} — ${v.fechaVuelo} ${v.horaVuelo}</p>
      </div>
    `;
    card.innerHTML = html;
    card.addEventListener('click', () => mostrarDetalleVuelo(v));
    listaVuelos.appendChild(card);
  });
}

/* mostrar detalle de vuelo */
function mostrarDetalleVuelo(vuelo) {
  detalleVueloDiv.style.display = 'block';
  reservasDiv.style.display = 'block';
  detalleReservaDiv.style.display = 'none';

  vueloImg.src = vuelo.imagen || '';
  vueloInfo.innerHTML = `
    <p><strong>Nombre:</strong> ${vuelo.nombre}</p>
    <p><strong>Fecha:</strong> ${vuelo.fechaVuelo}</p>
    <p><strong>Hora:</strong> ${vuelo.horaVuelo}</p>
    <p><strong>Duración:</strong> ${vuelo.duracion}</p>
    <p><strong>Asientos Turista (máx):</strong> ${vuelo.asientosMaxTurista}</p>
    <p><strong>Asientos Ejecutivo (máx):</strong> ${vuelo.asientosMaxEjecutivo}</p>
    <p><strong>Fecha de alta:</strong> ${typeof vuelo.fechaAlta !== 'undefined' ? vuelo.fechaAlta : '—'}</p>
  `;
  // guardo el nombre del vuelo para poder refrescar reservas al cambiar modo
  vueloInfo.setAttribute('data-current-flight', vuelo.nombre);

  mostrarReservas(vuelo);
}
// Agrega controles de prueba para cambiar tipo de usuario y nombre logueado
function agregarControlesPrueba() {
  // Insertar controles de prueba arriba del root (main-content o body)
  const root = document.getElementById('main-content') || document.body;
  if (root.querySelector('#controles-prueba')) return;
  const div = document.createElement('div');
  div.id = 'controles-prueba';
  div.style = 'margin:18px 0 0 0; padding:12px; background:rgba(255,255,255,0.07); border-radius:10px; max-width:500px;';
  div.innerHTML = `
    <strong>Simular sesión:</strong>
    <button id="btnAnonimo" style="margin:0 6px;">Anonimo</button>
    <button id="btnAero" style="margin:0 6px;">Aerolínea</button>
    <button id="btnCliente" style="margin:0 6px;">Cliente</button>
    <input id="inputCliente" type="text" placeholder="Nombre cliente" style="margin-left:10px; width:140px;" />
    <span id="estadoSesion" style="margin-left:10px; color:#01AAF5;"></span>
  `;
  root.insertBefore(div, root.firstChild);
  root.querySelector('#btnAnonimo').onclick = () => { tipoUsuario = 'anonimo'; usuarioLogueado = ''; refrescarVistaSesion(); };
  root.querySelector('#btnAero').onclick = () => { tipoUsuario = 'aerolinea'; usuarioLogueado = ''; refrescarVistaSesion(); };
  root.querySelector('#btnCliente').onclick = () => { tipoUsuario = 'cliente'; usuarioLogueado = root.querySelector('#inputCliente').value.trim(); refrescarVistaSesion(); };
  root.querySelector('#inputCliente').oninput = (e) => { if (tipoUsuario === 'cliente') { usuarioLogueado = e.target.value.trim(); refrescarVistaSesion(); } };
  refrescarVistaSesion();
}

function refrescarVistaSesion() {
  // Actualiza el estado visual y refresca reservas si hay vuelo abierto
  const root = document.getElementById('main-content') || document;
  const estado = root.querySelector('#estadoSesion');
  if (estado) {
    estado.textContent = `Modo: ${tipoUsuario}${tipoUsuario === 'cliente' && usuarioLogueado ? ' (' + usuarioLogueado + ')' : ''}`;
  }
  // Si hay vuelo en detalle, refrescar reservas
  if (vueloInfo && vueloInfo.getAttribute('data-current-flight')) {
    const found = findFlightByName(vueloInfo.getAttribute('data-current-flight'));
    if (found) mostrarReservas(found);
  }
}

/* mostrar reservas según tipo de usuario */
function mostrarReservas(vuelo) {
  listaReservas.innerHTML = '';
  detalleReservaDiv.style.display = 'none';

  // si no tiene field reservas, asumimos array vacío
  const reservas = Array.isArray(vuelo.reservas) ? vuelo.reservas : [];

  if (tipoUsuario === 'aerolinea') {
    if (reservas.length === 0) {
      listaReservas.innerHTML = '<p>No hay reservas en este vuelo.</p>';
      return;
    }
    reservas.forEach(r => {
      const div = document.createElement('div');
      div.className = 'reserva-card';
      div.innerHTML = `<strong>${r.cliente}</strong> — Asiento ${r.asiento} • ${r.clase}`;
      div.addEventListener('click', () => mostrarDetalleReserva(r));
      listaReservas.appendChild(div);
    });
  } else if (tipoUsuario === 'cliente') {
    if (!usuarioLogueado) {
      listaReservas.innerHTML = '<p>No se detectó usuario logueado.</p>';
      return;
    }
    const miReserva = reservas.find(r => r.cliente.toLowerCase() === usuarioLogueado.toLowerCase());
    if (!miReserva) {
      listaReservas.innerHTML = '<p>No tenés reservas en este vuelo.</p>';
      return;
    }
    const div = document.createElement('div');
    div.className = 'reserva-card';
    div.innerHTML = `<strong>Tu reserva</strong> — Asiento ${miReserva.asiento} • ${miReserva.clase}`;
    div.addEventListener('click', () => mostrarDetalleReserva(miReserva));
    listaReservas.appendChild(div);
  } else {
    // anonimo o visitante: no mostramos reservas
    listaReservas.innerHTML = '<p>Iniciá sesión para ver reservas o accedé como aerolínea/cliente.</p>';
  }
}

/* mostrar detalle de reserva */
function mostrarDetalleReserva(reserva) {
  detalleReservaDiv.style.display = 'block';
  detalleReservaInfo.innerHTML = `
    <p><strong>Cliente:</strong> ${reserva.cliente}</p>
    <p><strong>Asiento:</strong> ${reserva.asiento}</p>
    <p><strong>Clase:</strong> ${reserva.clase}</p>
    <p><strong>Fecha de reserva:</strong> ${reserva.fecha}</p>
    ${reserva.email ? `<p><strong>Email:</strong> ${reserva.email}</p>` : ''}
    ${reserva.telefono ? `<p><strong>Teléfono:</strong> ${reserva.telefono}</p>` : ''}
  `;
}

/* buscar vuelo por nombre (utilidad para refrescar reservas al cambiar modo) */
function findFlightByName(nombre) {
  if (!DATA) return null;
  for (const aero of DATA.aerolineas) {
    for (const ruta of aero.rutas) {
      for (const vuelo of ruta.vuelos) {
        if (vuelo.nombre === nombre) return vuelo;
      }
    }
  }
  return null;
}

/* limpia secciones al cambiar aerolinea */
function clearAfterAerolinea() {
  listaVuelos.innerHTML = '';
  detalleVueloDiv.style.display = 'none';
  reservasDiv.style.display = 'none';
  detalleReservaDiv.style.display = 'none';
}


