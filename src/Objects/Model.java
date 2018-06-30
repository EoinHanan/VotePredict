package Objects;

import Printer.CSVPrinter;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Model{
    public Constituency constituency;
    private ArrayList<Candidate> candidates;
    private ArrayList<CurrentCandidate> currentCandidates;
    private Transfer[][] transfers;
    private int quota;

    public Model (Constituency constituency){
        this.constituency = constituency;
        candidates = new ArrayList<Candidate>();
        quota = (int)(Math.ceil((constituency.getPoll2016() - constituency.getSpoiled2016()) / (constituency.getSeats() + 1)) + 1);
        System.out.println("Checking " + constituency.getName() + " Quota is " + quota);
    }

    public void addCandidate(Candidate candidate){
        candidates.add(candidate);
    }

    public void addRound(int i, Round round){
        candidates.get(i).addRound(round);
    }

    public int getCandidatesSize(){
        return candidates.size();
    }

    public Candidate getCandidate(int i ){
        return candidates.get(i);
    }

    public void printAll(){
        System.out.println("Constituency: " + constituency.getName() + "\n");

        for (int i =0; i < candidates.size();i++) {
            System.out.println("\nCandidate " + i + " : " + candidates.get(i).getName());

            ArrayList <Round> rounds = candidates.get(i).getRounds();
            for (int j =0; j < rounds.size();j++)
                System.out.println("Round "+ rounds.get(j).getNumber() + ": "  + rounds.get(j).getVotes());
        }

    }

    public void calculateTransfers() throws FileNotFoundException {
        //Setup up rounds
        int round = 0;
        int totalRounds = candidates.get(0).getRounds().size();

        createTransfers();

        System.out.println("There are " + candidates.size() + " Candidates");
        System.out.println("There are " + totalRounds + " rounds");

        //Set up Current Candidates
        currentCandidates = new ArrayList<>();
        for (int i =0; i < candidates.size();i++)
            currentCandidates.add(new CurrentCandidate(candidates.get(i)));

        setPositions();

        ArrayList <CurrentCandidate> transferFrom;
        ArrayList <CurrentCandidate> transferTo;
        //Set up transfers

        while (round != totalRounds - 1){

            // Set up for  round
            for (int i =0;  i < currentCandidates.size();i++) {
                System.out.println("Checking " + currentCandidates.get(i).getName());
                currentCandidates.get(i).setInfo(round);
            }

            System.out.println("\nStarting round " + (round + 1));

            transferFrom = new ArrayList<>();
            transferTo = new ArrayList<>();

            //Print current status of all candidates
//            for (int i =0; i < currentCandidates.size();i++){
//                System.out.println(currentCandidates.get(i).getName() + " " + currentCandidates.get(i).willBeRemoved() + " " + currentCandidates.get(i).isRemoved() + " " + currentCandidates.get(i).getCurrentVotes() + " " + currentCandidates.get(i).getNextVotes());
//            }


            //Make an arraylist of candidates that will be removed
            for (int i =0; i < currentCandidates.size(); i++){
                if (currentCandidates.get(i).willBeRemoved() && !currentCandidates.get(i).isRemoved())
                   transferFrom.add(currentCandidates.get(i));
                else if (!currentCandidates.get(i).willBeRemoved())
                   transferTo.add(currentCandidates.get(i));
            }

            //Work out the amount of votes that will be redistributed
            for (int i =0; i < transferFrom.size();i++)
                transferFrom.get(i).setElected(aboveQuota(transferFrom.get(i)));

            //Create transfers from this
            setTransfers(transferFrom,transferTo);

            //Increment round
            round++;
        }

        //Print Transfers
        printTransfers();

        CSVPrinter printer = new CSVPrinter (transfers, candidates,constituency.getName());
        printer.print();

    }

    private void createTransfers() {
        transfers = new Transfer[candidates.size()][candidates.size()];
        for (int i =0 ; i < candidates.size();i++){
            for (int j =0;  j < candidates.size();j++){
                Transfer transfer = new Transfer(candidates.get(i).getCanid(),candidates.get(j).getCanid());
                transfers[i][j] = transfer;
            }
        }
    }

    private void printTransfers(){
        System.out.print("\t\t\t");

        for (int i =0; i < candidates.size(); i++)
            System.out.print(candidates.get(i).getName().substring(0,8) + "\t");
        System.out.println();
        for (int i =0; i < transfers.length;i++) {
            for (int j = -1; j < transfers[i].length; j++) {
                if (j == -1)
                    System.out.print(candidates.get(i).getName().substring(0,8)+ "\t");
                else
                    System.out.print(clean(transfers[i][j].getPercentage()) + "\t\t\t");
            }
            System.out.println();
        }

    }

    public void redistributeVotes(ArrayList <Candidate> currentCandidates, ArrayList <Candidate> candidatesToRedistribute, double total){
        double percentage;

        for (int i =0; i < candidatesToRedistribute.size();i++){
            for (int j =0; j < currentCandidates.size();j++){

                percentage = (currentCandidates.get(j).getVotes() -  currentCandidates.get(j).getPreviousVotes())/total;
                percentage = Math.round(percentage * 100) ;

                transfers[candidatesToRedistribute.get(i).getPosition()][currentCandidates.get(j).getPosition()].setPercentage(percentage);

            }
        }
    }

    private boolean aboveQuota(CurrentCandidate candidate){
        return candidate.getCurrentVotes() >= quota;
    }

    public void setPositions(){
        for (int i =0;i < currentCandidates.size(); i++){
            currentCandidates.get(i).setPosition(i);
        }
    }

    private void setTransfers(ArrayList<CurrentCandidate> from, ArrayList<CurrentCandidate> to){
        double redistribution = 0;
        double difference[] = new double [to.size()];

        System.out.println("There are " + from.size() + " candidates transferring and there are " + to.size() + " candidates left");

        for (int i =0; i < from.size(); i++) {
            if (from.get(i).wasElected())
                redistribution += from.get(i).getCurrentVotes() - quota;
            else
                redistribution += from.get(i).getCurrentVotes();
        }

        System.out.println("Redistribution = " + redistribution);

        System.out.println("Transfers is a " + transfers.length + " * " + transfers[0].length);

        for (int i = 0; i < to.size();i++ ) {
            difference[i] = to.get(i).getNextVotes() - to.get(i).getCurrentVotes();
            System.out.println("Difference is " + difference[i]);
        }

        for (int i =0 ; i < from.size(); i++) {
            for (int j = 0; j < to.size(); j++) {
                System.out.println("From: " + from.get(i).getPosition() + " To: " + to.get(j).getPosition() + " with the difference " + (difference[j]/redistribution));
                transfers[from.get(i).getPosition()][to.get(j).getPosition()].setPercentage(difference[j]/redistribution);
            }
        }

    }

    public double clean(double number){
        number = number * 1000;
        number = Math.round(number);
        number = number /10;
        return number;
    }
}