package plugin.storage;

import HTTPcommunication.HTTPRequest;

public interface DataStoragePlugin {

    void storeData(HTTPRequest request);
}
