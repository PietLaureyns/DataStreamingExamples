import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class MyPublisher implements Flow.Publisher {

    public static SubmissionPublisher<ParkingData> publisher = new SubmissionPublisher<>();

    public static List<SubscriberAbstract> subs = new ArrayList<>();

    @Override
    public void subscribe(Flow.Subscriber subscriber) {
        subs.add((SubscriberAbstract)subscriber);
        publisher.subscribe(subscriber);
    }

    public static void startLoop(int amount, int timeBetweenInMillieSeconds){
        System.out.println("Starting loop on "+subs.size()+" subscribers. Will loop "+amount+" times with a "+timeBetweenInMillieSeconds/1000+" second interval");
        int counter = 0;
        while(true){
            counter++;
            if(counter > amount){
                subs.forEach(SubscriberAbstract::writeToCsv);
                break;
            }
            JsonArray json = getJsonArrayFromUrl("https://datatank.stad.gent/4/mobiliteit/bezettingparkingsrealtime.json");
            ParkingData data = new ParkingData();
            data.addData(json);
            publisher.submit(data);

            try {
                Thread.sleep(timeBetweenInMillieSeconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static JsonArray getJsonArrayFromUrl(String url){
        String json = readUrl(url);
        JsonParser parser = new JsonParser();
        return (JsonArray)  parser.parse(json);
    }

    private static String readUrl(String urlString) {
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
        }
        return null;
    }

    public void emit(String t){

    }

    public void close(){

    }
}
