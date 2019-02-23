package plugin.getDataPlugin;

import HTTPcommunication.HTTPRequest;
import plugin.PluginAnnotationStore;
import plugin.interfaces.StoreService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This plugin saves which time of the day http requests are sent to the server in a database. The purpose of this
 * is to be able to see things like patterns when the server needs to deal with most "traffic" or the opposite.
 * The time is rounded to the hours of the day so when a request is sent a "value++" is sent to one out of 24 values in the database.
 *
 * The "DisplayData" plugin gives a page with a visual representation of the results of this plugin.
 */

@PluginAnnotationStore
public class GetData implements StoreService {

    @Override
    public void storeData(HTTPRequest httpRequest) {
        System.out.println("\n\n\n");
        System.out.println("hej");
        System.out.println("\n\n\n");
        updateDatabase(parseTime());
    }


    /**
     * Use java's Date() function to get the time of the http request but parse it from hour:minute:second to only
     * the hour it's closest to since it's enough to keep a counter for 24 values.
     */
    private String parseTime(){

        Date date = new Date();

        String currentHour = new SimpleDateFormat("hh").format(date);
        String currentMinutes = new SimpleDateFormat("mm").format(date);

        //If minutes are greater than 30, round the hour up to the next hour.
        if(Integer.parseInt(currentMinutes) > 30){
            if(!currentHour.equals("23")){
                int i = Integer.parseInt(currentHour);
                i++;
                currentHour = "" + i;
            }
            else{
                currentHour = "00";
            }
        }
        return currentHour;
    }

    /**
     * calls to the SQLite database class that handles the database connection to insert the statistics
     * @param timeOfDay takes a number between 0-23 from the parseTime() method. The values represents the hours of the day.
     */
    private void updateDatabase(String timeOfDay){
        SQLiteStatistics.getInstance().updateCounter(timeOfDay);

//        System.out.println("\n\n\n");
//        SQLiteStatistics.getInstance().printDataToConsole();
//        System.out.println("\n\n\n");

    }



}