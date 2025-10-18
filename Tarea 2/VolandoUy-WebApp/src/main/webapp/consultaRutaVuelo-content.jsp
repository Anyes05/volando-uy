<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="contenido-principal">
  <section class="search-box">
    <div class="search-header">
      <h2>Consulta de Ruta de Vuelo</h2>
      <p class="descripcion-consulta">Explora las rutas de vuelo disponibles y encuentra la conexión perfecta para tu próximo viaje. Todas las rutas mostradas están confirmadas y operativas.</p>
    </div>
    
    <form id="filtro-rutas" class="filtros-container">
      <div class="filtro-group">
        <label for="select-aerolinea">Aerolínea:</label>
        <select id="select-aerolinea">
          <option value="">Seleccione una aerolínea</option>
        </select>
        <small class="filtro-hint">Primero seleccione una aerolínea para ver sus rutas</small>
      </div>

      <div class="filtro-group">
        <label for="select-categoria">Categoría:</label>
        <select id="select-categoria" disabled>
          <option value="">Todas las categorías</option>
        </select>
        <small class="filtro-hint">Se habilitará después de seleccionar una aerolínea</small>
      </div>

      <div class="filtro-group">
        <label for="buscador-nombre">Buscar por nombre:</label>
        <input type="text" id="buscador-nombre" placeholder="Ej: Montevideo - Madrid" disabled>
        <small class="filtro-hint">Se habilitará después de seleccionar una aerolínea</small>
      </div>
    </form>
  </section>
</div>

<div class="contenedor-principal">
  <!-- Contenedor de rutas + paginación -->
  <div class="contenedor-rutas">
    <div class="rutas-header">
      <h3>Rutas Disponibles</h3>
      <div class="rutas-stats" id="rutas-stats">
        <span class="total-rutas">0 rutas encontradas</span>
      </div>
    </div>
    <div id="lista-rutas" class="rutas-grid"></div>
    <div id="paginacion" class="paginacion-container"></div>
  </div>

  <!-- Aside de vuelos -->
  <aside id="aside-vuelos" class="aside-vuelos">
    <div class="aside-header">
      <h3><i class="fas fa-plane"></i> Vuelos</h3>
      <p class="aside-subtitle">Selecciona una ruta para ver sus vuelos</p>
    </div>
    <div id="lista-vuelos" class="vuelos-container">
      <div class="vuelos-placeholder">
        <i class="fas fa-route"></i>
        <p>Selecciona una ruta para ver sus vuelos disponibles</p>
      </div>
    </div>
    <button id="btn-agregar-vuelo" class="btn-consultar-vuelo">
      <i class="fas fa-search"></i> Consultar vuelo
    </button>
    <p id="mensaje-vuelo" class="mensaje-vuelo"></p>
  </aside>
</div>

<script>
// Variables globales
let aerolineasData = [];
let rutasData = [];
let currentPage = 1;
let cardsPerPage = 6;
let vueloSeleccionado = null;
let rutaSeleccionada = null;
let vuelosData = [];

// Inicializar
document.addEventListener('DOMContentLoaded', function() {
    console.log('Inicializando CONSULTARUTAVUELO...');
    cargarAerolineas();
    cargarVuelos();
});

function cargarVuelos() {
    fetch('<%= request.getContextPath() %>/json/vuelos.json')
        .then(res => res.json())
        .then(data => {
            vuelosData = data;
            console.log('Vuelos cargados:', vuelosData.length);
        })
        .catch(err => {
            console.error("Error cargando vuelos:", err);
            vuelosData = [];
        });
}

// Cargar aerolíneas desde la API del backend
function cargarAerolineas() {
    console.log('Cargando aerolíneas desde API...');
    fetch('<%= request.getContextPath() %>/api/aerolineas')
        .then(res => {
            console.log('Respuesta API aerolíneas:', res.status);
            if (!res.ok) throw new Error(`Error ${res.status}: ${res.statusText}`);
            return res.json();
        })
        .then(data => {
            console.log('Aerolíneas recibidas:', data);
            aerolineasData = data;
            cargarFiltrosAerolineas();
        })
        .catch(err => {
            console.error("Error cargando aerolíneas desde API:", err);
            // Fallback a JSON estático
            cargarAerolineasFallback();
        });
}

// Fallback a JSON estático si la API falla
function cargarAerolineasFallback() {
    console.log('Cargando aerolíneas desde JSON estático...');
    fetch('<%= request.getContextPath() %>/json/usuarios.json')
        .then(res => res.json())
        .then(data => {
            // Filtrar solo aerolíneas del JSON de usuarios
            aerolineasData = data.filter(user => user.tipo === 'aerolinea');
            cargarFiltrosAerolineas();
            console.log('Aerolíneas cargadas desde fallback:', aerolineasData);
        })
        .catch(err => {
            console.error("Error cargando aerolíneas desde fallback:", err);
            mostrarMensajeError("No se pudieron cargar las aerolíneas. Verifique que el sistema esté inicializado.");
        });
}

