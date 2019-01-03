import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlReader {

    public UrlReader(){

    }

    public JsonObject getJsonObjectFromUrl(String url){
        String json = readUrl(url);
        JsonParser parser = new JsonParser();
        return (JsonObject) parser.parse(json);
    }


    public JsonArray getJsonArrayFromUrl(String url){
        String json = readUrl(url);
        JsonParser parser = new JsonParser();
        return (JsonArray) parser.parse(json);
    }

    private String readUrl(String urlString) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
            return buffer.toString();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

