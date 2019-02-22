package plugin.jsonPlugin;

import HTTPcommunication.HTTPRequest;
import HTTPcommunication.HTTPResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONObject;
import plugin.PluginAnnotation;
import plugin.PluginService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

@PluginAnnotation("/JSON")
public class queryToJson implements PluginService {
    @Override
    public HTTPResponse response(HTTPRequest httpRequest) {
        if (httpRequest.getQuery().equals("") || !httpRequest.getQuery().contains("=")) {
            String noQueryResponse = "Please enter query";
            byte[] body = noQueryResponse.getBytes();

            return new HTTPResponse().setStatus(200).setMessage("OK").setContentType("text/html").setContentLength(body.length).setBody(body);
        }

        String[] parameters = queryParameters(decodeQuery(httpRequest.getQuery()));
        Set<String> duplicateQueryKeys = findDuplicateKeys(parameters);
        JSONObject jsonObject = createJsonObject(duplicateQueryKeys, parameters);
        String jsonData = jsonObject.toString();
        byte[] body = jsonData.getBytes();

        return new HTTPResponse().setStatus(200).setMessage("OK").setContentType("text/html").setContentLength(body.length).setBody(body);
    }

    private String decodeQuery(String query) {
        String decodedQuery = "";
        try {
            decodedQuery = URLDecoder.decode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return decodedQuery;
    }

    //Creates set of detected duplicate keys found in array of query string parameters
    private Set<String> findDuplicateKeys(String[] parameters) {
        Set<String> duplicateKeys = new HashSet<>();
        for (int i = 0; i < parameters.length; i++) {
            String keyi = parameters[i].substring(0, parameters[i].indexOf("="));
            for (int j = 0; j < parameters.length; j++) {
                String keyj = parameters[j].substring(0, parameters[j].indexOf("="));
                if (i != j && keyi.equals(keyj)) {
                    duplicateKeys.add(keyj);
                }
            }
        }

        return duplicateKeys;
    }

    //Creates JSON Object from array of query string parameters
    private JSONObject createJsonObject(Set<String> duplicateKeys, String[] parameters) {
        JSONObject jsonObject = new JSONObject();

        for (String param : parameters) {
            String key = param.substring(0, param.indexOf("="));
            String value = param.substring(param.indexOf("=") + 1);

            if (duplicateKeys.contains(key)) {
                jsonObject.append(key, value);
            } else {
                jsonObject.put(key, value);
            }
        }

        return jsonObject;
    }

    //Divides parameters from query string into string array
    private String[] queryParameters(String query) {
        return query.split("&");
    }

    //Creates pretty JSON format string from a JSON string
//    private String prettyJson(String json) {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        JsonParser jp = new JsonParser();
//        JsonElement je = jp.parse(json);
//
//        return gson.toJson(je);
//    }
}
