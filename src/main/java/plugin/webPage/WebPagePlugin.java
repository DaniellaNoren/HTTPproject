package plugin.webPage;

import HTTPcommunication.HTTPRequest;
import HTTPcommunication.HTTPResponse;

public interface WebPagePlugin {
    HTTPResponse response(HTTPRequest httpRequest);
}
