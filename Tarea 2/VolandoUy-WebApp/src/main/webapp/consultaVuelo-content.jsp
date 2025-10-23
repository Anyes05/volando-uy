<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="form-section-pro">
  <div class="form-card-pro">
    <h2>Consulta de Vuelo</h2>

    <!-- Selección de aerolínea -->
    <div class="controls-grid">
      <div class="form-group">
        <label for="selectAerolinea">Seleccionar Aerolínea</label>
        <select id="selectAerolinea">
          <option value="">-- Elegir aerolínea --</option>
        </select>
      </div>

      <div class="form-group">
        <label>Rutas de Vuelo</label>
        <div id="listaRutas" class="lista-rutas">Elija una aerolínea</div>
      </div>
    </div>

    <!-- Vuelos -->
    <div class="section-title">Vuelos disponibles</div>
    <div id="listaVuelos" class="grid-vuelos"></div>

    <!-- Detalle del vuelo -->
    <div id="detalle-vuelo" class="detalle-card" style="display:none;">
      <h3>Detalle del vuelo</h3>
      <img id="vuelo-img" alt="Imagen vuelo">
      <div id="vuelo-info"></div>
    </div>

    <!-- Reservas -->
    <div id="reservas" class="detalle-card" style="display:none;">
      <h3>Reservas</h3>
      <div id="lista-reservas"></div>
    </div>

    <!-- Detalle reserva -->
    <div id="detalle-reserva" class="detalle-card" style="display:none;">
      <h3>Detalle de Reserva</h3>
      <div id="detalleReserva"></div>
    </div>
  </div>
</section>

<!-- Script específico para consulta de vuelo -->
<script>
document.addEventListener('DOMContentLoaded', function() {
    initConsultaVuelo();
});

