//package HTTPcommunication;
//
//import java.io.BufferedOutputStream;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.Socket;
//
//public class Client extends Thread {
//
//    private Socket socket;
//    private BufferedOutputStream out;
//    private BufferedReader in;
//
//    public Client(Socket socket){
//        this.socket = socket;
//        try {
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            out = new BufferedOutputStream(socket.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void run(){
//        while(true){
//          logik grejer
 //               }
//    }
//}
package HTTPcommunication;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class Client{


    private static Socket clientSocket;
    private PrintWriter outChar;
    private BufferedOutputStream out;
    private BufferedReader in;
    private static boolean running = true;

    public Client(Socket socket) throws IOException {
        this.clientSocket = socket;
        outChar = new PrintWriter(socket.getOutputStream());
        out = new BufferedOutputStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void listen() {

            try{
                while(running){

                    StringTokenizer parsedClientInput = new StringTokenizer(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())).readLine());

                    String typeOfHttpRequest = parsedClientInput.nextToken().toUpperCase();
                    System.out.println(typeOfHttpRequest);
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
                    System.out.println("loop");

                }
            } catch (IOException e){
                e.printStackTrace();
            }


    }

    //to do : things that are in common between requests could be put in its own method? need to learn requests first...
    //länkar http responsen till din metod senare daniella :)
    private void httpRequestGet(String httpRequest) throws IOException {



        String pathToFiles = "./web"; //fix something more dynamic, right now all files need to be here. html/css/png etc..
        File requestedFile = new File(pathToFiles, httpRequest);
        int fileLength = (int) requestedFile.length();
        String contentType = contentTypeRequested(httpRequest.substring(httpRequest.lastIndexOf(".")));

        FileInputStream inputStream = new FileInputStream(requestedFile);
        byte[] content = new byte[fileLength];
        inputStream.read(content);
        inputStream.close();

        outChar.println("HTTP/1.1 200 OK");
        outChar.println("Date: " + new Date());
        outChar.println("Server: HttpServer");
        outChar.println("Content-type: " + contentType);
        outChar.println("Content-length: " + fileLength);
        outChar.println();
        outChar.flush();

        out.write(content, 0, fileLength);
        out.flush();

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
