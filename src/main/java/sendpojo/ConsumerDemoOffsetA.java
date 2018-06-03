package sendpojo;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author zonzie
 * @date 2018/4/17 17:07
 */
public class ConsumerDemoOffsetA extends Thread {
    // 指定具体的topic
    private String topic;

    // 构造函数
    public ConsumerDemoOffsetA(String topic) {
        this.topic = topic;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void run(){
        try{
            //构建consumer对象
            ConsumerConnector consumer =createConsumer();
            //构建一个map对象，代表topic-------String：topic名称，Integer：从这个topic中获取多少条记录
            Map<String, Integer> topicCountMap = new HashMap<>();
            //每次获取1条记录
            topicCountMap.put(this.topic, 1);
            //构造一个messageStreams：输入流      --String：topic名称，List获取的数据
            Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams = consumer.createMessageStreams(topicCountMap);
            //获取每次接收到的具体数据
            KafkaStream<byte[], byte[]> stream = messageStreams.get(this.topic).get(0);
            ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
            while(iterator.hasNext()){
                byte[] data = iterator.next().message();

                ByteArrayInputStream ba = new ByteArrayInputStream(data);
                ObjectInputStream os = new ObjectInputStream(ba);
                MsgEntity entity = EntityUtil.bytesToObject(iterator.next().message());

                consumer.commitOffsets();
                System.out.println("ConsumerA接收到的数据：" + entity.getUrl());
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //创建具体的Consumer
    @SuppressWarnings("deprecation")
    private ConsumerConnector createConsumer(){
        Properties prop = new Properties();
        //声明zk
        prop.put("zookeeper.connect", "192.168.198.128:2181");
        //指定这个consumer的消费组,每个组只能获取一次消息
        prop.put("group.id", "group1");
        //smallest和largest(默认)
        //此配置参数表示当此groupId下的消费者,在ZK中没有offset值时(比如新的groupId,或者是zk数据被清空),
        //consumer应该从哪个offset开始消费.
        //largest表示接受接收最大的offset(即最新消息),
        //smallest表示最小offset,即从topic的开始位置消费所有消息.
        prop.put("auto.offset.reset", "smallest");
        prop.put("auto.offset.reset", "smallest");
        prop.put("serializer.class", MsgEntity.class.getName());
        return Consumer.createJavaConsumerConnector(new ConsumerConfig(prop));
    }

    public static void main(String[] args) {
        new ConsumerDemoOffsetA("test").start();
    }
}
