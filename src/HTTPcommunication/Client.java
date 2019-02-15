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

public class Client extends Thread{


    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedOutputStream outByte;
    private static BufferedReader in;
    private static boolean running = true;

    public Client(Socket socket, PrintWriter outChar, BufferedOutputStream out, BufferedReader in) throws IOException {
        this.clientSocket = socket;
        this.out = outChar;
        this.outByte = out;
        this.in = in;
    }

    public void run() {
        //boolean keep alive = false; Om Connection: keep-alive i HTTPRequestet ska kanske detta loopas tills Connection inte är keep-alive
        //if(HTTPRequest.getConnection() == "keep-alive") Boolean run = true;
        //while(run){ }

        // 1. Få HTTP-Requestet, parse-a det och kolla på Metod, Content-Type, om den har en body etc.
        // 2. Spara de kriterierna (kanske i ett HTTPRequest-objekt?)
        // 3. Skapa en respons som är baserad på HTTPREquest-objektet som nyss skapades

                try {
                    System.out.println(getRequest());
                    sendResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            try {
                out.close();
                in.close();
                outByte.close();
                clientSocket.close();
                System.out.println("socket closed");
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public static String getRequest() throws IOException {
        StringBuilder req = new StringBuilder();
        String line = "bo";
        while((!(line = in.readLine()).isEmpty()) && in.ready()){
            req.append(line).append("\n");
        }

        return req.toString();
    }
    public void sendResponse(){
        out.write("HTTP/1.1 200 \r\n"); // Version & status code
        out.write("Content-Type: text/html\r\n"); // The type of data
        out.write("Connection: close\r\n"); // Will close stream
        out.write("\r\n"); // End of headers
        out.write("<!DOCTYPE html><html><form method='post'><input name='wow' type='text'/><input name='damn' type='text'/><input type='submit'/></form>");
        out.flush();
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

//        outChar.println("HTTP/1.1 200 OK");
//        outChar.println("Date: " + new Date());
//        outChar.println("Server: HttpServer");
//        outChar.println("Content-type: " + contentType);
//        outChar.println("Content-length: " + fileLength);
//        outChar.println();
//        outChar.flush();

//        out.write(content, 0, fileLength);
//        out.flush();

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
