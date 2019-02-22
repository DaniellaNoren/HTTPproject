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
        if(!(request.getPath().equals("/")) && !(request.getPath().equals("")))
            url = request.getPath().substring(1);


        if(!request.getPath().isEmpty() && request.getPath().contains(".")){
            String fileEnding = request.getPath().substring(request.getPath().indexOf("."));
            contentType = contentTypeRequested(fileEnding);
        }

        /*

        ServiceLoader<PluginService> loader = ServiceLoader.load();
        for(PluginService s : loader){
            PluginService service = s;
            if(service.getClass().getAnnotation().value().equals(url)
                    s.doThing();

        }



         */


        byte[] content = new byte[0];

        if(!(request.getPath().isEmpty())) {

            File file = null;

            if ((!(request.getPath().equals("/"))) && new File(WEB_ROOT, url).exists()) {
                System.out.println("Inside file found");
                file = new File(WEB_ROOT, url);
            }
            else if (request.getPath().equals("/")) {
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
            case ".pdf" : return "application/pdf";
            case ".jpg" : return "image/jpeg";
            case ".json" : return "application/json";
            default: return "text/plain";
        }
    }
}