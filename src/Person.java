/*
This is the Person class.
*/

public class Person {
    protected String firstname;
    protected String lastname;
    protected String ip;
    protected int port;
    
    public Person(String firstname, String lastname, String ip, int port){
        this.firstname = firstname;
        this.lastname = lastname;
        this.ip = ip;
        this.port = port;
    }
    
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    
    public String getFirstname(){
        return firstname;
    }
    
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    
    public String getLastname(){
        return lastname;
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