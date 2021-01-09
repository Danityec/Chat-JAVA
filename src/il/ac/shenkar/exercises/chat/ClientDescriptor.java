package il.ac.shenkar.exercises.chat;

import java.io.IOException;

/**
 * class ClientDescriptor implements from StringConsumer and StringProducer
 * class ClientDescriptor charge on add consumer to chat
 * class ClientDescriptor charge on remove consumer to chat
 * class ClientDescriptor charge to publish about when user left the chat, joined the chat and wright message
 * @params consumer
 * @params name
 */
public class ClientDescriptor implements StringConsumer, StringProducer {
    private StringConsumer consumer = null;
    private String name = "";
    /*
    methods addConsumer add consumer to chat
     */
    public void addConsumer(StringConsumer sc) {
        consumer = sc;
    }

    /*
    methods removeConsumer remove consumer from the chat
     */
    public void removeConsumer(StringConsumer sc) {
        consumer = null;
    }

    /*
     methods consume publish in chat to all users when user left the chat, joined the chat and wright message
    */
    public void consume(String str) {
        if (str.equals("disconnect")) {
            try {
                consumer.consume(name + " left the chat");
                removeConsumer(consumer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (name.equals("")) {
            try {
                name = str;
                consumer.consume(name + " joined the chat");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            try {
                consumer.consume(name + ": " + str);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
