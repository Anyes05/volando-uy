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
  const successReservationAlert = document.getElementById('successReservationAlert');
  const btnCerrarAlerta = document.getElementById('btnCerrarAlerta');
  const summaryPlaceholder = document.querySelector('.summary-placeholder');

  // Variables de estado
  let selectedAirline = null;
  let selectedRoute = null;
  let selectedFlight = null;
  let existingReservation = false;

  // ========== INICIALIZACIÓN ==========
  function init() {
    if (!selAirline) return; // No estamos en la página de reserva

    console.log('DEBUG: Inicializando página de reserva');

    loadAirlines();
    setupEventListeners();
    setupPaymentModeListeners();
    setupExistingReservationListeners();

    if (btnCerrarAlerta && successReservationAlert) {
      btnCerrarAlerta.addEventListener('click', () => {
        successReservationAlert.classList.add('hidden');
        window.location.reload();
      });
    }
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
        existingReservation = false;
        hideExistingReservationAlert();
        enableReservationForm();
      });
  }

  function showExistingReservationAlert() {
    if (!existingReservationAlert) return;
    existingReservationAlert.classList.remove('hidden');
    existingReservationAlert.scrollIntoView({ behavior: 'smooth', block: 'center' });
  }

  function hideExistingReservationAlert() {
    if (!existingReservationAlert) return;
    existingReservationAlert.classList.add('hidden');
  }

  function showSuccessAlert() {
    if (!successReservationAlert) return;
    successReservationAlert.classList.remove('hidden');
    successReservationAlert.scrollIntoView({ behavior: 'smooth', block: 'center' });
    disableReservationForm();
  }

  // ========== EVENT LISTENERS ==========
  function setupEventListeners() {
    selAirline.addEventListener('change', (e) => {
      selectedAirline = e.target.value;
      resetRouteSelection();   // limpia rutas + vuelos + datos
      loadRoutesForAirline(selectedAirline);
    });

    selRoute.addEventListener('change', (e) => {
      selectedRoute = e.target.value;
      resetFlightSelection();  // limpia vuelos + datos
      loadFlightsForRoute(selectedRoute);

      const payMode = document.querySelector('input[name="payMode"]:checked');
      if (payMode && payMode.value === 'paquete') {
        loadClientPackagesForRoute(selectedRoute);
      }
    });

    selFlight.addEventListener('change', (e) => {
      // al cambiar vuelo, limpiamos detalles dependientes antes de cargar
      clearReservationDependentData();
      loadFlightDetails(e.target.value);
    });

    qtyPassengers.addEventListener('change', (e) => {
      updatePassengersFields(parseInt(e.target.value));
      updateSummary();
    });

    seatType.addEventListener('change', updateSummary);
    qtyBaggage.addEventListener('change', updateSummary);

    selPackage.addEventListener('change', () => {
      updateSummary();
    });

    btnReserve.addEventListener('click', handleCreateReservation);
  }

  function setupPaymentModeListeners() {
    const payModeRadios = document.querySelectorAll('input[name="payMode"]');
    payModeRadios.forEach(radio => {
      radio.addEventListener('change', (e) => {
        if (e.target.value === 'paquete') {
          if (selectedRoute) {
            loadClientPackagesForRoute(selectedRoute);
          } else {
            selPackage.innerHTML = '<option value="">Seleccione una ruta primero</option>';
          }
          packageContainer.classList.remove('hidden');
        } else {
          packageContainer.classList.add('hidden');
          selPackage.innerHTML = '<option value="">No hay paquetes disponibles</option>';
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

    // CANCELAR → simplemente ir a inicio.jsp, sin confirm
    document.getElementById('btnCancelarCaso')?.addEventListener('click', () => {
      window.location.href = 'inicio.jsp';
    });
  }

  // ========== FUNCIONES DE RESET / LIMPIEZA ==========

  // Limpia todo lo que depende del vuelo seleccionado:
  // asientos, pasajeros, paquetes, resumen, detalles.
  function clearReservationDependentData() {
    // Detalles de vuelo
    clearFlightDetails();

    // Campos de asiento y cantidades
    seatType.value = '';
    seatType.disabled = true;

    qtyPassengers.value = 1;
    qtyPassengers.disabled = true;

    qtyBaggage.value = 0;
    qtyBaggage.disabled = true;

    // Pasajeros adicionales
    passengersContainer.classList.add('hidden');
    passengersList.innerHTML = '';

    // Paquetes
    packageContainer.classList.add('hidden');
    selPackage.innerHTML = '<option value="">No hay paquetes disponibles</option>';

    // Resumen
    if (summaryText) {
      summaryText.innerHTML = '';
      summaryText.classList.add('hidden');
    }
    if (summaryPlaceholder) {
      summaryPlaceholder.classList.remove('hidden');
    }

    // Botón reservar
    btnReserve.disabled = true;
  }

  function resetAirlineSelection() {
    selAirline.value = '';
    selectedAirline = null;

    // al cambiar aerolínea, se limpia TODO lo siguiente
    resetRouteSelection();
  }

  function resetRouteSelection() {
    selRoute.innerHTML = '<option value="">Seleccione aerolínea primero</option>';
    selRoute.disabled = true;
    selectedRoute = null;

    // al cambiar ruta, también limpiamos vuelos y datos
    resetFlightSelection();
  }

  function resetFlightSelection() {
    selFlight.innerHTML = '<option value="">Seleccione ruta primero</option>';
    selFlight.disabled = true;
    selectedFlight = null;

    clearReservationDependentData();
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

    setupPassengerValidation();
  }

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

    if (!nickname) {
      clearPassengerValidation(input);
      return;
    }

    showPassengerValidation(input, 'Verificando...', 'loading');

    fetch('/VolandoUy-WebApp/api/reservas/usuarios')
      .then(response => response.json())
      .then(data => {
        if (!Array.isArray(data)) {
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

    if (!validationDiv) return;

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
    if (validationDiv) validationDiv.innerHTML = '';
    input.classList.remove('success', 'error');
  }

  // ========== FUNCIONES DE UTILIDAD ==========
  function populateSelect(select, items, valueField, textField, defaultText) {
    console.log('DEBUG: populateSelect called with:', { select, items, valueField, textField, defaultText });
    select.innerHTML = `<option value="">${defaultText}</option>`;
    items.forEach(item => {
      const option = document.createElement('option');
      option.value = item[valueField];
      option.textContent = item[textField];
      select.appendChild(option);
    });
    console.log('DEBUG: Select populated. Total options:', select.options.length);
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
      if (summaryText) summaryText.classList.add('hidden');
      if (summaryPlaceholder) summaryPlaceholder.classList.remove('hidden');
      btnReserve.disabled = true;
      return;
    }

    const passengers = parseInt(qtyPassengers.value) || 1;
    const baggage = parseInt(qtyBaggage.value) || 0;
    const seatTypeValue = seatType.value;
    const payMode = document.querySelector('input[name="payMode"]:checked')?.value || 'normal';

    const baseCost = seatTypeValue === 'ejecutivo'
      ? (selectedFlight.ruta?.costoBaseEjecutivo || 200)
      : (selectedFlight.ruta?.costoBaseTurista || 100);

    const baggageCost = baggage * (selectedFlight.ruta?.costoEquipajeExtra || 50);
    const totalCost = (baseCost * passengers) + baggageCost;

    if (summaryText) {
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
      summaryText.classList.remove('hidden');
    }

    if (summaryPlaceholder) {
      summaryPlaceholder.classList.add('hidden');
    }

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
    const originalHtml = btnReserve.innerHTML;
    btnReserve.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Creando reserva...';

    fetch('/VolandoUy-WebApp/api/reservas', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'same-origin',
      body: JSON.stringify(reservationData)
    })
      .then(async (response) => {
        const text = await response.text();
        let data = null;
        const contentType = response.headers.get('content-type') || '';

        if (contentType.includes('application/json') || (/^\s*\{/.test(text))) {
          try {
            data = JSON.parse(text);
          } catch (err) {
            console.error('Respuesta no JSON válida:', text);
          }
        }

        if (response.status === 201 || response.status === 200) {
          showSuccessAlert();
          return;
        }

        if (response.status === 409) {
          if (data) {
            handleReservationConflict(data);
          } else {
            showToast('Conflicto: ya existe una reserva para este vuelo', 'warning');
          }
          return;
        }

        if (data && data.error) {
          showToast(`Error: ${data.error}`, 'error');
        } else if (text) {
          console.error('Error en la respuesta del servidor:', response.status, text);
          showToast('Error del servidor. Revisa la consola para más detalles.', 'error');
        } else {
          showToast('Error desconocido al crear la reserva', 'error');
        }
      })
      .catch(error => {
        console.error('Error en fetch crear reserva:', error);
        showToast('Error de red al crear la reserva. Intenta de nuevo.', 'error');
      })
      .finally(() => {
        btnReserve.disabled = false;
        btnReserve.innerHTML = originalHtml;
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

    let paqueteId = null;
    if (payMode === 'paquete') {
      paqueteId = selPackage.value;
      if (!paqueteId || paqueteId === '') {
        throw new Error('Debe seleccionar un paquete para continuar');
      }
    }

    return {
      vueloNombre: selectedFlight.nombre,
      tipoAsiento: seatType.value,
      cantidadPasajes: passengers,
      equipajeExtra: parseInt(qtyBaggage.value) || 0,
      formaPago: payMode,
      paqueteId: paqueteId,
      pasajeros: additionalPassengers
    };
  }

  function loadClientPackagesForRoute(rutaNombre) {
    if (!rutaNombre) {
      selPackage.innerHTML = '<option value="">Seleccione una ruta primero</option>';
      return;
    }

    showLoading(selPackage);

    fetch(`/VolandoUy-WebApp/api/reservas/paquetes-cliente/${encodeURIComponent(rutaNombre)}`)
      .then(response => response.json())
      .then(paquetes => {
        if (paquetes.length === 0) {
          selPackage.innerHTML = '<option value="">No hay paquetes disponibles para esta ruta</option>';
          showToast('No tienes paquetes que incluyan esta ruta', 'warning');
        } else {
          populateSelect(selPackage, paquetes, 'id', 'nombre', 'Seleccione paquete');
          showToast(`Se encontraron ${paquetes.length} paquete(s) disponibles`, 'success');
        }
        hideLoading(selPackage);
      })
      .catch(error => {
        console.error('Error al cargar paquetes:', error);
        selPackage.innerHTML = '<option value="">Error al cargar paquetes</option>';
        hideLoading(selPackage);
        showToast('Error al cargar paquetes del cliente', 'error');
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

  window.removePassenger = function (index) {
    const passengerItems = document.querySelectorAll('.passenger-item');
    if (passengerItems[index]) {
      passengerItems[index].remove();
    }
    const remainingPassengers = document.querySelectorAll('.passenger-item').length + 1;
    qtyPassengers.value = remainingPassengers;
    updateSummary();
  };

  // ========== MANEJO DE CONFLICTOS ==========
  function handleReservationConflict(conflictData) {
    const mensaje = conflictData.mensaje || 'Ya existe una reserva para este vuelo.';

    const modalHtml = `
      <div class="conflict-modal-overlay">
        <div class="conflict-modal">
          <div class="conflict-header">
            <i class="fas fa-exclamation-triangle"></i>
            <h3>Conflicto de Reserva</h3>
          </div>
          <div class="conflict-message">
            <p>${mensaje}</p>
          </div>
          <div class="conflict-options">
            <button id="conflictAcceptBtn">Aceptar</button>
          </div>
        </div>
      </div>
    `;

    const modalContainer = document.createElement('div');
    modalContainer.innerHTML = modalHtml;
    document.body.appendChild(modalContainer);

    const btnAceptar = modalContainer.querySelector('#conflictAcceptBtn');
    btnAceptar.addEventListener('click', () => {
      document.body.removeChild(modalContainer);
    });

    const handleEscape = (e) => {
      if (e.key === 'Escape') {
        document.body.removeChild(modalContainer);
        document.removeEventListener('keydown', handleEscape);
      }
    };
    document.addEventListener('keydown', handleEscape);
  }

  function handleConflictOption(option) {
    switch (option) {
      case '1':
        resetAirlineSelection();
        showToast('Selecciona una nueva aerolínea', 'info');
        break;
      case '2':
        resetRouteSelection();
        showToast('Selecciona una nueva ruta', 'info');
        break;
      case '3':
        resetFlightSelection();
        showToast('Selecciona un nuevo vuelo', 'info');
        break;
      case '4':
        showToast('En la interfaz web no puedes cambiar de cliente', 'warning');
        break;
      case '5':
        if (confirm('¿Estás seguro de que deseas cancelar la reserva?')) {
          window.location.href = 'inicio.jsp';
        }
        break;
      default:
        showToast('Opción no válida', 'error');
    }
  }

  document.addEventListener('DOMContentLoaded', init);
})();