// Cargar rutas de una aerolínea específica
function cargarRutasAerolinea(nickname) {
    if (!nickname) {
        console.warn('No se proporcionó nickname de aerolínea');
        return;
    }

    console.log('Cargando rutas para aerolínea:', nickname);
    fetch(`<%= request.getContextPath() %>/api/rutas?aerolinea=${encodeURIComponent(nickname)}`)
        .then(res => {
            console.log('Respuesta API rutas:', res.status);
            if (!res.ok) throw new Error(`Error ${res.status}: ${res.statusText}`);
            return res.json();
        })
        .then(data => {
            console.log('Rutas recibidas:', data);
            rutasData = data;
            cargarFiltrosRutas(rutasData);
            mostrarRutas(rutasData);
        })
        .catch(err => {
            console.error("Error cargando rutas para aerolínea", nickname, ":", err);
            mostrarMensajeError("No se pudieron cargar las rutas para esta aerolínea. Verifique que el sistema esté inicializado.");
        });
}

// Cargar filtros de aerolíneas
function cargarFiltrosAerolineas() {
    const selectAerolinea = document.getElementById("select-aerolinea");
    if (!selectAerolinea) return;

    selectAerolinea.innerHTML = '<option value="">Seleccione una aerolínea</option>';

    if (aerolineasData.length === 0) {
        const option = document.createElement("option");
        option.value = "";
        option.textContent = "No hay aerolíneas disponibles";
        option.disabled = true;
        selectAerolinea.appendChild(option);
        mostrarMensajeError("No hay aerolíneas disponibles. Por favor, inicialice el sistema primero.");
        return;
    }

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

    // Eventos de filtrado
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
        contenedor.innerHTML = `
            <div class="error-message" style="text-align: center; color: #c00; padding: 20px;">
                <i class="fas fa-exclamation-triangle"></i>
                <p><strong>Error:</strong> ${mensaje}</p>
                <p><a href="<%= request.getContextPath() %>/diagnostico.jsp" target="_blank">Ir a Diagnóstico del Sistema</a></p>
            </div>
        `;
    }
}

function quitarTildes(texto) {
    return texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
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

    // Si la ruta seleccionada ya no está en las filtradas, deseleccionarla
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

// Mostrar rutas en el contenedor
function mostrarRutas(lista) {
    const contenedor = document.getElementById("lista-rutas");
    if (!contenedor) return;

    contenedor.innerHTML = "";

    if (lista.length === 0) {
        contenedor.innerHTML = `
            <div class='no-results' style='text-align: center; padding: 40px; color: #666;'>
                <i class='fas fa-search'></i>
                <p>No se encontraron rutas para esta aerolínea.</p>
                <p><a href="<%= request.getContextPath() %>/diagnostico.jsp" target="_blank">Verificar estado del sistema</a></p>
            </div>
        `;
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

function mostrarVuelosDeRuta(nombreRuta) {
    const contenedor = document.getElementById("lista-vuelos");
    contenedor.innerHTML = "";

    const vuelosRuta = vuelosData.filter(
        v => v.ruta === nombreRuta && v.estado === "Confirmada"
    );

    if (vuelosRuta.length === 0) {
        contenedor.innerHTML = "<p>No hay vuelos confirmados para esta ruta.</p>";
        vueloSeleccionado = null;
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

    // Si el vuelo seleccionado ya no está en la lista actual, lo deseleccionamos
    if (!vuelosRuta.some(v => vueloSeleccionado && v.nombre === vueloSeleccionado.nombre)) {
        vueloSeleccionado = null;
    }
}

// Botón consultar vuelo
document.getElementById("btn-agregar-vuelo").textContent = "Consultar vuelo";

document.getElementById("btn-agregar-vuelo").addEventListener("click", () => {
    const mensaje = document.getElementById("mensaje-vuelo");

    if (!vueloSeleccionado) {
        mensaje.textContent = "⚠️ Por favor, selecciona un vuelo primero.";
        mensaje.style.display = "block";
        mensaje.style.opacity = "1";

        setTimeout(() => {
            mensaje.style.opacity = "0";
            setTimeout(() => {
                mensaje.style.display = "none";
            }, 300);
        }, 3000);

        return;
    }

    // Redirigir a la otra página con el vuelo seleccionado
    window.location.href = `consulta-vuelo.html?vuelo=${encodeURIComponent(vueloSeleccionado.nombre)}`;
});

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

// Recalcular al redimensionar ventana
let resizeTimeout;
window.addEventListener("resize", () => {
    clearTimeout(resizeTimeout);
    resizeTimeout = setTimeout(() => {
        if (rutasData && rutasData.length > 0) {
            mostrarRutas(rutasData);
        }
    }, 150);
  });
</script>