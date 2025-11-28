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
  <!-- Bootstrap CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
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
    /* Estilos para ocultar elementos no permitidos en móvil usando Bootstrap */
    /* Solo los casos de uso permitidos en móvil deben ser visibles:
       - inicioSesion
       - consultaRutaVuelo
       - consultaVuelo
       - consultaReserva
       - Realizar Check-in (solo mobile)
    */
    @media (max-width: 767.98px) {
      /* Ocultar elementos que NO están permitidos en móvil */
      .nav-item[href*="registrarUsuario"],
      .nav-item[href*="consultaUsuario"]:not([href*="consultaRutaVuelo"]),
      .nav-item[href*="consultaPaquete"],
      .nav-item[href*="compraPaquete"],
      .nav-item[href*="altaRutaVuelo"],
      .nav-item[href*="altaVuelo"],
      .nav-item[href*="reserva"]:not([href*="consultaReserva"]),
      .nav-item[href*="inicio.jsp"],
      .nav-item[href*="modificarUsuario"],
      /* Consulta de Check In solo PC: se oculta en mobile */
      .nav-item[href*="consultacheckin.jsp"] {
        display: none !important;
      }

      /* Ocultar botón de registro en header en móvil */
      .header-icons .signup-btn {
        display: none !important;
      }

      /* Ocultar secciones completas que solo tienen elementos no permitidos */
      /* Sección "Mi Cuenta" - ocultar si solo tiene elementos no permitidos */
      .nav-section:has(.nav-item[href*="consultaUsuario"]:not([href*="consultaRutaVuelo"])):not(:has(.nav-item[href*="inicioSesion"])):not(:has(.nav-item[href*="consultaRutaVuelo"])):not(:has(.nav-item[href*="consultaVuelo"])):not(:has(.nav-item[href*="consultaReserva"])):not(:has(.nav-item[href*="consultacheckin"])) {
        display: none !important;
      }

      /* Sección "Paquetes" - ocultar completamente en móvil */
      .nav-section:has(.nav-item[href*="consultaPaquete"]) {
        display: none !important;
      }

      /* Mostrar sección de cerrar sesión solo en móvil */
      .nav-section.mobile-only {
        display: block !important;
        margin-top: 20px;
        padding-top: 20px;
        border-top: 1px solid rgba(1, 170, 245, 0.2);
      }

      /* Ocultar menú desplegable del usuario en móvil */
      .user-avatar-container {
        pointer-events: none;
      }

      .user-avatar-container .dropdown-arrow {
        display: none;
      }
    }

    /* Ocultar sección de cerrar sesión en desktop */
    @media (min-width: 768px) {
      .nav-section.mobile-only {
        display: none !important;
      }

      .user-avatar-container {
        pointer-events: auto;
      }

      .user-avatar-container .dropdown-arrow {
        display: inline-block;
      }

      /* Realizar Check In solo mobile: se esconde en desktop */
      .nav-item[href*="realizarCheckIn.jsp"] {
        display: none !important;
      }
    }
  </style>

  <!-- Definir contextPath para uso en JavaScript (debe estar antes de cualquier script) -->
  <script>
    (function() {
      var contextPath = '${pageContext.request.contextPath}';
      // Asegurar que tenga la barra inicial pero no la final
      window.APP_CONTEXT_PATH = contextPath || '';
      console.log('APP_CONTEXT_PATH definido como:', window.APP_CONTEXT_PATH);
    })();
  </script>
</head>

