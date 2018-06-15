package Objects;

/**
 * Created by EoinH on 15/05/2017.
 */
public class Constituency {
    private String name;
    private int candidatesNumber;
    private int seats;
    private int electorate2016;
    private int poll2016;
    private int spoiled2016;


    public Constituency(String name, int candidatesNumber, int seats,int electorate ,int poll, int spoiled){
        this.name = name;
        this.candidatesNumber = candidatesNumber;
        this.seats = seats;
        electorate2016 = electorate;
        poll2016 = poll;
        spoiled2016 = spoiled;
    }
    public Constituency(String name, int seats,int electorate ,int poll, int spoiled){
        this.name = name;
        this.seats = seats;
        electorate2016 = electorate;
        poll2016 = poll;
        spoiled2016 = spoiled;
    }

    public String getName(){
        return name;
    }
    public int getCandidatesNumber(){
        return candidatesNumber;
    }
    public int getSeats(){
        return seats;
    }
    public int getElectorate2016(){
        return electorate2016;
    }
    public int getPoll2016(){
        return poll2016;
    }
    public int getSpoiled2016(){
        return spoiled2016;
    }
}