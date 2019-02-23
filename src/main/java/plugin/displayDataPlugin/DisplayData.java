package plugin.displayDataPlugin;

import HTTPcommunication.HTTPRequest;
import HTTPcommunication.HTTPResponse;
import plugin.PluginAnnotationPage;
import plugin.interfaces.PageService;

/**
 * This plugin leads to a page with a visual representation of the statistics that are saved with the "GetData" plugin.
 * It's a separate plugin so it can be a choice whether the statistics should only be stored or also displayed.
 */

@PluginAnnotationPage("/statistics")
public class DisplayData implements PageService {

    @Override
    public HTTPResponse response(HTTPRequest httpRequest) {
        return null;
    }

    //Vet fortfarande bara hur man läser från en jsonfil från javascript så löser det på samma sätt som kommentarsidan...
    /**
     * Get the statistics from the database and convert the info to a json file for the javascript to read and build
     * a dynamic page based on.
     */
    private void sqliteToJson(){

    }






}