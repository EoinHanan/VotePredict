package Objects;

import java.util.ArrayList;

/**
 * Created by EoinH on 15/05/2017.
 */
public class Candidate {
    private String name;
    private String constituency;
    private String party;
    private int votes;
    private int previousVotes;
    private String gender;
    private int canID;
    private int conID;
    private int pid;
    private ArrayList <Round> rounds;
    private Transfer[] transfers;
    private boolean active;
    private boolean willBeExcluded;
    private int position;

    public Candidate(int canID, String constituency, String name, String gender, String party,int votes){
        this.canID =canID;
        this.name = name.toLowerCase();
        this.constituency = constituency;
        this.gender = gender;
        this.votes = votes;
        this.party=party;
        rounds = new ArrayList<>();
        willBeExcluded = false;
    }

    public Candidate(int canID, int conID, String name, int pid, String gender, int votes) {
        this.canID =canID;
        this.conID = conID;
        this.name = name;
        this.pid = pid;
        this.gender = gender;
        this.votes = votes;
        active = true;
        rounds = new ArrayList<>();
        willBeExcluded = false;
    }

    public int getCanid(){return canID;}
    public int getConid(){return conID;}
    public int getPid(){return pid;}
    public String getName(){
        return name;
    }
    public String getConstituency (){return constituency;}
    public String getGender(){return gender;}
    public String getParty(){return party;}
    public int getVotes(){return votes;}
    public void setVotes(int votes) {
        this.previousVotes = this.votes;
        this.votes = votes;}
    public int getPreviousVotes(){return previousVotes;}
    public Round getRound(int i){
        return rounds.get(i);
    }
    public void addRound(Round round){
        rounds.add(round);
    }
    public int getPosition(){
        return position;
    }
    public void setPosition(int position){
        this.position = position;
    }
    public int roundSize(){
        return rounds.size();
    }

    public void setPid(String [] parties){
        int id =0;
        boolean found = false;
        for (int i =0;i < parties.length && !found;i++){
            if (party.equals(parties[i])) {
                found = true;
                id =i;
            }
            //System.out.println(party + " Equals " + parties[i] + " : " + found);
        }
        pid = id;
        //System.out.println(pid);
    }

    public void setConid(String [] constituencies){
        int id =0;
        boolean found = false;
        for (int i =0;i < constituencies.length && !found;i++) {
            if (constituency.equals(constituencies[i])) {
                found = true;
                id = i;
            }
            //System.out.println(constituency + " Equals " + constituencies[i] + " : " + found);
        }
        conID = id;
        //System.out.println(conid);
    }

    public void createTransfers(int number){
        transfers = new Transfer[number];
    }

    public Transfer getTransfer (int i ){
        return transfers[i];
    }

    public boolean isActive(){
        return active;
    }

    public void makeInactive(){
        active = false;
        votes = 0;
    }

    public boolean isToBeExcluded(){
        return willBeExcluded;
    }


}
