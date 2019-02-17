package HTTPcommunication;

public class HTTPRequest {

    private final String HTTP_VERSION = "HTTP/1.1";

    private String method;
    private String URL;
    private String connection;
    private String host;
    private String query;

    private int contentLength;
    private String contentType;
    private String body;

    public HTTPRequest(String method, String URL, String param, String connection, String host){
        this(method, URL, connection, host, param, "", 0, "");
    }
    public HTTPRequest(String method, String URL, String connection, String param, String host, String contentType, int contentLength, String body){
        this.method = method;
        this.URL = URL;
        this.connection = connection;
        this.host = host;
        this.query = param;
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

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "HTTPRequest{" +
                "HTTP_VERSION='" + HTTP_VERSION + '\'' +
                ", method='" + method + '\'' +
                ", URL='" + URL + '\'' +
                ", connection='" + connection + '\'' +
                ", host='" + host + '\'' +
                ", query=" + query + '\'' +
                ", contentLength=" + contentLength +
                ", contentType='" + contentType + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
