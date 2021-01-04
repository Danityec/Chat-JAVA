package il.ac.shenkar.exercises.chat;

public class ClientDescriptor implements StringConsumer, StringProducer{
    private  StringConsumer consumer= null;
    private  String name = null;

    public void addConsumer(StringConsumer sc) {    // חיבור הסוקט= חיבור המתמש
        consumer = sc;
        System.out.println("add consumer");
    }

    public void removeConsumer(StringConsumer sc){   // יציאה מהסוקט - מהחלון שיחה
        consumer = null;
        System.out.println("remove consumer");
    }

    public void consume(String str){           // מי עזב את הצט ומי נכנס לצט- ניהול משתמשים

        System.out.println("consume");
    }
}
