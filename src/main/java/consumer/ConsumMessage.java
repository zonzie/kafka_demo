package consumer;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import pojo.User;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author zonzie
 * @date 2018/4/17 14:13
 */
public class ConsumMessage {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.198.128:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

//        props.put("serializer.class", MsgEntity.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                String value = record.value();
                User user = null;
                try {
                    user = JSON.parseObject(value, User.class);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), user);
            }
        }
    }
}
