package il.ac.shenkar.exercises.chat;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionProxy extends Thread implements StringConsumer, StringProducer {
    private  StringConsumer _consumer;
    private  Socket _socket;
    private DataInputStream _dis;
    private DataOutputStream _dos;


    public ConnectionProxy(Socket socket) throws IOException {
        _socket = socket;
        _dis = new DataInputStream(_socket.getInputStream());
        _dos = new DataOutputStream(_socket.getOutputStream());

    }

    public void addConsumer(StringConsumer sc) {
        _consumer = sc;
        System.out.println("add consumer");
    }

    public void removeConsumer(StringConsumer sc){
        _consumer = null;
        System.out.println("remove consumer");
    }

    public void consume(String str) throws IOException {
        _dos.writeUTF(str);
        System.out.println("consume");
    }

    public void run(){
        try{
            while ((!_socket.isClosed())){
                String redMsg = _dis.readUTF();
                _consumer.consume(redMsg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
