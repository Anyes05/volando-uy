<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

    <!-- Lista de reservas (para aerolíneas) -->
    <div id="lista-reservas" class="section-content" style="display:none;">
      <div class="section-title">Reservas del Vuelo</div>
      <div id="listaReservas" class="grid-reservas"></div>
    </div>

    <!-- Reserva del cliente (para clientes) -->
    <div id="reserva-cliente" class="section-content" style="display:none;">
      <div class="section-title">Mi Reserva</div>
      <div id="reservaCliente" class="reserva-card"></div>
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

<!-- Script específico para consulta de reservas -->
<script>
// Variables globales
let infoInicial = null;
let rutaSeleccionada = null;
let vueloSeleccionado = null;

document.addEventListener('DOMContentLoaded', function() {
    initConsultaReserva();
});

function initConsultaReserva() {
    console.log('Inicializando consulta de reservas...');
    console.log('URL actual:', window.location.href);
    console.log('Elementos del DOM disponibles:');
    console.log('- info-inicial:', document.getElementById('info-inicial'));
    console.log('- mensaje-error:', document.getElementById('mensaje-error'));
    
    // Verificar que los elementos necesarios existen
    const elementosRequeridos = [
        'info-inicial',
        'mensaje-error',
        'lista-rutas-aerolinea',
        'seleccion-aerolinea',
        'seleccion-ruta',
        'lista-vuelos',
        'lista-reservas',
        'reserva-cliente',
        'detalle-reserva'
    ];
    
    elementosRequeridos.forEach(id => {
        const elemento = document.getElementById(id);
        if (!elemento) {
            console.error('Elemento requerido no encontrado:', id);
        } else {
            console.log('Elemento encontrado:', id);
        }
    });
    
    // Cargar información inicial
    cargarInfoInicial();
}

async function cargarInfoInicial() {
    try {
        // Primero probar el endpoint de prueba
        console.log('Probando endpoint de prueba...');
        const testResponse = await fetch('/VolandoUy-WebApp/api/consulta-reservas/test');
        const testData = await testResponse.json();
        console.log('Respuesta del endpoint de prueba:', testData);
        
        if (!testData.status || testData.status !== 'ok') {
            mostrarError('Error en el endpoint de prueba: ' + (testData.error || 'Respuesta inesperada'));
            return;
        }
        
        // Si el endpoint de prueba funciona, proceder con el principal
        console.log('Endpoint de prueba OK, cargando información inicial...');
        const response = await fetch('/VolandoUy-WebApp/api/consulta-reservas');
        
        if (!response.ok) {
            throw new Error('HTTP ' + response.status + ': ' + response.statusText);
        }
        
        const info = await response.json();
        
        if (info.error) {
            mostrarError(info.error);
            return;
        }
        
        infoInicial = info;
        console.log('Información inicial:', info);
        
        // Mostrar warning si existe
        if (info.warning) {
            mostrarWarning(info.warning);
        }
        
        if (info.tipoUsuario === 'aerolinea') {
            // Mostrar rutas directamente para aerolíneas
            mostrarRutasAerolinea(info.rutas || []);
        } else if (info.tipoUsuario === 'cliente') {
            // Mostrar aerolíneas para clientes
            mostrarAerolineas(info.aerolineas || []);
        } else {
            mostrarError('Tipo de usuario no reconocido: ' + info.tipoUsuario);
        }
        
    } catch (error) {
        console.error('Error al cargar información inicial:', error);
        mostrarError('Error al cargar la información inicial: ' + error.message);
    }
}

function mostrarRutasAerolinea(rutas) {
    const listaRutas = document.getElementById('listaRutas');
    const seccionRutas = document.getElementById('lista-rutas-aerolinea');
    
    if (rutas.length === 0) {
        listaRutas.innerHTML = '<p>No hay rutas disponibles.</p>';
    } else {
        listaRutas.innerHTML = '';
        rutas.forEach(ruta => {
            const card = document.createElement('div');
            card.className = 'ruta-card';
            card.innerHTML = 
                '<div class="card-body">' +
                    '<h4>' + (ruta.nombre || 'Sin nombre') + '</h4>' +
                    '<p>' + (ruta.ciudadOrigen || 'Sin origen') + ' → ' + (ruta.ciudadDestino || 'Sin destino') + '</p>' +
                    '<p>' + (ruta.descripcion || 'Sin descripción') + '</p>' +
                '</div>';
            card.addEventListener('click', () => seleccionarRuta(ruta));
            listaRutas.appendChild(card);
        });
    }
    
    seccionRutas.style.display = 'block';
}

