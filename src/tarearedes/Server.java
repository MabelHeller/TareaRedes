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

            BufferedImage[] buffImages = new BufferedImage[16];

            for (int i = 0; i < 16; i++) {
                byte[] imageAr = new byte[62100];
                inputStream.read(imageAr);
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
//                ImageIO.write(image, "jpg", new File("fnl+" + i + ".jpg"));
                System.out.println("Received " + image.getHeight() + "x" + image.getWidth());
                buffImages[i]=image;
            }
            
            int type = buffImages[0].getType();
            int chunkWidth = buffImages[0].getWidth();
            int chunkHeight = buffImages[0].getHeight();
            int rows = 4;
            int cols = 4;
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
            ImageIO.write(finalImg, "jpeg", new File("finalImg.jpg"));

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
