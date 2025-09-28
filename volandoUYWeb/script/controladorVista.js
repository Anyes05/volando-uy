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
          // ¿ya está el script cargado?
          const yaCargado = Array.from(document.scripts)
            .some(s => s.src.includes('script/consultaRutaVuelo.js'));

          const ejecutarInit = () => {
            // Esperar un poco más para que el DOM esté completamente listo
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
          // ¿ya está el script cargado?
          const yaCargado = Array.from(document.scripts)
            .some(s => s.src.includes('script/consultaUsuario.js'));

          const ejecutarInit = () => {
            // Esperar un poco más para que el DOM esté completamente listo
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

        // Si la vista es inicioSesión, no necesita JS adicional
        if (url === 'inicioSesión.html') {
          console.log('Página de inicio de sesión cargada');
        }
        });
      })
      .catch(err => {
        console.error('Error al cargar vista:', err);
        contenedor.classList.remove('loading');
      });
  }
};

// Función para registrar event listeners
function registrarEventListeners() {
  // Interceptar clics en enlaces con data-vista
  document.querySelectorAll('a[data-vista]').forEach(enlace => {
    enlace.addEventListener('click', function(e) {
      e.preventDefault();
      const vista = this.getAttribute('data-vista');
      controladorDeVista.cargar(vista);
    });
  });
}

document.addEventListener('DOMContentLoaded', function() {
  // Cargar vista inicial
  controladorDeVista.cargar('inicio.html');

  // Registrar event listeners iniciales
  registrarEventListeners();
});