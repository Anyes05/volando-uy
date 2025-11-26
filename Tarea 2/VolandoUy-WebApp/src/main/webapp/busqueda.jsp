<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="contenido-principal busqueda-wrapper">
  <!-- HERO SUPERIOR, ESTILO PORTADA -->
  <section class="busqueda-hero">
    <div class="busqueda-hero-left">
      <h1 class="busqueda-title">
        <i class="fas fa-search"></i>
        Resultados de búsqueda
      </h1>
      <p class="busqueda-subtitle">
        Estás viendo resultados para
        <strong>"<c:out value="${param.q}" />"</strong>.
        Te mostramos rutas de vuelo y paquetes que coinciden con tu búsqueda.
      </p>
    </div>

    <div class="busqueda-hero-right">
      <!-- Botón para volver al home -->
      <a href="<c:url value='inicio.jsp' />" class="btn-volver-home">
        <i class="fas fa-home"></i>
        Volver al inicio
      </a>
    </div>
  </section>

  <!-- SECCIÓN PRINCIPAL DE RESULTADOS -->
  <section class="busqueda-section">
    <div class="busqueda-card">
      <!-- Barra de orden / info -->
      <div class="search-toolbar">
        <div class="search-toolbar-left">
          <i class="fas fa-filter"></i>
          <span>Resultados:</span>
          <span class="search-count" id="search-count">0</span>
        </div>
        <div class="search-toolbar-right">
          <label for="orden-select" class="orden-label">Ordenar por</label>
          <select id="orden-select" class="orden-select">
            <option value="fecha">Por defecto (Fecha más reciente)</option>
            <option value="alfabetico">Alfabético (A-Z)</option>
          </select>
        </div>
      </div>

      <!-- LISTA DE RESULTADOS -->
      <div id="resultados-list" class="resultados-list"></div>
    </div>
  </section>
</div>

