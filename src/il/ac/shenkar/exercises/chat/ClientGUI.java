package il.ac.shenkar.exercises.chat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI implements StringConsumer, StringProducer {

    public ClientGUI() {
        frame = new JFrame("Cost Manager");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                frame.setVisible(false);
                frame.dispose();
                System.exit(0);
            }
        });
    }

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
