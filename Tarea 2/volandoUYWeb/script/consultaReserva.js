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
    fetch(usuariosURL).then(r => r.json()),
    fetch(reservasURL).then(r => r.json()),
    fetch(rutasURLReserva).then(r => r.json()),
    fetch(vuelosURLReserva).then(r => r.json())
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
    .catch(err => console.error("Error inicializando:", err));
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

// Flujo principal
function iniciarFlujoReserva() {
  const confirmadas = rutasDataReserva.filter((r) => r.estado === "Confirmada");

  if (!usuarioActual) return;

  if (usuarioActual.tipo === "cliente") {
    // Cliente ve todas las rutas confirmadas
    mostrarRutasReserva(confirmadas);
  } else if (usuarioActual.tipo === "aerolinea") {
    // Aerolínea ve solo sus rutas, comparando por nickname
    const rutasAerolinea = confirmadas.filter(
      (r) =>
        r.aerolinea?.nickname?.toLowerCase() ===
        usuarioActual.nickname?.toLowerCase()
    );
    mostrarRutasReserva(rutasAerolinea);
  }

  console.log("Usuario actual:", usuarioActual.nombre, "-", usuarioActual.tipo);
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
  const aerolineaSel = document.getElementById("filtro-aerolinea").value;
  const origenSel = document.getElementById("filtro-origen").value;
  const destinoSel = document.getElementById("filtro-destino").value;
  const buscarNombre = document.getElementById("filtro-nombre").value.toLowerCase().trim();

  let lista = rutasDataReserva.filter(r => r.estado === "Confirmada");

  // Filtro por aerolínea
  if (aerolineaSel !== "Todas") {
    lista = lista.filter(r => r.aerolinea?.nickname === aerolineaSel);
  }

  // Filtro por origen
  if (origenSel !== "Todos") {
    lista = lista.filter(r => r.ciudadOrigen?.nombre === origenSel);
  }

  // Filtro por destino
  if (destinoSel !== "Todos") {
    lista = lista.filter(r => r.ciudadDestino?.nombre === destinoSel);
  }

  // Filtro por nombre de ruta
  if (buscarNombre) {
    lista = lista.filter(r => (r.nombre || "").toLowerCase().includes(buscarNombre));
  }

  mostrarRutasReserva(lista);
}