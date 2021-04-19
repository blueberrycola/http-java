/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.http.java;
import java.util.ArrayList;
import java.net.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author chase-pc
 */

public class HTTPServer {
    private boolean running = false;
    private ServerSocket server_sock;

    public static void main(String args[]) throws IOException {
        HTTPServer cs = new HTTPServer();
        cs.startServer();
        
        
    }
    public void startServer() throws IOException {
        
        try {
           server_sock = new ServerSocket(32000);
           System.out.println("Server Socket created on port 32000");
           
           //Accept clients
           System.out.println("Listening for clients...");
           Socket client = server_sock.accept();
           System.out.println("Client Connected:" + client.getInetAddress().getHostAddress());
           System.out.println("Creating input and output for server");
           
//Start thread of client_handler class
           ClientHandler client_sock = new ClientHandler(client);
           new Thread(client_sock).start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(server_sock != null) {
                server_sock.close();
            }
        }
    }
     //MULTITHREADED METHODS
     class ClientHandler implements Runnable {
        //Socket that sends to the client
         private final Socket client_sock;
         private BufferedReader in;
         private PrintWriter out;
        
        
        public ClientHandler(Socket socket) throws IOException {
            this.client_sock = socket;
            in = new BufferedReader(new InputStreamReader(client_sock.getInputStream()));
            out = new PrintWriter(client_sock.getOutputStream());
            
        }
        @Override
        public void run() {
            System.out.println( "Received a connection" );
            try {
                //while(true){
                    String req = in.readLine();
                    System.out.println(req);
                //}
            } catch (IOException e) {
                System.err.println(e.getStackTrace());
            } finally {
                out.close();
                try {
                    in.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                
            }
            
            
            
            
            
            
            
            
            
            
        }
        
    }
    
}
