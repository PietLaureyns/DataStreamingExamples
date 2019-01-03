import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //Iedere 5 sec worden de available parking spots in gent via de publisher naar de subscriber verstuurd.
        //TODO Opslaan, en time loop verbeteren


        //Create Publisher
//        MyPublisher publisher = new MyPublisher();
//
//        //Create Subscriber
//        Subscriber subscriber = new Subscriber();
//
//        //Register Subscriber
//        publisher.subscribe(subscriber);
//
//        //Publish items
//        String[] items = {"1", "x", "2", "x", "3", "x"};
//        for(String item : items){
//            publisher.emit(item);
//        }
//        publisher.close();




        // Create Publisher
        MyPublisher pub = new MyPublisher();

        // Register Subscriber
        MySubscriber subs = new MySubscriber("Subscriber1", "TotalAvailableParkingSpots.csv");
        MyOtherSubscriber subs2 = new MyOtherSubscriber("Subscriber2", "TotalOccupiedParkingSpots.csv");

        // Subscribe
        pub.subscribe(subs);
        pub.subscribe(subs2);
        pub.startLoop(12,10000);
    }
}