function mostrarAerolineas(aerolineas) {
    const selectAerolinea = document.getElementById('selectAerolinea');
    const seccionAerolinea = document.getElementById('seleccion-aerolinea');
    
    selectAerolinea.innerHTML = '<option value="">-- Elegir aerolínea --</option>';
    aerolineas.forEach(aerolinea => {
        const option = document.createElement('option');
        option.value = aerolinea.nickname;
        option.textContent = aerolinea.nombre;
        selectAerolinea.appendChild(option);
    });
    
    selectAerolinea.addEventListener('change', (e) => {
        if (e.target.value) {
            cargarRutasAerolinea(e.target.value);
        }
    });
    
    seccionAerolinea.style.display = 'block';
}

async function cargarRutasAerolinea(aerolineaNickname) {
    try {
        console.log('Cargando rutas para aerolínea:', aerolineaNickname);
        const url = '/VolandoUy-WebApp/api/consulta-reservas/rutas/' + encodeURIComponent(aerolineaNickname);
        console.log('URL de la petición:', url);
        
        const response = await fetch(url);
        console.log('Respuesta recibida:', response.status, response.statusText);
        
        if (!response.ok) {
            throw new Error('HTTP ' + response.status + ': ' + response.statusText);
        }
        
        const rutas = await response.json();
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

function mostrarRutasCliente(rutas) {
    console.log('Mostrando rutas para cliente:', rutas);
    const selectRuta = document.getElementById('selectRuta');
    const seccionRuta = document.getElementById('seleccion-ruta');
    
    console.log('Elemento selectRuta encontrado:', selectRuta);
    console.log('Elemento seccionRuta encontrado:', seccionRuta);
    
    if (!selectRuta) {
        console.error('No se encontró el elemento selectRuta');
        return;
    }
    
    selectRuta.innerHTML = '<option value="">-- Elegir ruta --</option>';
    console.log('SelectRuta limpiado, opciones actuales:', selectRuta.options.length);
    
    if (rutas && rutas.length > 0) {
        rutas.forEach(ruta => {
            console.log('Agregando ruta:', ruta);
            console.log('Propiedades de la ruta:', Object.keys(ruta));
            console.log('ruta.nombre:', ruta.nombre);
            console.log('ruta.ciudadOrigen:', ruta.ciudadOrigen);
            console.log('ruta.ciudadDestino:', ruta.ciudadDestino);
            
            const option = document.createElement('option');
            option.value = ruta.nombre || 'Sin nombre';
            option.textContent = (ruta.nombre || 'Sin nombre') + ' (' + (ruta.ciudadOrigen || 'Sin origen') + ' → ' + (ruta.ciudadDestino || 'Sin destino') + ')';
            selectRuta.appendChild(option);
            console.log('Option creada:', option);
        });
    } else {
        console.log('No hay rutas disponibles');
    }
    
    selectRuta.addEventListener('change', (e) => {
        if (e.target.value) {
            cargarVuelosRuta(e.target.value);
        }
    });
    
    console.log('Opciones finales en selectRuta:', selectRuta.options.length);
    console.log('Contenido del selectRuta:', selectRuta.innerHTML);
    
    seccionRuta.style.display = 'block';
}

async function cargarVuelosRuta(rutaNombre) {
    try {
        const response = await fetch('/VolandoUy-WebApp/api/consulta-reservas/vuelos/' + encodeURIComponent(rutaNombre));
        const vuelos = await response.json();
        
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
    const listaVuelos = document.getElementById('listaVuelos');
    const seccionVuelos = document.getElementById('lista-vuelos');
    
    if (vuelos.length === 0) {
        listaVuelos.innerHTML = '<p>No hay vuelos disponibles para esta ruta.</p>';
    } else {
        listaVuelos.innerHTML = '';
        vuelos.forEach(vuelo => {
            const card = document.createElement('div');
            card.className = 'vuelo-card';
            card.innerHTML = 
                '<div class="card-body">' +
                    '<h4>' + (vuelo.nombre || 'Sin nombre') + '</h4>' +
                    '<p>Fecha: ' + (vuelo.fechaVuelo || 'Sin fecha') + ' ' + (vuelo.horaVuelo || 'Sin hora') + '</p>' +
                    '<p>Duración: ' + (vuelo.duracion || 'Sin duración') + '</p>' +
                    '<p>Asientos: ' + (vuelo.asientosMaxTurista || 0) + ' Turista, ' + (vuelo.asientosMaxEjecutivo || 0) + ' Ejecutivo</p>' +
                '</div>';
            card.addEventListener('click', () => seleccionarVuelo(vuelo));
            listaVuelos.appendChild(card);
        });
    }
    
    seccionVuelos.style.display = 'block';
}

async function seleccionarVuelo(vuelo) {
    console.log('Vuelo seleccionado:', vuelo);
    
    // Verificar tipo de usuario para mostrar reservas o reserva del cliente
    try {
        const response = await fetch('/VolandoUy-WebApp/api/consulta-reservas');
        const info = await response.json();
        
        if (info.tipoUsuario === 'aerolinea') {
            // Para aerolíneas: mostrar todas las reservas del vuelo
            cargarReservasVuelo(vuelo.nombre);
        } else if (info.tipoUsuario === 'cliente') {
            // Para clientes: mostrar su reserva para este vuelo
            cargarReservaCliente(vuelo.nombre);
        }
        
    } catch (error) {
        console.error('Error al verificar tipo de usuario:', error);
        mostrarError('Error al verificar el tipo de usuario');
    }
}

async function cargarReservasVuelo(vueloNombre) {
    try {
        const response = await fetch('/VolandoUy-WebApp/api/consulta-reservas/reservas-vuelo/' + encodeURIComponent(vueloNombre));
        const reservas = await response.json();
        
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
    console.log('Mostrando reservas del vuelo para aerolínea:', reservas);
    const listaReservas = document.getElementById('listaReservas');
    const seccionReservas = document.getElementById('lista-reservas');
    
    if (reservas.length === 0) {
        listaReservas.innerHTML = '<p>No hay reservas para este vuelo.</p>';
    } else {
        listaReservas.innerHTML = '';
        reservas.forEach(reserva => {
            console.log('Creando card para reserva:', reserva);
            const card = document.createElement('div');
            card.className = 'reserva-card';
            card.innerHTML = 
                '<div class="card-body">' +
                    '<h4>Reserva #' + (reserva.id || 'Sin ID') + '</h4>' +
                    '<p>Cliente: ' + (reserva.nicknameCliente || 'Sin cliente') + '</p>' +
                    '<p>Fecha de reserva: ' + (reserva.fechaReserva || 'Sin fecha') + '</p>' +
                    '<p>Vuelo: ' + (reserva.vueloNombre || 'Sin vuelo') + '</p>' +
                    '<p>Fecha de vuelo: ' + (reserva.fechaVuelo || 'Sin fecha') + ' ' + (reserva.horaVuelo || 'Sin hora') + '</p>' +
                '</div>';
            card.addEventListener('click', () => {
                console.log('Aerolínea hizo clic en reserva:', reserva.id);
                mostrarDetalleReserva(reserva.id);
            });
            listaReservas.appendChild(card);
        });
    }
    
    seccionReservas.style.display = 'block';
}

async function cargarReservaCliente(vueloNombre) {
    try {
        const response = await fetch('/VolandoUy-WebApp/api/consulta-reservas/reserva-cliente/' + encodeURIComponent(vueloNombre));
        const reserva = await response.json();
        
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
    const reservaCliente = document.getElementById('reservaCliente');
    const seccionReserva = document.getElementById('reserva-cliente');
    
    if (reserva === null) {
        reservaCliente.innerHTML = '<p>No tienes una reserva para este vuelo.</p>';
    } else {
        reservaCliente.innerHTML = 
            '<div class="reserva-card">' +
                '<div class="card-body">' +
                    '<h4>Mi Reserva #' + (reserva.id || 'Sin ID') + '</h4>' +
                    '<p>Fecha de reserva: ' + (reserva.fechaReserva || 'Sin fecha') + '</p>' +
                    '<p>Vuelo: ' + (reserva.vueloNombre || 'Sin vuelo') + '</p>' +
                    '<p>Fecha de vuelo: ' + (reserva.fechaVuelo || 'Sin fecha') + ' ' + (reserva.horaVuelo || 'Sin hora') + '</p>' +
                '</div>' +
            '</div>';
        
        // Agregar evento de clic para mostrar detalle
        reservaCliente.addEventListener('click', () => mostrarDetalleReserva(reserva.id));
    }
    
    seccionReserva.style.display = 'block';
}

async function mostrarDetalleReserva(reservaId) {
    try {
        const response = await fetch('/VolandoUy-WebApp/api/consulta-reservas/detalle-reserva/' + reservaId);
        const detalle = await response.json();
        
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
    console.log('Detalle.pasajeros:', detalle.pasajeros);
    console.log('Tipo de detalle.pasajeros:', typeof detalle.pasajeros);
    console.log('Es array?', Array.isArray(detalle.pasajeros));
    if (detalle.pasajeros) {
        console.log('Longitud de pasajeros:', detalle.pasajeros.length);
        console.log('Contenido de pasajeros:', detalle.pasajeros);
    }
    
    const detalleReserva = document.getElementById('detalleReserva');
    const seccionDetalle = document.getElementById('detalle-reserva');
    
    detalleReserva.innerHTML = 
        '<div class="detalle-completo">' +
            '<h4>Información de la Reserva</h4>' +
            '<p><strong>ID:</strong> ' + (detalle.id || 'Sin ID') + '</p>' +
            '<p><strong>Cliente:</strong> ' + (detalle.nicknameCliente || 'Sin cliente') + '</p>' +
            '<p><strong>Fecha de reserva:</strong> ' + (detalle.fechaReserva || 'Sin fecha') + '</p>' +
            '<p><strong>Costo:</strong> ' + (detalle.costoReserva ? (detalle.costoReserva.costoTotal || 'N/A') : 'N/A') + '</p>' +
            
            '<h4>Información del Vuelo</h4>' +
            '<p><strong>Nombre:</strong> ' + (detalle.vuelo.nombre || 'Sin nombre') + '</p>' +
            '<p><strong>Fecha:</strong> ' + (detalle.vuelo.fechaVuelo || 'Sin fecha') + '</p>' +
            '<p><strong>Hora:</strong> ' + (detalle.vuelo.horaVuelo || 'Sin hora') + '</p>' +
            '<p><strong>Duración:</strong> ' + (detalle.vuelo.duracion || 'Sin duración') + '</p>' +
            
            '<h4>Información de la Ruta</h4>' +
            '<p><strong>Nombre:</strong> ' + (detalle.ruta.nombre || 'Sin nombre') + '</p>' +
            '<p><strong>Descripción:</strong> ' + (detalle.ruta.descripcion || 'Sin descripción') + '</p>' +
            '<p><strong>Origen:</strong> ' + (detalle.ruta.ciudadOrigen || 'Sin origen') + '</p>' +
            '<p><strong>Destino:</strong> ' + (detalle.ruta.ciudadDestino || 'Sin destino') + '</p>' +
            
            '<h4>Información de la Aerolínea</h4>' +
            '<p><strong>Nombre:</strong> ' + (detalle.aerolinea.nombre || 'Sin nombre') + '</p>' +
            '<p><strong>Descripción:</strong> ' + (detalle.aerolinea.descripcion || 'Sin descripción') + '</p>' +
            
            '<h4>Pasajeros</h4>' +
            '<p>' + obtenerTextoPasajeros(detalle.pasajeros) + '</p>' +
        '</div>';
    
    seccionDetalle.style.display = 'block';
}

function obtenerTextoPasajeros(pasajeros) {
    console.log('Procesando pasajeros:', pasajeros);
    
    if (!pasajeros) {
        console.log('Pasajeros es null/undefined');
        return 'Información no disponible';
    }
    
    if (Array.isArray(pasajeros)) {
        console.log('Pasajeros es un array con', pasajeros.length, 'elementos');
        if (pasajeros.length === 0) {
            return 'No hay pasajeros registrados';
        }
        
        // Si es un array de strings
        if (typeof pasajeros[0] === 'string') {
            return pasajeros.join(', ');
        }
        
        // Si es un array de objetos, extraer nombres
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
    
    // Si es un objeto único
    if (typeof pasajeros === 'object') {
        if (pasajeros.nombre) return pasajeros.nombre;
        if (pasajeros.nickname) return pasajeros.nickname;
        return 'Pasajero sin nombre';
    }
    
    // Si es un string
    if (typeof pasajeros === 'string') {
        return pasajeros;
    }
    
    console.log('Tipo de pasajeros no reconocido:', typeof pasajeros);
    return 'Formato de pasajeros no reconocido';
}

function mostrarError(mensaje) {
    console.error('Error mostrado al usuario:', mensaje);
    
    const mensajeError = document.getElementById('mensaje-error');
    if (mensajeError) {
        mensajeError.textContent = mensaje;
        mensajeError.style.display = 'block';
        mensajeError.className = 'error-message';
        
        // Ocultar mensaje después de 10 segundos (más tiempo para debugging)
        setTimeout(() => {
            mensajeError.style.display = 'none';
        }, 10000);
    } else {
        // Si no existe el elemento, mostrar en consola y alert
        console.error('Elemento mensaje-error no encontrado');
        showToast('Error: ' + mensaje, 'error');
    }
}

function mostrarWarning(mensaje) {
    console.warn('Warning mostrado al usuario:', mensaje);
    
    const mensajeError = document.getElementById('mensaje-error');
    if (mensajeError) {
        mensajeError.textContent = mensaje;
        mensajeError.style.display = 'block';
        mensajeError.className = 'warning-message';
        
        // Ocultar mensaje después de 8 segundos
        setTimeout(() => {
            mensajeError.style.display = 'none';
        }, 8000);
    } else {
        // Si no existe el elemento, mostrar en consola y alert
        console.warn('Elemento mensaje-error no encontrado');
        showToast('Advertencia: ' + mensaje, 'warning');
    }
}

// Función para seleccionar ruta (para aerolíneas)
function seleccionarRuta(ruta) {
    console.log('Ruta seleccionada:', ruta);
    cargarVuelosRuta(ruta.nombre);
}
</script>

<style>
.info-inicial {
    margin-bottom: 20px;
}

.loading {
    text-align: center;
    padding: 20px;
    color: #666;
}

.controls-grid {
    display: grid;
    grid-template-columns: 1fr;
    gap: 15px;
    margin-bottom: 20px;
}

.section-content {
    margin-bottom: 20px;
}

.section-title {
    font-size: 1.2em;
    font-weight: bold;
    margin-bottom: 15px;
    color: #333;
}

.grid-rutas, .grid-vuelos, .grid-reservas {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 15px;
}

.ruta-card, .vuelo-card, .reserva-card {
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 15px;
    cursor: pointer;
    transition: all 0.3s ease;
    background: white;
}

.ruta-card:hover, .vuelo-card:hover, .reserva-card:hover {
    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    transform: translateY(-2px);
}

.card-body h4 {
    margin: 0 0 10px 0;
    color: #333;
}

.card-body p {
    margin: 5px 0;
    color: #666;
}

.detalle-card {
    background: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 20px;
    margin-top: 20px;
}

.detalle-completo h4 {
    color: #333;
    border-bottom: 1px solid #ddd;
    padding-bottom: 5px;
    margin-top: 20px;
    margin-bottom: 10px;
}

.detalle-completo h4:first-child {
    margin-top: 0;
}

.detalle-completo p {
    margin: 8px 0;
    color: #555;
}

.error-message {
    background: #ffebee;
    color: #c62828;
    padding: 10px;
    border-radius: 4px;
    margin: 10px 0;
    border-left: 4px solid #c62828;
}

.reserva-card {
    background: #e8f5e8;
    border-color: #4caf50;
}

.reserva-card:hover {
    background: #f1f8e9;
}
</style>