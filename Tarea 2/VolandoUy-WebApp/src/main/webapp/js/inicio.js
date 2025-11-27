// JavaScript simplificado para la página de inicio - Buscador funcional
// Helper para construir URLs con contextPath
function apiUrl(path) {
  const contextPath = window.APP_CONTEXT_PATH || '';
  return contextPath + (path.startsWith('/') ? path : '/' + path);
}

let ciudadesData = [];

// Función de inicialización
function initInicio() {
    console.log('Inicializando página de inicio...');
    
    // Obtener elementos del buscador
    const origenInput = document.getElementById('origen-input');
    const destinoInput = document.getElementById('destino-input');
    const fechaVueloInput = document.getElementById('fecha-vuelo');
    const btnBuscar = document.getElementById('btn-buscar');
    
    // Verificar que todos los elementos existen
    if (!origenInput || !destinoInput || !fechaVueloInput || !btnBuscar) {
        console.error('Elementos del buscador no encontrados');
        return;
    }
    
    // Configurar fecha por defecto (mañana)
    configurarFechaPorDefecto(fechaVueloInput);
    
    // Cargar ciudades disponibles
    cargarCiudades();
    
    // Event listeners
    configurarAutocompletado(origenInput, destinoInput);
    configurarBotonBuscar(btnBuscar, origenInput, destinoInput, fechaVueloInput);
    
    console.log('Página de inicio inicializada correctamente');
}

// Configurar fecha por defecto
function configurarFechaPorDefecto(fechaInput) {
    const mañana = new Date();
    mañana.setDate(mañana.getDate() + 1);
    
    const formatoFecha = (fecha) => {
        return fecha.getFullYear() + '-' + 
               String(fecha.getMonth() + 1).padStart(2, '0') + '-' + 
               String(fecha.getDate()).padStart(2, '0');
    };
    
    fechaInput.value = formatoFecha(mañana);
}

// Cargar ciudades disponibles desde el servidor
function cargarCiudades() {
    // Usar la API existente para obtener ciudades de las rutas
    fetch(apiUrl('/api/rutas/aerolineas'))
        .then(function (r) { return r.json(); })
        .then(function (aerolineas) {
            let todasLasCiudades = new Set();
            
            // Obtener ciudades de cada aerolínea
            let promesas = [];
            aerolineas.forEach(function (aerolinea) {
                promesas.push(
                    fetch(apiUrl(`/api/rutas?aerolinea=${encodeURIComponent(aerolinea.nickname)}`))
                        .then(function (r) { return r.json(); })
                        .then(function (rutas) {
                            rutas.forEach(function (ruta) {
                                if (ruta.ciudadOrigen) todasLasCiudades.add(ruta.ciudadOrigen);
                                if (ruta.ciudadDestino) todasLasCiudades.add(ruta.ciudadDestino);
                            });
                        })
                        .catch(function (error) {
                            console.error('Error al cargar rutas de aerolínea:', aerolinea.nickname, error);
                        })
                );
            });
            
            Promise.all(promesas).then(function () {
                ciudadesData = Array.from(todasLasCiudades).sort();
                console.log('Ciudades cargadas:', ciudadesData.length);
            });
        })
        .catch(function (error) {
            console.error('Error al cargar ciudades:', error);
        });
}

// Configurar autocompletado para origen y destino
function configurarAutocompletado(origenInput, destinoInput) {
    // Configurar autocompletado para origen
    configurarAutocompletadoInput(origenInput, ciudadesData);
    
    // Configurar autocompletado para destino
    configurarAutocompletadoInput(destinoInput, ciudadesData);
}

// Configurar autocompletado para un input específico
function configurarAutocompletadoInput(input, opciones) {
    let dropdown = null;
    
    input.addEventListener('input', function() {
        const valor = this.value.toLowerCase();
        const coincidencias = opciones.filter(ciudad => 
            ciudad.toLowerCase().includes(valor)
        );
        
        // Remover dropdown anterior si existe
        if (dropdown) {
            dropdown.remove();
        }
        
        if (coincidencias.length > 0 && valor.length > 0) {
            dropdown = crearDropdown(coincidencias, this);
            this.parentElement.appendChild(dropdown);
        }
    });
    
    // Ocultar dropdown al hacer clic fuera
    document.addEventListener('click', function(e) {
        if (dropdown && !dropdown.contains(e.target) && e.target !== input) {
            dropdown.remove();
            dropdown = null;
        }
    });
}

