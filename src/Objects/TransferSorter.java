package Objects;

import java.util.ArrayList;

public class TransferSorter {
    private ArrayList<Transfer> transfers;
    private ArrayList<Party> parties;
    private ArrayList<Candidate> candidates;
    ArrayList<Transfer>[][] transfersByParty;


    public TransferSorter(ArrayList<Transfer> transfers, ArrayList<Party> parties, ArrayList<Candidate> candidates){
        this.transfers = transfers;
        this.parties = parties;
        this.candidates = candidates;

        setTransfersPID();
    }

    public void sort() {
        //First dimension is the from and the second represents to
         transfersByParty = new ArrayList[parties.size()][parties.size()];

        for (int i = 0; i < transfersByParty.length;i++)
            for (int j = 0; j < transfersByParty[i].length;j++)
                transfersByParty[i][j] = new ArrayList<>();

//        System.out.println("Size of Array is " + transfersByParty.length + " * " + transfersByParty[0].length);


        for (Transfer transfer : transfers) {
//            System.out.println(transfer.getFromCanID() + "-" + transfer.getToCanID() + " is " + transfer.getFromPID() + " * " + transfer.getToPID());
            transfersByParty[transfer.getFromPID()][transfer.getToPID()].add(transfer);
        }

        printTable();

    }



    private void setTransfersPID(){
        for (Transfer transfer : transfers) {
            transfer.setFromPID(getTransferValue(transfer.getFromCanID()));
            transfer.setToPID(getTransferValue(transfer.getToCanID()));
        }

    }

    private int getTransferValue(int value){
        for (int i =0; i < candidates.size();i++)
            if (candidates.get(i).getCanid() == value)
                return candidates.get(i).getPid();

        return -1;
    }

    public void printAll(){
        for (int i =0; i < transfers.size();i++)
            System.out.println(i + ": " + parties.get(transfers.get(i).getFromPID()).getName() + " to " + parties.get(transfers.get(i).getToPID()).getName());
    }

    private void printTable(){
        System.out.print("\t\t\t");
        for (Party party : parties){
            System.out.print(party.getName().substring(0,7) + "\t\t");
        }

        System.out.println();

        for (int i = 0; i < transfersByParty.length;i++) {
            System.out.print(parties.get(i).getName().substring(0,7) + "\t\t");
            for (int j = 0; j < transfersByParty[i].length;j++)
                System.out.print(transfersByParty[i][j].size() + "\t\t\t");
            System.out.println();
        }

    }

}
