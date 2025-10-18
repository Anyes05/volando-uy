const usuariosURL = '${pageContext.request.contextPath}/api/usuarios';
let usuariosData = [];
let usuarioActual = null;

//  Función para quitar tildes/acentos
function quitarTildes(texto) {
  return texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

// Cargar usuarios desde JSON
function cargarUsuarios() {
  fetch(usuariosURL)
    .then(res => res.json())
    .then(data => {
      usuariosData = data;
      mostrarUsuarios(usuariosData);
      cargarFiltros(usuariosData);
    })
    .catch(err => console.error('Error cargando usuarios:', err));
}

// Mostrar usuarios en el contenedor
function mostrarUsuarios(lista) {
  const contenedor = document.getElementById("lista-usuarios");
  contenedor.innerHTML = "";

  if (lista.length === 0) {
    contenedor.innerHTML = "<p>No se encontraron usuarios.</p>";
    return;
  }

  lista.forEach(usuario => {
    const card = document.createElement("div");
    card.classList.add("usuario-card");
    card.onclick = () => mostrarDetalleUsuario(usuario);

    card.innerHTML = `
      <div class="usuario-card-header">
        <img src="${usuario.imagen}" alt="Imagen de ${usuario.nombre}">
        <div class="usuario-card-info">
          <h3>${usuario.nombre}</h3>
          <p>${usuario.nickname} / ${usuario.email}</p>
        </div>
      </div>
      <div class="usuario-card-descripcion">
        ${usuario.descripcion}
      </div>
      <span class="usuario-tipo ${usuario.tipo}">${usuario.tipo}</span>
    `;

    contenedor.appendChild(card);
  });
}

// Mostrar detalle de usuario
function mostrarDetalleUsuario(usuario) {
  usuarioActual = usuario;
  const detalle = document.querySelector('.detalle-usuario');
  
  // Ocultar lista y mostrar detalle
  document.getElementById('lista-usuarios').style.display = 'none';
  detalle.style.display = 'block';
  
  // Llenar información del header
  document.getElementById('usuario-imagen').src = usuario.imagen;
  document.getElementById('usuario-nombre').textContent = usuario.nombre;
  document.getElementById('usuario-credential').textContent = `${usuario.nickname} / ${usuario.email}`;
  
  // Llenar información del perfil
  document.getElementById('perfil-nickname').textContent = usuario.nickname;
  document.getElementById('perfil-nombre').textContent = usuario.nombre;
  document.getElementById('perfil-email').textContent = usuario.email;
  document.getElementById('perfil-sitio').textContent = usuario.sitioWeb || 'No especificado';
  document.getElementById('perfil-descripcion').textContent = usuario.descripcion;
  
  // Configurar tabs
  configurarTabs(usuario);
  
  // Scroll al detalle
  detalle.scrollIntoView({ behavior: 'smooth' });
}

// Configurar tabs según el tipo de usuario
function configurarTabs(usuario) {
  const tabRutas = document.querySelector('[data-tab="rutas"]');
  const tabPerfil = document.querySelector('[data-tab="perfil"]');
  
  // Mostrar tab de rutas solo para aerolíneas
  if (usuario.tipo === 'aerolinea') {
    tabRutas.style.display = 'block';
    tabRutas.textContent = 'Rutas de Vuelo';
  } else if (usuario.tipo === 'cliente') {
    tabRutas.style.display = 'block';
    tabRutas.textContent = 'Reservas y Paquetes';
  } else {
    tabRutas.style.display = 'none';
  }
  
  // Activar tab de perfil por defecto
  activarTab('perfil');
}

// Activar tab específico
function activarTab(tabName) {
  // Remover clase active de todos los tabs
  document.querySelectorAll('.tab-btn').forEach(btn => {
    btn.classList.remove('active');
  });
  
  // Ocultar todos los contenidos
  document.querySelectorAll('.tab-content').forEach(content => {
    content.style.display = 'none';
  });
  
  // Activar tab seleccionado
  document.querySelector(`[data-tab="${tabName}"]`).classList.add('active');
  document.getElementById(`tab-${tabName}`).style.display = 'block';
  
  // Cargar contenido específico del tab
  if (tabName === 'rutas' && usuarioActual) {
    cargarContenidoRutas(usuarioActual);
  }
}

// Cargar contenido de rutas/reservas según el tipo de usuario
function cargarContenidoRutas(usuario) {
  const rutasContent = document.getElementById('rutas-content');
  
  if (usuario.tipo === 'aerolinea') {
    mostrarRutasAerolinea(usuario, rutasContent);
  } else if (usuario.tipo === 'cliente') {
    mostrarReservasCliente(usuario, rutasContent);
  }
}

// Mostrar rutas de aerolínea
function mostrarRutasAerolinea(usuario, contenedor) {
  const rutas = usuario.rutasVuelo || [];
  
  if (rutas.length === 0) {
    contenedor.innerHTML = '<p>No hay rutas de vuelo registradas.</p>';
    return;
  }
  
  contenedor.innerHTML = `
    <div class="rutas-grid">
      ${rutas.map(ruta => `
        <div class="ruta-item" onclick="verDetalleRuta(${ruta.id})">
          <h4>${ruta.nombre}</h4>
          <p><strong>Estado:</strong> <span class="estado-badge ${ruta.estado}">${ruta.estado}</span></p>
          <p><strong>Descripción:</strong> ${ruta.descripcion}</p>
        </div>
      `).join('')}
    </div>
  `;
}

// Mostrar reservas y paquetes de cliente
function mostrarReservasCliente(usuario, contenedor) {
  const reservas = usuario.reservas || [];
  const paquetes = usuario.paquetes || [];
  
  let html = '';
  
  if (reservas.length > 0) {
    html += `
      <h3>Reservas de Vuelo</h3>
      <div class="rutas-grid">
        ${reservas.map(reserva => `
          <div class="ruta-item" onclick="verDetalleReserva(${reserva.id})">
            <h4>${reserva.vuelo}</h4>
            <p><strong>Fecha:</strong> ${reserva.fecha}</p>
            <p><strong>Estado:</strong> <span class="estado-badge ${reserva.estado}">${reserva.estado}</span></p>
          </div>
        `).join('')}
      </div>
    `;
  }
  
  if (paquetes.length > 0) {
    html += `
      <h3>Paquetes Comprados</h3>
      <div class="rutas-grid">
        ${paquetes.map(paquete => `
          <div class="ruta-item" onclick="verDetallePaquete(${paquete.id})">
            <h4>${paquete.nombre}</h4>
            <p><strong>Destinos:</strong> ${paquete.destinos.join(', ')}</p>
            <p><strong>Fecha:</strong> ${paquete.fecha}</p>
            <p><strong>Precio:</strong> $${paquete.precio}</p>
          </div>
        `).join('')}
      </div>
    `;
  }
  
  if (html === '') {
    html = '<p>No hay reservas ni paquetes registrados.</p>';
  }
  
  contenedor.innerHTML = html;
}

// Cargar filtros
function cargarFiltros(data) {
  const selectTipo = document.getElementById("select-tipo");
  const buscadorNombre = document.getElementById("buscador-nombre");

  // Eventos automáticos
  selectTipo.addEventListener("change", function(e) {
    e.preventDefault();
    filtrar();
  });
  buscadorNombre.addEventListener("input", filtrar);
}

// Filtrar en tiempo real
function filtrar() {
  const tipo = document.getElementById("select-tipo").value;
  const texto = quitarTildes(document.getElementById("buscador-nombre").value.toLowerCase());

  const filtrados = usuariosData.filter(usuario => {
    const nombreNormalizado = quitarTildes(usuario.nombre.toLowerCase());
    const nicknameNormalizado = quitarTildes(usuario.nickname.toLowerCase());
    const emailNormalizado = quitarTildes(usuario.email.toLowerCase());

    return (
      (tipo === "" || usuario.tipo === tipo) &&
      (texto === "" ||
        nombreNormalizado.includes(texto) ||
        nicknameNormalizado.includes(texto) ||
        emailNormalizado.includes(texto))
    );
  });

  mostrarUsuarios(filtrados);
}

// Función para volver a la lista
function volverALista() {
  document.getElementById('lista-usuarios').style.display = 'grid';
  document.querySelector('.detalle-usuario').style.display = 'none';
  usuarioActual = null;
  
  // Mantener los filtros activos
  const tipo = document.getElementById("select-tipo").value;
  const texto = document.getElementById("buscador-nombre").value;
  
  // Aplicar filtros actuales
  if (tipo || texto) {
    filtrar();
  }
  
  // Re-registrar event listeners para la navegación
  if (typeof registrarEventListeners === 'function') {
    registrarEventListeners();
  }
}

// Funciones para ver detalles específicos (placeholder)
function verDetalleRuta(id) {
  console.log('Ver detalle de ruta:', id);
  // Aquí se implementaría la navegación a consultaRutaVuelo
}

function verDetalleReserva(id) {
  console.log('Ver detalle de reserva:', id);
  // Aquí se implementaría la navegación a consultaReserva
}

function verDetallePaquete(id) {
  console.log('Ver detalle de paquete:', id);
  // Aquí se implementaría la navegación a consultaPaquete
}

// Event listeners para tabs
document.addEventListener('DOMContentLoaded', function() {
  // Event listeners para tabs
  document.addEventListener('click', function(e) {
    if (e.target.classList.contains('tab-btn')) {
      const tabName = e.target.getAttribute('data-tab');
      activarTab(tabName);
    }
  });
  
  // El botón de cerrar ya está en el HTML, no necesitamos crear uno dinámicamente
});

// Función para ver detalle de usuario (llamada desde JSP)
function verDetalleUsuario(usuarioId) {
  // Buscar usuario por ID en la lista actual
  const usuario = usuariosData.find(u => u.id === usuarioId);
  if (usuario) {
    mostrarDetalleUsuario(usuario);
  } else {
    // Si no está en la lista actual, cargar desde la API
    fetch(`${usuariosURL}/${usuarioId}`)
      .then(response => response.json())
      .then(usuario => {
        if (usuario.error) {
          alert('Error al cargar usuario: ' + usuario.error);
        } else {
          mostrarDetalleUsuario(usuario);
        }
      })
      .catch(error => {
        console.error('Error:', error);
        alert('Error al cargar el detalle del usuario');
      });
  }
}

// Función de inicialización para el sistema de vistas
window.initConsultaUsuario = function() {
  cargarUsuarios();
};

// Inicializar automáticamente solo si no hay usuarios ya cargados desde el servidor
document.addEventListener('DOMContentLoaded', function() {
  // Verificar si ya hay usuarios cargados desde el servidor (JSP)
  const usuariosGrid = document.querySelector('.usuarios-grid');
  if (!usuariosGrid || usuariosGrid.children.length === 0) {
    cargarUsuarios();
  } else {
    // Ya hay usuarios cargados desde el servidor, configurar filtros
    usuariosData = Array.from(document.querySelectorAll('.usuario-card')).map(card => ({
      id: parseInt(card.dataset.usuarioId),
      nombre: card.querySelector('h4').textContent,
      nickname: card.querySelector('.usuario-nickname').textContent.replace('@', ''),
      email: card.querySelector('.usuario-email').textContent,
      tipo: card.dataset.tipo.toLowerCase(),
      imagen: 'img/logoAvionSolo.png'
    }));
    cargarFiltros(usuariosData);
  }
});
