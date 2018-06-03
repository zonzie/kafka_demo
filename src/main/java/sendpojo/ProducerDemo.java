package sendpojo;


import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author zonzie
 * @date 2018/4/17 17:04
 */
public class ProducerDemo extends Thread{
    //指定具体的topic
    private String topic;

    //构造函数
    public ProducerDemo(String topic){
        this.topic = topic;
    }

    @Override
    @SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
    public void run(){
        //创建一个producer对象
        Producer producer = createProducer();
        int i=0;
        while(true){
            //使用producer发送数据
            producer.send(new KeyedMessage<Integer, Object>(this.topic, new MsgEntity(i, "www.baidu.com?id="+i)));
            System.out.println("Producer发送数据：" + i);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    @SuppressWarnings({ "deprecation", "rawtypes" })
    private Producer createProducer(){
        Properties prop = new Properties();
        //声明zk
        prop.put("zookeeper.connect", "192.168.198.128:2181");
        // 指定message的序列化方法，用户可以通过实现kafka.serializer.Encoder接口自定义该类
        // 默认情况下message的key和value都用相同的序列化，但是可以使用"key.serializer.class"指定key的序列化
        prop.put("serializer.class", MsgEntity.class.getName());
        // broker的地址
        prop.put("metadata.broker.list", "192.168.198.128:9092");
        // 这个参数用于通知broker接收到message后是否向producer发送确认信号
        //  0 - 表示producer不用等待任何确认信号，会一直发送消息，否则producer进入等待状态
        // -1 - 表示leader状态的replica需要等待所有in-sync状态的replica都接收到消息后才会向producer发送确认信号，再次之前producer一直处于等待状态
        prop.put("request.required.acks", "1");

        prop.put("producer.type", "async");
        prop.put("batch.num.messages", "5");
        return new Producer(new ProducerConfig(prop));
    }

    public static void main(String[] args) {
        new ProducerDemo("test").start();
    }
}
