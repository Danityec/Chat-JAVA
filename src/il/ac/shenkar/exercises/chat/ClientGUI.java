package il.ac.shenkar.exercises.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

public class ClientGUI implements StringConsumer, StringProducer{
    private JFrame chatApp;
    private String name ="";
    private JPanel contentPanel;

    private Socket socket = null;
    private StringConsumer consumer;
    private ConnectionProxy connectionProxy;
    private JTextArea messageBoard = new JTextArea(30,30);

    public ClientGUI() {
        chatApp = new JFrame("Chat App");

        chatApp.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                chatApp.setVisible(false);
                chatApp.dispose();
                try {
                    connectionProxy.consume("disconnect");
                } catch (IOException ioException) {}
                if (socket != null)
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                System.exit(0);
            }
        });

        contentPanel = new JPanel();
        contentPanel.setBackground(Color.lightGray);
        contentPanel.setLayout(new BorderLayout());

        Container container = chatApp.getContentPane();
        container.setLayout(new BorderLayout());
        container.add(contentPanel);

        homePage();
    }

    public void start() {
        chatApp.setVisible(true);
    }

    public void homePage() {
        contentPanel.removeAll();
        chatApp.setSize(500, 150);

        JLabel nameLbl = new JLabel("Name");
        JTextField nameTxt = new JTextField();

        JButton submitBtn = new JButton("Add to chat");

        JPanel formArea = new JPanel();
        formArea.setLayout(new GridLayout(3,1,0,0));

        formArea.add(nameLbl);
        formArea.add(nameTxt);
        formArea.add(submitBtn);

        contentPanel.add(formArea);

        ActionListener addAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                name = nameTxt.getText();
                chatPage();
            }
        };
        submitBtn.addActionListener(addAction);
    }

    public void chatPage() {
        contentPanel.removeAll();
        chatApp.setSize(500, 750);

        try {
            socket = new Socket("127.0.0.1",1400);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            connectionProxy = new ConnectionProxy(socket);
            connectionProxy.addConsumer(this);
            connectionProxy.consume(name);
            connectionProxy.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel chatArea = new JPanel();
        chatArea.setLayout(new GridLayout(2,1,0,0));

        messageBoard.setEditable(false);
        messageBoard.setBackground(Color.lightGray);

        JPanel formArea = new JPanel();
        formArea.setLayout(new GridLayout(1,2,0,0));
        JTextField msgTxt = new JTextField();
        JButton submitBtn = new JButton("Send");

        formArea.add(msgTxt);
        formArea.add(submitBtn);

        chatArea.add(messageBoard);
        chatArea.add("South", formArea);

        contentPanel.add(chatArea);

        ActionListener sendAction = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String msg = msgTxt.getText();
                try {
                    connectionProxy.consume(msg);
                    msgTxt.setText("");

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
        submitBtn.addActionListener(sendAction);
    }

    public void consume(String str) {
        synchronized (messageBoard) {
            messageBoard.append(str + "\n");
        }
    }

    public void addConsumer(StringConsumer sc) {
        consumer = sc;
    }

    public void removeConsumer(StringConsumer sc) {
        consumer = null;
    }
}