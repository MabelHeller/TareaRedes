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
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class Client {

    public static void main(String[] args) {
        int port = 2050;
        try {
            Socket clientSocket = new Socket("localhost", port);

            OutputStream outputStream = clientSocket.getOutputStream();
            BufferedImage image = ImageIO.read(new File("C:\\Users\\Maikel\\Pictures\\kirby.jpg"));
           

            int rows = 4; //You should decide the values for rows and cols variables
            int cols = 4;
            int chunks = rows * cols;

            int chunkWidth = image.getWidth() / cols; // determines the chunk width and height
            int chunkHeight = image.getHeight() / rows;
            int count = 0;
            BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks
            for (int x = 0; x < rows; x++) {
                for (int y = 0; y < cols; y++) {
                    //Initialize the image array with image chunks
                    imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                    // draws the image chunk
                    Graphics2D gr = imgs[count].createGraphics();
                    gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                    gr.dispose();
                    count++;
                }
            }
            System.out.println("Splitin done");
            for (int i = 0; i < chunks; i++) {
                 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(imgs[i], "jpg", byteArrayOutputStream);
                outputStream.write(byteArrayOutputStream.toByteArray());
                //ImageIO.write(imgs[i], "jpg", new File("‪fnnnl+"+i+".jpg"));
            //Thread.sleep(2000);
            }



            outputStream.close();
            clientSocket.close();
        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
