package plugin;

import HTTPcommunication.HTTPRequest;
import HTTPcommunication.HTTPResponse;
import plugin.webPage.WebPagePlugin;
import plugin.storage.DataStoragePlugin;
import plugin.webPage.WebPagePath;

import java.util.ServiceLoader;

public class PluginHandler {

    //Return a HTTP response if the request url path matches a pageService plugin annotation
    public static HTTPResponse responsePlugin(HTTPRequest request) {
        ServiceLoader<WebPagePlugin> loader = ServiceLoader.load(WebPagePlugin.class);

        for (WebPagePlugin plugin : loader) {
            if (plugin.getClass().getAnnotation(WebPagePath.class).value().equals(request.getPath())) {
                return plugin.response(request);
            }
        }

        return null;
    }

    //Run all data storage plugins
    public static void storagePlugin(HTTPRequest request){
        ServiceLoader<DataStoragePlugin> loader = ServiceLoader.load(DataStoragePlugin.class);

        for(DataStoragePlugin plugin : loader)
            plugin.storeData(request);
    }
}
