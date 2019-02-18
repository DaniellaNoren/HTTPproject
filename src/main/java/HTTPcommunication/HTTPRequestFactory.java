package HTTPcommunication;

import java.util.HashMap;
import java.util.StringTokenizer;
public class HTTPRequestFactory {

    //Väldigt ful metod men den funkar. Finns säkert mindre klumpigt sätt att göra detta på, ska jobba på det. Men nu funkar det i alla fall att den skapar ett HTTPRequest-objekt
    public static HTTPRequest getHTTPRequest(String req, String body){

        HTTPRequest request = null;

        String[] lines = req.split("\n"); //En String-array som innehåller varje rad
        HashMap<String, String> headers = new HashMap(); // En Hashmap som jag vill att Header-namnen ska bli KEYS och Header-värdena ska bli VALUES
        //Första raden på ett HTTPRequest är ju dock inte direkt ett key/value-par, det är ju METOD URL HTTP/1.1, därför är koden klumpig :P

        for(String s : lines){
            String[] heads = s.split(" ", 2); //Gör om varje värde i lines till en String[] med två värden, Header-namnet och värdet
            headers.put(heads[0], heads[1]); // Sätter in värdena i headers-hashmappen
        }

        StringTokenizer st = new StringTokenizer(lines[0]); //Detta är bara till för METOD URL, vet inte hur jag ska göra det annars
        String method = st.nextToken();
        String url = st.nextToken();
        String query = "";
        if(url.contains("?"))
            query = url.substring(url.indexOf("?"));


        String host = headers.get("Host:");
        String connection = headers.get("Connection:");

        if(method.equals("POST")){
            int contentLength = Integer.parseInt(headers.get("Content-Length:"));
            String contentType = headers.get("Content-Type:");
            String b = body;
            return request = new HTTPRequest(method, url, connection, host, query, contentType, contentLength, b);
        }else
            return request = new HTTPRequest(method, url, connection, host, query);

    }

}
