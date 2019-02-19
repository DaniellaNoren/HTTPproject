
package HTTPcommunication;

import commentpage.JsonParser;
import parsing.QueryStringToJSON;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{

    private Socket clientSocket;
    private PrintWriter out;
    private OutputStream outByte;
    private BufferedReader in;

    private HTTPResponse response;
    private HTTPRequest request;


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

                       //FIXA BÄTTRE LÖSNING
                       if (request.getURL().equals("/URL.html") && request.getQuery().length() > 0){
                           QueryStringToJSON.writeJsonObjToFile(QueryStringToJSON.convert(request.getQuery()), new File("web/jsonFromQuery.json"));
                       }



                        //Till kommentar sidan
                        if(request.getMethod().equals("POST")){

                            String httpBody = new String(request.getBody()); //byte array to string

                            new JsonParser().writeJsonToFile(httpBody);

                            //String keyValue = httpBody.substring(httpBody.indexOf("=") + 1);

                        }






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
