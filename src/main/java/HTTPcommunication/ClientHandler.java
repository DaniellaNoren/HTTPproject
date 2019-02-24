
package HTTPcommunication;

import plugin.PluginHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
//Handles the communication between client and server
public class ClientHandler extends Thread{

    private Socket clientSocket;
    private PrintWriter out;
    private OutputStream outByte;
    private BufferedReader in;

    private HTTPResponse response;
    private HTTPRequest request;


    public ClientHandler(Socket socket, PrintWriter out, OutputStream outByte, BufferedReader in){
        this.clientSocket = socket;
        this.out = out;
        this.outByte = outByte;
        this.in = in;
    }

    public void run() {

        try {
            request = HTTPRequestFactory.getHTTPRequest(getRequestAsList()); //Creates HTTPRequest-object
            request.setBody(getBody(request.getContentLength())); //Sets the body of the HTTPrequest

            response = PluginHandler.responsePlugin(request); //Get a HTTPResponse from plugins
            if(response == null) //If the plugin doesn't exist, create a static-file response
                response = StaticHTTPResponseGenerator.getHTTPResponse(request);

            PluginHandler.storagePlugin(request); //These plugins will always run

            sendHeaders(response);

            if (response.getBody() != null && response.getBody().length > 0 && !(request.getMethod().equals("HEAD"))) {
                sendBody(response.getBody());
            }

            out.close();
            in.close();
            clientSocket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This method reads the incoming HTTPrequest-headers from the client and saves it in a List
    public List<String> getRequestAsList() throws IOException {
        List<String> requestList = new ArrayList<>();
        String line = "";

        while((line = in.readLine()) != null && !(line.isEmpty())){
            requestList.add(line);
        }

        return requestList;
    }

    //This method reads the body of incoming HTTP-request, if it has one
    public byte[] getBody(int length) throws IOException {
        byte[] body = new byte[length];
        int i = 0;
        while(in.ready()){
            body[i] = (byte)in.read();
            i++;
        }
        return body;
    }
    //Send the response to the client
    public void sendHeaders(HTTPResponse response){
        out.write(response.toString());
        out.println();
        out.flush();
    }
    //Send the body to the client
    public void sendBody(byte[] content){
        try {
            outByte.write(content, 0, content.length);
            outByte.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
