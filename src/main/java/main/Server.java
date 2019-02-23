package main;

import HTTPcommunication.ClientHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
//This class runs the server
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
    //This will run forever until manually shut down
    public void startServer() {
            try {
                while (true) {
                    System.out.println(new Date().toString());
                    System.out.println("Server is open, scanning for requests at port " + port + "...");

                    socket = serverSocket.accept(); //Waits for connection

                    System.out.println("Connection detected!");

                    PrintWriter outChar = new PrintWriter(socket.getOutputStream());
                    BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //Send the newly created socket to ClientHandler, which is a thread
                    ClientHandler clientHandler = new ClientHandler(socket, outChar, out, in);
                    clientHandler.start(); //Start the new thread

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
