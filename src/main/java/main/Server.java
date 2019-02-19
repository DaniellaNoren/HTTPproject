package main;

import HTTPcommunication.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

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
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void startServer() {
            try {
                while (true) {
                    System.out.println(new Date().toString());
                    System.out.println("Server is open, scanning for requests at port " + port + "...");

                    socket = serverSocket.accept();

                    System.out.println("Connection detected!");

                    PrintWriter outChar = new PrintWriter(socket.getOutputStream());
                    BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    Client client = new Client(socket, outChar, out, in);
                    client.start();

                }
            }catch(IOException e){
                    e.getMessage();
            }finally{
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


    }

}
