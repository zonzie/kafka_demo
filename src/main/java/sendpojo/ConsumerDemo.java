package sendpojo;

import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.Properties;

/**
 * @author zonzie
 * @date 2018/4/17 18:39
 */
public class ConsumerDemo {
    private static Properties props;
    private static ConsumerConnector consumer;
    static{
        if(props == null){
            props = new Properties();
            //zookeeper 配置
            props.put("zookeeper.connect", "192.168.198.128:2181");

            //group 代表一个消费组
            props.put("group.id", "group11");

            //指定客户端连接zookeeper的最大超时时间
//            props.put("zookeeper.connection.timeout.ms", );
            //rebalance.max.retries * rebalance.backoff.ms > zookeeper.session.timeout.ms
            //  连接zk的session超时时间
//            props.put("zookeeper.session.timeout.ms", Constants.ZOOKEEPER_SESSION_TIMEOUT);
            props.put("zookeeper.sync.time.ms", "200");
            props.put("auto.commit.interval.ms", "1000");
            props.put("auto.offset.reset", "smallest");

//            props.put("rebalance.max.retries", "5");
            props.put("rebalance.backoff.ms", "1200");
            //序列化类
            props.put("serializer.class", MsgEntity.class.getName());
        }
    }

    public ConsumerConnector getConsumer(){
        if(consumer == null){
            consumer = kafka.consumer.Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
        }
        return consumer;
    }
}
