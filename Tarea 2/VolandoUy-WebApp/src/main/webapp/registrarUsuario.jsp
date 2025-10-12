<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
