package HTTPcommunication;

public class HTTPResponse {

   private final String HTTP_VERSION = "HTTP/1.1";

   private int status;
   private String version;
   private String message;
   private String contentType;
   private int contentLength;
   private String connection;
   private byte[] body;

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
        String s = HTTP_VERSION+" "+status+" "+message+"\n"+
                    "Content-Type: "+contentType+"\n"+
                    "Content-Length: "+contentLength+"\n"+
                    "Connection: "+connection+"\n"+
                    "Version: "+version+"\n\n";
        return s;
    }
}
