const rutasURL = "json/rutasVuelo.json";
let rutasData = [];
let currentPage = 1;
let cardsPerPage = 9; // valor inicial, se recalcula dinámicamente
let vueloSeleccionado = null;
let rutaSeleccionada = null;

// Inicializar
cargarRutas();
cargarVuelos();
let vuelosData = [];

function cargarVuelos() {
  fetch('json/vuelos.json')
    .then(res => res.json())
    .then(data => vuelosData = data)
    .catch(err => console.error("Error cargando vuelos:", err));
}

// Cargar rutas desde JSON
function cargarRutas() {
  fetch(rutasURL)
    .then(res => res.json())
    .then(data => {
      rutasData = data;
      const confirmadas = rutasData.filter(r => r.estado === "Confirmada");
      cargarFiltros(confirmadas);
      mostrarRutas(confirmadas);
      // 👇 fuerza recalculo de cards
      window.dispatchEvent(new Event("resize"));
    })
    .catch(err => console.error("Error cargando rutas:", err));
}

// 🔠 Función para quitar tildes/acentos
function quitarTildes(texto) {
  return texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

// Mostrar rutas en el contenedor
function mostrarRutas(lista) {
  const contenedor = document.getElementById("lista-rutas");
  contenedor.innerHTML = "";

  if (lista.length === 0) {
    contenedor.innerHTML = "<p>No se encontraron rutas.</p>";
    return;
  }

  // Calcular inicio y fin de la página
  const start = (currentPage - 1) * cardsPerPage;
  const end = start + cardsPerPage;
  const paginaRutas = lista.slice(start, end);

  paginaRutas.forEach((r) => {
    const card = document.createElement("article");
    card.classList.add("ruta-card");

    card.innerHTML = `
      <img src="${r.imagen}" alt="Imagen de ${r.ciudadDestino.nombre}" style="width:100%;max-width:400px;">
      <h3>${r.nombre}</h3>
      <p>${r.descripcion}</p>
      <button class="btn-ver-mas">Ver más</button>
      <div class="detalle-ruta" style="display:none; margin-top:10px;">
        <p><strong>Aerolínea:</strong> ${r.aerolinea.nombre}</p>
        <p><strong>Origen:</strong> ${r.ciudadOrigen.nombre} (${r.ciudadOrigen.aeropuerto})</p>
        <p><strong>Destino:</strong> ${r.ciudadDestino.nombre} (${r.ciudadDestino.aeropuerto})</p>
        <p><strong>Categorías:</strong> ${r.categorias.join(", ")}</p>
        <p><strong>Costo base:</strong> $${r.costoBase}</p>
        <p><strong>Fecha de alta:</strong> ${r.fechaAlta}</p>
      </div>
    `;

    const btn = card.querySelector(".btn-ver-mas");
    const detalle = card.querySelector(".detalle-ruta");

    btn.addEventListener("click", (e) => {
      e.stopPropagation();
      const visible = detalle.style.display === "block";
      detalle.style.display = visible ? "none" : "block";
      btn.textContent = visible ? "Ver más" : "Ver menos";
    });

    card.addEventListener("click", () => {
      document.querySelectorAll(".ruta-card").forEach(c => c.classList.remove("seleccionada"));
      card.classList.add("seleccionada");
      mostrarVuelosDeRuta(r.nombre);
    });

    contenedor.appendChild(card);
  });

  renderizarControles(lista.length);
  
  if (!lista.some(r => rutaSeleccionada && r.nombre === rutaSeleccionada.nombre)) {
  rutaSeleccionada = null;
  document.getElementById("lista-vuelos").innerHTML = "";
}

}
// Cargar aerolíneas y categorías únicas
function cargarFiltros(data) {
  const aerolineas = [...new Set(data.map((r) => r.aerolinea.nombre))];
  const categorias = [...new Set(data.flatMap((r) => r.categorias))];

  const selectAerolinea = document.getElementById("select-aerolinea");
  const selectCategoria = document.getElementById("select-categoria");

  selectAerolinea.innerHTML = '<option value="">Todas</option>';
  selectCategoria.innerHTML = '<option value="">Todas</option>';

  aerolineas.forEach((a) => {
    const option = document.createElement("option");
    option.value = a;
    option.textContent = a;
    selectAerolinea.appendChild(option);
  });

  categorias.forEach((c) => {
    const option = document.createElement("option");
    option.value = c;
    option.textContent = c;
    selectCategoria.appendChild(option);
  });

  // Eventos automáticos
  selectAerolinea.addEventListener("change", filtrar);
  selectCategoria.addEventListener("change", filtrar);
  document.getElementById("buscador-nombre").addEventListener("input", filtrar);
}

// Filtrar en tiempo real
function filtrar() {
  const aerolinea = document.getElementById("select-aerolinea").value;
  const categoria = document.getElementById("select-categoria").value;
  const texto = quitarTildes(
    document.getElementById("buscador-nombre").value.toLowerCase()
  );

  const filtradas = rutasData.filter((r) => {
    const nombreNormalizado = quitarTildes(r.nombre.toLowerCase());
    const origenNormalizado = quitarTildes(r.ciudadOrigen.nombre.toLowerCase());
    const destinoNormalizado = quitarTildes(r.ciudadDestino.nombre.toLowerCase());

    return (
      r.estado === "Confirmada" && // 🔑 solo confirmadas
      (aerolinea === "" || r.aerolinea.nombre === aerolinea) &&
      (categoria === "" || r.categorias.includes(categoria)) &&
      (texto === "" ||
        nombreNormalizado.includes(texto) ||
        origenNormalizado.includes(texto) ||
        destinoNormalizado.includes(texto))
    );
  });

  mostrarRutas(filtradas);
}

function mostrarVuelosDeRuta(nombreRuta) {
  const contenedor = document.getElementById("lista-vuelos");
  contenedor.innerHTML = "";

  const vuelosRuta = vuelosData.filter(
    v => v.ruta === nombreRuta && v.estado === "Confirmada"
  );

  if (vuelosRuta.length === 0) {
    contenedor.innerHTML = "<p>No hay vuelos confirmados para esta ruta.</p>";
    vueloSeleccionado = null; // 🔄 deseleccionar si no hay vuelos
    return;
  }

  vuelosRuta.forEach(v => {
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
      document.querySelectorAll(".vuelo-card").forEach(c => c.classList.remove("seleccionado"));
      card.classList.add("seleccionado");
      vueloSeleccionado = v;
    });

    contenedor.appendChild(card);
  });

  // 🔄 Si el vuelo seleccionado ya no está en la lista actual, lo deseleccionamos
  if (!vuelosRuta.some(v => vueloSeleccionado && v.nombre === vueloSeleccionado.nombre)) {
    vueloSeleccionado = null;
  }
}

