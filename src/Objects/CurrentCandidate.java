package Objects;

import java.util.ArrayList;

public class CurrentCandidate {
    private String name;
    private int canID;
    private int conID;
    private boolean toRemove;
    private boolean removed;
    private int currentVotes;
    private int nextVotes;
    private ArrayList<Round> rounds;
    private int position;
    private boolean elected;

    public CurrentCandidate(Candidate candidate){
        name = candidate.getName();
        canID = candidate.getCanid();
        conID = candidate.getConid();
        toRemove = false;
        removed = false;
        rounds = candidate.getRounds();
    }

    public String getName(){
        return name;
    }

    public int getCanID(){
        return canID;
    }

    public boolean willBeRemoved(){
        return toRemove;
    }

    public boolean isRemoved(){return removed;}

    public int getCurrentVotes (){return currentVotes;}

    public int getNextVotes (){return nextVotes;}

    public ArrayList <Round> getRounds (){
        return rounds;
    }

    public void setVotes(int nextVotes, int currentVotes){
        this.currentVotes = currentVotes;
        this.nextVotes = nextVotes;
    }

    public void setVotes(int currentVotes){
        this.currentVotes = currentVotes;
    }

    public void setRemoved(){
        removed = true;
    }

    public void setToBeRemoved(){
        toRemove =true;
    }

    public void setInfo(int round, boolean finalRound){
        if (toRemove)
            removed = true;

        if (rounds.get(round).getStatus().equals("Elected"))
            toRemove = true;

        if (!finalRound)
            if(rounds.get(round + 1).getStatus().equals("Excluded"))
                toRemove = true;

        if (finalRound){
            currentVotes = rounds.get(round).getVotes();
        }
        else {
            currentVotes = rounds.get(round).getVotes();
            nextVotes = rounds.get(round + 1).getVotes();
        }
    }

    public void setElected(boolean elected){
        this.elected = elected;
    }

    public boolean wasElected(){
        return elected;
    }

    public void setPosition(int position){
        this.position = position;
    }
    public int getPosition(){
        return position;
    }

}