function initConsultaVuelo() {
    const selectAerolinea = document.getElementById('selectAerolinea');
    const listaRutas = document.getElementById('listaRutas');
    const listaVuelos = document.getElementById('listaVuelos');
    const detalleVueloDiv = document.getElementById('detalle-vuelo');
    const vueloImg = document.getElementById('vuelo-img');
    const vueloInfo = document.getElementById('vuelo-info');
    const reservasDiv = document.getElementById('reservas');
    const listaReservas = document.getElementById('lista-reservas');
    const detalleReservaDiv = document.getElementById('detalle-reserva');
    const detalleReservaInfo = document.getElementById('detalleReserva');

    // Verificar si hay parámetros de búsqueda desde la página de inicio
    const busquedaOrigen = sessionStorage.getItem('busquedaOrigen');
    const busquedaDestino = sessionStorage.getItem('busquedaDestino');
    const busquedaFecha = sessionStorage.getItem('busquedaFecha');
    
    // Limpiar parámetros de búsqueda después de leerlos
    if (busquedaOrigen || busquedaDestino || busquedaFecha) {
        sessionStorage.removeItem('busquedaOrigen');
        sessionStorage.removeItem('busquedaDestino');
        sessionStorage.removeItem('busquedaFecha');
        
        // Mostrar información de búsqueda
        mostrarInfoBusqueda(busquedaOrigen, busquedaDestino, busquedaFecha);
    }

    // Cargar aerolíneas al inicio
    cargarAerolineas();

    // Función para mostrar información de búsqueda
    function mostrarInfoBusqueda(origen, destino, fecha) {
        const infoDiv = document.createElement('div');
        infoDiv.className = 'busqueda-info';
        infoDiv.style.cssText = `
            background: rgba(1, 170, 245, 0.15);
            border: 1px solid rgba(1, 170, 245, 0.4);
            border-radius: 12px;
            padding: 15px;
            margin-bottom: 20px;
            color: #eaf6fb;
            backdrop-filter: blur(10px);
        `;
        
        infoDiv.innerHTML = `
            <h3 style="color: #01AAF5; margin: 0 0 10px 0;">Resultados de búsqueda</h3>
            <p style="margin: 5px 0;"><strong>Origen:</strong> ${origen || 'No especificado'}</p>
            <p style="margin: 5px 0;"><strong>Destino:</strong> ${destino || 'No especificado'}</p>
            <p style="margin: 5px 0;"><strong>Fecha:</strong> ${fecha || 'No especificada'}</p>
        `;
        
        // Insertar antes del primer elemento del formulario
        const formCard = document.querySelector('.form-card-pro');
        formCard.insertBefore(infoDiv, formCard.firstChild);
    }

    // Función para cargar aerolíneas
    function cargarAerolineas() {
        fetch('/VolandoUy-WebApp/api/vuelos/aerolineas')
            .then(response => {
                console.log('Response status:', response.status);
                if (!response.ok) {
                    throw new Error('HTTP ' + response.status);
                }
                return response.json();
            })
            .then(aerolineas => {
                console.log('Aerolíneas recibidas:', aerolineas);
                console.log('Tipo de aerolíneas:', typeof aerolineas);
                console.log('Es array:', Array.isArray(aerolineas));
                
                if (!Array.isArray(aerolineas)) {
                    throw new Error('Los datos recibidos no son un array');
                }
                
                selectAerolinea.innerHTML = '<option value="">-- Elegir aerolínea --</option>';
                aerolineas.forEach(aero => {
                    const option = document.createElement('option');
                    option.value = aero.nickname;
                    option.textContent = aero.nombre;
                    selectAerolinea.appendChild(option);
                });
            })
            .catch(error => {
                console.error('Error al cargar aerolíneas:', error);
                selectAerolinea.innerHTML = '<option value="">Error al cargar aerolíneas</option>';
            });
    }

    // Event listener para cambio de aerolínea
    selectAerolinea.addEventListener('change', function() {
        const nickname = selectAerolinea.value;
        clearAfterAerolinea();
        
        if (!nickname) {
            listaRutas.innerHTML = 'Elija una aerolínea';
            return;
        }

        // Cargar rutas de la aerolínea seleccionada
        fetch(`/VolandoUy-WebApp/api/vuelos/rutas/\${nickname}`)
            .then(response => {
                console.log('Response status para rutas:', response.status);
                if (!response.ok) {
                    throw new Error('HTTP ' + response.status);
                }
                return response.json();
            })
            .then(rutas => {
                console.log('Rutas recibidas:', rutas);
                console.log('Tipo de rutas:', typeof rutas);
                console.log('Es array:', Array.isArray(rutas));
                
                if (!Array.isArray(rutas)) {
                    throw new Error('Los datos recibidos no son un array');
                }
                
                if (rutas.length === 0) {
                    listaRutas.innerHTML = 'No hay rutas confirmadas para esta aerolínea';
                    return;
                }

                listaRutas.innerHTML = '';
                
                // Filtrar rutas según parámetros de búsqueda si existen
                let rutasFiltradas = rutas;
                if (busquedaOrigen || busquedaDestino) {
                    rutasFiltradas = rutas.filter(ruta => {
                        const origenMatch = !busquedaOrigen || 
                            ruta.ciudadOrigen.nombre.toLowerCase().includes(busquedaOrigen.toLowerCase());
                        const destinoMatch = !busquedaDestino || 
                            ruta.ciudadDestino.nombre.toLowerCase().includes(busquedaDestino.toLowerCase());
                        return origenMatch && destinoMatch;
                    });
                }
                
                if (rutasFiltradas.length === 0) {
                    listaRutas.innerHTML = 'No se encontraron rutas que coincidan con la búsqueda';
                    return;
                }
                
                rutasFiltradas.forEach(ruta => {
                    const pill = document.createElement('div');
                    pill.className = 'ruta-pill';
                    pill.textContent = `\${ruta.nombre} (\${ruta.ciudadOrigen.nombre} → \${ruta.ciudadDestino.nombre})`;
                    pill.dataset.rutaNombre = ruta.nombre;
                    pill.addEventListener('click', () => {
                        // Remover selección anterior
                        document.querySelectorAll('.ruta-pill').forEach(p => p.classList.remove('active'));
                        pill.classList.add('active');
                        mostrarVuelos(ruta.nombre, busquedaFecha);
                    });
                    listaRutas.appendChild(pill);
                });
                
                // Si hay solo una ruta filtrada, seleccionarla automáticamente
                if (rutasFiltradas.length === 1) {
                    const primeraPill = listaRutas.querySelector('.ruta-pill');
                    if (primeraPill) {
                        primeraPill.click();
                    }
                }
            })
            .catch(error => {
                console.error('Error al cargar rutas:', error);
                listaRutas.innerHTML = 'Error al cargar rutas: ' + error.message;
            });
    });

    // Función para mostrar vuelos de una ruta
    function mostrarVuelos(nombreRuta, fechaFiltro = null) {
        listaVuelos.innerHTML = '';
        detalleVueloDiv.style.display = 'none';
        reservasDiv.style.display = 'none';
        detalleReservaDiv.style.display = 'none';

        fetch(`/VolandoUy-WebApp/api/vuelos/vuelos/\${encodeURIComponent(nombreRuta)}`)
            .then(response => response.json())
            .then(vuelos => {
                if (vuelos.length === 0) {
                    listaVuelos.innerHTML = '<p>No hay vuelos para esta ruta.</p>';
                    return;
                }

                // Filtrar vuelos por fecha si se proporciona
                let vuelosFiltrados = vuelos;
                if (fechaFiltro) {
                    vuelosFiltrados = vuelos.filter(vuelo => {
                        const fechaVuelo = new Date(vuelo.fechaVuelo);
                        const fechaBusqueda = new Date(fechaFiltro);
                        return fechaVuelo.toDateString() === fechaBusqueda.toDateString();
                    });
                }

                if (vuelosFiltrados.length === 0) {
                    listaVuelos.innerHTML = '<p>No hay vuelos disponibles para la fecha especificada.</p>';
                    return;
                }

                vuelosFiltrados.forEach(vuelo => {
                    const card = document.createElement('div');
                    card.className = 'vuelo-card';
                    const imagenHtml = vuelo.foto ? 
                        `<img src="\${vuelo.foto}" alt="\${vuelo.nombre}">` : 
                        '<div class="no-image">Sin imagen</div>';
                    
                    card.innerHTML = `
                        \${imagenHtml}
                        <div class="card-body">
                            <h4>\${vuelo.nombre}</h4>
                            <p>\${vuelo.ruta.nombre} — \${vuelo.fechaVuelo} \${vuelo.horaVuelo}</p>
                        </div>
                    `;
                    card.addEventListener('click', () => mostrarDetalleVuelo(vuelo));
                    listaVuelos.appendChild(card);
                });
            })
            .catch(error => {
                console.error('Error al cargar vuelos:', error);
                listaVuelos.innerHTML = '<p>Error al cargar vuelos</p>';
            });
    }

    // Función para mostrar detalle del vuelo
    function mostrarDetalleVuelo(vuelo) {
        detalleVueloDiv.style.display = 'block';
        detalleReservaDiv.style.display = 'none';

        vueloImg.src = vuelo.foto || '';
        vueloImg.style.display = vuelo.foto ? 'block' : 'none';
        
        vueloInfo.innerHTML = `
            <p><strong>Nombre:</strong> \${vuelo.nombre}</p>
            <p><strong>Fecha:</strong> \${vuelo.fechaVuelo}</p>
            <p><strong>Hora:</strong> \${vuelo.horaVuelo}</p>
            <p><strong>Duración:</strong> \${vuelo.duracion}</p>
            <p><strong>Asientos Turista (máx):</strong> \${vuelo.asientosMaxTurista}</p>
            <p><strong>Asientos Ejecutivo (máx):</strong> \${vuelo.asientosMaxEjecutivo}</p>
            <p><strong>Fecha de alta:</strong> \${vuelo.fechaAlta || '—'}</p>
        `;

        // Verificar si el usuario puede ver reservas y mostrarlas solo si corresponde
        verificarYMostrarReservas(vuelo.nombre);
    }

    // Función para verificar permisos y mostrar reservas
    function verificarYMostrarReservas(nombreVuelo) {
        // Obtener información de sesión desde atributos de la página
        const tipoUsuario = '${sessionScope.tipoUsuario}' || 'desconocido';
        const usuarioLogueado = '${sessionScope.usuarioLogueado}' || '';
        
        // Solo mostrar reservas si es aerolínea o cliente
        if (tipoUsuario === 'aerolinea' || tipoUsuario === 'cliente') {
            reservasDiv.style.display = 'block';
            mostrarReservas(nombreVuelo);
        } else {
            // Si no es aerolínea ni cliente, no mostrar reservas
            reservasDiv.style.display = 'none';
        }
    }

    // Función para mostrar reservas según tipo de usuario
    function mostrarReservas(nombreVuelo) {
        listaReservas.innerHTML = '';
        detalleReservaDiv.style.display = 'none';

        fetch(`/VolandoUy-WebApp/api/vuelos/reservas/\${encodeURIComponent(nombreVuelo)}`)
            .then(response => response.json())
            .then(reservas => {
                if (reservas.length === 0) {
                    listaReservas.innerHTML = '<p>No hay reservas disponibles para mostrar.</p>';
                    return;
                }

                reservas.forEach((reserva) => {
                    const div = document.createElement('div');
                    div.className = 'reserva-card';
                    div.innerHTML = '<strong>' + reserva.cliente + '</strong> — Fecha: ' + reserva.fechaReserva + ' • Costo: ' + formatearCostoReserva(reserva.costoReserva);
                    div.addEventListener('click', () => mostrarDetalleReserva(reserva));
                    listaReservas.appendChild(div);
                });
            })
            .catch(error => {
                console.error('Error al cargar reservas:', error);
                listaReservas.innerHTML = '<p>Error al cargar reservas</p>';
            });
    }

    // Función para formatear el costo de la reserva
    function formatearCostoReserva(costoReserva) {
        if (!costoReserva) return '$0';
        
        // Si es un número simple, devolverlo directamente
        if (typeof costoReserva === 'number') {
            return '$' + costoReserva;
        }
        
        // Si es un string, parsearlo
        if (typeof costoReserva === 'string') {
            const parsed = parseFloat(costoReserva);
            return '$' + (isNaN(parsed) ? 0 : parsed);
        }
        
        // Si es un objeto DTCostoBase con estructura de costos
        if (typeof costoReserva === 'object') {
            // Si tiene costoTotal, usarlo
            if (costoReserva.costoTotal !== undefined) {
                return '$' + costoReserva.costoTotal;
            }
            
            // Si tiene monto (estructura del JSON de ejemplo)
            if (costoReserva.monto !== undefined) {
                return '$' + costoReserva.monto + (costoReserva.moneda ? ' ' + costoReserva.moneda : '');
            }
            
            // Si tiene costoTurista y costoEjecutivo, mostrar ambos
            if (costoReserva.costoTurista !== undefined && costoReserva.costoEjecutivo !== undefined) {
                const turista = costoReserva.costoTurista || 0;
                const ejecutivo = costoReserva.costoEjecutivo || 0;
                
                if (ejecutivo > 0 && turista !== ejecutivo) {
                    return '$' + turista + ' (Turista) / $' + ejecutivo + ' (Ejecutivo)';
                } else {
                    return '$' + turista;
                }
            }
            
            // Fallback: intentar obtener cualquier valor numérico
            for (const key in costoReserva) {
                if (typeof costoReserva[key] === 'number') {
                    return '$' + costoReserva[key];
                }
            }
        }
        
        // Fallback final
        try {
            const parsed = parseFloat(String(costoReserva));
            return '$' + (isNaN(parsed) ? 0 : parsed);
        } catch (e) {
            console.warn('No se pudo parsear costoReserva:', costoReserva);
            return '$0';
        }
    }

    // Función para mostrar detalle de reserva
    function mostrarDetalleReserva(reserva) {
        detalleReservaDiv.style.display = 'block';
        
        let pasajerosHtml = '';
        if (reserva.pasajeros && reserva.pasajeros.length > 0) {
            pasajerosHtml = '<h4>Pasajeros:</h4><ul>';
            reserva.pasajeros.forEach((pasajero) => {
                const nombre = pasajero.nombre || '';
                const apellido = pasajero.apellido || '';
                
                pasajerosHtml += '<li><strong>' + nombre + ' ' + apellido + '</strong>';
                if (pasajero.nickname) {
                    pasajerosHtml += ' (' + pasajero.nickname + ')';
                }
                pasajerosHtml += '</li>';
            });
            pasajerosHtml += '</ul>';
        }
        
        detalleReservaInfo.innerHTML = 
            '<p><strong>ID:</strong> ' + reserva.id + '</p>' +
            '<p><strong>Cliente:</strong> ' + reserva.cliente + '</p>' +
            '<p><strong>Fecha de reserva:</strong> ' + reserva.fechaReserva + '</p>' +
            '<p><strong>Costo:</strong> ' + formatearCostoReserva(reserva.costoReserva) + '</p>' +
            pasajerosHtml;
    }

    // Función para limpiar secciones al cambiar aerolínea
    function clearAfterAerolinea() {
        listaVuelos.innerHTML = '';
        detalleVueloDiv.style.display = 'none';
        reservasDiv.style.display = 'none';
        detalleReservaDiv.style.display = 'none';
    }
}
</script>

