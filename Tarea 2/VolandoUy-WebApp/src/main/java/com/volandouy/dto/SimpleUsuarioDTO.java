package com.volandouy.dto;

import java.util.Base64;

public class SimpleUsuarioDTO {
    private String nickname;
    private String nombre;
    private String correo;
    private String foto; // Base64 encoded
    private boolean esCliente;
    
    // Campos específicos de Cliente
    private String apellido;
    private String tipoDocumento;
    private String numeroDocumento;
    private SimpleFechaDTO fechaNac;
    private String nacionalidad;
    
    // Campos específicos de Aerolínea
    private String descripcion;
    private String linkSitioWeb;

    // Constructor vacío
    public SimpleUsuarioDTO() {}

    // Constructor para datos básicos
    public SimpleUsuarioDTO(String nickname, String nombre, String correo, byte[] fotoBytes, boolean esCliente) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.correo = correo;
        this.foto = fotoBytes != null ? Base64.getEncoder().encodeToString(fotoBytes) : null;
        this.esCliente = esCliente;
    }

    // Getters y Setters
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public boolean isEsCliente() { return esCliente; }
    public void setEsCliente(boolean esCliente) { this.esCliente = esCliente; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public SimpleFechaDTO getFechaNac() { return fechaNac; }
    public void setFechaNac(SimpleFechaDTO fechaNac) { this.fechaNac = fechaNac; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getLinkSitioWeb() { return linkSitioWeb; }
    public void setLinkSitioWeb(String linkSitioWeb) { this.linkSitioWeb = linkSitioWeb; }

    public static class SimpleFechaDTO {
        private int dia;
        private int mes;
        private int ano;

        public SimpleFechaDTO() {}

        public SimpleFechaDTO(int dia, int mes, int ano) {
            this.dia = dia;
            this.mes = mes;
            this.ano = ano;
        }

        public int getDia() { return dia; }
        public void setDia(int dia) { this.dia = dia; }

        public int getMes() { return mes; }
        public void setMes(int mes) { this.mes = mes; }

        public int getAno() { return ano; }
        public void setAno(int ano) { this.ano = ano; }
    }
}