<style>
  /* ===== FONDO GENERAL TIPO "CONSULTA DE VUELO" ===== */
  body {
    margin: 0;
    /* Fondo oscuro con degradado estilo VolandoUY */
    background:
      radial-gradient(circle at 0% 0%, #0b2937 0%, #051821 40%, #020910 100%);
    color: #e5e7eb;
  }

  /* El wrapper de contenido en esta página no debe tener fondo blanco */
  .contenido-principal.busqueda-wrapper {
    background: transparent;
    padding: 24px 40px 40px;
    min-height: calc(100vh - 80px); /* ajustá si tenés header/footer altos */
    box-sizing: border-box;
  }

  /* ===== CONTEXTO GENERAL ===== */
  .busqueda-wrapper {
    font-family: 'Poppins', system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
  }

  /* ===== HERO SUPERIOR (similar portada) ===== */
  .busqueda-hero {
    background: linear-gradient(135deg, #2298ca 0%, #36b0e8 60%, #2ab0d8 100%);
    border-radius: 24px;
    padding: 1.6rem 2rem;
    margin-bottom: 1.5rem;
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 1.5rem;
    box-shadow: 0 18px 55px rgba(0, 0, 0, 0.5);
    position: relative;
    overflow: hidden;
  }

  .busqueda-hero::before {
    content: '';
    position: absolute;
    inset: -40%;
    background:
      radial-gradient(circle at 0% 0%, rgba(255,255,255,0.20), transparent 55%),
      radial-gradient(circle at 100% 0%, rgba(255,255,255,0.10), transparent 55%);
    opacity: 0.7;
    pointer-events: none;
  }

  .busqueda-hero-left,
  .busqueda-hero-right {
    position: relative;
    z-index: 1;
  }

  .busqueda-title {
    margin: 0;
    font-size: 2.1rem;
    font-weight: 700;
    color: #f9fafb;
    display: flex;
    align-items: center;
    gap: 0.75rem;
    text-shadow: 0 3px 8px rgba(0, 0, 0, 0.35);
  }

  .busqueda-title i {
    font-size: 1.9rem;
  }

  .busqueda-subtitle {
    margin: 0.6rem 0 0 0;
    color: #eaf6fb;
    font-size: 1.02rem;
    max-width: 600px;
  }

  .busqueda-subtitle strong {
    color: #0b1120;
    background: rgba(255, 255, 255, 0.9);
    padding: 0.1rem 0.5rem;
    border-radius: 999px;
    font-weight: 700;
  }

  .busqueda-hero-right {
    display: flex;
    align-items: center;
    justify-content: flex-end;
  }

  .btn-volver-home {
    display: inline-flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.7rem 1.4rem;
    border-radius: 999px;
    border: 2px solid rgba(15, 23, 42, 0.12);
    background: rgba(15, 23, 42, 0.18);
    color: #f9fafb;
    font-weight: 600;
    font-size: 0.95rem;
    text-decoration: none;
    box-shadow: 0 10px 30px rgba(15, 23, 42, 0.45);
    transition: all 0.25s ease;
    backdrop-filter: blur(10px);
    white-space: nowrap;
  }

  .btn-volver-home i {
    font-size: 1rem;
  }

  .btn-volver-home:hover {
    background: rgba(15, 23, 42, 0.35);
    transform: translateY(-2px);
    box-shadow: 0 16px 40px rgba(15, 23, 42, 0.7);
  }

  .btn-volver-home:active {
    transform: translateY(0);
    box-shadow: 0 8px 24px rgba(15, 23, 42, 0.6);
  }

  /* ===== SECCIÓN PRINCIPAL / CARD ===== */
  .busqueda-section {
    margin-top: 0.5rem;
  }

  .busqueda-card {
    background:
      radial-gradient(circle at top left, rgba(34,152,202,0.3), transparent 55%),
      radial-gradient(circle at bottom right, rgba(1,170,245,0.32), transparent 60%),
      linear-gradient(135deg, #32434b 0%, #1b262d 100%);
    border-radius: 22px;
    padding: 1.7rem 1.6rem 1.5rem;
    border: 1px solid rgba(148, 163, 184, 0.4);
    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.75);
    position: relative;
    overflow: hidden;
  }

  .busqueda-card::before {
    content: '';
    position: absolute;
    inset: 0;
    background: linear-gradient(
      135deg,
      rgba(255,255,255,0.05) 0%,
      transparent 45%,
      rgba(255,255,255,0.02) 100%
    );
    pointer-events: none;
    mix-blend-mode: screen;
  }

  .busqueda-card > * {
    position: relative;
    z-index: 1;
  }

  /* ===== BARRA SUPERIOR (FILTRO / INFO) ===== */
  .search-toolbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 1rem;
    margin-bottom: 1.25rem;
    padding: 0.9rem 1.1rem;
    border-radius: 16px;
    background: linear-gradient(
      135deg,
      rgba(1,170,245,0.18) 0%,
      rgba(34,152,202,0.12) 35%,
      rgba(5,24,35,0.95) 100%
    );
    border: 1px solid rgba(34, 152, 202, 0.65);
    box-shadow: 0 12px 40px rgba(0, 0, 0, 0.6);
  }

  .search-toolbar-left {
    display: flex;
    align-items: center;
    gap: 0.6rem;
    color: #eaf6fb;
    font-size: 0.96rem;
  }

  .search-toolbar-left i {
    color: #7dd3fc;
    font-size: 1.05rem;
  }

  .search-count {
    font-weight: 700;
    color: #7dd3fc;
  }

  .search-toolbar-right {
    display: flex;
    align-items: center;
    gap: 0.5rem;
  }

  .orden-label {
    font-size: 0.9rem;
    color: rgba(234, 246, 251, 0.9);
  }

  .orden-select {
    padding: 0.45rem 0.9rem;
    border-radius: 999px;
    border: 2px solid rgba(34, 152, 202, 0.7);
    background: rgba(15, 23, 42, 0.95);
    color: #eaf6fb;
    font-size: 0.9rem;
    outline: none;
    transition: all 0.25s ease;
    box-shadow: 0 6px 20px rgba(0, 0, 0, 0.6);
    min-width: 220px;
  }

  .orden-select:hover {
    border-color: #01aaf5;
  }

  .orden-select:focus {
    border-color: #01aaf5;
    box-shadow: 0 0 26px rgba(1, 170, 245, 0.5);
  }

  /* ===== GRID DE RESULTADOS ===== */
  .resultados-list {
    margin-top: 1.1rem;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(270px, 1fr));
    gap: 1.2rem;
  }

  .resultado-card {
    position: relative;
    background:
      radial-gradient(circle at top left, rgba(34,152,202,0.28), transparent 55%),
      radial-gradient(circle at bottom right, rgba(1,170,245,0.22), transparent 60%),
      linear-gradient(145deg, #32434b 0%, #101820 100%);
    border-radius: 16px;
    padding: 1rem 1rem 0.9rem;
    border: 1px solid rgba(148, 163, 184, 0.55);
    box-shadow: 0 16px 40px rgba(0, 0, 0, 0.8);
    color: #eaf6fb;
    overflow: hidden;
    transition: transform 0.2s ease, box-shadow 0.2s ease, border-color 0.2s ease, background 0.2s ease;
  }

  .resultado-card::before {
    content: '';
    position: absolute;
    inset: 0;
    background: linear-gradient(
      135deg,
      rgba(255,255,255,0.06) 0%,
      transparent 45%,
      rgba(255,255,255,0.03) 100%
    );
    pointer-events: none;
    mix-blend-mode: screen;
  }

  .resultado-card > * {
    position: relative;
    z-index: 1;
  }

  .resultado-card:hover {
    transform: translateY(-4px) scale(1.02);
    box-shadow: 0 24px 55px rgba(0, 0, 0, 0.95);
    border-color: rgba(1,170,245,0.9);
  }

  .resultado-tipo-chip {
    display: inline-flex;
    align-items: center;
    gap: 0.35rem;
    padding: 0.22rem 0.7rem;
    border-radius: 999px;
    font-size: 0.7rem;
    text-transform: uppercase;
    letter-spacing: 0.12em;
    background: rgba(15, 23, 42, 0.9);
    border: 1px solid rgba(125, 211, 252, 0.9);
    color: #e0f2fe;
    margin-bottom: 0.35rem;
    box-shadow: 0 8px 22px rgba(15, 23, 42, 0.95);
  }

  .resultado-tipo-chip i {
    font-size: 0.8rem;
    color: #7dd3fc;
  }

  .resultado-card h3 {
    margin: 0.25rem 0 0.35rem 0;
    font-size: 1.05rem;
    font-weight: 600;
    color: #f9fafb;
    text-shadow: 0 2px 6px rgba(0, 0, 0, 0.7);
  }

  .resultado-descripcion {
    margin: 0;
    font-size: 0.9rem;
    color: rgba(226, 232, 240, 0.96);
    line-height: 1.5;
  }

  .resultado-meta {
    margin-top: 0.7rem;
    padding-top: 0.55rem;
    border-top: 1px solid rgba(148, 163, 184, 0.7);
    display: flex;
    flex-wrap: wrap;
    gap: 0.75rem;
    font-size: 0.8rem;
  }

  .resultado-meta-item {
    display: inline-flex;
    align-items: center;
    gap: 0.35rem;
    color: rgba(226, 232, 240, 0.9);
  }

  .resultado-meta-item i {
    font-size: 0.85rem;
    color: #7dd3fc;
  }

  /* Mensaje sin resultados */
  .sin-resultados {
    grid-column: 1 / -1;
    text-align: center;
    padding: 1.8rem 1rem;
    border-radius: 16px;
    background: linear-gradient(135deg, #2a3a42 0%, #141c22 100%);
    border: 1px solid rgba(248, 250, 252, 0.12);
    color: #eaf6fb;
    font-size: 0.95rem;
    box-shadow: 0 18px 48px rgba(0, 0, 0, 0.8);
  }

  .sin-resultados i {
    display: block;
    font-size: 1.8rem;
    margin-bottom: 0.5rem;
    color: #7dd3fc;
    opacity: 0.95;
  }

  .sin-resultados p {
    margin: 0.2rem 0;
  }

  /* ===== RESPONSIVE ===== */
  @media (max-width: 992px) {
    .busqueda-hero {
      flex-direction: column;
      align-items: flex-start;
      padding: 1.5rem 1.4rem;
    }

    .busqueda-hero-right {
      width: 100%;
      justify-content: flex-start;
    }

    .busqueda-card {
      padding: 1.5rem 1.3rem;
    }

    .contenido-principal.busqueda-wrapper {
      padding: 20px;
    }
  }

  @media (max-width: 768px) {
    .busqueda-title {
      font-size: 1.7rem;
    }

    .busqueda-subtitle {
      font-size: 0.97rem;
    }

    .search-toolbar {
      flex-direction: column;
      align-items: flex-start;
    }

    .search-toolbar-right {
      width: 100%;
      justify-content: flex-start;
    }

    .orden-select {
      width: 100%;
      min-width: 0;
    }

    .resultados-list {
      grid-template-columns: 1fr;
    }
  }

  @media (max-width: 480px) {
    .busqueda-hero {
      padding: 1.2rem 1rem;
      border-radius: 18px;
    }

    .busqueda-title {
      font-size: 1.5rem;
    }

    .busqueda-card {
      padding: 1.2rem 0.9rem;
    }

    .busqueda-subtitle {
      font-size: 0.9rem;
    }

    .btn-volver-home {
      width: 100%;
      justify-content: center;
    }

    .contenido-principal.busqueda-wrapper {
      padding: 16px;
    }
  }
</style>

<script>
  document.addEventListener("DOMContentLoaded", function () {
    // Obtener parámetro "q" de la URL
    var urlParams = new URLSearchParams(window.location.search);
    var q = urlParams.get("q") || "";

    // URL del endpoint respetando el contexto
    var BUSQUEDA_URL = "<c:url value='/api/busqueda' />";

    fetch(BUSQUEDA_URL + "?q=" + encodeURIComponent(q))
      .then(function (r) { return r.json(); })
      .then(function (data) {
        if (!Array.isArray(data)) {
          console.error("Respuesta no es lista:", data);
          renderResultados([]);
          return;
        }
        window.__resultadosBusqueda = data;
        renderResultados(data);
      })
      .catch(function (err) {
        console.error("Error en fetch /api/busqueda:", err);
        renderResultados([]);
      });

    function renderResultados(lista) {
      var cont = document.getElementById("resultados-list");
      var countSpan = document.getElementById("search-count");
      cont.innerHTML = "";

      var cantidad = (lista && lista.length) ? lista.length : 0;
      if (countSpan) {
        countSpan.textContent = cantidad + " resultado" + (cantidad === 1 ? "" : "s");
      }

      if (!lista || lista.length === 0) {
        cont.innerHTML =
          "<div class='sin-resultados'>" +
            "<i class='fas fa-info-circle'></i>" +
            "<p>No se encontraron rutas de vuelo ni paquetes que coincidan con tu búsqueda.</p>" +
            "<p>Probá con otro término o usando palabras más generales.</p>" +
          "</div>";
        return;
      }

      lista.forEach(function (item) {
        var tipo = item.tipo ? String(item.tipo) : "Item";
        var nombre = item.nombre || "Sin nombre";
        var descripcion = item.descripcion || "Sin descripción";
        var fechaAlta = item.fechaAlta || "N/A";
        var aerolinea = item.aerolinea || null;

        var tipoUpper = tipo.toUpperCase();
        var tipoIcon = (tipoUpper === "RUTA" || tipoUpper === "RUTA DE VUELO")
          ? "fa-route"
          : (tipoUpper === "PAQUETE" ? "fa-box" : "fa-tag");

        var metaHtml =
          "<span class='resultado-meta-item'><i class='fas fa-calendar-alt'></i>" +
            escapeHtml(fechaAlta) +
          "</span>";

        if (aerolinea) {
          metaHtml +=
            "<span class='resultado-meta-item'><i class='fas fa-plane'></i>" +
              escapeHtml(aerolinea) +
            "</span>";
        }

        cont.innerHTML +=
          "<div class='resultado-card'>" +
            "<div class='resultado-tipo-chip'>" +
              "<i class='fas " + tipoIcon + "'></i>" +
              "<span>" + escapeHtml(tipoUpper) + "</span>" +
            "</div>" +
            "<h3>" + escapeHtml(nombre) + "</h3>" +
            "<p class='resultado-descripcion'>" + escapeHtml(descripcion) + "</p>" +
            "<div class='resultado-meta'>" + metaHtml + "</div>" +
          "</div>";
      });
    }

    // Ordenar por select
    var ordenSelect = document.getElementById("orden-select");
    ordenSelect.addEventListener("change", function (e) {
      var lista = (window.__resultadosBusqueda || []).slice();

      if (e.target.value === "alfabetico") {
        lista.sort(function (a, b) {
          var na = (a.nombre || "").toLowerCase();
          var nb = (b.nombre || "").toLowerCase();
          return na.localeCompare(nb);
        });
      } else {
        // Fecha descendente (asumiendo ISO o valores comparables como string)
        lista.sort(function (a, b) {
          var fa = a.fechaAlta || "";
          var fb = b.fechaAlta || "";
          return fb.localeCompare(fa);
        });
      }

      renderResultados(lista);
    });

    // Util para escapar HTML
    function escapeHtml(str) {
      if (!str) return "";
      return String(str)
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#39;");
    }
  });
</script>