// Crear dropdown de autocompletado
function crearDropdown(opciones, input) {
    const dropdown = document.createElement('div');
    dropdown.className = 'autocomplete-dropdown';
    dropdown.style.cssText = `
        position: absolute;
        top: 100%;
        left: 0;
        right: 0;
        background: white;
        border: 1px solid #ddd;
        border-top: none;
        max-height: 200px;
        overflow-y: auto;
        z-index: 1000;
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        border-radius: 0 0 10px 10px;
    `;
    
    opciones.forEach(opcion => {
        const item = document.createElement('div');
        item.className = 'autocomplete-item';
        item.textContent = opcion;
        item.style.cssText = `
            padding: 10px;
            cursor: pointer;
            border-bottom: 1px solid #eee;
            color: #333;
            transition: background-color 0.2s ease;
        `;
        
        item.addEventListener('mouseenter', function() {
            this.style.backgroundColor = '#f5f5f5';
        });
        
        item.addEventListener('mouseleave', function() {
            this.style.backgroundColor = 'white';
        });
        
        item.addEventListener('click', function() {
            input.value = opcion;
            dropdown.remove();
        });
        
        dropdown.appendChild(item);
    });
    
    return dropdown;
}

// Configurar botón de búsqueda
function configurarBotonBuscar(btnBuscar, origenInput, destinoInput, fechaVueloInput) {
    btnBuscar.addEventListener('click', function(e) {
        e.preventDefault();
        
        const origen = origenInput.value.trim();
        const destino = destinoInput.value.trim();
        const fechaVuelo = fechaVueloInput.value;
        
        // Validar campos obligatorios
        if (!origen || !destino || !fechaVuelo) {
            mostrarMensaje('Por favor complete todos los campos', 'error');
            return;
        }
        
        // Validar que origen y destino sean diferentes
        if (origen.toLowerCase() === destino.toLowerCase()) {
            mostrarMensaje('El origen y destino deben ser diferentes', 'error');
            return;
        }
        
        // Redirigir a consulta de vuelos con parámetros
        buscarVuelos(origen, destino, fechaVuelo);
    });
}

// Buscar vuelos usando la lógica del servidor
function buscarVuelos(origen, destino, fechaVuelo) {
    try {
        mostrarMensaje('Buscando vuelos...', 'info');
        
        // Guardar parámetros de búsqueda en sessionStorage para que consultaVuelo los use
        sessionStorage.setItem('busquedaOrigen', origen);
        sessionStorage.setItem('busquedaDestino', destino);
        sessionStorage.setItem('busquedaFecha', fechaVuelo);
        
        // Redirigir a la página de consulta de vuelos
        window.location.href = apiUrl('/consultaVuelo.jsp');
        
    } catch (error) {
        console.error('Error en la búsqueda:', error);
        mostrarMensaje('Error al realizar la búsqueda. Intente nuevamente.', 'error');
    }
}

// Mostrar mensaje al usuario
function mostrarMensaje(mensaje, tipo = 'info') {
    // Crear elemento de mensaje
    const mensajeEl = document.createElement('div');
    mensajeEl.className = `mensaje-busqueda mensaje-${tipo}`;
    mensajeEl.textContent = mensaje;
    
    // Estilos según el tipo
    const estilos = {
        info: 'background: #d1ecf1; color: #0c5460; border: 1px solid #bee5eb;',
        success: 'background: #d4edda; color: #155724; border: 1px solid #c3e6cb;',
        warning: 'background: #fff3cd; color: #856404; border: 1px solid #ffeaa7;',
        error: 'background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb;'
    };
    
    mensajeEl.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 20px;
        border-radius: 5px;
        z-index: 3000;
        max-width: 400px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        ${estilos[tipo]}
    `;
    
    document.body.appendChild(mensajeEl);
    
    // Remover mensaje después de 3 segundos
    setTimeout(() => {
        if (mensajeEl.parentNode) {
            mensajeEl.remove();
        }
    }, 3000);
}

// Inicializar cuando el DOM esté listo
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initInicio);
} else {
    initInicio();
}