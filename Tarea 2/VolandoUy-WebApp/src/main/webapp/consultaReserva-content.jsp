<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Estilos específicos para esta pantalla -->
<style>
  .btn-checkin {
    display: inline-flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.45rem 1.1rem;
    border-radius: 999px;
    border: none;
    cursor: pointer;
    font-size: 0.9rem;
    font-weight: 600;
    letter-spacing: 0.3px;
    background: linear-gradient(135deg, #22c55e, #16a34a);
    color: #ffffff;
    box-shadow: 0 4px 10px rgba(22, 163, 74, 0.35);
    transition: transform 0.15s ease, box-shadow 0.15s ease, background 0.15s ease;
    white-space: nowrap;
  }

  .btn-checkin i {
    font-size: 0.9rem;
  }

  .btn-checkin:hover {
    transform: translateY(-1px);
    box-shadow: 0 6px 16px rgba(22, 163, 74, 0.45);
    background: linear-gradient(135deg, #16a34a, #15803d);
  }

  .btn-checkin:active {
    transform: translateY(0);
    box-shadow: 0 3px 8px rgba(22, 163, 74, 0.4);
  }

  .btn-checkin:focus {
    outline: 2px solid rgba(34, 197, 94, 0.6);
    outline-offset: 2px;
  }

  /* Opcional: destacar las tarjetas de la reserva del cliente */
  .reserva-card.reserva-mia {
    border: 1px solid rgba(34, 197, 94, 0.5);
    box-shadow: 0 0 0 1px rgba(34, 197, 94, 0.1);
  }

  .reserva-card.reserva-mia .card-body h4 {
    color: #16a34a;
  }
</style>

<section class="form-section-pro">
  <div class="form-card-pro">
    <h2>Consulta de Reserva de Vuelo</h2>

    <!-- Información inicial según tipo de usuario -->
    <div id="info-inicial" class="info-inicial">
      <div id="loading-info" class="loading">Cargando información...</div>
    </div>

    <!-- Para Clientes: Selección de aerolínea -->
    <div id="seleccion-aerolinea" class="controls-grid" style="display:none;">
      <div class="form-group">
        <label for="selectAerolinea">Seleccionar Aerolínea</label>
        <select id="selectAerolinea">
          <option value="">-- Elegir aerolínea --</option>
        </select>
      </div>
    </div>

    <!-- Para Aerolíneas: Lista de rutas -->
    <div id="lista-rutas-aerolinea" class="section-content" style="display:none;">
      <div class="section-title">Rutas de Vuelo</div>
      <div id="listaRutas" class="grid-rutas"></div>
    </div>

    <!-- Selección de ruta (para clientes) -->
    <div id="seleccion-ruta" class="controls-grid" style="display:none;">
      <div class="form-group">
        <label for="selectRuta">Seleccionar Ruta</label>
        <select id="selectRuta">
          <option value="">-- Elegir ruta --</option>
        </select>
      </div>
    </div>

    <!-- Lista de vuelos -->
    <div id="lista-vuelos" class="section-content" style="display:none;">
      <div class="section-title">Vuelos Disponibles</div>
      <div id="listaVuelos" class="grid-vuelos"></div>
    </div>

    <!-- Lista de reservas (ahora SOLO del cliente logueado) -->
    <div id="lista-reservas" class="section-content" style="display:none;">
      <div class="section-title">Mis reservas en este vuelo</div>
      <div id="listaReservas" class="grid-reservas"></div>
    </div>

    <!-- Reserva del cliente (resumen rápido) -->
    <div id="reserva-cliente" class="section-content" style="display:none;">
      <div class="section-title">Mi Reserva</div>
      <div id="reservaCliente" class="reserva-card"></div>
    </div>

    <!-- Detalle de reserva -->
    <div id="detalle-reserva" class="detalle-card" style="display:none;">
      <h3>Detalle de la Reserva</h3>
      <div id="detalleReserva"></div>
    </div>

    <!-- Mensajes de error / warning -->
    <div id="mensaje-error" class="error-message" style="display:none;"></div>
  </div>
</section>

<script>
// ============================
// Variables globales
// ============================
var infoInicial = null;
var rutaSeleccionada = null;
var vueloSeleccionado = null;

// IDs de reservas del cliente que NO tienen check-in
var reservasSinCheckinIds = new Set();

document.addEventListener('DOMContentLoaded', function() {
    initConsultaReserva();
});

// ============================
// Inicialización
// ============================
function initConsultaReserva() {
    console.log('Inicializando consulta de reservas...');
    limpiarInterfaz();
    cargarInfoInicial();
}

function limpiarInterfaz() {
    var secciones = [
        'lista-rutas-aerolinea',
        'seleccion-aerolinea',
        'seleccion-ruta',
        'lista-vuelos',
        'lista-reservas',
        'reserva-cliente',
        'detalle-reserva'
    ];

    secciones.forEach(function(id) {
        var elemento = document.getElementById(id);
        if (elemento) {
            elemento.style.display = 'none';
            elemento.classList.remove('show', 'fade-in');
        }
    });

    var listas = ['listaRutas', 'listaVuelos', 'listaReservas', 'reservaCliente', 'detalleReserva'];
    listas.forEach(function(id) {
        var elemento = document.getElementById(id);
        if (elemento) {
            elemento.innerHTML = '';
        }
    });

    var selects = ['selectAerolinea', 'selectRuta'];
    selects.forEach(function(id) {
        var elemento = document.getElementById(id);
        if (elemento) {
            elemento.innerHTML = '<option value="">-- Seleccionar --</option>';
            elemento.value = '';
        }
    });

    var mensajeError = document.getElementById('mensaje-error');
    if (mensajeError) {
        mensajeError.style.display = 'none';
    }
}

function limpiarSeccionesDependientes(secciones) {
    secciones.forEach(function(id) {
        var elemento = document.getElementById(id);
        if (elemento) {
            elemento.style.display = 'none';
            elemento.classList.remove('show', 'fade-in');

            if (id === 'seleccion-ruta') {
                var selectRuta = document.getElementById('selectRuta');
                if (selectRuta) {
                    selectRuta.innerHTML = '<option value="">-- Seleccionar --</option>';
                    selectRuta.value = '';
                }
            } else if (id === 'lista-vuelos') {
                var listaVuelos = document.getElementById('listaVuelos');
                if (listaVuelos) listaVuelos.innerHTML = '';
            } else if (id === 'lista-reservas') {
                var listaReservas = document.getElementById('listaReservas');
                if (listaReservas) listaReservas.innerHTML = '';
            } else if (id === 'reserva-cliente') {
                var reservaCliente = document.getElementById('reservaCliente');
                if (reservaCliente) reservaCliente.innerHTML = '';
            } else if (id === 'detalle-reserva') {
                var detalleReserva = document.getElementById('detalleReserva');
                if (detalleReserva) detalleReserva.innerHTML = '';
            }
        }
    });
}

// ============================
// Carga inicial
// ============================
async function cargarInfoInicial() {
    try {
        console.log('Probando endpoint de prueba...');
        var testResponse = await fetch('/VolandoUy-WebApp/api/consulta-reservas/test');
        var testData = await testResponse.json();
        console.log('Respuesta del endpoint de prueba:', testData);

        if (!testData.status || testData.status !== 'ok') {
            mostrarError('Error en el endpoint de prueba: ' + (testData.error || 'Respuesta inesperada'));
            return;
        }

        console.log('Endpoint de prueba OK, cargando información inicial...');
        var response = await fetch('/VolandoUy-WebApp/api/consulta-reservas');

        if (!response.ok) {
            throw new Error('HTTP ' + response.status + ': ' + response.statusText);
        }

        var info = await response.json();

        if (info.error) {
            mostrarError(info.error);
            return;
        }

        infoInicial = info;
        console.log('Información inicial:', info);

        if (info.warning) {
            mostrarWarning(info.warning);
        }

        var loadingInfo = document.getElementById('loading-info');
        if (loadingInfo) {
            loadingInfo.style.display = 'none';
        }

        if (info.tipoUsuario === 'aerolinea') {
            // Para aerolínea mantenemos la lógica de rutas propias
            mostrarRutasAerolinea(info.rutas || []);
        } else if (info.tipoUsuario === 'cliente') {
            // Cliente: primero cargamos sus reservas SIN check-in
            await cargarReservasSinCheckin();
            mostrarAerolineas(info.aerolineas || []);
        } else {
            mostrarError('Tipo de usuario no reconocido: ' + info.tipoUsuario);
        }

    } catch (error) {
        console.error('Error al cargar información inicial:', error);

        var loadingInfo2 = document.getElementById('loading-info');
        if (loadingInfo2) {
            loadingInfo2.style.display = 'none';
        }

        mostrarError('Error al cargar la información inicial: ' + error.message);
    }
}

// Reservas sin check-in del cliente
async function cargarReservasSinCheckin() {
    try {
        console.log('Cargando reservas SIN check-in del cliente...');
        var resp = await fetch('/VolandoUy-WebApp/api/consulta-reservas/reservas-no-checkin');

        if (!resp.ok) {
            console.warn('No se pudieron obtener reservas sin check-in. HTTP', resp.status);
            return;
        }

        var data = await resp.json();
        console.log('Reservas sin check-in recibidas:', data);

        if (Array.isArray(data)) {
            reservasSinCheckinIds = new Set(data.map(function(r){ return r.id; }));
            console.log('Set reservasSinCheckinIds:', Array.from(reservasSinCheckinIds));
        }
    } catch (e) {
        console.error('Error al cargar reservas sin check-in:', e);
    }
}

// ============================
// Rutas para aerolínea
// ============================
function mostrarRutasAerolinea(rutas) {
    var listaRutas = document.getElementById('listaRutas');
    var seccionRutas = document.getElementById('lista-rutas-aerolinea');

    listaRutas.innerHTML = '';

    if (!rutas || rutas.length === 0) {
        listaRutas.innerHTML = '<div class="text-center"><p>No hay rutas disponibles.</p></div>';
    } else {
        rutas.forEach(function(ruta, index) {
            var card = document.createElement('div');
            card.className = 'ruta-card fade-in';
            card.style.animationDelay = (index * 0.1) + 's';
            card.innerHTML =
                '<div class="card-body">' +
                    '<h4>' + (ruta.nombre || 'Sin nombre') + '</h4>' +
                    '<p><strong>Ruta:</strong> ' + (ruta.ciudadOrigen || 'Sin origen') + ' → ' + (ruta.ciudadDestino || 'Sin destino') + '</p>' +
                    '<p>' + (ruta.descripcion || 'Sin descripción') + '</p>' +
                '</div>';
            card.addEventListener('click', function() { seleccionarRuta(ruta); });
            listaRutas.appendChild(card);
        });
    }

    seccionRutas.style.display = 'block';
    seccionRutas.classList.add('show', 'fade-in');
}

// ============================
// Aerolíneas para cliente
// ============================
function mostrarAerolineas(aerolineas) {
    var selectAerolinea = document.getElementById('selectAerolinea');
    var seccionAerolinea = document.getElementById('seleccion-aerolinea');

    selectAerolinea.innerHTML = '<option value="">-- Elegir aerolínea --</option>';

    if (aerolineas && aerolineas.length > 0) {
        aerolineas.forEach(function(aerolinea) {
            var option = document.createElement('option');
            option.value = aerolinea.nickname;
            option.textContent = aerolinea.nombre;
            selectAerolinea.appendChild(option);
        });

        selectAerolinea.removeEventListener('change', handleAerolineaChange);
        selectAerolinea.addEventListener('change', handleAerolineaChange);
    } else {
        selectAerolinea.innerHTML = '<option value="">No hay aerolíneas disponibles</option>';
    }

    seccionAerolinea.style.display = 'block';
    seccionAerolinea.classList.add('show', 'fade-in');
}

function handleAerolineaChange(e) {
    if (e.target.value) {
        limpiarSeccionesDependientes(['seleccion-ruta', 'lista-vuelos', 'lista-reservas', 'reserva-cliente', 'detalle-reserva']);
        cargarRutasAerolinea(e.target.value);
    }
}

async function cargarRutasAerolinea(aerolineaNickname) {
    try {
        console.log('Cargando rutas para aerolínea:', aerolineaNickname);
        var url = '/VolandoUy-WebApp/api/consulta-reservas/rutas/' + encodeURIComponent(aerolineaNickname);

        var response = await fetch(url);
        if (!response.ok) {
            throw new Error('HTTP ' + response.status + ': ' + response.statusText);
        }

        var rutas = await response.json();
        console.log('Rutas recibidas:', rutas);

        if (rutas.error) {
            mostrarError(rutas.error);
            return;
        }

        mostrarRutasCliente(rutas);

    } catch (error) {
        console.error('Error al cargar rutas:', error);
        mostrarError('Error al cargar las rutas');
    }
}

// ============================
// Rutas → Cliente
// ============================
function mostrarRutasCliente(rutas) {
    console.log('Mostrando rutas para cliente:', rutas);
    var selectRuta = document.getElementById('selectRuta');
    var seccionRuta = document.getElementById('seleccion-ruta');

    if (!selectRuta) {
        console.error('No se encontró el elemento selectRuta');
        return;
    }

    selectRuta.innerHTML = '<option value="">-- Elegir ruta --</option>';

    if (rutas && rutas.length > 0) {
        rutas.forEach(function(ruta) {
            var option = document.createElement('option');
            option.value = ruta.nombre || 'Sin nombre';
            option.textContent = (ruta.nombre || 'Sin nombre') + ' (' + (ruta.ciudadOrigen || 'Sin origen') + ' → ' + (ruta.ciudadDestino || 'Sin destino') + ')';
            selectRuta.appendChild(option);
        });
    }

    selectRuta.addEventListener('change', function(e) {
        if (e.target.value) {
            cargarVuelosRuta(e.target.value);
        }
    });

    seccionRuta.style.display = 'block';
}

// ============================
// Vuelos
// ============================
async function cargarVuelosRuta(rutaNombre) {
    try {
        var response = await fetch('/VolandoUy-WebApp/api/consulta-reservas/vuelos/' + encodeURIComponent(rutaNombre));
        var vuelos = await response.json();

        if (vuelos.error) {
            mostrarError(vuelos.error);
            return;
        }

        mostrarVuelos(vuelos);

    } catch (error) {
        console.error('Error al cargar vuelos:', error);
        mostrarError('Error al cargar los vuelos');
    }
}

function mostrarVuelos(vuelos) {
    var listaVuelos = document.getElementById('listaVuelos');
    var seccionVuelos = document.getElementById('lista-vuelos');

    listaVuelos.innerHTML = '';

    if (!vuelos || vuelos.length === 0) {
        listaVuelos.innerHTML = '<div class="text-center"><p>No hay vuelos disponibles para esta ruta.</p></div>';
    } else {
        vuelos.forEach(function(vuelo, index) {
            var card = document.createElement('div');
            card.className = 'vuelo-card fade-in';
            card.style.animationDelay = (index * 0.1) + 's';
            card.innerHTML =
                '<div class="card-body">' +
                    '<h4>' + (vuelo.nombre || 'Sin nombre') + '</h4>' +
                    '<p><strong>Fecha:</strong> ' + (vuelo.fechaVuelo || 'Sin fecha') + ' ' + (vuelo.horaVuelo || 'Sin hora') + '</p>' +
                    '<p><strong>Duración:</strong> ' + (vuelo.duracion || 'Sin duración') + '</p>' +
                    '<p><strong>Asientos:</strong> ' + (vuelo.asientosMaxTurista || 0) + ' Turista, ' + (vuelo.asientosMaxEjecutivo || 0) + ' Ejecutivo</p>' +
                '</div>';
            card.addEventListener('click', function() { seleccionarVuelo(vuelo); });
            listaVuelos.appendChild(card);
        });
    }

    seccionVuelos.style.display = 'block';
    seccionVuelos.classList.add('show', 'fade-in');
}

// ============================
// Seleccionar ruta (aerolínea)
// ============================
function seleccionarRuta(ruta) {
    console.log('Ruta seleccionada:', ruta);
    limpiarSeccionesDependientes(['lista-vuelos', 'lista-reservas', 'reserva-cliente', 'detalle-reserva']);
    cargarVuelosRuta(ruta.nombre);
}

// ============================
// Seleccionar vuelo (ambos tipos)
// ============================
async function seleccionarVuelo(vuelo) {
    console.log('Vuelo seleccionado:', vuelo);

    limpiarSeccionesDependientes(['lista-reservas', 'reserva-cliente', 'detalle-reserva']);

    try {
        var response = await fetch('/VolandoUy-WebApp/api/consulta-reservas');
        var info = await response.json();

        // Siempre cargamos reservas del vuelo,
        // pero luego filtramos SOLO las del cliente logueado.
        await cargarReservasVuelo(vuelo.nombre);

        // Si es cliente, también mostramos "Mi Reserva"
        if (info.tipoUsuario === 'cliente') {
            await cargarReservaCliente(vuelo.nombre);
        }

    } catch (error) {
        console.error('Error al verificar tipo de usuario:', error);
        mostrarError('Error al verificar el tipo de usuario');
    }
}

// ============================
// Reservas del vuelo (solo del cliente logueado)
// ============================
async function cargarReservasVuelo(vueloNombre) {
    try {
        var response = await fetch('/VolandoUy-WebApp/api/consulta-reservas/reservas-vuelo/' + encodeURIComponent(vueloNombre));
        var reservas = await response.json();

        if (reservas.error) {
            mostrarError(reservas.error);
            return;
        }

        mostrarReservasVuelo(reservas);

    } catch (error) {
        console.error('Error al cargar reservas del vuelo:', error);
        mostrarError('Error al cargar las reservas del vuelo');
    }
}

function mostrarReservasVuelo(reservas) {
    console.log('Mostrando reservas del vuelo (filtradas por cliente logueado):', reservas);
    var listaReservas = document.getElementById('listaReservas');
    var seccionReservas = document.getElementById('lista-reservas');

    listaReservas.innerHTML = '';

    if (!reservas || reservas.length === 0 || !infoInicial || !infoInicial.usuarioLogueado) {
        listaReservas.innerHTML = '<div class="text-center"><p>No tienes reservas para este vuelo.</p></div>';
    } else {
        var totalMias = 0;

        reservas.forEach(function(reserva, index) {
            var esMia = (reserva.nicknameCliente === infoInicial.usuarioLogueado);
            if (!esMia) {
                return; // Ignoramos reservas de otros clientes
            }

            totalMias++;

            var card = document.createElement('div');
            card.className = 'reserva-card fade-in reserva-mia';
            card.style.animationDelay = (index * 0.1) + 's';

            var tieneCheckinPendiente = reservasSinCheckinIds && reservasSinCheckinIds.has(reserva.id);

            var html = '';
            html += '<div class="card-body">';
            html +=   '<h4>Reserva #' + (reserva.id || 'Sin ID') + '</h4>';
            html +=   '<p><strong>Cliente:</strong> ' + (reserva.nicknameCliente || 'Sin cliente') + '</p>';
            html +=   '<p><strong>Fecha de reserva:</strong> ' + (reserva.fechaReserva || 'Sin fecha') + '</p>';
            html +=   '<p><strong>Vuelo:</strong> ' + (reserva.vueloNombre || 'Sin vuelo') + '</p>';
            html +=   '<p><strong>Fecha de vuelo:</strong> ' + (reserva.fechaVuelo || 'Sin fecha') + ' ' + (reserva.horaVuelo || 'Sin hora') + '</p>';
            if (tieneCheckinPendiente) {
                html += '<div class="card-actions">';
                html +=   '<button type="button" class="btn-checkin" data-reserva-id="' + reserva.id + '">';
                html +=     '<i class="fas fa-ticket-alt"></i><span>Realizar check-in</span>';
                html +=   '</button>';
                html += '</div>';
            }
            html += '</div>';

            card.innerHTML = html;

            // Click en la tarjeta → detalle
            card.addEventListener('click', function(e) {
                if (e.target && e.target.closest && e.target.closest('.btn-checkin')) {
                    return;
                }
                console.log('Click en reserva:', reserva.id);
                mostrarDetalleReserva(reserva.id);
            });

            // Click en el botón de check-in
            if (tieneCheckinPendiente) {
                var btn = card.querySelector('.btn-checkin');
                if (btn) {
                    btn.addEventListener('click', function(e) {
                        e.stopPropagation();
                        realizarCheckIn(reserva.id);
                    });
                }
            }

            listaReservas.appendChild(card);
        });

        if (totalMias === 0) {
            listaReservas.innerHTML = '<div class="text-center"><p>No tienes reservas para este vuelo.</p></div>';
        }
    }

    seccionReservas.style.display = 'block';
    seccionReservas.classList.add('show', 'fade-in');
}

// ============================
// Reserva del cliente (resumen rápido)
// ============================
async function cargarReservaCliente(vueloNombre) {
    try {
        var response = await fetch('/VolandoUy-WebApp/api/consulta-reservas/reserva-cliente/' + encodeURIComponent(vueloNombre));
        var reserva = await response.json();

        if (reserva.error) {
            if (response.status === 404) {
                mostrarReservaCliente(null);
            } else {
                mostrarError(reserva.error);
            }
            return;
        }

        mostrarReservaCliente(reserva);

    } catch (error) {
        console.error('Error al cargar reserva del cliente:', error);
        mostrarError('Error al cargar la reserva del cliente');
    }
}

function mostrarReservaCliente(reserva) {
    var reservaCliente = document.getElementById('reservaCliente');
    var seccionReserva = document.getElementById('reserva-cliente');

    reservaCliente.innerHTML = '';

    if (reserva === null) {
        reservaCliente.innerHTML = '<div class="text-center"><p>No tienes una reserva para este vuelo.</p></div>';
    } else {
        var card = document.createElement('div');
        card.className = 'reserva-card fade-in reserva-mia';
        card.innerHTML =
            '<div class="card-body">' +
                '<h4>Mi Reserva #' + (reserva.id || 'Sin ID') + '</h4>' +
                '<p><strong>Fecha de reserva:</strong> ' + (reserva.fechaReserva || 'Sin fecha') + '</p>' +
                '<p><strong>Vuelo:</strong> ' + (reserva.vueloNombre || 'Sin vuelo') + '</p>' +
                '<p><strong>Fecha de vuelo:</strong> ' + (reserva.fechaVuelo || 'Sin fecha') + ' ' + (reserva.horaVuelo || 'Sin hora') + '</p>' +
            '</div>';

        card.addEventListener('click', function() { mostrarDetalleReserva(reserva.id); });
        reservaCliente.appendChild(card);
    }

    seccionReserva.style.display = 'block';
    seccionReserva.classList.add('show', 'fade-in');
}

// ============================
// Detalle de reserva
// ============================
async function mostrarDetalleReserva(reservaId) {
    try {
        var response = await fetch('/VolandoUy-WebApp/api/consulta-reservas/detalle-reserva/' + reservaId);
        var detalle = await response.json();

        if (detalle.error) {
            mostrarError(detalle.error);
            return;
        }

        mostrarDetalleEnInterfaz(detalle);

    } catch (error) {
        console.error('Error al cargar detalle de reserva:', error);
        mostrarError('Error al cargar el detalle de la reserva');
    }
}

function mostrarDetalleEnInterfaz(detalle) {
    console.log('Mostrando detalle completo:', detalle);

    var detalleReserva = document.getElementById('detalleReserva');
    var seccionDetalle = document.getElementById('detalle-reserva');

    detalleReserva.innerHTML = '';

    var vuelo = detalle.vuelo || {};
    var ruta = detalle.ruta || {};
    var aerolinea = detalle.aerolinea || {};
    var costo = detalle.costoReserva || {};

    var costoTotal = (costo && typeof costo === 'object') ? (costo.costoTotal || 'N/A') : 'N/A';

    var html = '';
    html += '<div class="detalle-completo fade-in">';
    html +=   '<h4>Información de la Reserva</h4>';
    html +=   '<p><strong>ID:</strong> ' + (detalle.id || 'Sin ID') + '</p>';
    html +=   '<p><strong>Cliente:</strong> ' + (detalle.nicknameCliente || 'Sin cliente') + '</p>';
    html +=   '<p><strong>Fecha de reserva:</strong> ' + (detalle.fechaReserva || 'Sin fecha') + '</p>';
    html +=   '<p><strong>Costo:</strong> $' + costoTotal + '</p>';

    html +=   '<h4>Información del Vuelo</h4>';
    html +=   '<p><strong>Nombre:</strong> ' + (vuelo.nombre || 'Sin nombre') + '</p>';
    html +=   '<p><strong>Fecha:</strong> ' + (vuelo.fechaVuelo || 'Sin fecha') + '</p>';
    html +=   '<p><strong>Hora:</strong> ' + (vuelo.horaVuelo || 'Sin hora') + '</p>';
    html +=   '<p><strong>Duración:</strong> ' + (vuelo.duracion || 'Sin duración') + '</p>';

    html +=   '<h4>Información de la Ruta</h4>';
    html +=   '<p><strong>Nombre:</strong> ' + (ruta.nombre || 'Sin nombre') + '</p>';
    html +=   '<p><strong>Descripción:</strong> ' + (ruta.descripcion || 'Sin descripción') + '</p>';
    html +=   '<p><strong>Origen:</strong> ' + (ruta.ciudadOrigen || 'Sin origen') + '</p>';
    html +=   '<p><strong>Destino:</strong> ' + (ruta.ciudadDestino || 'Sin destino') + '</p>';

    html +=   '<h4>Información de la Aerolínea</h4>';
    html +=   '<p><strong>Nombre:</strong> ' + (aerolinea.nombre || 'Sin nombre') + '</p>';
    html +=   '<p><strong>Descripción:</strong> ' + (aerolinea.descripcion || 'Sin descripción') + '</p>';

    html +=   '<h4>Pasajeros</h4>';
    html +=   '<p>' + obtenerTextoPasajeros(detalle.pasajeros) + '</p>';

    html += '</div>';

    detalleReserva.innerHTML = html;

    seccionDetalle.style.display = 'block';
    seccionDetalle.classList.add('show', 'fade-in');
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
            var nombres = pasajeros.map(function(p) {
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

// ============================
// Check-in (versión simple)
// ============================
async function realizarCheckIn(reservaId) {
  try {
    // Llamamos al endpoint sin confirm() ni nada visual raro
    const resp = await fetch(
      '/VolandoUy-WebApp/api/consulta-reservas/realizar-checkin/' + encodeURIComponent(reservaId),
      { method: 'POST' }
    );

    let data = {};
    try {
      data = await resp.json();
    } catch (e) {
      data = {};
    }

    if (!resp.ok || data.error) {
      const msg = (data && data.error) ? data.error : ('HTTP ' + resp.status);
      mostrarError('Error al realizar el check-in: ' + msg);
      return;
    }

    // Sacamos esta reserva del set de "sin check-in"
    reservasSinCheckinIds.delete(reservaId);

    // Aviso amigable
    if (typeof showToast === 'function') {
      showToast('Check-in realizado correctamente', 'success');
    } else {
      alert('Check-in realizado correctamente');
    }

    // Refrescar la pantalla de consulta
    initConsultaReserva();

  } catch (e) {
    console.error('Error en realizarCheckIn:', e);
    mostrarError('Error al realizar el check-in');
  }
}

// ============================
// Errores y warnings
// ============================
function mostrarError(mensaje) {
    console.error('Error mostrado al usuario:', mensaje);

    var mensajeError = document.getElementById('mensaje-error');
    if (mensajeError) {
        mensajeError.textContent = mensaje;
        mensajeError.style.display = 'block';
        mensajeError.className = 'error-message fade-in';

        setTimeout(function() {
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

    var mensajeError = document.getElementById('mensaje-error');
    if (mensajeError) {
        mensajeError.textContent = mensaje;
        mensajeError.style.display = 'block';
        mensajeError.className = 'warning-message fade-in';

        setTimeout(function() {
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