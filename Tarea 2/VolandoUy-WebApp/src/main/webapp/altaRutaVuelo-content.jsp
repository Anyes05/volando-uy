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
          <option value="Low Cost">Low Cost</option>
          <option value="Premium">Premium</option>
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

    // validación básica en cliente (el servidor también valida)
    const nombre = form.querySelector("[name='nombre']").value.trim();
    const descripcion = form.querySelector("[name='descripcion']").value.trim();
    const hora = form.querySelector("[name='horaSalida']").value;
    const costoTurista = form.querySelector("[name='costoTurista']").value;
    const costoEjecutivo = form.querySelector("[name='costoEjecutivo']").value;
    const ciudadOrigen = form.querySelector("[name='ciudadOrigen']").value;
    const ciudadDestino = form.querySelector("[name='ciudadDestino']").value;

    if (!nombre || !descripcion || !hora || !costoTurista || !costoEjecutivo || !ciudadOrigen || !ciudadDestino) {
      showMessage("Complete los campos obligatorios");
      return;
    }

    // construir FormData (maneja file y campos múltiples)
    const formData = new FormData(form);

    // Para select multiple: ya se agregan automáticamente los valores seleccionados con formData
    // Enviar petición
    submitBtn.disabled = true;
    const originalText = submitBtn.textContent;
    submitBtn.textContent = "Guardando...";

    try {
            // NO establecer header Content-Type manualmente para FormData
            const response = await fetch("<%= request.getContextPath() %>/api/rutas", {
              method: "POST",
              body: formData
            });

            let result = {};
            try { result = await response.json(); } catch (err) { /* no-json */ }

            if (response.ok) {
              alert(result.mensaje || "Operación de Ruta completada.");
            } else {
              alert(result.error || ("Error de ruta: " + response.status));
            }
          } catch (error) {
            alert("Error al registrar ruta: " + error.message);
          }
  });
</script>