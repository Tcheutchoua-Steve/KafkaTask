/**
 * Created by tcheutchoua on 3/29/16.
 */
public class KafkaConsumerConfig {


    /******************************************************************************************************************/
    /*******************    Configuratios Involving Kafka Consumer                          ***************************/
    public static final String TOPIC = "topic1";
    public static final String ZOOKEEPER_URL = "localhost";
    public static final int ZOOKEEPER_PORT = 9092;
    public static final String ZOOKEEPER  = ZOOKEEPER_URL +":"+ZOOKEEPER_PORT ;

    // Group id is the group name
    public static final String GROUP_ID = "test-consumer-group" ;
    public static final String CONNECTION_TIMEOUT = "150000";
    public static final String ZOOKEEPER_SYNC_TIME_MS = "1000";
    public static final String COMMIT_INTERVAL_MS = "1000";


    // For best performance, number of threads should be same as number of partitions in Kafka brokers
    public static final int NUMBER_OF_THREADS = 2;
    public static final String TOPIC2 = "topic2";




    /******************************************************************************************************************/
    /*********************************  Configurations  For Sending Messages   ****************************************/
    public static final String MESSAGE_HOST = "localhost";
    public static final int MESSAGE_HOST_PORT = 6060;
    public static final int MESSAGE_HOST_PORT2 = 6070;
}