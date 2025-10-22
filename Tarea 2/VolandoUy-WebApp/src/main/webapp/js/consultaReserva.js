// JavaScript para consulta de reservas de vuelo
let infoInicial = null;
let rutaSeleccionada = null;
let vueloSeleccionado = null;

// Función de inicialización
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
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }
        
        const info = await response.json();
        
        if (info.error) {
            mostrarError(info.error);
            return;
        }
        
        infoInicial = info;
        console.log('Información inicial:', info);
        
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
            card.innerHTML = `
                <div class="card-body">
                    <h4>${ruta.nombre}</h4>
                    <p>${ruta.ciudadOrigen} → ${ruta.ciudadDestino}</p>
                    <p>${ruta.descripcion}</p>
                </div>
            `;
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
        const response = await fetch(`/VolandoUy-WebApp/api/consulta-reservas/rutas/${encodeURIComponent(aerolineaNickname)}`);
        const rutas = await response.json();
        
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
    const selectRuta = document.getElementById('selectRuta');
    const seccionRuta = document.getElementById('seleccion-ruta');
    
    selectRuta.innerHTML = '<option value="">-- Elegir ruta --</option>';
    rutas.forEach(ruta => {
        const option = document.createElement('option');
        option.value = ruta.nombre;
        option.textContent = `${ruta.nombre} (${ruta.ciudadOrigen} → ${ruta.ciudadDestino})`;
        selectRuta.appendChild(option);
    });
    
    selectRuta.addEventListener('change', (e) => {
        if (e.target.value) {
            cargarVuelosRuta(e.target.value);
        }
    });
    
    seccionRuta.style.display = 'block';
}

async function cargarVuelosRuta(rutaNombre) {
    try {
        const response = await fetch(`/VolandoUy-WebApp/api/consulta-reservas/vuelos/${encodeURIComponent(rutaNombre)}`);
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
            card.innerHTML = `
                <div class="card-body">
                    <h4>${vuelo.nombre}</h4>
                    <p>Fecha: ${vuelo.fechaVuelo} ${vuelo.horaVuelo}</p>
                    <p>Duración: ${vuelo.duracion}</p>
                    <p>Asientos: ${vuelo.asientosMaxTurista} Turista, ${vuelo.asientosMaxEjecutivo} Ejecutivo</p>
                </div>
            `;
            card.addEventListener('click', () => seleccionarVuelo(vuelo));
            listaVuelos.appendChild(card);
        });
    }
    
    seccionVuelos.style.display = 'block';
}

async function seleccionarVuelo(vuelo) {
    console.log('Vuelo seleccionado:', vuelo);
    vueloSeleccionado = vuelo;
    
    // Verificar tipo de usuario para mostrar reservas o reserva del cliente
    if (infoInicial.tipoUsuario === 'aerolinea') {
        // Para aerolíneas: mostrar todas las reservas del vuelo
        cargarReservasVuelo(vuelo.nombre);
    } else if (infoInicial.tipoUsuario === 'cliente') {
        // Para clientes: mostrar su reserva para este vuelo
        cargarReservaCliente(vuelo.nombre);
    }
}

