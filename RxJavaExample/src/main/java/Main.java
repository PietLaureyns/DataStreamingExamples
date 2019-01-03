import com.google.gson.JsonArray;
import io.reactivex.*;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        System.out.println("Start: "+new Date());
        Flowable<Integer> flowable = Flowable.create(flowableEmitter -> {
            try {
                String url = "https://datatank.stad.gent/4/mobiliteit/bezettingparkingsrealtime.json";
                int counter = 0;
                while (true) {
                    counter++;
                    if (counter >= 10000) { //We loopen 10.000 keer.
                        break;
                    }
                    JsonArray json = new UrlReader().getJsonArrayFromUrl(url);
                    //ParkingData class haalt de nodige data uit de json-data.
                    ParkingData parkingData = new ParkingData(json);

                    //Hier schrijven we het aantal bezette parkeerplaatsen weg naar onze subscriber.
                    flowableEmitter.onNext(parkingData.getTotalOccupied());
                }
                //Na de loop roepen we de onComplete() methode in de subscriber op.
                flowableEmitter.onComplete();
            } catch (Exception e) {
                flowableEmitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER); //Er wordt ook een backpressureStrategy meegegeven worden.

        //Een custom subscriber class die de Subscriber interface implementeert initialiseren.
        MySubscriber subscriber = new MySubscriber();
        //Met deze lijn code linken we de Subscriber aan de Flowable en wordt het gehele proces gestart.
        flowable.subscribe(subscriber);
        System.out.println("End: "+new Date());
    }
}
