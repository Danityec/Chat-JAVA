package il.ac.shenkar.exercises.chat;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionProxy extends Thread implements StringConsumer, StringProducer {
    private  StringConsumer consumer;
    private  Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;


    public ConnectionProxy(Socket skt) throws IOException {
        socket = skt;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());

    }

    public void addConsumer(StringConsumer sc) {
        consumer = sc;
        System.out.println("add consumer");
    }

    public void removeConsumer(StringConsumer sc){
        consumer = null;
        System.out.println("remove consumer");
    }

    public void consume(String str) throws IOException {
        dos.writeUTF(str);
        System.out.println("consume");
    }

    public void run(){
        try{
            while ((!socket.isClosed())){
                String redMsg = dis.readUTF();
                consumer.consume(redMsg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
