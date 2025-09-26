const rutasURL = 'json/rutasVuelo.json';
let rutasData = [];

// 🔠 Función para quitar tildes/acentos
function quitarTildes(texto) {
  return texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

// Cargar rutas desde JSON
function cargarRutas() {
  fetch(rutasURL)
    .then(res => res.json())
    .then(data => {
      rutasData = data;
      mostrarRutas(rutasData);
      cargarFiltros(rutasData);
    })
    .catch(err => console.error('Error cargando rutas:', err));
}

// Mostrar rutas en el contenedor
function mostrarRutas(lista) {
  const contenedor = document.getElementById("lista-rutas");
  contenedor.innerHTML = "";

  if (lista.length === 0) {
    contenedor.innerHTML = "<p>No se encontraron rutas.</p>";
    return;
  }

  lista.forEach(r => {
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
        <p><strong>Categorías:</strong> ${r.categorias.join(', ')}</p>
        <p><strong>Costo base:</strong> $${r.costoBase}</p>
        <p><strong>Fecha de alta:</strong> ${r.fechaAlta}</p>
      </div>
    `;

    // Evento para el botón "Ver más"
    const btn = card.querySelector(".btn-ver-mas");
    const detalle = card.querySelector(".detalle-ruta");

    btn.addEventListener("click", () => {
      const visible = detalle.style.display === "block";
      detalle.style.display = visible ? "none" : "block";
      btn.textContent = visible ? "Ver más" : "Ver menos";
    });

    contenedor.appendChild(card);
  });
}

// Cargar aerolíneas y categorías únicas
function cargarFiltros(data) {
  const aerolineas = [...new Set(data.map(r => r.aerolinea.nombre))];
  const categorias = [...new Set(data.flatMap(r => r.categorias))];

  const selectAerolinea = document.getElementById("select-aerolinea");
  const selectCategoria = document.getElementById("select-categoria");

  selectAerolinea.innerHTML = '<option value="">Todas</option>';
  selectCategoria.innerHTML = '<option value="">Todas</option>';

  aerolineas.forEach(a => {
    const option = document.createElement("option");
    option.value = a;
    option.textContent = a;
    selectAerolinea.appendChild(option);
  });

  categorias.forEach(c => {
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
  const texto = quitarTildes(document.getElementById("buscador-nombre").value.toLowerCase());

  const filtradas = rutasData.filter(r => {
    const nombreNormalizado = quitarTildes(r.nombre.toLowerCase());
    const origenNormalizado = quitarTildes(r.ciudadOrigen.nombre.toLowerCase());
    const destinoNormalizado = quitarTildes(r.ciudadDestino.nombre.toLowerCase());

    return (
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

// Inicializar
cargarRutas();