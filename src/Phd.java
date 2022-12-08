public class Phd extends CollegeStudent{
    private Boolean researchComplete;

    public Phd(String firstname, String lastname, String ip, int port, double gpa, String college, Boolean researchComplete){
        super(firstname, lastname, ip, port, gpa, college);
        this.researchComplete = researchComplete;
    }

    public void setResearchComplete(boolean ResearchComplete){
        this.researchComplete = ResearchComplete;
    }
    
    public boolean getResearchComplete(){
        return researchComplete;
    }
}