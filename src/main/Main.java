package main;

import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {

        Server server = new Server(8081);
        server.startServer();

    }
}
