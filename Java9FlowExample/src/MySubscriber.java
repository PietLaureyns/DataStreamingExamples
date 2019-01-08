import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Flow;

public class MySubscriber extends SubscriberAbstract {

    private Flow.Subscription subscription;
    private String fileName;
    private String name;
    private List<String> formattedData = new ArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public MySubscriber(String name, String fileName){
        this.name = name;
        this.fileName = fileName;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println(name+": Subscribed");
        this.subscription = subscription;
        this.subscription.request(1); //requesting data from publisher
    }

    @Override
    public void onNext(Object item) {
        System.out.println(name+": Processing( " + item.toString()+" )");
        writeStringToCsv(dateFormat.format(new Date())+ ";"+item.toString().split("/")[0]);
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(name+": Error");
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println(name+": Completed");
    }

    @Override
    public void writeToCsv(){
        if(!formattedData.isEmpty()){
            System.out.println(name+": Start writing to csv");
            FileWriter fw;
            try {
                fw = new FileWriter(fileName, true);
                for (String s : formattedData) {
                    fw.write("\n"+s);
                }
                fw.close();
                System.out.println(name+": Done writing");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println(name+": File Not Found.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println(name+": No Data to write.");
        }
    }

    private void writeStringToCsv(String line){
        if(!line.isEmpty()){
            FileWriter fw;
            try {
                fw = new FileWriter(fileName, true);
                fw.write("\n"+line);

                fw.close();
                System.out.println(name+": Done writing to csv");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println(name+": File Not Found.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println(name+": No Data to write.");
        }
    }
}
