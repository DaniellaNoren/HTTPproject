package HTTPcommunication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
//Get a response if a static resource is requested
public class StaticHTTPResponseGenerator {

    //The root-folder of all resources
    private final static String WEB_ROOT = ".//web";

    public static HTTPResponse getHTTPResponse(HTTPRequest request){

        String message = "OK";
        String contentType = "";
        int status = 200;

        String url = "";

        //Remove the first '/' of the path so the file can later be found
        if(!(request.getPath().equals("/")) && !(request.getPath().equals("")))
            url = request.getPath().substring(1);


        ///Cut out the file ending and get a ContentType from contentTypeRequested-method
        if(!request.getPath().isEmpty() && request.getPath().contains(".")){
            String fileEnding = request.getPath().substring(request.getPath().indexOf("."));
            contentType = contentTypeRequested(fileEnding);
        }

        byte[] body = new byte[0];

        if(!(request.getPath().isEmpty())) {

            File file;

            if ((!(request.getPath().equals("/"))) && new File(WEB_ROOT, url).exists()) {
                file = new File(WEB_ROOT, url); //If the requested resource exists, it becomes file
            }
            else if (request.getPath().equals("/")) {
                file = new File(WEB_ROOT, "index.html"); //If no resource requested, "index.html" becomes file
                contentType = "text/html";
            }
            else {
                status = 404;
                message = "Not Found";
                file = new File(WEB_ROOT, "404.html"); //If file can't be found, "404.html" becomes file
            }

            try { //Try to convert the resource into bytes
                body = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int contentLength = body.length;
        String connection = request.getConnection();

        return new HTTPResponse().setStatus(status).setMessage(message).setContentType(contentType).setContentLength(contentLength).setConnection(connection).setBody(body);
    }

    private static String contentTypeRequested(String fileExtension){
        switch (fileExtension){
            case ".html" : return "text/html";
            case ".css" : return "text/css";
            case ".js" : return "text/javascript";
            case ".png" : return "image/png";
            case ".pdf" : return "application/pdf";
            case ".jpg" : return "image/jpeg";
            case ".json" : return "application/json";
            default: return "text/plain";
        }
    }
}