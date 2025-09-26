// script/controladorVista.js
const controladorDeVista = {
  cargar: function(url) {
    fetch(url)
      .then(res => res.text())
      .then(html => {
        const contenedor = document.getElementById('main-content');
        if (!contenedor) {
          console.error('No se encontró el contenedor #main-content');
          return;
        }

        contenedor.innerHTML = html;

        // Si la vista es consultaRutaVuelo, cargar su JS y ejecutar init
        if (url === 'consultaRutaVuelo.html') {
          // ¿ya está el script cargado?
          const yaCargado = Array.from(document.scripts)
            .some(s => s.src.includes('script/consultaRutaVuelo.js'));

          const ejecutarInit = () => {
            if (typeof window.initConsultaRutaVuelo === 'function') {
              window.initConsultaRutaVuelo();
            } else {
              console.warn('initConsultaRutaVuelo no está disponible.');
            }
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
      })
      .catch(err => console.error('Error al cargar vista:', err));
  }
};

document.addEventListener('DOMContentLoaded', function() {
  // Cargar vista inicial
  controladorDeVista.cargar('inicio.html');

  // Interceptar clics en enlaces con data-vista
  document.querySelectorAll('a[data-vista]').forEach(enlace => {
    enlace.addEventListener('click', function(e) {
      e.preventDefault();
      const vista = this.getAttribute('data-vista');
      controladorDeVista.cargar(vista);
    });
  });
});