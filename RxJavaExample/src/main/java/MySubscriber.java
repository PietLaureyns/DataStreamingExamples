import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class MySubscriber implements Subscriber<Integer> {

    private Subscription subscription;
    private int previousData = 0;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        if (item != this.previousData) {
            this.writeToCsv(item);
            this.previousData = item;
        }
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Completed");
    }

    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private void writeToCsv(int data) {
        FileWriter fw;
        try {
            fw = new FileWriter("TotalOccupied.csv", true);
            System.out.println("Writing " + data + " to TotalOccupied.csv");
            fw.write("\n" + dateFormat.format(new Date()) + ";" + data);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
