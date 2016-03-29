import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by tcheutchoua on 3/29/16.
 */
public class ReadKafkaMessage implements Runnable {

    // Kafka variables
    private KafkaStream stream;
    private int threadNumber;

    //Network declarations
    /*public static void sendMessage(String message) throws IOException {
        Socket msgSocket = new
               Socket(KafkaConsumerConfig.MESSAGE_HOST ,KafkaConsumerConfig.MESSAGE_HOST_PORT);
        DataOutputStream out = new DataOutputStream (msgSocket.getOutputStream());
        out.writeUTF(message);
        out.flush();
        out.close ();
        msgSocket.close ();
    }*/





    public ReadKafkaMessage(KafkaStream a_stream, int a_threadNumber){
        threadNumber = a_threadNumber;
        stream = a_stream;
    }
    @Override
    public void run() {

        // consuming a stream of message from kafka Broker
        ConsumerIterator<byte[], byte[]> it = stream.iterator();

        try {
            Socket msgSocket = new
                    Socket(KafkaConsumerConfig.MESSAGE_HOST ,KafkaConsumerConfig.MESSAGE_HOST_PORT);
            DataOutputStream out = new DataOutputStream (msgSocket.getOutputStream());

            // Getting each message from the Kafka stream of messages
            while (it.hasNext()) {

                System.out.println("Thread " + threadNumber + ": " + new String(it.next().message()));

                    // Send the message to the specified host.
                    out.writeUTF(new String(it.next().message()));
                    out.flush();

            }
            out.close ();
            msgSocket.close ();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Thread shuts down because there are no more messages in stream
        System.out.println("Shutting down Thread: " + threadNumber);
    }
}
