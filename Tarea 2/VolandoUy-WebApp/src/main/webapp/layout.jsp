<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.volandouy.helper.DeviceDetector" %>
<%
  // Detectar si es un dispositivo móvil
  boolean isMobile = DeviceDetector.isMobileDevice(request);
  boolean isTablet = DeviceDetector.isTabletDevice(request);
  boolean isMobilePhone = DeviceDetector.isMobilePhone(request);
  
  // Guardar en request scope para usar en JSP
  request.setAttribute("isMobileDevice", isMobile);
  request.setAttribute("isTabletDevice", isTablet);
  request.setAttribute("isMobilePhone", isMobilePhone);
%>
<!DOCTYPE html>
<html lang="es">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>VolandoUY</title>
  <link rel="icon" href="static/img/logoAvionConFondo.png">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
    integrity="sha512-Avb2QiuDEEvB4bZJYdft2mNjVShBftLdPG8FJ0V7irTLQ8Uo0qcPxh4Plq7G5tGm0rU+1SPhVotteLpBERwTkw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="static/css/style.css">
  <link rel="stylesheet" href="static/css/consultaRutaVuelo.css">
  <link rel="stylesheet" href="static/css/consultaUsuario.css">
  <link rel="stylesheet" href="static/css/altaVuelo.css">
  <link rel="stylesheet" href="static/css/altaRutaVuelo.css">
  <link rel="stylesheet" href="static/css/consultaVueloEstilos.css">
  <link rel="stylesheet" href="static/css/modificarUsuario.css">
  <link rel="stylesheet" href="static/css/reserva.css">
  <link rel="stylesheet" href="css/consultaReserva.css">
  <link rel="stylesheet" href="static/css/consultaPaquete.css">
  <link rel="stylesheet" href="static/css/compraPaquete.css">
  <link rel="stylesheet" href="static/css/registrarUsuario.css">
  <link rel="stylesheet" href="static/css/login.css">
  <link rel="stylesheet" href="css/errorHandler.css">
  <style>
    /* Panel de debug temporal - remover en producción */
    .debug-panel {
      position: fixed;
      top: 10px;
      right: 10px;
      background: rgba(255, 255, 0, 0.9);
      padding: 15px;
      border-radius: 8px;
      font-size: 12px;
      z-index: 10000;
      max-width: 300px;
      box-shadow: 0 4px 6px rgba(0,0,0,0.3);
      font-family: monospace;
    }
    .debug-panel h4 {
      margin: 0 0 10px 0;
      color: #000;
    }
    .debug-panel p {
      margin: 5px 0;
      color: #333;
    }
    .debug-panel .true { color: green; font-weight: bold; }
    .debug-panel .false { color: red; font-weight: bold; }
    
    /* CSS para ocultar elementos en móvil basándose en el ancho de pantalla */
    /* Esto funciona como respaldo cuando el User-Agent no se detecta correctamente */
    @media (max-width: 767px) {
      /* Ocultar elementos que solo deben verse en desktop */
      .desktop-only,
      body:not(.mobile-phone) .nav-section:has(a[href*="registrarUsuario"]),
      body:not(.mobile-phone) .nav-section:has(a[href*="consultaUsuario"]):not(:has(a[href*="consultaRutaVuelo"])),
      body:not(.mobile-phone) .nav-section:has(a[href*="consultaPaquete"]),
      body:not(.mobile-phone) .nav-section:has(a[href*="compraPaquete"]),
      body:not(.mobile-phone) .nav-section:has(a[href*="altaRutaVuelo"]),
      body:not(.mobile-phone) .nav-section:has(a[href*="altaVuelo"]),
      body:not(.mobile-phone) .nav-section:has(a[href*="reserva"]),
      body:not(.mobile-phone) .nav-item[href*="inicio.jsp"] {
        display: none !important;
      }
      
      /* Asegurar que solo se muestren los casos de uso permitidos en móvil */
      .mobile-phone .nav-section:has(a[href*="registrarUsuario"]) { display: none !important; }
      .mobile-phone .nav-section:has(a[href*="consultaUsuario"]):not(:has(a[href*="consultaRutaVuelo"])) { display: none !important; }
      .mobile-phone .nav-section:has(a[href*="consultaPaquete"]) { display: none !important; }
      .mobile-phone .nav-section:has(a[href*="compraPaquete"]) { display: none !important; }
      .mobile-phone .nav-section:has(a[href*="altaRutaVuelo"]) { display: none !important; }
      .mobile-phone .nav-section:has(a[href*="altaVuelo"]) { display: none !important; }
      .mobile-phone .nav-section:has(a[href*="reserva"]) { display: none !important; }
      .mobile-phone .nav-item[href*="inicio.jsp"] { display: none !important; }
    }
    
    /* Forzar visibilidad de elementos móviles cuando la clase mobile-phone está presente */
    body.mobile-phone .nav-section:has(a[href*="inicioSesion"]) { display: block !important; }
    body.mobile-phone .nav-section:has(a[href*="consultaRutaVuelo"]) { display: block !important; }
    body.mobile-phone .nav-section:has(a[href*="consultaVuelo"]) { display: block !important; }
    body.mobile-phone .nav-section:has(a[href*="consultaReserva"]) { display: block !important; }
  </style>
