package HTTPcommunication;

public class HTTPRequestFactory {

    public static HTTPRequest getHTTPRequest(String req){
        /*
        Kolla igenom Strängen som vi antar är i rätt format
        Spara alla HEADERS i någon variabel
        tex String method = GET/POST, vad som nu finns i Strängen, String contentType = req.substring(något);
        HTTPREquest request = new HTTPRequest(method, contentType, URL etc, etc);
        return request;

        Det är i Client-klassen som vi skapar Strängen genom BufferedReader
        Skickar in den Strängen till HTTPRequestFactory.gethTTPRequest()
        Sen kan Client använda ett HTTPRequest-objekt för att skapa någon respons, eller skicka info
        till en klass som skapar respons.


         */

        return null;
    }
}
