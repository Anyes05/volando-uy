<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="contenido-principal">
  <section class="search-box">
    <div class="search-header">
      <h2>Consulta de Ruta de Vuelo</h2>
      <p class="descripcion-consulta">Explora las rutas de vuelo disponibles y encuentra la conexión perfecta para tu próximo viaje. Todas las rutas mostradas están confirmadas y operativas.</p>
    </div>
    
    <form id="filtro-rutas" class="filtros-container">
      <div class="filtro-group">
        <label for="select-aerolinea">Aerolínea:</label>
        <select id="select-aerolinea">
          <option value="">Todas las aerolíneas</option>
        </select>
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

<div class="contenedor-principal">
  <!-- Contenedor de rutas + paginación -->
  <div class="contenedor-rutas">
    <div class="rutas-header">
      <h3>Rutas Disponibles</h3>
      <div class="rutas-stats" id="rutas-stats">
        <span class="total-rutas">0 rutas encontradas</span>
      </div>
    </div>
    <div id="lista-rutas" class="rutas-grid"></div>
    <div id="paginacion" class="paginacion-container"></div>
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
