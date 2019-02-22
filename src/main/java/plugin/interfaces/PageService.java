package plugin.interfaces;

import HTTPcommunication.HTTPRequest;
import HTTPcommunication.HTTPResponse;

public interface PageService {
    HTTPResponse response(HTTPRequest httpRequest);
}
