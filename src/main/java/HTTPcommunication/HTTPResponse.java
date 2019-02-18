package HTTPcommunication;

import java.util.Date;

public class HTTPResponse {

   private final String HTTP_VERSION = "HTTP/1.1";

   private int status;
   private String version;
   private String message;
   private String contentType;
   private int contentLength;
   private String connection;
   private byte[] body;

   public HTTPResponse(int status, String message, String contentType, int contentLength, String connection){
       this(status, message, contentType, contentLength, connection, new byte[0]);

   }
   public HTTPResponse(int status, String message, String contentType, int contentLength, String connection, byte[] body){
       this.status = status;
       this.message = message;
       this.contentType = contentType;
       this.contentLength = contentLength;
       this.connection = connection;
       this.body = body;
}

    public int getStatus() {
        return status;
    }

    public String getVersion() {
        return version;
    }

    public String getMessage() {
        return message;
    }

    public String getContentType() {
        return contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public String getConnection() {
        return connection;
    }

    public byte[] getBody() {
        return body;
    }

    public String getHTTP_VERSION(){
        return HTTP_VERSION;
    }
    @Override
    public String toString() {
        String s = HTTP_VERSION+" "+status+" "+message+"\n"
                    +"Date: "+new Date()+"\n"+
                "Content-Length: "+contentLength+"\n"+
                "Content-Type: "+contentType+"\n";

        return s;
    }
}
