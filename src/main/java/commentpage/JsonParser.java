package commentpage;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public void writeJsonToFile(List<String> messages){

        List<Comment> comments = new ArrayList<>();

        for(String s : messages){
            comments.add(new Comment(s));
        }

        ObjectMapper mapper = new ObjectMapper();

        try{
            mapper.writeValue(new File("./web/comments.json"), comments);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}