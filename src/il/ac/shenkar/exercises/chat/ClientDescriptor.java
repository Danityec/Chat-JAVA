package il.ac.shenkar.exercises.chat;

public class ClientDescriptor implements StringConsumer, StringProducer{
    public void addConsumer(StringConsumer sc) {
        System.out.println("add consumer");
    }

    public void removeConsumer(StringConsumer sc){
        System.out.println("remove consumer");
    }

    public void consume(String str){
        System.out.println("consume");
    }
}
