// Variables globales
let aerolineasData = [];
let rutasData = [];
let currentPage = 1;
let cardsPerPage = 6; // máximo 6 rutas por página
let vueloSeleccionado = null;
let rutaSeleccionada = null;
let vuelosData = [];

// Inicializar
cargarAerolineas();
cargarVuelos();

function cargarVuelos() {
  fetch('json/vuelos.json')
    .then(res => res.json())
    .then(data => vuelosData = data)
    .catch(err => console.error("Error cargando vuelos:", err));
}

// Cargar aerolíneas desde la API del backend
function cargarAerolineas() {
  fetch('/api/aerolineas')
    .then(res => {
      if (!res.ok) throw new Error(`Error ${res.status}: ${res.statusText}`);
      return res.json();
    })
    .then(data => {
      aerolineasData = data;
      cargarFiltrosAerolineas();
      console.log('Aerolíneas cargadas:', aerolineasData);
    })
    .catch(err => {
      console.error("Error cargando aerolíneas:", err);
      // Fallback a JSON estático si la API falla
      cargarAerolineasFallback();
    });
}

// Fallback a JSON estático si la API falla
function cargarAerolineasFallback() {
  fetch('json/usuarios.json')
    .then(res => res.json())
    .then(data => {
      // Filtrar solo aerolíneas del JSON de usuarios
      aerolineasData = data.filter(user => user.tipo === 'aerolinea');
      cargarFiltrosAerolineas();
      console.log('Aerolíneas cargadas desde fallback:', aerolineasData);
    })
    .catch(err => console.error("Error cargando aerolíneas desde fallback:", err));
}

// Cargar rutas de una aerolínea específica
function cargarRutasAerolinea(nickname) {
  if (!nickname) {
    console.warn('No se proporcionó nickname de aerolínea');
    return;
  }

  fetch(`/api/rutas?aerolinea=${encodeURIComponent(nickname)}`)
    .then(res => {
      if (!res.ok) throw new Error(`Error ${res.status}: ${res.statusText}`);
      return res.json();
    })
    .then(data => {
      rutasData = data;
      cargarFiltrosRutas(rutasData);
      mostrarRutas(rutasData);
      console.log('Rutas cargadas para aerolínea', nickname, ':', rutasData);
    })
    .catch(err => {
      console.error("Error cargando rutas para aerolínea", nickname, ":", err);
      // Mostrar mensaje de error al usuario
      mostrarMensajeError("No se pudieron cargar las rutas para esta aerolínea.");
    });
}

