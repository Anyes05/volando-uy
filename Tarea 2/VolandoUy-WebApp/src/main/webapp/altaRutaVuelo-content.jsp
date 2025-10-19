<!-- language: html -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="form-section-pro">
  <div class="form-card-pro">
    <h2> Alta de Ruta de Vuelo</h2>
    <p class="form-subtitle">Ingrese los datos de la nueva ruta</p>

    <form id="formAltaRutaPro" class="form-grid" enctype="multipart/form-data" method="post" novalidate>
      <!-- Nombre y Descripción corta -->
      <div class="form-group">
        <label for="nombre">Nombre de la ruta</label>
        <input type="text" id="nombre" name="nombre" placeholder="Montevideo - Madrid" required>
      </div>
      <div class="form-group">
        <label for="descripcionCorta">Descripción corta</label>
        <input type="text" id="descripcionCorta" name="descripcionCorta" maxlength="120" placeholder="Máx. 120 caracteres" required>
      </div>

      <!-- Descripción -->
      <div class="form-group full-width">
        <label for="descripcion">Descripción</label>
        <textarea id="descripcion" name="descripcion" rows="4" placeholder="Detalles de la ruta, servicios incluidos..." required></textarea>
      </div>

      <!-- Costos -->
      <div class="form-group">
        <label for="costoTurista">Costo Turista</label>
        <input type="number" id="costoTurista" name="costoTurista" min="0" step="0.01" placeholder="USD" required>
      </div>
      <div class="form-group">
        <label for="costoEjecutivo">Costo Ejecutivo</label>
        <input type="number" id="costoEjecutivo" name="costoEjecutivo" min="0" step="0.01" placeholder="USD" required>
      </div>
      <div class="form-group">
        <label for="costoEquipaje">Costo Equipaje Extra</label>
        <input type="number" id="costoEquipaje" name="costoEquipaje" min="0" step="0.01" placeholder="USD">
      </div>
      <div class="form-group">
        <label for="fechaAlta">Fecha de alta</label>
        <input type="date" id="fechaAlta" name="fechaAlta">
      </div>

      <!-- Ciudades -->
      <div class="form-group">
        <label for="ciudadOrigen">Ciudad Origen</label>
        <select id="ciudadOrigen" name="ciudadOrigen" required>
          <option value="">Seleccione ciudad origen</option>
          <!-- Las ciudades se cargarán dinámicamente -->
        </select>
      </div>
      <div class="form-group">
        <label for="ciudadDestino">Ciudad Destino</label>
        <select id="ciudadDestino" name="ciudadDestino" required>
          <option value="">Seleccione ciudad destino</option>
          <!-- Las ciudades se cargarán dinámicamente -->
        </select>
      </div>

      <!-- Categorías -->
      <div class="form-group full-width">
        <label for="categorias">Categorías</label>
        <div id="categorias-container" class="categorias-checkbox-container">
          <!-- Las categorías se cargarán dinámicamente aquí -->
        </div>
        <small class="hint">Seleccione las categorías que aplican a esta ruta</small>
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

      <!-- Mensaje -->
      <div id="formMessage" class="form-message full-width" style="display:none;color:#c00;"></div>

      <!-- Botón -->
      <div class="form-actions full-width">
        <button id="submitBtn" type="submit" class="btn-pro"> Guardar Ruta</button>
      </div>
    </form>
  </div>
</section>

