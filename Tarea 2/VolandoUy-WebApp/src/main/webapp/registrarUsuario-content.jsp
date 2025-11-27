<section id="registro-usuario" class="form-box">
  <h2>Registro de Usuario</h2>
  <form id="form-registro">

    <!-- Comunes -->
    <div class="form-group">
      <label for="nickname">Nickname:</label>
      <input type="text" id="nickname" name="nickname" required>
      <small class="error-msg"></small>
    </div>

    <div class="form-group">
      <label for="nombre">Nombre:</label>
      <input type="text" id="nombre" name="nombre" required>
      <small class="error-msg"></small>
    </div>

    <div class="form-group">
      <label for="correo">Correo:</label>
      <input type="email" id="correo" name="correo" required>
      <small class="error-msg"></small>
    </div>

    <div class="form-group">
      <label for="contrasena">Contrase&ntilde;a:</label>
      <input type="password" id="contrasena" name="contrasena" required>
      <small class="error-msg"></small>
    </div>

    <div class="form-group">
      <label for="confirmarContrasena"> Confirmar contrase&ntilde;a:</label>
      <input type="password" id="confirmarContrasena" name="confirmarContrasena" required>
      <small class="error-msg"></small>
    </div>

    <!-- Foto -->
     <div class="form-group full-width">
       <label for="foto" style="display: block; margin-bottom: 8px; font-weight: 600; color: #eaf6fb; font-size: 14px;">Imagen de perfil</label>
       <div class="file-upload-container">
         <input type="file" id="foto" name="foto" accept="image/*" class="file-input">
         <div class="file-upload-label" onclick="document.getElementById('foto').click();">
           <div class="file-upload-content">
             <span class="file-upload-text">Haz clic para seleccionar una imagen</span>
             <span class="file-upload-hint">PNG, JPG o GIF (máx. 5MB)</span>
           </div>
         </div>
         <div id="file-preview" class="file-preview" style="display: none;">
           <img id="preview-image" src="" alt="Vista previa">
           <button type="button" class="remove-file-btn" id="remove-file-btn">×</button>
         </div>
       </div>
     </div>

    <!-- Tipo de usuario -->
    <div class="form-group">
      <label for="tipo">Tipo de Usuario:</label>
      <select id="tipo" name="tipo" required>
        <option value="">Seleccionar...</option>
        <option value="cliente">Cliente</option>
        <option value="aerolinea">Aerolinea</option>
      </select>
      <small class="error-msg"></small>
    </div>

    <!-- Campos especificos para Cliente -->
    <div id="campos-cliente" style="display:none;">
      <div class="form-group">
        <label for="apellido">Apellido:</label>
        <input type="text" id="apellido" name="apellido">
        <small class="error-msg"></small>
      </div>

      <div class="form-group">
        <label for="tipoDocumento">Tipo de Documento:</label>
        <select id="tipoDocumento" name="tipoDocumento">
          <option value="">Seleccionar...</option>
          <option value="CI">Cedula</option>
          <option value="Pasaporte">Pasaporte</option>
          <option value="DNI">DNI</option>
        </select>
        <small class="error-msg"></small>
      </div>

      <div class="form-group">
        <label for="numeroDocumento">Numero de Documento:</label>
        <input type="text" id="numeroDocumento" name="numeroDocumento">
        <small class="error-msg"></small>
      </div>

      <div class="form-group">
        <label for="fechaNacimiento">Fecha de Nacimiento:</label>
        <input type="date" id="fechaNacimiento" name="fechaNacimiento">
        <small class="error-msg"></small>
      </div>

      <div class="form-group">
        <label for="nacionalidad">Nacionalidad:</label>
        <input type="text" id="nacionalidad" name="nacionalidad">
        <small class="error-msg"></small>
      </div>
    </div>

    <!-- Campos especificos para Aerolinea -->
    <div id="campos-aerolinea" style="display:none;">
      <div class="form-group">
        <label for="descripcion">Descripcion:</label>
        <textarea id="descripcion" name="descripcion" rows="3"></textarea>
        <small class="error-msg"></small>
      </div>

      <div class="form-group">
        <label for="linkSitioWeb">Sitio Web:</label>
        <input type="url" id="linkSitioWeb" name="linkSitioWeb" placeholder="https://ejemplo.com">
        <small class="error-msg"></small>
      </div>
    </div>

    <!-- Boton -->
    <div class="form-group">
      <button type="submit">Registrar</button>
    </div>

  </form>
</section>

