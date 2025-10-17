<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="contenido-principal">
  <section class="search-box">
    <div class="search-header">
      <h2>Consulta de Ruta de Vuelo</h2>
      <p class="descripcion-consulta">Explora las rutas de vuelo disponibles y encuentra la conexión perfecta para tu próximo viaje. Todas las rutas mostradas están confirmadas y operativas.</p>
    </div>


    <div class="form-group">
       <label for="selectAerolinea">Seleccionar una aerolinea</label>
                    <select id="selectAerolinea" name="aerolineas" required>
                       <option value="">-- Elegir aerolinea --</option>
                       <c:forEach var="aerolinea" items="${aerolineas}">
                           <option value="${aerolinea.id}">${aerolinea.nombre}</option>
                       </c:forEach>
                     </select>
                     <input type="hidden" id="aerolineasNombre" name="aerolineasNombre" value="">
                   </div>

      <div class="filtro-group">
        <label for="select-categoria">Categoría:</label>
        <select id="select-categoria">
          <option value="">Todas las categorías</option>
        </select>
      </div>

      <div class="filtro-group">
        <label for="buscador-nombre">Buscar por nombre:</label>
        <input type="text" id="buscador-nombre" placeholder="Ej: Montevideo - Madrid">
      </div>
    </form>
  </section>
</div>

<div class="form-group">
        <label for="selectRuta">Seleccionar ruta de vuelo</label>
        <select id="selectRuta" name="rutasvuelo" required>
          <option value="">-- Elegir ruta --</option>
          <c:forEach var="ruta" items="${rutas}">
              <option value="${ruta.id}">${ruta.nombre}</option>
          </c:forEach>
        </select>
        <input type="hidden" id="rutasvueloNombre" name="rutasvueloNombre" value="">
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

<!-- Detalle de ruta (puede usarse en el futuro si querés mostrar más info al hacer clic) -->
<section class="detalle-ruta" style="display: none">
  <h2>Detalle de la Ruta</h2>
  <p><strong>Origen:</strong> <span id="detalle-origen"></span></p>
  <p><strong>Destino:</strong> <span id="detalle-destino"></span></p>
  <p><strong>Fechas:</strong> <span id="detalle-fechas"></span></p>
  <h3>Vuelos asociados</h3>
  <ul id="detalle-vuelos"></ul>
  <h3>Paquete asociado</h3>
  <p id="detalle-paquete"></p>
</section>


<script>
  (async function loadAerolineas() {
    const select = document.getElementById('selectAerolinea');
    if (!select) return;

    try {
      const resp = await fetch('<%= request.getContextPath() %>/api/aerolineas', {
        method: 'GET',
        credentials: 'include'
      });

      if (!resp.ok) {
        console.warn('No se pudieron cargar aerolíneas:', resp.status);
        return;
      }

      const data = await resp.json();
      // La API puede devolver un array directo o un objeto { aerolineas: [...] }
      const aerolineas = Array.isArray(data) ? data : (Array.isArray(data.aerolineas) ? data.aerolineas : []);

      // Mantener el placeholder
      const placeholder = select.querySelector('option[value=""]');
      select.innerHTML = '';
      if (placeholder) select.appendChild(placeholder);

      aerolineas.forEach(a => {
        const opt = document.createElement('option');
        const idVal = a.id ?? a.codigo ?? a.nickname ?? "";
        const nombreVal = a.nombre ?? a.razonSocial ?? a.nickname ?? "";

        opt.value = idVal !== "" ? idVal : nombreVal;
        opt.textContent = nombreVal || idVal || 'Aerolínea sin nombre';
        opt.dataset.nombre = nombreVal;

        select.appendChild(opt);
      });

      // Actualizar campo oculto con el nombre seleccionado
      select.addEventListener('change', function () {
        const selectedOption = this.options[this.selectedIndex];
        document.getElementById('aerolineasNombre').value = selectedOption.dataset.nombre || '';
      });

    } catch (err) {
      console.error('Error cargando aerolíneas:', err);
    }
  })();
</script>

<script>
  (async function loadRutas() {
    const select = document.getElementById('selectRuta');
    if (!select) return;

    try {
      const resp = await fetch('<%= request.getContextPath() %>/api/rutas', {
        method: 'GET',
        credentials: 'include'
      });

      if (!resp.ok) {
        console.warn('No se pudieron cargar rutas: ', resp.status);
        return;
      }

      const data = await resp.json();

      // La API podría devolver directamente un array o un objeto { rutas: [...] }
      const rutas = Array.isArray(data) ? data : (Array.isArray(data.rutas) ? data.rutas : []);

      // Depuración (quitar/ocultar en producción)
      console.debug('Rutas cargadas:', rutas);

      // Limpiar opciones excepto la primera (placeholder)
      // Más seguro: dejar solo el primer option
      const placeholder = select.querySelector('option[value=""]');
      select.innerHTML = '';
      if (placeholder) select.appendChild(placeholder);

      rutas.forEach(r => {
        // r puede tener distintas propiedades según tu API: id, nombre, nombreRuta, etc.
        const opt = document.createElement('option');

        // Evitar value = undefined: elegir la mejor propiedad disponible
        const idVal = r.id ?? r.idRuta ?? r.codigo ?? "";
        const nombreVal = r.nombre ?? r.nombreRuta ?? (r.origen && r.destino ? `${r.origen} - ${r.destino}` : "");

        // Si la lógica del back espera el NOMBRE de la ruta, podríamos setear value=nombreVal.
        // Para ser conservadores, dejamos value = idVal si existe, si no usamos nombreVal.
        opt.value = (idVal !== "") ? idVal : (nombreVal !== "" ? nombreVal : "");

        // Mostrar texto legible
        opt.textContent = nombreVal || idVal || 'Ruta sin nombre';

        // Guardar el nombre en data-* por si querés usarlo en cliente
        opt.dataset.nombre = nombreVal;

        select.appendChild(opt);
      });

    } catch (err) {
      console.error('Error cargando rutas:', err);
    }
  })();
</script>