// 🔠 Función para quitar tildes/acentos
function quitarTildes(texto) {
  return texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

// Mostrar rutas en el contenedor
function mostrarRutas(lista) {
  const contenedor = document.getElementById("lista-rutas");
  if (!contenedor) return;

  contenedor.innerHTML = "";

  if (lista.length === 0) {
    contenedor.innerHTML = "<div class='no-results' style='text-align: center; padding: 40px; color: #666;'><i class='fas fa-search'></i><p>No se encontraron rutas para esta aerolínea.</p></div>";
    return;
  }

  // Calcular inicio y fin de la página
  const start = (currentPage - 1) * cardsPerPage;
  const end = start + cardsPerPage;
  const paginaRutas = lista.slice(start, end);

  paginaRutas.forEach((r) => {
    const card = document.createElement("article");
    card.classList.add("ruta-card");

    // Construir HTML de manera segura
    const imagen = r.imagen || "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=400&q=80";
    const nombre = r.nombre || "Ruta sin nombre";
    const descripcion = r.descripcion || "Sin descripción";
    const origen = r.ciudadOrigen?.nombre || "Origen no especificado";
    const destino = r.ciudadDestino?.nombre || "Destino no especificado";
    const aeropuertoOrigen = r.ciudadOrigen?.aeropuerto || "";
    const aeropuertoDestino = r.ciudadDestino?.aeropuerto || "";
    const categorias = r.categorias ? r.categorias.join(", ") : "Sin categorías";
    const costoBase = r.costoBase || 0;
    const fechaAlta = r.fechaAlta || "No especificada";

    card.innerHTML = `
      <img src="${imagen}" alt="Imagen de ${destino}" style="width:100%;max-width:400px;">
      <h3>${nombre}</h3>
      <p>${descripcion}</p>
      <button class="btn-ver-mas">Ver más</button>
      <div class="detalle-ruta" style="display:none; margin-top:10px;">
        <p><strong>Origen:</strong> ${origen} ${aeropuertoOrigen ? `(${aeropuertoOrigen})` : ''}</p>
        <p><strong>Destino:</strong> ${destino} ${aeropuertoDestino ? `(${aeropuertoDestino})` : ''}</p>
        <p><strong>Categorías:</strong> ${categorias}</p>
        <p><strong>Costo base:</strong> $${costoBase}</p>
        <p><strong>Fecha de alta:</strong> ${fechaAlta}</p>
      </div>
    `;

    const btn = card.querySelector(".btn-ver-mas");
    const detalle = card.querySelector(".detalle-ruta");

    if (btn && detalle) {
      btn.addEventListener("click", (e) => {
        e.stopPropagation();
        const visible = detalle.style.display === "block";
        detalle.style.display = visible ? "none" : "block";
        btn.textContent = visible ? "Ver más" : "Ver menos";
      });
    }

    card.addEventListener("click", () => {
      document.querySelectorAll(".ruta-card").forEach(c => c.classList.remove("seleccionada"));
      card.classList.add("seleccionada");
      rutaSeleccionada = r;
      mostrarVuelosDeRuta(r.nombre);
    });

    contenedor.appendChild(card);
  });

  renderizarControles(lista.length);
  
  // Verificar si la ruta seleccionada sigue estando en la lista
  if (!lista.some(r => rutaSeleccionada && r.nombre === rutaSeleccionada.nombre)) {
    rutaSeleccionada = null;
    const listaVuelos = document.getElementById("lista-vuelos");
    if (listaVuelos) {
      listaVuelos.innerHTML = '<div class="vuelos-placeholder"><i class="fas fa-route"></i><p>Selecciona una ruta para ver sus vuelos disponibles</p></div>';
    }
  }
}
// Cargar filtros de aerolíneas
function cargarFiltrosAerolineas() {
  const selectAerolinea = document.getElementById("select-aerolinea");
  if (!selectAerolinea) return;

  selectAerolinea.innerHTML = '<option value="">Seleccione una aerolínea</option>';

  aerolineasData.forEach(aerolinea => {
    const option = document.createElement("option");
    option.value = aerolinea.nickname || aerolinea.id;
    option.textContent = aerolinea.nombre || aerolinea.nickname;
    option.dataset.nickname = aerolinea.nickname || aerolinea.id;
    selectAerolinea.appendChild(option);
  });

  // Evento para cargar rutas cuando se selecciona una aerolínea
  selectAerolinea.addEventListener("change", (e) => {
    const nickname = e.target.value;
    const selectCategoria = document.getElementById("select-categoria");
    const buscador = document.getElementById("buscador-nombre");
    
    if (nickname) {
      // Habilitar filtros secundarios
      if (selectCategoria) selectCategoria.disabled = false;
      if (buscador) buscador.disabled = false;
      
      cargarRutasAerolinea(nickname);
    } else {
      // Deshabilitar filtros secundarios y limpiar rutas
      if (selectCategoria) {
        selectCategoria.disabled = true;
        selectCategoria.innerHTML = '<option value="">Todas las categorías</option>';
      }
      if (buscador) {
        buscador.disabled = true;
        buscador.value = "";
      }
      
      rutasData = [];
      mostrarRutas([]);
    }
  });
}

// Cargar filtros de rutas (categorías y búsqueda)
function cargarFiltrosRutas(data) {
  const selectCategoria = document.getElementById("select-categoria");
  const buscador = document.getElementById("buscador-nombre");
  
  if (!selectCategoria) return;

  // Extraer categorías únicas de las rutas
  const categorias = [...new Set(data.flatMap(r => r.categorias || []).filter(Boolean))].sort();

  selectCategoria.innerHTML = '<option value="">Todas las categorías</option>';

  categorias.forEach(cat => {
    const option = document.createElement("option");
    option.value = cat;
    option.textContent = cat;
    selectCategoria.appendChild(option);
  });

  // Habilitar filtros
  selectCategoria.disabled = false;
  if (buscador) {
    buscador.disabled = false;
  }

  // Eventos de filtrado (remover listeners anteriores para evitar duplicados)
  selectCategoria.removeEventListener("change", filtrar);
  selectCategoria.addEventListener("change", filtrar);
  
  if (buscador) {
    buscador.removeEventListener("input", filtrar);
    buscador.addEventListener("input", filtrar);
  }
}

// Función para mostrar mensajes de error
function mostrarMensajeError(mensaje) {
  const contenedor = document.getElementById("lista-rutas");
  if (contenedor) {
    contenedor.innerHTML = `<div class="error-message" style="text-align: center; color: #c00; padding: 20px;">
      <i class="fas fa-exclamation-triangle"></i>
      <p>${mensaje}</p>
    </div>`;
  }
}

function filtrar() {
  const categoria = document.getElementById("select-categoria")?.value || "";
  const texto = quitarTildes(
    (document.getElementById("buscador-nombre")?.value || "").toLowerCase()
  );

  const filtradas = rutasData.filter((r) => {
    const nombreNormalizado = quitarTildes((r.nombre || "").toLowerCase());
    const origenNormalizado = quitarTildes((r.ciudadOrigen?.nombre || "").toLowerCase());
    const destinoNormalizado = quitarTildes((r.ciudadDestino?.nombre || "").toLowerCase());

    return (
      (r.estado === "CONFIRMADA" || r.estado === "Confirmada") &&
      (categoria === "" || (r.categorias && r.categorias.includes(categoria))) &&
      (texto === "" ||
        nombreNormalizado.includes(texto) ||
        origenNormalizado.includes(texto) ||
        destinoNormalizado.includes(texto))
    );
  });

  // 🔄 Si la ruta seleccionada ya no está en las filtradas, deseleccionarla
  if (rutaSeleccionada && !filtradas.some(r => r.nombre === rutaSeleccionada.nombre)) {
    rutaSeleccionada = null;
    vueloSeleccionado = null;
    const listaVuelos = document.getElementById("lista-vuelos");
    if (listaVuelos) {
      listaVuelos.innerHTML = '<div class="vuelos-placeholder"><i class="fas fa-route"></i><p>Selecciona una ruta para ver sus vuelos disponibles</p></div>';
    }
  }

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
  // Mantener siempre 6 rutas por página como máximo
  cardsPerPage = 6;
}

function mostrarRutas(lista) {
  calcularCardsPorPagina(); // recalcular antes de mostrar

  const contenedor = document.getElementById("lista-rutas");
  const statsContainer = document.getElementById("rutas-stats");
  
  if (statsContainer) {
    statsContainer.innerHTML = `<span class="total-rutas">${lista.length} ${lista.length === 1 ? 'ruta encontrada' : 'rutas encontradas'}</span>`;
  }
  
  contenedor.innerHTML = "";

  if (lista.length === 0) {
    contenedor.innerHTML = "<p>No se encontraron rutas.</p>";
    rutaSeleccionada = null; // 🔄 deseleccionar si no hay rutas
    document.getElementById("lista-vuelos").innerHTML = '<div class="vuelos-placeholder"><i class="fas fa-route"></i><p>Selecciona una ruta para ver sus vuelos disponibles</p></div>';
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
    document.getElementById("lista-vuelos").innerHTML = '<div class="vuelos-placeholder"><i class="fas fa-route"></i><p>Selecciona una ruta para ver sus vuelos disponibles</p></div>';
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
  cargarAerolineas();
};

// Inicializar automáticamente solo si no estamos en el sistema de vistas
if (!window.location.hash || window.location.hash === '#') {
  cargarAerolineas();
}
