// JavaScript para consulta de ruta de vuelo
let rutasData = [];
let rutaSeleccionada = null;
let vueloSeleccionado = null;

// Función de inicialización
function initConsultaRutaVuelo() {
    console.log('Inicializando consulta de ruta de vuelo...');
    
    const selectAerolinea = document.getElementById('select-aerolinea');
    const selectCategoria = document.getElementById('select-categoria');
    const buscadorNombre = document.getElementById('buscador-nombre');
    const listaRutas = document.getElementById('lista-rutas');
    const listaVuelos = document.getElementById('lista-vuelos');
    const mensajeVuelo = document.getElementById('mensaje-vuelo');
    const rutasStats = document.getElementById('rutas-stats');

    // Verificar que todos los elementos existen
    if (!selectAerolinea || !selectCategoria || !buscadorNombre || !listaRutas || !listaVuelos) {
        console.error('Elementos del DOM no encontrados');
        return;
    }

    // Verificar si hay una ruta seleccionada desde otra vista
    const rutaSeleccionadaStorage = sessionStorage.getItem('rutaSeleccionada');
    if (rutaSeleccionadaStorage) {
        try {
            const rutaData = JSON.parse(rutaSeleccionadaStorage);
            console.log('Ruta seleccionada encontrada:', rutaData);
            
            // Limpiar el sessionStorage
            sessionStorage.removeItem('rutaSeleccionada');
            
            // Guardar el nombre de la ruta para seleccionarla después de cargar
            window.rutaASeleccionar = rutaData.nombre;
        } catch (error) {
            console.error('Error al parsear ruta seleccionada:', error);
            sessionStorage.removeItem('rutaSeleccionada');
        }
    }

    // Cargar datos iniciales
    cargarAerolineas();
    cargarCategorias();
    cargarTodasLasRutas();

    // Event listeners
    selectAerolinea.addEventListener('change', filtrarRutas);
    selectCategoria.addEventListener('change', filtrarRutas);
    buscadorNombre.addEventListener('input', filtrarRutas);

    // Función para cargar aerolíneas
    async function cargarAerolineas() {
        try {
            console.log('Cargando aerolíneas...');
            const response = await fetch('/VolandoUy-WebApp/api/rutas/aerolineas');
            
            if (!response.ok) {
                throw new Error('Error HTTP: ' + response.status);
            }
            
            const aerolineas = await response.json();
            console.log('Aerolíneas recibidas:', aerolineas);
            
            selectAerolinea.innerHTML = '<option value="">Todas las aerolíneas</option>';
            
            if (Array.isArray(aerolineas)) {
                aerolineas.forEach(function(aero) {
                    const option = document.createElement('option');
                    option.value = aero.nickname;
                    option.textContent = aero.nombre;
                    selectAerolinea.appendChild(option);
                });
                console.log('Aerolíneas cargadas en el select:', aerolineas.length);
            }
        } catch (error) {
            console.error('Error al cargar aerolíneas:', error);
            selectAerolinea.innerHTML = '<option value="">Error al cargar aerolíneas</option>';
        }
    }

    // Función para cargar categorías
    async function cargarCategorias() {
        try {
            console.log('Cargando categorías...');
            const response = await fetch('/VolandoUy-WebApp/api/rutas/categorias');
            
            if (!response.ok) {
                throw new Error('Error HTTP: ' + response.status);
            }
            
            const categorias = await response.json();
            console.log('Categorías recibidas:', categorias);
            
            selectCategoria.innerHTML = '<option value="">Todas las categorías</option>';
            
            if (Array.isArray(categorias)) {
                categorias.forEach(function(cat) {
                    const option = document.createElement('option');
                    option.value = cat;
                    option.textContent = cat;
                    selectCategoria.appendChild(option);
                });
                console.log('Categorías cargadas en el select:', categorias.length);
            }
        } catch (error) {
            console.error('Error al cargar categorías:', error);
            selectCategoria.innerHTML = '<option value="">Error al cargar categorías</option>';
        }
    }

    // Función para cargar todas las rutas
    async function cargarTodasLasRutas() {
        try {
            console.log('Iniciando carga de rutas...');
            
            const response = await fetch('/VolandoUy-WebApp/api/rutas/aerolineas');
            
            if (!response.ok) {
                throw new Error('Error HTTP: ' + response.status);
            }
            
            const aerolineas = await response.json();
            console.log('Aerolíneas cargadas:', aerolineas);
            
            let todasLasRutas = [];
            
            // Procesar cada aerolínea de forma secuencial
            for (let i = 0; i < aerolineas.length; i++) {
                const aero = aerolineas[i];
                try {
                    console.log('Cargando rutas de aerolínea: ' + aero.nombre);
                    const rutasResponse = await fetch('/VolandoUy-WebApp/api/rutas?aerolinea=' + encodeURIComponent(aero.nickname));
                    
                    if (rutasResponse.ok) {
                        const rutas = await rutasResponse.json();
                        if (Array.isArray(rutas)) {
                            todasLasRutas = todasLasRutas.concat(rutas);
                            console.log('Rutas cargadas de ' + aero.nombre + ':', rutas.length);
                        }
                    } else if (rutasResponse.status === 500) {
                        // Error del servidor - intentar precargar si es el primer error
                        console.warn('Error HTTP 500 cargando rutas de ' + aero.nombre);
                        if (i === 0) {
                            console.log('Intentando precargar sistema...');
                            try {
                                const precargarResponse = await fetch('/VolandoUy-WebApp/api/rutas/precargar', {
                                    method: 'GET'
                                });
                                if (precargarResponse.ok) {
                                    console.log('Sistema precargado exitosamente, reintentando...');
                                    // Reintentar esta aerolínea
                                    const retryResponse = await fetch('/VolandoUy-WebApp/api/rutas?aerolinea=' + encodeURIComponent(aero.nickname));
                                    if (retryResponse.ok) {
                                        const retryRutas = await retryResponse.json();
                                        if (Array.isArray(retryRutas)) {
                                            todasLasRutas = todasLasRutas.concat(retryRutas);
                                            console.log('Rutas cargadas de ' + aero.nombre + ' (después de precarga):', retryRutas.length);
                                        }
                                    }
                                } else {
                                    console.error('Error al precargar sistema');
                                }
                            } catch (precargarError) {
                                console.error('Error al precargar sistema:', precargarError);
                            }
                        }
                    } else {
                        console.warn('Error HTTP ' + rutasResponse.status + ' cargando rutas de ' + aero.nombre);
                    }
                } catch (error) {
                    console.warn('Error cargando rutas de ' + aero.nombre + ':', error);
                }
            }
            
            console.log('Total de rutas cargadas:', todasLasRutas.length);
            rutasData = todasLasRutas;
            mostrarRutas(todasLasRutas);
        } catch (error) {
            console.error('Error al cargar rutas:', error);
            mostrarError('Error al cargar las rutas disponibles');
        }
    }

    // Función para filtrar rutas
    async function filtrarRutas() {
        const aerolineaSeleccionada = selectAerolinea.value;
        const categoriaSeleccionada = selectCategoria.value;
        const textoBusqueda = buscadorNombre.value.toLowerCase();

        // Siempre limpiar la selección al cambiar filtros
        limpiarSeleccionVuelos();

        let rutasFiltradas = rutasData.slice(); // Crear copia del array

        // Filtrar por aerolínea
        if (aerolineaSeleccionada) {
            try {
                const response = await fetch('/VolandoUy-WebApp/api/rutas?aerolinea=' + encodeURIComponent(aerolineaSeleccionada));
                if (response.ok) {
                    const rutasAerolinea = await response.json();
                    rutasFiltradas = rutasAerolinea;
                }
            } catch (error) {
                console.error('Error filtrando por aerolínea:', error);
            }
        }

        // Filtrar por categoría
        if (categoriaSeleccionada) {
            try {
                const response = await fetch('/VolandoUy-WebApp/api/rutas/por-categoria?categoria=' + encodeURIComponent(categoriaSeleccionada));
                if (response.ok) {
                    const rutasCategoria = await response.json();
                    rutasFiltradas = rutasFiltradas.filter(function(ruta) {
                        return rutasCategoria.some(function(rc) {
                            return rc.nombre === ruta.nombre;
                        });
                    });
                }
            } catch (error) {
                console.error('Error filtrando por categoría:', error);
            }
        }

        // Filtrar por texto de búsqueda
        if (textoBusqueda) {
            rutasFiltradas = rutasFiltradas.filter(function(ruta) {
                return ruta.nombre.toLowerCase().includes(textoBusqueda) ||
                       ruta.descripcion.toLowerCase().includes(textoBusqueda) ||
                       (ruta.ciudadOrigen && ruta.ciudadOrigen.nombre.toLowerCase().includes(textoBusqueda)) ||
                       (ruta.ciudadDestino && ruta.ciudadDestino.nombre.toLowerCase().includes(textoBusqueda));
            });
        }

        mostrarRutas(rutasFiltradas);
    }

    // Función para mostrar rutas
    function mostrarRutas(rutas) {
        listaRutas.innerHTML = '';
        
        if (rutasStats) {
            const texto = rutas.length === 1 ? 'ruta encontrada' : 'rutas encontradas';
            rutasStats.innerHTML = '<span class="total-rutas">' + rutas.length + ' ' + texto + '</span>';
        }

        if (rutas.length === 0) {
            listaRutas.innerHTML = '<div style="text-align: center; padding: 2rem; color: #eaf6fb; grid-column: 1 / -1;"><i class="fas fa-route" style="font-size: 3rem; margin-bottom: 1rem; color: #01aaf5; opacity: 0.6;"></i><p>No se encontraron rutas con los filtros aplicados.</p></div>';
            limpiarSeleccionVuelos(); // Limpiar selección si no hay rutas
            return;
        }

        rutas.forEach(function(ruta) {
            // Debug: log temporal para ver la estructura del costoBase
            if (ruta.costoBase) {
                console.log('Costo base de ruta "' + ruta.nombre + '":', ruta.costoBase, 'Tipo:', typeof ruta.costoBase);
            }
            
            const card = document.createElement('div');
            card.className = 'ruta-card';
            card.onclick = function() {
                seleccionarRuta(ruta);
            };

            const imagenHtml = ruta.imagen ? 
                '<img src="' + ruta.imagen + '" alt="' + ruta.nombre + '">' : 
                '<div class="no-image"><i class="fas fa-route"></i><p>Sin imagen</p></div>';

            card.innerHTML = imagenHtml +
                '<h3>' + ruta.nombre + '</h3>' +
                '<p>' + (ruta.descripcion || 'Sin descripción') + '</p>' +
                '<p><strong>Origen:</strong> ' + (ruta.ciudadOrigen ? ruta.ciudadOrigen.nombre : 'N/A') + '</p>' +
                '<p><strong>Destino:</strong> ' + (ruta.ciudadDestino ? ruta.ciudadDestino.nombre : 'N/A') + '</p>' +
                '<p><strong>Costo base:</strong> ' + formatearCostosRuta(ruta.costoBase) + '</p>' +
                '<button class="btn-ver-mas">Ver vuelos</button>';

            listaRutas.appendChild(card);
        });
        
        // Si hay una ruta para seleccionar automáticamente (viene de sessionStorage)
        if (window.rutaASeleccionar) {
            const rutaParaSeleccionar = rutas.find(r => r.nombre === window.rutaASeleccionar);
            if (rutaParaSeleccionar) {
                console.log('Seleccionando automáticamente la ruta:', window.rutaASeleccionar);
                // Buscar la tarjeta de la ruta y hacer click en ella
                setTimeout(() => {
                    const cards = document.querySelectorAll('.ruta-card');
                    cards.forEach(card => {
                        if (card.textContent.includes(window.rutaASeleccionar)) {
                            card.click();
                        }
                    });
                    window.rutaASeleccionar = null; // Limpiar la variable
                }, 100);
            }
        }
    }

    // Función helper para formatear los costos de la ruta
    function formatearCostosRuta(costoBase) {
        if (!costoBase) return '$0';
        
        // Si es un número simple, devolverlo directamente
        if (typeof costoBase === 'number') {
            return '$' + costoBase;
        }
        
        // Si es un string, parsearlo
        if (typeof costoBase === 'string') {
            const parsed = parseFloat(costoBase);
            return '$' + (isNaN(parsed) ? 0 : parsed);
        }
        
        // Si es el objeto con estructura de costos múltiples
        if (typeof costoBase === 'object' && costoBase.costoTurista !== undefined) {
            const turista = costoBase.costoTurista || 0;
            const ejecutivo = costoBase.costoEjecutivo || 0;
            
            // Mostrar ambos precios si son diferentes
            if (ejecutivo > 0 && turista !== ejecutivo) {
                return '$' + turista + ' (Turista) / $' + ejecutivo + ' (Ejecutivo)';
            } else {
                // Solo mostrar el precio turista si es el único disponible
                return '$' + turista;
            }
        }
        
        // Si es un objeto simple con otras propiedades, intentar obtener valor
        if (typeof costoBase === 'object') {
            if (costoBase.value !== undefined) return '$' + (parseFloat(costoBase.value) || 0);
            if (costoBase.amount !== undefined) return '$' + (parseFloat(costoBase.amount) || 0);
            if (costoBase.valueOf !== undefined) return '$' + (parseFloat(costoBase.valueOf()) || 0);
            if (costoBase.toString !== undefined) {
                const str = costoBase.toString();
                const parsed = parseFloat(str);
                return '$' + (isNaN(parsed) ? 0 : parsed);
            }
        }
        
        // Fallback: intentar convertir a string y parsear
        try {
            const parsed = parseFloat(String(costoBase));
            return '$' + (isNaN(parsed) ? 0 : parsed);
        } catch (e) {
            console.warn('No se pudo parsear costoBase:', costoBase);
            return '$0';
        }
    }

    // Función para limpiar la selección de vuelos y detalles
    function limpiarSeleccionVuelos() {
        // Limpiar variables de selección
        rutaSeleccionada = null;
        vueloSeleccionado = null;
        
        // Limpiar contenedor de vuelos
        if (listaVuelos) {
            listaVuelos.innerHTML = '<div class="vuelos-placeholder"><i class="fas fa-route"></i><p>Selecciona una ruta para ver sus vuelos disponibles</p></div>';
        }
        
        // Limpiar cualquier detalle de vuelo que pueda estar visible
        const detalleVuelo = document.getElementById("detalle-vuelo");
        if (detalleVuelo) {
            detalleVuelo.style.display = "none";
        }
        
        // Limpiar mensaje de vuelo
        if (mensajeVuelo) {
            mensajeVuelo.style.display = "none";
            mensajeVuelo.textContent = "";
        }
        
        console.log("Selección de vuelos limpiada");
    }

    // Función para seleccionar una ruta (con funcionalidad de deselección)
    function seleccionarRuta(ruta) {
        const card = event.currentTarget;
        
        // Si esta ruta ya está seleccionada, deseleccionarla
        if (rutaSeleccionada && rutaSeleccionada.nombre === ruta.nombre) {
            // Deseleccionar ruta actual
            card.classList.remove('seleccionada');
            limpiarSeleccionVuelos();
            console.log('Ruta deseleccionada:', ruta.nombre);
        } else {
            // Seleccionar nueva ruta
            // Remover selección anterior
            document.querySelectorAll('.ruta-card').forEach(function(c) {
                c.classList.remove('seleccionada');
            });
            
            // Agregar selección actual
            card.classList.add('seleccionada');
            rutaSeleccionada = ruta;
            
            // Cargar vuelos de la ruta
            cargarVuelosRuta(ruta.nombre);
            console.log('Ruta seleccionada:', ruta.nombre);
        }
    }

    // Función para cargar vuelos de una ruta
    async function cargarVuelosRuta(nombreRuta) {
        try {
            const response = await fetch('/VolandoUy-WebApp/api/rutas/' + encodeURIComponent(nombreRuta) + '/vuelos');
            const vuelos = await response.json();
            
            mostrarVuelos(vuelos);
        } catch (error) {
            console.error('Error al cargar vuelos:', error);
            mostrarErrorVuelos('Error al cargar los vuelos de esta ruta');
        }
    }

    // Función para mostrar vuelos
    function mostrarVuelos(vuelos) {
        listaVuelos.innerHTML = '';
        
        if (vuelos.length === 0) {
            listaVuelos.innerHTML = '<div class="vuelos-placeholder"><i class="fas fa-plane"></i><p>No hay vuelos disponibles para esta ruta</p></div>';
            vueloSeleccionado = null; // Limpiar selección de vuelo si no hay vuelos
            // Limpiar mensaje de vuelo si existe
            if (mensajeVuelo) {
                mensajeVuelo.style.display = "none";
                mensajeVuelo.textContent = "";
            }
            return;
        }

        vuelos.forEach(function(vuelo) {
            const card = document.createElement('div');
            card.className = 'vuelo-card';
            card.onclick = function() {
                seleccionarVuelo(vuelo);
            };

            const imagenHtml = vuelo.foto ? 
                '<img src="' + vuelo.foto + '" alt="' + vuelo.nombre + '">' : 
                '<div class="no-image"><i class="fas fa-plane"></i></div>';

            card.innerHTML = imagenHtml +
                '<p><strong>' + vuelo.nombre + '</strong></p>' +
                '<p>Fecha: ' + (vuelo.fechaVuelo || 'N/A') + '</p>' +
                '<p>Hora: ' + (vuelo.horaVuelo || 'N/A') + '</p>' +
                '<p>Duración: ' + (vuelo.duracion || 'N/A') + '</p>' +
                '<p>Asientos: ' + (vuelo.asientosMaxTurista || 0) + ' Turista / ' + (vuelo.asientosMaxEjecutivo || 0) + ' Ejecutivo</p>';

            listaVuelos.appendChild(card);
        });
    }

    // Función para seleccionar un vuelo y mostrar detalles
    function seleccionarVuelo(vuelo) {
        const card = event.currentTarget;
        
        // Si este vuelo ya está seleccionado, deseleccionarlo
        if (vueloSeleccionado && vueloSeleccionado.nombre === vuelo.nombre) {
            // Deseleccionar vuelo actual
            card.classList.remove('seleccionado');
            vueloSeleccionado = null;
            
            // Ocultar panel de detalles
            const detalleVuelo = document.getElementById('detalle-vuelo');
            if (detalleVuelo) {
                detalleVuelo.style.display = 'none';
            }
            
            console.log('Vuelo deseleccionado:', vuelo.nombre);
        } else {
            // Seleccionar nuevo vuelo
            // Remover selección anterior
            document.querySelectorAll('.vuelo-card').forEach(function(c) {
                c.classList.remove('seleccionado');
            });
            
            // Agregar selección actual
            card.classList.add('seleccionado');
            vueloSeleccionado = vuelo;
            
            // Mostrar detalles del vuelo
            mostrarDetalleVuelo(vuelo);
            
            console.log('Vuelo seleccionado:', vuelo.nombre);
        }
    }

    // Función para mostrar detalles del vuelo
    function mostrarDetalleVuelo(vuelo) {
        // Eliminar panel anterior si existe
        const panelAnterior = document.getElementById('detalle-vuelo-' + vuelo.nombre);
        if (panelAnterior) {
            panelAnterior.remove();
        }
        
        // Crear panel de detalles
        const detalleVuelo = document.createElement('div');
        detalleVuelo.id = 'detalle-vuelo-' + vuelo.nombre;
        detalleVuelo.className = 'detalle-vuelo';
        detalleVuelo.style.display = 'block';
        
        // Crear contenido del panel
        detalleVuelo.innerHTML = 
            '<div class="detalle-vuelo-header">' +
                '<h3><i class="fas fa-info-circle"></i> Detalles del Vuelo</h3>' +
            '</div>' +
            '<div class="detalle-vuelo-info">' +
                '<p><strong>Nombre:</strong> ' + vuelo.nombre + '</p>' +
                '<p><strong>Fecha:</strong> ' + (vuelo.fechaVuelo || 'N/A') + '</p>' +
                '<p><strong>Hora:</strong> ' + (vuelo.horaVuelo || 'N/A') + '</p>' +
                '<p><strong>Duración:</strong> ' + (vuelo.duracion || 'N/A') + '</p>' +
                '<p><strong>Asientos Turista:</strong> ' + (vuelo.asientosMaxTurista || 0) + '</p>' +
                '<p><strong>Asientos Ejecutivo:</strong> ' + (vuelo.asientosMaxEjecutivo || 0) + '</p>' +
            '</div>' +
            '<div id="seccion-reservas-' + vuelo.nombre + '" class="seccion-reservas" style="display: none;">' +
                '<h4><i class="fas fa-users"></i> Reservas</h4>' +
                '<div id="lista-reservas-' + vuelo.nombre + '"></div>' +
                '<div id="detalle-reserva-' + vuelo.nombre + '" class="detalle-reserva" style="display: none;">' +
                    '<h4><i class="fas fa-ticket-alt"></i> Detalle de Reserva</h4>' +
                    '<div id="detalle-reserva-info-' + vuelo.nombre + '"></div>' +
                '</div>' +
            '</div>';
        
        // Insertar el panel después del vuelo seleccionado
        const vueloCard = document.querySelector('.vuelo-card.seleccionado');
        if (vueloCard && vueloCard.parentNode) {
            vueloCard.parentNode.insertBefore(detalleVuelo, vueloCard.nextSibling);
        } else {
            // Si no se encuentra el card, insertar al final de la lista de vuelos
            const listaVuelos = document.getElementById('lista-vuelos');
            if (listaVuelos) {
                listaVuelos.appendChild(detalleVuelo);
            }
        }
        
        // Verificar si se pueden mostrar reservas
        verificarYMostrarReservas(vuelo.nombre);
    }
    
    // Función para verificar y mostrar reservas
    function verificarYMostrarReservas(nombreVuelo) {
        const seccionReservas = document.getElementById('seccion-reservas-' + nombreVuelo);
        const listaReservas = document.getElementById('lista-reservas-' + nombreVuelo);
        const detalleReserva = document.getElementById('detalle-reserva-' + nombreVuelo);
        
        if (!seccionReservas || !listaReservas || !detalleReserva) return;
        
        // Ocultar secciones de reservas inicialmente
        seccionReservas.style.display = 'none';
        detalleReserva.style.display = 'none';
        
        // Obtener información del usuario logueado desde el servidor
        fetch('/VolandoUy-WebApp/api/vuelos/reservas/' + encodeURIComponent(nombreVuelo))
            .then(response => response.json())
            .then(reservas => {
                if (reservas.length === 0) {
                    listaReservas.innerHTML = '<p>No hay reservas disponibles para mostrar.</p>';
                    seccionReservas.style.display = 'block';
                    return;
                }
                
                // Mostrar reservas
                mostrarReservas(reservas, nombreVuelo);
                seccionReservas.style.display = 'block';
            })
            .catch(error => {
                console.error('Error al cargar reservas:', error);
                listaReservas.innerHTML = '<p>Error al cargar reservas</p>';
                seccionReservas.style.display = 'block';
            });
    }
    
    // Función para mostrar reservas
    function mostrarReservas(reservas, nombreVuelo) {
        const listaReservas = document.getElementById('lista-reservas-' + nombreVuelo);
        const detalleReserva = document.getElementById('detalle-reserva-' + nombreVuelo);
        
        if (!listaReservas || !detalleReserva) return;
        
        listaReservas.innerHTML = '';
        detalleReserva.style.display = 'none';
        
        reservas.forEach((reserva) => {
            const div = document.createElement('div');
            div.className = 'reserva-card';
            div.innerHTML = '<strong>' + reserva.cliente + '</strong> — Fecha: ' + reserva.fechaReserva + ' • Costo: ' + formatearCostoReserva(reserva.costoReserva);
            div.addEventListener('click', () => mostrarDetalleReserva(reserva, nombreVuelo));
            listaReservas.appendChild(div);
        });
    }
    
    // Función para mostrar detalle de reserva
    function mostrarDetalleReserva(reserva, nombreVuelo) {
        const detalleReserva = document.getElementById('detalle-reserva-' + nombreVuelo);
        const detalleReservaInfo = document.getElementById('detalle-reserva-info-' + nombreVuelo);
        
        if (!detalleReserva || !detalleReservaInfo) return;
        
        detalleReserva.style.display = 'block';
        
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
    
    // Función para formatear el costo de la reserva
    function formatearCostoReserva(costoReserva) {
        if (!costoReserva) return '$0';
        
        try {
            if (typeof costoReserva === 'object' && costoReserva.costoTotal) {
                return '$' + costoReserva.costoTotal.toFixed(2);
            } else if (typeof costoReserva === 'number') {
                return '$' + costoReserva.toFixed(2);
            } else {
                return '$' + parseFloat(costoReserva).toFixed(2);
            }
        } catch (error) {
            console.warn('No se pudo parsear costoReserva:', costoReserva);
            return '$0';
        }
    }

    // Función para mostrar error
    function mostrarError(mensaje) {
        listaRutas.innerHTML = '<div style="text-align: center; padding: 2rem; color: #eaf6fb; grid-column: 1 / -1;"><i class="fas fa-exclamation-triangle" style="font-size: 3rem; margin-bottom: 1rem; color: #dc3545;"></i><p>' + mensaje + '</p></div>';
    }

    // Función para mostrar error en vuelos
    function mostrarErrorVuelos(mensaje) {
        listaVuelos.innerHTML = '<div class="vuelos-placeholder"><i class="fas fa-exclamation-triangle" style="color: #dc3545;"></i><p>' + mensaje + '</p></div>';
    }
}

// Auto-inicializar cuando se carga la página
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM cargado, inicializando consulta de ruta de vuelo...');
    initConsultaRutaVuelo();
});

// Exportar función para uso global
window.initConsultaRutaVuelo = initConsultaRutaVuelo;
