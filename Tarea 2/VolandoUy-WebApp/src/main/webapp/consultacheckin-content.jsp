<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="form-section-pro">
  <div class="form-card-pro">
    <h2>Mis Reservas con Check In</h2>

    <!-- Información inicial / resumen -->
    <div id="info-inicial" class="info-inicial">
      <div id="loading-info" class="loading">Cargando información...</div>
      <div id="resumen-inicial" style="display:none;"></div>
    </div>

    <!-- Reservas con Check In (solo clientes) -->
    <div id="reservas-checkin" class="section-content" style="display:none;">
      <div class="section-title">Reservas con Check In</div>
      <div id="listaReservasCheckin" class="grid-reservas"></div>
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

.btn-pdf {
  border: none;
  background: transparent;
  cursor: pointer;
  font-size: 1rem;
  padding: 4px 8px;
  border-radius: 6px;
  transition: 0.2s;
}

.btn-pdf:hover {
  background: rgba(0,0,0,0.08);
}
</style>

<script>
// ===== IMPORTANTE: base del API generada por el servidor =====
const BASE_URL = '<c:url value="/api/consulta-reservas"/>';
console.log('BASE_URL de consulta reservas:', BASE_URL);

// Variables globales
let infoInicial = null;

document.addEventListener('DOMContentLoaded', function() {
    initConsultaReserva();
});

function initConsultaReserva() {
    console.log('Inicializando consulta de reservas (solo cliente)...');
    limpiarInterfaz();
    cargarInfoInicial();
}

function limpiarInterfaz() {
    const secciones = [
        'reservas-checkin',
        'detalle-reserva'
    ];

    secciones.forEach(id => {
        const elemento = document.getElementById(id);
        if (elemento) {
            elemento.style.display = 'none';
            elemento.classList.remove('show', 'fade-in');
        }
    });

    const listas = ['listaReservasCheckin', 'detalleReserva', 'resumen-inicial'];
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

async function cargarInfoInicial() {
    console.log('Cargando información inicial desde:', BASE_URL);
    fetch(BASE_URL)
        .then(function (r) { return r.json(); })
        .then(function (info) {
            console.log('Información inicial recibida:', info);

            if (info.error) {
                mostrarError(info.error);
                ocultarLoading();
                return;
            }

            infoInicial = info;

            ocultarLoading();
            mostrarResumenInicial(info);

            if (info.tipoUsuario !== 'cliente') {
                mostrarError('Solo los clientes pueden acceder a esta consulta de reservas.');
                return;
            }

            // Cargar reservas con check in del cliente
            cargarReservasCheckinCliente();
        })
        .catch(function (error) {
            console.error('Error al cargar información inicial:', error);
            ocultarLoading();
            mostrarError('Error al cargar la información inicial: ' + error.message);
        });
}

function ocultarLoading() {
    const loadingInfo = document.getElementById('loading-info');
    if (loadingInfo) {
        loadingInfo.style.display = 'none';
    }
}

function mostrarResumenInicial(info) {
    const resumen = document.getElementById('resumen-inicial');
    if (!resumen) return;

    const usuario = info.usuarioLogueado || 'Desconocido';

    resumen.innerHTML = '<p><strong>Usuario:</strong> ' + usuario + '</p>'

    resumen.style.display = 'block';
}

/* ================================
   RESERVAS CON CHECK IN (CLIENTE)
   ================================ */

function cargarReservasCheckinCliente() {
    const url = BASE_URL + '/reservas-checkin';
    console.log('Cargando reservas con check in del cliente desde:', url);

    fetch(url)
        .then(function (r) { return r.json(); })
        .then(function (reservas) {
            console.log('Reservas con check in recibidas:', reservas);

            if (reservas && reservas.error) {
                mostrarReservasCheckinCliente([]);
                mostrarError(reservas.error);
                return;
            }

            mostrarReservasCheckinCliente(reservas || []);
        })
        .catch(function (error) {
            console.error('Error al cargar reservas con check in:', error);
            mostrarReservasCheckinCliente([]);
            mostrarError('Error al cargar las reservas con check in: ' + error.message);
        });
}

function mostrarReservasCheckinCliente(reservas) {
    console.log('Mostrando reservas con check in para cliente:', reservas);

    const lista = document.getElementById('listaReservasCheckin');
    const seccion = document.getElementById('reservas-checkin');

    if (!lista || !seccion) {
        console.error('No se encontró el contenedor de reservas con check in');
        return;
    }

    lista.innerHTML = '';

    if (!reservas || reservas.length === 0) {
        lista.innerHTML = '<div class="text-center"><p>No tienes reservas con check in realizado.</p></div>';
    } else {
        reservas.forEach((reserva, index) => {
            console.log('Construyendo card de reserva con check in (simple):', reserva);

            const card = document.createElement('div');
            card.className = 'reserva-card fade-in';
            card.style.animationDelay = `${index * 0.1}s`;

            let html = '';
            html += '<div class="card-body">';
            html +=   '<div class="card-header-row">';
            html +=     '<h4>Reserva con Check In #' + (reserva.id || 'Sin ID') + '</h4>';
            // 🔽 Botón de descarga de PDF
            if (reserva.id) {
                html +=     '<button type="button" class="btn-pdf" title="Descargar PDF" ';
                html +=             'onclick="event.stopPropagation(); descargarPdf(' + reserva.id + ');">';
                html +=         '📄 PDF';
                html +=     '</button>';
            }
            html +=   '</div>';
            html +=   '<p>Haz clic en la tarjeta para ver el detalle completo.</p>';
            html += '</div>';

            card.innerHTML = html;

            if (reserva.id) {
                card.addEventListener('click', () => {
                    console.log('Click en reserva con check in, ID:', reserva.id);
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
   DETALLE DE RESERVA
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
   UTILIDADES
   ================================ */
function descargarPdf(id) {
    const url = BASE_URL + '/pdf/' + id;
    console.log('Descargando PDF desde:', url);
    window.open(url, '_blank');
}




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
