<section class="form-section-pro">
  <div class="form-card-pro">
    <h2> Alta de Vuelo</h2>
    <p class="form-subtitle">Ingrese los datos del vuelo</p>

    <form id="formAltaVuelo" class="form-grid" enctype="multipart/form-data" method="post" novalidate>
      <div class="form-group">
        <label for="nombre">Nombre del vuelo</label>
        <input type="text" id="nombre" name="nombre" required>
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

      <div class="form-group">
              <label for="fechaVuelo">Fecha de salida del Vuelo</label>
              <input type="date" id="fechaVuelo" name="fechaVuelo" required>
            </div>

      <div class="form-group">
              <label for="hora">Hora de salida</label>
              <!-- name debe coincidir con lo que espera el controlador: horaSalida -->
              <input type="time" id="hora" name="horaSalida" required>
      </div>

      <!-- Duración: un solo campo HH:MM -->
      <div class="form-group">
        <label for="duracion">Duración (HH:MM)</label>
        <input type="text" id="duracion" name="duracion" pattern="^\d{1,2}:\d{2}$" placeholder="02:30" required>
        <small class="hint">Formato: horas: minutos, p.ej. 02:30</small>
      </div>

      <div class="form-group">
        <label for="fechaAlta">Fecha de alta</label>
        <input type="date" id="fechaAlta" name="fechaAlta" required>
      </div>

      <div class="form-group">
        <label for="cantidadAsientosTuristas">Cantidad de Asientos Turistas</label>
        <input type="number" id="cantidadAsientosTuristas" name="cantidadAsientosTuristas" min="1" required>
      </div>

      <div class="form-group">
        <label for="cantidadAsientosEjecutivo">Cantidad de Asientos Ejecutivos</label>
        <input type="number" id="cantidadAsientosEjecutivo" name="cantidadAsientosEjecutivo" min="1" required>
      </div>

      <div class="form-group full-width">
        <label for="foto">Imagen de portada</label>
        <input type="file" id="foto" name="foto" accept="image/*">
      </div>

      <div id="formMessage" class="form-message full-width" style="display:none;color:#c00;"></div>

      <div class="form-actions full-width">
        <button id="submitBtn" type="submit" class="btn-pro"> Guardar Vuelo</button>
      </div>
    </form>
  </div>
</section>

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


<script>
  document.getElementById("fechaAlta").valueAsDate = new Date();

  const form = document.getElementById("formAltaVuelo");
  const submitBtn = document.getElementById("submitBtn");
  const msgDiv = document.getElementById("formMessage");

  function showMessage(text, isError = true) {
    msgDiv.style.display = "block";
    msgDiv.style.color = isError ? "#c00" : "#080";
    msgDiv.textContent = text;
  }

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    msgDiv.style.display = "none";

    const nombre = form.querySelector("[name='nombre']").value.trim();
    const duracion = form.querySelector("[name='duracion']").value.trim();
    const fecha = form.querySelector("[name='fechaAlta']").value;
    const rutaSeleccionada = form.querySelector("[name='rutasvuelo']").value;
    const horaSalida = form.querySelector("[name='horaSalida']").value;
    const cantidadTurista = form.querySelector("[name='cantidadAsientosTuristas']").value;
    const cantidadEjecutivo = form.querySelector("[name='cantidadAsientosEjecutivo']").value;

    const duracionRegex = /^\d{1,2}:\d{2}$/;
    if (!nombre || !duracion || !duracionRegex.test(duracion) || !fecha || !cantidadTurista || !cantidadEjecutivo) {
      showMessage("Complete los campos obligatorios y use formato de duración HH:MM");
      return;
    }

    const formData = new FormData(form);

    submitBtn.disabled = true;
    const originalText = submitBtn.textContent;
    submitBtn.textContent = "Guardando...";

    try {
      const response = await fetch("<%= request.getContextPath() %>/api/vuelos", {
        method: "POST",
        body: formData,
        credentials: "include"
      });

      let result = {};
      try { result = await response.json(); } catch (err) {}

      if (response.ok) {
        alert(result.mensaje || "Vuelo creado exitosamente.");
        form.reset();
        document.getElementById("fechaAlta").valueAsDate = new Date();
        msgDiv.style.display = "none";
      } else {
        alert(result.error || ("Error al crear vuelo: " + response.status));
      }
    } catch (error) {
      alert("Error al crear vuelo: " + (error.message || error));
    } finally {
      submitBtn.disabled = false;
      submitBtn.textContent = originalText;
    }
  });
</script>