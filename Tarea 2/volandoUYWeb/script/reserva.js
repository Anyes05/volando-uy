// script/reserva.js
(function () {
  if (window.__reservaInit) return;
  window.__reservaInit = true;

  // Mock data (sustituir por fetch a backend cuando corresponda)
  const DATA = {
    airlines: [
      { id: 'a1', name: 'AeroUruguay', routes: [
          { id: 'r1', name: 'Montevideo - Madrid', flights: [
            { id: 'f1', name: 'AUY101', duration: '11h 20m', date: '2025-11-10', time: '10:30', maxTurista: 180, maxEjecutivo: 24 },
            { id: 'f2', name: 'AUY102', duration: '11h 15m', date: '2025-11-12', time: '22:00', maxTurista: 180, maxEjecutivo: 24 }
          ]},
          { id: 'r2', name: 'Montevideo - Miami', flights: [
            { id: 'f3', name: 'AUY201', duration: '9h 00m', date: '2025-10-02', time: '08:45', maxTurista: 200, maxEjecutivo: 30 }
          ]}
        ]},
      { id: 'a2', name: 'SkyLatam', routes: [
          { id: 'r3', name: 'Buenos Aires - Santiago', flights: [
            { id: 'f4', name: 'SL301', duration: '1h 10m', date: '2025-09-01', time: '06:20', maxTurista: 120, maxEjecutivo: 16 }
          ]},
          { id: 'r4', name: 'Buenos Aires - Lima', flights: [
            { id: 'f5', name: 'SL401', duration: '4h', date: '2025-10-20', time: '14:00', maxTurista: 160, maxEjecutivo: 25 }
          ]}
        ]},
      { id: 'a3', name: 'GlobalWings', routes: [
          { id: 'r5', name: 'San Pablo - Roma', flights: [
            { id: 'f6', name: 'GW501', duration: '13h', date: '2025-12-01', time: '18:00', maxTurista: 210, maxEjecutivo: 40 }
          ]},
          { id: 'r6', name: 'San Pablo - Nueva York', flights: [
            { id: 'f7', name: 'GW601', duration: '10h', date: '2025-12-10', time: '20:00', maxTurista: 200, maxEjecutivo: 50 }
          ]}
        ]}
    ],
    clientPackages: [
      { id: 'p1', name: 'Pack 5 rutas - Julio 2025' },
      { id: 'p2', name: 'Promo Familiar - Marzo' }
    ]
  };

  // Helpers
  const $ = id => document.getElementById(id);

  function populateAirlines() {
    const selAirline = $('selAirline');
    if (!selAirline) return;
    selAirline.innerHTML = '<option value="">Seleccione aerolínea</option>' +
      DATA.airlines.map(a => `<option value="${a.id}">${a.name}</option>`).join('');
  }

  function populatePackages() {
    const selPackage = $('selPackage');
    if (!selPackage) return;
    selPackage.innerHTML = '<option value="">Seleccione paquete</option>' +
      DATA.clientPackages.map(p => `<option value="${p.id}">${p.name}</option>`).join('');
  }

  function findAirline(aid) { return DATA.airlines.find(a => a.id === aid); }
  function findRoute(airline, rid) { return airline ? airline.routes.find(r => r.id === rid) : null; }
  function findFlight(route, fid) { return route ? route.flights.find(f => f.id === fid) : null; }

  // Main init function
  function initReserva() {
    // ensure elements exist (view was loaded)
    const selAirline = $('selAirline');
    if (!selAirline) return; // view not present

    const selRoute = $('selRoute');
    const selFlight = $('selFlight');
    const flightDetails = $('flightDetails');
    const seatType = $('seatType');
    const qtyPassengers = $('qtyPassengers');
    const qtyBaggage = $('qtyBaggage');
    const passengersContainer = $('passengersContainer');
    const passengersList = $('passengersList');
    const packageContainer = $('packageContainer');
    const selPackage = $('selPackage');
    const summaryText = $('summaryText');
    const btnReserve = $('btnReserve');
    const toast = $('toast');

    // Populate initial selects
    populateAirlines();
    populatePackages();

    // Reset downstream controls
    function resetAfterAirline() {
      selRoute.innerHTML = '<option value="">Seleccione aerolínea primero</option>';
      selRoute.disabled = true;
      resetAfterRoute();
    }
    function resetAfterRoute() {
      selFlight.innerHTML = '<option value="">Seleccione ruta primero</option>';
      selFlight.disabled = true;
      clearFlightDetails();
    }
    function clearFlightDetails() {
      flightDetails.classList.add('hidden');
      flightDetails.innerHTML = '';
      seatType.value = '';
      seatType.disabled = true;
      qtyPassengers.value = 1;
      qtyPassengers.disabled = true;
      qtyBaggage.value = 0;
      qtyBaggage.disabled = true;
      passengersContainer.classList.add('hidden');
      passengersList.innerHTML = '';
      document.querySelectorAll('input[name="payMode"]').forEach(r => r.checked = false);
      packageContainer.classList.add('hidden');
      selPackage.value = '';
      disableReserve('Seleccione un vuelo y complete los datos obligatorios.');
    }

    // Event handlers
    function onAirlineChange(e) {
      const aid = e.target.value;
      if (!aid) { resetAfterAirline(); return; }
      const airline = findAirline(aid);
      selRoute.innerHTML = '<option value="">Seleccione ruta</option>' +
        airline.routes.map(r => `<option value="${r.id}">${r.name}</option>`).join('');
      selRoute.disabled = false;
      resetAfterRoute();
    }

    function onRouteChange(e) {
      const rid = e.target.value;
      const airline = findAirline(selAirline.value);
      if (!rid || !airline) { resetAfterRoute(); return; }
      const route = findRoute(airline, rid);
      selFlight.innerHTML = '<option value="">Seleccione vuelo</option>' +
        route.flights.map(f => `<option value="${f.id}">${f.name} — ${f.date} ${f.time}</option>`).join('');
      selFlight.disabled = false;
      clearFlightDetails();
    }

    function onFlightChange(e) {
      const fid = e.target.value;
      const airline = findAirline(selAirline.value);
      const route = findRoute(airline, selRoute.value);
      const flight = findFlight(route, fid);
      if (!flight) { clearFlightDetails(); return; }

      flightDetails.classList.remove('hidden');
      flightDetails.innerHTML = `
        <div class="flight-details">
          <div class="row"><div><b>Vuelo</b></div><div>${flight.name}</div></div>
          <div class="row"><div><b>Ruta</b></div><div>${route.name}</div></div>
          <div class="row"><div><b>Duración</b></div><div>${flight.duration}</div></div>
          <div class="row"><div><b>Fecha</b></div><div>${flight.date}</div></div>
          <div class="row"><div><b>Hora</b></div><div>${flight.time}</div></div>
          <div class="row"><div><b>Máx. Turista</b></div><div>${flight.maxTurista}</div></div>
          <div class="row"><div><b>Máx. Ejecutivo</b></div><div>${flight.maxEjecutivo}</div></div>
        </div>
      `;

      seatType.disabled = false;
      qtyPassengers.disabled = false;
      qtyBaggage.disabled = false;
      qtyPassengers.value = 1;
      qtyBaggage.value = 0;
      passengersContainer.classList.add('hidden');
      passengersList.innerHTML = '';
      document.querySelectorAll('input[name="payMode"]').forEach(r => r.checked = false);
      packageContainer.classList.add('hidden');

      disableReserve('Seleccione tipo de asiento y complete la información.');
    }

    function onQtyChange(e) {
      const q = Math.max(1, parseInt(e.target.value, 10) || 1);
      const additional = Math.max(0, q - 1);
      passengersList.innerHTML = '';
      if (additional > 0) {
        passengersContainer.classList.remove('hidden');
        for (let i = 0; i < additional; i++) {
          const idx = i + 1;
          const div = document.createElement('div');
          div.className = 'passenger-row';
          div.innerHTML = `
            <input type="text" class="pname" placeholder="Nombre pasajero ${idx}" aria-label="Nombre pasajero ${idx}">
            <input type="text" class="psurname" placeholder="Apellido pasajero ${idx}" aria-label="Apellido pasajero ${idx}">
          `;
          passengersList.appendChild(div);
        }
      } else {
        passengersContainer.classList.add('hidden');
      }
      validateForm();
    }

    function onPayModeChange(e) {
      if (e.target.value === 'paquete') {
        packageContainer.classList.remove('hidden');
      } else {
        packageContainer.classList.add('hidden');
        selPackage.value = '';
      }
      validateForm();
    }

    function validateForm() {
      const airline = findAirline(selAirline.value);
      const route = airline ? findRoute(airline, selRoute.value) : null;
      const flight = route ? findFlight(route, selFlight.value) : null;
      if (!flight) { disableReserve('Seleccione un vuelo.'); return; }

      const st = seatType.value;
      if (!st) { disableReserve('Seleccione tipo de asiento.'); return; }

      const qty = Math.max(1, parseInt(qtyPassengers.value, 10) || 1);
      const available = (st === 'turista') ? flight.maxTurista : flight.maxEjecutivo;
      if (qty > available) { disableReserve(`Cantidad (${qty}) supera disponibilidad (${available}).`); return; }

      const additional = Math.max(0, qty - 1);
      if (additional > 0) {
        const rows = passengersList.querySelectorAll('.passenger-row');
        if (rows.length < additional) { disableReserve('Complete nombres de pasajeros adicionales.'); return; }
        for (let r of rows) {
          const name = r.querySelector('.pname').value.trim();
          const surname = r.querySelector('.psurname').value.trim();
          if (!name || !surname) { disableReserve('Complete todos los nombres y apellidos de pasajeros.'); return; }
        }
      }

      const pay = document.querySelector('input[name="payMode"]:checked');
      if (!pay) { disableReserve('Seleccione forma de reserva.'); return; }
      if (pay.value === 'paquete' && !selPackage.value) { disableReserve('Seleccione un paquete.'); return; }

      enableReserve();
      updateSummary();
    }

    function disableReserve(msg) {
      btnReserve.disabled = true;
      btnReserve.setAttribute('aria-disabled', 'true');
      summaryText.textContent = msg;
    }
    function enableReserve() {
      btnReserve.disabled = false;
      btnReserve.removeAttribute('aria-disabled');
    }

    function updateSummary() {
      const airline = findAirline(selAirline.value);
      const route = airline ? findRoute(airline, selRoute.value) : null;
      const flight = route ? findFlight(route, selFlight.value) : null;
      const summaryEl = document.getElementById('summaryText');
      const placeholderEl = summaryEl.parentElement.querySelector('.summary-placeholder');
      
      if (!flight) {
        if (placeholderEl) placeholderEl.style.display = 'block';
        summaryEl.classList.add('hidden');
        return;
      }
      
      if (placeholderEl) placeholderEl.style.display = 'none';
      summaryEl.classList.remove('hidden');

      const st = seatType.value;
      const qty = Math.max(1, parseInt(qtyPassengers.value, 10) || 1);
      const bag = Math.max(0, parseInt(qtyBaggage.value, 10) || 0);

      const priceTur = 120;
      const priceEje = 420;
      const bagUnit = 25;
      const unitPrice = (st === 'ejecutivo') ? priceEje : priceTur;
      const total = (unitPrice * qty) + (bagUnit * bag);

      // Crear el resumen con HTML estructurado
      summaryEl.innerHTML = `
        <div class="summary-item">
          <span class="label">Aerolínea:</span>
          <span class="value">${airline.name}</span>
        </div>
        <div class="summary-item">
          <span class="label">Ruta:</span>
          <span class="value">${route.name}</span>
        </div>
        <div class="summary-item">
          <span class="label">Vuelo:</span>
          <span class="value">${flight.name}</span>
        </div>
        <div class="summary-item">
          <span class="label">Fecha:</span>
          <span class="value">${flight.date}</span>
        </div>
        <div class="summary-item">
          <span class="label">Hora:</span>
          <span class="value">${flight.time}</span>
        </div>
        <div class="summary-item">
          <span class="label">Tipo asiento:</span>
          <span class="value">${st}</span>
        </div>
        <div class="summary-item">
          <span class="label">Pasajes:</span>
          <span class="value">${qty}</span>
        </div>
        <div class="summary-item">
          <span class="label">Equipaje extra:</span>
          <span class="value">${bag} unidades</span>
        </div>
        <div class="summary-total">
          <span class="label">Precio estimado:</span>
          <span class="value">USD $${total}</span>
        </div>
      `;
    }

    function onReserve(e) {
      e.preventDefault();
      validateForm();
      if (btnReserve.disabled) { showToast('Faltan datos o hay errores.'); return; }

      const airline = findAirline(selAirline.value);
      const route = findRoute(airline, selRoute.value);
      const flight = findFlight(route, selFlight.value);
      const payload = {
        airlineId: airline.id,
        routeId: route.id,
        flightId: flight.id,
        seatType: seatType.value,
        qty: parseInt(qtyPassengers.value, 10),
        baggage: parseInt(qtyBaggage.value, 10),
        passengers: Array.from(passengersList.querySelectorAll('.passenger-row')).map(r => ({
          name: r.querySelector('.pname').value.trim(),
          surname: r.querySelector('.psurname').value.trim()
        })),
        payMode: (document.querySelector('input[name="payMode"]:checked') || {}).value || 'normal',
        packageId: selPackage.value || null
      };

      console.log('Payload reserva:', payload);
      showToast('Reserva enviada. Procesando...', 1600);
      btnReserve.disabled = true;
      btnReserve.setAttribute('aria-disabled', 'true');

      setTimeout(() => {
        showToast('Reserva registrada correctamente.', 2000);
      }, 1200);
    }

    function showToast(msg, ms = 2000) {
      if (!toast) return;
      toast.textContent = msg;
      toast.classList.remove('hidden');
      setTimeout(() => toast.classList.add('hidden'), ms);
    }

    // Attach events (single init)
    selAirline.addEventListener('change', onAirlineChange);
    selRoute.addEventListener('change', onRouteChange);
    selFlight.addEventListener('change', onFlightChange);
    seatType.addEventListener('change', validateForm);
    qtyPassengers.addEventListener('input', onQtyChange);
    qtyBaggage.addEventListener('input', validateForm);
    document.querySelectorAll('input[name="payMode"]').forEach(r => r.addEventListener('change', onPayModeChange));
    selPackage.addEventListener('change', validateForm);
    btnReserve.addEventListener('click', onReserve);
    passengersList.addEventListener('input', validateForm);

    // initial state
    resetAfterAirline();
    disableReserve('Seleccione un vuelo para comenzar.');
  } // end initReserva

  // expose init function in case controlador wants to call it
  window.initReserva = initReserva;

  // try to auto-start if DOM already has the view nodes
  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', () => {
      // small delay to ensure controller inserted the partial
      setTimeout(() => { if (document.getElementById('selAirline')) initReserva(); }, 10);
    });
  } else {
    // DOM loaded — try to init after small delay to let controller insert HTML
    setTimeout(() => { if (document.getElementById('selAirline')) initReserva(); }, 10);
  }

})(); // IIFE end
