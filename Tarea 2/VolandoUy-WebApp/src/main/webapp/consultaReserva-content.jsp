<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Selector de actor -->
<section id="selector-actor">

  <button id="btn-cliente">Cliente</button>
  <button id="btn-aerolinea">Aerolínea</button>
  <p id="mensaje-actor" class="mensaje-vuelo" style="display:none;"></p>
</section>

<!-- Filtros (adaptados a Consulta Reserva) -->
<section class="search-box">
  <h2>Consulta de Reserva</h2>
  <form id="filtro-rutas">
    <div class="filtro-group">
      <label for="select-aerolinea">Aerolínea:</label>
      <select id="select-aerolinea">
        <option value="Todos">Todas</option>
      </select>
    </div>

    <div class="filtro-group">
      <label for="select-origen">Origen:</label>
      <select id="select-origen">
        <option value="Todos">Todos</option>
      </select>
    </div>

    <div class="filtro-group">
      <label for="select-destino">Destino:</label>
      <select id="select-destino">
        <option value="Todos">Todos</option>
      </select>
    </div>

    <div class="filtro-group">
      <label for="busqueda">Buscar por nombre:</label>
      <input type="text" id="busqueda" placeholder="Ej: Montevideo - Madrid">
    </div>

  </form>
</section>

<!-- Layout principal (rutas + aside vuelos) -->
<div class="contenedor-principal">
  <!-- Contenedor de rutas + paginación -->
  <div class="contenedor-rutas">
    <div id="lista-rutas"></div>
    <div id="paginacion"></div>
  </div>

  <!-- Aside de vuelos -->
  <aside id="aside-vuelos">
    <h3>Vuelos</h3>
    <div id="lista-vuelos">
      <p>Selecciona una ruta para ver sus vuelos</p>
    </div>
    <p id="mensaje-vuelo" class="mensaje-vuelo" style="display:none;"></p>
  </aside>
</div>

<!-- Panel flotante para reservas -->
<div id="panel-reserva" class="modal-reserva" style="display:none;">
  <button id="cerrar-panel" class="cerrar-panel" aria-label="Cerrar">✕</button>
  <div id="contenido-panel">
    <h3>Reservas del vuelo</h3>
    <div id="lista-reservas"></div>
    <div id="detalle-reserva"></div>
  </div>
</div>

<!-- Sección futura de detalle de ruta -->
<section class="detalle-ruta" style="display:none;">
  <h2>Detalle de la Ruta</h2>
  <p><strong>Origen:</strong> <span id="detalle-origen"></span></p>
  <p><strong>Destino:</strong> <span id="detalle-destino"></span></p>
  <p><strong>Fechas:</strong> <span id="detalle-fechas"></span></p>
  <h3>Vuelos asociados</h3>
  <ul id="detalle-vuelos"></ul>
  <h3>Paquete asociado</h3>
  <p id="detalle-paquete"></p>
</section>
