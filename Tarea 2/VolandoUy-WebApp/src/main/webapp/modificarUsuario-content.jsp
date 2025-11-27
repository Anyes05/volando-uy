<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Verificar si el usuario está logueado -->
<c:if test="${empty sessionScope.usuarioLogueado}">
  <div class="error-message">
    <h2>Acceso Denegado</h2>
    <p>Debes iniciar sesión para acceder a esta página.</p>
    <a href="inicioSesion.jsp" class="btn-login">Iniciar Sesión</a>
  </div>
</c:if>

<c:if test="${not empty sessionScope.usuarioLogueado}">
  <section class="modificar-perfil-container">
    <h2>Mi Perfil</h2>
    <p class="subtitle">Modifica tus datos personales. No puedes cambiar tu nickname ni correo electrónico.</p>

    <!-- Mensaje de carga -->
    <div id="loading-message" style="display: none;">
      <p>Cargando datos del perfil...</p>
    </div>

    <!-- Formulario para Cliente -->
    <section class="modificar-usuario-box" id="modificar-form-cliente" style="display:none;">
      <h3>Perfil de Cliente</h3>
      <form id="form-modificar-cliente" enctype="multipart/form-data">
        <div class="usuario-header">
          <img id="modificar-cliente-imagen" src="" alt="Imagen del cliente">
          <div class="usuario-info">
            <p><strong>Nickname:</strong> <span id="modificar-cliente-nickname">${sessionScope.usuarioLogueado}</span></p>
            <p><strong>Email:</strong> <span id="modificar-cliente-email">${sessionScope.correoUsuario}</span></p>
          </div>
        </div>
        
        <div class="form-row">
          <label for="modificar-cliente-nombre">Nombre:</label>
          <input type="text" id="modificar-cliente-nombre" name="nombre" required>
        </div>
        
        <div class="form-row">
          <label for="modificar-cliente-apellido">Apellido:</label>
          <input type="text" id="modificar-cliente-apellido" name="apellido" required>
        </div>
        
        <div class="form-row">
          <label for="modificar-cliente-nacionalidad">Nacionalidad:</label>
          <input type="text" id="modificar-cliente-nacionalidad" name="nacionalidad">
        </div>
        
        <div class="form-row">
          <label for="modificar-cliente-tipo-documento">Tipo de Documento:</label>
          <select id="modificar-cliente-tipo-documento" name="tipoDocumento">
            <option value="">Seleccionar...</option>
            <option value="CI">Cédula</option>
            <option value="Pasaporte">Pasaporte</option>
            <option value="DNI">DNI</option>
          </select>
        </div>
        
        <div class="form-row">
          <label for="modificar-cliente-numero-documento">Número de Documento:</label>
          <input type="text" id="modificar-cliente-numero-documento" name="numeroDocumento">
        </div>
        
        <div class="form-row">
          <label for="modificar-cliente-fecha-nacimiento">Fecha de Nacimiento:</label>
          <input type="date" id="modificar-cliente-fecha-nacimiento" name="fechaNacimiento">
        </div>
        
        <div class="form-row">
          <label for="modificar-cliente-imagen-input">Imagen de perfil:</label>
          <div class="file-upload-container">
            <input type="file" id="modificar-cliente-imagen-input" name="imagen" accept="image/*" class="file-input">
            <label for="modificar-cliente-imagen-input" class="file-upload-label">
              <div class="file-upload-content">
                <i class="fas fa-cloud-upload-alt"></i>
                <span class="file-upload-text">Seleccionar imagen</span>
                <span class="file-upload-hint">PNG, JPG, JPEG hasta 5MB</span>
              </div>
            </label>
            <div class="file-preview" id="file-preview-cliente" style="display: none;">
              <img id="preview-image-cliente" src="" alt="Vista previa">
              <button type="button" class="remove-file-btn" onclick="removeFileCliente()">
                <i class="fas fa-times"></i>
              </button>
            </div>
          </div>
        </div>
        
        <div class="form-actions">
          <button type="submit" class="btn-guardar">Guardar cambios</button>
          <button type="button" class="btn-cancelar" id="cancelar-modificacion-cliente">Cancelar</button>
        </div>
      </form>
      <div id="mensaje-modificar-cliente" class="mensaje-usuario" style="display:none;"></div>
    </section>

    <!-- Formulario para Aerolínea -->
    <section class="modificar-usuario-box" id="modificar-form-aerolinea" style="display:none;">
      <h3>Perfil de Aerolínea</h3>
      <form id="form-modificar-aerolinea" enctype="multipart/form-data">
        <div class="usuario-header">
          <img id="modificar-aerolinea-imagen" src="" alt="Imagen de la aerolínea">
          <div class="usuario-info">
            <p><strong>Nickname:</strong> <span id="modificar-aerolinea-nickname">${sessionScope.usuarioLogueado}</span></p>
            <p><strong>Email:</strong> <span id="modificar-aerolinea-email">${sessionScope.correoUsuario}</span></p>
          </div>
        </div>
        
        <div class="form-row">
          <label for="modificar-aerolinea-nombre">Nombre:</label>
          <input type="text" id="modificar-aerolinea-nombre" name="nombre" required>
        </div>
        
        <div class="form-row">
          <label for="modificar-aerolinea-sitio">Sitio web:</label>
          <input type="url" id="modificar-aerolinea-sitio" name="sitioWeb" placeholder="https://">
        </div>
        
        <div class="form-row">
          <label for="modificar-aerolinea-descripcion">Descripción:</label>
          <textarea id="modificar-aerolinea-descripcion" name="descripcion" rows="3"></textarea>
        </div>
        
        <div class="form-row">
          <label for="modificar-aerolinea-imagen-input">Imagen de perfil:</label>
          <div class="file-upload-container">
            <input type="file" id="modificar-aerolinea-imagen-input" name="imagen" accept="image/*" class="file-input">
            <label for="modificar-aerolinea-imagen-input" class="file-upload-label">
              <div class="file-upload-content">
                <i class="fas fa-cloud-upload-alt"></i>
                <span class="file-upload-text">Seleccionar imagen</span>
                <span class="file-upload-hint">PNG, JPG, JPEG hasta 5MB</span>
              </div>
            </label>
            <div class="file-preview" id="file-preview-aerolinea" style="display: none;">
              <img id="preview-image-aerolinea" src="" alt="Vista previa">
              <button type="button" class="remove-file-btn" onclick="removeFileAerolinea()">
                <i class="fas fa-times"></i>
              </button>
            </div>
          </div>
        </div>
        
        <div class="form-actions">
          <button type="submit" class="btn-guardar">Guardar cambios</button>
          <button type="button" class="btn-cancelar" id="cancelar-modificacion-aerolinea">Cancelar</button>
        </div>
      </form>
      <div id="mensaje-modificar-aerolinea" class="mensaje-usuario" style="display:none;"></div>
    </section>

    <!-- Mensaje de error -->
    <div id="error-message" class="error-message" style="display:none;">
      <p>Error al cargar los datos del perfil. Por favor, inténtalo de nuevo.</p>
    </div>
  </section>
