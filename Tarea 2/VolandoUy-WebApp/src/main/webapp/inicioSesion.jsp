<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="login-container">
  <div class="login-box">
    <h1>
      <img src="${pageContext.request.contextPath}/img/logoAvionSolo.png" alt="VolandoUY" class="logo-img">
      VolandoUY
    </h1>
    <h2>Iniciar Sesión</h2>
    
    <form id="login-form" action="${pageContext.request.contextPath}/login" method="post">
      <div class="input-group">
        <label for="email">Correo Electrónico</label>
        <input type="email" id="email" name="email" placeholder="tu@email.com" required>
      </div>
      
      <div class="input-group">
        <label for="password">Contraseña</label>
        <input type="password" id="password" name="password" placeholder="Tu contraseña" required>
      </div>
      
      <button type="submit" class="btn-login">
        <i class="fas fa-sign-in-alt"></i> Iniciar Sesión
      </button>
    </form>
    
    <div class="extra-links">
      <a href="#" data-vista="registrarUsuario.jsp">¿No tienes cuenta? Regístrate</a>
      <a href="#">¿Olvidaste tu contraseña?</a>
    </div>
  </div>
</div>

<script>
document.getElementById('login-form').addEventListener('submit', function(e) {
  e.preventDefault();
  
  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;
  const submitBtn = document.querySelector('.btn-login');
  
  // Mostrar estado de carga
  submitBtn.classList.add('loading');
  submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Iniciando sesión...';
  
  // Simular autenticación (aquí iría la llamada al servidor)
  setTimeout(() => {
    if (email && password) {
      // Redirigir al inicio después del login exitoso
      window.location.href = '${pageContext.request.contextPath}/inicio.jsp';
    } else {
      alert('Por favor, completa todos los campos');
      submitBtn.classList.remove('loading');
      submitBtn.innerHTML = '<i class="fas fa-sign-in-alt"></i> Iniciar Sesión';
    }
  }, 1500);
});
</script>
