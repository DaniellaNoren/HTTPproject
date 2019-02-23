package HTTPcommunication;
//Object representing a HTTP-request
public class HTTPRequest {

    private final String HTTP_VERSION = "HTTP/1.1";

    private String method;
    private String path;
    private String connection;
    private String host;
    private String query;

    private int contentLength;
    private String contentType;
    private byte[] body;


    public HTTPRequest setMethod(String method) {
        this.method = method;
        return this;
    }

    public HTTPRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public HTTPRequest setConnection(String connection) {
        this.connection = connection;
        return this;
    }

    public HTTPRequest setHost(String host) {
        this.host = host;
        return this;
    }

    public HTTPRequest setQuery(String query) {
        this.query = query;
        return this;
    }

    public HTTPRequest setContentLength(int contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public HTTPRequest setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public HTTPRequest setBody(byte[] body) {
        this.body = body;
        return this;
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

    public String getQuery(){
        return query;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getConnection() {
        return connection;
    }

    @Override
    public String toString() {
        String req = method + " " + path + " " + HTTP_VERSION + "\n" +
                "Content-Length: " + contentLength + "\n" +
                "Content-Type: " + contentType + "\n" +
                "Host: " + host + "\n" +
                "Query: " + query + "\n" +
                "Connection: " + connection + "\n";
        return req;
    }
}