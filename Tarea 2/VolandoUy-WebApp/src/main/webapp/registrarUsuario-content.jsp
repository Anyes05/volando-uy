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
      <label for="confirmarContrasena">Confirmar contrase&ntilde;a:</label>
      <input type="password" id="confirmarContrasena" name="confirmarContrasena" required>
      <small class="error-msg"></small>
    </div>

    <!-- Foto -->
    <div class="form-group full-width">
      <label for="foto">Imagen de portada</label>
      <div class="file-upload-container">
        <input type="file" id="foto" name="foto" accept="image/*" class="file-input">
        <label for="foto" class="file-upload-label">
          <div class="file-upload-content">
            <i class="fas fa-cloud-upload-alt"></i>
            <span class="file-upload-text">Seleccionar imagen</span>
            <span class="file-upload-hint">PNG, JPG, JPEG hasta 5MB</span>
          </div>
        </label>
        <div class="file-preview" id="file-preview" style="display: none;">
          <img id="preview-image" src="" alt="Vista previa">
          <button type="button" class="remove-file-btn" onclick="removeFile()">
            <i class="fas fa-times"></i>
          </button>
        </div>
      </div>
    </div>

    <!-- Tipo de usuario -->
    <div class="form-group">
      <label for="tipo">Tipo de Usuario:</label>
      <select id="tipo" name="tipo" required>
        <option value="">Seleccionar...</option>
        <option value="cliente">Cliente</option>
        <option value="aerolinea">Aerolínea</option>
      </select>
      <small class="error-msg"></small>
    </div>

    <!-- Campos específicos para Cliente -->
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
          <option value="CI">Cédula</option>
          <option value="Pasaporte">Pasaporte</option>
          <option value="DNI">DNI</option>
        </select>
        <small class="error-msg"></small>
      </div>

      <div class="form-group">
        <label for="numeroDocumento">Número de Documento:</label>
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

    <!-- Campos específicos para Aerolínea -->
    <div id="campos-aerolinea" style="display:none;">
      <div class="form-group">
        <label for="descripcion">Descripción:</label>
        <textarea id="descripcion" name="descripcion" rows="3"></textarea>
        <small class="error-msg"></small>
      </div>

      <div class="form-group">
        <label for="linkSitioWeb">Sitio Web:</label>
        <input type="url" id="linkSitioWeb" name="linkSitioWeb" placeholder="https://ejemplo.com">
        <small class="error-msg"></small>
      </div>
    </div>

    <!-- Botón -->
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

    // Manejo del input de archivo
    const fileInput = document.getElementById("foto");
    const filePreview = document.getElementById("file-preview");
    const previewImage = document.getElementById("preview-image");

    fileInput.addEventListener("change", function(e) {
      const file = e.target.files[0];
      if (file) {
        // Validar tipo de archivo
        if (!file.type.startsWith('image/')) {
          alert('Por favor selecciona un archivo de imagen válido.');
          fileInput.value = '';
          return;
        }
        
        // Validar tamaño (5MB)
        if (file.size > 5 * 1024 * 1024) {
          alert('El archivo es demasiado grande. Máximo 5MB.');
          fileInput.value = '';
          return;
        }

        // Mostrar vista previa
        const reader = new FileReader();
        reader.onload = function(e) {
          previewImage.src = e.target.result;
          filePreview.style.display = 'block';
        };
        reader.readAsDataURL(file);
      }
    });

    // Función para remover archivo
    window.removeFile = function() {
      fileInput.value = '';
      filePreview.style.display = 'none';
      previewImage.src = '';
    };

    document.getElementById("form-registro").addEventListener("submit", async function(e) {
      e.preventDefault();
      const form = e.target;
      const tipo = form.tipo.value;

      if (form.contrasena.value !== form.confirmarContrasena.value) {
        alert("Las contraseñas no coinciden.");
        return;
      }

      // construir FormData (incluye archivo si se seleccionó)
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
        if (tipo == "Cliente" && (tipoDocumento != "DNI" || tipoDocumento != "Cédula" || tipoDocumento != "Pasaporte")) {
          alert(result.mensaje || "Operacion no valida, seleccione un tipo de documento.");
            return;
        }
        if (response.ok) {
          alert(result.mensaje || "Operación completada.");
        } else {
          alert(result.error || ("Error: " + response.status));
        }
      } catch (error) {
        alert("Error al registrar: " + error.message);
      }
    });
  });
</script>
