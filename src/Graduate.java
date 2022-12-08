public class Graduate extends CollegeStudent{
    private Boolean thesisComplete;

    public Graduate(String firstname, String lastname, String ip, int port, double gpa, String college, Boolean thesisComplete){
        super(firstname, lastname, ip, port, gpa, college);
        this.thesisComplete = thesisComplete;
    }

    public void setThesisComplete(boolean thesisComplete){
        this.thesisComplete = thesisComplete;
    }
    
    public boolean getThesisComplete(){
        return thesisComplete;
    }
}