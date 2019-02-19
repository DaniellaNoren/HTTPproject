package HTTPcommunication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HTTPResponseGenerator {

    private final static String WEB_ROOT = ".//web";

    public static HTTPResponse getHTTPResponse(HTTPRequest request){

        HTTPResponse response = null;
        String message = "OK";
        String contentType = "";
        int status = 200;
        int contentLength = 0;

        String url = "";
        if(!(request.getURL().equals("/") && request.getURL().isEmpty()))
         url = request.getURL().substring(1);


        if(!request.getURL().isEmpty() && request.getURL().contains(".")){
                String b = request.getURL().substring(request.getURL().indexOf("."));
                contentType = contentTypeRequested(b);
                System.out.println(contentType);
            }

        File file = null;
        byte[] content = new byte[0];

        if((!(request.getURL().equals("/"))) && new File(WEB_ROOT, url).exists()) {
            file = new File(WEB_ROOT, url);
        }else if(request.getURL().equals("/")){
             file = new File(WEB_ROOT, "index.html");
             contentType = "text/html";
        }
        else {
            status = 404;
            message = "Not Found";
            file = new File(WEB_ROOT, "404.html");
        }


            try {
                content = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

        contentLength = content.length;
        String connection = request.getConnection();

        response = new HTTPResponse(status, message, contentType, contentLength, connection, content);

        return response;
    }

    private static String contentTypeRequested(String fileExtension){
        switch (fileExtension){
            case ".html" : return "text/html";
            case ".css" : return "text/css";
            case ".js" : return "text/javascript";
            case ".png" : return "image/png";
            case ".pdf" : return "application.pdf";
            case ".jpg" : return "image/jpeg";
            default: return "text/plain";
        }
    }
}
