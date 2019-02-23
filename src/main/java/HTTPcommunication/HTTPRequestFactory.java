package HTTPcommunication;

import java.util.List;

public class HTTPRequestFactory {
    //Parse the Strings from a List containing HTTP-Headers into a HTTPRequest-object
    public static HTTPRequest getHTTPRequest(List<String> headersList){

        String connection = "";
        String host = "";
        int contentLength = 0;
        String accept = "";
        String query = "";
        String method = "";
        String path = "";
        String contentType = "";

        if(headersList != null && !(headersList.isEmpty())) {
            //The first String in the List should contain the method and path
            method = headersList.get(0).substring(0, headersList.get(0).indexOf(" "));
            path = headersList.get(0).substring(headersList.get(0).indexOf(" ") + 1, headersList.get(0).lastIndexOf(" "));
            //Check if it has a query
            if(path.contains("?")) {
                query = path.substring(path.indexOf("?")+1);
                path = path.substring(0, path.indexOf("?"));
            }
            //Loop through the list, save relevant header-values
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
                .setHost(host).setQuery(query).setPath(path);

    }

}