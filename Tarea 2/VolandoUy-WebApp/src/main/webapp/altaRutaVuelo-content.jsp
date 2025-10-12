<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="form-section-pro">
  <div class="form-card-pro">
    <h2> Alta de Ruta de Vuelo</h2>
    <p class="form-subtitle">Ingrese los datos de la nueva ruta</p>

    <form id="formAltaRutaPro" class="form-grid">
      <!-- Nombre y Descripción corta -->
      <div class="form-group">
        <label for="nombre">Nombre de la ruta</label>
        <input type="text" id="nombre" placeholder="Montevideo - Madrid" required>
      </div>
      <div class="form-group">
        <label for="descripcionCorta">Descripción corta</label>
        <input type="text" id="descripcionCorta" maxlength="120" placeholder="Máx. 120 caracteres" required>
      </div>

      <!-- Descripción -->
      <div class="form-group full-width">
        <label for="descripcion">Descripción</label>
        <textarea id="descripcion" rows="4" placeholder="Detalles de la ruta, servicios incluidos..." required></textarea>
      </div>

      <!-- Hora y Fecha -->
      <div class="form-group">
        <label for="hora">Hora de salida</label>
        <input type="time" id="hora" required>
      </div>
      <div class="form-group">
        <label for="fechaAlta">Fecha de alta</label>
        <input type="date" id="fechaAlta">
      </div>

      <!-- Costos -->
      <div class="form-group">
        <label for="costoTurista">Costo Turista</label>
        <input type="number" id="costoTurista" min="0" placeholder="USD" required>
      </div>
      <div class="form-group">
        <label for="costoEjecutivo">Costo Ejecutivo</label>
        <input type="number" id="costoEjecutivo" min="0" placeholder="USD" required>
      </div>
      <div class="form-group full-width">
        <label for="costoEquipaje">Costo Equipaje Extra</label>
        <input type="number" id="costoEquipaje" min="0" placeholder="USD">
      </div>

      <!-- Ciudades -->
      <div class="form-group">
        <label for="ciudadOrigen">Ciudad Origen</label>
        <select id="ciudadOrigen" required>
          <option value="">Seleccione</option>
          <option>Montevideo</option>
          <option>Buenos Aires</option>
          <option>San Pablo</option>
        </select>
      </div>
      <div class="form-group">
        <label for="ciudadDestino">Ciudad Destino</label>
        <select id="ciudadDestino" required>
          <option value="">Seleccione</option>
          <option>Madrid</option>
          <option>Miami</option>
          <option>Santiago</option>
        </select>
      </div>

      <!-- Categorías -->
      <div class="form-group full-width">
        <label for="categorias">Categorías</label>
        <select id="categorias" multiple>
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
        <input type="file" id="foto" accept="image/*">
      </div>

      <!-- Botón -->
      <div class="form-actions full-width">
        <button type="submit" class="btn-pro"> Guardar Ruta</button>
      </div>
    </form>
  </div>
</section>

<script>
  document.getElementById("fechaAlta").valueAsDate = new Date();
  document.getElementById("formAltaRutaPro").addEventListener("submit", e=>{
    e.preventDefault();
    alert("Ruta de vuelo registrada ✅");
  });
</script>
