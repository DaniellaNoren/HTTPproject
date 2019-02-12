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
                    if(httpRequest.equals("/")){
                        httpRequest += "index.html";
                    }

                    switch(typeOfHttpRequest){
                        case "GET" :
                            System.out.println("Got a GET request from client: " + httpRequest);
                            httpRequestGet(httpRequest);
                            break;
                        case "POST" :
                            System.out.println("Got a POST request from client " + httpRequest);
                            httpRequestPost(httpRequest);
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

    //to do : things that are in common between requests could be put in its own method? need to learn requests first...
    //länkar http responsen till din metod senare daniella :)
    private static void httpRequestGet(String httpRequest) throws IOException {



        String pathToFiles = "./web"; //fix something more dynamic, right now all files need to be here. html/css/png etc..
        File requestedFile = new File(pathToFiles, httpRequest);
        int fileLength = (int) requestedFile.length();
        String contentType = contentTypeRequested(httpRequest.substring(httpRequest.lastIndexOf(".")));

        FileInputStream inputStream = new FileInputStream(requestedFile);
        byte[] content = new byte[fileLength];
        inputStream.read(content);
        inputStream.close();



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

        httpResponseBody.write(content, 0, fileLength);
        httpResponseBody.flush();

    }


    private static void httpRequestPost(String httpRequest){

    }

    private static void httpRequestHead(){
        System.out.println("Got a HEAD request from client");
    }

    private static void httpRequestFailed(){
        System.out.println("404");
    }



    /**
     * This method looks at the type of file the client is requesting through the HTTP request by looking at the file
     * extension and returns the right content type for the HTTP response that will be sent back.
     *
     * @param fileExtension What type of file is being requested? "example.html" means the file extension would be ".html".
     * @return String value of the content type the file extension represents. For example: ".html" = "text/html" or ".png" = "image.png".
     */
    private static String contentTypeRequested(String fileExtension){
        switch (fileExtension){
            case ".html" : return "text/html";
            case ".css" : return "text/css";
            case ".js" : return "text/javascript";
            case ".png" : return "image/png";
            case ".pdf" : return "application.pdf";
            default: return "text/plain";
        }
    }





}