async function cargarReservasVuelo(vueloNombre) {
    try {
        const response = await fetch(`/VolandoUy-WebApp/api/consulta-reservas/reservas-vuelo/${encodeURIComponent(vueloNombre)}`);
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
    const listaReservas = document.getElementById('listaReservas');
    const seccionReservas = document.getElementById('lista-reservas');
    
    if (reservas.length === 0) {
        listaReservas.innerHTML = '<p>No hay reservas para este vuelo.</p>';
    } else {
        listaReservas.innerHTML = '';
        reservas.forEach(reserva => {
            const card = document.createElement('div');
            card.className = 'reserva-card';
            card.innerHTML = `
                <div class="card-body">
                    <h4>Reserva #${reserva.id}</h4>
                    <p>Cliente: ${reserva.nicknameCliente}</p>
                    <p>Fecha de reserva: ${reserva.fechaReserva}</p>
                    <p>Vuelo: ${reserva.vueloNombre}</p>
                    <p>Fecha de vuelo: ${reserva.fechaVuelo} ${reserva.horaVuelo}</p>
                </div>
            `;
            card.addEventListener('click', () => mostrarDetalleReserva(reserva.id));
            listaReservas.appendChild(card);
        });
    }
    
    seccionReservas.style.display = 'block';
}

async function cargarReservaCliente(vueloNombre) {
    try {
        const response = await fetch(`/VolandoUy-WebApp/api/consulta-reservas/reserva-cliente/${encodeURIComponent(vueloNombre)}`);
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
        reservaCliente.innerHTML = `
            <div class="reserva-card">
                <div class="card-body">
                    <h4>Mi Reserva #${reserva.id}</h4>
                    <p>Fecha de reserva: ${reserva.fechaReserva}</p>
                    <p>Vuelo: ${reserva.vueloNombre}</p>
                    <p>Fecha de vuelo: ${reserva.fechaVuelo} ${reserva.horaVuelo}</p>
                </div>
            </div>
        `;
        
        // Agregar evento de clic para mostrar detalle
        reservaCliente.addEventListener('click', () => mostrarDetalleReserva(reserva.id));
    }
    
    seccionReserva.style.display = 'block';
}

async function mostrarDetalleReserva(reservaId) {
    try {
        const response = await fetch(`/VolandoUy-WebApp/api/consulta-reservas/detalle-reserva/${reservaId}`);
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
    const detalleReserva = document.getElementById('detalleReserva');
    const seccionDetalle = document.getElementById('detalle-reserva');
    
    detalleReserva.innerHTML = `
        <div class="detalle-completo">
            <h4>Información de la Reserva</h4>
            <p><strong>ID:</strong> ${detalle.id}</p>
            <p><strong>Cliente:</strong> ${detalle.nicknameCliente}</p>
            <p><strong>Fecha de reserva:</strong> ${detalle.fechaReserva}</p>
            <p><strong>Costo:</strong> ${detalle.costoReserva ? detalle.costoReserva.costoTotal || 'N/A' : 'N/A'}</p>
            
            <h4>Información del Vuelo</h4>
            <p><strong>Nombre:</strong> ${detalle.vuelo.nombre}</p>
            <p><strong>Fecha:</strong> ${detalle.vuelo.fechaVuelo}</p>
            <p><strong>Hora:</strong> ${detalle.vuelo.horaVuelo}</p>
            <p><strong>Duración:</strong> ${detalle.vuelo.duracion}</p>
            
            <h4>Información de la Ruta</h4>
            <p><strong>Nombre:</strong> ${detalle.ruta.nombre}</p>
            <p><strong>Descripción:</strong> ${detalle.ruta.descripcion}</p>
            <p><strong>Origen:</strong> ${detalle.ruta.ciudadOrigen}</p>
            <p><strong>Destino:</strong> ${detalle.ruta.ciudadDestino}</p>
            
            <h4>Información de la Aerolínea</h4>
            <p><strong>Nombre:</strong> ${detalle.aerolinea.nombre}</p>
            <p><strong>Descripción:</strong> ${detalle.aerolinea.descripcion}</p>
            
            <h4>Pasajeros</h4>
            <p>${detalle.pasajeros.length > 0 ? detalle.pasajeros.join(', ') : 'Información no disponible'}</p>
        </div>
    `;
    
    seccionDetalle.style.display = 'block';
}

function mostrarError(mensaje) {
    console.error('Error mostrado al usuario:', mensaje);
    
    const mensajeError = document.getElementById('mensaje-error');
    if (mensajeError) {
        mensajeError.textContent = mensaje;
        mensajeError.style.display = 'block';
        
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

// Función para seleccionar ruta (para aerolíneas)
function seleccionarRuta(ruta) {
    console.log('Ruta seleccionada:', ruta);
    rutaSeleccionada = ruta;
    cargarVuelosRuta(ruta.nombre);
}

// Auto-inicializar cuando se carga la página
document.addEventListener('DOMContentLoaded', function() {
    initConsultaReserva();
});
