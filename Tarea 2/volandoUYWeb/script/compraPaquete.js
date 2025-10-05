const paquetesURL = 'json/paquetes.json';
let paquetesData = [];
let paqueteSeleccionado = null;
let comprasRealizadas = []; // Simularemos las compras del usuario

// Datos simulados del cliente loggeado
const clienteActual = {
  id: 1,
  nombre: "Usuario Loggeado",
  email: "cliente@ejemplo.com"
};

function quitarTildes(texto) {
  return texto.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}

function cargarPaquetesCompra() {
  fetch(paquetesURL)
    .then(res => res.json())
    .then(data => {
      paquetesData = data;
      mostrarPaquetesCompra(paquetesData);
      cargarFiltrosCompra(paquetesData);
      actualizarInformacionCliente();
    })
    .catch(err => console.error('Error cargando paquetes:', err));
}

function mostrarPaquetesCompra(lista) {
  const contenedor = document.getElementById('lista-paquetes-compra');
  const sinPaquetes = document.getElementById('sin-paquetes');
  
  contenedor.innerHTML = '';

  if (!lista || lista.length === 0) {
    contenedor.style.display = 'none';
    sinPaquetes.style.display = 'block';
    return;
  }

  contenedor.style.display = 'grid';
  sinPaquetes.style.display = 'none';

  lista.forEach((p, idx) => {
    const yaComprado = comprasRealizadas.some(compra => compra.paqueteId === p.id);
    
    const card = document.createElement('div');
    card.classList.add('paquete-card-compra');
    card.onclick = () => mostrarDetalleCompra(p);

    // Calcular precio con descuento
    const precioOriginal = p.costoTotal;
    const descuento = p.descuento || 0;
    const precioFinal = precioOriginal * (1 - descuento / 100);

    let badgeDescuento = '';
    if (descuento > 0) {
      badgeDescuento = `<div class="descuento-badge">-${descuento}%</div>`;
    }

    let estadoCompra = '';
    if (yaComprado) {
      estadoCompra = '<p style="color: #ffc107; font-weight: 600;"><i class="fas fa-check"></i> Ya comprado</p>';
      card.style.borderColor = '#ffc107';
    }

    card.innerHTML = `
      ${badgeDescuento}
      <img src="${p.imagen || ''}" alt="${p.nombre}">
      <h3>${p.nombre}</h3>
      <p>${p.descripcion}</p>
      <p><strong>Descuento:</strong> ${descuento}%</p>
      <p><strong>Precio original:</strong> $${precioOriginal}</p>
      <div class="precio">$${precioFinal.toFixed(0)}</div>
      ${estadoCompra}
    `;

    contenedor.appendChild(card);
  });
}

function mostrarDetalleCompra(paquete) {
  paqueteSeleccionado = paquete;
  const detalle = document.querySelector('.detalle-compra');
  const yaComprado = comprasRealizadas.some(compra => compra.paqueteId === paquete.id);

  document.getElementById('lista-paquetes-compra').style.display = 'none';
  detalle.style.display = 'block';

  // Llenar información del paquete
  document.getElementById('paquete-compra-imagen').src = paquete.imagen || '';
  document.getElementById('paquete-compra-nombre').textContent = paquete.nombre;
  document.getElementById('paquete-compra-descripcion').textContent = paquete.descripcion;
  document.getElementById('paquete-compra-dias').textContent = paquete.diasValidos || 'No especificado';
  document.getElementById('paquete-compra-descuento').textContent = paquete.descuento || 0;
  document.getElementById('paquete-compra-fecha').textContent = paquete.fechaAlta || '';
  document.getElementById('paquete-compra-costo').textContent = paquete.costoTotal || 0;

  // Llenar rutas incluidas
  const cont = document.getElementById('paquete-compra-cantidades');
  cont.innerHTML = '';

  const cantidades = paquete.cantidades || [];
  if (cantidades.length === 0) {
    cont.innerHTML = '<p>No hay rutas asociadas a este paquete.</p>';
  } else {
    cantidades.forEach(c => {
      const item = document.createElement('div');
      item.classList.add('ruta-item');
      item.innerHTML = `
        <h4>${c.nombreRuta || ('Ruta ' + c.rutaId)}</h4>
        <p><strong>Cantidad:</strong> ${c.cantidad}</p>
        <p><strong>Tipo:</strong> ${c.tipo || 'N/A'}</p>
      `;
      cont.appendChild(item);
    });
  }

  // Llenar resumen de compra
  const precioOriginal = paquete.costoTotal;
  const descuento = paquete.descuento || 0;
  const precioFinal = precioOriginal * (1 - descuento / 100);
  const ahorro = precioOriginal - precioFinal;

  document.getElementById('resumen-nombre').textContent = paquete.nombre;
  document.getElementById('resumen-precio-original').textContent = `$${precioOriginal}`;
  document.getElementById('resumen-descuento').textContent = `-$${ahorro.toFixed(0)} (${descuento}%)`;
  document.getElementById('resumen-total').textContent = `$${precioFinal.toFixed(0)}`;

  // Mostrar/ocultar mensaje si ya fue comprado
  const mensajeYaComprado = document.getElementById('ya-comprado');
  const btnComprar = document.getElementById('btn-confirmar-compra');
  
  if (yaComprado) {
    mensajeYaComprado.style.display = 'flex';
    btnComprar.disabled = true;
    btnComprar.textContent = 'Ya Comprado';
    btnComprar.style.background = '#6c757d';
  } else {
    mensajeYaComprado.style.display = 'none';
    btnComprar.disabled = false;
    btnComprar.innerHTML = '<i class="fas fa-shopping-cart"></i> Confirmar Compra';
    btnComprar.style.background = '';
  }

  detalle.scrollIntoView({ behavior: 'smooth' });
}

