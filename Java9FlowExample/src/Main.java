import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //De code hieronder is van het voorbeeld op de bachelorproef,

        //Publisher aanmaken
        MyPublisher publisher = new MyPublisher();

        //Subscriber aanmaken
        Subscriber subscriber = new Subscriber();

        //Subscriber abonneren op de Publisher
        publisher.subscribe(subscriber);

        //Een lijst van data via de publisher verzenden
        String[] items = {"1", "x", "2", "x", "3", "x"};
        for(String item : items){
            publisher.emit(item);
        }
        publisher.close();


//        // Create Publisher
//        MyPublisher pub = new MyPublisher();
//
//        // Register Subscriber
//        MySubscriber subs = new MySubscriber("Subscriber1", "TotalAvailableParkingSpots.csv");
//        MyOtherSubscriber subs2 = new MyOtherSubscriber("Subscriber2", "TotalOccupiedParkingSpots.csv");
//
//        // Subscribe
//        pub.subscribe(subs);
//        pub.subscribe(subs2);
//        pub.startLoop(12,10000);
    }
}
