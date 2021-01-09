package il.ac.shenkar.exercises.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 * class ConnectionProxy implements from StringConsumer and StringProducer and extend from Thread
 * class ConnectionProxy Responsible for user management
 * class ConnectionProxy charge on remove consumer to chat
 * class ConnectionProxy charge on add user to proxy
 * class ConnectionProxy charge listen to all message in the chat
 * @params consumer
 * @params socket
 * @params dis
 * @params dos
 */
public class ConnectionProxy extends Thread implements StringConsumer, StringProducer {
    private StringConsumer consumer;
    private Socket socket;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    /*
        methods ConnectionProxy is a constructor , Initializing socket, dis and dos.
    */
    public ConnectionProxy(Socket skt) throws IOException {
        socket = skt;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }
    /*
        methods addConsumer add consumer to chat
    */
    public void addConsumer(StringConsumer sc) {
        consumer = sc;
    }
    /*
        methods removeConsumer remove consumer to chat
    */
    public void removeConsumer(StringConsumer sc) {
            consumer = null;
    }
    /*
        methods consume reads from input stream
     */
    public void consume(String str) throws IOException {
        dos.writeUTF(str);
    }
    /*
        methods run listen As long as the socket open to all message
    */
    public void run() {
        try {
            while ((!socket.isClosed())) {
                String msg = dis.readUTF();
                consumer.consume(msg);
            }
            removeConsumer(consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