<body>

  <!-- Overlay para mobile / sidebar -->
  <div id="overlay" class="overlay" onclick="closeSidebar()"></div>

  <!-- Sidebar -->
  <aside class="sidebar" id="sidenav-1">
    <button class="close-btn" onclick="closeSidebar()" aria-label="Cerrar menú">×</button>

    <!-- Inicio - solo en desktop (oculto en móvil con CSS) -->
    <a href="inicio.jsp" class="nav-item d-none d-md-block">
      <i class="fas fa-home"></i>
      <span>Inicio</span>
    </a>

    <!-- Navegación para visitantes -->
    <c:if test="${empty sessionScope.usuarioLogueado}">
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

    <!-- Navegación para usuarios logueados -->
    <c:if test="${not empty sessionScope.usuarioLogueado}">
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

      <!-- Rutas -->
      <div class="nav-section">
        <h3 class="nav-section-title">Rutas</h3>
        <a href="consultaRutaVuelo.jsp" class="nav-item">
          <i class="fas fa-route"></i>
          <span>Consulta de Rutas</span>
        </a>
        <!-- Opciones específicas para aerolíneas (rutas) -->
        <c:if test="${sessionScope.tipoUsuario == 'aerolinea'}">
          <a href="altaRutaVuelo.jsp" class="nav-item">
            <i class="fas fa-plus-circle"></i>
            <span>Alta y Finalizacion de Ruta</span>
          </a>
        </c:if>
      </div>

      <!-- Vuelos -->
      <div class="nav-section">
        <h3 class="nav-section-title">Vuelos</h3>
        <a href="consultaVuelo.jsp" class="nav-item">
          <i class="fas fa-plane"></i>
          <span>Consulta de Vuelos</span>
        </a>

        <!-- Opciones específicas para aerolíneas (vuelos) -->
        <c:if test="${sessionScope.tipoUsuario == 'aerolinea'}">
          <a href="altaVuelo.jsp" class="nav-item">
            <i class="fas fa-plus-circle"></i>
            <span>Alta de Vuelo</span>
          </a>
        </c:if>

        <!-- Opciones específicas para clientes (reservar vuelo) -->
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

        <!-- Realizar Check In: solo mobile (se oculta en desktop via CSS) -->
        <a href="realizarCheckIn.jsp" class="nav-item">
          <i class="fas fa-check-circle"></i>
          <span>Realizar Check In</span>
        </a>

        <!-- Consulta de Check In: solo desktop (se oculta en mobile via CSS) -->
        <a href="consultacheckin.jsp" class="nav-item">
          <i class="fas fa-route"></i>
          <span>Consulta de Check In</span>
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

      <!-- Cerrar Sesión - Solo para móvil y clientes -->
      <c:if test="${sessionScope.tipoUsuario == 'cliente'}">
        <div class="nav-section mobile-only">
          <button onclick="cerrarSesion()" class="nav-item logout-nav-item">
            <i class="fas fa-sign-out-alt"></i>
            <span>Cerrar Sesión</span>
          </button>
        </div>
      </c:if>
    </c:if>
  </aside>

  <!-- Main content -->
  <div class="main">

    <!-- Header -->
    <header class="header">
      <!-- Lado izquierdo: menú + buscador -->
      <div class="header-left">
        <!-- Toggler responsive -->
        <button data-mdb-button-init data-mdb-toggle="sidenav" data-mdb-target="#sidenav-1" data-mdb-ripple-init
          class="sidenav-toggler" id="menuBtn" aria-controls="#sidenav-1" aria-haspopup="true" aria-label="Abrir menú">
          <i class="fas fa-bars" aria-hidden="true"></i>
        </button>

      <!-- BUSCADOR GLOBAL (va directo a busqueda.jsp) -->
      <div class="global-search-container">
        <form action="busqueda.jsp" method="get" class="global-search-form">
          <input
            type="text"
            name="q"
            class="global-search-input"
            placeholder="Buscar rutas o paquetes..."
            autocomplete="off"
          />
          <button type="submit" class="global-search-btn">
            <i class="fas fa-search"></i>
          </button>
        </form>
      </div>


      <!-- Logo y branding (centrado) -->
      <div class="brand-container">
        <div class="logo-container">
          <img src="static/img/logoAvionSolo.png" alt="VolandoUY Logo" class="logo-img">
          <div class="brand-text">
            <h1 class="brand-title">VolandoUY</h1>
            <p class="brand-slogan">Conectamos tus rutas, acercamos tus destinos</p>
          </div>
        </div>
      </div>

      <!-- Header icons a la derecha (usuario) -->
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
            <a href="registrarUsuario.jsp" class="signup-btn">
              <i class="fas fa-user-plus"></i>
              <span class="btn-text">Registrarse</span>
            </a>
          </c:otherwise>
        </c:choose>
      </div>

      <!-- Estilos específicos del header + buscador -->
      <style>
        .header {
          display: flex;
          align-items: center;
          justify-content: space-between;
          gap: 1rem;
        }

        .header-left {
          display: flex;
          align-items: center;
          gap: 0.75rem;
          flex: 1 1 auto;
        }

        .brand-container {
          flex: 0 0 auto;
        }

        .header-icons {
          flex: 0 0 auto;
        }

        .global-search-container {
          flex: 1 1 auto;
          max-width: 360px;
        }

        .global-search-form {
          width: 100%;
          display: flex;
          align-items: center;
          background: rgba(15, 23, 42, 0.9);
          border-radius: 999px;
          border: 1px solid rgba(34, 152, 202, 0.8);
          box-shadow: 0 10px 30px rgba(0, 0, 0, 0.6);
          overflow: hidden;
        }

        .global-search-input {
          flex: 1 1 auto;
          padding: 0.45rem 0.9rem 0.45rem 1rem;
          border: none;
          outline: none;
          font-size: 0.9rem;
          background: transparent;
          color: #e5f4ff;
        }

        .global-search-input::placeholder {
          color: rgba(148, 163, 184, 0.9);
        }

        .global-search-btn {
          background: linear-gradient(135deg, #01aaf5 0%, #007bbd 100%);
          border: none;
          width: 42px;
          height: 34px;
          margin-right: 4px;
          margin-top: 2px;
          margin-bottom: 2px;
          border-radius: 999px;
          color: white;
          cursor: pointer;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 0.9rem;
          box-shadow: 0 8px 22px rgba(1, 170, 245, 0.55);
          transition: transform 0.15s ease, box-shadow 0.15s ease;
        }

        .global-search-btn:hover {
          transform: translateY(-1px);
          box-shadow: 0 10px 26px rgba(1, 170, 245, 0.75);
        }

        @media (max-width: 992px) {
          .global-search-container {
            max-width: 260px;
          }
        }

        @media (max-width: 768px) {
          .header {
            gap: 0.6rem;
          }
          .global-search-container {
            max-width: 220px;
          }
          .brand-container .brand-slogan {
            display: none;
          }
        }

        @media (max-width: 576px) {
          .global-search-container {
            display: none; /* si en mobile prefieres no mostrar el buscador en el header */
          }
        }
      </style>
    </header>

    <div id="main-content" class="${param.content == 'inicioSesion-content.jsp' ? 'login-page' : ''}">
      <!-- Contenido específico de cada página -->
      <c:choose>
        <c:when test="${not empty param.content}">
          <jsp:include page="${param.content}" />
        </c:when>
        <c:otherwise>
          <jsp:include page="inicio-content.jsp" />
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

  <!-- Bootstrap JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>

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
      // No permitir que se despliegue en móvil
      const isMobile = window.innerWidth <= 767;
      if (isMobile) {
        return; // No hacer nada en móvil
      }

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
      const sidebarEl = document.getElementById('sidenav-1');
      if (sidebarEl) {
        if (sidebarEl.scrollHeight > sidebarEl.clientHeight) {
          sidebarEl.classList.add('scrollable');
        } else {
          sidebarEl.classList.remove('scrollable');
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

      // Si no hay carrusel en la página, no hacer nada
      if (!carousel || !prevBtn || !nextBtn) {
        console.log("No hay carrusel en esta página, se omite inicialización.");
        return;
      }

      let currentIndex = 0;
      const cards = carousel.querySelectorAll(".offer-card");
      const totalCards = cards.length;

      if (totalCards === 0) {
        console.warn("Carrusel encontrado pero sin tarjetas.");
        return;
      }

      // Lógica normal del carrusel
      function showCard(index) {
        const step = getStep(); // ancho de una tarjeta + gap
        const targetScroll = index * step;
        carousel.scrollTo({
          left: targetScroll,
          behavior: 'smooth'
        });
      }

      prevBtn.addEventListener("click", () => {
        currentIndex = (currentIndex - 1 + totalCards) % totalCards;
        showCard(currentIndex);
      });

      nextBtn.addEventListener("click", () => {
        currentIndex = (currentIndex + 1) % totalCards;
        showCard(currentIndex);
      });

      // Mostrar la primera tarjeta al inicio
      showCard(currentIndex);
    }

    // Solo inicializar si existe carrusel en la página
    document.addEventListener("DOMContentLoaded", () => {
      if (document.getElementById("carousel")) {
        initCarousel();
      }
    });

    function getStep() {
      const carousel = document.getElementById("carousel");
      const card = carousel.querySelector('.offer-card');
      if (!card) return 235;
      const gapValue = getComputedStyle(carousel).gap || '15px';
      const gap = parseInt(gapValue, 10) || 15;
      return card.offsetWidth + gap;
    }

    function scrollToIndex(index) {
      const carousel = document.getElementById("carousel");
      const step = getStep();
      const targetScroll = index * step;
      carousel.scrollTo({
        left: targetScroll,
        behavior: 'smooth'
      });
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
  });
  </script>

</body>

</html>
