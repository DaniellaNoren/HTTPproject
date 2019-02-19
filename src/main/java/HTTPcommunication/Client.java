
package HTTPcommunication;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class Client extends Thread{

    private Socket clientSocket;
    private PrintWriter out;
    private OutputStream outByte;
    private BufferedReader in;

    private HTTPResponse response;
    private HTTPRequest request;

    //printwriter är header. buffered outputstream för bodyn som är bytekod
    public Client(Socket socket, PrintWriter outChar, OutputStream out, BufferedReader in) throws IOException {
        this.clientSocket = socket;
        this.out = outChar;
        this.outByte = out;
        this.in = in;
    }

    public void run() {

                try {
                       request = HTTPRequestFactory.getHTTPRequest(getRequest());
                       request.setBody(getBody(request.getContentLength()));



                        //---
                        if(request.getMethod().equals("POST")){
                            System.out.println("\n\n\n\n\n");
                            String byteArrayToString = new String(request.getBody());
                            System.out.println(byteArrayToString);
                            System.out.println("\n\n\n\n\n");

                            //skicka byteArrayToString variabeln till en databas/lista/liknande här

                            //läs in databas/lista/liknande här i json. prova denna biten först med test databas.

                        }
                        //---





                       response = HTTPResponseGenerator.getHTTPResponse(request);
                       sendResponse(response);

                       if (response.getBody().length > 0 && !(request.getMethod().equals("HEAD"))) {
                           sendFile(response.getBody());
                       }else
                           out.println();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            try {

                out.close();
                in.close();
//                outByte.close();
                clientSocket.close();
                System.out.println("socket closed");
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public String getRequest() throws IOException {
        StringBuilder req = new StringBuilder();
        String line = "";

        while((line = in.readLine()) != null && !(line.isEmpty())){
            req.append(line).append("\n");
        }

        return req.toString();
    }

    public byte[] getBody(int length) throws IOException {
        //StringBuilder body = new StringBuilder();
        byte[] body = new byte[length];
        int i = 0;
        while(in.ready()){
            //body.append((char) in.read());
            body[i] = (byte)in.read();
            i++;
        }
        return body;
    }

     public void sendResponse(HTTPResponse response){
        out.write(response.getHTTP_VERSION()+" "+response.getStatus()+" "+response.getMessage()+"\r\n"); // Version & status code
        out.write("Content-Type: "+response.getContentType()+"\r\n"); // The type of data
        out.write("Content-Length: "+response.getContentLength()+"\r\n\n");
        out.flush();
    }

    public String byteArrayToString(byte[] content){
        String body = "";
        for(int i = 0; i < content.length; i++){
            body += (char)content[i];
        }
        return body;
    }
    public void sendFile(byte[] content){
        try {
            outByte.write(content, 0, content.length);
            outByte.close();
            System.out.println("in send file");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
