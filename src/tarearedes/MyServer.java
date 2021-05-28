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

public class MyServer extends Thread {

    private Socket socket;
    private String action;

    public MyServer(Socket socket) {
        this.socket = socket;
        this.action = "";
    } // constructor

    @Override
    public void run() {
        try {
            DataOutputStream send = new DataOutputStream(this.socket.getOutputStream());
            DataInputStream receive = new DataInputStream(this.socket.getInputStream());
            this.action = receive.readUTF();
            switch (this.action) {
                case "action":
                    System.out.println(this.action);
                    break;
                case "save-image":
                    InputStream inputStream = socket.getInputStream();

                    BufferedImage[] buffImages = new BufferedImage[16];

                    for (int i = 0; i < 16; i++) {
                        byte[] imageAr = new byte[62100];
                        inputStream.read(imageAr);
                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
//                ImageIO.write(image, "jpg", new File("fnl+" + i + ".jpg"));
                        System.out.println("Received " + image.getHeight() + "x" + image.getWidth());
                        buffImages[i] = image;
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
                    socket.close();
                    break;
                default:
                    break;
            }

        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
