package il.ac.shenkar.exercises.chat;
import java.net.Socket;

public class ConnectionProxy extends Thread implements StringConsumer, StringProducer {

    public ConnectionProxy(Socket socket) { }

    public void addConsumer(StringConsumer sc) {
        System.out.println("add consumer");
    }

    public void removeConsumer(StringConsumer sc){
        System.out.println("remove consumer");
    }

    public void consume(String str){
        System.out.println("consume");
    }
}
