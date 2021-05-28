/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarearedes;

/**
 *
 * @author Heller & Maikel
 */
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class Server {

    public static void main(String[] args) {
        int port = 2050;
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            Socket clientSocket = serverSocket.accept();

            InputStream inputStream = clientSocket.getInputStream();

            

            for (int i = 0; i < 15; i++) {

                byte[] imageAr = new byte[62100];
                inputStream.read(imageAr);

                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                System.out.println("Received " + image.getHeight() + "x" + image.getWidth() );
                ImageIO.write(image, "jpg", new File("fnl+"+i+".jpg"));
            }

            inputStream.close();
            clientSocket.close();
            serverSocket.close();
        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
