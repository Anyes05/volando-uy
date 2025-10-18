// Script para manejar la compra de paquetes
// Implementa el caso de uso "Compra de Paquete de Rutas de Vuelo"

let paquetesDisponibles = [];
let paqueteSeleccionado = null;
let clienteActual = null;

// Inicializar la página
document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM cargado, inicializando página de compra de paquetes');
    
    console.log('Elementos disponibles:', {
        listaPaquetes: document.getElementById('lista-paquetes-compra'),
        sinPaquetes: document.getElementById('sin-paquetes'),
        clienteNombre: document.getElementById('cliente-nombre'),
        cargando: document.getElementById('cargando-paquetes')
    });
    
    // Inicializar inmediatamente
    initCompraPaquete();
});

// Cargar información del cliente logueado
async function cargarInformacionCliente() {
    try {
        const response = await fetch('/VolandoUy-WebApp/api/usuarios/perfil');
        if (response.ok) {
            const data = await response.json();
            clienteActual = data;
            actualizarInformacionCliente(data);
        } else if (response.status === 401) {
            // Usuario no autenticado, redirigir al login usando el controlador de vista
            if (typeof controladorDeVista !== 'undefined' && controladorDeVista.cargar) {
                // Asegurar que se carguen los estilos CSS del login
                const loginCSS = document.querySelector('link[href*="login.css"]');
                if (!loginCSS) {
                    const link = document.createElement('link');
                    link.rel = 'stylesheet';
                    link.href = 'css/login.css';
                    document.head.appendChild(link);
                    console.log('Estilos de login cargados dinámicamente desde compra paquete');
                }
                controladorDeVista.cargar('inicioSesion.jsp');
            } else {
                window.location.href = '/VolandoUy-WebApp/vista/inicioSesion';
            }
        } else {
            console.error('Error al cargar información del cliente:', response.statusText);
        }
    } catch (error) {
        console.error('Error al cargar información del cliente:', error);
    }
}

// Actualizar la información del cliente en la interfaz
function actualizarInformacionCliente(cliente) {
    const nombreElement = document.getElementById('cliente-nombre');
    const emailElement = document.getElementById('cliente-email');
    const fechaElement = document.getElementById('fecha-compra');
    
    if (nombreElement) nombreElement.textContent = cliente.nombre || 'Usuario';
    if (emailElement) emailElement.textContent = cliente.correo || '';
    if (fechaElement) fechaElement.textContent = new Date().toLocaleDateString('es-UY');
}

// Cargar paquetes disponibles desde el servidor
async function cargarPaquetesDisponibles() {
    try {
        console.log('Iniciando carga de paquetes disponibles...');
        const response = await fetch('/VolandoUy-WebApp/api/paquetes');
        console.log('Respuesta recibida:', response.status, response.statusText);
        
        if (response.ok) {
            const data = await response.json();
            console.log('Datos recibidos:', data);
            paquetesDisponibles = data.paquetes || [];
            
            // Verificar cuáles paquetes ya fueron comprados
            await verificarPaquetesComprados();
            
            console.log('Paquetes disponibles:', paquetesDisponibles);
            mostrarPaquetesDisponibles();
        } else {
            console.error('Error al cargar paquetes:', response.statusText);
            mostrarMensajeSinPaquetes();
        }
    } catch (error) {
        console.error('Error al cargar paquetes:', error);
        mostrarMensajeSinPaquetes();
    }
}

// Verificar qué paquetes ya fueron comprados por el cliente
async function verificarPaquetesComprados() {
    if (!clienteActual || paquetesDisponibles.length === 0) {
        return;
    }
    
    try {
        // Verificar cada paquete individualmente
        for (let paquete of paquetesDisponibles) {
            try {
                const response = await fetch(`/VolandoUy-WebApp/api/compra-paquetes?paquete=${encodeURIComponent(paquete.nombre)}`);
                if (response.ok) {
                    const data = await response.json();
                    paquete.yaComprado = data.yaCompro || false;
                }
            } catch (error) {
                console.warn(`Error verificando compra del paquete ${paquete.nombre}:`, error);
                paquete.yaComprado = false;
            }
        }
    } catch (error) {
        console.error('Error verificando paquetes comprados:', error);
    }
}

