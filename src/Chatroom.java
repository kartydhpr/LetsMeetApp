/*
This is the Chatroom class.
*/

public class Chatroom{
    private Person sender;
    private Person receiver;

    public void setSender(Person sender){
        this.sender = sender;
    }
    
    public Person getSender(){
        return sender;
    }
    
    public void setReceiver(Person receiver){
        this.receiver = receiver;
    }
    
    public Person getReceiver(){
        return receiver;
    }
    
    public void sendMessage(Person p, String message){    //client   
        Client c = new Client();
        c.Client(p, message);
    }
}