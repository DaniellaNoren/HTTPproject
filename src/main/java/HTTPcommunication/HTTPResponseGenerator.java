package HTTPcommunication;

import parsing.FileToBytesConverter;

import java.io.File;

public class HTTPResponseGenerator {

    public static HTTPResponse getHTTPResponse(HTTPRequest request){
        HTTPResponse response = null;
        String message = "OK";
        String contentType = "";
        int status = 200;
        int contentLength = 0;
        if(!request.getURL().isEmpty())
            if(request.getURL().contains(".")) {
                String url = request.getURL().substring(request.getURL().indexOf("."));
                contentType = contentTypeRequested(url);
                System.out.println(contentType);
            }
        File file = null;
        byte[] content = null;
        if(new File(".//src//web//", request.getURL()).exists()) {
            file = new File("src//web//" + request);
            content = new FileToBytesConverter().convertFileToBytes(file);
            contentLength = content.length;
        }else {
            status = 404;
            message = "Not Found";
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
