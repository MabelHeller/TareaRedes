/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author Heller
 */
public class Usuario {
    private Integer id;
    private String nombreUsuario;
    private String contrasena;
    private String imagen;
    private String extension;
    
    public Usuario() {
        this.id = 0;
        this.nombreUsuario = "";
        this.contrasena = "";
        this.imagen = "";
        this.extension = "";
    }

    public Usuario(Integer id, String nombreUsuario, String contrasena, String imagen, String extension) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.imagen = imagen;
        this.extension = extension;
    }
    
    public Usuario(String nombreUsuario, String imagen, String extension) {
        this.nombreUsuario = nombreUsuario;
        this.imagen = imagen;
        this.extension = extension;
    }

    public Integer getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getImagen() {
        return imagen;
    }
    
    public String getExtension() {
        return extension;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "Tarea{" + "id=" + id + ", nombreUsuario=" + nombreUsuario + ", contraseña=" + contrasena + ", imagen=" + imagen + "extension"+ extension+'}';
    }
    
}