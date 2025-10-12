<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- views/reserva.jsp -->
<div class="contenido-principal">
  <section class="reserve-section" aria-labelledby="reserveTitle">
    <div class="reserve-card">
      <header class="reserve-header">
        <div class="header-content">
          <h1 id="reserveTitle">
            <i class="fas fa-plane-departure"></i>
            Reservar un vuelo
          </h1>
          <p class="reserve-sub">Seleccione aerolínea, ruta y vuelo. Complete los datos para finalizar la reserva.</p>
        </div>
      </header>

    <main class="reserve-body">
      <div class="left-col">
        <!-- Aerolínea -->
        <div class="field">
          <label for="selAirline">Aerolínea</label>
          <select id="selAirline" aria-label="Seleccionar aerolínea">
            <option value="">Seleccione aerolínea</option>
        
          </select>
        </div>

        <!-- Ruta -->
        <div class="field">
          <label for="selRoute">Ruta</label>
          <select id="selRoute" aria-label="Seleccionar ruta" disabled>
            <option value="">Seleccione aerolínea primero</option>
          </select>
        </div>

        <!-- Vuelo -->
        <div class="field">
          <label for="selFlight">Vuelo</label>
          <select id="selFlight" aria-label="Seleccionar vuelo" disabled>
            <option value="">Seleccione ruta primero</option>
          </select>
        </div>

        <!-- Detalles -->
        <div id="flightDetails" class="flight-details hidden" aria-live="polite"></div>

        <!-- Tipo asiento / qty / baggage -->
        <div class="field-inline">
          <div class="field small">
            <label for="seatType">Tipo de asiento</label>
            <select id="seatType" aria-label="Tipo de asiento" disabled>
              <option value="">Seleccione</option>
              <option value="turista">Turista</option>
              <option value="ejecutivo">Ejecutivo</option>
            </select>
          </div>

          <div class="field small">
            <label for="qtyPassengers">Cantidad de pasajes</label>
            <input type="number" id="qtyPassengers" min="1" value="1" aria-label="Cantidad de pasajes" disabled>
          </div>

          <div class="field small">
            <label for="qtyBaggage">Equipaje extra (unidades)</label>
            <input type="number" id="qtyBaggage" min="0" value="0" aria-label="Equipaje extra" disabled>
          </div>
        </div>

        <!-- Pasajeros adicionales -->
        <div id="passengersContainer" class="full-width hidden">
          <label>Datos de pasajeros adicionales (sin contarte a ti)</label>
          <div id="passengersList" class="passengers-list"></div>
        </div>

        <!-- Forma de reserva -->
        <div class="field full-width">
          <label>Forma de reserva</label>
          <div class="radio-row">
            <label class="radio-item"><input type="radio" name="payMode" value="normal"> Reserva normal</label>
            <label class="radio-item"><input type="radio" name="payMode" value="paquete"> Reserva con paquete</label>
          </div>
        </div>

        <!-- Paquetes -->
        <div id="packageContainer" class="field full-width hidden">
          <label for="selPackage">Paquetes disponibles</label>
          <select id="selPackage" aria-label="Seleccionar paquete">
            <option value="">No hay paquetes</option>
          </select>
          <small class="hint">Se mostrarán paquetes que ya compraste</small>
        </div>
      </div>

      <aside class="summary">
        <div class="summary-header">
          <h3><i class="fas fa-receipt"></i> Resumen de Reserva</h3>
        </div>
        <div id="summaryBox" class="summary-content">
          <div class="summary-placeholder">
            <i class="fas fa-info-circle"></i>
            <p>Seleccione un vuelo para ver el resumen detallado</p>
          </div>
          <div id="summaryText" class="summary-details hidden"></div>
        </div>

        <div class="summary-actions">
          <button id="btnReserve" class="btn-reserve" disabled aria-disabled="true">
            <i class="fas fa-check"></i> Confirmar Reserva
          </button>
        </div>
      </aside>
    </main>
  </div>

  <div id="toast" class="toast hidden" role="status" aria-live="polite"></div>
</section>
</div>