<script>
  document.addEventListener("DOMContentLoaded", () => {
    const tipoSelect = document.getElementById("tipo");
    const camposCliente = document.getElementById("campos-cliente");
    const camposAerolinea = document.getElementById("campos-aerolinea");
    const nicknameInput = document.getElementById("nickname");
    const correoInput = document.getElementById("correo");
    const fotoInput = document.getElementById("foto");
    const filePreview = document.getElementById("file-preview");
    const previewImage = document.getElementById("preview-image");
    const removeFileBtn = document.getElementById("remove-file-btn");
    const fileUploadLabel = document.querySelector('.file-upload-label');
    const fileUploadText = fileUploadLabel.querySelector('.file-upload-text');

    // Variable para el contexto de la aplicación
    const contextPath = '<%= request.getContextPath() %>';

    // Manejo de vista previa de imagen
    if (fotoInput && filePreview && previewImage) {
      fotoInput.addEventListener('change', function(e) {
        const file = e.target.files[0];
        if (file) {
          const reader = new FileReader();
          reader.onload = function(e) {
            previewImage.src = e.target.result;
            filePreview.style.display = 'block';
            fileUploadText.textContent = file.name;
          };
          reader.readAsDataURL(file);
        }
      });

      if (removeFileBtn) {
        removeFileBtn.addEventListener('click', function(e) {
          e.preventDefault();
          fotoInput.value = '';
          filePreview.style.display = 'none';
          previewImage.src = '';
          fileUploadText.textContent = 'Haz clic para seleccionar una imagen';
        });
      }
    }

    // Variables para controlar debounce y cancelar peticiones pendientes
    let nicknameTimeout = null;
    let emailTimeout = null;
    let nicknameAbortController = null;
    let emailAbortController = null;

    // Función para mostrar mensaje de validación
    function mostrarValidacion(input, mensaje, tipo) {
      const errorMsg = input.parentElement.querySelector('.error-msg');
      if (errorMsg) {
        errorMsg.textContent = mensaje;
        errorMsg.className = 'error-msg';
        if (tipo === 'success') {
          errorMsg.classList.add('success-msg');
          input.classList.remove('input-error');
          input.classList.add('input-success');
        } else if (tipo === 'error') {
          errorMsg.classList.add('error-msg-active');
          input.classList.remove('input-success');
          input.classList.add('input-error');
        } else if (tipo === 'loading') {
          errorMsg.classList.add('loading-msg');
          input.classList.remove('input-success', 'input-error');
        } else {
          errorMsg.textContent = '';
          input.classList.remove('input-success', 'input-error');
        }
      }
    }

    // Función para verificar disponibilidad de nickname
    function verificarNickname(nickname) {
      // Cancelar petición anterior si existe
      if (nicknameAbortController) {
        nicknameAbortController.abort();
      }
      nicknameAbortController = new AbortController();

      if (!nickname || nickname.trim().length < 3) {
        mostrarValidacion(nicknameInput, '', 'clear');
        return;
      }

      mostrarValidacion(nicknameInput, 'Verificando...', 'loading');

      fetch(contextPath + '/api/usuarios/check-nickname?nickname=' + encodeURIComponent(nickname), {
        signal: nicknameAbortController.signal
      })
        .then(function (r) { return r.json(); })
        .then(function (data) {
          if (data.disponible) {
            mostrarValidacion(nicknameInput, 'Nickname disponible', 'success');
          } else {
            mostrarValidacion(nicknameInput, 'Este nickname ya esta en uso', 'error');
          }
        })
        .catch(function (error) {
          if (error.name !== 'AbortError') {
            console.error('Error al verificar nickname:', error);
            mostrarValidacion(nicknameInput, 'Error al verificar disponibilidad', 'error');
          }
        });
    }

    // Función para verificar disponibilidad de email
    function verificarEmail(email) {
      // Cancelar petición anterior si existe
      if (emailAbortController) {
        emailAbortController.abort();
      }
      emailAbortController = new AbortController();

      // Validar formato básico de email
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!email || !emailRegex.test(email)) {
        mostrarValidacion(correoInput, '', 'clear');
        return;
      }

      mostrarValidacion(correoInput, 'Verificando...', 'loading');

      fetch(contextPath + '/api/usuarios/check-email?email=' + encodeURIComponent(email), {
        signal: emailAbortController.signal
      })
        .then(function (r) { return r.json(); })
        .then(function (data) {
          if (data.disponible) {
            mostrarValidacion(correoInput, 'Email disponible', 'success');
          } else {
            mostrarValidacion(correoInput, 'Este email ya esta registrado', 'error');
          }
        })
        .catch(function (error) {
          if (error.name !== 'AbortError') {
            console.error('Error al verificar email:', error);
            mostrarValidacion(correoInput, 'Error al verificar disponibilidad', 'error');
          }
        });
    }

    // Event listeners para validación asíncrona
    nicknameInput.addEventListener('input', (e) => {
      const nickname = e.target.value.trim();
      
      // Limpiar timeout anterior
      if (nicknameTimeout) {
        clearTimeout(nicknameTimeout);
      }
      
      // Esperar 500ms después de que el usuario deje de escribir
      nicknameTimeout = setTimeout(() => {
        verificarNickname(nickname);
      }, 500);
    });

    correoInput.addEventListener('input', (e) => {
      const email = e.target.value.trim();
      
      // Limpiar timeout anterior
      if (emailTimeout) {
        clearTimeout(emailTimeout);
      }
      
      // Esperar 500ms después de que el usuario deje de escribir
      emailTimeout = setTimeout(() => {
        verificarEmail(email);
      }, 500);
    });

    tipoSelect.addEventListener("change", () => {
      const tipo = tipoSelect.value;
      if (tipo === "cliente") {
        camposCliente.style.display = "block";
        camposAerolinea.style.display = "none";
      } else if (tipo === "aerolinea") {
        camposCliente.style.display = "none";
        camposAerolinea.style.display = "block";
      } else {
        camposCliente.style.display = "none";
        camposAerolinea.style.display = "none";
      }
    });

    document.getElementById("form-registro").addEventListener("submit", async function(e) {
      e.preventDefault();
      const form = e.target;
      const tipo = form.tipo.value;

      // Verificar que los campos de validación no tengan errores
      const nicknameError = nicknameInput.classList.contains('input-error');
      const emailError = correoInput.classList.contains('input-error');
      
      if (nicknameError || emailError) {
        showToast("Por favor, corrija los errores en los campos antes de continuar.", "error");
        return;
      }

      // Verificar que los campos tengan valores válidos
      if (!nicknameInput.value.trim() || !correoInput.value.trim()) {
        showToast("Por favor, complete todos los campos obligatorios.", "error");
        return;
      }

      if (form.contrasena.value !== form.confirmarContrasena.value) {
        showToast("Las contrasenas no coinciden.", "error");
        return;
      }

      // construir FormData (incluye archivo si se selecciono)
      const formData = new FormData();
      formData.append("nickname", form.nickname.value);
      formData.append("nombre", form.nombre.value);
      formData.append("correo", form.correo.value);
      formData.append("tipo", tipo);
      formData.append("contrasena", form.contrasena.value);
      formData.append("confirmarContrasena", form.confirmarContrasena.value);

      if (tipo === "cliente") {
        formData.append("apellido", form.apellido.value);
        formData.append("tipoDocumento", form.tipoDocumento.value);
        formData.append("numeroDocumento", form.numeroDocumento.value);
        formData.append("fechaNacimiento", form.fechaNacimiento.value);
        formData.append("nacionalidad", form.nacionalidad.value);
      } else if (tipo === "aerolinea") {
        formData.append("descripcion", form.descripcion.value);
        formData.append("linkSitioWeb", form.linkSitioWeb.value);
      }

      const fotoInput = document.getElementById("foto");
      if (fotoInput && fotoInput.files && fotoInput.files.length > 0) {
        formData.append("foto", fotoInput.files[0]);
      }

      // NO establecer header Content-Type manualmente para FormData
      fetch(contextPath + "/api/usuarios", {
        method: "POST",
        body: formData
      })
        .then(function (r) { return r.json(); })
        .then(function (result) {
          if (tipo == "Cliente" && (tipoDocumento != "DNI" || tipoDocumento != "Cedula" || tipoDocumento != "Pasaporte")) {
            showToast(result.mensaje || "Operacion no valida, seleccione un tipo de documento.", "error");
            return;
          }
          
          showToast(result.mensaje || "Operacion completada.", "success");

          // Iniciar sesión automáticamente después del registro exitoso
          fetch(contextPath + "/login", {
            method: "POST",
            credentials: 'include',
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({
              email: form.correo.value,
              password: form.contrasena.value
            })
          })
            .then(function (r) { return r.json(); })
            .then(function (loginResult) {
              // Guardar datos útiles en sessionStorage
              if (loginResult.nickname) sessionStorage.setItem('usuarioLogueado', loginResult.nickname);
              if (loginResult.nombre) sessionStorage.setItem('nombreUsuario', loginResult.nombre);
              if (loginResult.correo) sessionStorage.setItem('correoUsuario', loginResult.correo);
              if (loginResult.tipo) sessionStorage.setItem('tipoUsuario', loginResult.tipo);
              if (loginResult.foto) sessionStorage.setItem('fotoUsuario', loginResult.foto);

              // Redirigir al inicio con sesión iniciada
              window.location.href = contextPath + "/inicio.jsp";
            })
            .catch(function (loginError) {
              console.error('Error en login automático:', loginError);
              showToast("Registro exitoso. Por favor, inicia sesión manualmente.", "success");
              window.location.href = contextPath + "/inicioSesion.jsp";
            });
        })
        .catch(function (error) {
          console.error("Error al registrar usuario:", error);
          showToast("Error al registrar usuario. Por favor, intente nuevamente.", "error");
        });
    });
  });
</script>