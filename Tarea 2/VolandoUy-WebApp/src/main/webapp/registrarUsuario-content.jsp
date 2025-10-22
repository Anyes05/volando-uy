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
      <label for="contrasena">Contrasena:</label>
      <input type="password" id="contrasena" name="contrasena" required>
      <small class="error-msg"></small>
    </div>

    <div class="form-group">
      <label for="confirmarContrasena"> Confirmar contrasena:</label>
      <input type="password" id="confirmarContrasena" name="confirmarContrasena" required>
      <small class="error-msg"></small>
    </div>

    <!-- Foto -->
     <div class="form-group full-width">
       <label for="foto">Imagen de portada</label>
       <input type="file" id="foto" name="foto" accept="image/*">
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

      try {
        // NO establecer header Content-Type manualmente para FormData
        const response = await fetch("<%= request.getContextPath() %>/api/usuarios", {
          method: "POST",
          body: formData
        });

        let result = {};
        try { result = await response.json(); } catch (err) { /* no-json */ }
        if (tipo == "Cliente" && (tipoDocumento != "DNI" || tipoDocumento != "Cedula" || tipoDocumento != "Pasaporte")) {
          showToast(result.mensaje || "Operacion no valida, seleccione un tipo de documento.", "error");
            return;
        }
        if (response.ok) {
          showToast(result.mensaje || "Operacion completada.", "success");

          // Iniciar sesión automáticamente después del registro exitoso
          try {
            const loginResponse = await fetch("<%= request.getContextPath() %>/login", {
              method: "POST",
              credentials: 'include',
              headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
              },
              body: new URLSearchParams({
                email: form.correo.value,
                password: form.contrasena.value
              })
            });

            const loginResult = await loginResponse.json().catch(() => ({}));

            if (loginResponse.ok) {
              // Guardar datos útiles en sessionStorage
              if (loginResult.nickname) sessionStorage.setItem('usuarioLogueado', loginResult.nickname);
              if (loginResult.nombre) sessionStorage.setItem('nombreUsuario', loginResult.nombre);
              if (loginResult.correo) sessionStorage.setItem('correoUsuario', loginResult.correo);
              if (loginResult.tipo) sessionStorage.setItem('tipoUsuario', loginResult.tipo);
              if (loginResult.foto) sessionStorage.setItem('fotoUsuario', loginResult.foto);

              // Redirigir al inicio con sesión iniciada
              window.location.href = "<%= request.getContextPath() %>/inicio.jsp";
            } else {
              // Si falla el login automático, redirigir a la página de login
              showToast("Registro exitoso. Por favor, inicia sesión manualmente.", "success");
              window.location.href = "<%= request.getContextPath() %>/inicioSesion.jsp";
            }
          } catch (loginError) {
            console.error('Error en login automático:', loginError);
            showToast("Registro exitoso. Por favor, inicia sesión manualmente.", "success");
            window.location.href = "<%= request.getContextPath() %>/inicioSesion.jsp";
          }
        } else {
          showToast(result.error || ("Error: " + response.status), "error");
        }
      } catch (error) {
        showToast("Error al registrar: " + error.message, "error");
      }
    });
  });
</script>