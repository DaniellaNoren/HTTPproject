package main;

import HTTPcommunication.Client;

import java.io.IOException;
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

    public void startServer() {

            while(true) {
                System.out.println("Server is open, scanning for requests at port " + port + "...");
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Connection detected!");


                //skicka med socketen till den nya klienten

                try {
                  Client client = new Client(socket);
                   client.listen();
                    System.out.println("this wont be printed");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }





    }

}
