package il.ac.shenkar.exercises.chat;

import java.util.ArrayList;

public class MessageBoard implements StringConsumer, StringProducer {

    private ArrayList<StringConsumer> consumerList;

    public void addConsumer(StringConsumer sc) {
        consumerList.add(sc);
        System.out.println("add consumer");
    }

    public void removeConsumer(StringConsumer sc){
        consumerList.remove(sc);
        System.out.println("remove consumer");
    }

    public void consume(String str){      //ניהול הודעות

        System.out.println("consume");
    }
}
