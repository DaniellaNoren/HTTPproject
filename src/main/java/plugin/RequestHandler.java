package plugin;

import HTTPcommunication.HTTPRequest;
import HTTPcommunication.HTTPResponse;
import plugin.interfaces.PageService;
import plugin.interfaces.StoreService;

import java.util.ServiceLoader;

public class RequestHandler {

    public static HTTPResponse responsePlugin(HTTPRequest request) {
        ServiceLoader<PageService> loader = ServiceLoader.load(PageService.class);

        for (PageService pageService : loader) {
            if (pageService.getClass().getAnnotation(PluginAnnotation.class).value().equals(request.getPath())) {
                return pageService.response(request);
            }
        }

        return null;
    }
  
    public static void storagePlugin(HTTPRequest request){
        ServiceLoader<StoreService> loader = ServiceLoader.load(StoreService.class);

        for(StoreService s : loader)
            s.storeData(request);

    }

}