</head>

<body class="${isMobileDevice ? 'mobile-device' : 'desktop-device'} ${isMobilePhone ? 'mobile-phone' : ''} ${isTabletDevice ? 'tablet-device' : ''}" 
      data-server-mobile="${isMobileDevice}" 
      data-server-mobile-phone="${isMobilePhone}" 
      data-server-tablet="${isTabletDevice}">
  
  <!-- Panel de debug temporal -->
  <div class="debug-panel" id="debugPanel">
    <h4>🔍 Debug - Detección de Dispositivo</h4>
    <p><strong>Servidor (User-Agent):</strong></p>
    <p>isMobileDevice: <span class="${isMobileDevice ? 'true' : 'false'}">${isMobileDevice}</span></p>
    <p>isMobilePhone: <span class="${isMobilePhone ? 'true' : 'false'}">${isMobilePhone}</span></p>
    <p>isTabletDevice: <span class="${isTabletDevice ? 'true' : 'false'}">${isTabletDevice}</span></p>
    <p><strong>Cliente (Pantalla):</strong></p>
    <p>Ancho: <span id="screenWidth">-</span>px</p>
    <p>Es Móvil: <span id="clientMobile">-</span></p>
    <p><strong>User-Agent:</strong></p>
    <p style="font-size: 10px; word-break: break-all;">${header['User-Agent']}</p>
    <button onclick="document.getElementById('debugPanel').style.display='none'" style="margin-top: 10px; padding: 5px 10px; cursor: pointer;">Cerrar</button>
  </div>

  <!-- Overlay para mobile / sidebar -->
  <div id="overlay" class="overlay" onclick="closeSidebar()"></div>

  <!-- Sidebar -->
  <aside class="sidebar" id="sidenav-1">
    <button class="close-btn" onclick="closeSidebar()" aria-label="Cerrar menú">×</button>
    
    <!-- Inicio - solo en desktop -->
    <c:if test="${!isMobilePhone}">
      <a href="inicio.jsp" class="nav-item">
        <i class="fas fa-home"></i>
        <span>Inicio</span>
      </a>
    </c:if>

    <!-- Navegación para visitantes -->
    <c:if test="${empty sessionScope.usuarioLogueado}">
      <%-- En móvil: solo mostrar Iniciar Sesión y las consultas permitidas --%>
      <c:if test="${isMobilePhone}">
        <div class="nav-section">
          <h3 class="nav-section-title">Acceso</h3>
          <a href="inicioSesion.jsp" class="nav-item">
            <i class="fas fa-sign-in-alt"></i>
            <span>Iniciar Sesión</span>
          </a>
        </div>
        
        <div class="nav-section">
          <h3 class="nav-section-title">Consultas</h3>
          <a href="consultaRutaVuelo.jsp" class="nav-item">
            <i class="fas fa-route"></i>
            <span>Consulta de Rutas</span>
          </a>
          <a href="consultaVuelo.jsp" class="nav-item">
            <i class="fas fa-plane"></i>
            <span>Consulta de Vuelos</span>
          </a>
        </div>
      </c:if>
      
      <%-- En desktop: mostrar todas las opciones --%>
      <c:if test="${!isMobilePhone}">
        <div class="nav-section">
          <h3 class="nav-section-title">Acceso</h3>
          <a href="inicioSesion.jsp" class="nav-item">
            <i class="fas fa-sign-in-alt"></i>
            <span>Iniciar Sesión</span>
          </a>
          <a href="registrarUsuario.jsp" class="nav-item">
            <i class="fas fa-user-plus"></i>
            <span>Registrarse</span>
          </a>
        </div>

        <div class="nav-section">
          <h3 class="nav-section-title">Consultas</h3>
          <a href="consultaUsuario.jsp" class="nav-item">
            <i class="fas fa-search"></i>
            <span>Consulta de Usuario</span>
          </a>
          <a href="consultaRutaVuelo.jsp" class="nav-item">
            <i class="fas fa-route"></i>
            <span>Consulta de Rutas</span>
          </a>
          <a href="consultaVuelo.jsp" class="nav-item">
            <i class="fas fa-plane"></i>
            <span>Consulta de Vuelos</span>
          </a>
          <a href="consultaPaquete.jsp" class="nav-item">
            <i class="fas fa-box"></i>
            <span>Consulta de Paquetes</span>
          </a>
        </div>
      </c:if>
    </c:if>

    <!-- Navegación para usuarios logueados -->
    <c:if test="${not empty sessionScope.usuarioLogueado}">
      <%-- En móvil: solo mostrar las consultas permitidas --%>
      <c:if test="${isMobilePhone}">
        <div class="nav-section">
          <h3 class="nav-section-title">Vuelos</h3>
          <a href="consultaRutaVuelo.jsp" class="nav-item">
            <i class="fas fa-route"></i>
            <span>Consulta de Rutas</span>
          </a>
          <a href="consultaVuelo.jsp" class="nav-item">
            <i class="fas fa-plane"></i>
            <span>Consulta de Vuelos</span>
          </a>
        </div>

        <div class="nav-section">
          <h3 class="nav-section-title">Reservas</h3>
          <a href="consultaReserva.jsp" class="nav-item">
            <i class="fas fa-list-alt"></i>
            <span>Consulta de Reservas</span>
          </a>
        </div>
      </c:if>
      
      <%-- En desktop: mostrar todas las opciones --%>
      <c:if test="${!isMobilePhone}">
        <!-- Usuario -->
        <div class="nav-section">
          <h3 class="nav-section-title">Mi Cuenta</h3>
          <a href="consultaUsuario.jsp" class="nav-item">
            <i class="fas fa-user"></i>
            <span>Mi Perfil</span>
          </a>
          <c:if test="${sessionScope.tipoUsuario == 'cliente' || sessionScope.tipoUsuario == 'aerolinea'}">
            <a href="modificarUsuario.jsp" class="nav-item">
              <i class="fas fa-edit"></i>
              <span>Modificar Perfil</span>
            </a>
          </c:if>
        </div>

        <!-- Vuelos -->
        <div class="nav-section">
          <h3 class="nav-section-title">Vuelos</h3>
          <a href="consultaRutaVuelo.jsp" class="nav-item">
            <i class="fas fa-route"></i>
            <span>Consulta de Rutas</span>
          </a>
          <a href="consultaVuelo.jsp" class="nav-item">
            <i class="fas fa-plane"></i>
            <span>Consulta de Vuelos</span>
          </a>
          
          <!-- Opciones específicas para aerolíneas -->
          <c:if test="${sessionScope.tipoUsuario == 'aerolinea'}">
            <a href="altaRutaVuelo.jsp" class="nav-item">
              <i class="fas fa-plus-circle"></i>
              <span>Alta y Finalizacion de Ruta</span>
            </a>
            <a href="altaVuelo.jsp" class="nav-item">
              <i class="fas fa-plus-circle"></i>
              <span>Alta de Vuelo</span>
            </a>
          </c:if>
          
          <!-- Opciones específicas para clientes -->
          <c:if test="${sessionScope.tipoUsuario == 'cliente'}">
            <a href="reserva.jsp" class="nav-item">
              <i class="fas fa-calendar-check"></i>
              <span>Reservar Vuelo</span>
            </a>
          </c:if>
        </div>

        <!-- Reservas -->
        <div class="nav-section">
          <h3 class="nav-section-title">Reservas</h3>
          <a href="consultaReserva.jsp" class="nav-item">
            <i class="fas fa-list-alt"></i>
            <span>Consulta de Reservas</span>
          </a>
        </div>

        <!-- Paquetes -->
        <div class="nav-section">
          <h3 class="nav-section-title">Paquetes</h3>
          <a href="consultaPaquete.jsp" class="nav-item">
            <i class="fas fa-box"></i>
            <span>Consulta de Paquetes</span>
          </a>
          <c:if test="${sessionScope.tipoUsuario == 'cliente'}">
            <a href="compraPaquete.jsp" class="nav-item">
              <i class="fas fa-shopping-cart"></i>
              <span>Compra de Paquetes</span>
            </a>
          </c:if>
        </div>
      </c:if>
    </c:if>
  </aside>

  <!-- Main content -->
  <div class="main">

    <!-- Header -->
    <header class="header">
      <!-- Toggler responsive -->
      <button data-mdb-button-init data-mdb-toggle="sidenav" data-mdb-target="#sidenav-1" data-mdb-ripple-init
        class="sidenav-toggler" id="menuBtn" aria-controls="#sidenav-1" aria-haspopup="true" aria-label="Abrir menú">
        <i class="fas fa-bars" aria-hidden="true"></i>
      </button>

      <!-- Logo y branding -->
      <div class="brand-container">
        <div class="logo-container">
          <img src="static/img/logoAvionSolo.png" alt="VolandoUY Logo" class="logo-img">
          <div class="brand-text">
            <h1 class="brand-title">VolandoUY</h1>
            <p class="brand-slogan">Conectamos tus rutas, acercamos tus destinos</p>
          </div>
        </div>
      </div>

      <!-- Header icons -->
      <div class="header-icons">
        <c:choose>
          <c:when test="${not empty sessionScope.usuarioLogueado}">
            <!-- Usuario logueado: mostrar foto con menú desplegable -->
            <div class="user-logged-in">
              <div class="user-profile-dropdown">
                <div class="user-avatar-container" onclick="toggleUserMenu()">
                  <c:choose>
                    <c:when test="${not empty sessionScope.fotoUsuario}">
                      <img src="data:image/jpeg;base64,${sessionScope.fotoUsuario}"
                           alt="${sessionScope.nombreUsuario}"
                           class="user-avatar-circle"
                           title="${sessionScope.nombreUsuario}">
                    </c:when>
                    <c:otherwise>
                      <div class="user-avatar-circle user-avatar-default" title="${sessionScope.nombreUsuario}">
                        <i class="fas fa-user"></i>
                      </div>
                    </c:otherwise>
                  </c:choose>
                  <i class="fas fa-chevron-down dropdown-arrow"></i>
                </div>
                
                <!-- Menú desplegable del usuario -->
                <div class="user-dropdown-menu" id="userDropdownMenu">
                  <div class="user-info">
                    <div class="user-name">${sessionScope.nombreUsuario}</div>
                    <div class="user-type">${sessionScope.tipoUsuario}</div>
                  </div>
                  <div class="dropdown-divider"></div>
                  <a href="consultaUsuario.jsp" class="dropdown-item">
                    <i class="fas fa-user"></i>
                    <span>Mi Perfil</span>
                  </a>
                  <c:if test="${sessionScope.tipoUsuario == 'cliente' || sessionScope.tipoUsuario == 'aerolinea'}">
                    <a href="modificarUsuario.jsp" class="dropdown-item">
                      <i class="fas fa-edit"></i>
                      <span>Modificar Perfil</span>
                    </a>
                  </c:if>
                  <div class="dropdown-divider"></div>
                  <button onclick="cerrarSesion()" class="dropdown-item logout-item">
                    <i class="fas fa-sign-out-alt"></i>
                    <span>Cerrar Sesión</span>
                  </button>
                </div>
              </div>
            </div>
          </c:when>
          <c:otherwise>
            <!-- Usuario visitante: mostrar botones de login y registro -->
            <a href="inicioSesion.jsp" class="login-btn">
              <i class="fas fa-user"></i>
              <span class="btn-text">Iniciar sesión</span>
            </a>
            <%-- En móvil, ocultar botón de registro --%>
            <c:if test="${!isMobilePhone}">
              <a href="registrarUsuario.jsp" class="signup-btn">
                <i class="fas fa-user-plus"></i>
                <span class="btn-text">Registrarse</span>
              </a>
            </c:if>
          </c:otherwise>
        </c:choose>
      </div>
    </header>

    <div id="main-content" class="${param.content == 'inicioSesion-content.jsp' ? 'login-page' : ''}">
      <!-- Contenido específico de cada página -->
      <c:choose>
        <c:when test="${not empty param.content}">
          <jsp:include page="${param.content}" />
        </c:when>
        <c:otherwise>
          <%-- En móvil, la página inicial es inicio de sesión; en desktop, es inicio --%>
          <c:choose>
            <c:when test="${isMobilePhone && empty sessionScope.usuarioLogueado}">
              <jsp:include page="inicioSesion-content.jsp" />
            </c:when>
            <c:otherwise>
              <jsp:include page="inicio-content.jsp" />
            </c:otherwise>
          </c:choose>
        </c:otherwise>
      </c:choose>
    </div>

    <!-- Beneficios -->
    <section class="benefits">
      <div class="benefits-container">
        <h2 class="benefits-title">¿Por qué elegir VolandoUY?</h2>
        <div class="benefits-grid">
          <div class="benefit-card">
            <div class="benefit-icon">
              <i class="fas fa-tags"></i>
            </div>
            <h3>Mejores Tarifas</h3>
            <p>Encontramos las mejores ofertas y promociones para tus viajes</p>
          </div>
          <div class="benefit-card">
            <div class="benefit-icon">
              <i class="fas fa-shield-alt"></i>
            </div>
            <h3>Pago Seguro</h3>
            <p>Procesamos tus pagos con la máxima seguridad y encriptación</p>
          </div>
          <div class="benefit-card">
            <div class="benefit-icon">
              <i class="fas fa-headset"></i>
            </div>
            <h3>Atención 24/7</h3>
            <p>Nuestro equipo está disponible las 24 horas para ayudarte</p>
          </div>
        </div>
      </div>
    </section>

    <!-- Footer -->
    <footer class="footer">
      <div class="footer-container">
        <div class="footer-content">
          <div class="footer-section">
            <div class="footer-logo">
              <img src="static/img/logoAvionSolo.png" alt="VolandoUY" class="footer-logo-img">
              <h3>VolandoUY</h3>
            </div>
            <p class="footer-description">
              Conectamos tus rutas, acercamos tus destinos. Tu compañía de confianza para viajar por el mundo.
            </p>
            <div class="social-links">
              <a href="#" class="social-link" aria-label="Facebook">
                <i class="fab fa-facebook-f"></i>
              </a>
              <a href="#" class="social-link" aria-label="Twitter">
                <i class="fab fa-twitter"></i>
              </a>
              <a href="#" class="social-link" aria-label="Instagram">
                <i class="fab fa-instagram"></i>
              </a>
              <a href="#" class="social-link" aria-label="LinkedIn">
                <i class="fab fa-linkedin-in"></i>
              </a>
            </div>
          </div>

          <div class="footer-section">
            <h4>Enlaces Rápidos</h4>
            <ul class="footer-links">
              <li><a href="inicio.jsp">Inicio</a></li>
              <li><a href="consultaVuelo.jsp">Buscar Vuelos</a></li>
              <li><a href="reserva.jsp">Reservar</a></li>
              <li><a href="consultaPaquete.jsp">Paquetes</a></li>
            </ul>
          </div>

          <div class="footer-section">
            <h4>Servicios</h4>
            <ul class="footer-links">
              <li><a href="consultaReserva.jsp">Mis Reservas</a></li>
              <li><a href="consultaUsuario.jsp">Mi Cuenta</a></li>
              <li><a href="modificarUsuario.jsp">Perfil</a></li>
              <li><a href="#">Check-in Online</a></li>
            </ul>
          </div>

          <div class="footer-section">
            <h4>Contacto</h4>
            <div class="contact-info">
              <div class="contact-item">
                <i class="fas fa-phone"></i>
                <span>+598 1234 5678</span>
              </div>
              <div class="contact-item">
                <i class="fas fa-envelope"></i>
                <span>info@volandouy.com</span>
              </div>
              <div class="contact-item">
                <i class="fas fa-map-marker-alt"></i>
                <span>Montevideo, Uruguay</span>
              </div>
            </div>
          </div>
        </div>

        <div class="footer-bottom">
          <div class="footer-bottom-content">
            <p>&copy; 2025 VolandoUY - Todos los derechos reservados</p>
            <div class="legal-links">
              <a href="#">Términos y Condiciones</a>
              <a href="#">Política de Privacidad</a>
              <a href="#">Cookies</a>
            </div>
          </div>
        </div>
      </div>
    </footer>

  </div>

  <!-- Sistema de manejo de errores estético -->
  <script src="js/errorHandler.js"></script>

  <!-- Script: sidebar + carrusel (autoplay) -->
  <script>
    const sidebar = document.getElementById('sidenav-1');
    const overlay = document.getElementById('overlay');
    const menuBtn = document.getElementById('menuBtn');

    function openSidebar() {
      sidebar.classList.add('active');
      overlay.classList.add('active');
      document.documentElement.style.overflow = 'hidden';
      document.body.style.overflow = 'hidden';
    }
    function closeSidebar() {
      sidebar.classList.remove('active');
      overlay.classList.remove('active');
      document.documentElement.style.overflow = '';
      document.body.style.overflow = '';
    }
    function toggleSidebar() {
      if (sidebar.classList.contains('active')) closeSidebar();
      else openSidebar();
    }

    menuBtn.addEventListener('click', (e) => {
      e.preventDefault();
      toggleSidebar();
    });

    document.addEventListener('keydown', (e) => {
      if (e.key === 'Escape' && sidebar.classList.contains('active')) closeSidebar();
    });

    function cerrarSesion() {
      showConfirm('¿Estás seguro de que quieres cerrar sesión?', 'Cerrar Sesión', ['Sí, cerrar sesión', 'Cancelar'])
        .then(option => {
          if (option === 0) {
            window.location.href = '${pageContext.request.contextPath}/logout';
          }
        });
    }

    // Función para manejar el menú desplegable del usuario
    function toggleUserMenu() {
      const dropdown = document.getElementById('userDropdownMenu');
      if (dropdown) {
        dropdown.classList.toggle('show');
      }
    }

    // Cerrar menú desplegable al hacer clic fuera
    document.addEventListener('click', function(event) {
      const dropdown = document.getElementById('userDropdownMenu');
      const avatarContainer = document.querySelector('.user-avatar-container');
      
      if (dropdown && avatarContainer && !avatarContainer.contains(event.target)) {
        dropdown.classList.remove('show');
      }
    });

    // Detectar si el sidebar necesita scroll
    function checkSidebarScroll() {
      const sidebar = document.getElementById('sidenav-1');
      if (sidebar) {
        if (sidebar.scrollHeight > sidebar.clientHeight) {
          sidebar.classList.add('scrollable');
        } else {
          sidebar.classList.remove('scrollable');
        }
      }
    }

    // Verificar scroll al cargar y al redimensionar
    document.addEventListener('DOMContentLoaded', checkSidebarScroll);
    window.addEventListener('resize', checkSidebarScroll);

    /* ===== Carrusel ===== */
    function initCarousel() {
      const carousel = document.getElementById("carousel");

      const prevBtn = document.getElementById("prevBtn");
      const nextBtn = document.getElementById("nextBtn");

      if (!carousel || !prevBtn || !nextBtn) {
        console.log('Carousel elements not found, retrying...');
        setTimeout(initCarousel, 500);
        return;
      }

      let currentIndex = 0;
      const cards = carousel.querySelectorAll('.offer-card');
      const totalCards = cards.length;

      if (totalCards === 0) {
        console.log('No cards found, retrying...');
        setTimeout(initCarousel, 500);
        return;
      }

      function getStep() {
        const card = carousel.querySelector('.offer-card');
        if (!card) return 235;
        const gapValue = getComputedStyle(carousel).gap || '15px';
        const gap = parseInt(gapValue, 10) || 15;
        return card.offsetWidth + gap;
      }

      function scrollToIndex(index) {
        const step = getStep();
        const targetScroll = index * step;
        carousel.scrollTo({
          left: targetScroll,
          behavior: 'smooth'
        });
      }

      function scrollNext() {
        currentIndex = (currentIndex + 1) % totalCards;
        scrollToIndex(currentIndex);
      }

      function scrollPrev() {
        currentIndex = (currentIndex - 1 + totalCards) % totalCards;
        scrollToIndex(currentIndex);
      }

      let autoplayTimer = null;
      const AUTOPLAY_DELAY = 4000;

      function startAutoplay() {
        stopAutoplay();
        autoplayTimer = setInterval(scrollNext, AUTOPLAY_DELAY);
      }

      function stopAutoplay() {
        if (autoplayTimer) {
          clearInterval(autoplayTimer);
          autoplayTimer = null;
        }
      }

      nextBtn.addEventListener('click', (e) => {
        e.preventDefault();
        stopAutoplay();
        scrollNext();
        setTimeout(startAutoplay, 3000);
      });

      prevBtn.addEventListener('click', (e) => {
        e.preventDefault();
        stopAutoplay();
        scrollPrev();
        setTimeout(startAutoplay, 3000);
      });

      carousel.addEventListener('mouseenter', stopAutoplay);
      carousel.addEventListener('mouseleave', startAutoplay);
      carousel.addEventListener('touchstart', () => { stopAutoplay(); }, {passive:true});
      carousel.addEventListener('touchend', () => { setTimeout(startAutoplay, 2000); });

      window.addEventListener('resize', () => {
        stopAutoplay();
        setTimeout(startAutoplay, 500);
      });

      setTimeout(startAutoplay, 2000);
      console.log('Carousel initialized successfully');
    }

    if (document.readyState === 'loading') {
      document.addEventListener('DOMContentLoaded', initCarousel);
    } else {
      initCarousel();
    }
  </script>

