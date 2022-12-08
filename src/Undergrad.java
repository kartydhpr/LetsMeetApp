public class Undergrad extends CollegeStudent{
    private String collegeYear;

    public Undergrad(String firstname, String lastname, String ip, int port, double gpa, String college, String collegeYear){
        super(firstname, lastname, ip, port, gpa, college);
        this.collegeYear = collegeYear;
    }

    public void setCollegeYear(String collegeYear){
        this.collegeYear = collegeYear;
    }
    
    public String getCollegeYear(){
        return collegeYear;
    }
}