const controladorDeVista = {
  cargar: function(url) {
    fetch(url)
      .then(res => res.text())
      .then(html => {
        document.getElementById('main-content').innerHTML = html;
        // Llama a cargarRutas si corresponde
        if (url === 'consultaRutaVuelo.html' && typeof cargarRutas === 'function') {
          cargarRutas();
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
      e.preventDefault(); // Evita que el enlace recargue la página
      const vista = this.getAttribute('data-vista');
      controladorDeVista.cargar(vista);
    });
  });
});
// Fin del archivo