<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.volandouy.helper.DeviceDetector" %>

<%
    boolean isMobilePhone = DeviceDetector.isMobilePhone(request);
    request.setAttribute("isMobilePhone", isMobilePhone);
%>

<c:if test="${not isMobilePhone}">
  <!-- Vista SOLO si NO es móvil: mensaje de bloqueo -->
  <section class="form-section-pro">
    <div class="form-card-pro">
      <h2>Realizar Check In</h2>
      <div class="info-inicial">
        <p style="text-align:center; margin-top:1rem;">
          Este caso de uso solo está disponible desde la
          <strong>versión móvil</strong> de VolandoUY.
        </p>
        <p style="text-align:center;">
          Por favor, accede desde tu teléfono para realizar el check in de tus reservas.
        </p>
      </div>
    </div>
  </section>
</c:if>

<c:if test="${isMobilePhone}">
  <!-- VISTA MOBILE REAL: acá va toda la lógica de Realizar Check In -->

  <section class="form-section-pro mobile-checkin-section">
    <div class="form-card-pro">
      <h2>Realizar Check In</h2>

      <!-- Información inicial / resumen -->
      <div id="info-inicial" class="info-inicial">
        <div id="loading-info" class="loading">Cargando información...</div>
        <div id="resumen-inicial" style="display:none;"></div>
      </div>

      <!-- Reservas SIN Check In (solo clientes) -->
      <div id="reservas-sin-checkin" class="section-content" style="display:none;">
        <div class="section-title">Reservas pendientes de Check In</div>
        <div id="listaReservasSinCheckin" class="grid-reservas"></div>
      </div>

      <!-- Detalle de reserva -->
      <div id="detalle-reserva" class="detalle-card" style="display:none;">
        <h3>Detalle de la Reserva</h3>
        <div id="detalleReserva"></div>
      </div>

      <!-- Mensajes de error -->
      <div id="mensaje-error" class="error-message" style="display:none;"></div>
    </div>
  </section>

  <style>
    /* Ajustes para que se vea bien en mobile full screen */
    .mobile-checkin-section {
      padding: 1rem;
    }

    .mobile-checkin-section .form-card-pro {
      margin: 0;
      border-radius: 16px;
    }

    .grid-reservas {
      display: flex;
      flex-direction: column;
      gap: 12px;
    }

    /* ⭐ Tarjeta mejorada para mobile (no toda blanca, texto legible) */
    .reserva-card {
      border-radius: 12px;
      padding: 12px 14px;
      background: #f5f7ff;                  /* celeste muy clarito */
      color: #0b1f33;                       /* texto azul oscuro */
      box-shadow: 0 2px 6px rgba(0,0,0,0.08);
      border: 1px solid rgba(0,0,0,0.05);
    }

    .reserva-card .card-body {
      font-size: 0.95rem;
    }

    .reserva-card h4 {
      font-size: 1rem;
      margin: 0;
      color: #0b1f33;
    }

    .reserva-card .card-body p {
      margin: 4px 0;
      font-size: 0.9rem;
      color: #444;
    }

    .card-header-row {
      display: flex;
      justify-content: space-between;
      align-items: center;
      gap: 8px;
    }

    .card-header-row h4 {
      font-size: 1rem;
      margin: 0;
    }

    .btn-checkin {
      border: none;
      background: #007bff;
      color: #fff;
      cursor: pointer;
      font-size: 0.85rem;
      padding: 6px 10px;
      border-radius: 999px;
      white-space: nowrap;
      display: inline-flex;
      align-items: center;
      gap: 4px;
    }

    .btn-checkin i {
      font-size: 0.8rem;
    }

    .btn-checkin:active {
      transform: scale(0.97);
    }

    .detalle-card {
      margin-top: 1rem;
      border-radius: 12px;
    }

    .detalle-completo p {
      margin-bottom: 4px;
      font-size: 0.9rem;
    }
  </style>

  <script>
    // ===== IMPORTANTE: base del API generada por el servidor =====
    const BASE_URL = '<c:url value="/api/consulta-reservas"/>';
    console.log('BASE_URL de consulta reservas (realizar check-in MOBILE):', BASE_URL);

    let infoInicial = null;

    document.addEventListener('DOMContentLoaded', function() {
        initConsultaReservaSinCheck();
    });

    function initConsultaReservaSinCheck() {
        console.log('Inicializando reservas SIN check in (MOBILE)...');
        limpiarInterfazSinCheck();
        cargarInfoInicialSinCheck();
    }

    function limpiarInterfazSinCheck() {
        const secciones = [
            'reservas-sin-checkin',
            'detalle-reserva'
        ];

        secciones.forEach(id => {
            const elemento = document.getElementById(id);
            if (elemento) {
                elemento.style.display = 'none';
                elemento.classList.remove('show', 'fade-in');
            }
        });

        const listas = ['listaReservasSinCheckin', 'detalleReserva', 'resumen-inicial'];
        listas.forEach(id => {
            const elemento = document.getElementById(id);
            if (elemento) {
                elemento.innerHTML = '';
            }
        });

        const mensajeError = document.getElementById('mensaje-error');
        if (mensajeError) {
            mensajeError.style.display = 'none';
        }
    }

    function cargarInfoInicialSinCheck() {
        console.log('Cargando información inicial desde:', BASE_URL);
        fetch(BASE_URL)
            .then(function (r) { return r.json(); })
            .then(function (info) {
                console.log('Información inicial recibida:', info);

                if (info.error) {
                    mostrarError(info.error);
                    ocultarLoadingSinCheck();
                    return;
                }

                infoInicial = info;

                ocultarLoadingSinCheck();
                mostrarResumenInicialSinCheck(info);

                if (info.tipoUsuario !== 'cliente') {
                    mostrarError('Solo los clientes pueden acceder a esta funcionalidad.');
                    return;
                }

                // Cargar reservas SIN check in
                cargarReservasSinCheckinCliente();
            })
            .catch(function (error) {
                console.error('Error al cargar información inicial:', error);
                ocultarLoadingSinCheck();
                mostrarError('Error al cargar la información inicial: ' + error.message);
            });
    }

    function ocultarLoadingSinCheck() {
        const loadingInfo = document.getElementById('loading-info');
        if (loadingInfo) {
            loadingInfo.style.display = 'none';
        }
    }

    function mostrarResumenInicialSinCheck(info) {
        const resumen = document.getElementById('resumen-inicial');
        if (!resumen) return;

        const usuario = info.usuarioLogueado || 'Desconocido';
        resumen.innerHTML = '<p><strong>Usuario:</strong> ' + usuario + '</p>';
        resumen.style.display = 'block';
    }

    /* ================================
       RESERVAS SIN CHECK IN (CLIENTE)
       ================================ */

    function cargarReservasSinCheckinCliente() {
        const url = BASE_URL + '/reservas-no-checkin';
        console.log('Cargando reservas SIN check in desde:', url);

        fetch(url)
            .then(function (r) { return r.json(); })
            .then(function (reservas) {
                console.log('Reservas SIN check in recibidas:', reservas);

                if (reservas && reservas.error) {
                    mostrarReservasSinCheckinCliente([]);
                    mostrarError(reservas.error);
                    return;
                }

                mostrarReservasSinCheckinCliente(reservas || []);
            })
            .catch(function (error) {
                console.error('Error al cargar reservas sin check in:', error);
                mostrarReservasSinCheckinCliente([]);
                mostrarError('Error al cargar las reservas sin check in: ' + error.message);
            });
    }

    function mostrarReservasSinCheckinCliente(reservas) {
        console.log('Mostrando reservas SIN check in (MOBILE):', reservas);

        const lista = document.getElementById('listaReservasSinCheckin');
        const seccion = document.getElementById('reservas-sin-checkin');

        if (!lista || !seccion) {
            console.error('No se encontró el contenedor de reservas sin check in');
            return;
        }

        lista.innerHTML = '';

        if (!reservas || reservas.length === 0) {
            lista.innerHTML = '<div class="text-center"><p>No tienes reservas pendientes de check in.</p></div>';
        } else {
            reservas.forEach((reserva, index) => {
                console.log('Construyendo card de reserva SIN check in:', reserva);

                const card = document.createElement('div');
                card.className = 'reserva-card fade-in';
                card.style.animationDelay = `${index * 0.05}s`;

                let html = '';
                html += '<div class="card-body">';
                html +=   '<div class="card-header-row">';
                html +=     '<h4>Reserva #' + (reserva.id || 'Sin ID') + '</h4>';
                html +=     '<button type="button" class="btn-checkin" ';
                html +=             'onclick="event.stopPropagation(); realizarCheckIn(' + reserva.id + ');">';
                html +=         '<i class="fas fa-check-circle"></i> Check In';
                html +=     '</button>';
                html +=   '</div>';

                if (reserva.vueloNombre) {
                    html += '<p style="margin-top:6px;"><strong>Vuelo:</strong> ' + reserva.vueloNombre + '</p>';
                }
                if (typeof reserva.costoTotal !== 'undefined') {
                    html += '<p><strong>Costo:</strong> $' + reserva.costoTotal + '</p>';
                }

                html +=   '<p style="margin-top:4px; font-size:0.85rem; color:#666;">Toca para ver el detalle completo.</p>';
                html += '</div>';

                card.innerHTML = html;

                if (reserva.id) {
                    card.addEventListener('click', () => {
                        console.log('Click en reserva SIN check in, ID:', reserva.id);
                        mostrarDetalleReserva(reserva.id);
                    });
                }

                lista.appendChild(card);
            });
        }

        seccion.style.display = 'block';
        seccion.classList.add('show', 'fade-in');
    }

    /* ================================
       DETALLE DE RESERVA (reutilizado)
       ================================ */

    function mostrarDetalleReserva(reservaId) {
        const url = BASE_URL + '/detalle-reserva/' + reservaId;
        console.log('Cargando detalle de reserva ID:', reservaId, 'desde', url);

        fetch(url)
            .then(function (r) { return r.json(); })
            .then(function (detalle) {
                if (detalle.error) {
                    mostrarError(detalle.error);
                    return;
                }

                mostrarDetalleEnInterfaz(detalle);
            })
            .catch(function (error) {
                console.error('Error al cargar detalle de reserva:', error);
                mostrarError('Error al cargar el detalle de la reserva: ' + error.message);
            });
    }

    function mostrarDetalleEnInterfaz(detalle) {
        console.log('Mostrando detalle completo (MOBILE):', detalle);

        const detalleReserva = document.getElementById('detalleReserva');
        const seccionDetalle = document.getElementById('detalle-reserva');

        detalleReserva.innerHTML = '';

        const detalleCompleto = document.createElement('div');
        detalleCompleto.className = 'detalle-completo fade-in';
        detalleCompleto.innerHTML =
            '<h4>Información de la Reserva</h4>' +
            '<p><strong>ID:</strong> ' + (detalle.id || 'Sin ID') + '</p>' +
            '<p><strong>Cliente:</strong> ' + (detalle.nicknameCliente || 'Sin cliente') + '</p>' +
            '<p><strong>Fecha de reserva:</strong> ' + (detalle.fechaReserva || 'Sin fecha') + '</p>' +
            '<p><strong>Costo:</strong> $' + (detalle.costoReserva && detalle.costoReserva.costoTotal ? detalle.costoReserva.costoTotal : 'N/A') + '</p>' +

            '<h4 style="margin-top:10px;">Información del Vuelo</h4>' +
            '<p><strong>Nombre:</strong> ' + (detalle.vuelo?.nombre || 'Sin nombre') + '</p>' +
            '<p><strong>Fecha:</strong> ' + (detalle.vuelo?.fechaVuelo || 'Sin fecha') + '</p>' +
            '<p><strong>Hora:</strong> ' + (detalle.vuelo?.horaVuelo || 'Sin hora') + '</p>' +
            '<p><strong>Duración:</strong> ' + (detalle.vuelo?.duracion || 'Sin duración') + '</p>' +

            '<h4 style="margin-top:10px;">Pasajeros</h4>' +
            '<p>' + obtenerTextoPasajeros(detalle.pasajeros) + '</p>';

        detalleReserva.appendChild(detalleCompleto);

        seccionDetalle.style.display = 'block';
        seccionDetalle.classList.add('show', 'fade-in');
    }

    // 💡 Nueva utilidad: limpiar detalle cuando ya no debe mostrarse
    function limpiarDetalleReserva() {
        const seccionDetalle = document.getElementById('detalle-reserva');
        const detalleReserva = document.getElementById('detalleReserva');

        if (detalleReserva) {
            detalleReserva.innerHTML = '';
        }
        if (seccionDetalle) {
            seccionDetalle.style.display = 'none';
            seccionDetalle.classList.remove('show', 'fade-in');
        }
    }

    /* ================================
       REALIZAR CHECK IN
       ================================ */

    function realizarCheckIn(reservaId) {
        const url = BASE_URL + '/realizar-checkin/' + reservaId;
        console.log('Realizando check in para reserva ID:', reservaId, 'en', url);

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(function (r) { return r.json(); })
            .then(function (data) {
                if (data.error) {
                    mostrarError('No se pudo realizar el check in: ' + data.error);
                    return;
                }

                if (typeof showToast === 'function') {
                    showToast('Check in realizado correctamente para la reserva #' + reservaId, 'success');
                } else {
                mostrarWarning('Check in realizado correctamente para la reserva #' + reservaId);
            }

            // 💡 Limpiar el detalle si estaba visible (para que no quede en pantalla)
            limpiarDetalleReserva();

            // Volver a cargar lista de pendientes
            cargarReservasSinCheckinCliente();

        } catch (error) {
            console.error('Error al realizar check in:', error);
            mostrarError('Error al realizar el check in: ' + error.message);
        }
    }

    /* ================================
       UTILIDADES
       ================================ */

    function obtenerTextoPasajeros(pasajeros) {
        console.log('Procesando pasajeros:', pasajeros);

        if (!pasajeros) {
            return 'Información no disponible';
        }

        if (Array.isArray(pasajeros)) {
            if (pasajeros.length === 0) {
                return 'No hay pasajeros registrados';
            }

            if (typeof pasajeros[0] === 'string') {
                return pasajeros.join(', ');
            }

            if (typeof pasajeros[0] === 'object') {
                const nombres = pasajeros.map(p => {
                    if (p.nombre) return p.nombre + (p.apellido ? ' ' + p.apellido : '');
                    if (p.nickname) return p.nickname;
                    return 'Pasajero sin nombre';
                });
                return nombres.join(', ');
            }

            return pasajeros.join(', ');
        }

        if (typeof pasajeros === 'object') {
            if (pasajeros.nombre) return pasajeros.nombre;
            if (pasajeros.nickname) return pasajeros.nickname;
            return 'Pasajero sin nombre';
        }

        if (typeof pasajeros === 'string') {
            return pasajeros;
        }

        return 'Formato de pasajeros no reconocido';
    }

    function mostrarError(mensaje) {
        console.error('Error mostrado al usuario:', mensaje);

        const mensajeError = document.getElementById('mensaje-error');
        if (mensajeError) {
            mensajeError.textContent = mensaje;
            mensajeError.style.display = 'block';
            mensajeError.className = 'error-message fade-in';

            setTimeout(() => {
                mensajeError.style.display = 'none';
                mensajeError.classList.remove('fade-in');
            }, 8000);
        } else {
            if (typeof showToast === 'function') {
                showToast('Error: ' + mensaje, 'error');
            } else {
                alert('Error: ' + mensaje);
            }
        }
    }

    function mostrarWarning(mensaje) {
        console.warn('Warning mostrado al usuario:', mensaje);

        const mensajeError = document.getElementById('mensaje-error');
        if (mensajeError) {
            mensajeError.textContent = mensaje;
            mensajeError.style.display = 'block';
            mensajeError.className = 'warning-message fade-in';

            setTimeout(() => {
                mensajeError.style.display = 'none';
                mensajeError.classList.remove('fade-in');
            }, 6000);
        } else {
            if (typeof showToast === 'function') {
                showToast('Advertencia: ' + mensaje, 'warning');
            } else {
                alert('Advertencia: ' + mensaje);
            }
        }
    }
  </script>
</c:if>
