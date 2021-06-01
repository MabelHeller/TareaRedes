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
import javax.swing.JTextField;
import vistas.Client;

public class ClientRetrieveImg extends Thread implements Runnable {

    private final String imgName;
    private final String imgPath;
    private final JTextField jtf;
    private final String userName;
    
    public ClientRetrieveImg(String name, String path, String user,JTextField jt) {
        this.imgName = name;
        this.imgPath = path;
        this.jtf = jt;
        this.userName = user;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("localhost", utilities.Constants.socketPortNumber);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
               
                dos.writeUTF("get-image");
                dos.writeUTF(this.imgName);
                dos.writeUTF(this.userName);
                int rows = dis.readInt();
                int cols = dis.readInt();
                InputStream inputStream = socket.getInputStream();

                BufferedImage[] buffImages = new BufferedImage[rows * cols];

                for (int i = 0; i < (rows * cols); i++) {
                    byte[] imageAr = new byte[62100];
                    inputStream.read(imageAr);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                    buffImages[i] = image;
                    if (!dis.readBoolean()) {
                        dos.writeBoolean(false);
                        break;
                    } else {
                        dos.writeBoolean(true);
                    }
                }
                dos.writeBoolean(true);
                int type = buffImages[0].getType();
                int chunkWidth = buffImages[0].getWidth();
                int chunkHeight = buffImages[0].getHeight();

                BufferedImage finalImg = new BufferedImage(chunkWidth * cols, chunkHeight * rows, type);

                int num = 0;
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        finalImg.createGraphics().drawImage(buffImages[num], chunkWidth * j, chunkHeight * i, null);
                        num++;
                    }
                }
                String ext = imgName.substring(imgName.lastIndexOf(".") + 1, imgName.length());
                ImageIO.write(finalImg, ext, new File(imgPath + "/" + imgName));

                inputStream.close();
                socket.close();
                jtf.setText("Guardada en: " + imgPath + "\\" + imgName);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}
