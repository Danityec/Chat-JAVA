package il.ac.shenkar.exercises.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionProxy extends Thread implements StringConsumer, StringProducer {
    private StringConsumer consumer;
    private Socket socket;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;


    public ConnectionProxy(Socket skt) throws IOException {
        socket = skt;
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    public void addConsumer(StringConsumer sc) {
        consumer = sc;
    }

    public void removeConsumer(StringConsumer sc) {
            consumer = null;
    }

    public void consume(String str) throws IOException {
        dos.writeUTF(str);
    }

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
