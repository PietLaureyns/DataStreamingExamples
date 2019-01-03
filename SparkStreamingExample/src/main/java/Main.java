//import io.netty.handler.codec.string.StringDecoder;
import kafka.Kafka;
import kafka.serializer.Decoder;
import kafka.serializer.StringDecoder;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.StorageLevels;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.*;
import org.apache.spark.streaming.kafka.KafkaUtils;
import scala.Tuple2;

import java.util.*;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("JavaNetworkWordCount").setMaster("local[*]");
        JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(2000));

        JavaReceiverInputDStream<String> lines = ssc.socketTextStream("localhost", 9999, StorageLevels.MEMORY_AND_DISK_SER);
        JavaDStream<String> words = lines.flatMap(x -> Arrays.asList(Pattern.compile(" ").split(x)).iterator());
        JavaPairDStream<String, Integer> wordCounts = words.mapToPair(s -> new Tuple2<>(s,1)).reduceByKey((i1,i2) -> i1 + i2);

        wordCounts.print();
        ssc.start();
        try {
            ssc.awaitTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        SparkConf conf = new SparkConf().setAppName("kafka-sandbox").setMaster("local[*]");
//        JavaSparkContext sc = new JavaSparkContext(conf);
//        JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(2000));
//
//        Set<String> topics = Collections.singleton("mytopic");
//        Map<String, String> kafkaParams = new HashMap<>();
//        kafkaParams.put("metadata.broker.list", "localhost:9092");
//
//        JavaPairInputDStream<String, String> directKafkaStream = KafkaUtils.createDirectStream(ssc, String.class, String.class, Decoder.class, Decoder.class, kafkaParams, topics);
//
//        directKafkaStream.foreachRDD(rdd -> {
//            System.out.println("--- New RDD with " + rdd.partitions().size()
//                    + " partitions and " + rdd.count() + " records");
//            rdd.foreach(record -> System.out.println(record._2));
//        });
//
//        ssc.start();
//        try {
//            ssc.awaitTermination();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