<style>
  /* Estilos para categorías - integrado con el diseño de la página */
  .categorias-checkbox-container {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
    gap: 10px;
    padding: 0;
    margin-top: 8px;
  }

  .categoria-checkbox-item {
    display: flex;
    align-items: center;
    padding: 10px 12px;
    background: rgba(255, 255, 255, 0.08);
    border: 1px solid rgba(255, 255, 255, 0.15);
    border-radius: 12px;
    cursor: pointer;
    transition: all 0.3s ease;
    color: #fff;
    font-size: 13px;
    backdrop-filter: blur(10px);
  }

  .categoria-checkbox-item:hover {
    background: rgba(255, 255, 255, 0.12);
    border-color: rgba(1, 170, 245, 0.4);
    transform: translateY(-1px);
  }

  .categoria-checkbox-item.selected {
    background: rgba(1, 170, 245, 0.15);
    border-color: #01aaf5;
  }

  .categoria-checkbox-item input[type="checkbox"] {
    margin: 0;
    margin-right: 8px;
    width: 14px;
    height: 14px;
    accent-color: #01aaf5;
    cursor: pointer;
  }

  .categoria-checkbox-item label {
    margin: 0;
    cursor: pointer;
    font-weight: 500;
    user-select: none;
    flex: 1;
    color: inherit;
  }

  .categorias-loading {
    text-align: center;
    padding: 20px;
    color: rgba(255, 255, 255, 0.7);
    font-style: italic;
    background: rgba(255, 255, 255, 0.08);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 12px;
    backdrop-filter: blur(10px);
  }

  /* Responsive */
  @media (max-width: 768px) {
    .categorias-checkbox-container {
      grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
      gap: 8px;
    }
  }
</style>

<script>
  // Cargar categorías dinámicamente
  async function cargarCategorias() {
    const container = document.getElementById('categorias-container');
    
    try {
      container.innerHTML = '<div class="categorias-loading">Cargando categorías...</div>';
      
      const response = await fetch('<%= request.getContextPath() %>/api/rutas/categorias', {
        method: 'GET',
        credentials: 'include'
      });

      if (!response.ok) {
        throw new Error('Error HTTP: ' + response.status);
      }

      const categorias = await response.json();
      
      if (!Array.isArray(categorias) || categorias.length === 0) {
        container.innerHTML = '<div class="categorias-loading">No hay categorías disponibles</div>';
        return;
      }

      // Crear checkboxes para cada categoría
      container.innerHTML = '';
      categorias.forEach((categoria, index) => {
        const itemDiv = document.createElement('div');
        itemDiv.className = 'categoria-checkbox-item';
        
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.id = `categoria-${index}`;
        checkbox.name = 'categorias';
        checkbox.value = categoria;
        
        const label = document.createElement('label');
        label.htmlFor = `categoria-${index}`;
        label.textContent = categoria;
        
        // Event listeners para el estilo visual
        checkbox.addEventListener('change', function() {
          if (this.checked) {
            itemDiv.classList.add('selected');
          } else {
            itemDiv.classList.remove('selected');
          }
        });
        
        itemDiv.addEventListener('click', function(e) {
          if (e.target !== checkbox) {
            checkbox.click();
          }
        });
        
        itemDiv.appendChild(checkbox);
        itemDiv.appendChild(label);
        container.appendChild(itemDiv);
      });
      
    } catch (error) {
      console.error('Error al cargar categorías:', error);
      container.innerHTML = '<div class="categorias-loading" style="color: #ff6b6b;">Error al cargar categorías</div>';
    }
  }

  // Cargar ciudades dinámicamente
  async function cargarCiudades() {
    try {
      console.log('Cargando ciudades...');
      
      const response = await fetch('<%= request.getContextPath() %>/api/rutas/ciudades', {
        method: 'GET',
        credentials: 'include'
      });

      if (!response.ok) {
        throw new Error('Error HTTP: ' + response.status);
      }

      const ciudades = await response.json();
      
      if (!Array.isArray(ciudades) || ciudades.length === 0) {
        console.warn('No hay ciudades disponibles');
        return;
      }

      // Cargar ciudades origen
      const selectOrigen = document.getElementById('ciudadOrigen');
      const selectDestino = document.getElementById('ciudadDestino');
      
      // Limpiar opciones existentes (excepto la primera)
      selectOrigen.innerHTML = '<option value="">Seleccione ciudad origen</option>';
      selectDestino.innerHTML = '<option value="">Seleccione ciudad destino</option>';
      
      // Agregar ciudades a ambos selects
      ciudades.forEach(ciudad => {
        const optionOrigen = document.createElement('option');
        optionOrigen.value = ciudad.nombre;
        optionOrigen.textContent = ciudad.nombreCompleto;
        selectOrigen.appendChild(optionOrigen);
        
        const optionDestino = document.createElement('option');
        optionDestino.value = ciudad.nombre;
        optionDestino.textContent = ciudad.nombreCompleto;
        selectDestino.appendChild(optionDestino);
      });
      
      // Agregar listener para filtrar destino cuando se seleccione origen
      selectOrigen.addEventListener('change', function() {
        const ciudadOrigenSeleccionada = this.value;
        
        // Recargar ciudades destino excluyendo la seleccionada como origen
        selectDestino.innerHTML = '<option value="">Seleccione ciudad destino</option>';
        
        ciudades.forEach(ciudad => {
          if (ciudad.nombre !== ciudadOrigenSeleccionada) {
            const option = document.createElement('option');
            option.value = ciudad.nombre;
            option.textContent = ciudad.nombreCompleto;
            selectDestino.appendChild(option);
          }
        });
      });
      
      console.log('Ciudades cargadas exitosamente:', ciudades.length);
      
    } catch (error) {
      console.error('Error al cargar ciudades:', error);
    }
  }

  // Cargar datos al cargar la página
  document.addEventListener('DOMContentLoaded', function() {
    cargarCategorias();
    cargarCiudades();
  });
