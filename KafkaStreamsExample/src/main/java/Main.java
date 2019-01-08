import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers","localhost:9092");
        properties.put("client.id", "producer");
        properties.put("key.serializer",
                "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer producer = new KafkaProducer(properties);

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "consumer");
        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.LongDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        Consumer<Long, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("input-topic"));

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords = consumer.poll(10000);
            for(ConsumerRecord record: consumerRecords){
                System.out.println(record.key());
                System.out.println(record.value());
                String woord = record.value().toString().toUpperCase();
                producer.send(new ProducerRecord("output-topic", 0, woord));
            }
            consumer.commitAsync();
        }
    }
}
