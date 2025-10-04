// script/controladorVista.js
const controladorDeVista = {
  cargar: function(url) {
    // Mostrar indicador de carga
    const contenedor = document.getElementById('main-content');
    if (!contenedor) {
      console.error('No se encontró el contenedor #main-content');
      return;
    }

    // Aplicar clase de carga para evitar parpadeo
    contenedor.classList.add('loading');

    fetch(url)
      .then(res => res.text())
      .then(html => {
        // Insertar HTML sin mostrar aún
        contenedor.innerHTML = html;

        // Esperar un frame para que el CSS se aplique
        requestAnimationFrame(() => {
          // Remover clase de carga
          contenedor.classList.remove('loading');

          // Re-registrar event listeners después de cambiar el contenido
          registrarEventListeners();

          // Si la vista es consultaRutaVuelo, cargar su JS y ejecutar init
          if (url === 'consultaRutaVuelo.html') {
            const yaCargado = Array.from(document.scripts)
              .some(s => s.src.includes('script/consultaRutaVuelo.js'));

            const ejecutarInit = () => {
              setTimeout(() => {
                if (typeof window.initConsultaRutaVuelo === 'function') {
                  window.initConsultaRutaVuelo();
                } else if (typeof cargarRutas === 'function') {
                  cargarRutas();
                } else {
                  console.warn('initConsultaRutaVuelo no está disponible.');
                }
              }, 100);
            };

            if (yaCargado) {
              ejecutarInit();
            } else {
              const script = document.createElement('script');
              script.src = 'script/consultaRutaVuelo.js';
              script.defer = true;
              script.onload = ejecutarInit;
              document.body.appendChild(script);
            }
          }

          // Si la vista es consultaVuelo, cargar su JS y ejecutar init
          if (url === 'consultaVuelo.html') {
            const yaCargado = Array.from(document.scripts)
              .some(s => s.src.includes('script/consultaVuelo.js'));
            const ejecutarInit = () => {
              setTimeout(() => {
                if (typeof window.initConsultaVuelo === 'function') {
                  window.initConsultaVuelo();
                }
              }, 100);
            };
            if (yaCargado) {
              ejecutarInit();
            } else {
              const script = document.createElement('script');
              script.src = 'script/consultaVuelo.js';
              script.defer = true;
              script.onload = ejecutarInit;
              document.body.appendChild(script);
            }
          }

          // Si la vista es consultaUsuario, cargar su JS y ejecutar init
          if (url === 'consultaUsuario.html') {
            const yaCargado = Array.from(document.scripts)
              .some(s => s.src.includes('script/consultaUsuario.js'));

            const ejecutarInit = () => {
              setTimeout(() => {
                if (typeof window.initConsultaUsuario === 'function') {
                  window.initConsultaUsuario();
                } else if (typeof cargarUsuarios === 'function') {
                  cargarUsuarios();
                } else {
                  console.warn('initConsultaUsuario no está disponible.');
                }
              }, 100);
            };

            if (yaCargado) {
              ejecutarInit();
            } else {
              const script = document.createElement('script');
              script.src = 'script/consultaUsuario.js';
              script.defer = true;
              script.onload = ejecutarInit;
              document.body.appendChild(script);
            }
          }

          // Si la vista es consultaPaquete, cargar su JS y ejecutar init
          if (url === 'consultaPaquete.html') {
            const yaCargado = Array.from(document.scripts)
              .some(s => s.src.includes('script/consultaPaquete.js'));

            const ejecutarInit = () => {
              setTimeout(() => {
                if (typeof window.initConsultaPaquete === 'function') {
                  window.initConsultaPaquete();
                } else if (typeof cargarPaquetes === 'function') {
                  cargarPaquetes();
                } else {
                  console.warn('initConsultaPaquete no está disponible.');
                }
              }, 100);
            };

            if (yaCargado) {
              ejecutarInit();
            } else {
              const script = document.createElement('script');
              script.src = 'script/consultaPaquete.js';
              script.defer = true;
              script.onload = ejecutarInit;
              document.body.appendChild(script);
            }
          }

          // Si la vista es modificarUsuario, cargar su JS y ejecutar init
          if (url === 'modificarUsuario.html') {
            const yaCargado = Array.from(document.scripts)
              .some(s => s.src.includes('script/modificarUsuario.js'));

            const ejecutarInit = () => {
              setTimeout(() => {
                if (typeof window.initModificarUsuario === 'function') {
                  window.initModificarUsuario();
                } else {
                  console.warn('initModificarUsuario no está disponible.');
                }
              }, 100);
            };

            if (yaCargado) {
              ejecutarInit();
            } else {
              const script = document.createElement('script');
              script.src = 'script/modificarUsuario.js';
              script.defer = true;
              script.onload = ejecutarInit;
              document.body.appendChild(script);
            }
          }

          // Si la vista es inicioSesión, no necesita JS adicional
          if (url === 'inicioSesión.html') {
            console.log('Página de inicio de sesión cargada');
          }

          // Si la vista es reserva.html, cargar su JS y ejecutar init (patrón igual al de arriba)
          if (url === 'reserva.html' || url === 'reservaVuelo.html') {
            const yaCargado = Array.from(document.scripts)
              .some(s => s.src.includes('script/reserva.js'));

            const ejecutarInit = () => {
              setTimeout(() => {
                if (typeof window.initReserva === 'function') {
                  window.initReserva();
                } else {
                  // el script también se auto-inicializa; esto es solo informativo
                  console.log('reserva script cargado (ejecutarInit).');
                }
              }, 100);
            };

            if (yaCargado) {
              ejecutarInit();
            } else {
              const script = document.createElement('script');
              script.src = 'script/reserva.js';
              script.defer = true;
              script.onload = ejecutarInit;
              document.body.appendChild(script);
            }
          }
          if (url === 'consultaVuelo.html') {
  const yaCargado = Array.from(document.scripts)
    .some(s => s.src.includes('script/consultaVuelo.js'));

  const ejecutarInit = () => {
    setTimeout(() => {
      if (typeof window.initConsultaVuelo === 'function') {
        window.initConsultaVuelo();
      } else {
        console.warn('initConsultaVuelo no está disponible.');
      }
    }, 100);
  };

  if (yaCargado) {
    ejecutarInit();
  } else {
    const script = document.createElement('script');
    script.src = 'script/consultaVuelo.js';
    script.defer = true;
    script.onload = ejecutarInit;
    document.body.appendChild(script);
  }
}

        });
      })
      .catch(err => {
        console.error('Error al cargar vista:', err);
        contenedor.classList.remove('loading');
      });
  }
};

// Registrar event listeners (delegación) — solo se engancha una vez para evitar duplicados
function registrarEventListeners() {
  if (window.__vistaDelegationAttached) return;
  window.__vistaDelegationAttached = true;

  document.addEventListener('click', function (e) {
    const enlace = e.target.closest && e.target.closest('a[data-vista]');
    if (!enlace) return;
    e.preventDefault();
    const vista = enlace.getAttribute('data-vista');
    if (vista) controladorDeVista.cargar(vista);
  });
}

document.addEventListener('DOMContentLoaded', function() {
  // Cargar vista inicial
  controladorDeVista.cargar('inicio.html');

  // Registrar event listeners iniciales (delegación)
  registrarEventListeners();
});
