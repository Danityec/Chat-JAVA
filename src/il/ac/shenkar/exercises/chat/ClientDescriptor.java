package il.ac.shenkar.exercises.chat;

import java.io.IOException;

public class ClientDescriptor implements StringConsumer, StringProducer {
    private StringConsumer consumer = null;
    private String name = null;

    public void addConsumer(StringConsumer sc) {    // חיבור הסוקט= חיבור המתמש
        consumer = sc;
    }

    public void removeConsumer(StringConsumer sc) {   // יציאה מהסוקט - מהחלון שיחה
        consumer = null;
    }

    public void consume(String str) {           // מי עזב את הצט ומי נכנס לצט- ניהול משתמשים
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
