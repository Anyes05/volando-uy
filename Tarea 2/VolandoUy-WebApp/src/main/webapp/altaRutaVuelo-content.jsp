<!-- language: html -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="form-section-pro">
  <div class="form-card-pro">
    <h2> Alta de Ruta de Vuelo</h2>
    <p class="form-subtitle">Ingrese los datos de la nueva ruta</p>

    <form id="formAltaRutaPro" class="form-grid" enctype="multipart/form-data" method="post" novalidate>
      <!-- Nombre y Descripción corta -->
      <div class="form-group">
        <label for="nombre">Nombre de la ruta</label>
        <input type="text" id="nombre" name="nombre" placeholder="Montevideo - Madrid" required>
      </div>
      <div class="form-group">
        <label for="descripcionCorta">Descripción corta</label>
        <input type="text" id="descripcionCorta" name="descripcionCorta" maxlength="120" placeholder="Máx. 120 caracteres" required>
      </div>

      <!-- Descripción -->
      <div class="form-group full-width">
        <label for="descripcion">Descripción</label>
        <textarea id="descripcion" name="descripcion" rows="4" placeholder="Detalles de la ruta, servicios incluidos..." required></textarea>
      </div>

      <!-- Hora y Fecha -->
      <div class="form-group">
        <label for="hora">Hora de salida</label>
        <!-- name debe coincidir con lo que espera el controlador: horaSalida -->
        <input type="time" id="hora" name="horaSalida" required>
      </div>
      <div class="form-group">
        <label for="fechaAlta">Fecha de alta</label>
        <input type="date" id="fechaAlta" name="fechaAlta">
      </div>

      <!-- Costos -->
      <div class="form-group">
        <label for="costoTurista">Costo Turista</label>
        <input type="number" id="costoTurista" name="costoTurista" min="0" step="0.01" placeholder="USD" required>
      </div>
      <div class="form-group">
        <label for="costoEjecutivo">Costo Ejecutivo</label>
        <input type="number" id="costoEjecutivo" name="costoEjecutivo" min="0" step="0.01" placeholder="USD" required>
      </div>
      <div class="form-group full-width">
        <label for="costoEquipaje">Costo Equipaje Extra</label>
        <input type="number" id="costoEquipaje" name="costoEquipaje" min="0" step="0.01" placeholder="USD">
      </div>

      <!-- Ciudades -->
      <div class="form-group">
        <label for="ciudadOrigen">Ciudad Origen</label>
        <select id="ciudadOrigen" name="ciudadOrigen" required>
          <option value="">Seleccione</option>
          <option>Montevideo</option>
          <option>Buenos Aires</option>
          <option>San Pablo</option>
        </select>
      </div>
      <div class="form-group">
        <label for="ciudadDestino">Ciudad Destino</label>
        <select id="ciudadDestino" name="ciudadDestino" required>
          <option value="">Seleccione</option>
          <option>Madrid</option>
          <option>Miami</option>
          <option>Santiago</option>
        </select>
      </div>

      <!-- Categorías -->
      <div class="form-group full-width">
        <label for="categorias">Categorías</label>
        <select id="categorias" name="categorias" multiple>
          <option value="Internacional">Internacional</option>
          <option value="Regional">Regional</option>
        </select>
        <small class="hint">Puede seleccionar varias</small>
      </div>

      <!-- Foto -->
      <div class="form-group full-width">
        <label for="foto">Imagen de portada</label>
        <input type="file" id="foto" name="foto" accept="image/*">
      </div>

      <!-- Mensaje -->
      <div id="formMessage" class="form-message full-width" style="display:none;color:#c00;"></div>

      <!-- Botón -->
      <div class="form-actions full-width">
        <button id="submitBtn" type="submit" class="btn-pro"> Guardar Ruta</button>
      </div>
    </form>
  </div>
</section>

<script>
  // poner fecha por defecto a hoy
  document.getElementById("fechaAlta").valueAsDate = new Date();

  const form = document.getElementById("formAltaRutaPro");
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

    // -- OBLIGATORIO: actualizar hidden con el texto (nombre) de la opción seleccionada --
    const select = form.querySelector("[name='rutasvuelo']");
    const hiddenNombre = document.getElementById('rutasvueloNombre');
    if (select) {
      const selectedOption = select.options[select.selectedIndex];
      // preferimos data-nombre si existe, sino textContent
      const nombreDeRuta = selectedOption ? (selectedOption.dataset.nombre || selectedOption.textContent || "") : "";
      if (hiddenNombre) hiddenNombre.value = nombreDeRuta;
    }

    // Ahora creamos formData (incluye el hidden con rutasvueloNombre)
    const formData = new FormData(form);

    // ... el resto de tu validación y envío sigue igual
    const nombre = form.querySelector("[name='nombre']").value.trim();
    const duracion = form.querySelector("[name='duracion']").value.trim();
    const fecha = form.querySelector("[name='fechaAlta']").value;
    const rutaSeleccionada = form.querySelector("[name='rutasvuelo']").value;
    const horaSalida = form.querySelector("[name='horaSalida']").value;
    const cantidadTurista = form.querySelector("[name='cantidadAsientosTuristas']").value;
    const cantidadEjecutivo = form.querySelector("[name='cantidadAsientosEjecutivo']").value;

    const duracionRegex = /^\d{1,2}:\d{2}$/;
    if (!nombre || !duracion || !duracionRegex.test(duracion) || !fecha || !cantidadTurista || !cantidadEjecutivo || !rutaSeleccionada) {
      showMessage("Complete los campos obligatorios y use formato de duración HH:MM");
      return;
    }

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