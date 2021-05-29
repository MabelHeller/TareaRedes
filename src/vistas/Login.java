/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistas;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelos.Usuario;
import servicios.Conexion;
import servicios.Consultas;

/**
 *
 * @author Heller
 */
public class Login extends javax.swing.JFrame {

    private final Consultas consultas = new Consultas();
    private final Usuario usuario;

    public Login() {
        this.usuario = new Usuario();
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public Login(Usuario usuario) {
        this.usuario = usuario;
        initComponents();
        jtfnombreUsuario.setText(this.usuario.getNombreUsuario());
        jtfContrasena.setText(this.usuario.getContrasena());
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtfnombreUsuario = new javax.swing.JTextField();
        jbtRegistrarse = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jtfContrasena = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setText("Nombre Usuario");

        jLabel4.setText("Contraseña");

        jbtRegistrarse.setText("Registrarse");
        jbtRegistrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtRegistrarseActionPerformed(evt);
            }
        });

        jButton2.setText("Iniciar Sesión");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtRegistrarse)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton2)
                            .addComponent(jtfnombreUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(jtfContrasena))))
                .addContainerGap(80, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtfnombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtfContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtRegistrarse)
                    .addComponent(jButton2))
                .addContainerGap(98, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtRegistrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtRegistrarseActionPerformed
        guardar();        
    }//GEN-LAST:event_jbtRegistrarseActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        login();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton jbtRegistrarse;
    private javax.swing.JPasswordField jtfContrasena;
    private javax.swing.JTextField jtfnombreUsuario;
    // End of variables declaration//GEN-END:variables

    
    private void guardar() {
        int resultado=0;
        String nombreUsuario = jtfnombreUsuario.getText();       
        String contrasena = jtfContrasena.getText();
        this.usuario.setNombreUsuario(nombreUsuario);
        this.usuario.setContrasena(contrasena);
        try {
            resultado=this.consultas.registrarUsuario(Conexion.obtener(), this.usuario);
            if (resultado>0){
                this.jtfnombreUsuario.setText("");
                this.jtfContrasena.setText("");
                JOptionPane.showMessageDialog(this, "Se ha guardado exitosamente");                
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido guardar el registro.");
        }
    }
    
    
    private void login() {
        boolean resultado;
        String nombreUsuario = jtfnombreUsuario.getText();       
        String contrasena = jtfContrasena.getText();
        this.usuario.setNombreUsuario(nombreUsuario);
        this.usuario.setContrasena(contrasena);
        try {
            if (this.consultas.Login(Conexion.obtener(), this.usuario)){                
                this.jtfnombreUsuario.setText("");
                this.jtfContrasena.setText("");
                this.dispose();
                Client client = new Client();
                client.setVisible(true);
                client.setLocationRelativeTo(null);
            }else {
                JOptionPane.showMessageDialog(this, "Las credenciales no son correctas");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido iniciar sesión el registro.");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(this, "Ha surgido un error y no se ha podido iniciar sesión el registro.");
        }
    }
}
