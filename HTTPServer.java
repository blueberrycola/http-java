/*
 * TO DO:
 * ENABLE MULTITHREADING X
 * CHROME CAN SEND REQUEST X
 * SERVER CAN PARSE REQUEST X
 * SERVER CAN OPEN FILE X
 * SERVER CAN SEND HTTP FILE TO CHROME CLIENT
 * IF SERVER CAN SEND FILE ALSO SEND 200 IN ITS HEADER FILE
 * SERVER CAN SEND 404 FOR UNAVAILABLE FILE
 */
package com.mycompany.http.java;
import java.net.*;
import java.io.*;
import java.util.Scanner;

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
           while(true) {
               //Accept clients
           System.out.println("Listening for clients...");
           Socket client = server_sock.accept();
           System.out.println("Client Connected:" + client.getInetAddress().getHostAddress());
           System.out.println("Creating input and output for server");
           //Start thread of client_handler class
           ClientHandler client_sock = new ClientHandler(client);
           new Thread(client_sock).start();
           }
           
           

           
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
                    
                    //Takes chrome get request and prints it
                    String req = in.readLine();
                    System.out.println(req);
                    //params 0: command, param[1]: path, param[2] type
                    String[] params = req.split(" ");
                    
                    String s = System.getProperty("user.dir");
                    if(params[0].contains("GET")) {
                        
                        //Open file
                        String path = "C:\\Users\\chase-pc\\Documents\\GitHub\\http-java";
                        String newstr = params[1].replace("/", "\\");
                        
                        path += newstr;
                        System.out.println(path);
                        File fp = new File(path);
                        String code = "";
                        String htmlbody = "";
                        //200 OK
                        if(fp.exists()) {
                            System.out.println("File found!");
                            code = "200 OK";
                            //Append html body from fp
                            Scanner scnr = new Scanner(fp);
                            while(scnr.hasNextLine()) {
                                htmlbody += scnr.nextLine();
                            }
                        //404 File not found
                        } else {
                            System.out.println("Error File not found");
                            code = "404 Not Found";
                            htmlbody = "<h1>404 File not found</h1>";
                        }
                        int size = htmlbody.length();
                        //Send back responce to browser
                        out.println("HTTP/1.0 " + code);
                        out.println("Content-Length: " + size);
                        out.println(htmlbody);
                        
                        out.flush();
                        System.out.println("Responce sent");
                        
                        //Build chrome header with html file
                        
                    }
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
