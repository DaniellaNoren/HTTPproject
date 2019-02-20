package parsing;

import com.fasterxml.jackson.databind.ObjectMapper;
import commentpage.Comment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlToJsonFile {

    /**
     * This class reads all comments that are stored in a database and convert the result into a json file.
     * The json file can then be read by the javascript in "commentpage.js" to load all comments into html.
     *
     * There may be a more direct or a better way of doing this than write to and read from a file for the javascript...
     * @param messages a list of all the comments that in this case are stored in a database
     */
    public void writeJsonToFile(List<String> messages){

        List<Comment> comments = new ArrayList<>();

        for(int i = messages.size()-1; i >= 0; i--){
            comments.add(new Comment(messages.get(i)));
        }

        ObjectMapper mapper = new ObjectMapper();

        try{
            mapper.writeValue(new File("./web/comments.json"), comments);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
