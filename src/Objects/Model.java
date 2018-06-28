package Objects;

import java.util.ArrayList;

public class Model{
    public Constituency constituency;
    private ArrayList<Candidate> allCandidates;
    ArrayList<Candidate> currentCandidates;
    private ArrayList<ArrayList<Round>> rounds;
    private Transfer[][] transfers;

    public Model (Constituency constituency){
        this.constituency = constituency;
        allCandidates = new ArrayList<Candidate>();
        rounds = new ArrayList<ArrayList<Round>>();
    }

    public void addCandidate(Candidate candidate){
        allCandidates.add(candidate);
        rounds.add(new ArrayList<Round>());
    }

    public void addRound(int i, Round round){
        allCandidates.get(i).addRound(round);
    }

    public int getCandidatesSize(){
        return allCandidates.size();
    }

    public Candidate getCandidate(int i ){
        return allCandidates.get(i);
    }

    public void printAll(){
        System.out.println("Constituency: " + constituency.getName() + "\n");

        for (int i =0; i < allCandidates.size();i++) {
            System.out.println("\nCandidate " + i + " : " + allCandidates.get(i).getName());


            for (int j =0; j < rounds.get(i).size();j++)
                System.out.println("Round "+ rounds.get(i).get(j).getNumber() + ": "  + rounds.get(i).get(j).getVotes());
        }

    }

    public void calculateTransfers(){
        /*int round =0;
        int elected =0;
        int overflow;
        int total;
//        totalRounds = setRoundLimit();
        int quota = (int)(Math.ceil((constituency.getPoll2016() - constituency.getSpoiled2016() )/ (constituency.getSeats() + 1))) + 1;
        System.out.println("Quota: " + quota);
        boolean triggered;
        currentCandidates = (ArrayList<Candidate>)allCandidates.clone();
        ArrayList<Candidate> redistributeCandidates;
        createTransfers();

        setPositions();

        System.out.println("The number of rounds is " + totalRounds);

//        while (constituency.getSeats()!=elected){
        while (round!=totalRounds){
            currentCandidates = sortCandidates(currentCandidates);
            redistributeCandidates = new ArrayList<>();
            triggered = false;
            total = 0;
            System.out.println("\nRound " + (round + 1));
            overflow = 0;

            //Check to see if above quota (ie elected)
            for (int i = 0; i < currentCandidates.size();i++){
                if (currentCandidates.get(i).getVotes() >= quota) {
                    elected++;
                    overflow += currentCandidates.get(i).getVotes() - quota;
                    System.out.println(currentCandidates.get(i).getName() + " was elected to seat " + elected);

                    redistributeCandidates.add(currentCandidates.get(i));
                    currentCandidates.remove(i);
                    i--;
                    triggered = true;
                }
            }

            //Check to see if minimum amount of candidates left
            if(currentCandidates.size() + elected == constituency.getSeats() + 1){
                int count = currentCandidates.size() - 1;
                System.out.println("Count = "+ count);

                for (int j = count; j > 0; j--){
                    elected++;
                    System.out.println(currentCandidates.get(j).getName() + " was elected to seat " + elected);
                    currentCandidates.remove(j);
                }
                System.out.println(currentCandidates.get(0).getName() + " was excluded");
                triggered = true;
            }

            //Exclude Candidates (ie with the fewest votes)

            if (currentCandidates.size() > 1) {
                int count = getBottom(currentCandidates, overflow, (constituency.getSeats() - elected));
                if (count > 0)
                    for (int i = count - 1; i >= 0; i--) {
                        System.out.println(currentCandidates.get(i).getName() + " was excluded");
                        redistributeCandidates.add(currentCandidates.get(i));
                        overflow+= currentCandidates.get(i).getVotes();
                        currentCandidates.remove(i);
                    }
            }

            round++;
            if (constituency.getSeats()!=elected)
                currentCandidates = updateVotes(currentCandidates, round);

            //If so redistribute those votes
            redistributeVotes(currentCandidates, redistributeCandidates, overflow);

            if (round > 100)
                System.exit(0);
        }


        printTransfers();*/
    }


    public void signalElected(int round, int candidate){
        int total = rounds.get(candidate).get(round).getVotes() - (int)(Math.ceil(constituency.getPoll2016()/(constituency.getSeats() + 1)));
        //System.out.println("Candidate:"+ rounds.get(candidate).get(round).getVotes()+ " Number of seats: " + constituency.getSeats() + " Valid polls " + constituency.getPoll2016());
        System.out.println("Elected, Total transfer for " + allCandidates.get(candidate).getName() + " = " + total);
    }

    public void signalExcluded(int round, int candidate){
        int total = rounds.get(candidate).get(round).getVotes();
        System.out.println("Excluded, Total transfer for " + allCandidates.get(candidate).getName() + " = " + total);
    }

    public ArrayList<Candidate> updateVotes(ArrayList<Candidate> candidates, int round){

        for (int i =0; i < candidates.size(); i++) {
            System.out.println("Checking " + candidates.get(i).getName());
            candidates.get(i).setVotes(candidates.get(i).getRound(round).getVotes());
//            System.out.println(candidates.get(i).getName() + " " + candidates.get(i).getVotes());
        }
        return candidates;
    }

