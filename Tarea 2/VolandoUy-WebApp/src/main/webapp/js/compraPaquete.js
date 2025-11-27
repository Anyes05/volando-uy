// Script para manejar la compra de paquetes
// Implementa el caso de uso "Compra de Paquete de Rutas de Vuelo"

// Helper para construir URLs con contextPath
function apiUrl(path) {
  const contextPath = window.APP_CONTEXT_PATH || '';
  return contextPath + (path.startsWith('/') ? path : '/' + path);
}

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
function cargarInformacionCliente() {
    fetch(apiUrl('/api/usuarios/perfil'))
        .then(function (r) { return r.json(); })
        .then(function (data) {
            clienteActual = data;
            actualizarInformacionCliente(data);
        })
        .catch(function (error) {
            console.error('Error al cargar información del cliente:', error);
        });
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
function cargarPaquetesDisponibles() {
    console.log('Iniciando carga de paquetes disponibles...');
    fetch(apiUrl('/api/paquetes'))
        .then(function (r) { return r.json(); })
        .then(function (data) {
            console.log('Datos recibidos:', data);
            paquetesDisponibles = data.paquetes || [];
            
            // Verificar cuáles paquetes ya fueron comprados
            verificarPaquetesComprados();
            
            console.log('Paquetes disponibles:', paquetesDisponibles);
            mostrarPaquetesDisponibles();
        })
        .catch(function (error) {
            console.error('Error al cargar paquetes:', error);
            mostrarMensajeSinPaquetes();
        });
}

// Verificar qué paquetes ya fueron comprados por el cliente
function verificarPaquetesComprados() {
    if (!clienteActual || paquetesDisponibles.length === 0) {
        return;
    }
    
    // Verificar cada paquete individualmente
    paquetesDisponibles.forEach(function (paquete) {
        fetch(apiUrl(`/api/compra-paquetes?paquete=${encodeURIComponent(paquete.nombre)}`))
            .then(function (r) { return r.json(); })
            .then(function (data) {
                paquete.yaComprado = data.yaCompro || false;
            })
            .catch(function (error) {
                console.warn(`Error verificando compra del paquete ${paquete.nombre}:`, error);
                paquete.yaComprado = false;
            });
    });
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
    
    // Calcular precios para mostrar correctamente
    const precioConDescuento = paquete.costoTotal;
    const descuentoPorcentaje = paquete.descuento || 0;
    
    // Regla de tres: Si precioConDescuento = (100-descuento)%, entonces precioOriginal = 100%
    const precioOriginal = descuentoPorcentaje > 0 
        ? (precioConDescuento * 100) / (100 - descuentoPorcentaje)
        : precioConDescuento;
    
    // Crear el HTML del precio dependiendo si hay descuento o no
    const precioHTML = descuentoPorcentaje > 0 
        ? `<span class="precio-original">$${Math.round(precioOriginal)}</span>
           <span class="costo">$${Math.round(precioConDescuento)}</span>`
        : `<span class="costo">$${Math.round(precioConDescuento)}</span>`;
    
    const logoUrl = apiUrl('/img/logoAvionSolo.png');
    card.innerHTML = `
        <div class="paquete-imagen">
            <img src="${imagenSrc}" alt="${paquete.nombre}" onerror="this.src='${logoUrl}'" loading="lazy">
            ${yaComprado ? '<div class="comprado-badge"><i class="fas fa-check-circle"></i> Comprado</div>' : ''}
        </div>
        <div class="paquete-info">
            <h3>${paquete.nombre}</h3>
            <p class="paquete-descripcion">${paquete.descripcion || 'Sin descripción'}</p>
            <div class="paquete-details">
                <span class="dias-validos"><i class="fas fa-calendar"></i> ${paquete.diasValidos} días</span>
                <span class="descuento"><i class="fas fa-percentage"></i> ${paquete.descuento}% desc.</span>
                <div class="precio-container">${precioHTML}</div>
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
function seleccionarPaqueteParaCompra(paquete) {
    console.log('Seleccionando paquete para compra:', paquete.nombre);
    
    // Verificar si el cliente ya compró este paquete
    fetch(apiUrl(`/api/compra-paquetes?paquete=${encodeURIComponent(paquete.nombre)}`))
        .then(function (r) { return r.json(); })
        .then(function (data) {
            if (data.yaCompro) {
                mostrarMensajeYaComprado();
                return;
            }
            
            // Si no lo compró, proceder con la selección
            paqueteSeleccionado = paquete;
            mostrarDetalleCompra(paquete);
        })
        .catch(function (error) {
            console.error('Error al verificar compra previa:', error);
            // Continuar con la selección en caso de error
            paqueteSeleccionado = paquete;
            mostrarDetalleCompra(paquete);
        });
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
    console.log('=== EJECUTANDO actualizarDetallePaquete ===');
    console.log('Paquete:', paquete.nombre);
    console.log('Costo Total (con descuento):', paquete.costoTotal);
    console.log('Descuento %:', paquete.descuento);
    
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
                    this.src = apiUrl('/img/logoAvionSolo.png');
                };
            } else {
                elemento.textContent = valor;
            }
        }
    });
    
    // Actualizar resumen de compra
    // El costoTotal que viene del backend ya tiene el descuento aplicado
    // Usamos regla de tres para calcular el precio original
    const precioConDescuento = paquete.costoTotal;
    const descuentoPorcentaje = paquete.descuento || 0;
    
    // Regla de tres: Si precioConDescuento = (100-descuento)%, entonces precioOriginal = 100%
    // precioOriginal = (precioConDescuento * 100) / (100 - descuento)
    const precioOriginal = descuentoPorcentaje > 0 
        ? (precioConDescuento * 100) / (100 - descuentoPorcentaje)
        : precioConDescuento;
    
    const ahorroMonetario = precioOriginal - precioConDescuento;
    
    console.log('Precio con descuento:', precioConDescuento);
    console.log('Descuento %:', descuentoPorcentaje);
    console.log('Precio original calculado (regla de 3):', precioOriginal);
    console.log('Ahorro monetario:', ahorroMonetario);
    
    const resumenElementos = {
        'resumen-nombre': paquete.nombre,
        'resumen-precio-original': `$${Math.round(precioOriginal)}`,
        'resumen-descuento': descuentoPorcentaje > 0 ? `-$${Math.round(ahorroMonetario)} (${descuentoPorcentaje}%)` : `${descuentoPorcentaje}%`,
        'resumen-total': `$${Math.round(precioConDescuento)}`
    };
    
    console.log('Valores del resumen:', resumenElementos);
    
    Object.entries(resumenElementos).forEach(([id, valor]) => {
        const elemento = document.getElementById(id);
        if (elemento) {
            console.log(`Actualizando ${id} con valor: ${valor}`);
            elemento.textContent = valor;
        } else {
            console.warn(`No se encontró elemento con ID: ${id}`);
        }
    });
    
    // Cargar rutas del paquete
    cargarRutasPaquete(paquete.nombre);
}

// Cargar rutas del paquete
function cargarRutasPaquete(nombrePaquete) {
    fetch(apiUrl(`/api/paquetes/${encodeURIComponent(nombrePaquete)}`))
        .then(function (r) { return r.json(); })
        .then(function (data) {
            mostrarRutasPaquete(data.rutas || []);
        })
        .catch(function (error) {
            console.error('Error al cargar rutas del paquete:', error);
        });
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
function confirmarCompra() {
    if (!paqueteSeleccionado) {
        showToast('No hay paquete seleccionado', 'warning');
        return;
    }
    
    if (!clienteActual) {
        showToast('No hay cliente autenticado', 'warning');
        return;
    }
    
    const btnComprar = document.getElementById('btn-confirmar-compra');
    if (btnComprar) {
        btnComprar.disabled = true;
        btnComprar.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Procesando...';
    }
    
    const requestData = {
        nombrePaquete: paqueteSeleccionado.nombre,
        costo: paqueteSeleccionado.costoTotal
    };
    
    fetch(apiUrl('/api/compra-paquetes'), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
        .then(function (r) { return r.json(); })
        .then(function (data) {
            if (data.compra) {
                mostrarModalConfirmacion(data.compra);
            } else if (data.error) {
                mostrarMensajeYaComprado();
                showToast('Ya has comprado este paquete anteriormente. Puedes seleccionar otro paquete.', 'warning');
            } else {
                showToast('Error al realizar la compra: ' + (data.error || 'Error desconocido'), 'error');
            }
        })
        .catch(function (error) {
            console.error('Error al confirmar compra:', error);
            showToast('Error al realizar la compra. Por favor, intenta nuevamente.', 'error');
        })
        .then(function () {
            if (btnComprar) {
                btnComprar.disabled = false;
                btnComprar.innerHTML = '<i class="fas fa-shopping-cart"></i> Confirmar Compra';
            }
        });
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
function verDetallePaquete(nombrePaquete) {
    fetch(apiUrl(`/api/paquetes/${encodeURIComponent(nombrePaquete)}`))
        .then(function (r) { return r.json(); })
        .then(function (data) {
            mostrarDetalleCompra(data.paquete);
        })
        .catch(function (error) {
            console.error('Error al cargar detalle del paquete:', error);
        });
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
