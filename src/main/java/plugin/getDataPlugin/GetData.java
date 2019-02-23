package plugin.getDataPlugin;

import HTTPcommunication.HTTPRequest;
import HTTPcommunication.HTTPResponse;
import plugin.PluginAnnotation;
import plugin.interfaces.PageService;
import plugin.interfaces.StoreService;

/**
 * This plugin saves which time of the day http requests are sent to the server in a database. The purpose of this
 * is to be able to see things like patterns when the server needs to deal with most "traffic" or the opposite.
 * The time is rounded to the hours of the day so when a request is sent a "value++" is sent to one out of 24 values in the database.
 *
 * The "DisplayData" plugin gives a page with a visual representation of the results of this plugin.
 */

public class GetData implements StoreService {

    @Override
    public void storeData(HTTPRequest httpRequest) {

    }


    private void parseTime(){

    }

    private void updateDatabase(){

    }



}