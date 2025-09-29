// script/modificarUsuario.js
// --- NUEVA LÓGICA: Buscar, filtrar y seleccionar usuario para modificar ---
const usuariosURL = 'json/usuarios.json';
let usuariosData = [];
let usuarioActual = null;

// Quitar tildes para búsqueda
function quitarTildes(texto) {
  return texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

// Cargar usuarios y mostrar lista
function cargarUsuariosModificar() {
  fetch(usuariosURL)
    .then(res => res.json())
    .then(data => {
      usuariosData = data;
      mostrarUsuariosModificar(usuariosData);
      cargarFiltrosModificar();
    })
    .catch(err => {
      document.getElementById('modificar-lista-usuarios').innerHTML = '<p>Error cargando usuarios.</p>';
    });
}

// Mostrar lista de usuarios para elegir
function mostrarUsuariosModificar(lista) {
  const contenedor = document.getElementById("modificar-lista-usuarios");
  contenedor.innerHTML = "";
  document.getElementById('modificar-form-section').style.display = 'none';

  if (!lista || lista.length === 0) {
    contenedor.innerHTML = "<p>No se encontraron usuarios.</p>";
    return;
  }

  lista.forEach(usuario => {
    const card = document.createElement("div");
    card.classList.add("usuario-card");
    card.onclick = () => seleccionarUsuarioParaModificar(usuario);
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
  contenedor.style.display = 'grid';
}

// Al seleccionar usuario, mostrar formulario de edición
function seleccionarUsuarioParaModificar(usuario) {
  usuarioActual = usuario;
  document.getElementById('modificar-lista-usuarios').style.display = 'none';
  document.getElementById('modificar-form-section').style.display = 'block';
  mostrarDatosUsuario(usuario);
}

// Mostrar datos en el formulario
function mostrarDatosUsuario(usuario) {
  document.getElementById('modificar-usuario-imagen').src = usuario.imagen;
  document.getElementById('modificar-nickname').textContent = usuario.nickname;
  document.getElementById('modificar-email').textContent = usuario.email;
  document.getElementById('modificar-nombre').value = usuario.nombre;
  document.getElementById('modificar-sitio').value = usuario.sitioWeb || '';
  document.getElementById('modificar-descripcion').value = usuario.descripcion || '';
  // Reset input file
  document.getElementById('modificar-imagen').value = '';
}

// Filtros y búsqueda
function cargarFiltrosModificar() {
  const selectTipo = document.getElementById("modificar-select-tipo");
  const buscadorNombre = document.getElementById("modificar-buscador-nombre");
  selectTipo.addEventListener("change", filtrarModificar);
  buscadorNombre.addEventListener("input", filtrarModificar);
}

function filtrarModificar() {
  const tipo = document.getElementById("modificar-select-tipo").value;
  const texto = quitarTildes(document.getElementById("modificar-buscador-nombre").value.toLowerCase());
  const filtrados = usuariosData.filter(usuario => {
    const coincideTipo = !tipo || usuario.tipo === tipo;
    const coincideTexto = !texto || quitarTildes(usuario.nombre.toLowerCase()).includes(texto) || quitarTildes(usuario.nickname.toLowerCase()).includes(texto);
    return coincideTipo && coincideTexto;
  });
  mostrarUsuariosModificar(filtrados);
}

// Previsualizar imagen
function registrarInputImagen() {
  const inputImagen = document.getElementById('modificar-imagen');
  if (inputImagen) {
    inputImagen.addEventListener('change', function(e) {
      const file = e.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = function(ev) {
          document.getElementById('modificar-usuario-imagen').src = ev.target.result;
        };
        reader.readAsDataURL(file);
      }
    });
  }
}

// Guardar cambios
function registrarFormModificar() {
  const form = document.getElementById('form-modificar-usuario');
  if (form) {
    form.addEventListener('submit', function(e) {
      e.preventDefault();
      if (!usuarioActual) return mostrarMensaje('No se encontró el usuario', 'error');
      // Obtener datos
      const nombre = document.getElementById('modificar-nombre').value.trim();
      const sitioWeb = document.getElementById('modificar-sitio').value.trim();
      const descripcion = document.getElementById('modificar-descripcion').value.trim();
      let imagen = usuarioActual.imagen;
      const file = document.getElementById('modificar-imagen').files[0];
      if (file) {
        imagen = document.getElementById('modificar-usuario-imagen').src;
      }
      usuarioActual = {
        ...usuarioActual,
        nombre,
        sitioWeb,
        descripcion,
        imagen
      };
      mostrarMensaje('¡Perfil actualizado con éxito!', 'exito');
      // Aquí se integraría con backend para guardar cambios
    });
  }
}

// Cancelar edición
function registrarBtnCancelar() {
  const btnCancelar = document.getElementById('cancelar-modificacion');
  if (btnCancelar) {
    btnCancelar.addEventListener('click', function() {
      document.getElementById('modificar-form-section').style.display = 'none';
      document.getElementById('modificar-lista-usuarios').style.display = 'grid';
      mostrarUsuariosModificar(usuariosData);
      mostrarMensaje('', '');
    });
  }
}

// Mostrar mensaje
function mostrarMensaje(msg, tipo) {
  const div = document.getElementById('mensaje-modificar-usuario');
  if (!div) return;
  div.textContent = msg;
  div.className = 'mensaje-usuario' + (tipo ? ' ' + tipo : '');
  div.style.display = msg ? 'block' : 'none';
  if (tipo === 'exito') {
    setTimeout(() => { div.style.display = 'none'; }, 2000);
  }
}

// Inicializar
window.initModificarUsuario = function() {
  cargarUsuariosModificar();
  registrarInputImagen();
  registrarFormModificar();
  registrarBtnCancelar();
}

document.addEventListener('DOMContentLoaded', function() {
  if (document.getElementById('filtro-modificar-usuarios')) {
    window.initModificarUsuario();
  }
});
