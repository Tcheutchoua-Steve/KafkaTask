/**
 * Created by tcheutchoua on 3/29/16.
 */

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HighLevelConsumer {
    private final ConsumerConnector consumer;
    private final String topic;
    private  ExecutorService executor;

    //KafkaConsumerConfig confgiuration = new KafkaConsumerConfig();

    public HighLevelConsumer() {
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                createConsumerConfig());
        this.topic = KafkaConsumerConfig.TOPIC;
    }


    public void run(int a_numThreads) {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(a_numThreads));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        // now launch all the threads
        //
        executor = Executors.newFixedThreadPool(a_numThreads);

        // now create an object to consume the messages
        //
        int threadNumber = 0;
        for (final KafkaStream stream : streams) {
            executor.submit(new ReadKafkaMessage(stream, threadNumber));
            threadNumber++;
        }
    }

    private static ConsumerConfig createConsumerConfig() {
        Properties props = new Properties();
        props.put("zookeeper.connect", KafkaConsumerConfig.ZOOKEEPER);
        props.put("group.id", KafkaConsumerConfig.GROUP_ID);
        props.put("zookeeper.session.timeout.ms", KafkaConsumerConfig.CONNECTION_TIMEOUT);
        props.put("zookeeper.sync.time.ms", KafkaConsumerConfig.ZOOKEEPER_SYNC_TIME_MS);
        props.put("auto.commit.interval.ms", KafkaConsumerConfig.COMMIT_INTERVAL_MS);

        return new ConsumerConfig(props);
    }

    public static void main(String[] args) {
       // String zooKeeper = KafkaConsumerConfig.ZOOKEEPER;
       // String groupId = KafkaConsumerConfig.GROUP_ID;
       // String topic = args[2];
        int threads = Integer.parseInt(args[3]);

        HighLevelConsumer example = new HighLevelConsumer();
        example.run(threads);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {

        }
        example.shutdown();
    }



    public void shutdown() {
        if (consumer != null) consumer.shutdown();
        if (executor != null) executor.shutdown();
        try {
            if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                System.out.println("Timed out waiting for consumer threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted during shutdown, exiting uncleanly");
        }
    }
}
