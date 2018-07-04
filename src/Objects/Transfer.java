package Objects;

/**
 * Created by EoinH on 10/06/2017.
 */
public class Transfer {
    private int fromCanID;
    private int toCanID;
    private double percentage;
    private boolean found;
    private int weight;
    private int options;
    private int fromPID;
    private int toPID;

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
    public Transfer(int fromCanID, int toCanID, double percentage, boolean found, int weight, int options){
        this.fromCanID = fromCanID;
        this.toCanID = toCanID;
        this.percentage = percentage;
        this.found = found;
        this.weight = weight;
        this.options = options;
    }

    public int getFromCanID(){
        return fromCanID;
    }
    public int getToCanID(){
        return toCanID;
    }
    public int getFromPID(){
        return fromPID;
    }
    public int getToPID(){
        return toPID;
    }
    public double getPercentage(){
        return percentage;
    }
    public void setPercentage(double percentage){
        this.percentage = percentage;
        found = true;
    }
    public void setWeight(int weight){
        this.weight = weight;
    }
    public boolean isFound(){
        return found;
    }
    public int getWeight(){
        return weight;
    }
    public void setOptions(int options){
        this.options = options;
    }

    public int getOptions(){
        return options;
    }

    public void setFromPID(int fromPID){
        this.fromPID = fromPID;
    }
    public void setToPID(int toPID){
        this.toPID = toPID;
    }
}