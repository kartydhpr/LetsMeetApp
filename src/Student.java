public class Student extends Person {
    protected double gpa;

    public Student(String firstname, String lastname, String ip, int port, double gpa){
        super(firstname, lastname, ip, port);
        this.gpa = gpa;
    }

    public void setGpa(double gpa){
        this.gpa = gpa;
    }
    
    public double getGpa(){
        return gpa;
    }
}