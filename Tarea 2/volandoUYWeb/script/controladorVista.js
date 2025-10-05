// script/controladorVista.js
const controladorDeVista = {
  cargar: function (url) {
    // Mostrar indicador de carga
    const contenedor = document.getElementById("main-content");
    if (!contenedor) {
      console.error("No se encontró el contenedor #main-content");
      return;
    }

    // Aplicar clase de carga para evitar parpadeo
    contenedor.classList.add("loading");

    fetch(url)
      .then((res) => res.text())
      .then((html) => {
        // Insertar HTML sin mostrar aún
        contenedor.innerHTML = html;

        // Limpiar clases específicas de páginas
        contenedor.classList.remove("login-page", "register-page");

        // Esperar un frame para que el CSS se aplique
        requestAnimationFrame(() => {
          // Remover clase de carga
          contenedor.classList.remove("loading");

          // Re-registrar event listeners después de cambiar el contenido
          registrarEventListeners();

          if (url === "consultaReserva.html") {
            const cargarScript = (src, callback) => {
              // 1. Buscar si ya existe un <script> con ese src
              const prev = document.querySelector(`script[src="${src}"]`);
              if (prev) {
                prev.remove(); // lo eliminamos para evitar duplicados
              }

              // 2. Crear el nuevo script
              const script = document.createElement("script");
              script.src = src;
              script.defer = true;
              script.onload = callback;
              document.body.appendChild(script);
            };

            const ejecutarInit = () => {
              // Esperar a que el DOM esté completamente listo y los elementos existan
              const esperarElemento = () => {
                const consultaForm = document.querySelector('.search-box') || document.getElementById('form-consulta-reserva');
                if (consultaForm) {
                  if (typeof window.initConsultaReserva === "function") {
                    window.initConsultaReserva();
                  } else {
                    console.warn("initConsultaReserva no está disponible.");
                  }
                } else {
                  // Si el elemento no existe, esperar un poco más
                  setTimeout(esperarElemento, 50);
                }
              };
              setTimeout(esperarElemento, 100);
            };

            cargarScript("script/consultaReserva.js", ejecutarInit);
          }

          if (url === "consultaRutaVuelo.html") {
            const ejecutarInit = () => {
              // Esperar a que el DOM esté completamente listo y los elementos existan
              const esperarElemento = () => {
                const listaRutas = document.getElementById("lista-rutas") || document.querySelector(".rutas-grid");
                if (listaRutas) {
                  if (typeof window.initConsultaRutaVuelo === "function") {
                    window.initConsultaRutaVuelo();
                  } else if (typeof cargarRutas === "function") {
                    cargarRutas();
                  } else {
                    console.warn("initConsultaRutaVuelo no está disponible.");
                  }
                } else {
                  // Si el elemento no existe, esperar un poco más
                  setTimeout(esperarElemento, 50);
                }
              };
              setTimeout(esperarElemento, 100);
            };

            // Eliminar script anterior si existe
            const prev = document.querySelector(
              'script[src="script/consultaRutaVuelo.js"]'
            );
            if (prev) prev.remove();

            // Inyectar script nuevo
            const script = document.createElement("script");
            script.src = "script/consultaRutaVuelo.js";
            script.defer = true;
            script.onload = ejecutarInit;
            document.body.appendChild(script);
          }
          
          if (url === "registrarUsuario.html") {
            // Agregar clase específica para el fondo del registro
            contenedor.className = "register-page";
            
            const ejecutarInit = () => {
              // Esperar a que el DOM esté completamente listo y los elementos existan
              const esperarElemento = () => {
                const registrarForm = document.querySelector('.form-box') || document.getElementById('form-registro');
                if (registrarForm) {
                  if (typeof window.initregistrarUsuario === "function") {
                    window.initregistrarUsuario();
                  } else if (typeof cargarRutas === "function") {
                    cargarRutas();
                  } else {
                    console.warn("initregistrarUsuario no está disponible.");
                  }
                } else {
                  // Si el elemento no existe, esperar un poco más
                  setTimeout(esperarElemento, 50);
                }
              };
              setTimeout(esperarElemento, 100);
            };

            // Eliminar script anterior si existe
            const prev = document.querySelector(
              'script[src="script/registrarUsuario.js"]'
            );
            if (prev) prev.remove();

            // Inyectar script nuevo
            const script = document.createElement("script");
            script.src = "script/registrarUsuario.js";
            script.defer = true;
            script.onload = ejecutarInit;
            document.body.appendChild(script);
          }

          // Si la vista es consultaVuelo, cargar su JS y ejecutar init
          if (url === "consultaVuelo.html") {
            const yaCargado = Array.from(document.scripts).some((s) =>
              s.src.includes("script/consultaVuelo.js")
            );
            const ejecutarInit = () => {
              // Esperar a que el DOM esté completamente listo y los elementos existan
              const esperarElemento = () => {
                const listaVuelos = document.getElementById("lista-vuelos") || document.querySelector(".vuelos-grid");
                if (listaVuelos) {
                  if (typeof window.initConsultaVuelo === "function") {
                    window.initConsultaVuelo();
                  } else {
                    console.warn("initConsultaVuelo no está disponible.");
                  }
                } else {
                  // Si el elemento no existe, esperar un poco más
                  setTimeout(esperarElemento, 50);
                }
              };
              setTimeout(esperarElemento, 100);
            };
            if (yaCargado) {
              ejecutarInit();
            } else {
              const script = document.createElement("script");
              script.src = "script/consultaVuelo.js";
              script.defer = true;
              script.onload = ejecutarInit;
              document.body.appendChild(script);
            }
          }

          // Si la vista es consultaUsuario, cargar su JS y ejecutar init
          if (url === "consultaUsuario.html") {
            const yaCargado = Array.from(document.scripts).some((s) =>
              s.src.includes("script/consultaUsuario.js")
            );

            const ejecutarInit = () => {
              // Esperar a que el DOM esté completamente listo y los elementos existan
              const esperarElemento = () => {
                const listaUsuarios = document.getElementById("lista-usuarios");
                if (listaUsuarios) {
                  if (typeof window.initConsultaUsuario === "function") {
                    window.initConsultaUsuario();
                  } else if (typeof cargarUsuarios === "function") {
                    cargarUsuarios();
                  } else {
                    console.warn("initConsultaUsuario no está disponible.");
                  }
                } else {
                  // Si el elemento no existe, esperar un poco más
                  setTimeout(esperarElemento, 50);
                }
              };
              setTimeout(esperarElemento, 100);
            };

            if (yaCargado) {
              ejecutarInit();
            } else {
              const script = document.createElement("script");
              script.src = "script/consultaUsuario.js";
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
              // Esperar a que el DOM esté completamente listo y los elementos existan
              const esperarElemento = () => {
                const listaPaquetes = document.getElementById("lista-paquetes") || document.querySelector(".paquetes-grid");
                if (listaPaquetes) {
                  if (typeof window.initConsultaPaquete === 'function') {
                    window.initConsultaPaquete();
                  } else if (typeof cargarPaquetes === 'function') {
                    cargarPaquetes();
                  } else {
                    console.warn('initConsultaPaquete no está disponible.');
                  }
                } else {
                  // Si el elemento no existe, esperar un poco más
                  setTimeout(esperarElemento, 50);
                }
              };
              setTimeout(esperarElemento, 100);
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
          if (url === "modificarUsuario.html") {
            const yaCargado = Array.from(document.scripts).some((s) =>
              s.src.includes("script/modificarUsuario.js")
            );

            const ejecutarInit = () => {
              // Esperar a que el DOM esté completamente listo y los elementos existan
              const esperarElemento = () => {
                const modificarForm = document.querySelector('.form-box') || document.getElementById('form-modificar');
                if (modificarForm) {
                  if (typeof window.initModificarUsuario === "function") {
                    window.initModificarUsuario();
                  } else {
                    console.warn("initModificarUsuario no está disponible.");
                  }
                } else {
                  // Si el elemento no existe, esperar un poco más
                  setTimeout(esperarElemento, 50);
                }
              };
              setTimeout(esperarElemento, 100);
            };

            if (yaCargado) {
              ejecutarInit();
            } else {
              const script = document.createElement("script");
              script.src = "script/modificarUsuario.js";
              script.defer = true;
              script.onload = ejecutarInit;
              document.body.appendChild(script);
            }
          }

          // Si la vista es compraPaquete, cargar su JS y ejecutar init
          if (url === "compraPaquete.html") {
            const yaCargado = Array.from(document.scripts).some((s) =>
              s.src.includes("script/compraPaquete.js")
            );

            const ejecutarInit = () => {
              // Esperar a que el DOM esté completamente listo y los elementos existan
              const esperarElemento = () => {
                const listaPaquetes = document.getElementById("lista-paquetes-compra");
                if (listaPaquetes) {
                  if (typeof window.initCompraPaquete === "function") {
                    window.initCompraPaquete();
                  } else {
                    console.warn("initCompraPaquete no está disponible.");
                  }
                } else {
                  // Si el elemento no existe, esperar un poco más
                  setTimeout(esperarElemento, 50);
                }
              };
              setTimeout(esperarElemento, 100);
            };

            if (yaCargado) {
              ejecutarInit();
            } else {
              const script = document.createElement("script");
              script.src = "script/compraPaquete.js";
              script.defer = true;
              script.onload = ejecutarInit;
              document.body.appendChild(script);
            }
          }

          // Si la vista es inicioSesión, no necesita JS adicional
          if (url === "inicioSesión.html") {
            console.log("Página de inicio de sesión cargada");
            // Agregar clase específica para el fondo del login
            contenedor.className = "login-page";
          }

          // Si la vista es reserva.html, cargar su JS y ejecutar init (patrón igual al de arriba)
          if (url === "reserva.html" || url === "reservaVuelo.html") {
            const yaCargado = Array.from(document.scripts).some((s) =>
              s.src.includes("script/reserva.js")
            );

            const ejecutarInit = () => {
              // Esperar a que el DOM esté completamente listo y los elementos existan
              const esperarElemento = () => {
                const reservaForm = document.querySelector('.reserve-section') || document.getElementById('form-reserva');
                if (reservaForm) {
                  if (typeof window.initReserva === "function") {
                    window.initReserva();
                  } else {
                    console.log("reserva script cargado (ejecutarInit).");
                  }
                } else {
                  // Si el elemento no existe, esperar un poco más
                  setTimeout(esperarElemento, 50);
                }
              };
              setTimeout(esperarElemento, 100);
            };

            if (yaCargado) {
              ejecutarInit();
            } else {
              const script = document.createElement("script");
              script.src = "script/reserva.js";
              script.defer = true;
              script.onload = ejecutarInit;
              document.body.appendChild(script);
            }
          }
        });
      })
      .catch((err) => {
        console.error("Error al cargar vista:", err);
        contenedor.classList.remove("loading");
      });
  },
};

// Registrar event listeners (delegación) — solo se engancha una vez para evitar duplicados
function registrarEventListeners() {
  if (window.__vistaDelegationAttached) return;
  window.__vistaDelegationAttached = true;

  document.addEventListener("click", function (e) {
    const enlace = e.target.closest && e.target.closest("a[data-vista]");
    if (!enlace) return;
    e.preventDefault();
    const vista = enlace.getAttribute("data-vista");
    if (vista) controladorDeVista.cargar(vista);
  });
}

document.addEventListener("DOMContentLoaded", function () {
  // Cargar vista inicial
  controladorDeVista.cargar("inicio.html");

  // Registrar event listeners iniciales (delegación)
  registrarEventListeners();
});
