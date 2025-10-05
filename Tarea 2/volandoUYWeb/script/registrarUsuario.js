
document.getElementById("tipo").addEventListener("change", function () {
  const tipo = this.value;
  document.getElementById("campos-cliente").style.display = tipo === "cliente" ? "block" : "none";
  document.getElementById("campos-aerolinea").style.display = tipo === "aerolinea" ? "block" : "none";
});