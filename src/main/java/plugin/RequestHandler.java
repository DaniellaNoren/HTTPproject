package plugin;

import HTTPcommunication.HTTPRequest;
import HTTPcommunication.HTTPResponse;

import java.util.ServiceLoader;

public class RequestHandler {

    public static HTTPResponse serviceLoader(HTTPRequest request) {
        ServiceLoader<PluginService> loader = ServiceLoader.load(PluginService.class);

        for (PluginService pluginService : loader) {
            if (pluginService.getClass().getAnnotation(PluginAnnotation.class).value().equals(request.getURL())) {
                return pluginService.response();
            }
        }

        return null;
    }
}
