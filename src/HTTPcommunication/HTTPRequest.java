package HTTPcommunication;

public class HTTPRequest {

    private String method;
    private String contentType;
    private String URL;

    public HTTPRequest(String method, String contentType, String URL){
        this.method = method;
        this.contentType = contentType;
        this.URL = URL;
    }

    //getters/setters
    //toString
}