    public ArrayList<Candidate> sortCandidates(ArrayList<Candidate> candidates){
        Candidate spare;

        for (int i =0; i < candidates.size();i++)
        {
            for (int j = 0; j < candidates.size() - 1 - i;j++)
                if (candidates.get(j).getVotes() > candidates.get(j + 1).getVotes()) {
                    spare = candidates.get(j);
                    candidates.set(j, candidates.get(j + 1));
                    candidates.set(j + 1, spare);
                }
        }


        return candidates;
    }

    private int getBottom(ArrayList<Candidate> candidates, int overflow, int seats){
        boolean valid;
        int potentialOverflow;

        Candidate[] currentCandidates = new Candidate[candidates.size()];

        for (int i =0; i < currentCandidates.length; i++){
            currentCandidates[i] = candidates.get(i);
        }
//        System.out.println("Base Overflow: " + overflow);
        for (int i = currentCandidates.length - seats; i >= 0; i--){
            valid = false;
            potentialOverflow = overflow;


            if (i > 0) {
                for (int j = i - 1; j >= 0 && !valid; j--) {
//                    System.out.println("Overflow before = " + potentialOverflow);
                    potentialOverflow += currentCandidates[j].getVotes();
////                    System.out.println("Potential overflow for " + currentCandidates[i].getName() + " " + i + " :" + potentialOverflow + " number to beat: " + currentCandidates[i].getVotes());
//                    System.out.println("Checking " + currentCandidates[j].getName());
//                    System.out.println("Potential overflow: " + potentialOverflow );
//                    System.out.println("To beat: " + currentCandidates[i].getVotes() );

                    if (potentialOverflow > currentCandidates[i].getVotes()) {
//                        System.out.println("Beaten\n");
                        valid = true;
                    }
                }
            }
            else {
                potentialOverflow += currentCandidates[i].getVotes();
                System.out.println("Potential overflow: " + potentialOverflow );
                System.out.println("To beat: " + currentCandidates[i + 1].getVotes() );
                if (potentialOverflow > currentCandidates[i].getVotes()) {
//                    System.out.println("Beaten\n");
                    valid = true;
                }
            }

            if (!valid)
                return i;
        }


        System.out.println("Returning 0");
        return 0;
    }

//    public int getBottom(ArrayList<Candidate> candidates ,int overflow){
//        System.out.println("Overflow is " + overflow);
//
//        if (overflow + candidates.get(0).getVotes() > candidates.get(1).getVotes())
//        {
//            return 0;
//        }
//        int count = 1;
//
//        int total = candidates.get(0).getVotes() + overflow;
//        System.out.println("Overflow total " + (total) + " next total " + candidates.get(1).getVotes());
//
//        for (int i =1; i < candidates.size() - 1;i++) {
//            System.out.println("Overflow total " + (total + candidates.get(i).getVotes()) + " next total " + candidates.get(i + 1).getVotes());
//            if (total + candidates.get(i).getVotes() < candidates.get(i + 1).getVotes()) {
//                count = i;
//                total += candidates.get(i).getVotes();
//            }
//                else
//                        break;
//        }
//        return count;
//    }
//
//    private int setRoundLimit(){
//        int highest =0;
//        for (int i =0; i < rounds.size();i++) {
//            if (rounds.get(i).size() > highest)
//                highest = rounds.get(i).size();
//        }
//        return highest;
//    }

    private void createTransfers() {
        transfers = new Transfer[allCandidates.size()][allCandidates.size()];
        for (int i =0 ; i < allCandidates.size();i++){
            for (int j =0;  j < allCandidates.size();j++){
                Transfer transfer = new Transfer(allCandidates.get(i).getCanid(),allCandidates.get(j).getCanid());
                transfers[i][j] = transfer;
            }
        }
    }

    private void printTransfers(){
        System.out.print("\t\t\t");

        for (int i =0; i < allCandidates.size(); i++)
            System.out.print(allCandidates.get(i).getName().substring(0,8) + "\t");
        System.out.println();
        for (int i =0; i < transfers.length;i++) {
            for (int j = -1; j < transfers[i].length; j++) {
                if (j == -1)
                    System.out.print(allCandidates.get(i).getName().substring(0,8)+ "\t");
                else
                    System.out.print(transfers[i][j].getPercentage() + "\t\t\t");
            }
            System.out.println();
        }

    }

    public void redistributeVotes(ArrayList <Candidate> currentCandidates, ArrayList <Candidate> candidatesToRedistribute, double total){
//        transfers[12][10].setPercentage(1);
        double percentage;

        for (int i =0; i < candidatesToRedistribute.size();i++){
            for (int j =0; j < currentCandidates.size();j++){

                percentage = (currentCandidates.get(j).getVotes() -  currentCandidates.get(j).getPreviousVotes())/total;
                percentage = Math.round(percentage * 100) ;

                transfers[candidatesToRedistribute.get(i).getPosition()][currentCandidates.get(j).getPosition()].setPercentage(percentage);

            }
        }
    }

    public void setPositions(){
        for (int i =0;i < currentCandidates.size(); i++){
            currentCandidates.get(i).setPosition(i);
        }
    }
}