//BOTON CONSULTAR VUELO
document.getElementById("btn-agregar-vuelo").textContent = "Consultar vuelo";

document.getElementById("btn-agregar-vuelo").addEventListener("click", () => {
  const mensaje = document.getElementById("mensaje-vuelo");

  if (!vueloSeleccionado) {
    mensaje.textContent = "⚠️ Por favor, selecciona un vuelo primero.";
    mensaje.style.display = "block";
    mensaje.style.opacity = "1";

    // Ocultar después de 3 segundos con fade-out
    setTimeout(() => {
      mensaje.style.opacity = "0";
      setTimeout(() => {
        mensaje.style.display = "none";
      }, 300); // coincide con el transition
    }, 3000);

    return;
  }

  // Redirigir a la otra página con el vuelo seleccionado
  window.location.href = `consulta-vuelo.html?vuelo=${encodeURIComponent(vueloSeleccionado.nombre)}`;
});


  //Lo comentado es para obtener el parámetro en la otra página de CONSULTA VUELO
  //const params = new URLSearchParams(window.location.search);
  //const vueloBuscado = params.get("vuelo");


function calcularCardsPorPagina() {
  const contenedor = document.getElementById("lista-rutas");
  if (!contenedor) return;

  // Ancho disponible
  const anchoContenedor = contenedor.clientWidth || 1100; 
  // Ancho estimado de cada tarjeta (incluyendo márgenes)
  const anchoCard = 300; 

  const columnas = Math.floor(anchoContenedor / anchoCard);
  cardsPerPage = columnas * 3; // 3 filas
  if (cardsPerPage < 3) cardsPerPage = 3; // mínimo
}

