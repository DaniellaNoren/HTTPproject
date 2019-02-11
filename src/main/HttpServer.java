package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class HttpServer {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;

    private static boolean running = true;

    private static String htmlHomePage = "index.html";
    private static String someOtherPage = "page2.html";
    private static String htmlErrorPage = "404.html";


    public static void startServer(int PORT){

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            clientSocket = serverSocket.accept(); //wait on this line until a client connects to localhost:PORT
            System.out.println("Client connected");
            listen();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void listen() {
        Thread clientListenerThread = new Thread(() -> {
            try{
                while(running){

                    StringTokenizer parsedClientInput = new StringTokenizer(new BufferedReader(new InputStreamReader(
                            clientSocket.getInputStream())).readLine());

                    String typeOfHttpRequest = parsedClientInput.nextToken().toUpperCase();
                    String httpRequest = parsedClientInput.nextToken().toLowerCase();

                    switch(typeOfHttpRequest){
                        case "GET" :
                            httpRequestGet(httpRequest);
                            break;
                        case "POST" :
                            httpRequestPost();
                            break;
                        case "HEAD" :
                            httpRequestHead();
                            break;
                        default :
                            httpRequestFailed();
                            break;
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }); clientListenerThread.start();
    }

    //to do stuff: the things that are in common between all type of requests could be put in some other method?
    private static void httpRequestGet(String httpRequest) throws IOException {
        System.out.println("Got a GET request from client");

        if(httpRequest.endsWith("/")){ //this line can cause bugs. fix later
            httpRequest += htmlHomePage;
        }

        File file = new File(new File("."), httpRequest);
        int fileLength = (int) file.length();
        String contentType = httpRequest.endsWith(".html") ? "text/html" : "text/plain";
        byte[] contentData = readFileData(file, fileLength);


        //Http response
        PrintWriter httpResponseHeader = new PrintWriter(clientSocket.getOutputStream());
        BufferedOutputStream httpResponseBody = new BufferedOutputStream(clientSocket.getOutputStream());

        httpResponseHeader.println("HTTP/1.1 200 OK");
        httpResponseHeader.println("Date: " + new Date());
        httpResponseHeader.println("Server: HttpServer");
        httpResponseHeader.println("Content-type: " + contentType);
        httpResponseHeader.println("Content-length: " + fileLength);
        httpResponseHeader.println();
        httpResponseHeader.flush();

        httpResponseBody.write(contentData, 0, fileLength);
        httpResponseBody.flush();

    }

    private static void httpRequestPost(){
        System.out.println("Got a POST request from client");
    }

    private static void httpRequestHead(){
        System.out.println("Got a HEAD request from client");
    }

    private static void httpRequestFailed(){
        System.out.println("404");
    }



    private static byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        byte[] fileData = new byte[fileLength];
        inputStream.read(fileData);
        inputStream.close();

        return fileData;
    }







}
