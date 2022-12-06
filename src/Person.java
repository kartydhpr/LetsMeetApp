/*
This is the Person class.
*/

public class Person {
    private String username;
    private String id;
    private String ip;
    private int port;
    
    public Person(String username, String id, String ip, int port){
        this.username = username;
        this.id = id;
        this.ip = ip;
        this.port = port;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public String getId(){
        return id;
    }
    
    public void setIp(String ip){
        this.ip = ip;
    }
    
    public String getIp(){
        return ip;
    }
    
    public void setPort(int port){
        this.port = port;
    }
    
    public int getPort(){
        return port;
    }
}
