<section class="search-box">
  <h2>Consulta de Usuario</h2>
  <p class="descripcion-consulta">Total de usuarios registrados: <strong>${totalUsuarios}</strong></p>
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

<!-- Contenedor donde se listan los usuarios -->
<div id="lista-usuarios">
  <c:choose>
    <c:when test="${not empty usuarios}">
      <div class="usuarios-grid">
        <c:forEach var="usuario" items="${usuarios}">
          <div class="usuario-card" data-usuario-id="${usuario.id}" data-tipo="${usuario.getClass().simpleName}">
            <div class="usuario-card-header">
              <img src="static/img/logoAvionSolo.png" alt="Imagen del usuario" class="usuario-avatar">
              <div class="usuario-card-info">
                <h4>${usuario.nombre}</h4>
                <p class="usuario-nickname">@${usuario.nickname}</p>
                <p class="usuario-email">${usuario.correo}</p>
              </div>
            </div>
            <div class="usuario-card-body">
              <c:choose>
                <c:when test="${usuario.getClass().simpleName == 'Cliente'}">
                  <p class="usuario-tipo cliente">Cliente</p>
                  <p class="usuario-detalle">Paquetes: ${usuario.cantidadPaquetes}</p>
                </c:when>
                <c:when test="${usuario.getClass().simpleName == 'Aerolinea'}">
                  <p class="usuario-tipo aerolinea">Aerolínea</p>
                  <p class="usuario-detalle">${usuario.descripcion}</p>
                </c:when>
                <c:otherwise>
                  <p class="usuario-tipo">Usuario</p>
                </c:otherwise>
              </c:choose>
            </div>
            <div class="usuario-card-actions">
              <button class="btn-ver-detalle" onclick="verDetalleUsuario(${usuario.id})">
                Ver Detalle
              </button>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:when>
    <c:otherwise>
      <div class="no-usuarios">
        <i class="fas fa-users"></i>
        <h3>No hay usuarios registrados</h3>
        <p>No se encontraron usuarios en el sistema.</p>
      </div>
    </c:otherwise>
  </c:choose>
</div>

<!-- Detalle de usuario (se muestra al hacer clic en un usuario) -->
<section class="detalle-usuario" style="display: none">
  <button class="close-detail-btn" onclick="volverALista()" aria-label="Cerrar detalle">×</button>
  <div class="usuario-header">
    <img id="usuario-imagen" src="" alt="Imagen del usuario">
    <div class="usuario-info">
      <h2 id="usuario-nombre"></h2>
      <p id="usuario-credential"></p>
    </div>
  </div>
  
  <div class="usuario-tabs">
    <button class="tab-btn active" data-tab="perfil">Perfil</button>
    <button class="tab-btn" data-tab="rutas">Rutas de Vuelo</button>
  </div>

  <div class="tab-content" id="tab-perfil">
    <div class="perfil-details">
      <p><strong>Nickname:</strong> <span id="perfil-nickname"></span></p>
      <p><strong>Nombre:</strong> <span id="perfil-nombre"></span> <i class="fas fa-edit"></i></p>
      <p><strong>E-mail:</strong> <span id="perfil-email"></span></p>
      <p><strong>Sitio web:</strong> <span id="perfil-sitio"></span> <i class="fas fa-external-link-alt"></i></p>
      <p><strong>Descripción:</strong> <span id="perfil-descripcion"></span> <i class="fas fa-edit"></i></p>
    </div>
  </div>

  <div class="tab-content" id="tab-rutas" style="display: none">
    <div id="rutas-content">
      <!-- Se llena dinámicamente según el tipo de usuario -->
    </div>
  </div>
</section>

<script src="static/js/consultaUsuario.js"></script>