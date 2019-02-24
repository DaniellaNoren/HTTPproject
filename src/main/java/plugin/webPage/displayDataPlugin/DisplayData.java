package plugin.webPage.displayDataPlugin;

import HTTPcommunication.HTTPRequest;
import HTTPcommunication.HTTPResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import plugin.webPage.WebPagePath;
import plugin.storage.getDataPlugin.SQLiteStatistics;
import plugin.webPage.WebPagePlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This plugin leads to a page with a visual representation of the statistics that are saved with the "GetData" plugin.
 * It's a separate plugin so it can be a choice whether the statistics should only be stored or also displayed.
 */

@WebPagePath("/statistics")
public class DisplayData implements WebPagePlugin {

    @Override
    public HTTPResponse response(HTTPRequest httpRequest) {

        //write the data in the sqlite database to a jsonfile the javascript can read and then display on the page.
        sqliteToJson();

        //send http respons
        String htmlDocument = htmlDocument();

        byte[] body = htmlDocument.getBytes();
        HTTPResponse response = new HTTPResponse();
        response.setStatus(200).setMessage("OK").setContentType("text/html").setContentLength(body.length).setBody(body);

        return response;
    }


    /**
     * Get the statistics from the database and convert the info to a json file for the javascript to read and build
     * a dynamic page based on.
     */
    private void sqliteToJson(){ //Do a check if database exist later
        List statistics = SQLiteStatistics.getInstance().getAllData();

        try {
            new ObjectMapper().writeValue(new File("./web/statistics.json"), statistics);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //This code is the exact same as in the Statistics.html file. Can't figure out for now how to load the file into a
    //http response so now a string can be loaded with this method instead. To read this better just look at the real html file.
    private String htmlDocument(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "\n" +
                "    <style>\n" +
                "        #all-statistics-containers{\n" +
                "            display: flex;\n" +
                "            flex-direction: column;\n" +
                "            align-items: center;\n" +
                "        }\n" +
                "        .container{\n" +
                "            background-color: rgb(204, 204, 204);\n" +
                "            margin: 30px 0;\n" +
                "            max-width:800px;\n" +
                "        }\n" +
                "        .title{\n" +
                "            text-align: center;\n" +
                "            margin: 0 10%;\n" +
                "            padding: 5%\n" +
                "        }\n" +
                "        #statistics-example, #statistics-real{\n" +
                "            box-sizing: border-box;\n" +
                "            display: flex;\n" +
                "            justify-content: space-evenly;\n" +
                "            align-items: flex-end;\n" +
                "            height: 300px;\n" +
                "            padding: 3%;\n" +
                "            margin: 3% 1%;\n" +
                "        }\n" +
                "        .box{\n" +
                "            background-color: rgb(54, 160, 192);\n" +
                "            display: flex;\n" +
                "            align-items: flex-end;\n" +
                "        }\n" +
                "        .box p{\n" +
                "            color: white;\n" +
                "            margin: 0;\n" +
                "        }\n" +
                "    </style>\n" +
                "\n" +
                "\n" +
                "\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div id=\"all-statistics-containers\">\n" +
                "\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"title\">\n" +
                "            <p style=\"color: rgb(8, 158, 3)\">Real graph</p>\n" +
                "            <p>Based on all http request the server receives, this diagram shows in percentage which hours of the day the server gets the most/least http requests.</p>\n" +
                "            <p>This graph shows the real representation from the server. The data started to count from when the database was created (when the plugin got loaded).</p>\n" +
                "            <p>The numbers represents the hours of the day</p>\n" +

                "        </div>\n" +
                "\n" +
                "        <div id=\"statistics-real\">\n" +
                "            <!--The graph pillars that should be where this line of code is\n" +
                "        are generated in javascript since the real graphics should\n" +
                "        be here-->\n" +
                "        </div>\n" +
                "\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "\n" +
                "    <div class=\"container\">\n" +
                "\n" +
                "        <!--Hard coded example graph-->\n" +
                "\n" +
                "        <div class=\"title\">\n" +
                "            <p style=\"color: red\">Exempel graf</p>\n" +
                "            <p>Eftersom den riktiga grafen ovan troligtvis kommer få 100% på en pelare och 0% på alla andra när det här programmet rättas eller testas av någon är det här ett exempel på hur det skulle kunnat se ut om pluginnet varit aktivt en längre tid.</p>\n" +
                "        </div>\n" +
                "\n" +
                "        <!--107% is the true 100% and 7% is the true 0%.\n" +
                "            It's like that so that each pillar gets a square bottom-->\n" +
                "        <div id=\"statistics-example\">\n" +
                "            <div class=\"box\" style=\"height:07%\"><p>00</p></div>\n" +
                "            <div class=\"box\" style=\"height:7%\"><p>01</p></div>\n" +
                "            <div class=\"box\"style=\"height:7%\"><p>02</p></div>\n" +
                "            <div class=\"box\"style=\"height:10%\"><p>03</p></div>\n" +
                "            <div class=\"box\"style=\"height:12%\"><p>04</p></div>\n" +
                "            <div class=\"box\"style=\"height:16%\"><p>05</p></div>\n" +
                "            <div class=\"box\"style=\"height:14%\"><p>06</p></div>\n" +
                "            <div class=\"box\"style=\"height:9%\"><p>07</p></div>\n" +
                "            <div class=\"box\"style=\"height:7%\"><p>08</p></div>\n" +
                "            <div class=\"box\"style=\"height:7%\"><p>09</p></div>\n" +
                "            <div class=\"box\"style=\"height:7%\"><p>10</p></div>\n" +
                "            <div class=\"box\"style=\"height:7%\"><p>11</p></div>\n" +
                "            <div class=\"box\"style=\"height:7%\"><p>12</p></div>\n" +
                "            <div class=\"box\"style=\"height:7%\"><p>13</p></div>\n" +
                "            <div class=\"box\"style=\"height:7%\"><p>14</p></div>\n" +
                "            <div class=\"box\"style=\"height:8%\"><p>15</p></div>\n" +
                "            <div class=\"box\"style=\"height:15%\"><p>16</p></div>\n" +
                "            <div class=\"box\"style=\"height:10%\"><p>17</p></div>\n" +
                "            <div class=\"box\"style=\"height:19%\"><p>18</p></div>\n" +
                "            <div class=\"box\"style=\"height:57%\"><p>19</p></div>\n" +
                "            <div class=\"box\"style=\"height:30%\"><p>20</p></div>\n" +
                "            <div class=\"box\"style=\"height:20%\"><p>21</p></div>\n" +
                "            <div class=\"box\"style=\"height:7%\"><p>22</p></div>\n" +
                "            <div class=\"box\"style=\"height:7%\"><p>23</p></div>\n" +
                "        </div>\n" +
                "\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<script>\n" +
                "    var request = new XMLHttpRequest();\n" +
                "    request.onreadystatechange = function() {\n" +
                "        if (this.readyState == 4 && this.status == 200) {\n" +
                "            var jsonObject = JSON.parse(this.responseText);\n" +
                "            //console.log(jsonObject);\n" +
                "            generateStatistics(jsonObject);\n" +
                "        }\n" +
                "    };\n" +
                "    request.open(\"GET\", \"statistics.json\", true);\n" +
                "    request.send();\n" +
                "    function generateStatistics(jsonObject){\n" +
                "        var container = document.getElementById(\"statistics-real\");\n" +
                "        for(var i = 0; i < 24; i++){\n" +
                "            var pillar = document.createElement(\"div\");\n" +
                "            pillar.setAttribute(\"class\", \"box\");\n" +
                "            pillar.setAttribute(\"style\", \"height:\" + jsonObject[i].counter + \"%\");\n" +
                "            var pillarText = document.createElement(\"p\")\n" +
                "            if(i < 10){\n" +
                "                pillarText.innerHTML = \"\" + 0 + i;\n" +
                "            }\n" +
                "            else{\n" +
                "                pillarText.innerHTML = i;\n" +
                "            }\n" +
                "            pillar.appendChild(pillarText);\n" +
                "            container.appendChild(pillar);\n" +
                "        }\n" +
                "    }\n" +
                "</script>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }






}