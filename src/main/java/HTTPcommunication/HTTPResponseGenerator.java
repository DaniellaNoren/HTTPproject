package HTTPcommunication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HTTPResponseGenerator {

    private final static String WEB_ROOT = ".//web";

    public static HTTPResponse getHTTPResponse(HTTPRequest request){

        String message = "OK";
        String contentType = "";
        int status = 200;

        String url = "";
        if(!(request.getURL().equals("/")) && !(request.getURL().equals("")))
            url = request.getURL().substring(1);


        if(!request.getURL().isEmpty() && request.getURL().contains(".")){
            String fileEnding = request.getURL().substring(request.getURL().indexOf("."));
            contentType = contentTypeRequested(fileEnding);
        }


        byte[] content = new byte[0];

        if(!(request.getURL().isEmpty())) {

            File file = null;

            if ((!(request.getURL().equals("/"))) && new File(WEB_ROOT, url).exists()) {
                System.out.println("Inside file found");
                file = new File(WEB_ROOT, url);
            }
            else if (request.getURL().equals("/")) {
                System.out.println("Inside default");
                file = new File(WEB_ROOT, "index.html");
                contentType = "text/html";
            }
            else {
                System.out.println("Inside 404");
                status = 404;
                message = "Not Found";
                file = new File(WEB_ROOT, "404.html");
            }

            try {
                content = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int contentLength = content.length;
        String connection = request.getConnection();


        return new HTTPResponse().setStatus(status).setMessage(message).setContentType(contentType).setContentLength(contentLength).setConnection(connection).setBody(content);
    }

    private static String contentTypeRequested(String fileExtension){
        switch (fileExtension){
            case ".html" : return "text/html";
            case ".css" : return "text/css";
            case ".js" : return "text/javascript";
            case ".png" : return "image/png";
            case ".pdf" : return "application.pdf";
            case ".jpg" : return "image/jpeg";
            case ".json" : return "application.json";
            default: return "text/plain";
        }
    }
}