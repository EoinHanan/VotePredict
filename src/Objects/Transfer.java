package Objects;

/**
 * Created by EoinH on 10/06/2017.
 */
public class Transfer {
    private int fromCanID;
    private int toCanID;
    private double percentage;
    private boolean found;

    public Transfer(int fromCanID, int toCanID){
        this.fromCanID = fromCanID;
        this.toCanID = toCanID;
        this.percentage = 0;
        this.found = false;
    }
    public Transfer(int fromCanID, int toCanID, double percentage){
        this.fromCanID = fromCanID;
        this.toCanID = toCanID;
        this.percentage = percentage;
        this.found = true;
    }

    public int getFromCanID(){
        return fromCanID;
    }
    public int getToCanID(){
        return toCanID;
    }
    public double getPercentage(){
        return percentage;
    }
    public void setPercentage(double percentage){
        this.percentage = percentage;
        found = true;
    }
    public boolean isFound(){
        return found;
    }
}