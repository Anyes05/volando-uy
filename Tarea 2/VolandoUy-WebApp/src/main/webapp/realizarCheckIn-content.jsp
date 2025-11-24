<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="form-section-pro">
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
.card-header-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.btn-checkin {
  border: none;
  background: #007bff;
  color: #fff;
  cursor: pointer;
  font-size: 0.9rem;
  padding: 6px 12px;
  border-radius: 6px;
  transition: 0.2s;
}

.btn-checkin:hover {
  background: #0056b3;
}
</style>

<script>
// ===== IMPORTANTE: base del API generada por el servidor =====
const BASE_URL = '<c:url value="/api/consulta-reservas"/>';
console.log('BASE_URL de consulta reservas (realizar check-in):', BASE_URL);

// Variables globales
let infoInicial = null;

document.addEventListener('DOMContentLoaded', function() {
    initConsultaReservaSinCheck();
});

function initConsultaReservaSinCheck() {
    console.log('Inicializando consulta de reservas SIN check in (solo cliente)...');
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

async function cargarInfoInicialSinCheck() {
    try {
        console.log('Cargando información inicial desde:', BASE_URL);
        const response = await fetch(BASE_URL);

        console.log('Respuesta info inicial status:', response.status, response.statusText);

        if (!response.ok) {
            throw new Error('HTTP ' + response.status + ': ' + response.statusText);
        }

        const info = await response.json();
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
            mostrarError('Solo los clientes pueden acceder a esta consulta de reservas.');
            return;
        }

        // Cargar reservas SIN check in del cliente (para realizar check in)
        cargarReservasSinCheckinCliente();

    } catch (error) {
        console.error('Error al cargar información inicial:', error);
        ocultarLoadingSinCheck();
        mostrarError('Error al cargar la información inicial: ' + error.message);
    }
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

async function cargarReservasSinCheckinCliente() {
    try {
        const url = BASE_URL + '/reservas-no-checkin';
        console.log('Cargando reservas SIN check in del cliente desde:', url);

        const response = await fetch(url);

        console.log('Respuesta reservas-no-checkin status:', response.status, response.statusText);

        if (!response.ok) {
            mostrarReservasSinCheckinCliente([]);
            throw new Error('HTTP ' + response.status + ': ' + response.statusText);
        }

        const reservas = await response.json();
        console.log('Reservas SIN check in recibidas:', reservas);

        if (reservas && reservas.error) {
            mostrarReservasSinCheckinCliente([]);
            mostrarError(reservas.error);
            return;
        }

        mostrarReservasSinCheckinCliente(reservas || []);

    } catch (error) {
        console.error('Error al cargar reservas sin check in:', error);
        mostrarReservasSinCheckinCliente([]);
        mostrarError('Error al cargar las reservas sin check in: ' + error.message);
    }
}

function mostrarReservasSinCheckinCliente(reservas) {
    console.log('Reservas SIN check in (crudo del backend):', reservas);

    const lista = document.getElementById('listaReservasSinCheckin');
    const seccion = document.getElementById('reservas-sin-checkin');

    if (!lista || !seccion) {
        console.error('No se encontró el contenedor de reservas sin check in');
        return;
    }

    lista.innerHTML = '';

    // 👉 Ajustá el nombre de la propiedad según tu DTO:
    //    puede ser checkinRealizado, checkInRealizado, realizadoCheckIn, etc.
    const pendientes = (reservas || []).filter(r => !r.checkinRealizado);

    console.log('Reservas pendientes (filtradas en JS):', pendientes);

    if (pendientes.length === 0) {
        lista.innerHTML = '<div class="text-center"><p>No tienes reservas pendientes de check in.</p></div>';
    } else {
        pendientes.forEach((reserva, index) => {
            const card = document.createElement('div');
            card.className = 'reserva-card fade-in';
            card.style.animationDelay = `${index * 0.1}s`;

            let html = '';
            html += '<div class="card-body">';
            html +=   '<div class="card-header-row">';
            html +=     '<h4>Reserva pendiente #' + (reserva.id || 'Sin ID') + '</h4>';
            html +=     '<button type="button" class="btn-checkin" title="Realizar Check In" ';
            html +=             'onclick="event.stopPropagation(); realizarCheckIn(' + reserva.id + ');">';
            html +=         'Realizar Check In';
            html +=     '</button>';
            html +=   '</div>';
            html +=   '<p>Haz clic en la tarjeta para ver el detalle completo.</p>';
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

async function mostrarDetalleReserva(reservaId) {
    try {
        const url = BASE_URL + '/detalle-reserva/' + reservaId;
        console.log('Cargando detalle de reserva ID:', reservaId, 'desde', url);

        const response = await fetch(url);

        console.log('Respuesta detalle-reserva status:', response.status, response.statusText);

        if (!response.ok) {
            throw new Error('HTTP ' + response.status + ': ' + response.statusText);
        }

        const detalle = await response.json();

        if (detalle.error) {
            mostrarError(detalle.error);
            return;
        }

        mostrarDetalleEnInterfaz(detalle);

    } catch (error) {
        console.error('Error al cargar detalle de reserva:', error);
        mostrarError('Error al cargar el detalle de la reserva: ' + error.message);
    }
}

function mostrarDetalleEnInterfaz(detalle) {
    console.log('Mostrando detalle completo:', detalle);

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
        '<p><strong>Costo:</strong> $' + (detalle.costoReserva ? (detalle.costoReserva.costoTotal || 'N/A') : 'N/A') + '</p>' +

        '<h4>Información del Vuelo</h4>' +
        '<p><strong>Nombre:</strong> ' + (detalle.vuelo?.nombre || 'Sin nombre') + '</p>' +
        '<p><strong>Fecha:</strong> ' + (detalle.vuelo?.fechaVuelo || 'Sin fecha') + '</p>' +
        '<p><strong>Hora:</strong> ' + (detalle.vuelo?.horaVuelo || 'Sin hora') + '</p>' +
        '<p><strong>Duración:</strong> ' + (detalle.vuelo?.duracion || 'Sin duración') + '</p>' +

        '<h4>Información de la Ruta</h4>' +
        '<p><strong>Nombre:</strong> ' + (detalle.ruta?.nombre || 'Sin nombre') + '</p>' +
        '<p><strong>Descripción:</strong> ' + (detalle.ruta?.descripcion || 'Sin descripción') + '</p>' +
        '<p><strong>Origen:</strong> ' + (detalle.ruta?.ciudadOrigen || 'Sin origen') + '</p>' +
        '<p><strong>Destino:</strong> ' + (detalle.ruta?.ciudadDestino || 'Sin destino') + '</p>' +

        '<h4>Información de la Aerolínea</h4>' +
        '<p><strong>Nombre:</strong> ' + (detalle.aerolinea?.nombre || 'Sin nombre') + '</p>' +
        '<p><strong>Descripción:</strong> ' + (detalle.aerolinea?.descripcion || 'Sin descripción') + '</p>' +

        '<h4>Pasajeros</h4>' +
        '<p>' + obtenerTextoPasajeros(detalle.pasajeros) + '</p>';

    detalleReserva.appendChild(detalleCompleto);

    seccionDetalle.style.display = 'block';
    seccionDetalle.classList.add('show', 'fade-in');
}

/* ================================
   REALIZAR CHECK IN
   ================================ */

async function realizarCheckIn(reservaId) {
    try {
        const url = BASE_URL + '/realizar-checkin/' + reservaId;
        console.log('Realizando check in para reserva ID:', reservaId, 'en', url);

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        console.log('Respuesta realizar-checkin status:', response.status, response.statusText);

        const data = await response.json().catch(() => ({}));

        if (!response.ok) {
            const msg = data.error || ('HTTP ' + response.status + ': ' + response.statusText);
            mostrarError('No se pudo realizar el check in: ' + msg);
            return;
        }

        if (data.error) {
            mostrarError('No se pudo realizar el check in: ' + data.error);
            return;
        }

        // Éxito
        if (typeof showToast === 'function') {
            showToast('Check in realizado correctamente para la reserva #' + reservaId, 'success');
        } else {
            mostrarWarning('Check in realizado correctamente para la reserva #' + reservaId);
        }

        // Volver a cargar la lista de reservas SIN check in (ya no debería aparecer esta)
        cargarReservasSinCheckinCliente();

    } catch (error) {
        console.error('Error al realizar check in:', error);
        mostrarError('Error al realizar el check in: ' + error.message);
    }
}

/* ================================
   UTILIDADES (reutilizadas)
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
                if (p.nombre) return p.nombre;
                if (p.nickname) return p.nickname;
                if (p.apellido) return p.apellido;
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
