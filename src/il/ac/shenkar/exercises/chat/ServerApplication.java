package il.ac.shenkar.exercises.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerApplication {
    public static void main(String args[]) {
        ServerSocket server = null;
        MessageBoard mb = new MessageBoard();
        try {
            server = new ServerSocket(1300, 5);  //connecnt new server to port 1300
        } catch (IOException e) {
            System.out.println("error");
        }
        Socket socket = null;
        ClientDescriptor client = null;
        ConnectionProxy connection = null;

        while(true) {
            try {
                socket = server.accept();
                connection = new ConnectionProxy(socket);
                client = new ClientDescriptor();
                connection.addConsumer(client);
                client.addConsumer(mb);
                mb.addConsumer(connection);
                connection.start();
            } catch (IOException e) {
                System.out.println("error");
            }
        }
    }
}
