package HTTPcommunication;

import java.util.List;

public class HTTPRequestFactory {

    public static HTTPRequest getHTTPRequest(List<String> headersList){

        String connection = "";
        String host = "";
        int contentLength = 0;
        String accept = "";
        String query = "";
        String method = "";
        String URL = "";
        String contentType = "";

        if(headersList != null && !(headersList.isEmpty())) {

            method = headersList.get(0).substring(0, headersList.get(0).indexOf(" "));
            URL = headersList.get(0).substring(headersList.get(0).indexOf(" ") + 1, headersList.get(0).lastIndexOf(" "));

            if(URL.contains("?")) {
                query = URL.substring(URL.indexOf("?")+1);
                URL = URL.substring(0, URL.indexOf("?"));
            }

            for(String s : headersList){
                switch(s.substring(0, s.indexOf(" ")).toLowerCase()){
                    case "connection:" : connection = s.substring(s.indexOf(" ")+1);
                        break;
                    case "host:" : host = s.substring(s.indexOf(" ")+1);
                        break;
                    case "content-length:" : contentLength = Integer.parseInt(s.substring(s.indexOf(" ")+1));
                        break;
                    case "accept:" : accept = s.substring(s.indexOf(" ")+1);
                        break;
                    case "content-type:" : contentType = s.substring(s.indexOf(" ")+1);
                        break;
                }
            }
        }
        return new HTTPRequest().setMethod(method).setConnection(connection).setContentLength(contentLength).setContentType(contentType)
                .setHost(host).setQuery(query).setPath(URL);

    }

}