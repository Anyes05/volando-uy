<!-- Hero -->
<section class="hero">
  <span class="badge">Escala gratuita</span>
  <h1>Disfruta 2 noches de hotel gratuitas en Abu Dhabi</h1>
  <p>Reserva ahora y suma millas con VolandoUY</p>
  <button class="btn-primary" onclick="window.location.href='reserva.jsp'">Reservar ahora</button>

  <!-- Buscador -->
  <div class="search-box">
    <div class="inputs">
      <div class="input-group">
        <input type="text" id="origen-input" placeholder="Ciudad Origen" autocomplete="off">
      </div>
      <div class="input-group">
        <input type="text" id="destino-input" placeholder="Ciudad Destino" autocomplete="off">
      </div>
      <div class="input-group">
        <input type="date" id="fecha-vuelo" required>
      </div>
      <button class="btn-search" id="btn-buscar"><i class="fas fa-search"></i> Buscar</button>
    </div>
  </div>
</section>

<!-- Ofertas -->
<section class="offers">
  <h2>Ofertas desde <span>Montevideo</span></h2>
  <div class="carousel-container">
    <button class="carousel-btn prev" id="prevBtn" aria-label="Anterior"><i class="fas fa-chevron-left"></i></button>
    <div class="carousel" id="carousel">
      <div class="offer-card"><img src="https://picsum.photos/300/160?1" alt="Hanoi"><h3>Hanoi</h3><p>Desde <span>USD 604</span></p></div>
      <div class="offer-card"><img src="https://picsum.photos/300/160?2" alt="Chiang Mai"><h3>Chiang Mai</h3><p>Desde <span>USD 697</span></p></div>
      <div class="offer-card"><img src="https://picsum.photos/300/160?3" alt="Dubai"><h3>Dubai</h3><p>Desde <span>USD 499</span></p></div>
      <div class="offer-card"><img src="https://picsum.photos/300/160?4" alt="Maldivas"><h3>Maldivas</h3><p>Desde <span>USD 1,199</span></p></div>
      <div class="offer-card"><img src="https://picsum.photos/300/160?5" alt="Tokio"><h3>Tokio</h3><p>Desde <span>USD 899</span></p></div>
      <div class="offer-card"><img src="https://picsum.photos/300/160?6" alt="Madrid"><h3>Madrid</h3><p>Desde <span>USD 799</span></p></div>
    </div>
    <button class="carousel-btn next" id="nextBtn" aria-label="Siguiente"><i class="fas fa-chevron-right"></i></button>
  </div>
</section>
