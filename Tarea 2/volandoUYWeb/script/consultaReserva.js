// consultaReserva.js
// Versión simple con variables declaradas en global

// Estado
let usuarioActual = null;
let usuariosData = [];

let rutasDataReserva = [];
let vuelosDataReserva = [];
let reservasDataReserva = [];

const rutasURLReserva = "json/rutasVuelo.json";
const vuelosURLReserva = "json/vuelos.json";
const usuariosURL = "json/usuarios.json";
const reservasURL = "json/reservas.json"; // dataset de reservas

// Inicialización
// Inicialización de la vista de Consulta de Reserva
function initConsultaReserva() {
  // Cargar datasets en paralelo
  Promise.all([
    fetch(usuariosURL).then((r) => r.json()),
    fetch(reservasURL).then((r) => r.json()),
    fetch(rutasURLReserva).then((r) => r.json()),
    fetch(vuelosURLReserva).then((r) => r.json()),
  ])
    .then(([usuarios, reservas, rutas, vuelos]) => {
      // Guardamos en memoria global
      usuariosData = usuarios;
      reservasDataReserva = reservas;
      rutasDataReserva = rutas;
      vuelosDataReserva = vuelos;

      // Configuramos botones de cliente/aerolínea/cerrar
      configurarBotones();

      // Configuramos listeners de filtros (aerolínea, origen, destino, buscador)
      configurarFiltros();

      // Si ya hay un usuario seleccionado, arrancamos flujo
      if (usuarioActual) {
        iniciarFlujoReserva();
      }
    })
    .catch((err) => console.error("Error inicializando:", err));
}

// Configurar botones
function configurarBotones() {
  const btnCliente = document.getElementById("btn-cliente");
  const btnAerolinea = document.getElementById("btn-aerolinea");
  const btnCerrar = document.getElementById("cerrar-panel");

  if (btnCliente) {
    btnCliente.addEventListener("click", () => {
      // Forzamos a Juan Pérez (id 3)
      usuarioActual = usuariosData.find((u) => u.id === 3);
      iniciarFlujoReserva();
    });
  }

  if (btnAerolinea) {
    btnAerolinea.addEventListener("click", () => {
      // Forzamos a LATAM (id 6)
      usuarioActual = usuariosData.find((u) => u.id === 6);
      iniciarFlujoReserva();
    });
  }

  if (btnCerrar) {
    btnCerrar.addEventListener("click", cerrarPanelReserva);
  }
}

function iniciarFlujoReserva() {
  let confirmadas = rutasDataReserva.filter(r => r.estado === "Confirmada");
  if (!usuarioActual) return;

  if (usuarioActual.tipo === "aerolinea") {
    confirmadas = confirmadas.filter(
      r => (r.aerolinea?.nickname || "").toLowerCase() === usuarioActual.nickname?.toLowerCase()
    );
  }

  window.rutasFiltrables = confirmadas;

  configurarFiltros();
  aplicarFiltrosRutas();
}


function configurarFiltros() {
  const selAerol = document.getElementById("select-aerolinea");
  const selOrigen = document.getElementById("select-origen");
  const selDestino = document.getElementById("select-destino");
  const inputNombre = document.getElementById("busqueda");

  if (!selAerol || !selOrigen || !selDestino || !inputNombre) return;

  const base = window.rutasFiltrables || [];

  // === Aerolínea vs Cliente ===
  if (usuarioActual?.tipo === "aerolinea") {
    const nombreAero = usuarioActual.nombre || usuarioActual.aerolinea?.nombre || usuarioActual.nickname;
    selAerol.innerHTML = `<option value="${usuarioActual.nickname}" selected>${nombreAero}</option>`;
    selAerol.disabled = true;
  } else {
    selAerol.disabled = false;
    const aerolineas = new Map();
    base.forEach(r => {
      if (r.aerolinea?.nickname) {
        aerolineas.set(r.aerolinea.nickname, r.aerolinea.nombre);
      }
    });
    selAerol.innerHTML = `<option value="Todos">Todas</option>`;
    aerolineas.forEach((nombre, nick) => {
      selAerol.innerHTML += `<option value="${nick}">${nombre}</option>`;
    });
    selAerol.onchange = aplicarFiltrosRutas;
  }

  // === Orígenes ===
  const origenes = [...new Set(base.map(r => r.ciudadOrigen?.nombre).filter(Boolean))];
  selOrigen.innerHTML = `<option value="Todos">Todos</option>`;
  origenes.forEach(o => selOrigen.innerHTML += `<option value="${o}">${o}</option>`);

  // === Destinos ===
  const destinos = [...new Set(base.map(r => r.ciudadDestino?.nombre).filter(Boolean))];
  selDestino.innerHTML = `<option value="Todos">Todos</option>`;
  destinos.forEach(d => selDestino.innerHTML += `<option value="${d}">${d}</option>`);

  // === Listeners comunes ===
  selOrigen.onchange = aplicarFiltrosRutas;
  selDestino.onchange = aplicarFiltrosRutas;
  inputNombre.oninput = aplicarFiltrosRutas;
}

// Mostrar rutas
function mostrarRutasReserva(lista) {
  const contenedor = document.getElementById("lista-rutas");
  if (!contenedor) return;
  contenedor.innerHTML = "";

  if (!lista.length) {
    contenedor.innerHTML = "<p>No hay rutas disponibles.</p>";
    return;
  }

  lista.forEach((r) => {
    const card = document.createElement("div");
    card.classList.add("ruta-card");
    card.innerHTML = `
      <p><strong>${r.nombre || "Ruta sin nombre"}</strong></p>
      <p><strong>Aerolínea:</strong> ${r.aerolinea?.nombre || "N/D"}</p>
      <p><strong>Origen:</strong> ${r.ciudadOrigen?.nombre || "N/D"}</p>
      <p><strong>Destino:</strong> ${r.ciudadDestino?.nombre || "N/D"}</p>
    `;
    card.addEventListener("click", () => {
      document
        .querySelectorAll(".ruta-card")
        .forEach((c) => c.classList.remove("seleccionada"));
      card.classList.add("seleccionada");
      mostrarVuelosDeRutaReserva(r.nombre);
    });
    contenedor.appendChild(card);
  });
}

