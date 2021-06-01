/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author Heller & Maikel
 */
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelos.Usuario;
import servicios.Conexion;
import servicios.Consultas;

public class MyServer extends Thread {

    private Socket socket;
    private String action;
       
    Consultas consultas= new Consultas();
    
    public MyServer(Socket socket) {
        this.socket = socket;
        this.action = "";
    } // constructor

    @Override
    public void run() {
        try {
            DataOutputStream dos = new DataOutputStream(this.socket.getOutputStream());
            DataInputStream dis = new DataInputStream(this.socket.getInputStream());
            this.action = dis.readUTF();
            switch (this.action) {
                case "login":                   
                    String nombreUsuario = dis.readUTF();
                    String contrasena = dis.readUTF();
                    Conexion conexion=new Conexion();
                    Usuario usuario=new Usuario(nombreUsuario, contrasena);
                    if (consultas.Login(conexion.obtener(), usuario)){
                        dos.writeBoolean(true);
                    }else{
                        dos.writeBoolean(false);
                    }                    
                                        
                    break;
                case "save-image":
                    String directoryPath = "ServerImages/"+dis.readUTF()+"/";
                    File file = new File(directoryPath);
                    if (file.isDirectory()) {
                        System.out.println("File is a Directory");
                    } else {
                        boolean dirCreated = file.mkdir();
                        System.out.println(directoryPath +"created? "+ dirCreated);
                    }
                    InputStream inputStream = socket.getInputStream();
                    int rows = Integer.parseInt(dis.readUTF());
                    int cols = Integer.parseInt(dis.readUTF());
                    System.out.println(rows + " " + cols);
                    BufferedImage[] buffImages = new BufferedImage[rows*cols];

                    for (int i = 0; i < (rows*cols); i++) {
                        byte[] imageAr = new byte[62100];
                        inputStream.read(imageAr);
                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                        System.out.println("Received " + image.getHeight() + "x" + image.getWidth());
                        buffImages[i] = image;
                    }
                    
                    
                    int type = buffImages[0].getType();
                    int chunkWidth = buffImages[0].getWidth();
                    int chunkHeight = buffImages[0].getHeight();
                    
                    //Initializing the final image
                    BufferedImage finalImg = new BufferedImage(chunkWidth * cols, chunkHeight * rows, type);

                    int num = 0;
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            finalImg.createGraphics().drawImage(buffImages[num], chunkWidth * j, chunkHeight * i, null);
                            num++;
                        }
                    }
                    System.out.println("Image concatenated.....");
                    ImageIO.write(finalImg, "jpeg", new File(directoryPath+"finalImg.jpg"));

                    inputStream.close();
                    
                    break;
                default:
                    break;
            }
            socket.close();

        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (SQLException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void login(){
        
        
        
    }
    
}
