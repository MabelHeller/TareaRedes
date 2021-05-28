/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelos.Usuario;

/**
 *
 * @author Heller
 */
public class Consultas {
    private final String tabla = "Usuario";
    
    public void guardar(Connection conexion, Usuario usuario) throws SQLException{
        try{
            PreparedStatement consulta;
            if(usuario.getId()== null){
                consulta = conexion.prepareStatement("INSERT INTO " + this.tabla + "(nombreUsuario,contrasena) VALUES(?, ?)");
                consulta.setString(1, usuario.getNombreUsuario());
                consulta.setString(2, usuario.getContrasena());
            }else{
                consulta = conexion.prepareStatement("UPDATE " + this.tabla + " SET nombreUsuario = ?, contrasena = ?, imagen = ?, extension = ? WHERE id= ?");
                consulta.setString(1, usuario.getNombreUsuario());
                consulta.setString(2, usuario.getContrasena());
                consulta.setString(3, usuario.getImagen());
                consulta.setString(4, usuario.getExtension());
            }
            consulta.executeUpdate();
        }catch(SQLException ex){
            throw new SQLException(ex);
        }
    }
    
    public Usuario recuperarPorNombreUsuario(Connection conexion, String nombreUsuario) throws SQLException {
        Usuario usuario = null;
        try{
            PreparedStatement consulta = conexion.prepareStatement("SELECT imagen, extension FROM " + this.tabla + " WHERE nombreUsuario = ?" );
            consulta.setString(1, nombreUsuario);
            ResultSet resultado = consulta.executeQuery();
            while(resultado.next()){
                usuario = new Usuario(nombreUsuario, resultado.getString("imagen"), resultado.getString("extension"));
            }
        }catch(SQLException ex){
            throw new SQLException(ex);
        }
        return usuario;
    }
    
}