public class CollegeStudent extends Student{
    protected String college;

    public CollegeStudent(String firstname, String lastname, String ip, int port, double gpa, String college){
        super(firstname, lastname, ip, port, gpa);
        this.college = college;
    }

    public void setCollege(String college){
        this.college = college;
    }
    
    public String getCollege(){
        return college;
    }
}