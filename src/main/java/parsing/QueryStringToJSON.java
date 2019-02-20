package parsing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONObject;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

public class QueryStringToJSON {

    public static void convert(File jsonFile, String url) {
        String query = getQueryStringFromUrl(decodeUrl(url));
        String[] queryParameters = queryParameters(query);

        Set<String> duplicateQueryKeys = findDuplicateKeys(queryParameters);
        JSONObject jsonObject = createJsonObject(duplicateQueryKeys, queryParameters);

        String jsonData = prettyJson(jsonObject.toString());
        writeJsonFile(jsonFile, jsonData);
    }

    private static String getQueryStringFromUrl(String url) {
        return url.substring(url.indexOf("?") + 1);
    }

    private static String decodeUrl(String url) {
        String decodedUrl = "";
        try {
            decodedUrl = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return decodedUrl;
    }

    //Creates set of detected duplicate keys found in array of query string parameters
    private static Set<String> findDuplicateKeys(String[] parameters) {
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
    private static JSONObject createJsonObject(Set<String> duplicateKeys, String[] parameters) {
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
    private static String[] queryParameters(String query) {
        return query.split("&");
    }

    //Creates pretty JSON format string from a JSON string
    private static String prettyJson(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);

        return gson.toJson(je);
    }

    private static void writeJsonFile(File jsonFile, String json) {
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
