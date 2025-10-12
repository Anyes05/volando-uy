<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="contenido-principal">
  <section class="search-box">
    <h2>Compra de Paquete de Rutas de Vuelo</h2>
    <p class="descripcion-compra">Selecciona un paquete de rutas de vuelo disponible para realizar tu compra. Los paquetes mostrados contienen al menos una ruta de vuelo activa.</p>
    
    <!-- Información del cliente (se llenaría dinámicamente) -->
    <div class="cliente-info">
      <h3>Información del Cliente</h3>
      <div class="cliente-details">
        <p><strong>Cliente:</strong> <span id="cliente-nombre">Usuario Loggeado</span></p>
        <p><strong>Email:</strong> <span id="cliente-email">cliente@ejemplo.com</span></p>
        <p><strong>Fecha de compra:</strong> <span id="fecha-compra">-</span></p>
      </div>
    </div>
  </section>
</div>

<!-- Contenedor donde se listan los paquetes disponibles para compra -->
<div id="lista-paquetes-compra" class="grid-list"></div>

<!-- Mensaje si no hay paquetes disponibles -->
<div id="sin-paquetes" class="mensaje-vacio" style="display: none;">
  <i class="fas fa-box-open"></i>
  <h3>No hay paquetes disponibles</h3>
  <p>No se encontraron paquetes con rutas de vuelo activas para comprar en este momento.</p>
</div>

<!-- Detalle de paquete seleccionado para compra -->
<section class="detalle-compra" style="display: none">
  <button class="close-detail-btn" onclick="volverAListaCompra()" aria-label="Cerrar detalle">×</button>

  <div class="paquete-header">
    <img id="paquete-compra-imagen" src="" alt="Imagen del paquete">
    <div class="paquete-info">
      <h2 id="paquete-compra-nombre"></h2>
      <p id="paquete-compra-descripcion"></p>
    </div>
  </div>

  <div class="paquete-details">
    <p><strong>Días válidos:</strong> <span id="paquete-compra-dias"></span></p>
    <p><strong>Descuento:</strong> <span id="paquete-compra-descuento"></span>%</p>
    <p><strong>Fecha de alta:</strong> <span id="paquete-compra-fecha"></span></p>
    <p><strong>Costo total:</strong> $<span id="paquete-compra-costo"></span></p>
  </div>

  <h3>Rutas incluidas en el paquete</h3>
  <div id="paquete-compra-cantidades" class="rutas-grid"></div>

  <!-- Sección de compra -->
  <div class="seccion-compra">
    <h3>Confirmar Compra</h3>
    <div class="resumen-compra">
      <div class="resumen-item">
        <span>Paquete:</span>
        <span id="resumen-nombre"></span>
      </div>
      <div class="resumen-item">
        <span>Precio original:</span>
        <span id="resumen-precio-original"></span>
      </div>
      <div class="resumen-item descuento">
        <span>Descuento:</span>
        <span id="resumen-descuento"></span>
      </div>
      <div class="resumen-item total">
        <span>Total a pagar:</span>
        <span id="resumen-total"></span>
      </div>
    </div>

    <!-- Mensaje si ya compró el paquete -->
    <div id="ya-comprado" class="mensaje-error" style="display: none;">
      <i class="fas fa-exclamation-triangle"></i>
      <p>Ya has comprado este paquete anteriormente. Puedes seleccionar otro paquete o cancelar la operación.</p>
    </div>

    <div class="acciones-compra">
      <button class="btn-cancelar" onclick="volverAListaCompra()">
        <i class="fas fa-times"></i> Cancelar
      </button>
      <button class="btn-comprar" id="btn-confirmar-compra" onclick="confirmarCompra()">
        <i class="fas fa-shopping-cart"></i> Confirmar Compra
      </button>
    </div>
  </div>
</section>

<!-- Modal de confirmación -->
<div id="modal-confirmacion" class="modal" style="display: none;">
  <div class="modal-content">
    <div class="modal-header">
      <h3><i class="fas fa-check-circle"></i> Compra Confirmada</h3>
      <button class="close-modal" onclick="cerrarModal()">&times;</button>
    </div>
    <div class="modal-body">
      <p>¡Felicitaciones! Tu compra del paquete <strong id="modal-nombre-paquete"></strong> ha sido procesada exitosamente.</p>
      <div class="detalles-compra-modal">
        <p><strong>Fecha de compra:</strong> <span id="modal-fecha-compra"></span></p>
        <p><strong>Vencimiento:</strong> <span id="modal-vencimiento"></span></p>
        <p><strong>Total pagado:</strong> $<span id="modal-total-pagado"></span></p>
      </div>
      <p class="mensaje-exito">La compra ha sido registrada en el sistema con fecha actual. Ya puedes utilizar los vuelos incluidos en tu paquete.</p>
    </div>
    <div class="modal-footer">
      <button class="btn-primary" onclick="cerrarModal()">Aceptar</button>
    </div>
  </div>
</div>
