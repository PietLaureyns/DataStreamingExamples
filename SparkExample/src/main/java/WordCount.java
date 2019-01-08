import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.RootLogger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.StorageLevels;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Serializable;
import scala.Tuple2;

import java.util.Arrays;
import java.util.regex.Pattern;

public class WordCount implements Serializable {

    public static void main(String[] args) throws InterruptedException{
        //https://stackoverflow.com/questions/35652665/java-io-ioexception-could-not-locate-executable-null-bin-winutils-exe-in-the-ha
        // System.setProperty("hadoop.home.dir", "C:\\Users\\Piet Laureyns\\Desktop");

        SparkConf sparkConf =
                new SparkConf().setAppName("SparkStreamingWordCountVoorbeeld").setMaster("local[*]");
        JavaStreamingContext ssc =
                new JavaStreamingContext(sparkConf, new Duration(10000)); //10 seconden

        //De tekst/data ophalen van de host, in dit geval is het localhost:9999
        JavaReceiverInputDStream<String> lines =
                ssc.socketTextStream("localhost", 9999, StorageLevels.MEMORY_AND_DISK_SER);

        //Roologger doet de rode info logs weg van de output
        // RootLogger rootLogger = (RootLogger) Logger.getRootLogger();
        // rootLogger.setLevel(Level.ERROR);

        //De woorden uit de tekst halen.
        JavaDStream<String> words =
                lines.flatMap(x -> Arrays.asList(Pattern.compile(" ").split(x)).iterator());

        //Optellen hoeveel keer een woord voorkomt.
        JavaPairDStream<String, Integer> wordCounts =
                words.mapToPair(s -> new Tuple2<>(s,1)).reduceByKey((i1,i2) -> i1 + i2);

        //Output printen.
        wordCounts.print();

        ssc.start();
        ssc.awaitTermination();
    }
}



//



