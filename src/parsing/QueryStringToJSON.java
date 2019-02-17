package parsing;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class QueryStringToJSON {

    public String convert(String url) throws UnsupportedEncodingException {
        String decodedURL = URLDecoder.decode(url.substring(url.indexOf("?") + 1), "UTF-8");

        String[] parameters = decodedURL.split("&");

        JSONObject jsonObject = new JSONObject();

        for (String param : parameters) {
            String key = param.substring(0, param.indexOf("="));
            String value = param.substring(param.indexOf("=") + 1);
            jsonObject.put(key, value);
        }

        return jsonObject.toString();
    }
}