// Mostrar paquetes disponibles en la interfaz
function mostrarPaquetesDisponibles() {
    console.log('Mostrando paquetes disponibles...');
    const contenedor = document.getElementById('lista-paquetes-compra');
    const sinPaquetes = document.getElementById('sin-paquetes');
    const cargando = document.getElementById('cargando-paquetes');
    
    console.log('Contenedor encontrado:', contenedor);
    console.log('Sin paquetes encontrado:', sinPaquetes);
    console.log('Cargando encontrado:', cargando);
    console.log('Cantidad de paquetes:', paquetesDisponibles.length);
    
    // Ocultar indicador de carga inmediatamente
    if (cargando) {
        cargando.style.display = 'none';
    }
    
    if (!contenedor) {
        console.error('No se encontró el contenedor lista-paquetes-compra');
        return;
    }
    
    if (paquetesDisponibles.length === 0) {
        console.log('No hay paquetes disponibles, mostrando mensaje');
        mostrarMensajeSinPaquetes();
        return;
    }
    
    // Limpiar contenido anterior
    contenedor.innerHTML = '';
    
    // Crear cards sin animaciones complejas para evitar parpadeo
    paquetesDisponibles.forEach((paquete, index) => {
        console.log(`Creando card para paquete ${index}:`, paquete.nombre);
        const paqueteCard = crearCardPaquete(paquete);
        contenedor.appendChild(paqueteCard);
    });
    
    if (sinPaquetes) {
        sinPaquetes.style.display = 'none';
    }
    
    // Mostrar el contenedor inmediatamente
    contenedor.style.display = 'grid';
    
    console.log('Paquetes mostrados exitosamente');
}

// Función auxiliar para convertir imagen a base64
function convertirImagenABase64(foto) {
    if (!foto || foto.length === 0) {
        return '/img/logoAvionSolo.png';
    }
    
    try {
        // Verificar si es un array de bytes válido
        if (Array.isArray(foto) || foto instanceof Uint8Array) {
            // Convertir array de bytes a base64
            const base64 = btoa(String.fromCharCode(...foto));
            return `data:image/jpeg;base64,${base64}`;
        } else if (typeof foto === 'string') {
            // Si ya es una cadena base64
            if (foto.startsWith('data:')) {
                return foto;
            } else if (foto.startsWith('/') || foto.startsWith('http')) {
                // Si es una URL o ruta
                return foto;
            } else {
                // Asumir que es base64 sin prefijo
                return `data:image/jpeg;base64,${foto}`;
            }
        } else {
            console.warn('Formato de imagen no válido:', typeof foto);
            return '/img/logoAvionSolo.png';
        }
    } catch (error) {
        console.warn('Error convirtiendo imagen:', error);
        return '/img/logoAvionSolo.png';
    }
}

