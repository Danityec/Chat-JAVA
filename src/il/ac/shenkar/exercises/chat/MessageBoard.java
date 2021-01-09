package il.ac.shenkar.exercises.chat;

import java.io.IOException;
import java.util.ArrayList;
/**
 * class MessageBoard implements from StringConsumer and StringProducer
 * class MessageBoard Responsible for all user management
 * class MessageBoard charge on remove consumer  to list
 * class MessageBoard charge on add consumer to list
 * @params consumerList

 */
public class MessageBoard implements StringConsumer, StringProducer {
    private ArrayList<StringConsumer> consumerList;
    /*
       methods MessageBoard is a constructor , Initializing consumerList
     */
    public MessageBoard() {
        consumerList = new ArrayList<>();
    }
    /*
       methods addConsumer add consumer to list
    */
    public void addConsumer(StringConsumer sc) {
        consumerList.add(sc);
    }
    /*
       methods removeConsumer remove consumer to list
    */
    public void removeConsumer(StringConsumer sc){
        consumerList.remove(sc);
    }
    /*
       methods consume publish the message to all consumer
    */
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