</script>

<script>
  // Manejo del input de archivo para Alta Ruta Vuelo
  const fileInput = document.getElementById("foto");
  const filePreview = document.getElementById("file-preview");
  const previewImage = document.getElementById("preview-image");

  if (fileInput && filePreview && previewImage) {
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
  }
</script>

<script>
  // poner fecha por defecto a hoy
  document.getElementById("fechaAlta").valueAsDate = new Date();

  const form = document.getElementById("formAltaRutaPro");
  const submitBtn = document.getElementById("submitBtn");
  const msgDiv = document.getElementById("formMessage");

  function showMessage(text, isError = true) {
    msgDiv.style.display = "block";
    msgDiv.style.color = isError ? "#c00" : "#080";
    msgDiv.textContent = text;
  }

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    msgDiv.style.display = "none";

    // Validar campos obligatorios
    const nombre = form.querySelector("[name='nombre']").value.trim();
    const descripcionCorta = form.querySelector("[name='descripcionCorta']").value.trim();
    const descripcion = form.querySelector("[name='descripcion']").value.trim();
    const costoTurista = form.querySelector("[name='costoTurista']").value;
    const costoEjecutivo = form.querySelector("[name='costoEjecutivo']").value;
    const costoEquipaje = form.querySelector("[name='costoEquipaje']").value;
    const ciudadOrigen = form.querySelector("[name='ciudadOrigen']").value;
    const ciudadDestino = form.querySelector("[name='ciudadDestino']").value;
    const fechaAlta = form.querySelector("[name='fechaAlta']").value;

    // Validar que se haya seleccionado al menos una categoría
    const categoriasSeleccionadas = Array.from(form.querySelectorAll('input[name="categorias"]:checked'));
    
    if (!nombre || !descripcionCorta || !descripcion || !costoTurista || !costoEjecutivo || !costoEquipaje || 
        !ciudadOrigen || !ciudadDestino || !fechaAlta || categoriasSeleccionadas.length === 0) {
      showMessage("Complete todos los campos obligatorios y seleccione al menos una categoría");
      return;
    }

    // Validar que las ciudades origen y destino sean diferentes
    if (ciudadOrigen === ciudadDestino) {
      showMessage("La ciudad de origen y destino deben ser diferentes");
      return;
    }

    const formData = new FormData(form);

    submitBtn.disabled = true;
    const originalText = submitBtn.textContent;
    submitBtn.textContent = "Guardando...";

    try {
      const response = await fetch("<%= request.getContextPath() %>/api/rutas", {
        method: "POST",
        body: formData,
        credentials: "include"
      });

      let result = {};
      try { result = await response.json(); } catch (err) {}

      if (response.ok) {
        showMessage(result.mensaje || "Ruta de vuelo creada exitosamente.", false);
        form.reset();
        document.getElementById("fechaAlta").valueAsDate = new Date();
        // Recargar categorías para limpiar selecciones
        cargarCategorias();
      } else {
        showMessage(result.error || ("Error al crear ruta de vuelo: " + response.status));
      }
    } catch (error) {
      showMessage("Error al crear ruta de vuelo: " + (error.message || error));
    } finally {
      submitBtn.disabled = false;
      submitBtn.textContent = originalText;
    }
  });

</script>