// Crear card para un paquete
function crearCardPaquete(paquete) {
    const card = document.createElement('div');
    card.className = 'paquete-card';
    card.onclick = () => seleccionarPaqueteParaCompra(paquete);
    
    const imagenSrc = convertirImagenABase64(paquete.foto);
    
    // Verificar si el paquete ya fue comprado
    const yaComprado = paquete.yaComprado || false;
    const cardClass = yaComprado ? 'paquete-card comprado' : 'paquete-card';
    const botonComprar = yaComprado 
        ? '<button class="btn-comprado" disabled><i class="fas fa-check"></i> Ya Comprado</button>'
        : '<button class="btn-comprar" onclick="event.stopPropagation(); seleccionarPaqueteParaCompra(' + JSON.stringify(paquete).replace(/"/g, '&quot;') + ')"><i class="fas fa-shopping-cart"></i> Comprar</button>';
    
    card.className = cardClass;
    
    card.innerHTML = `
        <div class="paquete-imagen">
            <img src="${imagenSrc}" alt="${paquete.nombre}" onerror="this.src='/VolandoUy-WebApp/img/logoAvionSolo.png'" loading="lazy">
            ${yaComprado ? '<div class="comprado-badge"><i class="fas fa-check-circle"></i> Comprado</div>' : ''}
        </div>
        <div class="paquete-info">
            <h3>${paquete.nombre}</h3>
            <p class="paquete-descripcion">${paquete.descripcion || 'Sin descripción'}</p>
            <div class="paquete-details">
                <span class="dias-validos"><i class="fas fa-calendar"></i> ${paquete.diasValidos} días</span>
                <span class="descuento"><i class="fas fa-percentage"></i> ${paquete.descuento}% desc.</span>
                <span class="costo"><i class="fas fa-dollar-sign"></i> $${paquete.costoTotal}</span>
            </div>
        </div>
        <div class="paquete-acciones">
            <button class="btn-ver-detalle" onclick="event.stopPropagation(); verDetallePaquete('${paquete.nombre}')">
                <i class="fas fa-eye"></i> Ver Detalle
            </button>
            ${botonComprar}
        </div>
    `;
    
    return card;
}

// Mostrar mensaje cuando no hay paquetes disponibles
function mostrarMensajeSinPaquetes() {
    const contenedor = document.getElementById('lista-paquetes-compra');
    const sinPaquetes = document.getElementById('sin-paquetes');
    const cargando = document.getElementById('cargando-paquetes');
    
    // Ocultar indicador de carga
    if (cargando) {
        cargando.style.display = 'none';
    }
    
    if (contenedor) {
        contenedor.style.display = 'none';
    }
    if (sinPaquetes) {
        sinPaquetes.style.display = 'block';
    }
}

// Seleccionar paquete para compra
async function seleccionarPaqueteParaCompra(paquete) {
    console.log('Seleccionando paquete para compra:', paquete.nombre);
    
    try {
        // Verificar si el cliente ya compró este paquete
        const response = await fetch(`/VolandoUy-WebApp/api/compra-paquetes?paquete=${encodeURIComponent(paquete.nombre)}`);
        
        if (response.ok) {
            const data = await response.json();
            
            if (data.yaCompro) {
                mostrarMensajeYaComprado();
                return;
            }
        }
        
        // Si no lo compró, proceder con la selección
        paqueteSeleccionado = paquete;
        mostrarDetalleCompra(paquete);
        
    } catch (error) {
        console.error('Error al verificar compra previa:', error);
        // Continuar con la selección en caso de error
        paqueteSeleccionado = paquete;
        mostrarDetalleCompra(paquete);
    }
}

// Mostrar mensaje de que ya compró el paquete
function mostrarMensajeYaComprado() {
    const yaComprado = document.getElementById('ya-comprado');
    if (yaComprado) {
        yaComprado.style.display = 'block';
    }
    
    // Mostrar también el detalle para que pueda ver la información
    if (paqueteSeleccionado) {
        mostrarDetalleCompra(paqueteSeleccionado);
    }
}

// Mostrar detalle de compra
function mostrarDetalleCompra(paquete) {
    const detalleSection = document.querySelector('.detalle-compra');
    if (!detalleSection) return;
    
    // Ocultar lista de paquetes
    const listaPaquetes = document.getElementById('lista-paquetes-compra');
    if (listaPaquetes) listaPaquetes.style.display = 'none';
    
    // Mostrar detalle
    detalleSection.style.display = 'block';
    
    // Llenar información del paquete
    actualizarDetallePaquete(paquete);
    
    // Ocultar mensaje de ya comprado si existe
    const yaComprado = document.getElementById('ya-comprado');
    if (yaComprado) yaComprado.style.display = 'none';
}

// Actualizar detalle del paquete en la interfaz
function actualizarDetallePaquete(paquete) {
    const elementos = {
        'paquete-compra-imagen': convertirImagenABase64(paquete.foto),
        'paquete-compra-nombre': paquete.nombre,
        'paquete-compra-descripcion': paquete.descripcion || 'Sin descripción',
        'paquete-compra-dias': paquete.diasValidos,
        'paquete-compra-descuento': paquete.descuento,
        'paquete-compra-fecha': paquete.fechaAlta,
        'paquete-compra-costo': paquete.costoTotal
    };
    
    // Actualizar elementos básicos
    Object.entries(elementos).forEach(([id, valor]) => {
        const elemento = document.getElementById(id);
        if (elemento) {
            if (id === 'paquete-compra-imagen') {
                elemento.src = valor;
                elemento.alt = paquete.nombre;
                elemento.onerror = function() {
                    this.src = '/VolandoUy-WebApp/img/logoAvionSolo.png';
                };
            } else {
                elemento.textContent = valor;
            }
        }
    });
    
    // Actualizar resumen de compra
    const resumenElementos = {
        'resumen-nombre': paquete.nombre,
        'resumen-precio-original': `$${paquete.costoTotal}`,
        'resumen-descuento': `${paquete.descuento}%`,
        'resumen-total': `$${paquete.costoTotal}`
    };
    
    Object.entries(resumenElementos).forEach(([id, valor]) => {
        const elemento = document.getElementById(id);
        if (elemento) elemento.textContent = valor;
    });
    
    // Cargar rutas del paquete
    cargarRutasPaquete(paquete.nombre);
}

// Cargar rutas del paquete
async function cargarRutasPaquete(nombrePaquete) {
    try {
        const response = await fetch(`/VolandoUy-WebApp/api/paquetes/${encodeURIComponent(nombrePaquete)}`);
        if (response.ok) {
            const data = await response.json();
            mostrarRutasPaquete(data.rutas || []);
        } else {
            console.error('Error al cargar rutas del paquete:', response.statusText);
        }
    } catch (error) {
        console.error('Error al cargar rutas del paquete:', error);
    }
}

// Mostrar rutas del paquete
function mostrarRutasPaquete(rutas) {
    const contenedor = document.getElementById('paquete-compra-cantidades');
    if (!contenedor) return;
    
    contenedor.innerHTML = '';
    
    if (rutas.length === 0) {
        contenedor.innerHTML = '<p>No hay rutas incluidas en este paquete.</p>';
        return;
    }
    
    rutas.forEach(ruta => {
        const rutaCard = document.createElement('div');
        rutaCard.className = 'ruta-card';
        rutaCard.innerHTML = `
            <div class="ruta-info">
                <h4>${ruta.nombre}</h4>
                <p>${ruta.ciudadOrigen} → ${ruta.ciudadDestino}</p>
                <p class="ruta-descripcion">${ruta.descripcion || ''}</p>
            </div>
            <div class="ruta-details">
                <span class="cantidad">Cantidad: ${ruta.cantidad}</span>
                <span class="tipo-asiento">Tipo: ${ruta.tipoAsiento}</span>
                <span class="costo-turista">Turista: $${ruta.costoBaseTurista}</span>
                <span class="costo-ejecutivo">Ejecutivo: $${ruta.costoBaseEjecutivo}</span>
            </div>
        `;
        contenedor.appendChild(rutaCard);
    });
}

// Confirmar compra
async function confirmarCompra() {
    if (!paqueteSeleccionado) {
        alert('No hay paquete seleccionado');
        return;
    }
    
    if (!clienteActual) {
        alert('No hay cliente autenticado');
        return;
    }
    
    const btnComprar = document.getElementById('btn-confirmar-compra');
    if (btnComprar) {
        btnComprar.disabled = true;
        btnComprar.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Procesando...';
    }
    
    try {
        const requestData = {
            nombrePaquete: paqueteSeleccionado.nombre,
            costo: paqueteSeleccionado.costoTotal
        };
        
        const response = await fetch('/VolandoUy-WebApp/api/compra-paquetes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        });
        
        if (response.ok) {
            const data = await response.json();
            mostrarModalConfirmacion(data.compra);
        } else if (response.status === 409) {
            // Ya compró el paquete
            const data = await response.json();
            mostrarMensajeYaComprado();
            alert('Ya has comprado este paquete anteriormente. Puedes seleccionar otro paquete.');
        } else {
            const errorData = await response.json();
            alert('Error al realizar la compra: ' + (errorData.error || 'Error desconocido'));
        }
        
    } catch (error) {
        console.error('Error al confirmar compra:', error);
        alert('Error al realizar la compra. Por favor, intenta nuevamente.');
    } finally {
        if (btnComprar) {
            btnComprar.disabled = false;
            btnComprar.innerHTML = '<i class="fas fa-shopping-cart"></i> Confirmar Compra';
        }
    }
}

// Mostrar modal de confirmación
function mostrarModalConfirmacion(compra) {
    const modal = document.getElementById('modal-confirmacion');
    if (!modal) return;
    
    // Llenar información del modal
    const elementos = {
        'modal-nombre-paquete': compra.paquete,
        'modal-fecha-compra': compra.fechaCompra,
        'modal-vencimiento': compra.vencimiento,
        'modal-total-pagado': compra.costo
    };
    
    Object.entries(elementos).forEach(([id, valor]) => {
        const elemento = document.getElementById(id);
        if (elemento) elemento.textContent = valor;
    });
    
    modal.style.display = 'block';
}

// Cerrar modal
function cerrarModal() {
    const modal = document.getElementById('modal-confirmacion');
    if (modal) modal.style.display = 'none';
    
    // Marcar el paquete como comprado en lugar de eliminarlo
    if (paqueteSeleccionado) {
        const paqueteEnLista = paquetesDisponibles.find(p => p.nombre === paqueteSeleccionado.nombre);
        if (paqueteEnLista) {
            paqueteEnLista.yaComprado = true;
            console.log('Paquete marcado como comprado:', paqueteSeleccionado.nombre);
        }
    }
    
    // Volver a la lista de paquetes
    volverAListaCompra();
}

// Volver a la lista de compra
function volverAListaCompra() {
    const detalleSection = document.querySelector('.detalle-compra');
    const listaPaquetes = document.getElementById('lista-paquetes-compra');
    
    if (detalleSection) detalleSection.style.display = 'none';
    if (listaPaquetes) listaPaquetes.style.display = 'grid';
    
    paqueteSeleccionado = null;
    
    // Ocultar mensaje de ya comprado
    const yaComprado = document.getElementById('ya-comprado');
    if (yaComprado) yaComprado.style.display = 'none';
    
    // Refrescar la lista de paquetes para mostrar los cambios
    mostrarPaquetesDisponibles();
}

// Ver detalle del paquete (función auxiliar)
async function verDetallePaquete(nombrePaquete) {
    try {
        const response = await fetch(`/VolandoUy-WebApp/api/paquetes/${encodeURIComponent(nombrePaquete)}`);
        if (response.ok) {
            const data = await response.json();
            mostrarDetalleCompra(data.paquete);
        } else {
            console.error('Error al cargar detalle del paquete:', response.statusText);
        }
    } catch (error) {
        console.error('Error al cargar detalle del paquete:', error);
    }
}

// Cerrar modal al hacer clic fuera de él
window.onclick = function(event) {
    const modal = document.getElementById('modal-confirmacion');
    if (event.target === modal) {
        cerrarModal();
    }
}

// Función de inicialización
function initCompraPaquete() {
    // Evitar inicialización múltiple
    if (window.compraPaqueteInicializado) {
        console.log('CompraPaquete ya inicializado, omitiendo...');
        return;
    }
    
    console.log('Inicializando compra de paquetes...');
    window.compraPaqueteInicializado = true;
    
    // Mostrar indicador de carga inmediatamente
    const cargando = document.getElementById('cargando-paquetes');
    const contenedor = document.getElementById('lista-paquetes-compra');
    const sinPaquetes = document.getElementById('sin-paquetes');
    
    if (cargando) cargando.style.display = 'flex';
    if (contenedor) contenedor.style.display = 'none';
    if (sinPaquetes) sinPaquetes.style.display = 'none';
    
    // Cargar datos inmediatamente sin delay
    cargarInformacionCliente();
    cargarPaquetesDisponibles();
}

// Exportar función para uso global
window.initCompraPaquete = initCompraPaquete;
window.volverAListaCompra = volverAListaCompra;
window.confirmarCompra = confirmarCompra;
window.cerrarModal = cerrarModal;
