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
import javax.swing.JOptionPane;
import modelos.Usuario;

/**
 *
 * @author Heller & Maikel
 */
public class Consultas {

    private final String tabla = "Usuario";

    public int registrarUsuario(Connection conexion, Usuario usuario) throws SQLException {   
        int resultado=0;
        try {
            PreparedStatement consulta;
            consulta = conexion.prepareStatement("INSERT INTO Usuario(nombreUsuario,contrasena) VALUES(?,?);");
            consulta.setString(1, usuario.getNombreUsuario());
            consulta.setString(2, usuario.getContrasena());
            resultado=consulta.executeUpdate();            
            consulta.close();
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
       return resultado;
    }
    
      
    public boolean Login(Connection conexion, Usuario usuario) throws SQLException {
        PreparedStatement consulta=null;
        try{
            consulta = conexion.prepareStatement("SELECT nombreUsuario, contrasena FROM Usuario WHERE nombreUsuario = ? and contrasena= ?" );
            consulta.setString(1, usuario.getNombreUsuario());
            consulta.setString(2, usuario.getContrasena());
            ResultSet resultado = consulta.executeQuery();
            if(resultado.next()){
                consulta.close();
                return true;
            }
        }catch(SQLException ex){
            throw new SQLException(ex);
        }
        consulta.close();
        return false;
    }
}
