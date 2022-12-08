package letsmeetappbeta;

/**
Author: Ryan Melenchuk
Lab Description: Provides string storing and retrieving functionality to the chatroom class
**/

public class Message {
    String message;
    
    public String getMessage(){
        return message;
    }
    public String setMessage(String newMessage){
        message = newMessage;
        return message;
    }
}