<script>
document.addEventListener('DOMContentLoaded', function() {
  // Si el servidor tiene usuario en sesión, inyectar esos datos en sessionStorage
  <%-- Si hay usuario en sesión, se escriben los campos; si no, se limpian --%>
  <c:choose>
    <c:when test="${not empty sessionScope.usuarioLogueado}">
      (function(){
        try {
          sessionStorage.setItem('usuarioLogueado', '<c:out value="${sessionScope.usuarioLogueado}" />');
          sessionStorage.setItem('nombreUsuario', '<c:out value="${sessionScope.nombreUsuario}" />');
          sessionStorage.setItem('tipoUsuario', '<c:out value="${sessionScope.tipoUsuario}" />');
          // si guardas la foto en base64 en sesión, la ponemos también
          <c:if test="${not empty sessionScope.fotoUsuario}">
            sessionStorage.setItem('fotoUsuario', 'data:image/jpeg;base64,<c:out value="${sessionScope.fotoUsuario}" />');
          </c:if>
        } catch(e) {
          console.warn('No se pudo setear sessionStorage desde sesión JSP:', e);
        }
      })();
    </c:when>
    <c:otherwise>
      (function(){
        try {
          sessionStorage.removeItem('usuarioLogueado');
          sessionStorage.removeItem('nombreUsuario');
          sessionStorage.removeItem('tipoUsuario');
          sessionStorage.removeItem('fotoUsuario');
        } catch(e) {
          /* ignore */
        }
      })();
    </c:otherwise>
  </c:choose>

  // ===== DETECCIÓN DE DISPOSITIVO MÓVIL DEL LADO DEL CLIENTE =====
  // Esto funciona como respaldo cuando el User-Agent no se detecta correctamente
  function detectarDispositivoMovil() {
    const anchoPantalla = window.innerWidth || document.documentElement.clientWidth;
    const esMovil = anchoPantalla <= 767; // Mismo breakpoint que CSS
    
    // Actualizar panel de debug
    const screenWidthEl = document.getElementById('screenWidth');
    const clientMobileEl = document.getElementById('clientMobile');
    if (screenWidthEl) screenWidthEl.textContent = anchoPantalla;
    if (clientMobileEl) {
      clientMobileEl.textContent = esMovil ? 'Sí' : 'No';
      clientMobileEl.className = esMovil ? 'true' : 'false';
    }
    
    // Obtener detección del servidor
    const serverMobile = document.body.getAttribute('data-server-mobile-phone') === 'true';
    const serverMobileDevice = document.body.getAttribute('data-server-mobile') === 'true';
    
    // PRIORIDAD: Si el cliente detecta móvil (pantalla pequeña), usar esa detección
    // Esto es necesario cuando las DevTools simulan dispositivo pero no cambian el User-Agent
    const esMovilFinal = esMovil || serverMobileDevice;
    
    // Agregar/quitar clases al body
    if (esMovilFinal) {
      document.body.classList.add('mobile-device', 'mobile-phone');
      document.body.classList.remove('desktop-device');
    } else {
      document.body.classList.add('desktop-device');
      document.body.classList.remove('mobile-device', 'mobile-phone');
    }
    
    // Ajustar elementos del sidebar basándose en el ancho de pantalla
    // IMPORTANTE: Usar la detección del cliente (esMovil) para el ajuste
    ajustarSidebarParaMovil(esMovil);
    
    console.log('Detección dispositivo:', {
      anchoPantalla: anchoPantalla,
      servidorDetectoMovil: serverMobileDevice,
      clienteDetectoMovil: esMovil,
      esMovilFinal: esMovilFinal,
      usandoCliente: esMovil && !serverMobileDevice
    });
    
    return esMovilFinal;
  }
  
  // Función para ajustar el sidebar dinámicamente
  function ajustarSidebarParaMovil(esMovil) {
    // Esta función se ejecuta después de que el servidor ya renderizó el HTML
    // Oculta/muestra elementos basándose en el ancho de pantalla
    const sidebar = document.querySelector('.sidebar');
    if (!sidebar) {
      console.warn('Sidebar no encontrado');
      return;
    }
    
    console.log('Ajustando sidebar para móvil:', esMovil);
    
    // Lista de enlaces que deben ocultarse en móvil
    // NOTA: NO incluir 'consultaReserva' aquí porque debe mostrarse
    const enlacesAOcultar = [
      'registrarUsuario',
      'consultaUsuario',
      'consultaPaquete',
      'compraPaquete',
      'altaRutaVuelo',
      'altaVuelo',
      'reserva',  // Este es "reserva.jsp" (reservar vuelo), NO "consultaReserva"
      'inicio.jsp',
      'modificarUsuario'
    ];
    
    // Lista de enlaces que deben mostrarse en móvil
    const enlacesPermitidos = [
      'inicioSesion',
      'consultaRutaVuelo',
      'consultaVuelo',
      'consultaReserva'
    ];
    
    if (esMovil) {
      console.log('Ocultando elementos no permitidos en móvil...');
      
      // En móvil: ocultar elementos no permitidos
      enlacesAOcultar.forEach(patron => {
        const enlaces = sidebar.querySelectorAll(`a[href*="${patron}"]`);
        console.log(`Encontrados ${enlaces.length} enlaces con patrón "${patron}"`);
        enlaces.forEach(enlace => {
          enlace.style.display = 'none';
          enlace.setAttribute('data-mobile-hidden', 'true');
          
          // Ocultar también la sección padre si todos sus enlaces están ocultos
          const seccion = enlace.closest('.nav-section');
          if (seccion) {
            const todosLosEnlaces = seccion.querySelectorAll('a');
            const enlacesVisibles = Array.from(todosLosEnlaces).filter(a => 
              a.style.display !== 'none' && a.getAttribute('data-mobile-hidden') !== 'true'
            );
            if (enlacesVisibles.length === 0 && todosLosEnlaces.length > 0) {
              seccion.style.display = 'none';
              seccion.setAttribute('data-mobile-hidden', 'true');
            }
          }
        });
      });
      
      // Asegurar que los elementos permitidos sean visibles
      console.log('Mostrando elementos permitidos en móvil...');
      enlacesPermitidos.forEach(patron => {
        const enlaces = sidebar.querySelectorAll(`a[href*="${patron}"]`);
        console.log(`Mostrando ${enlaces.length} enlaces con patrón "${patron}"`);
        enlaces.forEach(enlace => {
          enlace.style.display = '';
          enlace.removeAttribute('data-mobile-hidden');
          const seccion = enlace.closest('.nav-section');
          if (seccion) {
            seccion.style.display = 'block';
            seccion.removeAttribute('data-mobile-hidden');
            // Asegurar que el título de la sección también sea visible
            const titulo = seccion.querySelector('.nav-section-title');
            if (titulo) {
              titulo.style.display = '';
            }
          }
        });
      });
      
      // Asegurar que la sección de Reservas sea visible si tiene consultaReserva
      // Buscar el enlace de consultaReserva y luego su sección padre
      const enlaceReserva = sidebar.querySelector('a[href*="consultaReserva"]');
      if (enlaceReserva) {
        enlaceReserva.style.display = '';
        enlaceReserva.removeAttribute('data-mobile-hidden');
        const seccionReservas = enlaceReserva.closest('.nav-section');
        if (seccionReservas) {
          seccionReservas.style.display = 'block';
          seccionReservas.removeAttribute('data-mobile-hidden');
          // Asegurar que el título también sea visible
          const titulo = seccionReservas.querySelector('.nav-section-title');
          if (titulo) {
            titulo.style.display = '';
          }
        }
        console.log('Sección de Reservas mostrada correctamente');
      } else {
        console.warn('No se encontró el enlace de consultaReserva');
      }
      
      // Ocultar botón de registro en el header
      const botonRegistro = document.querySelector('a[href*="registrarUsuario"]');
      if (botonRegistro && botonRegistro.closest('.header-icons')) {
        botonRegistro.style.display = 'none';
      }
      
    } else {
      console.log('Mostrando todos los elementos (desktop)...');
      
      // En desktop: mostrar todos los elementos
      const elementosOcultos = sidebar.querySelectorAll('[data-mobile-hidden="true"]');
      console.log(`Restaurando ${elementosOcultos.length} elementos ocultos`);
      elementosOcultos.forEach(el => {
        el.style.display = '';
        el.removeAttribute('data-mobile-hidden');
      });
      
      // Mostrar botón de registro en el header
      const botonRegistro = document.querySelector('a[href*="registrarUsuario"]');
      if (botonRegistro && botonRegistro.closest('.header-icons')) {
        botonRegistro.style.display = '';
      }
    }
    
    console.log('Ajuste del sidebar completado');
  }
  
  // Ejecutar detección al cargar (con pequeño delay para asegurar que el DOM esté listo)
  setTimeout(function() {
    detectarDispositivoMovil();
  }, 100);
  
  // Ejecutar al redimensionar la ventana
  let resizeTimer;
  window.addEventListener('resize', function() {
    clearTimeout(resizeTimer);
    resizeTimer = setTimeout(function() {
      detectarDispositivoMovil();
    }, 250);
  });
  
  // También ejecutar cuando el DOM esté completamente cargado
  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', function() {
      setTimeout(detectarDispositivoMovil, 100);
    });
  }
});
</script>

</body>

</html>

