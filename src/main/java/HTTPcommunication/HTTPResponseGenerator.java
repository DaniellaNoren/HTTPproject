package HTTPcommunication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HTTPResponseGenerator {

    public static HTTPResponse getHTTPResponse(HTTPRequest request){
        HTTPResponse response = null;
        String message = "OK";
        String contentType = "";
        int status = 200;
        int contentLength = 0;
        String path = ".//web";
        String URL2 = request.getURL();
        URL2 = URL2.substring(1);
        System.out.println("url:"+request.getURL());
        if(!request.getURL().isEmpty())
            if(request.getURL().contains(".")) {
                String url = request.getURL().substring(request.getURL().indexOf("."));
                contentType = contentTypeRequested(url);
                System.out.println(contentType);
            }
        File file = null;
        byte[] content = new byte[request.getContentLength()];
        if(new File(path, URL2).exists()) {
            System.out.println("kjsdksddkfj"+URL2);
            file = new File(path, URL2);
            //content = new FileToBytesConverter().convertFileToBytes(file);
            try {
                content = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            contentLength = content.length;
        }else if(request.getURL().equals("/")){
            try {
                content = Files.readAllBytes(new File(path, "index.html").toPath());
                contentType = "text/html";
                contentLength = content.length;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            status = 404;
            message = "Not Found";
            try {
                File f = new File(path,"404.html");
                //String path = f.getAbsolutePath();
                //System.out.println(path);
                content = Files.readAllBytes((f).toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            default: return "text/plain";
        }
    }
}
