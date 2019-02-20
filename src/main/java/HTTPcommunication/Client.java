
package HTTPcommunication;


import parsing.SqlToJsonFile;
import parsing.QueryStringToJSON;
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

            //Create json file if URL page contains parameters
            if (request.getURL().equals("/URL.html") && request.getQuery().length() > 0){
                QueryStringToJSON.convert(new File("web/jsonFromQuery.json"), request.getQuery());
            }

            //Till kommentar sidan
            if(request.getMethod().equals("POST")){

                String httpBody = new String(request.getBody()); //byte array to string
                String s = httpBody.replaceAll("\\+", " "); //all blank spaces became + symbols... this fixes it back to normal
                String keyValue = URLDecoder.decode(s.substring(s.indexOf("=") + 1), "UTF-8"); //only take the key from key/value


                //Adds keyValue from Json to database.
                SQLDatabase.addPost(keyValue);
                new SqlToJsonFile().writeJsonToFile(SQLDatabase.selectAllPost());
                //if the request equals a POST it is ok!
                response = new HTTPResponse().setStatus(200).setMessage("OK");
                sendResponse(response);
            }
            else {
                //if it is not a POST
                response = HTTPResponseGenerator.getHTTPResponse(request);
                sendResponse(response);

                if (response.getBody().length > 0 && !(request.getMethod().equals("HEAD"))) {
                    sendFile(response.getBody());
                } else
                    out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            out.close();
            in.close();
            clientSocket.close();
            System.out.println("socket closed");
        } catch (IOException e) {
            e.printStackTrace();
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

    public void sendResponse(HTTPResponse response){
        out.write(response.toString());
        out.println();
        out.flush();
    }

    public void sendFile(byte[] content){
        try {
            outByte.write(content, 0, content.length);
            outByte.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
