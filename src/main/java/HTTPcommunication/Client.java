
package HTTPcommunication;

import parsing.FileToBytesConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread{


    private FileToBytesConverter fileToBytes;
    private Socket clientSocket;
    private PrintWriter out;
    private OutputStream outByte;
    private BufferedReader in;
    private boolean running = true;

    public Client(Socket socket, PrintWriter outChar, OutputStream out, BufferedReader in) throws IOException {
        this.fileToBytes = new FileToBytesConverter();
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
                   String getReq = getRequest();
                   HTTPRequest request = null;

                       request = HTTPRequestFactory.getHTTPRequest(getReq, getBody());
                       if (request.getBody() != null || request.getBody().length > 0) ;
                       System.out.println(byteArrayToString(request.getBody()));//getReq läser in
                       HTTPResponseGenerator.getHTTPResponse(request);// headers, getBody() läser in bodyn
                       System.out.println(request); //Skriver ut requesten för att testa
                       //sendResponse(); //Denna metoden är bara för att testa, den skickar alltid samma respons nu
                       HTTPResponse response = HTTPResponseGenerator.getHTTPResponse(request);
                       //out.write(response.toString());
                    System.out.println(response);
                       sendResponse(response);
                    System.out.println(response.getBody());
                        out.flush();
                       if (response.getBody().length > 0) {
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

        while(in != null && (!(line = in.readLine()).isEmpty())){
            req.append(line).append("\n");
        }

        return req.toString();
    }

    public byte[] getBody() throws IOException {
        //StringBuilder body = new StringBuilder();
        byte[] body = new byte[4096];
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
        out.write("Content-Length: "+response.getContentLength()+"\r\n");
        out.write("Connection: keep-alive\r\n\n");
        // Will close stream
        //out.write("\r\n"); // End of headers
         //out.flush();

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
            //outByte.flush();
            System.out.println("in send file");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
