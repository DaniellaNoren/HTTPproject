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

    public JSONObject convert(String url) throws UnsupportedEncodingException {
        String decodedURL = URLDecoder.decode(url.substring(url.indexOf("?") + 1), "UTF-8");
        String[] parameters = decodedURL.split("&");

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

    public void writeJsonObjToFile(JSONObject jsonObject, File jsonFile) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonObject.toString());

        try(FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(gson.toJson(je));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
