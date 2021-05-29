/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

/**
 *
 * @author Heller & Maikel
 */
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", utilities.Constants.socketPortNumber);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            
            dos.writeUTF("save-image");
            dos.writeUTF("Maikel");
            
            OutputStream outputStream = socket.getOutputStream();
            BufferedImage image = ImageIO.read(new File("C:\\Users\\Maikel\\Pictures\\kirby.jpg"));
           

            int rows =(int)(4 + (Math.random()* 10)); 
            int cols = (int)(4 + (Math.random()* 10)); 
            int chunks = rows * cols;
            System.out.println(rows + " "+ cols);
            int chunkWidth = image.getWidth() / cols; // determines the chunk width and height
            int chunkHeight = image.getHeight() / rows;
            int count = 0;
            BufferedImage imgs[] = new BufferedImage[chunks]; 
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    
                    imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
                    Graphics2D gr = imgs[count].createGraphics();
                    gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                    gr.dispose();
                    count++;
                }
            }
            System.out.println("Splitin done");
            dos.writeUTF(String.valueOf(rows));
            dos.writeUTF(String.valueOf(cols));
            for (int i = 0; i < chunks; i++) {
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(imgs[i], "jpg", byteArrayOutputStream);
                outputStream.write(byteArrayOutputStream.toByteArray());
                Thread.sleep(100);
            }
            
            
            outputStream.close();
            socket.close();
        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
