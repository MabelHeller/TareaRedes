/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarearedes;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maikel
 */
public class Server  {
    public static void main(String[] args) {
        try {
            System.out.println("Server Active... Waiting Client");
            ServerSocket mainServer = new ServerSocket(utilities.Constants.socketPortNumber);
            do {
                new MyServer(mainServer.accept()).start();
            } while (true);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // main
}
