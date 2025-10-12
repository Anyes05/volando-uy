<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<section class="search-box">
  <h2>Modificar Usuario</h2>
  <form id="filtro-modificar-usuarios">
    <div class="filtro-row">
      <label>Tipo de usuario:</label>
      <select id="modificar-select-tipo">
        <option value="">Todos</option>
        <option value="aerolinea">Aerolínea</option>
        <option value="cliente">Cliente</option>
      </select>
    </div>
    <div class="filtro-row">
      <label>Buscar por nombre o nickname:</label>
      <input type="text" id="modificar-buscador-nombre" placeholder="Ej: zfly, Juan Pérez, LATAM">
    </div>
  </form>
</section>
<div id="modificar-lista-usuarios"></div>
<section class="modificar-usuario-box" id="modificar-form-section" style="display:none;">
  <h2>Modificar Perfil</h2>
  <form id="form-modificar-usuario" enctype="multipart/form-data">
    <div class="usuario-header">
      <img id="modificar-usuario-imagen" src="" alt="Imagen del usuario">
      <div class="usuario-info">
        <p><strong>Nickname:</strong> <span id="modificar-nickname"></span></p>
        <p><strong>Email:</strong> <span id="modificar-email"></span></p>
      </div>
    </div>
    <div class="form-row">
      <label for="modificar-nombre">Nombre:</label>
      <input type="text" id="modificar-nombre" name="nombre" required>
    </div>
    <div class="form-row">
      <label for="modificar-sitio">Sitio web:</label>
      <input type="url" id="modificar-sitio" name="sitioWeb" placeholder="https://">
    </div>
    <div class="form-row">
      <label for="modificar-descripcion">Descripción:</label>
      <textarea id="modificar-descripcion" name="descripcion" rows="3"></textarea>
    </div>
    <div class="form-row">
      <label for="modificar-imagen">Imagen de perfil:</label>
      <input type="file" id="modificar-imagen" name="imagen" accept="image/*">
    </div>
    <div class="form-actions">
      <button type="submit" class="btn-guardar">Guardar cambios</button>
      <button type="button" class="btn-cancelar" id="cancelar-modificacion">Cancelar</button>
    </div>
  </form>
  <div id="mensaje-modificar-usuario" class="mensaje-usuario" style="display:none;"></div>
</section>
