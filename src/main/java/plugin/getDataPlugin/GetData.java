package plugin.getDataPlugin;

import HTTPcommunication.HTTPRequest;
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
        System.out.println("\n\n\n\n");
        System.out.println("Inside GetDataPlugin");
        System.out.println("\n\n\n\n");
    }


    /**
     * Use java's Date() function to get the time of the http request but parse it from hour:minute:second to only
     * the hour it's closest to since it's enough to keep a counter for 24 values.
     */
    private void parseTime(){

    }

    /**
     * calls to the SQLite database class that handles the database connection to insert the statistics
     */
    private void updateDatabase(){

    }



}