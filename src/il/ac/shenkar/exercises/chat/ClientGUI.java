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

    //GUI components signUp
    private JFrame chatApp;
    private JButton submitButton;
    private JPanel chatPanel;
    private JTextField clientInput;
    private String name ="";

    private StringConsumer consumer;

    public ClientGUI() {
        chatApp = new JFrame("Chat App");

        chatApp.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                chatApp.setVisible(false);
                chatApp.dispose();
                ConnectionProxy.consume("disconnect");
                closeConnection(socket);
                System.exit(0);
            }
        });
    }





    // set the UI of sign-up
    public void signUp() {
        //set background color to panel1 and panel 2
        panel1.setBackground(Color.PINK);
        panel2.setBackground(Color.PINK);

        frameSignUp.setLayout(new FlowLayout());

        //set the fonts in signup frame
        headSignUp.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        submitButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        submitButton.setBackground(Color.magenta);

        //add the text box,button and header to the panels
        panel1.add(headSignUp);
        panel2.add(clientDeatils);
        panel2.add(submitButton);

        //add the panels to the frame
        frameSignUp.add(panel1);
        frameSignUp.add(panel2);

        //set the size of the frame(window)
        frameSignUp.setSize(350, 200);

        //set the sign-up window to be visible
        frameSignUp.setVisible(true);

    }

    //set the UI of the chat
    public void chat() {
        //set the background colors
        displayText.setBackground(Color.PINK);
        writeText.setBackground(Color.PINK);
        sendButton.setBackground(Color.magenta);

        //set the fonts
        sendButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        headChat.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));

        frameChat.setLayout(new FlowLayout());

        //add the text box,button and header to the places in the window
        displayHead.add(headChat);
        displayText.add(messageBoard);
        writeText.add(textMessage);
        writeText.add(sendButton);

        //add all panels(write and display) to the frame
        frameChat.add(displayHead);
        frameChat.add(displayText);
        frameChat.add(writeText);

        //set the size of the frame(window)
        frameChat.setSize(460, 650);

        //set the sign-up window to be visible
        frameChat.setVisible(true);
    }

    //display the sign-up window to the user
    public void runSignUp() {
        signUp();
        //when submit to the name click
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //save the nickname name the user sent
                nickname = clientDeatils.getText();
                //start the chat
                runChat();
            }
        });
    }
    //run the chat
    public void runChat() {
        try {
            //close sign-up window
            closeSignUp();
            //display the chat window
            chat();
            //open new socket and connect the user to this port
            Socket socket = new Socket("127.0.0.1",1400);
            ConnectionProxy connectionProxy = new ConnectionProxy(socket);
            connectionProxy.addConsumer(this);
            connectionProxy.consume(nickname);
            //start connectionProxy
            connectionProxy.start();

            //when send message button click
            sendButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //display the message to the window
                    String message = textMessage.getText();
                    try {
                        //put the name of the sender before the message
                        connectionProxy.consume(message);
                        textMessage.setText("");

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });

            //Register the listener for the form using the anonymous inner class
            frameChat.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        //when the user disconnect


                    } catch (IOException ioException) {}

                    super.windowClosing(e);
                    //close the window
                    closeChat();
                    //close the socket
                    closeConnection(socket);
                    System.exit(0);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void closeConnection(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
