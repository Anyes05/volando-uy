<div class="login-container">
  <div class="login-box">
    <h1>
      <img src="${pageContext.request.contextPath}/static/img/logoAvionSolo.png" alt="VolandoUY" class="logo-img">
      VolandoUY
    </h1>
    <h2>Iniciar Sesi&oacute;n</h2>

    <form id="login-form" action="${pageContext.request.contextPath}/login" method="post">
      <div class="input-group">
        <label for="email">Correo o Usuario</label>
        <input type="text" id="email" name="email" placeholder="tu@email.com o nickname" required>
      </div>

      <div class="input-group">
        <label for="password">Contrase&ntilde;a</label>
        <input type="password" id="password" name="password" placeholder="Tu contrase&ntilde;a" required>
      </div>

      <button type="submit" class="btn-login">
        <i class="fas fa-sign-in-alt"></i> Iniciar Sesi&oacute;n
      </button>
    </form>

    <div class="extra-links">
      <a href="registrarUsuario.jsp">&iquest;No tienes cuenta? Reg&iacute;strate</a>
      <a href="#">&iquest;Olvidaste tu contrase&ntilde;a?</a>
    </div>
  </div>
</div>

<script>
document.getElementById('login-form').addEventListener('submit', async function(e) {
  e.preventDefault();

  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;
  const submitBtn = document.querySelector('.btn-login');

  // Mostrar estado de carga
  submitBtn.disabled = true;
  submitBtn.classList.add('loading');
  submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Iniciando sesi&oacute;n...';

  try {
    const response = await fetch('${pageContext.request.contextPath}/login', {
      method: 'POST',
      credentials: 'include', // importante: envia cookie de sesion (JSESSIONID)
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: new URLSearchParams({
        email: email,
        password: password
      })
    });

    const result = await response.json().catch(() => ({}));

    if (response.ok) {
      // Guardar datos utiles en sessionStorage para reutilizar en frontend
      if (result.nickname) sessionStorage.setItem('usuarioLogueado', result.nickname);
      if (result.nombre) sessionStorage.setItem('nombreUsuario', result.nombre);
      if (result.correo) sessionStorage.setItem('correoUsuario', result.correo);
      if (result.tipoUsuario) sessionStorage.setItem('tipoUsuario', result.tipoUsuario);
      // Nota: layout.jsp también sincroniza tipoUsuario desde la sesión del servidor
      // Si el backend devuelve foto en Base64, guardarla (en uso en <img> usa 'data:image/png;base64,'+foto)
      if (result.foto) sessionStorage.setItem('fotoUsuario', result.foto);

      // Restaurar boton y redirigir
      submitBtn.disabled = false;
      submitBtn.classList.remove('loading');
      submitBtn.innerHTML = '<i class="fas fa-sign-in-alt"></i> Iniciar Sesi&oacute;n';

      // Redirigir según el dispositivo: en móvil después de login, ir a inicio.jsp (que mostrará el contenido apropiado)
      window.location.href = '${pageContext.request.contextPath}/inicio.jsp';
    } else {
      // Mostrar error y restaurar boton
      const msg = result.mensaje || result.error || 'Error al iniciar sesi&oacute;n';
      showToast(msg, 'error');
      submitBtn.disabled = false;
      submitBtn.classList.remove('loading');
      submitBtn.innerHTML = '<i class="fas fa-sign-in-alt"></i> Iniciar Sesi&oacute;n';
    }
  } catch (error) {
    console.error('Error:', error);
    showToast('Error de conexi&oacute;n: ' + (error.message || error), 'error');
    submitBtn.disabled = false;
    submitBtn.classList.remove('loading');
    submitBtn.innerHTML = '<i class="fas fa-sign-in-alt"></i> Iniciar Sesi&oacute;n';
  }
});
</script>
