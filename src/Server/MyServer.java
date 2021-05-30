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
import Client.ClientSendImg;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

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

            System.out.println("Waiting for action...");
            this.action = receive.readUTF();
            System.out.println("Action recieve:" + action);
            switch (this.action) {
                case "save-user":
                    break;
                case "save-image":
                    saveImage(receive,send);
                    break;
                case "get-image":
                    sendImage(receive, send);
                    break;
                case "directory":
                    getDirectory(receive);
                    break;
                default:

                    break;
            }
            send.close();
            receive.close();
            this.socket.close();

        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void saveImage(DataInputStream recieve,DataOutputStream dos) {
        String directoryPath;
        try {
            directoryPath = "ServerImages/" + recieve.readUTF() + "/";

            File file = new File(directoryPath);
            if (!file.isDirectory()) {
                file.mkdir();
             
            }
            InputStream inputStream = socket.getInputStream();

            int rows = Integer.parseInt(recieve.readUTF());
            int cols = Integer.parseInt(recieve.readUTF());
            String imgName = recieve.readUTF();

            BufferedImage[] buffImages = new BufferedImage[rows * cols];

            for (int i = 0; i < (rows * cols); i++) {
                byte[] imageAr = new byte[62100];
                inputStream.read(imageAr);
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                buffImages[i] = image;
                if(!recieve.readBoolean()){
                        dos.writeBoolean(false);
                        break;
                    }else{  
                        dos.writeBoolean(true);
                    }
            }

            int type = buffImages[0].getType();
            System.out.println(type);
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
            String ext = imgName.substring(imgName.lastIndexOf(".") + 1, imgName.length());
            System.out.println("Image concatenated in..."+ directoryPath);
            ImageIO.write(finalImg, ext, new File(directoryPath + imgName));
            dos.writeBoolean(true);
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getDirectory(DataInputStream recieve) {
        try {
            String imgs[] = new File("ServerImages/Maikel/").list();
            try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
                oos.writeObject(imgs);
            }
            
        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void sendImage(DataInputStream recieve, DataOutputStream dos) {
        String directoryPath;
        try {
            String imgName = recieve.readUTF();
            directoryPath = "ServerImages/Maikel/" + imgName;
            File file = new File(directoryPath);
            if (file.isFile()) {
                try (OutputStream outputStream = socket.getOutputStream()) {
                    BufferedImage image = ImageIO.read(new File(directoryPath));
                    int rows = (int) (4 + (Math.random() * 10));
                    int cols = (int) (4 + (Math.random() * 10));
                    int chunks = rows * cols;

                    System.out.println(rows + " " + cols);
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
                    System.out.println("Server Spliting done");
                    dos.writeInt(rows);
                    dos.writeInt(cols);
                    String ext = imgName.substring(imgName.lastIndexOf(".") + 1, imgName.length());
                    for (int i = 0; i < chunks; i++) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ImageIO.write(imgs[i], ext, byteArrayOutputStream);
                        outputStream.write(byteArrayOutputStream.toByteArray());
                        dos.writeBoolean(true);
                        if(!recieve.readBoolean()){
                            break;
                        }
                    }
                }
            } else {
                System.out.println(directoryPath + "Not exists");
            }
            socket.close();
        } catch (UnknownHostException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
