package HTTPcommunication;

public class HTTPRequest {

    private final String HTTP_VERSION = "HTTP/1.1";

    private String method;
    private String URL;
    private String connection;
    private String host;

    private int contentLength;
    private String contentType;
    private byte[] body;

    public HTTPRequest(String method, String URL, String connection, String host){
        this(method, URL, connection, host, "", 0, null);
    }
    public HTTPRequest(String method, String URL, String connection, String host, String contentType, int contentLength, byte[] body){
        this.method = method;
        this.URL = URL;
        this.connection = connection;
        this.host = host;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.body = body;
    }


    public String getMethod() {
        return method;
    }

    public String getURL() {
        return URL;
    }

    public String getConnection() {
        return connection;
    }

    public String getHost() {
        return host;
    }

    public int getContentLength() {
        return contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body;
    }
}
