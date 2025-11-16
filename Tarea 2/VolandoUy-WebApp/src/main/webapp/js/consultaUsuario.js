const usuariosURL = '/VolandoUy-WebApp/api/usuarios';
let usuariosData = [];
let usuarioActual = null;

// Función auxiliar para determinar el tipo de usuario
function determinarTipoUsuario(usuario) {
  console.log('Determinando tipo de usuario para:', usuario.nickname, usuario);
  
  // El backend ahora devuelve el tipo directamente
  if (usuario.tipo) {
    console.log('Tipo encontrado directamente:', usuario.tipo);
    return usuario.tipo;
  }
  
  // Fallback: verificar propiedades específicas para determinar el tipo
  if (usuario.nacionalidad || usuario.apellido || usuario.numeroDocumento || usuario.tipoDocumento) {
    console.log('Detectado como cliente por propiedades');
    return 'cliente';
  }
  
  if (usuario.descripcion || usuario.linkSitioWeb || usuario.sitioWeb) {
    console.log('Detectado como aerolínea por propiedades');
    return 'aerolinea';
  }
  
  // Por defecto
  console.log('No se pudo determinar el tipo de usuario:', usuario);
  return 'usuario';
}

// Función para quitar tildes/acentos
function quitarTildes(texto) {
  return texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

// Cargar usuarios desde la API REST 
async function cargarUsuarios() {
  try {
    console.log('Cargando usuarios desde backend...');
    
    // Mostrar loading
    mostrarLoading(true);
    
    const response = await fetch(usuariosURL);
    
    if (!response.ok) {
      throw new Error(`Error HTTP: ${response.status}`);
    }
    
    const data = await response.json();
    usuariosData = data.usuarios || [];
    console.log('Usuarios cargados:', usuariosData);
    console.log('Primer usuario:', usuariosData[0]);
    if (usuariosData[0]) {
      console.log('Clase del primer usuario:', usuariosData[0].getClass ? usuariosData[0].getClass().simpleName : 'No tiene getClass');
    }
    
    mostrarUsuarios(usuariosData);
    actualizarContador(usuariosData.length);
    cargarFiltros(usuariosData);
  } catch (error) {
    console.error('Error cargando usuarios:', error);
    mostrarError('Error al cargar los usuarios. Por favor, intente nuevamente.');
  } finally {
    // Ocultar loading
    mostrarLoading(false);
  }
}

// Mostrar/ocultar loading
function mostrarLoading(mostrar) {
  const loadingMessage = document.getElementById("loading-message");
  const listaUsuarios = document.getElementById("lista-usuarios");
  
  if (loadingMessage) {
    loadingMessage.style.display = mostrar ? "block" : "none";
  }
  
  if (listaUsuarios) {
    listaUsuarios.style.display = mostrar ? "none" : "block";
  }
}

// Actualizar contador de usuarios
function actualizarContador(total) {
  const contador = document.getElementById("total-usuarios");
  if (contador) {
    contador.textContent = total;
  }
}

// Mostrar usuarios en el contenedor
// Obtener usuario logueado desde backend
async function obtenerUsuarioLogueado() {
  try {
    const resp = await fetch("/VolandoUy-WebApp/api/usuarios/perfil", { credentials: "include" });
    if (resp.ok) {
      return await resp.json(); // objeto con nickname, tipo, seguidos, etc.
    }
  } catch (e) {
    console.error("No se pudo obtener usuario logueado:", e);
  }
  return null;
}

// Mostrar usuarios en el contenedor
async function mostrarUsuarios(lista) {
  const contenedor = document.getElementById("lista-usuarios");
  if (!contenedor) return;

  contenedor.innerHTML = '';

  if (!lista || lista.length === 0) {
    contenedor.innerHTML = `<p>No se encontraron usuarios registrados.</p>`;
    return;
  }

  const usuarioLogueado = await obtenerUsuarioLogueado();

  lista.forEach(usuario => {
    const card = document.createElement("div");
    card.className = "usuario-card";

    let imagenSrc = usuario.foto ? `data:image/jpeg;base64,${usuario.foto}` : 'static/img/logoAvionSolo.png';
    let tipoUsuario = determinarTipoUsuario(usuario);
    let detalleInfo = tipoUsuario === 'cliente'
      ? (usuario.nacionalidad || 'Cliente')
      : (tipoUsuario === 'aerolinea' ? (usuario.descripcion || 'Aerolínea') : 'Usuario');

    const yaLoSigue = usuarioLogueado?.seguidos?.includes(usuario.nickname);

    card.innerHTML = `
      <div class="usuario-card-header">
        <img src="${imagenSrc}" alt="Imagen del usuario" class="usuario-avatar">
        <div class="usuario-card-info">
          <h4>${usuario.nombre}</h4>
          <p class="usuario-nickname">
            @${usuario.nickname}
            ${
              usuarioLogueado && usuarioLogueado.nickname !== usuario.nickname
                ? `<button class="follow-btn ${yaLoSigue ? 'dejar' : 'seguir'}"
                           data-nickname="${usuario.nickname}"
                           data-following="${yaLoSigue ? 'true' : 'false'}">
                     ${yaLoSigue ? 'Dejar de seguir' : 'Seguir'}
                   </button>`
                : ''
            }
          </p>
          <p class="usuario-email">${usuario.correo}</p>
        </div>
      </div>
      <div class="usuario-card-body">
        <p class="usuario-tipo ${tipoUsuario}">${tipoUsuario}</p>
        <p class="usuario-detalle">${detalleInfo}</p>
      </div>
      <div class="usuario-card-footer">
        <p class="click-hint">Haz clic para ver detalles</p>
      </div>
    `;

    // ✅ Click en la card → carga detalle completo desde API
    card.addEventListener("click", (e) => {
      if (e.target.closest('.follow-btn')) return; // ignora clicks en el botón
      cargarDetalleUsuarioDesdeAPI(usuario.nickname);
    });

    contenedor.appendChild(card);
  });
}



// Mostrar mensaje cuando no hay usuarios
function mostrarMensajeNoUsuarios(mensaje) {
  const contenedor = document.getElementById("lista-usuarios");
  if (contenedor) {
    contenedor.innerHTML = `
      <div class="no-usuarios">
        <i class="fas fa-users"></i>
        <h3>No hay usuarios registrados</h3>
        <p>${mensaje}</p>
      </div>
    `;
  }
}

// Mostrar error (igual que Consulta Paquete)
function mostrarError(mensaje) {
  const contenedor = document.getElementById("lista-usuarios");
  if (contenedor) {
    contenedor.innerHTML = `
      <div style="text-align: center; padding: 2rem; color: #eaf6fb;">
        <i class="fas fa-exclamation-triangle" style="font-size: 3rem; margin-bottom: 1rem; color: #dc3545;"></i>
        <p>${mensaje}</p>
        <button onclick="cargarUsuarios()" style="
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

// Mostrar detalle de usuario


async function mostrarDetalleUsuario(nickname) {
  try {
    const resp = await fetch(`/VolandoUy-WebApp/api/usuarios/${nickname}`, { credentials: "include" });
    if (!resp.ok) return;

    const usuario = await resp.json();
    const usuarioLogueado = await obtenerUsuarioLogueado();
    const yaLoSigue = usuarioLogueado?.seguidos?.includes(usuario.nickname);

    const detalle = document.getElementById("detalle-usuario");
    detalle.innerHTML = `
      <h2>
        ${usuario.nombre} (@${usuario.nickname})
        ${
          usuarioLogueado && usuarioLogueado.nickname !== usuario.nickname
            ? `<button class="follow-btn ${yaLoSigue ? 'dejar' : 'seguir'}"
                       data-nickname="${usuario.nickname}"
                       data-following="${yaLoSigue ? 'true' : 'false'}">
                 ${yaLoSigue ? 'Dejar de seguir' : 'Seguir'}
               </button>`
            : ''
        }
      </h2>
      <p>Email: ${usuario.correo}</p>
      <p>Tipo: ${usuario.tipo}</p>
      <p>Detalle: ${usuario.descripcion || usuario.nacionalidad || ''}</p>
    `;
  } catch (e) {
    console.error("No se pudo cargar detalle de usuario:", e);
  }
}

// Cargar detalle de usuario desde API
async function cargarDetalleUsuarioDesdeAPI(nickname) {
  console.log("Cargando detalle desde API para:", nickname);
  
  try {
    const response = await fetch(`${usuariosURL}/${nickname}`);
    console.log("Response status:", response.status);
    
    if (response.ok) {
      const usuario = await response.json();
      console.log("Usuario obtenido:", usuario);
      console.log("Reservas del usuario:", usuario.reservas);
      console.log("Paquetes del usuario:", usuario.paquetes);
      
      // Convertir datos de la API al formato esperado
      usuarioActual = {
        nickname: usuario.nickname,
        tipo: usuario.tipo || 'usuario',
        nombre: usuario.nombre,
        correo: usuario.correo,
        foto: usuario.foto,
        nacionalidad: usuario.nacionalidad,
        descripcion: usuario.descripcion,
        sitioWeb: usuario.sitioWeb,
        apellido: usuario.apellido,
        numeroDocumento: usuario.numeroDocumento,
        tipoDocumento: usuario.tipoDocumento,
        fechaNacimiento: usuario.fechaNacimiento,
        reservas: usuario.reservas || [], // Incluir reservas si están disponibles
        paquetes: usuario.paquetes || [], // Incluir paquetes si están disponibles
        seguidores: usuario.seguidores || [], // Incluir seguidores (características sociales)
        seguidos: usuario.seguidos || [] // Incluir seguidos (características sociales)
      };
      
      console.log("Usuario actual configurado:", usuarioActual);
      mostrarDetalleUsuarioCompleto(usuarioActual);
    } else if (response.status === 404) {
      showToast('Usuario no encontrado: ' + nickname, 'error');
    } else {
      const errorData = await response.json();
      showToast('Error al cargar usuario: ' + (errorData.error || 'Error desconocido'), 'error');
    }
  } catch (error) {
    console.error('Error:', error);
    showToast('Error al cargar el detalle del usuario', 'error');
  }
}

// Mostrar detalle completo del usuario
function mostrarDetalleUsuarioCompleto(usuario) {
  const detalle = document.querySelector('.detalle-usuario');
  
  // Determinar el tipo de usuario
  let tipoUsuario = determinarTipoUsuario(usuario);
  
  // Ocultar lista y mostrar detalle
  document.getElementById('lista-usuarios').style.display = 'none';
  detalle.style.display = 'block';
  
  // Llenar información del header
  let imagenSrc = '';
  if (usuario.foto && usuario.foto.trim() !== '') {
    imagenSrc = `data:image/jpeg;base64,${usuario.foto}`;
  } else {
    imagenSrc = 'static/img/logoAvionSolo.png';
  }
  
  document.getElementById('usuario-imagen').src = imagenSrc;
  document.getElementById('usuario-nombre').textContent = usuario.nombre;
  document.getElementById('usuario-credential').textContent = `${usuario.nickname} / ${usuario.correo}`;
  
  // Llenar información del perfil
  document.getElementById('perfil-nickname').textContent = usuario.nickname;
  document.getElementById('perfil-nombre').textContent = usuario.nombre;
  document.getElementById('perfil-email').textContent = usuario.correo;
  
  // Ocultar todos los campos específicos primero
  const camposEspecificos = ['perfil-apellido', 'perfil-nacionalidad', 'perfil-documento', 
                            'perfil-fecha-nacimiento', 'perfil-sitio', 'perfil-descripcion'];
  camposEspecificos.forEach(campo => {
    const elemento = document.getElementById(campo);
    if (elemento) elemento.style.display = 'none';
  });
  
  // Mostrar campos específicos según el tipo de usuario
  if (tipoUsuario === 'cliente') {
    // Campos de cliente
    if (usuario.apellido) {
      const apellidoElement = document.getElementById('perfil-apellido');
      apellidoElement.style.display = 'block';
      apellidoElement.querySelector('span').textContent = usuario.apellido;
    }
    
    if (usuario.nacionalidad) {
      const nacionalidadElement = document.getElementById('perfil-nacionalidad');
      nacionalidadElement.style.display = 'block';
      nacionalidadElement.querySelector('span').textContent = usuario.nacionalidad;
    }
    
    if (usuario.numeroDocumento && usuario.tipoDocumento) {
      const documentoElement = document.getElementById('perfil-documento');
      documentoElement.style.display = 'block';
      documentoElement.querySelector('span').textContent = `${usuario.tipoDocumento}: ${usuario.numeroDocumento}`;
    }
    
    if (usuario.fechaNacimiento) {
      const fechaElement = document.getElementById('perfil-fecha-nacimiento');
      fechaElement.style.display = 'block';
      let fechaTexto = '';
      if (typeof usuario.fechaNacimiento === 'object') {
        fechaTexto = `${usuario.fechaNacimiento.dia}/${usuario.fechaNacimiento.mes}/${usuario.fechaNacimiento.ano}`;
      } else {
        fechaTexto = usuario.fechaNacimiento;
      }
      fechaElement.querySelector('span').textContent = fechaTexto;
    }
    
    // Configurar tab de actividad para cliente
    const tabActividad = document.querySelector('[data-tab="actividad"]');
    tabActividad.style.display = 'block';
    tabActividad.textContent = 'Reservas y Paquetes';
    
  } else if (tipoUsuario === 'aerolinea') {
    // Campos de aerolínea
    if (usuario.descripcion) {
      const descripcionElement = document.getElementById('perfil-descripcion');
      descripcionElement.style.display = 'block';
      descripcionElement.querySelector('span').textContent = usuario.descripcion;
    }
    
    if (usuario.sitioWeb) {
      const sitioElement = document.getElementById('perfil-sitio');
      sitioElement.style.display = 'block';
      sitioElement.querySelector('span').textContent = usuario.sitioWeb;
    }
    
    // Configurar tab de actividad para aerolínea
    const tabActividad = document.querySelector('[data-tab="actividad"]');
    tabActividad.style.display = 'block';
    tabActividad.textContent = 'Rutas de Vuelo';
  }
  
  // Mostrar seguidores y seguidos (características sociales)
  mostrarSeguidoresYSeguidos(usuario);
  
  // Activar tab de perfil por defecto
  activarTab('perfil');
  
  // Scroll al detalle
  detalle.scrollIntoView({ behavior: 'smooth' });
}

// Función para mostrar seguidores y seguidos
function mostrarSeguidoresYSeguidos(usuario) {
  const seguidoresList = document.getElementById('seguidores-list');
  const seguidosList = document.getElementById('seguidos-list');
  const seguidoresCount = document.getElementById('seguidores-count');
  const seguidosCount = document.getElementById('seguidos-count');
  
  // Mostrar seguidores
  const seguidores = usuario.seguidores || [];
  seguidoresCount.textContent = `(${seguidores.length})`;
  
  if (seguidores.length > 0) {
    seguidoresList.innerHTML = seguidores.map(nickname => {
      // Escapar el nickname para evitar problemas con comillas
      const safeNickname = nickname.replace(/'/g, "\\'");
      return `
        <div class="seguidor-item" onclick="mostrarDetalleUsuario('${safeNickname}')">
          <i class="fas fa-user"></i>
          <span class="nickname">${nickname}</span>
        </div>
      `;
    }).join('');
  } else {
    seguidoresList.innerHTML = '<p class="social-empty-message">No hay seguidores</p>';
  }
  
  // Mostrar seguidos
  const seguidos = usuario.seguidos || [];
  seguidosCount.textContent = `(${seguidos.length})`;
  
  if (seguidos.length > 0) {
    seguidosList.innerHTML = seguidos.map(nickname => {
      // Escapar el nickname para evitar problemas con comillas
      const safeNickname = nickname.replace(/'/g, "\\'");
      return `
        <div class="seguido-item" onclick="mostrarDetalleUsuario('${safeNickname}')">
          <i class="fas fa-user"></i>
          <span class="nickname">${nickname}</span>
        </div>
      `;
    }).join('');
  } else {
    seguidosList.innerHTML = '<p class="social-empty-message">No sigue a nadie</p>';
  }
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
  const tabBtn = document.querySelector(`[data-tab="${tabName}"]`);
  const tabContent = document.getElementById(`tab-${tabName}`);
  
  if (tabBtn && tabContent) {
    tabBtn.classList.add('active');
    tabContent.style.display = 'block';
    
    // Cargar contenido específico del tab
    if (tabName === 'actividad' && usuarioActual) {
      cargarContenidoActividad(usuarioActual);
    }
  }
}

// Cargar contenido de actividad según el tipo de usuario
async function cargarContenidoActividad(usuario) {
  const actividadContent = document.getElementById('actividad-content');
  
  // Determinar el tipo de usuario
  let tipoUsuario = determinarTipoUsuario(usuario);
  console.log('Tipo de usuario determinado:', tipoUsuario);
  
  if (tipoUsuario === 'aerolinea') {
    console.log('Mostrando rutas de aerolínea');
    mostrarRutasAerolinea(usuario, actividadContent);
  } else if (tipoUsuario === 'cliente') {
    console.log('Usuario es cliente, verificando si está logueado...');
    // Verificar si el usuario está viendo su propio perfil
    const esUsuarioLogueado = await verificarSiEsUsuarioLogueado(usuario);
    console.log('Resultado de verificación:', esUsuarioLogueado);
    mostrarReservasCliente(usuario, actividadContent, esUsuarioLogueado);
  } else {
    console.log('Tipo de usuario no reconocido:', tipoUsuario);
    actividadContent.innerHTML = '<p class="info-message">Tipo de usuario no reconocido.</p>';
  }
}

// Verificar si el usuario actual es una aerolínea logueada
async function verificarSiEsAerolineaLogueada(usuario) {
  try {
    console.log('=== VERIFICANDO SI ES AEROLÍNEA LOGUEADA ===');
    console.log('Nickname del usuario consultado:', usuario.nickname);
    
    // Obtener información del usuario logueado desde el servidor
    const response = await fetch('/VolandoUy-WebApp/api/usuarios/perfil', {
      credentials: 'include' // Importante: enviar cookies de sesión
    });
    console.log('Response del perfil:', response.status);
    
    if (response.ok) {
      const usuarioLogueado = await response.json();
      console.log('Usuario logueado obtenido:', usuarioLogueado);
      console.log('Nickname logueado:', usuarioLogueado.nickname);
      console.log('Tipo logueado:', usuarioLogueado.tipo);
      
      const nicknameCoincide = usuarioLogueado.nickname === usuario.nickname;
      const esAerolinea = usuarioLogueado.tipo === 'aerolinea';
      const esLogueado = nicknameCoincide && esAerolinea;
      
      console.log('¿Nickname coincide?', nicknameCoincide);
      console.log('¿Es aerolínea?', esAerolinea);
      console.log('¿Es la aerolínea logueada?', esLogueado);
      console.log('==========================================');
      return esLogueado;
    } else {
      console.log('No se pudo obtener el perfil del usuario logueado');
      console.log('==========================================');
    }
  } catch (error) {
    console.error('Error verificando aerolínea logueada:', error);
    console.log('==========================================');
  }
  
  // Fallback: si no se puede verificar, asumir que no es la aerolínea logueada
  console.log('Fallback: asumiendo que NO es la aerolínea logueada');
  console.log('==========================================');
  return false;
}

// Verificar si el usuario actual es el usuario logueado
async function verificarSiEsUsuarioLogueado(usuario) {
  try {
    console.log('Verificando si es usuario logueado:', usuario.nickname);
    
    // Obtener información del usuario logueado desde el servidor
    const response = await fetch('/VolandoUy-WebApp/api/usuarios/perfil', {
      credentials: 'include' // Importante: enviar cookies de sesión
    });
    console.log('Response del perfil:', response.status);
    
    if (response.ok) {
      const usuarioLogueado = await response.json();
      console.log('Usuario logueado obtenido:', usuarioLogueado);
      const esLogueado = usuarioLogueado.nickname === usuario.nickname;
      console.log('¿Es el usuario logueado?', esLogueado);
      return esLogueado;
    } else {
      console.log('No se pudo obtener el perfil del usuario logueado');
    }
  } catch (error) {
    console.error('Error verificando usuario logueado:', error);
  }
  
  // Fallback: si no se puede verificar, asumir que no es el usuario logueado
  console.log('Fallback: asumiendo que NO es el usuario logueado');
  return false;
}

// Verificar si el usuario logueado es un cliente
async function verificarSiEsClienteLogueado() {
  try {
    const response = await fetch('/VolandoUy-WebApp/api/usuarios/perfil', {
      credentials: 'include'
    });
    
    if (response.ok) {
      const usuarioLogueado = await response.json();
      return usuarioLogueado.tipo === 'cliente';
    }
  } catch (error) {
    console.error('Error verificando tipo de usuario:', error);
  }
  
  return false;
}

// Mostrar rutas de aerolínea
async function mostrarRutasAerolinea(usuario, contenedor) {
  contenedor.innerHTML = '<p class="info-message">Cargando rutas de vuelo...</p>';
  
  try {
    console.log('=== CARGANDO RUTAS DE AEROLÍNEA ===');
    console.log('Nickname de la aerolínea:', usuario.nickname);
    console.log('Rutas en el usuario:', usuario.rutas);
    
    // Verificar si es la aerolínea logueada
    const esAerolineaLogueada = await verificarSiEsAerolineaLogueada(usuario);
    console.log('¿Es la aerolínea logueada?', esAerolineaLogueada);
    
    let rutas = [];
    
    // Si es la aerolínea logueada y tiene rutas en el objeto usuario, usarlas
    if (esAerolineaLogueada && usuario.rutas && usuario.rutas.length > 0) {
      console.log('Usando rutas del objeto usuario (todas las rutas)');
      rutas = usuario.rutas;
    } else {
      // Si no, llamar a la API para obtener solo las rutas confirmadas
      console.log('Llamando a la API para obtener rutas confirmadas');
      const url = `/VolandoUy-WebApp/api/rutas?aerolinea=${encodeURIComponent(usuario.nickname)}`;
      console.log('URL de la petición:', url);
      
      const response = await fetch(url, {
        credentials: 'include' // Importante: enviar cookies de sesión
      });
      console.log('Status de la respuesta:', response.status);
      
      if (!response.ok) {
        throw new Error(`Error HTTP: ${response.status}`);
      }
      
      rutas = await response.json();
    }
    
    console.log('Rutas obtenidas:', rutas);
    console.log('Número de rutas:', rutas ? rutas.length : 0);
    if (rutas && rutas.length > 0) {
      console.log('Estados de las rutas:', rutas.map(r => r.estado));
    }
    console.log('===================================');
    
    if (rutas && rutas.length > 0) {
      // Verificar si el usuario logueado es un cliente
      const esClienteLogueado = await verificarSiEsClienteLogueado();
      
      // Usar el valor ya calculado de esAerolineaLogueada
      const titulo = esAerolineaLogueada ? 'Rutas de Vuelo' : 'Rutas de Vuelo Confirmadas';
      
      contenedor.innerHTML = `
        <div class="rutas-container">
          <h4>${titulo}</h4>
          <div class="rutas-grid">
            ${rutas.map(ruta => `
              <div class="ruta-card clickeable" data-ruta-nombre="${ruta.nombre}" data-ruta-descripcion="${ruta.descripcion || ''}">
                <h5>${ruta.nombre}</h5>
                <p class="ruta-descripcion">${ruta.descripcion || 'Sin descripción'}</p>
                <div class="ruta-ciudades">
                  <span class="ciudad-origen">${typeof ruta.ciudadOrigen === 'object' ? ruta.ciudadOrigen.nombre : ruta.ciudadOrigen}</span>
                  <i class="fas fa-arrow-right"></i>
                  <span class="ciudad-destino">${typeof ruta.ciudadDestino === 'object' ? ruta.ciudadDestino.nombre : ruta.ciudadDestino}</span>
                </div>
                <div class="ruta-estado">
                  <span class="estado-badge ${ruta.estado ? ruta.estado.toLowerCase() : 'confirmada'}">${ruta.estado || 'Confirmada'}</span>
                </div>
              </div>
            `).join('')}
          </div>
        </div>
      `;
      
      // Agregar event listeners para TODAS las rutas (clientes y aerolíneas)
      // Cuando se hace click en una ruta, mostrar el detalle en un modal
      document.querySelectorAll('.ruta-card').forEach(card => {
        card.addEventListener('click', async () => {
          const rutaNombre = card.dataset.rutaNombre;
          console.log('Usuario hizo click en ruta:', rutaNombre);
          
          // Si es cliente logueado, buscar paquetes primero
          if (esClienteLogueado) {
            try {
              const response = await fetch(`/VolandoUy-WebApp/api/reservas/paquetes-cliente/${encodeURIComponent(rutaNombre)}`, {
                credentials: 'include'
              });
              
              if (response.ok) {
                const paquetes = await response.json();
                console.log('Paquetes encontrados para la ruta:', paquetes);
                
                if (paquetes && paquetes.length > 0) {
                  // Si hay paquetes, mostrar el detalle del paquete en el modal
                  const paquete = paquetes[0];
                  console.log('Mostrando detalle del paquete:', paquete);
                  mostrarDetallePaqueteEnModal(paquete);
                  return;
                }
              }
            } catch (error) {
              console.error('Error al buscar paquetes:', error);
            }
          }
          
          // Si no es cliente o no tiene paquetes, mostrar detalle de la ruta en el modal
          mostrarDetalleRutaEnModal(rutaNombre);
        });
      });
    } else {
      // Usar el valor ya calculado de esAerolineaLogueada
      const mensaje = esAerolineaLogueada 
        ? 'Esta aerolínea no tiene rutas de vuelo registradas.' 
        : 'Esta aerolínea no tiene rutas de vuelo confirmadas.';
      
      contenedor.innerHTML = `
        <div class="info-message">
          <i class="fas fa-info-circle"></i>
          <p>${mensaje}</p>
        </div>
      `;
    }
  } catch (error) {
    console.error('Error cargando rutas:', error);
    contenedor.innerHTML = `
      <div class="error-message">
        <i class="fas fa-exclamation-triangle"></i>
        <p>Error al cargar las rutas de vuelo: ${error.message}</p>
      </div>
    `;
  }
}

// Mostrar reservas y paquetes de cliente
async function mostrarReservasCliente(usuario, contenedor, esUsuarioLogueado = false) {
  if (!esUsuarioLogueado) {
    // Si no es el usuario logueado, mostrar mensaje informativo
    contenedor.innerHTML = `
      <div class="info-message">
        <i class="fas fa-info-circle"></i>
        <p>Las reservas y paquetes de este cliente solo son visibles para el propio usuario.</p>
      </div>
    `;
    return;
  }
  
  contenedor.innerHTML = '<p class="info-message">Cargando reservas y paquetes...</p>';
  
  try {
    console.log('Cargando reservas y paquetes para cliente logueado:', usuario.nickname);
    console.log('Datos completos del usuario:', usuario);
    
    // Usar las reservas y paquetes que vienen del usuario si están disponibles
    let reservas = usuario.reservas || [];
    let paquetes = usuario.paquetes || [];
    
    console.log('Reservas iniciales:', reservas);
    console.log('Paquetes iniciales:', paquetes);
    
    // Si no hay reservas en el usuario, intentar cargar desde el endpoint específico
    if (!reservas || reservas.length === 0) {
      console.log('No hay reservas en el usuario, intentando cargar desde endpoint específico...');
      
      // Intentar obtener reservas usando el método mostrarDatosUsuario directamente
      try {
        const debugResponse = await fetch(`/VolandoUy-WebApp/api/usuarios/debug?nickname=${usuario.nickname}`);
        if (debugResponse.ok) {
          const debugData = await debugResponse.json();
          console.log('Debug data:', debugData);
          
          if (debugData.mostrarDatosUsuario === 'OK') {
            // Si mostrarDatosUsuario funciona, usar el endpoint completo
            const fullResponse = await fetch(`/VolandoUy-WebApp/api/usuarios/${usuario.nickname}`);
            if (fullResponse.ok) {
              const fullData = await fullResponse.json();
              console.log('Full data:', fullData);
              reservas = fullData.reservas || [];
              paquetes = fullData.paquetes || [];
            }
          }
        }
      } catch (debugError) {
        console.log('Error en debug:', debugError);
      }
    }
    
    console.log('Reservas finales:', reservas);
    console.log('Paquetes finales:', paquetes);
    
    // Construir HTML
    let html = '<div class="cliente-actividad">';
    
    // Mostrar reservas
    if (reservas && reservas.length > 0) {
      html += `
        <div class="reservas-section">
          <h4>Reservas de Vuelo</h4>
          <div class="reservas-grid">
            ${reservas.map(reserva => `
              <div class="reserva-card">
                <h5>Reserva #${reserva.id || 'N/A'}</h5>
                <p class="reserva-cliente">Cliente: ${reserva.nickname || usuario.nickname}</p>
                <p class="reserva-fecha">Fecha: ${reserva.fechaReserva || 'N/A'}</p>
                <p class="reserva-costo">Costo: $${reserva.costoReserva || 'N/A'}</p>
                ${reserva.vuelo ? `<p class="reserva-vuelo">Vuelo: ${reserva.vuelo}</p>` : ''}
              </div>
            `).join('')}
          </div>
        </div>
      `;
    } else {
      html += `
        <div class="reservas-section">
          <h4>Reservas de Vuelo</h4>
          <div class="info-message">
            <i class="fas fa-info-circle"></i>
            <p>No tienes reservas de vuelo registradas.</p>
          </div>
        </div>
      `;
    }
    
    // Mostrar paquetes comprados
    if (paquetes && paquetes.length > 0) {
      html += `
        <div class="paquetes-section">
          <h4>Paquetes Comprados</h4>
          <div class="paquetes-grid">
            ${paquetes.map(paquete => `
              <div class="paquete-card">
                <h5>${paquete.nombrePaquete || paquete.nombre || 'Paquete #' + (paquete.id || 'N/A')}</h5>
                <p class="paquete-cliente">Cliente: ${paquete.nickname || usuario.nickname}</p>
                <p class="paquete-fecha">Fecha de Compra: ${paquete.fechaCompra || 'N/A'}</p>
                <p class="paquete-costo">Costo Total: $${paquete.costoTotal || 'N/A'}</p>
                <p class="paquete-vencimiento">Vencimiento: ${paquete.vencimiento || 'N/A'}</p>
              </div>
            `).join('')}
          </div>
        </div>
      `;
    } else {
      html += `
        <div class="paquetes-section">
          <h4>Paquetes Comprados</h4>
          <div class="info-message">
            <i class="fas fa-info-circle"></i>
            <p>No tienes paquetes comprados.</p>
            <div class="action-buttons">
              <button class="btn-secondary" onclick="window.location.href='compraPaquete.jsp'">
                Comprar Paquetes
              </button>
            </div>
          </div>
        </div>
      `;
    }
    
    html += '</div>';
    contenedor.innerHTML = html;
    
    // Agregar event listeners a las tarjetas de reservas
    document.querySelectorAll('.reserva-card').forEach((card, index) => {
      card.style.cursor = 'pointer';
      card.addEventListener('click', () => {
        const reserva = reservas[index];
        mostrarDetalleReservaEnModal(reserva);
      });
    });
    
    // Agregar event listeners a las tarjetas de paquetes
    document.querySelectorAll('.paquete-card').forEach((card, index) => {
      card.style.cursor = 'pointer';
      card.addEventListener('click', () => {
        const paquete = paquetes[index];
        mostrarDetallePaqueteCompradoEnModal(paquete);
      });
    });
    
  } catch (error) {
    console.error('Error cargando reservas y paquetes:', error);
    contenedor.innerHTML = `
      <div class="error-message">
        <i class="fas fa-exclamation-triangle"></i>
        <p>Error al cargar las reservas y paquetes: ${error.message}</p>
      </div>
    `;
  }
}

// Cargar filtros
function cargarFiltros(data) {
  const selectTipo = document.getElementById("select-tipo");
  const buscadorNombre = document.getElementById("buscador-nombre");

  if (!selectTipo || !buscadorNombre) {
    console.error('No se encontraron los elementos de filtro');
    return;
  }

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
    // Determinar el tipo de usuario usando la función mejorada
    let tipoUsuario = determinarTipoUsuario(usuario);
    
    const nombreNormalizado = quitarTildes(usuario.nombre.toLowerCase());
    const nicknameNormalizado = quitarTildes(usuario.nickname.toLowerCase());
    const emailNormalizado = quitarTildes(usuario.correo.toLowerCase());

    return (
      (tipo === "" || tipoUsuario === tipo) &&
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
  document.getElementById('lista-usuarios').style.display = 'block';
  document.querySelector('.detalle-usuario').style.display = 'none';
  usuarioActual = null;
  
  // Mantener los filtros activos
  const tipo = document.getElementById("select-tipo").value;
  const texto = document.getElementById("buscador-nombre").value;
  
  // Aplicar filtros actuales
  if (tipo || texto) {
    filtrar();
  } else {
    mostrarUsuarios(usuariosData);
  }
}

// Función para abrir el modal de detalle
function abrirModalDetalle() {
  const modal = document.getElementById('modal-detalle');
  if (modal) {
    console.log('Abriendo modal de detalle');
    modal.style.display = 'flex';
    modal.style.zIndex = '1000';
  } else {
    console.error('Modal no encontrado');
  }
}

// Función para cerrar el modal de detalle
function cerrarModalDetalle() {
  const modal = document.getElementById('modal-detalle');
  if (modal) {
    modal.style.display = 'none';
  }
}

// Función para mostrar detalle de ruta en el modal
async function mostrarDetalleRutaEnModal(rutaNombre) {
  abrirModalDetalle();
  const modalBody = document.getElementById('modal-body');
  modalBody.innerHTML = '<div class="loading-container"><div class="spinner"></div><p>Cargando detalles de la ruta...</p></div>';
  
  try {
    // Cargar detalles de la ruta
    const response = await fetch(`/VolandoUy-WebApp/api/rutas/${encodeURIComponent(rutaNombre)}`, {
      credentials: 'include'
    });
    
    if (!response.ok) {
      throw new Error(`Error HTTP: ${response.status}`);
    }
    
    const ruta = await response.json();
    
    // Cargar vuelos de la ruta
    const vuelosResponse = await fetch(`/VolandoUy-WebApp/api/rutas/${encodeURIComponent(rutaNombre)}/vuelos`, {
      credentials: 'include'
    });
    
    let vuelos = [];
    if (vuelosResponse.ok) {
      vuelos = await vuelosResponse.json();
    }
    
    // Construir el HTML del detalle
    let html = `
      <h2>${ruta.nombre}</h2>
      <p><strong>Descripción:</strong> ${ruta.descripcion || 'Sin descripción'}</p>
      <p><strong>Origen:</strong> ${ruta.ciudadOrigen ? ruta.ciudadOrigen.nombre + ', ' + ruta.ciudadOrigen.pais : 'N/A'}</p>
      <p><strong>Destino:</strong> ${ruta.ciudadDestino ? ruta.ciudadDestino.nombre + ', ' + ruta.ciudadDestino.pais : 'N/A'}</p>
      <p><strong>Estado:</strong> <span class="estado-badge ${ruta.estado ? ruta.estado.toLowerCase() : 'confirmada'}">${ruta.estado || 'Confirmada'}</span></p>
      <p><strong>Fecha de alta:</strong> ${ruta.fechaAlta || 'N/A'}</p>
    `;
    
    if (ruta.categorias && ruta.categorias.length > 0) {
      html += `<p><strong>Categorías:</strong> ${ruta.categorias.join(', ')}</p>`;
    }
    
    // Mostrar vuelos
    if (vuelos && vuelos.length > 0) {
      html += '<h3>Vuelos disponibles</h3>';
      html += '<div class="modal-vuelos-grid">';
      vuelos.forEach(vuelo => {
        html += `
          <div class="modal-vuelo-card" onclick="mostrarDetalleVueloEnModal('${vuelo.nombre}')">
            <h4>${vuelo.nombre}</h4>
            <p><strong>Fecha:</strong> ${vuelo.fechaVuelo || 'N/A'}</p>
            <p><strong>Hora:</strong> ${vuelo.horaVuelo || 'N/A'}</p>
            <p><strong>Duración:</strong> ${vuelo.duracion || 'N/A'}</p>
            <p><strong>Asientos:</strong> ${vuelo.asientosMaxTurista || 0} Turista / ${vuelo.asientosMaxEjecutivo || 0} Ejecutivo</p>
          </div>
        `;
      });
      html += '</div>';
    } else {
      html += '<p>No hay vuelos disponibles para esta ruta.</p>';
    }
    
    modalBody.innerHTML = html;
  } catch (error) {
    console.error('Error cargando detalle de ruta:', error);
    modalBody.innerHTML = '<p>Error al cargar los detalles de la ruta.</p>';
  }
}

// Función para mostrar detalle de vuelo en el modal
async function mostrarDetalleVueloEnModal(vueloNombre) {
  abrirModalDetalle();
  const modalBody = document.getElementById('modal-body');
  modalBody.innerHTML = '<div class="loading-container"><div class="spinner"></div><p>Cargando detalles del vuelo...</p></div>';
  
  try {
    // Cargar detalles del vuelo
    const response = await fetch(`/VolandoUy-WebApp/api/reservas/vuelo-detalle/${encodeURIComponent(vueloNombre)}`, {
      credentials: 'include'
    });
    
    if (!response.ok) {
      throw new Error(`Error HTTP: ${response.status}`);
    }
    
    const vuelo = await response.json();
    
    // Cargar reservas del vuelo
    const reservasResponse = await fetch(`/VolandoUy-WebApp/api/vuelos/reservas/${encodeURIComponent(vueloNombre)}`, {
      credentials: 'include'
    });
    
    let reservas = [];
    if (reservasResponse.ok) {
      reservas = await reservasResponse.json();
    }
    
    // Construir el HTML del detalle
    let html = `
      <h2>${vuelo.nombre}</h2>
      <p><strong>Fecha de vuelo:</strong> ${vuelo.fechaVuelo || 'N/A'}</p>
      <p><strong>Hora de vuelo:</strong> ${vuelo.horaVuelo || 'N/A'}</p>
      <p><strong>Duración:</strong> ${vuelo.duracion || 'N/A'}</p>
      <p><strong>Asientos máximos Turista:</strong> ${vuelo.asientosMaxTurista || 0}</p>
      <p><strong>Asientos máximos Ejecutivo:</strong> ${vuelo.asientosMaxEjecutivo || 0}</p>
    `;
    
    // Mostrar información de la ruta si está disponible
    if (vuelo.ruta) {
      html += '<h3>Información de la ruta</h3>';
      html += `<p><strong>Ruta:</strong> ${vuelo.ruta.nombre || 'N/A'}</p>`;
      html += `<p><strong>Descripción:</strong> ${vuelo.ruta.descripcion || 'Sin descripción'}</p>`;
      if (vuelo.ruta.costoBaseTurista) {
        html += `<p><strong>Costo base Turista:</strong> $${vuelo.ruta.costoBaseTurista}</p>`;
      }
      if (vuelo.ruta.costoBaseEjecutivo) {
        html += `<p><strong>Costo base Ejecutivo:</strong> $${vuelo.ruta.costoBaseEjecutivo}</p>`;
      }
      if (vuelo.ruta.costoEquipajeExtra) {
        html += `<p><strong>Costo equipaje extra:</strong> $${vuelo.ruta.costoEquipajeExtra}</p>`;
      }
    }
    
    // Mostrar reservas si las hay
    if (reservas && reservas.length > 0) {
      html += '<h3>Reservas</h3>';
      html += '<div class="modal-vuelos-grid">';
      reservas.forEach(reserva => {
        html += `
          <div class="modal-vuelo-card">
            <h4>Reserva #${reserva.id}</h4>
            <p><strong>Cliente:</strong> ${reserva.cliente || 'N/A'}</p>
            <p><strong>Fecha de reserva:</strong> ${reserva.fechaReserva || 'N/A'}</p>
            <p><strong>Costo:</strong> $${(reserva.costoReserva || 0).toFixed(2)}</p>
          </div>
        `;
      });
      html += '</div>';
    }
    
    modalBody.innerHTML = html;
  } catch (error) {
    console.error('Error cargando detalle de vuelo:', error);
    modalBody.innerHTML = '<p>Error al cargar los detalles del vuelo.</p>';
  }
}

// Función para mostrar detalle de paquete en el modal
async function mostrarDetallePaqueteEnModal(paquete) {
  abrirModalDetalle();
  const modalBody = document.getElementById('modal-body');
  modalBody.innerHTML = '<div class="loading-container"><div class="spinner"></div><p>Cargando detalles del paquete...</p></div>';
  
  try {
    // Cargar detalles completos del paquete
    const response = await fetch(`/VolandoUy-WebApp/api/paquetes/${encodeURIComponent(paquete.nombre)}`, {
      credentials: 'include'
    });
    
    if (!response.ok) {
      throw new Error(`Error HTTP: ${response.status}`);
    }
    
    const data = await response.json();
    const paqueteCompleto = data.paquete;
    const rutas = data.rutas || [];
    
    // Construir el HTML del detalle
    let html = `
      <h2>${paqueteCompleto.nombre}</h2>
      <p><strong>Descripción:</strong> ${paqueteCompleto.descripcion || 'Sin descripción'}</p>
      <p><strong>Días válidos:</strong> ${paqueteCompleto.diasValidos || 0}</p>
      <p><strong>Descuento:</strong> ${paqueteCompleto.descuento || 0}%</p>
      <p><strong>Costo total:</strong> $${paqueteCompleto.costoTotal || 0}</p>
      <p><strong>Fecha de alta:</strong> ${paqueteCompleto.fechaAlta || 'N/A'}</p>
    `;
    
    // Mostrar rutas del paquete
    if (rutas && rutas.length > 0) {
      html += '<h3>Rutas incluidas</h3>';
      html += '<div class="modal-vuelos-grid">';
      rutas.forEach(ruta => {
        html += `
          <div class="modal-vuelo-card" onclick="mostrarDetalleRutaEnModal('${ruta.nombre}')">
            <h4>${ruta.nombre}</h4>
            <p><strong>Origen:</strong> ${ruta.ciudadOrigen || 'N/A'}</p>
            <p><strong>Destino:</strong> ${ruta.ciudadDestino || 'N/A'}</p>
            <p><strong>Cantidad:</strong> ${ruta.cantidad || 0}</p>
            <p><strong>Tipo asiento:</strong> ${ruta.tipoAsiento || 'No especificado'}</p>
          </div>
        `;
      });
      html += '</div>';
    } else {
      html += '<p>No hay rutas incluidas en este paquete.</p>';
    }
    
    modalBody.innerHTML = html;
  } catch (error) {
    console.error('Error cargando detalle de paquete:', error);
    modalBody.innerHTML = '<p>Error al cargar los detalles del paquete.</p>';
  }
}

// Función para mostrar detalle de reserva en el modal
async function mostrarDetalleReservaEnModal(reserva) {
  console.log('=== MOSTRAR DETALLE RESERVA ===');
  console.log('Reserva recibida:', reserva);
  abrirModalDetalle();
  const modalBody = document.getElementById('modal-body');
  modalBody.innerHTML = '<div class="loading-container"><div class="spinner"></div><p>Cargando detalles de la reserva...</p></div>';
  
  try {
    // Mostrar datos básicos primero
    let reservaCompleta = reserva;
    console.log('Usando datos básicos de reserva:', reservaCompleta);
    
    // Intentar cargar detalles adicionales usando el endpoint de consulta de reservas
    try {
      const reservaResponse = await fetch(`/VolandoUy-WebApp/api/consulta-reservas/detalle-reserva/${reserva.id}`, {
        credentials: 'include'
      });
      
      if (reservaResponse.ok) {
        reservaCompleta = await reservaResponse.json();
        console.log('Detalle completo de reserva cargado desde consulta-reservas:', reservaCompleta);
      } else {
        console.log('Error en respuesta consulta-reservas:', reservaResponse.status, reservaResponse.statusText);
        
        // Fallback: intentar con el endpoint de usuarios
        try {
          const reservaResponse2 = await fetch(`/VolandoUy-WebApp/api/usuarios/reserva-detalle/${reserva.id}`, {
            credentials: 'include'
          });
          
          if (reservaResponse2.ok) {
            reservaCompleta = await reservaResponse2.json();
            console.log('Detalle completo de reserva cargado desde usuarios:', reservaCompleta);
          }
        } catch (error2) {
          console.log('Error al cargar detalles desde usuarios:', error2);
        }
      }
    } catch (error) {
      console.log('Error al cargar detalles:', error);
    }
    
    // Construir información del vuelo desde los datos cargados
    let vueloInfo = '';
    if (reservaCompleta.vuelo) {
      const vuelo = reservaCompleta.vuelo;
      vueloInfo = `
        <div class="flight-details">
          <h3><i class="fas fa-plane"></i> Información del Vuelo</h3>
          <div class="detail-grid">
            <div class="detail-item">
              <span class="label">Vuelo:</span>
              <span class="value">${vuelo.nombre || 'N/A'}</span>
            </div>
            <div class="detail-item">
              <span class="label">Fecha:</span>
              <span class="value">${vuelo.fechaVuelo || 'N/A'}</span>
            </div>
            <div class="detail-item">
              <span class="label">Hora:</span>
              <span class="value">${vuelo.horaVuelo || 'N/A'}</span>
            </div>
            <div class="detail-item">
              <span class="label">Duración:</span>
              <span class="value">${vuelo.duracion || 'N/A'}</span>
            </div>
          </div>
          ${vuelo.ruta ? `
            <div class="ruta-details">
              <h4><i class="fas fa-route"></i> Detalles de la Ruta</h4>
              <div class="detail-grid">
                <div class="detail-item">
                  <span class="label">Nombre:</span>
                  <span class="value">${vuelo.ruta.nombre || 'N/A'}</span>
                </div>
                <div class="detail-item">
                  <span class="label">Descripción:</span>
                  <span class="value">${vuelo.ruta.descripcion || 'Sin descripción'}</span>
                </div>
                <div class="detail-item">
                  <span class="label">Origen:</span>
                  <span class="value">${vuelo.ruta.ciudadOrigen?.nombre || 'N/A'}, ${vuelo.ruta.ciudadOrigen?.pais || 'N/A'}</span>
                </div>
                <div class="detail-item">
                  <span class="label">Destino:</span>
                  <span class="value">${vuelo.ruta.ciudadDestino?.nombre || 'N/A'}, ${vuelo.ruta.ciudadDestino?.pais || 'N/A'}</span>
                </div>
                ${vuelo.ruta.costos ? `
                  <div class="detail-item">
                    <span class="label">Costo Base Turista:</span>
                    <span class="value cost">$${vuelo.ruta.costos.costoBaseTurista || 0}</span>
                  </div>
                  <div class="detail-item">
                    <span class="label">Costo Base Ejecutivo:</span>
                    <span class="value cost">$${vuelo.ruta.costos.costoBaseEjecutivo || 0}</span>
                  </div>
                  <div class="detail-item">
                    <span class="label">Costo Equipaje Extra:</span>
                    <span class="value cost">$${vuelo.ruta.costos.costoEquipajeExtra || 0}</span>
                  </div>
                ` : ''}
              </div>
            </div>
          ` : ''}
        </div>
      `;
    }
    
    // Construir información de pasajeros desde los datos cargados (similar a consultaReserva)
    let pasajerosInfo = '';
    if (reservaCompleta.pasajeros) {
      pasajerosInfo = `
        <div class="passengers-details">
          <h3><i class="fas fa-users"></i> Pasajeros</h3>
          <div class="passengers-info">
            <p>${obtenerTextoPasajeros(reservaCompleta.pasajeros)}</p>
          </div>
        </div>
      `;
    }
    
    // Construir información de la aerolínea si está disponible
    let aerolineaInfo = '';
    if (reservaCompleta.aerolinea) {
      const aerolinea = reservaCompleta.aerolinea;
      aerolineaInfo = `
        <div class="airline-details">
          <h3><i class="fas fa-building"></i> Información de la Aerolínea</h3>
          <div class="detail-grid">
            <div class="detail-item">
              <span class="label">Nombre:</span>
              <span class="value">${aerolinea.nombre || 'N/A'}</span>
            </div>
            <div class="detail-item">
              <span class="label">Descripción:</span>
              <span class="value">${aerolinea.descripcion || 'Sin descripción'}</span>
            </div>
          </div>
        </div>
      `;
    }
    
    // Mostrar datos básicos de la reserva
    console.log('Construyendo HTML del modal');
    const html = `
      <div class="reservation-details">
        <div class="reservation-header">
          <h2><i class="fas fa-ticket-alt"></i> Reserva #${reservaCompleta.id || 'N/A'}</h2>
        </div>
        
        <div class="reservation-info">
          <h3><i class="fas fa-info-circle"></i> Información de la Reserva</h3>
          <div class="detail-grid">
            <div class="detail-item">
              <span class="label">Cliente:</span>
              <span class="value">${reservaCompleta.nicknameCliente || reservaCompleta.nickname || 'N/A'}</span>
            </div>
            <div class="detail-item">
              <span class="label">Fecha de reserva:</span>
              <span class="value">${reservaCompleta.fechaReserva || 'N/A'}</span>
            </div>
            <div class="detail-item">
              <span class="label">Costo total:</span>
              <span class="value cost">$${(reservaCompleta.costoReserva?.costoTotal || reservaCompleta.costoReserva || 0).toFixed(2)}</span>
            </div>
            <div class="detail-item">
              <span class="label">Estado:</span>
              <span class="value status">Confirmada</span>
            </div>
          </div>
        </div>
        
        ${vueloInfo}
        ${aerolineaInfo}
        ${pasajerosInfo}
        
        <div class="info-message">
          <i class="fas fa-info-circle"></i>
          <p>Esta es la información completa de la reserva. Para más detalles, consulta la sección de reservas en el menú principal.</p>
        </div>
      </div>
    `;
    
    console.log('HTML construido, insertando en modal');
    modalBody.innerHTML = html;
    console.log('Modal actualizado con contenido');
    
  } catch (error) {
    console.error('Error cargando detalle de reserva:', error);
    modalBody.innerHTML = `
      <div class="error-message">
        <i class="fas fa-exclamation-triangle"></i>
        <p>Error al cargar los detalles de la reserva: ${error.message}</p>
      </div>
    `;
  }
}

// Función auxiliar para procesar pasajeros (similar a consultaReserva)
function obtenerTextoPasajeros(pasajeros) {
  console.log('Procesando pasajeros:', pasajeros);
  
  if (!pasajeros) {
    console.log('Pasajeros es null/undefined');
    return 'Información no disponible';
  }
  
  if (Array.isArray(pasajeros)) {
    console.log('Pasajeros es un array con', pasajeros.length, 'elementos');
    if (pasajeros.length === 0) {
      return 'No hay pasajeros registrados';
    }
    
    // Si es un array de strings
    if (typeof pasajeros[0] === 'string') {
      return pasajeros.join(', ');
    }
    
    // Si es un array de objetos, extraer nombres
    if (typeof pasajeros[0] === 'object') {
      const nombres = pasajeros.map(p => {
        if (p.nombre) return p.nombre;
        if (p.nickname) return p.nickname;
        if (p.apellido) return p.apellido;
        return 'Pasajero sin nombre';
      });
      return nombres.join(', ');
    }
    
    return pasajeros.join(', ');
  }
  
  // Si es un objeto único
  if (typeof pasajeros === 'object') {
    if (pasajeros.nombre) return pasajeros.nombre;
    if (pasajeros.nickname) return pasajeros.nickname;
    return 'Pasajero sin nombre';
  }
  
  // Si es un string
  if (typeof pasajeros === 'string') {
    return pasajeros;
  }
  
  console.log('Tipo de pasajeros no reconocido:', typeof pasajeros);
  return 'Formato de pasajeros no reconocido';
}

// Función para mostrar detalle de paquete comprado en el modal
async function mostrarDetallePaqueteCompradoEnModal(paquete) {
  console.log('=== MOSTRAR DETALLE PAQUETE ===');
  console.log('Paquete recibido:', paquete);
  abrirModalDetalle();
  const modalBody = document.getElementById('modal-body');
  modalBody.innerHTML = '<div class="loading-container"><div class="spinner"></div><p>Cargando detalles del paquete...</p></div>';
  
  try {
    // Usar los datos del paquete que ya están disponibles
    let paqueteCompleto = paquete;
    let rutas = [];
    
    // Mostrar solo los datos disponibles del paquete comprado
    console.log('Usando datos disponibles del paquete:', paquete);
    
    // Construir HTML del detalle del paquete
    let html = `
      <div class="package-details">
        <div class="package-info">
          <h3><i class="fas fa-info-circle"></i> Información del Paquete</h3>
          <div class="detail-grid">
            <div class="detail-item">
              <span class="label">Cliente:</span>
              <span class="value">${paquete.nickname || 'N/A'}</span>
            </div>
            <div class="detail-item">
              <span class="label">Fecha de compra:</span>
              <span class="value">${paquete.fechaCompra || 'N/A'}</span>
            </div>
            <div class="detail-item">
              <span class="label">Costo total:</span>
              <span class="value cost">$${paquete.costoTotal || 0}</span>
            </div>
            <div class="detail-item">
              <span class="label">Vencimiento:</span>
              <span class="value">${paquete.vencimiento || 'N/A'}</span>
            </div>
          </div>
        </div>
    `;
    
    // Agregar mensaje informativo
    html += `
      <div class="info-message">
        <i class="fas fa-info-circle"></i>
        <p>Esta es la información disponible del paquete comprado. Para ver más detalles, consulta la sección de paquetes en el menú principal.</p>
      </div>
    `;
    
    html += '</div>';
    modalBody.innerHTML = html;
    
  } catch (error) {
    console.error('Error cargando detalle de paquete:', error);
    modalBody.innerHTML = `
      <div class="error-message">
        <i class="fas fa-exclamation-triangle"></i>
        <p>Error al cargar los detalles del paquete: ${error.message}</p>
      </div>
    `;
  }
}


// Función para ver detalle de usuario (llamada desde JSP)
function verDetalleUsuario(nickname) {
  mostrarDetalleUsuario(nickname);
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
  
  // Cerrar modal al hacer clic fuera de él
  const modal = document.getElementById('modal-detalle');
  if (modal) {
    modal.addEventListener('click', function(e) {
      if (e.target === modal) {
        cerrarModalDetalle();
      }
    });
  }
  
  // Inicializar la página
  cargarUsuarios();
});

// Función de inicialización para el sistema de vistas
window.initConsultaUsuario = function() {
  cargarUsuarios();
};


// Obtener usuario logueado desde backend
async function obtenerUsuarioLogueado() {
  try {
    const resp = await fetch("/VolandoUy-WebApp/api/usuarios/perfil", { credentials: "include" });
    if (resp.ok) {
      return await resp.json(); // objeto con nickname, tipo, seguidos, etc.
    }
  } catch (e) {
    console.error("No se pudo obtener usuario logueado:", e);
  }
  return null;
}



// Listener global para seguir/dejar de seguir
document.addEventListener('click', async function(e) {
  if (e.target.classList.contains('follow-btn')) {
    e.stopPropagation(); // evita que se dispare el click de la card

    const btn = e.target;
    const nickname = btn.dataset.nickname;
    const isFollowing = btn.dataset.following === 'true';

    try {
      if (isFollowing) {
        const resp = await fetch(`/VolandoUy-WebApp/api/seguidores/${nickname}/dejar`, {
          method: 'POST',
          credentials: 'include'
        });
        if (resp.ok) {
          btn.textContent = 'Seguir';
          btn.dataset.following = 'false';
          btn.classList.remove('dejar');
          btn.classList.add('seguir');
        }
      } else {
        const resp = await fetch(`/VolandoUy-WebApp/api/seguidores/${nickname}/seguir`, {
          method: 'POST',
          credentials: 'include'
        });
        if (resp.ok) {
          btn.textContent = 'Dejar de seguir';
          btn.dataset.following = 'true';
          btn.classList.remove('seguir');
          btn.classList.add('dejar');
        }
      }
    } catch (error) {
      console.error('Error al cambiar estado de seguimiento:', error);
    }
  }
});

// Exportar funciones para uso global
window.cerrarModalDetalle = cerrarModalDetalle;
window.mostrarDetalleRutaEnModal = mostrarDetalleRutaEnModal;
window.mostrarDetalleVueloEnModal = mostrarDetalleVueloEnModal;