<style>
/* Consistente con el tema glassmorphism */
.ruta-pill {
    background: rgba(1, 170, 245, 0.15);
    border: 1px solid rgba(1, 170, 245, 0.4);
    border-radius: 20px;
    padding: 8px 14px;
    margin: 0;
    display: inline-block;
    cursor: pointer;
    transition: all 0.3s ease;
    backdrop-filter: blur(10px);
    color: #eaf6fb;
    font-weight: 500;
    font-size: 14px;
    white-space: nowrap;
}

.ruta-pill:hover {
    background: rgba(1, 170, 245, 0.25);
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(1, 170, 245, 0.3);
}

.ruta-pill.active {
    background: linear-gradient(135deg, #01AAF5, #0088cc);
    color: white;
    box-shadow: 0 4px 15px rgba(1, 170, 245, 0.4);
}

.vuelo-card {
    background: rgba(255, 255, 255, 0.08);
    backdrop-filter: blur(12px);
    border: 1px solid rgba(255, 255, 255, 0.15);
    border-radius: 12px;
    padding: 18px;
    margin: 8px;
    cursor: pointer;
    transition: all 0.3s ease;
    color: #eaf6fb;
}

.vuelo-card:hover {
    background: rgba(255, 255, 255, 0.12);
    box-shadow: 0 8px 25px rgba(0,0,0,0.3);
    transform: translateY(-3px);
    border-color: rgba(1, 170, 245, 0.4);
}

.vuelo-card img {
    width: 100%;
    height: 150px;
    object-fit: cover;
    border-radius: 8px;
    margin-bottom: 12px;
}

.no-image {
    width: 100%;
    height: 150px;
    background: rgba(255, 255, 255, 0.05);
    border: 2px dashed rgba(255, 255, 255, 0.2);
    display: flex;
    align-items: center;
    justify-content: center;
    color: rgba(234, 246, 251, 0.6);
    border-radius: 8px;
    margin-bottom: 12px;
    font-style: italic;
}

.reserva-card {
    background: rgba(255, 255, 255, 0.06);
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 8px;
    padding: 12px 16px;
    margin: 8px 0;
    cursor: pointer;
    transition: all 0.3s ease;
    color: #eaf6fb;
}

.reserva-card:hover {
    background: rgba(1, 170, 245, 0.15);
    border-color: rgba(1, 170, 245, 0.3);
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(1, 170, 245, 0.2);
}

.detalle-card {
    background: rgba(255, 255, 255, 0.08);
    backdrop-filter: blur(12px);
    border: 1px solid rgba(255, 255, 255, 0.15);
    border-radius: 12px;
    padding: 20px;
    margin: 20px 0;
    color: #eaf6fb;
    box-shadow: 0 4px 20px rgba(0,0,0,0.2);
}

.detalle-card h3 {
    color: #01AAF5;
    margin-bottom: 16px;
    font-weight: 600;
}

.grid-vuelos {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 18px;
    margin: 20px 0;
}

.lista-rutas {
    min-height: 60px;
    max-height: 200px;
    overflow-y: auto;
    padding: 15px;
    background: rgba(255, 255, 255, 0.06);
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.15);
    border-radius: 12px;
    color: #eaf6fb;
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
    align-content: flex-start;
}

/* Imagen de vuelo en detalle */
#vuelo-img {
    max-width: 100%;
    height: auto;
    border-radius: 8px;
    margin-bottom: 16px;
    box-shadow: 0 4px 12px rgba(0,0,0,0.3);
}

/* Información del vuelo */
#vuelo-info p {
    margin: 8px 0;
    color: #eaf6fb;
}

#vuelo-info strong {
    color: #01AAF5;
}
</style>
