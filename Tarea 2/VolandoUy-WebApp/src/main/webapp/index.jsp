<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.volandouy.helper.DeviceDetector" %>
<%
  // Detectar dispositivo y determinar qué contenido mostrar
  // En móvil, si no hay usuario logueado, mostrar inicio de sesión directamente
  boolean isMobilePhone = DeviceDetector.isMobilePhone(request);
  boolean usuarioLogueado = session.getAttribute("usuarioLogueado") != null;
  
  String contenido = "inicio-content.jsp";
  if (isMobilePhone && !usuarioLogueado) {
    contenido = "inicioSesion-content.jsp";
  }
%>
<jsp:include page="layout.jsp">
  <jsp:param name="content" value="<%=contenido%>" />
</jsp:include>
