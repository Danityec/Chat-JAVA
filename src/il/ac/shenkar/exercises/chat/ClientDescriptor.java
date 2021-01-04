package il.ac.shenkar.exercises.chat;

public class ClientDescriptor implements StringConsumer, StringProducer{
    private  StringConsumer _consumer;
    private  String _name;

    public void ClientDescriptor(String _name ){_name = "";}

    public void addConsumer(StringConsumer sc) {    // חיבור הסוקט= חיבור המתמש
        _consumer = sc;
        System.out.println("add consumer");
    }

    public void removeConsumer(StringConsumer sc){   // יציאה מהסוקט - מהחלון שיחה
        _consumer = null;

        System.out.println("remove consumer");
    }

    public void consume(String str){           // מי עזב את הצט ומי נכנס לצט

        System.out.println("consume");
    }
}