// Mostrar vuelos
function mostrarVuelosDeRutaReserva(nombreRuta) {
  const contenedor = document.getElementById("lista-vuelos");
  if (!contenedor) return;
  contenedor.innerHTML = "";

  const vuelosRuta = vuelosDataReserva.filter(
    (v) => v.ruta === nombreRuta && v.estado === "Confirmada"
  );

  if (!vuelosRuta.length) {
    contenedor.innerHTML = "<p>No hay vuelos confirmados para esta ruta.</p>";
    return;
  }

  vuelosRuta.forEach((v) => {
    const card = document.createElement("div");
    card.classList.add("vuelo-card");
    card.innerHTML = `
      <img src="${v.imagen}" alt="Imagen del vuelo ${v.nombre}">
      <p><strong>${v.nombre}</strong></p>
      <p><strong>Fecha:</strong> ${v.fechaVuelo}</p>
      <p><strong>Hora:</strong> ${v.horaVuelo}</p>
      <p><strong>Duración:</strong> ${v.duracion}</p>
    `;
    card.addEventListener("click", () => {
      document
        .querySelectorAll(".vuelo-card")
        .forEach((c) => c.classList.remove("seleccionado"));
      card.classList.add("seleccionado");
      abrirPanelReserva(v);
    });
    contenedor.appendChild(card);
  });
}

// Panel de reservas
function abrirPanelReserva(vuelo) {
  const panel = document.getElementById("panel-reserva");
  if (!panel) return;
  panel.style.display = "block";

  const lista = document.getElementById("lista-reservas");
  if (!lista) return;
  lista.innerHTML = "";

  // Filtrar reservas de este vuelo y de este usuario
  const reservas = reservasDataReserva.filter(
    (r) => r.vuelo === vuelo.nombre && r.nickname === usuarioActual.nickname
  );

  if (!reservas.length) {
    lista.innerHTML = "<p>No hay reservas de este usuario para este vuelo.</p>";
    return;
  }

  reservas.forEach((r) => {
    const div = document.createElement("div");
    div.classList.add("reserva-card");
    div.innerHTML = `<p><strong>Reserva #${r.id}</strong></p>`;
    div.addEventListener("click", () => mostrarDetalleReserva(r));
    lista.appendChild(div);
  });
}

// Mostrar detalle de la reserva
function mostrarDetalleReserva(reserva) {
  const detalle = document.getElementById("detalle-reserva");
  if (!detalle) return;

  detalle.innerHTML = `
    <h3>Detalle de la Reserva</h3>
    <p><strong>Vuelo:</strong> ${reserva.vuelo}</p>
    <p><strong>Fecha de Reserva:</strong> ${reserva.fechaReserva}</p>
    <p><strong>Estado:</strong> ${reserva.estado}</p>
    <p><strong>Costo:</strong> ${reserva.costoReserva.monto} ${
    reserva.costoReserva.moneda
  }</p>
    ${
      reserva.detalle
        ? `<p><strong>Descripción:</strong> ${reserva.detalle.descripcion}</p>`
        : ""
    }
    ${
      reserva.detalle?.servicios
        ? `<p><strong>Servicios:</strong> ${reserva.detalle.servicios.join(
            ", "
          )}</p>`
        : ""
    }
  `;

  if (reserva.pasajeros && reserva.pasajeros.length > 0) {
    const lista = document.createElement("ul");
    reserva.pasajeros.forEach((p) => {
      const li = document.createElement("li");
      li.textContent = `${p.nombre} ${p.apellido}`;
      lista.appendChild(li);
    });
    detalle.innerHTML += "<h4>Otros Pasajeros</h4>";
    detalle.appendChild(lista);
  }
}

function cerrarPanelReserva() {
  const panel = document.getElementById("panel-reserva");
  if (panel) panel.style.display = "none";
}

function aplicarFiltrosRutas() {
  if (!window.rutasFiltrables) return;

  const origenSel = document.getElementById("select-origen")?.value || "Todos";
  const destinoSel = document.getElementById("select-destino")?.value || "Todos";
  const buscarNombre = document.getElementById("busqueda")?.value.toLowerCase().trim() || "";

  let lista = [...window.rutasFiltrables];

  // Cliente: usa el select de aerolínea
  if (usuarioActual?.tipo === "cliente") {
    const aerolineaSel = document.getElementById("select-aerolinea")?.value || "Todos";
    if (aerolineaSel !== "Todos") {
      lista = lista.filter(r => (r.aerolinea?.nickname || "").toLowerCase() === aerolineaSel.toLowerCase());
    }
  }

  // Aerolínea: refuerzo del filtro por seguridad
  if (usuarioActual?.tipo === "aerolinea") {
    lista = lista.filter(r => (r.aerolinea?.nickname || "").toLowerCase() === usuarioActual.nickname?.toLowerCase());
  }

  if (origenSel !== "Todos") {
    lista = lista.filter(r => r.ciudadOrigen?.nombre === origenSel);
  }
  if (destinoSel !== "Todos") {
    lista = lista.filter(r => r.ciudadDestino?.nombre === destinoSel);
  }
  if (buscarNombre) {
    lista = lista.filter(r => (r.nombre || "").toLowerCase().includes(buscarNombre));
  }

  mostrarRutasReserva(lista);
}