function mostrarRutas(lista) {
  calcularCardsPorPagina(); // recalcular antes de mostrar

  const contenedor = document.getElementById("lista-rutas");
  contenedor.innerHTML = "";

  if (lista.length === 0) {
    contenedor.innerHTML = "<p>No se encontraron rutas.</p>";
    rutaSeleccionada = null; // 🔄 deseleccionar si no hay rutas
    document.getElementById("lista-vuelos").innerHTML = "";
    return;
  }

  const start = (currentPage - 1) * cardsPerPage;
  const end = start + cardsPerPage;
  const paginaRutas = lista.slice(start, end);

  paginaRutas.forEach((r) => {
    const card = document.createElement("article");
    card.classList.add("ruta-card");

    card.innerHTML = `
      <img src="${r.imagen}" alt="Imagen de ${r.ciudadDestino.nombre}" style="width:100%;max-width:400px;">
      <h3>${r.nombre}</h3>
      <p>${r.descripcion}</p>
      <button class="btn-ver-mas">Ver más</button>
      <div class="detalle-ruta" style="display:none; margin-top:10px;">
        <p><strong>Aerolínea:</strong> ${r.aerolinea.nombre}</p>
        <p><strong>Origen:</strong> ${r.ciudadOrigen.nombre} (${r.ciudadOrigen.aeropuerto})</p>
        <p><strong>Destino:</strong> ${r.ciudadDestino.nombre} (${r.ciudadDestino.aeropuerto})</p>
        <p><strong>Categorías:</strong> ${r.categorias.join(", ")}</p>
        <p><strong>Costo base:</strong> $${r.costoBase}</p>
        <p><strong>Fecha de alta:</strong> ${r.fechaAlta}</p>
      </div>
    `;

    const btn = card.querySelector(".btn-ver-mas");
    const detalle = card.querySelector(".detalle-ruta");

    btn.addEventListener("click", (e) => {
      e.stopPropagation();
      const visible = detalle.style.display === "block";
      detalle.style.display = visible ? "none" : "block";
      btn.textContent = visible ? "Ver más" : "Ver menos";
    });

    card.addEventListener("click", () => {
      document.querySelectorAll(".ruta-card").forEach(c => c.classList.remove("seleccionada"));
      card.classList.add("seleccionada");
      rutaSeleccionada = r;
      mostrarVuelosDeRuta(r.nombre);
    });

    contenedor.appendChild(card);
  });

  // 🔄 Si la ruta seleccionada ya no está en la página actual, la deseleccionamos
  if (!paginaRutas.some(r => rutaSeleccionada && r.nombre === rutaSeleccionada.nombre)) {
    rutaSeleccionada = null;
    document.getElementById("lista-vuelos").innerHTML = "";
  }

  renderizarControles(lista.length);
}

function renderizarControles(totalRutas) {
  const contenedor = document.getElementById("paginacion");
  contenedor.innerHTML = "";

  const totalPaginas = Math.ceil(totalRutas / cardsPerPage);
  if (totalPaginas <= 1) return;

  const btnPrev = document.createElement("button");
  btnPrev.innerHTML = "◀ Anterior";
  btnPrev.disabled = currentPage === 1;
  btnPrev.addEventListener("click", () => {
    if (currentPage > 1) {
      currentPage--;
      mostrarRutas(rutasData);
      window.scrollTo({ top: 0, behavior: "smooth" });
    }
  });

  const btnNext = document.createElement("button");
  btnNext.innerHTML = "Siguiente ▶";
  btnNext.disabled = currentPage === totalPaginas;
  btnNext.addEventListener("click", () => {
    if (currentPage < totalPaginas) {
      currentPage++;
      mostrarRutas(rutasData);
      window.scrollTo({ top: 0, behavior: "smooth" });
    }
  });

  const indicador = document.createElement("span");
  indicador.textContent = `Página ${currentPage} de ${totalPaginas}`;

  contenedor.appendChild(btnPrev);
  contenedor.appendChild(indicador);
  contenedor.appendChild(btnNext);
}

// Recalcular al redimensionar ventana (con debounce para evitar múltiples llamadas)
let resizeTimeout;
window.addEventListener("resize", () => {
  clearTimeout(resizeTimeout);
  resizeTimeout = setTimeout(() => {
    if (rutasData && rutasData.length > 0) {
      mostrarRutas(rutasData);
    }
  }, 150);
});

// Función de inicialización para el sistema de vistas
window.initConsultaRutaVuelo = function() {
  cargarRutas();
};

// Inicializar automáticamente solo si no estamos en el sistema de vistas
if (!window.location.hash || window.location.hash === '#') {
  cargarRutas();
}
