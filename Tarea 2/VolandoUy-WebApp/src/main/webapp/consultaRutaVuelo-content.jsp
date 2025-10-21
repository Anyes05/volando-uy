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

<!-- Estilos para el panel de detalles del vuelo -->
<style>
.detalle-vuelo {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 12px;
  padding: 20px;
  margin: 15px 0;
  box-shadow: 0 8px 32px rgba(0,0,0,0.1);
  color: white;
  position: relative;
  overflow: hidden;
}

.detalle-vuelo::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(255,255,255,0.1);
  backdrop-filter: blur(10px);
  z-index: 0;
}

.detalle-vuelo > * {
  position: relative;
  z-index: 1;
}

.detalle-vuelo-header h3 {
  color: white;
  margin-bottom: 15px;
  font-size: 1.3em;
  font-weight: 600;
  text-shadow: 0 2px 4px rgba(0,0,0,0.3);
}

.detalle-vuelo-info {
  background: rgba(255,255,255,0.15);
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 15px;
  backdrop-filter: blur(10px);
}

.detalle-vuelo-info p {
  margin: 8px 0;
  color: white;
  font-weight: 500;
  text-shadow: 0 1px 2px rgba(0,0,0,0.2);
}

.detalle-vuelo-info strong {
  color: #fff;
  font-weight: 700;
}

.seccion-reservas {
  background: rgba(255,255,255,0.1);
  border-radius: 8px;
  padding: 15px;
  margin-top: 15px;
  backdrop-filter: blur(10px);
}

.seccion-reservas h4 {
  color: white;
  margin: 0 0 15px 0;
  font-size: 1.1em;
  font-weight: 600;
  text-shadow: 0 2px 4px rgba(0,0,0,0.3);
}

.detalle-reserva {
  background: rgba(255,255,255,0.2);
  border: 1px solid rgba(255,255,255,0.3);
  border-radius: 8px;
  padding: 15px;
  margin-top: 15px;
  backdrop-filter: blur(10px);
}

.detalle-reserva h4 {
  color: white;
  margin: 0 0 10px 0;
  font-size: 1em;
  font-weight: 600;
  text-shadow: 0 1px 2px rgba(0,0,0,0.3);
}

.reserva-card {
  background: rgba(255,255,255,0.2);
  border: 1px solid rgba(255,255,255,0.3);
  border-radius: 8px;
  padding: 12px;
  margin: 8px 0;
  cursor: pointer;
  transition: all 0.3s ease;
  color: white;
  backdrop-filter: blur(10px);
}

.reserva-card:hover {
  background: rgba(255,255,255,0.3);
  border-color: rgba(255,255,255,0.5);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.2);
}

.reserva-card:active {
  transform: translateY(0);
}

.detalle-reserva-info {
  background: rgba(255,255,255,0.15);
  border-radius: 6px;
  padding: 12px;
  margin-top: 10px;
  backdrop-filter: blur(10px);
}

.detalle-reserva-info p {
  margin: 5px 0;
  color: white;
  text-shadow: 0 1px 2px rgba(0,0,0,0.2);
}

.detalle-reserva-info ul {
  margin: 10px 0;
  padding-left: 20px;
}

.detalle-reserva-info li {
  margin: 5px 0;
  color: white;
  text-shadow: 0 1px 2px rgba(0,0,0,0.2);
}

.detalle-reserva-info strong {
  color: #fff;
  font-weight: 700;
}
</style>

<!-- Script específico para consulta de ruta de vuelo -->
<script src="js/consultaRutaVuelo.js"></script>