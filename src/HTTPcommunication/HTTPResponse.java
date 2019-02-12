package HTTPcommunication;

import java.io.File;
import java.util.Date;

public class HTTPResponse {

    //Basic idea of how a HTTPresponse-generator might look like. Need to rethink how to add the body, though
    public static String getHTTPResponse(int status, String msg, String type, File body){
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1").append("\t").append(status).append("\t").append(msg).append("\n")
        .append("Date:").append("\t").append(new Date()).append("\n")
        .append("Server: HttpServer").append("\n")
        .append("Content-Type:").append("\t").append(type).append("\n")
        .append("Content-Length:").append("\t").append(body.length()).append("\n\n")
        .append(body);

        return sb.toString();
    }

}
