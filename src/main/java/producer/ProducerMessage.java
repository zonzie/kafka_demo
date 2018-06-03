package producer;

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import pojo.User;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @author zonzie
 * @date 2018/4/17 14:03
 */
public class ProducerMessage {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.198.128:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        User user = new User();
        user.setUsername("xiaoming");
        user.setPassword("123456");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 100; i < 200; i+=2) {
            user.setId(i);
            String s = JSONObject.toJSONString(user);
            Future<RecordMetadata> test = producer.send(new ProducerRecord<>("test", s));
            Thread.sleep(1000);
            System.out.println(test.isDone());
        }

        producer.close();
    }
}
