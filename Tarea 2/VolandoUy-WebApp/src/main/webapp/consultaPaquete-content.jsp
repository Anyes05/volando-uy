<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="contenido-principal">
  <section class="search-box">
    <div class="search-header">
      <h2><i class="fas fa-plane"></i> Consulta de Paquetes</h2>
      <p class="descripcion-consulta">Explora nuestros paquetes de vuelos disponibles y descubre las mejores ofertas para tu próximo viaje.</p>
    </div>
    
    <form id="filtro-paquetes" class="filtros-container">
      <div class="filtro-group">
        <label for="buscador-nombre">Buscar por nombre:</label>
        <input type="text" id="buscador-nombre" placeholder="Ej: Escapada Caribe, Semana de Europa">
      </div>
    </form>
  </section>

  <!-- Contenedor donde se listan los paquetes -->
  <div class="paquetes-container">
    <div class="paquetes-header">
      <h3>Paquetes Disponibles</h3>
      <div class="paquetes-stats" id="paquetes-stats">
        <span class="total-paquetes">0 paquetes encontrados</span>
      </div>
    </div>
    <div id="lista-paquetes" class="grid-list"></div>
  </div>
</div>

<!-- Detalle de paquete (se muestra al hacer clic en un paquete) -->
<section class="detalle-paquete" style="display: none">
  <button class="close-detail-btn" onclick="volverAListaPaquetes()" aria-label="Cerrar detalle">×</button>

  <div class="paquete-header">
    <img id="paquete-imagen" src="" alt="Imagen del paquete">
    <div class="paquete-info">
      <h2 id="paquete-nombre"></h2>
      <p id="paquete-descripcion"></p>
    </div>
  </div>

  <div class="paquete-details">
    <p><strong>Días válidos:</strong> <span id="paquete-dias"></span></p>
    <p><strong>Descuento:</strong> <span id="paquete-descuento"></span>%</p>
    <p><strong>Fecha de alta:</strong> <span id="paquete-fecha"></span></p>
    <p><strong>Costo total:</strong> $<span id="paquete-costo"></span></p>
  </div>

  <h3>Rutas incluidas</h3>
  <div id="paquete-cantidades" class="rutas-grid"></div>
</section>

<script src="js/consultaPaquete.js"></script>
