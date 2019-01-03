import java.io.FileReader;
import java.util.concurrent.SubmissionPublisher;

import netscape.javascript.JSObject;
import org.json.JSONArray;

public class Main {

    public static void main(String[] args) {

        // Create Publisher
//        SubmissionPublisher<ParkingData> publisher = new SubmissionPublisher<>();
//
//        JsonObject json = new JsonObject(jsonString);
//
//        // parsing file "JSONExample.json"
//        Object obj = new JSONParser().parse();
//
//        // typecasting obj to JSONObject
//        JSONObject jo = (JSONObject) obj;
//
//        // getting firstName and lastName
//        String firstName = (String) jo.get("firstName");
//        String lastName = (String) jo.get("lastName");
//
//        // Register Subscriber
//        MySubscriber subs = new MySubscriber();
//        publisher.subscribe(subs);
//
//        List<Employee> emps = EmpHelper.getEmps();
//
//        // Publish items
//        System.out.println("Publishing Items to Subscriber");
//        emps.stream().forEach(i -> publisher.submit(i));
//
//        // logic to wait till processing of all messages are over
//        while (emps.size() != subs.getCounter()) {
//            Thread.sleep(10);
//        }
//        // close the Publisher
//        publisher.close();

        System.out.println("Exiting the app");
    }

//    public static void JsonObject{
//
//        Gson gson = new Gson();
//        JsonParser parser = new JsonParser();
//        JsonObject object = (JsonObject) parser.parse(response);// response will be the json String
//        YourPojo emp = gson.fromJson(object, YourPojo.class);
//
//        InputStream is = new URL(url).openStream();
//        try {
//            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//            String jsonText = readAll(rd);
//
//            JSONArray json = new JSONArray(jsonText);
//
//            return json;
//        } finally {
//            is.close();
//        }
//    }



}
