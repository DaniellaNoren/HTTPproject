package main;

import storage.SQLDatabase;

import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {

        SQLDatabase db = new SQLDatabase();
        Server server = new Server(8081);
        server.startServer();

    }
}