function confirmarCompra() {
  if (!paqueteSeleccionado) return;

  const yaComprado = comprasRealizadas.some(compra => compra.paqueteId === paqueteSeleccionado.id);
  if (yaComprado) {
    alert('Este paquete ya ha sido comprado anteriormente.');
    return;
  }

  // Simular la compra
  const fechaActual = new Date();
  const fechaVencimiento = new Date(fechaActual);
  fechaVencimiento.setDate(fechaActual.getDate() + (paqueteSeleccionado.diasValidos || 30));

  const compra = {
    id: comprasRealizadas.length + 1,
    paqueteId: paqueteSeleccionado.id,
    clienteId: clienteActual.id,
    fechaCompra: fechaActual.toLocaleDateString('es-UY'),
    vencimiento: fechaVencimiento.toLocaleDateString('es-UY'),
    costo: paqueteSeleccionado.costoTotal * (1 - (paqueteSeleccionado.descuento || 0) / 100)
  };

  comprasRealizadas.push(compra);

  // Mostrar modal de confirmación
  document.getElementById('modal-nombre-paquete').textContent = paqueteSeleccionado.nombre;
  document.getElementById('modal-fecha-compra').textContent = compra.fechaCompra;
  document.getElementById('modal-vencimiento').textContent = compra.vencimiento;
  document.getElementById('modal-total-pagado').textContent = compra.costo.toFixed(0);
  
  document.getElementById('modal-confirmacion').style.display = 'flex';

  // Actualizar la vista para reflejar la compra
  mostrarPaquetesCompra(paquetesData);
}

function cerrarModal() {
  document.getElementById('modal-confirmacion').style.display = 'none';
  volverAListaCompra();
}

function volverAListaCompra() {
  document.getElementById('lista-paquetes-compra').style.display = 'grid';
  document.querySelector('.detalle-compra').style.display = 'none';
  paqueteSeleccionado = null;
}

function cargarFiltrosCompra(data) {
  // Por ahora no implementamos filtros, pero mantenemos la estructura
  // para futuras mejoras
}

function actualizarInformacionCliente() {
  document.getElementById('cliente-nombre').textContent = clienteActual.nombre;
  document.getElementById('cliente-email').textContent = clienteActual.email;
  
  const fechaActual = new Date();
  document.getElementById('fecha-compra').textContent = fechaActual.toLocaleDateString('es-UY');
}

function filtrarCompra() {
  const input = document.getElementById('buscador-nombre');
  const texto = input ? quitarTildes(input.value.toLowerCase()) : '';

  const filtrados = paquetesData.filter(p => {
    const nombre = quitarTildes((p.nombre || '').toLowerCase());
    const descripcion = quitarTildes((p.descripcion || '').toLowerCase());
    return texto === '' || nombre.includes(texto) || descripcion.includes(texto);
  });

  mostrarPaquetesCompra(filtrados);
}

// Event listeners
document.addEventListener('click', function(e) {
  if (e.target.classList.contains('tab-btn')) {
    // No hay tabs aquí pero preservamos el patrón
  }
});

// Función de inicialización para el controlador de vista
window.initCompraPaquete = function() {
  cargarPaquetesCompra();
};

// Auto-inicializar para desarrollo (cuando se abre la página directamente)
cargarPaquetesCompra();
