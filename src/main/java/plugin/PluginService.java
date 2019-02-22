package plugin;

import HTTPcommunication.HTTPRequest;
import HTTPcommunication.HTTPResponse;

public interface PluginService {
    HTTPResponse response(HTTPRequest httpRequest);
}
