import java.util.concurrent.Flow;

public abstract class SubscriberAbstract implements Flow.Subscriber {

    public abstract void writeToCsv();
}
