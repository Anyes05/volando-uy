<section class="search-box">
  <h2>Consulta de Usuario</h2>
  <p class="descripcion-consulta">Total de usuarios registrados: <strong id="total-usuarios">Cargando...</strong></p>
  <form id="filtro-usuarios">
    <div class="filtro-row">
      <label>Tipo de usuario:</label>
      <select id="select-tipo">
        <option value="">Todos</option>
        <option value="aerolinea">Aerolínea</option>
        <option value="cliente">Cliente</option>
      </select>
    </div>

    <div class="filtro-row">
      <label>Buscar por nombre o nickname:</label>
      <input type="text" id="buscador-nombre" placeholder="Ej: zfly, Juan Pérez, LATAM">
    </div>
  </form>
</section>

<!-- Mensaje de carga -->
<div id="loading-message" style="display: none;">
  <div class="loading-container">
    <div class="spinner"></div>
    <p>Cargando usuarios...</p>
  </div>
</div>

<!-- Contenedor donde se listan los usuarios -->
<div id="lista-usuarios">
  <!-- Se llena dinámicamente con JavaScript -->
</div>

<!-- Detalle de usuario (se muestra al hacer clic en un usuario) -->
<section class="detalle-usuario" style="display: none">
  <button class="close-detail-btn" onclick="volverALista()" aria-label="Cerrar detalle">
    <i class="fas fa-times"></i>
  </button>
  <div class="usuario-header">
    <img id="usuario-imagen" src="" alt="Imagen del usuario">
    <div class="usuario-info">
      <h2 id="usuario-nombre"></h2>
      <p id="usuario-credential"></p>
    </div>
  </div>
  
  <div class="usuario-tabs">
    <button class="tab-btn active" data-tab="perfil">Perfil</button>
    <button class="tab-btn" data-tab="actividad">Actividad</button>
  </div>

  <div class="tab-content" id="tab-perfil">
    <div class="perfil-details">
      <p><strong>Nickname:</strong> <span id="perfil-nickname"></span></p>
      <p><strong>Nombre:</strong> <span id="perfil-nombre"></span></p>
      <p><strong>E-mail:</strong> <span id="perfil-email"></span></p>
      <p id="perfil-apellido" style="display: none;"><strong>Apellido:</strong> <span></span></p>
      <p id="perfil-nacionalidad" style="display: none;"><strong>Nacionalidad:</strong> <span></span></p>
      <p id="perfil-documento" style="display: none;"><strong>Documento:</strong> <span></span></p>
      <p id="perfil-fecha-nacimiento" style="display: none;"><strong>Fecha de Nacimiento:</strong> <span></span></p>
      <p id="perfil-sitio" style="display: none;"><strong>Sitio web:</strong> <span></span></p>
      <p id="perfil-descripcion" style="display: none;"><strong>Descripción:</strong> <span></span></p>
      
      <!-- Sección de características sociales: Seguidores y Seguidos -->
      <div id="perfil-social">
        <h3>Red Social</h3>
        <div class="social-grid">
          <div id="seguidores-section">
            <h4>
              <i class="fas fa-users"></i> Seguidores 
              <span id="seguidores-count">(0)</span>
            </h4>
            <div id="seguidores-list">
              <p class="social-empty-message">No hay seguidores</p>
            </div>
          </div>
          <div id="seguidos-section">
            <h4>
              <i class="fas fa-user-plus"></i> Siguiendo 
              <span id="seguidos-count">(0)</span>
            </h4>
            <div id="seguidos-list">
              <p class="social-empty-message">No sigue a nadie</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="tab-content" id="tab-actividad" style="display: none">
    <div id="actividad-content">
      <!-- Se llena dinámicamente según el tipo de usuario -->
    </div>
  </div>
</section>

<!-- Modal para mostrar detalle de ruta/vuelo/paquete -->
<div id="modal-detalle" class="modal-detalle" style="display: none;">
  <div class="modal-content">
    <button class="close-modal-btn" onclick="cerrarModalDetalle()" aria-label="Cerrar modal">
      <i class="fas fa-times"></i>
    </button>
    <div id="modal-body">
      <!-- Se llena dinámicamente con el detalle -->
    </div>
  </div>
</div>

<script src="js/consultaUsuario.js"></script>