package il.ac.shenkar.exercises.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

/**
 * clientGUI class provides the VIEW section of the chat application.
 */
public class ClientGUI implements StringConsumer, StringProducer{
    private JFrame chatApp;
    private String name ="";
    private JPanel contentPanel;

    private Socket socket = null;
    private StringConsumer consumer;
    private ConnectionProxy connectionProxy;
    private JTextArea messageBoard = new JTextArea(30,30);

    /*
     * the constructor initializes the frame and
     * dictates action to be preformed when the window is closed
     */
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

    /*
     * start sets the window visibility
     */
    public void start() {
        chatApp.setVisible(true);
    }

    /*
     * homePage displays the first page of the application, the sign up page
     * here a user enters a name and joins the chat
     */
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

    /*
     * chatPage displays the chat page of the application
     * here a socket is created and the user is added to the chats server side
     * messages from the messageBoard are displayed and
     * there is a text field for the user to send his own messages
     */
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

    /*
     * consume adds new messages to the messageBoard
     */
    public void consume(String str) {
        synchronized (messageBoard) {
            messageBoard.append(str + "\n");
        }
    }

    /*
     * addConsumer adds a new user to the application
     */
    public void addConsumer(StringConsumer sc) {
        consumer = sc;
    }

    /*
     * removeConsumer removes a user from the application
     */
    public void removeConsumer(StringConsumer sc) {
        consumer = null;
    }
}