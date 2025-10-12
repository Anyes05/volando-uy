<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="form-section-pro">
  <div class="form-card-pro">
    <h2> Alta de Vuelo</h2>
    <p class="form-subtitle">Ingrese los datos del vuelo</p>

    <form id="formAltaRutaPro" class="form-grid">
      <div class="form-group">
        <label for="nombre">Nombre del vuelo</label>
        <input type="text" id="nombre" required>
      </div>

      <div class="form-group">
        <label for="horas">Duración</label>
        <div class="duration-inputs">
          <input type="number" id="horas" min="0" max="99" placeholder="Horas" required>
          <span>h</span>
          <input type="number" id="minutos" min="0" max="59" placeholder="Minutos" required>
          <span>m</span>
        </div>
      </div>

      <div class="form-group">
        <label for="fechaAlta">Fecha de alta</label>
        <input type="date" id="fechaAlta" required>
      </div>

      <div class="form-group">
        <label for="cantidadAsientosTuristas">Cantidad de Asientos Turistas</label>
        <input type="number" id="cantidadAsientosTuristas" min="0" required>
      </div>

      <div class="form-group">
        <label for="cantidadAsientosEjecutivo">Cantidad de Asientos Ejecutivos</label>
        <input type="number" id="cantidadAsientosEjecutivo" min="0" required>
      </div>

      <div class="form-group full-width">
        <label for="foto">Imagen de portada</label>
        <input type="file" id="foto" accept="image/*">
      </div>

      <div class="form-actions full-width">
        <button type="submit" class="btn-pro"> Guardar Vuelo</button>
      </div>
    </form>
  </div>
</section>

<script>
  document.getElementById("fechaAlta").valueAsDate = new Date();

  document.getElementById("formAltaRutaPro").addEventListener("submit", e => {
    e.preventDefault();
    
    // Obtiene los valores de los inputs de número para la duración
    const horas = document.getElementById("horas").value;
    const minutos = document.getElementById("minutos").value;
    
    // Validación de que ambos campos no estén vacíos
    if (horas === "" || minutos === "") {
      alert("Error: Por favor, ingrese un valor para Horas y Minutos.");
      return;
    }

    // Formatea la duración
    const duracion = `${horas}h ${minutos}m`;

    alert("Vuelo registrado con duración: " + duracion);
  });
</script>
