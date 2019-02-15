package main;

import HTTPcommunication.Client;

import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private int port;

    public Server(int port){
        if(port > 0) {
            try {
                this.port = port;
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{  //Kanske använd ett annat exception, eller skita i exceptionet?
            throw new IllegalArgumentException();
        }
    }

    public void startServer() throws IOException {

                while(true) {
                    System.out.println("Server is open, scanning for requests at port " + port + "...");

                    socket = serverSocket.accept();

                    System.out.println("Connection detected!");

                    PrintWriter outChar = new PrintWriter(socket.getOutputStream());
                    BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    Client client = new Client(socket, outChar, out, in);
                    client.start();

                    System.out.println(client.getName());
                }

    }

}
