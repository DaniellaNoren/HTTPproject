package plugin.displayDataPlugin;

import HTTPcommunication.HTTPRequest;
import HTTPcommunication.HTTPResponse;
import plugin.PluginAnnotationPage;
import plugin.interfaces.PageService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

/**
 * This plugin leads to a page with a visual representation of the statistics that are saved with the "GetData" plugin.
 * It's a separate plugin so it can be a choice whether the statistics should only be stored or also displayed.
 */

@PluginAnnotationPage("/statistics")
public class DisplayData implements PageService {

    @Override
    public HTTPResponse response(HTTPRequest httpRequest) {

        String htmlDocument = htmlDocument();

        byte[] body = htmlDocument.getBytes();
        HTTPResponse response = new HTTPResponse();
        response.setStatus(200).setMessage("OK").setContentType("text/html").setContentLength(body.length).setBody(body);

        return response;
    }


    //Got some problems figuring out how to write a http response with loading separate html/css/js files so here
    //is the entire html document as one string for now.
    private String htmlDocument(){
        return "<!DOCTYPE html><html><h3 style='color:red;'>HELLO "+"hej"+"</h3>"+"<" +
                "<form method='GET'><input type='text' name='name'/><input type='submit'/></form></html>";
    }


    //Vet fortfarande bara hur man läser från en jsonfil från javascript så löser det på ett liknande sätt som kommentarsidan.
    /**
     * Get the statistics from the database and convert the info to a json file for the javascript to read and build
     * a dynamic page based on.
     */
    private void sqliteToJson(){
        //do some % chart later, amount in category divided by all values in total
    }






}