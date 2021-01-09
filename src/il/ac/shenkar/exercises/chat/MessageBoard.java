package il.ac.shenkar.exercises.chat;

import java.io.IOException;
import java.util.ArrayList;

public class MessageBoard implements StringConsumer, StringProducer {
    private ArrayList<StringConsumer> consumerList;

    public MessageBoard() {
        consumerList = new ArrayList<>();
    }

    public void addConsumer(StringConsumer sc) {
        consumerList.add(sc);
    }

    public void removeConsumer(StringConsumer sc){
        consumerList.remove(sc);
    }

    public void consume(String str) throws IOException{
        try {
            for ( int i=0; i < consumerList.size(); i++) {
                consumerList.get(i).consume(str);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
