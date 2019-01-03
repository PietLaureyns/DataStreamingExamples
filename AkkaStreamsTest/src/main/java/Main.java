import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.*;
import akka.stream.javadsl.Flow;
import com.google.gson.JsonArray;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class Main {

    public static String previous = "";

    public static void main(String[] args) {

        ActorSystem system = ActorSystem.create("Actor");
        Materializer materializer = ActorMaterializer.create(system);

        //Source aanmaken met een list van integers.
        Source<Integer, NotUsed> source = Source.range(1,10);

        //Sink aanmaken die alle integers die hij ontvangt zal optellen.
        Sink<Integer, CompletionStage<Integer>> sink =
                Sink.fold(0, (next, total) -> total += next);

        //RunnableGraph wordt aangemaakt met de aangemaakte sink en source.
        RunnableGraph<CompletionStage<Integer>> runnable
                = source.toMat(sink,Keep.right());

        //De RunnableGraph wordt gestart met de .run() methode.
        //Er moet ook een materializer meegegeven worden voordat het kan runnen.
        CompletionStage<Integer> sum = runnable.run(materializer);

        //print het resultaat uit, het resultaat zal 55 zijn.
        sum.thenAccept(System.out::println);










//        System.out.println("Start: "+new Date());
//
//        UrlReader urlReader = new UrlReader();
//
//        Sink<String, CompletionStage<String>> fold = Sink.fold("", (next, total) -> total += next);
//        int counter = 0;
//        while(true){
//            counter++;
//            if(counter > 5000){
//                break;
//            }
//            //https://datatank.stad.gent/4/mobiliteit/reistijden.json
//            JsonArray json = urlReader.getJsonArrayFromUrl("https://datatank.stad.gent/4/mobiliteit/bezettingparkingsrealtime.json");
//            ParkingData data = new ParkingData(json);
//            Source<String, NotUsed> source = Source.from(data.totalParkingen);
//
//            CompletionStage<String> stringCompletionStage = source.runWith(fold, materializer);
//            int finalCounter = counter;
//            stringCompletionStage.thenAccept(s -> {
//                System.out.println(finalCounter + ": "+s);
//                if(!s.equals(previous)){
//                    System.out.println("---------------------------------------------------------------------UPDATE");
//                    writeToCsv(s);
//                    previous = s;
//                }
//            });
//
//        }
//        System.out.println("End: "+new Date());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    private static void writeToCsv(String data) {
        FileWriter fw;
        try {
            fw = new FileWriter("TotalOccupied.csv", true);
            System.out.println("Writing " + data + " to TotalOccupied.csv");
            fw.write("\n"+ data +" from "+ new Date());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
