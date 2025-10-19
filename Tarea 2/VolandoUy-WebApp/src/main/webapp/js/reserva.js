// reserva.js - Funcionalidad para el caso de uso de reserva de vuelo
(function () {
  if (window.__reservaInit) return;
  window.__reservaInit = true;

  // Referencias a elementos del DOM
  const selAirline = document.getElementById('selAirline');
  const selRoute = document.getElementById('selRoute');
  const selFlight = document.getElementById('selFlight');
  const flightDetails = document.getElementById('flightDetails');
  const seatType = document.getElementById('seatType');
  const qtyPassengers = document.getElementById('qtyPassengers');
  const qtyBaggage = document.getElementById('qtyBaggage');
  const passengersContainer = document.getElementById('passengersContainer');
  const passengersList = document.getElementById('passengersList');
  const packageContainer = document.getElementById('packageContainer');
  const selPackage = document.getElementById('selPackage');
  const summaryText = document.getElementById('summaryText');
  const btnReserve = document.getElementById('btnReserve');
  const toast = document.getElementById('toast');
  const existingReservationAlert = document.getElementById('existingReservationAlert');

  // Variables de estado
  let selectedAirline = null;
  let selectedRoute = null;
  let selectedFlight = null;
  let existingReservation = false;

  // ========== INICIALIZACIÓN ==========
  function init() {
    if (!selAirline) return; // No estamos en la página de reserva

    loadAirlines();
    setupEventListeners();
    setupPaymentModeListeners();
    setupExistingReservationListeners();
  }

  // ========== CARGA INICIAL DE DATOS ==========
  function loadAirlines() {
    showLoading(selAirline);
    
    fetch('/VolandoUy-WebApp/api/reservas/aerolineas')
      .then(response => response.json())
      .then(aerolineas => {
        populateSelect(selAirline, aerolineas, 'nickname', 'nombre', 'Seleccione aerolínea');
        selAirline.disabled = false;
        hideLoading(selAirline);
      })
      .catch(error => {
        console.error('Error al cargar aerolíneas:', error);
        showToast('Error al cargar aerolíneas', 'error');
        hideLoading(selAirline);
      });
  }

  function loadRoutesForAirline(airlineNickname) {
    if (!airlineNickname) {
      resetRouteSelection();
      return;
    }

    showLoading(selRoute);
    
    fetch(`/VolandoUy-WebApp/api/reservas/rutas/${encodeURIComponent(airlineNickname)}`)
      .then(response => response.json())
      .then(rutas => {
        populateSelect(selRoute, rutas, 'nombre', 'nombre', 'Seleccione ruta');
        selRoute.disabled = false;
        hideLoading(selRoute);
        
        if (rutas.length === 0) {
          showToast('Esta aerolínea no tiene rutas disponibles', 'warning');
        }
      })
      .catch(error => {
        console.error('Error al cargar rutas:', error);
        showToast('Error al cargar rutas de la aerolínea', 'error');
        hideLoading(selRoute);
      });
  }

  function loadFlightsForRoute(routeName) {
    if (!routeName) {
      resetFlightSelection();
      return;
    }

    showLoading(selFlight);
    
    fetch(`/VolandoUy-WebApp/api/reservas/vuelos/${encodeURIComponent(routeName)}`)
      .then(response => response.json())
      .then(vuelos => {
        populateSelect(selFlight, vuelos, 'nombre', 'nombre', 'Seleccione vuelo');
        selFlight.disabled = false;
        hideLoading(selFlight);
        
        if (vuelos.length === 0) {
          showToast('Esta ruta no tiene vuelos disponibles', 'warning');
        }
      })
      .catch(error => {
        console.error('Error al cargar vuelos:', error);
        showToast('Error al cargar vuelos de la ruta', 'error');
        hideLoading(selFlight);
      });
  }

  function loadFlightDetails(flightName) {
    if (!flightName) {
      clearFlightDetails();
      return;
    }

    showLoading(flightDetails);
    
    fetch(`/VolandoUy-WebApp/api/reservas/vuelo-detalle/${encodeURIComponent(flightName)}`)
      .then(response => response.json())
      .then(vuelo => {
        selectedFlight = vuelo;
        displayFlightDetails(vuelo);
        checkExistingReservation(flightName);
        enableReservationFields();
        hideLoading(flightDetails);
      })
      .catch(error => {
        console.error('Error al cargar detalles del vuelo:', error);
        showToast('Error al cargar detalles del vuelo', 'error');
        hideLoading(flightDetails);
      });
  }

  // ========== MANEJO DE RESERVAS EXISTENTES ==========
  function checkExistingReservation(flightName) {
    fetch(`/VolandoUy-WebApp/api/reservas/verificar-reserva-existente/${encodeURIComponent(flightName)}`)
      .then(response => response.json())
      .then(data => {
        if (data.existeReserva) {
          existingReservation = true;
          showExistingReservationAlert();
          disableReservationForm();
        } else {
          existingReservation = false;
          hideExistingReservationAlert();
          enableReservationForm();
        }
      })
      .catch(error => {
        console.error('Error al verificar reserva existente:', error);
        // Si hay error, asumir que no existe reserva
        existingReservation = false;
        hideExistingReservationAlert();
        enableReservationForm();
      });
  }

  function showExistingReservationAlert() {
    existingReservationAlert.classList.remove('hidden');
    existingReservationAlert.scrollIntoView({ behavior: 'smooth', block: 'center' });
  }

  function hideExistingReservationAlert() {
    existingReservationAlert.classList.add('hidden');
  }

  // ========== EVENT LISTENERS ==========
  function setupEventListeners() {
    selAirline.addEventListener('change', (e) => {
      selectedAirline = e.target.value;
      resetRouteSelection();
      resetFlightSelection();
      loadRoutesForAirline(selectedAirline);
    });

    selRoute.addEventListener('change', (e) => {
      selectedRoute = e.target.value;
      resetFlightSelection();
      loadFlightsForRoute(selectedRoute);
    });

    selFlight.addEventListener('change', (e) => {
      loadFlightDetails(e.target.value);
    });

    qtyPassengers.addEventListener('change', (e) => {
      updatePassengersFields(parseInt(e.target.value));
      updateSummary();
    });

    seatType.addEventListener('change', updateSummary);
    qtyBaggage.addEventListener('change', updateSummary);

    btnReserve.addEventListener('click', handleCreateReservation);
  }

  function setupPaymentModeListeners() {
    const payModeRadios = document.querySelectorAll('input[name="payMode"]');
    payModeRadios.forEach(radio => {
      radio.addEventListener('change', (e) => {
        if (e.target.value === 'paquete') {
          loadClientPackages();
          packageContainer.classList.remove('hidden');
        } else {
          packageContainer.classList.add('hidden');
        }
        updateSummary();
      });
    });
  }

  function setupExistingReservationListeners() {
    document.getElementById('btnCambiarAerolinea')?.addEventListener('click', () => {
      resetAirlineSelection();
      hideExistingReservationAlert();
      showToast('Selecciona una nueva aerolínea', 'info');
    });

    document.getElementById('btnCambiarRuta')?.addEventListener('click', () => {
      resetRouteSelection();
      hideExistingReservationAlert();
      showToast('Selecciona una nueva ruta', 'info');
    });

    document.getElementById('btnCambiarVuelo')?.addEventListener('click', () => {
      resetFlightSelection();
      hideExistingReservationAlert();
      showToast('Selecciona un nuevo vuelo', 'info');
    });

    document.getElementById('btnCancelarCaso')?.addEventListener('click', () => {
      if (confirm('¿Estás seguro de que deseas cancelar la reserva?')) {
        window.location.href = '/VolandoUy-WebApp/inicio';
      }
    });
  }

  // ========== FUNCIONES DE RESET ==========
  function resetAirlineSelection() {
    selAirline.value = '';
    selectedAirline = null;
    resetRouteSelection();
    resetFlightSelection();
  }

  function resetRouteSelection() {
    selRoute.innerHTML = '<option value="">Seleccione aerolínea primero</option>';
    selRoute.disabled = true;
    selectedRoute = null;
  }

  function resetFlightSelection() {
    selFlight.innerHTML = '<option value="">Seleccione ruta primero</option>';
    selFlight.disabled = true;
    selectedFlight = null;
    clearFlightDetails();
    disableReservationFields();
  }

  // ========== DISPLAY DE DETALLES ==========
  function displayFlightDetails(vuelo) {
    flightDetails.classList.remove('hidden');
    flightDetails.innerHTML = `
      <div class="flight-details">
        <div class="detail-header">
          <h3><i class="fas fa-plane"></i> Detalles del Vuelo</h3>
        </div>
        <div class="detail-grid">
          <div class="detail-item">
            <span class="label">Vuelo:</span>
            <span class="value">${vuelo.nombre}</span>
          </div>
          <div class="detail-item">
            <span class="label">Ruta:</span>
            <span class="value">${vuelo.ruta?.nombre || 'N/A'}</span>
          </div>
          <div class="detail-item">
            <span class="label">Fecha:</span>
            <span class="value">${formatDate(vuelo.fechaVuelo)}</span>
          </div>
          <div class="detail-item">
            <span class="label">Hora:</span>
            <span class="value">${vuelo.horaVuelo}</span>
          </div>
          <div class="detail-item">
            <span class="label">Duración:</span>
            <span class="value">${vuelo.duracion}</span>
          </div>
          <div class="detail-item">
            <span class="label">Asientos Turista:</span>
            <span class="value">${vuelo.asientosMaxTurista}</span>
          </div>
          <div class="detail-item">
            <span class="label">Asientos Ejecutivo:</span>
            <span class="value">${vuelo.asientosMaxEjecutivo}</span>
          </div>
        </div>
      </div>
    `;
  }

  function clearFlightDetails() {
    flightDetails.classList.add('hidden');
    flightDetails.innerHTML = '';
  }

  // ========== MANEJO DE PASAJEROS ==========
  function updatePassengersFields(quantity) {
    if (quantity <= 1) {
      passengersContainer.classList.add('hidden');
      passengersList.innerHTML = '';
      return;
    }

    passengersContainer.classList.remove('hidden');
    const additionalPassengers = quantity - 1;

    passengersList.innerHTML = '';
    for (let i = 0; i < additionalPassengers; i++) {
      const passengerDiv = document.createElement('div');
      passengerDiv.className = 'passenger-item';
      passengerDiv.innerHTML = `
        <div class="passenger-fields">
          <div class="passenger-field">
            <input type="text" placeholder="Nombre" required data-passenger-name="${i}" class="passenger-name">
          </div>
          <div class="passenger-field">
            <input type="text" placeholder="Apellido" required data-passenger-lastname="${i}" class="passenger-lastname">
          </div>
          <div class="passenger-field">
            <input type="text" placeholder="Nickname" required data-passenger-nickname="${i}" class="passenger-nickname">
          </div>
          <div class="passenger-actions">
            <button type="button" class="btn-remove" onclick="removePassenger(${i})" title="Eliminar pasajero">
              <i class="fas fa-times"></i>
            </button>
          </div>
        </div>
        <div class="passenger-validation" data-passenger-validation="${i}"></div>
      `;
      passengersList.appendChild(passengerDiv);
    }

    // Agregar event listeners para validación en tiempo real
    setupPassengerValidation();
  }

  // ========== VALIDACIÓN DE PASAJEROS ==========
  function setupPassengerValidation() {
    const nicknameInputs = document.querySelectorAll('.passenger-nickname');
    nicknameInputs.forEach(input => {
      input.addEventListener('blur', (e) => {
        validatePassengerNickname(e.target);
      });
      
      input.addEventListener('input', (e) => {
        clearPassengerValidation(e.target);
      });
    });
  }

  function validatePassengerNickname(input) {
    const nickname = input.value.trim();
    const index = input.getAttribute('data-passenger-nickname');
    const validationDiv = document.querySelector(`[data-passenger-validation="${index}"]`);
    
    if (!nickname) {
      clearPassengerValidation(input);
      return;
    }

    // Verificar si el nickname existe en el sistema
    showPassengerValidation(input, 'Verificando...', 'loading');
    
    fetch('/VolandoUy-WebApp/api/reservas/usuarios')
      .then(response => {
        if (!response.ok) {
          return response.json().then(errorData => {
            throw new Error(`HTTP error! status: ${response.status}, message: ${errorData.error || 'Unknown error'}`);
          });
        }
        return response.json();
      })
      .then(data => {
        // Verificar si la respuesta es un array
        if (!Array.isArray(data)) {
          console.error('Respuesta inesperada del servidor:', data);
          showPassengerValidation(input, 'Error en la respuesta del servidor', 'error');
          return;
        }
        
        const cliente = data.find(c => c.nickname && c.nickname.toLowerCase() === nickname.toLowerCase());
        if (cliente) {
          showPassengerValidation(input, `✓ ${cliente.nombre} ${cliente.apellido}`, 'success');
        } else {
          showPassengerValidation(input, 'Nickname no registrado en el sistema', 'error');
        }
      })
      .catch(error => {
        console.error('Error al validar nickname:', error);
        showPassengerValidation(input, 'Error al verificar nickname', 'error');
      });
  }

  function showPassengerValidation(input, message, type) {
    const index = input.getAttribute('data-passenger-nickname');
    const validationDiv = document.querySelector(`[data-passenger-validation="${index}"]`);
    
    validationDiv.innerHTML = `<small class="validation-${type}">${message}</small>`;
    
    if (type === 'success') {
      input.classList.remove('error');
      input.classList.add('success');
    } else if (type === 'error') {
      input.classList.remove('success');
      input.classList.add('error');
    } else {
      input.classList.remove('success', 'error');
    }
  }

  function clearPassengerValidation(input) {
    const index = input.getAttribute('data-passenger-nickname');
    const validationDiv = document.querySelector(`[data-passenger-validation="${index}"]`);
    validationDiv.innerHTML = '';
    input.classList.remove('success', 'error');
  }

  // ========== FUNCIONES DE UTILIDAD ==========
  function populateSelect(select, items, valueField, textField, defaultText) {
    select.innerHTML = `<option value="">${defaultText}</option>`;
    items.forEach(item => {
      const option = document.createElement('option');
      option.value = item[valueField];
      option.textContent = item[textField];
      select.appendChild(option);
    });
  }

  function enableReservationFields() {
    seatType.disabled = false;
    qtyPassengers.disabled = false;
    qtyBaggage.disabled = false;
    updateSummary();
  }

  function disableReservationFields() {
    seatType.disabled = true;
    seatType.value = '';
    qtyPassengers.disabled = true;
    qtyPassengers.value = 1;
    qtyBaggage.disabled = true;
    qtyBaggage.value = 0;
    btnReserve.disabled = true;
  }

  function enableReservationForm() {
    btnReserve.disabled = false;
  }

  function disableReservationForm() {
    btnReserve.disabled = true;
  }

  function updateSummary() {
    if (!selectedFlight || !seatType.value) {
      summaryText.classList.add('hidden');
      document.querySelector('.summary-placeholder').classList.remove('hidden');
      btnReserve.disabled = true;
      return;
    }

    const passengers = parseInt(qtyPassengers.value) || 1;
    const baggage = parseInt(qtyBaggage.value) || 0;
    const seatTypeValue = seatType.value;
    const payMode = document.querySelector('input[name="payMode"]:checked')?.value || 'normal';

    // Calcular costos (simplificado)
    const baseCost = seatTypeValue === 'ejecutivo' ? 
      (selectedFlight.ruta?.costoBaseEjecutivo || 200) : 
      (selectedFlight.ruta?.costoBaseTurista || 100);
    
    const baggageCost = baggage * (selectedFlight.ruta?.costoEquipajeExtra || 50);
    const totalCost = (baseCost * passengers) + baggageCost;

    summaryText.innerHTML = `
      <div class="summary-item">
        <span>Vuelo:</span>
        <span>${selectedFlight.nombre}</span>
      </div>
      <div class="summary-item">
        <span>Tipo de asiento:</span>
        <span>${seatTypeValue === 'ejecutivo' ? 'Ejecutivo' : 'Turista'}</span>
      </div>
      <div class="summary-item">
        <span>Pasajeros:</span>
        <span>${passengers}</span>
      </div>
      <div class="summary-item">
        <span>Equipaje extra:</span>
        <span>${baggage} unidades</span>
      </div>
      <div class="summary-item">
        <span>Forma de pago:</span>
        <span>${payMode === 'paquete' ? 'Con paquete' : 'Normal'}</span>
      </div>
      <div class="summary-total">
        <span>Total estimado:</span>
        <span>$${totalCost}</span>
      </div>
    `;

    document.querySelector('.summary-placeholder').classList.add('hidden');
    summaryText.classList.remove('hidden');
    
    if (!existingReservation) {
      btnReserve.disabled = false;
    }
  }

  // ========== CREAR RESERVA ==========
  function handleCreateReservation() {
    if (!validateReservationForm()) {
      return;
    }

    const reservationData = collectReservationData();
    
    btnReserve.disabled = true;
    btnReserve.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Creando reserva...';

    fetch('/VolandoUy-WebApp/api/reservas', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(reservationData)
    })
    .then(response => {
      if (response.status === 409) { // Conflict - reserva existente
        return response.json().then(data => {
          handleReservationConflict(data);
        });
      }
      return response.json();
    })
    .then(data => {
      if (data && data.error && data.error !== 'CONFLICT') {
        showToast(`Error: ${data.error}`, 'error');
      } else if (data && data.mensaje) {
        showToast('¡Reserva creada exitosamente!', 'success');
        setTimeout(() => {
          window.location.href = '/VolandoUy-WebApp/consultaReserva';
        }, 2000);
      }
    })
    .catch(error => {
      console.error('Error al crear reserva:', error);
      showToast('Error al crear la reserva. Inténtalo nuevamente.', 'error');
    })
    .finally(() => {
      btnReserve.disabled = false;
      btnReserve.innerHTML = '<i class="fas fa-check"></i> Confirmar Reserva';
    });
  }

  function validateReservationForm() {
    if (!selectedFlight) {
      showToast('Debe seleccionar un vuelo', 'error');
      return false;
    }

    if (!seatType.value) {
      showToast('Debe seleccionar un tipo de asiento', 'error');
      return false;
    }

    const passengers = parseInt(qtyPassengers.value);
    if (passengers < 1) {
      showToast('Debe especificar al menos 1 pasajero', 'error');
      return false;
    }

    // Validar datos de pasajeros adicionales
    if (passengers > 1) {
      const nameInputs = document.querySelectorAll('[data-passenger-name]');
      const lastnameInputs = document.querySelectorAll('[data-passenger-lastname]');
      const nicknameInputs = document.querySelectorAll('[data-passenger-nickname]');
      
      for (let i = 0; i < nameInputs.length; i++) {
        const nombre = nameInputs[i].value.trim();
        const apellido = lastnameInputs[i].value.trim();
        const nickname = nicknameInputs[i].value.trim();
        
        if (!nombre || !apellido || !nickname) {
          showToast(`Complete todos los datos del pasajero ${i + 2}`, 'error');
          if (!nombre) nameInputs[i].focus();
          else if (!apellido) lastnameInputs[i].focus();
          else if (!nickname) nicknameInputs[i].focus();
          return false;
        }
        
        // Verificar que el nickname tiene validación exitosa
        const validationDiv = document.querySelector(`[data-passenger-validation="${i}"]`);
        if (validationDiv && !validationDiv.querySelector('.validation-success')) {
          showToast(`El nickname del pasajero ${i + 2} no está registrado en el sistema`, 'error');
          nicknameInputs[i].focus();
          return false;
        }
      }
    }

    return true;
  }

  function collectReservationData() {
    const passengers = parseInt(qtyPassengers.value);
    const additionalPassengers = [];

    if (passengers > 1) {
      const nameInputs = document.querySelectorAll('[data-passenger-name]');
      const lastnameInputs = document.querySelectorAll('[data-passenger-lastname]');
      const nicknameInputs = document.querySelectorAll('[data-passenger-nickname]');
      
      for (let i = 0; i < nameInputs.length; i++) {
        additionalPassengers.push({
          nombre: nameInputs[i].value.trim(),
          apellido: lastnameInputs[i].value.trim(),
          nickname: nicknameInputs[i].value.trim()
        });
      }
    }

    const payMode = document.querySelector('input[name="payMode"]:checked')?.value || 'normal';

    return {
      vueloNombre: selectedFlight.nombre,
      tipoAsiento: seatType.value,
      cantidadPasajes: passengers,
      equipajeExtra: parseInt(qtyBaggage.value) || 0,
      formaPago: payMode,
      paqueteId: payMode === 'paquete' ? selPackage.value : null,
      pasajeros: additionalPassengers
    };
  }

  // ========== FUNCIONES DE PAQUETES ==========
  function loadClientPackages() {
    fetch('/VolandoUy-WebApp/api/reservas/paquetes-cliente')
      .then(response => response.json())
      .then(paquetes => {
        populateSelect(selPackage, paquetes, 'id', 'nombre', 'Seleccione paquete');
      })
      .catch(error => {
        console.error('Error al cargar paquetes:', error);
        selPackage.innerHTML = '<option value="">Error al cargar paquetes</option>';
      });
  }

  // ========== FUNCIONES DE UI ==========
  function showLoading(element) {
    element.classList.add('loading');
  }

  function hideLoading(element) {
    element.classList.remove('loading');
  }

  function showToast(message, type = 'info') {
    if (!toast) return;

    toast.className = `toast ${type}`;
    toast.textContent = message;
    toast.classList.remove('hidden');

    setTimeout(() => {
      toast.classList.add('hidden');
    }, 4000);
  }

  function formatDate(dateString) {
    try {
      const date = new Date(dateString);
      return date.toLocaleDateString('es-UY', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      });
    } catch {
      return dateString;
    }
  }

  // ========== FUNCIONES GLOBALES ==========
  window.removePassenger = function(index) {
    const passengerItems = document.querySelectorAll('.passenger-item');
    if (passengerItems[index]) {
      passengerItems[index].remove();
    }
    
    // Recalcular cantidad de pasajeros
    const remainingPassengers = document.querySelectorAll('.passenger-item').length + 1;
    qtyPassengers.value = remainingPassengers;
    updateSummary();
  };

  // ========== MANEJO DE CONFLICTOS ==========
  function handleReservationConflict(conflictData) {
    const mensaje = conflictData.mensaje || 'Ya existe una reserva para este cliente en este vuelo.';
    const opciones = conflictData.opciones || [];
    
    // Crear modal de opciones similar al JOptionPane de Swing
    const modalHtml = `
      <div class="conflict-modal-overlay">
        <div class="conflict-modal">
          <div class="conflict-header">
            <i class="fas fa-exclamation-triangle"></i>
            <h3>Conflicto de Reserva</h3>
          </div>
          <div class="conflict-message">
            <p>${mensaje}</p>
            <p>¿Qué deseas hacer?</p>
          </div>
          <div class="conflict-options">
            ${opciones.map((opcion, index) => 
              `<button class="conflict-option-btn" data-option="${index + 1}">${opcion}</button>`
            ).join('')}
          </div>
        </div>
      </div>
    `;
    
    // Agregar modal al DOM
    const modalContainer = document.createElement('div');
    modalContainer.innerHTML = modalHtml;
    document.body.appendChild(modalContainer);
    
    // Agregar event listeners a las opciones
    const optionButtons = modalContainer.querySelectorAll('.conflict-option-btn');
    optionButtons.forEach(button => {
      button.addEventListener('click', (e) => {
        const option = e.target.getAttribute('data-option');
        handleConflictOption(option);
        document.body.removeChild(modalContainer);
      });
    });
    
    // Cerrar modal con ESC
    const handleEscape = (e) => {
      if (e.key === 'Escape') {
        document.body.removeChild(modalContainer);
        document.removeEventListener('keydown', handleEscape);
      }
    };
    document.addEventListener('keydown', handleEscape);
  }
  
  function handleConflictOption(option) {
    switch(option) {
      case '1': // Cambiar aerolínea
        resetAirlineSelection();
        showToast('Selecciona una nueva aerolínea', 'info');
        break;
      case '2': // Cambiar ruta de vuelo  
        resetRouteSelection();
        showToast('Selecciona una nueva ruta', 'info');
        break;
      case '3': // Cambiar vuelo
        resetFlightSelection(); 
        showToast('Selecciona un nuevo vuelo', 'info');
        break;
      case '4': // Cambiar cliente (no aplica en web, solo admin puede)
        showToast('En la interfaz web no puedes cambiar de cliente', 'warning');
        break;
      case '5': // Cancelar caso de uso
        if (confirm('¿Estás seguro de que deseas cancelar la reserva?')) {
          window.location.href = '/VolandoUy-WebApp/inicio';
        }
        break;
      default:
        showToast('Opción no válida', 'error');
    }
  }

  // ========== INICIALIZACIÓN ==========
  document.addEventListener('DOMContentLoaded', init);
})();