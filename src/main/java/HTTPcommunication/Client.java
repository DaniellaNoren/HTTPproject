
package HTTPcommunication;


import parsing.SqlToJsonFile;
import parsing.QueryStringToJSON;
import plugin.RequestHandler;
import storage.SQLDatabase;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Client extends Thread{

    private Socket clientSocket;
    private PrintWriter out;
    private OutputStream outByte;
    private BufferedReader in;

    private HTTPResponse response;
    private HTTPRequest request;


    public Client(Socket socket, PrintWriter out, OutputStream outByte, BufferedReader in) throws IOException {
        this.clientSocket = socket;
        this.out = out;
        this.outByte = outByte;
        this.in = in;
    }

    public void run() {

        try {
            request = HTTPRequestFactory.getHTTPRequest(getRequestAsList());
            request.setBody(getBody(request.getContentLength()));

            response = RequestHandler.responsePlugin(request);
            if(response == null)
                response = HTTPResponseGenerator.getHTTPResponse(request);

            RequestHandler.storagePlugin(request);
            specificUrlHandler(request.getPath());

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

    private void specificUrlHandler(String url) {
        switch (url) {
            case "/URL.html":
                if (request.getQuery().length() > 0){ //Create json file if URL page contains parameters
                    QueryStringToJSON.convert(new File("web/jsonFromQuery.json"), request.getQuery());
                }
                break;
            case "/comment":
                String httpBody = new String(request.getBody());
                String keyValue = null;
                try {
                    keyValue = URLDecoder.decode(httpBody.substring(httpBody.indexOf("=") + 1), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                SQLDatabase.addPost(keyValue);
                new SqlToJsonFile().writeJsonToFile(SQLDatabase.selectAllPost());
                response = new HTTPResponse().setStatus(200).setMessage("OK");
                sendHeaders(response);
            default:
                break;
        }
    }

    public List<String> getRequestAsList() throws IOException {
        List<String> requestList = new ArrayList<>();
        String line = "";

        while((line = in.readLine()) != null && !(line.isEmpty())){
            requestList.add(line);
        }

        return requestList;
    }


    public byte[] getBody(int length) throws IOException {
        byte[] body = new byte[length];
        int i = 0;
        while(in.ready()){
            body[i] = (byte)in.read();
            i++;
        }
        return body;
    }

    public void sendHeaders(HTTPResponse response){
        out.write(response.toString());
        out.println();
        out.flush();
    }

    public void sendBody(byte[] content){
        try {
            outByte.write(content, 0, content.length);
            outByte.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
