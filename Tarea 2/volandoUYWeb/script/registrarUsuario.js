// Mostrar u ocultar campos según tipo y deshabilitar los no usados
function toggleCampos(tipo) {
  const cliente = document.getElementById("campos-cliente");
  const aerolinea = document.getElementById("campos-aerolinea");

  cliente.style.display = tipo === "cliente" ? "block" : "none";
  aerolinea.style.display = tipo === "aerolinea" ? "block" : "none";

  [...cliente.querySelectorAll("input, select")].forEach(el => el.disabled = tipo !== "cliente");
  [...aerolinea.querySelectorAll("input, textarea")].forEach(el => el.disabled = tipo !== "aerolinea");
}

document.getElementById("tipo").addEventListener("change", function () {
  toggleCampos(this.value);
});

// Mostrar y limpiar errores
function mostrarError(idCampo, mensaje) {
  const campo = document.getElementById(idCampo);
  const error = campo.parentElement.querySelector(".error-msg");
  campo.classList.add("input-error");
  if (error) error.textContent = mensaje;
}

function limpiarError(idCampo) {
  const campo = document.getElementById(idCampo);
  const error = campo.parentElement.querySelector(".error-msg");
  campo.classList.remove("input-error");
  if (error) error.textContent = "";
}

// Validar cliente
function validarClienteJS() {
  const campos = ["nickname", "nombre", "apellido", "correo", "nacionalidad", "numeroDocumento", "fechaNacimiento", "tipoDocumento"];
  campos.forEach(limpiarError);

  const nick = document.getElementById("nickname").value.trim();
  const nombre = document.getElementById("nombre").value.trim();
  const apellido = document.getElementById("apellido").value.trim();
  const correo = document.getElementById("correo").value.trim();
  const nacionalidad = document.getElementById("nacionalidad").value.trim();
  const tipoDoc = document.getElementById("tipoDocumento").value;
  const numeroDoc = document.getElementById("numeroDocumento").value.trim();
  const fechaNac = document.getElementById("fechaNacimiento").value;

  let valido = true;

  if (!fechaNac || new Date(fechaNac) > new Date()) {
    mostrarError("fechaNacimiento", "Ingrese una fecha de nacimiento válida.");
    valido = false;
  }

  if (!nick || nick.length < 4) {
    mostrarError("nickname", "El nickname debe tener al menos 4 caracteres.");
    valido = false;
  }

  const soloLetras = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/;
  if (!nombre || !soloLetras.test(nombre)) {
    mostrarError("nombre", "El nombre solo puede contener letras y espacios.");
    valido = false;
  }
  if (!apellido || !soloLetras.test(apellido)) {
    mostrarError("apellido", "El apellido solo puede contener letras y espacios.");
    valido = false;
  }
  if (!nacionalidad || !soloLetras.test(nacionalidad)) {
    mostrarError("nacionalidad", "La nacionalidad solo puede contener letras.");
    valido = false;
  }

  const regexCorreo = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,6}$/;
  if (!correo || !regexCorreo.test(correo)) {
    mostrarError("correo", "Ingrese un correo electrónico válido.");
    valido = false;
  }

  if (!numeroDoc) {
    mostrarError("numeroDocumento", "Ingrese el número de documento.");
    valido = false;
  } else {
    switch (tipoDoc) {
      case "CI":
        if (!numeroDoc.match(/^[0-9]{7,8}$/) && !numeroDoc.match(/^[0-9]{1,2}\.[0-9]{3}\.[0-9]{3}-[0-9]$/)) {
          mostrarError("numeroDocumento", "CI inválida (ej: 12345678 o 1.234.567-8).");
          valido = false;
        }
        break;
      case "Pasaporte":
        if (!numeroDoc.match(/^[A-Z0-9]{6,9}$/)) {
          mostrarError("numeroDocumento", "Pasaporte inválido (ej: A1234567).");
          valido = false;
        }
        break;
      case "DNI":
        if (!numeroDoc.match(/^[0-9]{7,8}[A-Za-z]?$/)) {
          mostrarError("numeroDocumento", "DNI inválido (ej: 12345678 o 12345678A).");
          valido = false;
        }
        break;
      default:
        mostrarError("tipoDocumento", "Seleccione un tipo de documento válido.");
        valido = false;
    }
  }

  return valido;
}

// Validar aerolínea
function validarAerolineaJS() {
  const campos = ["nickname", "nombre", "correo", "linkSitioWeb", "descripcion"];
  campos.forEach(limpiarError);

  const nick = document.getElementById("nickname").value.trim();
  const nombre = document.getElementById("nombre").value.trim();
  const correo = document.getElementById("correo").value.trim();
  const sitioWeb = document.getElementById("linkSitioWeb").value.trim();
  const descripcion = document.getElementById("descripcion").value.trim();

  let valido = true;

  if (!nick || nick.length < 4) {
    mostrarError("nickname", "El nickname debe tener al menos 4 caracteres.");
    valido = false;
  }

  if (!nombre || nombre.length < 4 || !nombre.match(/^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/)) {
    mostrarError("nombre", "El nombre debe tener al menos 4 caracteres y solo letras.");
    valido = false;
  }

  const regexCorreo = /^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
  if (!correo || !regexCorreo.test(correo)) {
    mostrarError("correo", "El correo electrónico no es válido.");
    valido = false;
  }

  if (!sitioWeb || !sitioWeb.match(/.*\.[a-zA-Z]{2,}$/)) {
    mostrarError("linkSitioWeb", "El sitio web debe tener un dominio válido (.com, .ar, etc).");
    valido = false;
  }

  if (!descripcion || descripcion.length < 10) {
    mostrarError("descripcion", "La descripción debe tener al menos 10 caracteres.");
    valido = false;
  }

  return valido;
}

// Construir DTO
function construirDTO() {
  const tipo = document.getElementById("tipo").value;
  if (tipo === "cliente") {
    return {
      tipo: "cliente",
      nickname: document.getElementById("nickname").value.trim(),
      nombre: document.getElementById("nombre").value.trim(),
      correo: document.getElementById("correo").value.trim(),
      apellido: document.getElementById("apellido").value.trim(),
      tipoDocumento: document.getElementById("tipoDocumento").value,
      numeroDocumento: document.getElementById("numeroDocumento").value.trim(),
      fechaNacimiento: document.getElementById("fechaNacimiento").value,
      nacionalidad: document.getElementById("nacionalidad").value.trim()
    };
  } else if (tipo === "aerolinea") {
    return {
      tipo: "aerolinea",
      nickname: document.getElementById("nickname").value.trim(),
      nombre: document.getElementById("nombre").value.trim(),
      correo: document.getElementById("correo").value.trim(),
      descripcion: document.getElementById("descripcion").value.trim(),
      linkSitioWeb: document.getElementById("linkSitioWeb").value.trim()
    };
  }
  return null;
}

// Submit del formulario
document.getElementById("form-registro").addEventListener("submit", function (e) {
  e.preventDefault();
  const tipo = document.getElementById("tipo").value;
  const valido = tipo === "cliente" ? validarClienteJS() : validarAerolineaJS();
  if (valido) {
    const dto = construirDTO();
    console.log("DTO generado:", dto);
    alert("Formulario válido.");
  }
});