</c:if>

<script>
document.addEventListener("DOMContentLoaded", () => {
  // Manejo del input de archivo para Cliente
  const fileInputCliente = document.getElementById("modificar-cliente-imagen-input");
  const filePreviewCliente = document.getElementById("file-preview-cliente");
  const previewImageCliente = document.getElementById("preview-image-cliente");

  if (fileInputCliente && filePreviewCliente && previewImageCliente) {
    fileInputCliente.addEventListener("change", function(e) {
      const file = e.target.files[0];
      if (file) {
        // Validar tipo de archivo
        if (!file.type.startsWith('image/')) {
          showToast('Por favor selecciona un archivo de imagen válido.', 'warning');
          fileInputCliente.value = '';
          return;
        }
        
        // Validar tamaño (5MB)
        if (file.size > 5 * 1024 * 1024) {
          showToast('El archivo es demasiado grande. Máximo 5MB.', 'warning');
          fileInputCliente.value = '';
          return;
        }

        // Mostrar vista previa
        const reader = new FileReader();
        reader.onload = function(e) {
          previewImageCliente.src = e.target.result;
          filePreviewCliente.style.display = 'block';
        };
        reader.readAsDataURL(file);
      }
    });

    // Función para remover archivo de cliente
    window.removeFileCliente = function() {
      fileInputCliente.value = '';
      filePreviewCliente.style.display = 'none';
      previewImageCliente.src = '';
    };
  }

  // Manejo del input de archivo para Aerolínea
  const fileInputAerolinea = document.getElementById("modificar-aerolinea-imagen-input");
  const filePreviewAerolinea = document.getElementById("file-preview-aerolinea");
  const previewImageAerolinea = document.getElementById("preview-image-aerolinea");

  if (fileInputAerolinea && filePreviewAerolinea && previewImageAerolinea) {
    fileInputAerolinea.addEventListener("change", function(e) {
      const file = e.target.files[0];
      if (file) {
        // Validar tipo de archivo
        if (!file.type.startsWith('image/')) {
          showToast('Por favor selecciona un archivo de imagen válido.', 'warning');
          fileInputAerolinea.value = '';
          return;
        }
        
        // Validar tamaño (5MB)
        if (file.size > 5 * 1024 * 1024) {
          showToast('El archivo es demasiado grande. Máximo 5MB.', 'warning');
          fileInputAerolinea.value = '';
          return;
        }

        // Mostrar vista previa
        const reader = new FileReader();
        reader.onload = function(e) {
          previewImageAerolinea.src = e.target.result;
          filePreviewAerolinea.style.display = 'block';
        };
        reader.readAsDataURL(file);
      }
    });

    // Función para remover archivo de aerolínea
    window.removeFileAerolinea = function() {
      fileInputAerolinea.value = '';
      filePreviewAerolinea.style.display = 'none';
      previewImageAerolinea.src = '';
    };
  }

  // Solo ejecutar si el usuario está logueado
  const usuarioLogueado = '<c:out value="${sessionScope.usuarioLogueado}" default="" />';
  if (!usuarioLogueado || usuarioLogueado === '') {
    console.log("Usuario no logueado, no se ejecutará el script");
    return; // No hacer nada si no está logueado
  }

  console.log("Usuario logueado:", usuarioLogueado);
  
  // Cargar datos del usuario logueado
  cargarDatosUsuario();

  // Event listeners para formularios (solo si existen los elementos)
  const formAerolinea = document.getElementById("form-modificar-aerolinea");
  const formCliente = document.getElementById("form-modificar-cliente");
  const cancelarAerolinea = document.getElementById("cancelar-modificacion-aerolinea");
  const cancelarCliente = document.getElementById("cancelar-modificacion-cliente");

  if (formAerolinea) {
    formAerolinea.addEventListener("submit", guardarCambiosAerolinea);
  }
  if (formCliente) {
    formCliente.addEventListener("submit", guardarCambiosCliente);
  }
  if (cancelarAerolinea) {
    cancelarAerolinea.addEventListener("click", cancelarModificacion);
  }
  if (cancelarCliente) {
    cancelarCliente.addEventListener("click", cancelarModificacion);
  }

  // Event listener para preview de imagen en cliente
  const imagenInputCliente = document.getElementById("modificar-cliente-imagen-input");
  if (imagenInputCliente) {
    imagenInputCliente.addEventListener("change", function(e) {
      const file = e.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
          const imagenPreview = document.getElementById("modificar-cliente-imagen");
          if (imagenPreview) {
            imagenPreview.src = e.target.result;
            imagenPreview.style.display = "block";
          }
        };
        reader.readAsDataURL(file);
      }
    });
  }

  // Event listener para preview de imagen en aerolínea
  const imagenInputAerolinea = document.getElementById("modificar-aerolinea-imagen-input");
  if (imagenInputAerolinea) {
    imagenInputAerolinea.addEventListener("change", function(e) {
      const file = e.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
          const imagenPreview = document.getElementById("modificar-aerolinea-imagen");
          if (imagenPreview) {
            imagenPreview.src = e.target.result;
            imagenPreview.style.display = "block";
          }
        };
        reader.readAsDataURL(file);
      }
    });
  }

  async function cargarDatosUsuario() {
    console.log("Iniciando carga de datos del usuario");
    const loadingMessage = document.getElementById("loading-message");
    if (loadingMessage) {
      loadingMessage.style.display = "block";
    }
    
    console.log("Haciendo fetch a /api/usuarios/perfil");
    fetch("<%= request.getContextPath() %>/api/usuarios/perfil")
      .then(function (r) { return r.json(); })
      .then(function (usuario) {
        console.log("Usuario obtenido:", usuario);
        mostrarFormularioUsuario(usuario);
        
        const loadingMessage = document.getElementById("loading-message");
        if (loadingMessage) {
          loadingMessage.style.display = "none";
        }
      })
      .catch(function (error) {
        console.error("Error en cargarDatosUsuario:", error);
        mostrarError("Error de conexión: " + error.message);
        
        const loadingMessage = document.getElementById("loading-message");
        if (loadingMessage) {
          loadingMessage.style.display = "none";
        }
      });
  }

  function mostrarFormularioUsuario(usuario) {
    console.log("Mostrando formulario para usuario:", usuario);
    try {
      if (usuario.tipo === "cliente") {
        mostrarFormularioCliente(usuario);
      } else if (usuario.tipo === "aerolinea") {
        mostrarFormularioAerolinea(usuario);
      } else {
        console.error("Tipo de usuario desconocido:", usuario.tipo);
        mostrarError("Tipo de usuario no reconocido");
      }
    } catch (error) {
      console.error("Error al mostrar formulario:", error);
      mostrarError("Error al mostrar el formulario");
    }
  }

  function mostrarFormularioAerolinea(aerolinea) {
    console.log("Mostrando formulario aerolínea");
    const formAerolinea = document.getElementById("modificar-form-aerolinea");
    const formCliente = document.getElementById("modificar-form-cliente");
    
    if (formAerolinea) {
      formAerolinea.style.display = "block";
    }
    if (formCliente) {
      formCliente.style.display = "none";
    }

    // Llenar datos
    const nombreInput = document.getElementById("modificar-aerolinea-nombre");
    const sitioInput = document.getElementById("modificar-aerolinea-sitio");
    const descripcionInput = document.getElementById("modificar-aerolinea-descripcion");
    
    if (nombreInput) nombreInput.value = aerolinea.nombre || "";
    if (sitioInput) sitioInput.value = aerolinea.sitioWeb || "";
    if (descripcionInput) descripcionInput.value = aerolinea.descripcion || "";

    // Imagen
    const imagen = document.getElementById("modificar-aerolinea-imagen");
    if (imagen) {
      if (aerolinea.foto && aerolinea.foto.length > 0) {
        imagen.src = "data:image/jpeg;base64," + aerolinea.foto;
        imagen.style.display = "block";
      } else {
        imagen.src = "";
        imagen.alt = "Sin imagen";
        imagen.style.display = "none";
      }
    }
  }

  function mostrarFormularioCliente(cliente) {
    console.log("Mostrando formulario cliente");
    const formCliente = document.getElementById("modificar-form-cliente");
    const formAerolinea = document.getElementById("modificar-form-aerolinea");
    
    if (formCliente) {
      formCliente.style.display = "block";
    }
    if (formAerolinea) {
      formAerolinea.style.display = "none";
    }

    // Llenar datos con verificaciones
    const nombreInput = document.getElementById("modificar-cliente-nombre");
    const apellidoInput = document.getElementById("modificar-cliente-apellido");
    const nacionalidadInput = document.getElementById("modificar-cliente-nacionalidad");
    const tipoDocSelect = document.getElementById("modificar-cliente-tipo-documento");
    const numeroDocInput = document.getElementById("modificar-cliente-numero-documento");
    const fechaNacInput = document.getElementById("modificar-cliente-fecha-nacimiento");
    
    if (nombreInput) nombreInput.value = cliente.nombre || "";
    if (apellidoInput) apellidoInput.value = cliente.apellido || "";
    if (nacionalidadInput) nacionalidadInput.value = cliente.nacionalidad || "";
    if (tipoDocSelect) tipoDocSelect.value = cliente.tipoDocumento || "";
    if (numeroDocInput) numeroDocInput.value = cliente.numeroDocumento || "";

    // Fecha de nacimiento
    if (fechaNacInput && cliente.fechaNacimiento) {
      try {
        const fecha = new Date(cliente.fechaNacimiento.ano, cliente.fechaNacimiento.mes - 1, cliente.fechaNacimiento.dia);
        fechaNacInput.value = fecha.toISOString().split('T')[0];
      } catch (error) {
        console.error("Error al parsear fecha de nacimiento:", error);
      }
    }

    // Imagen
    const imagen = document.getElementById("modificar-cliente-imagen");
    if (imagen) {
      if (cliente.foto && cliente.foto.length > 0) {
        imagen.src = "data:image/jpeg;base64," + cliente.foto;
        imagen.style.display = "block";
      } else {
        imagen.src = "";
        imagen.alt = "Sin imagen";
        imagen.style.display = "none";
      }
    }
  }

  function guardarCambiosAerolinea(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    
    fetch("<%= request.getContextPath() %>/api/usuarios/perfil", {
      method: "PUT",
      body: formData
    })
      .then(function (r) { return r.json(); })
      .then(function (result) {
        mostrarMensaje("mensaje-modificar-aerolinea", result.mensaje || "Perfil modificado exitosamente", "success");
        // Recargar datos para mostrar los cambios, pero mantener la imagen si se subió una
        setTimeout(function () {
          cargarDatosUsuario();
        }, 1500);
      })
      .catch(function (error) {
        mostrarMensaje("mensaje-modificar-aerolinea", "Error de conexión: " + error.message, "error");
      });
  }

  function guardarCambiosCliente(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    
    fetch("<%= request.getContextPath() %>/api/usuarios/perfil", {
      method: "PUT",
      body: formData
    })
      .then(function (r) { return r.json(); })
      .then(function (result) {
        mostrarMensaje("mensaje-modificar-cliente", result.mensaje || "Perfil modificado exitosamente", "success");
        // Recargar datos para mostrar los cambios, pero mantener la imagen si se subió una
        setTimeout(function () {
          cargarDatosUsuario();
        }, 1500);
      })
      .catch(function (error) {
        mostrarMensaje("mensaje-modificar-cliente", "Error de conexión: " + error.message, "error");
      });
  }

  function cancelarModificacion() {
    // Recargar datos originales
    cargarDatosUsuario();
    
    // Limpiar mensajes
    document.getElementById("mensaje-modificar-aerolinea").style.display = "none";
    document.getElementById("mensaje-modificar-cliente").style.display = "none";
  }

  function mostrarMensaje(elementoId, mensaje, tipo) {
    const elemento = document.getElementById(elementoId);
    if (elemento) {
      elemento.textContent = mensaje;
      elemento.className = `mensaje-usuario ${tipo}`;
      elemento.style.display = "block";
    } else {
      console.error("Elemento no encontrado:", elementoId);
    }
  }

  function mostrarError(mensaje) {
    console.error("Mostrando error:", mensaje);
    const errorDiv = document.getElementById("error-message");
    if (errorDiv) {
      const p = errorDiv.querySelector("p");
      if (p) {
        p.textContent = mensaje;
      }
      errorDiv.style.display = "block";
    } else {
      // Fallback: mostrar alert si no existe el div de error
      showToast("Error: " + mensaje, "error");
    }
  }
});
</script>
