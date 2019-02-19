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

    public HTTPResponse setStatus(int status){
        this.status = status;
        return this;
    }

    public HTTPResponse setVersion(String version) {
        this.version = version;
        return this;
    }

    public HTTPResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public HTTPResponse setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public HTTPResponse setContentLength(int contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public HTTPResponse setConnection(String connection) {
        this.connection = connection;
        return this;
    }

    public HTTPResponse setBody(byte[] body) {
        this.body = body;
        return this;
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

        String s = HTTP_VERSION+" "+status+" "+message+"\n"+
                "Content-Length: "+contentLength+"\n"+
                "Content-Type: "+contentType+"\n"+
                "Connection: "+connection+"\n";

        return s;
    }
}