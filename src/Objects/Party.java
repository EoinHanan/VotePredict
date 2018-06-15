package Objects;

public class Party {
    private String name;
    private int pid;
    private String abbreviation;

    public Party(int pid, String name, String abbreviation)
    {
        this.name = name;
        this.pid = pid;
        this.abbreviation = abbreviation;
    }

    public String getName()    {        return name;    }
    public int getPid(){return pid;}
    public String getAbbreviation (){return abbreviation;}
}
