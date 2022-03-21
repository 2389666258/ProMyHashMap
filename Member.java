package CHC5223;

public class Member implements IMember {

    private String name;
    private String affiliation;

    public Member(String nameTemp, String affiliationTemp) {
        setName(nameTemp);
        setAffiliation(affiliationTemp);
    }

    public void setName(String nameTemp) {
        name = nameTemp;
    }
    public void setAffiliation(String affiliationTemp) {
        affiliation = affiliationTemp;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAffiliation() {
        return affiliation;
    }

    @Override
    public String toString() {
        return "name: " + name + " affiliation: " + affiliation;
    }
}
