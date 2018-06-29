package Printer;

import Objects.Candidate;
import Objects.Transfer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVPrinter {
    private Transfer[][] transfers;
    private ArrayList<Candidate> candidates;
    private File file;
    private PrintWriter printWriter;

    public CSVPrinter(Transfer[][] transfers, ArrayList<Candidate> candidates, String name) throws FileNotFoundException {
        this.transfers = transfers;
        this.candidates = candidates;

        file = new File (name + ".csv");
        printWriter = new PrintWriter (file);
    }

    public void print(){
        printWriter.print("\t");

        for (int i =0; i < candidates.size(); i++)
            printWriter.print(candidates.get(i).getName().substring(0,8) + "\t");
        printWriter.print("\n");
        for (int i =0; i < transfers.length;i++) {
            for (int j = -1; j < transfers[i].length; j++) {
                if (j == -1)
                    printWriter.print(candidates.get(i).getName().substring(0,8)+ "\t");
                else
                    printWriter.print(transfers[i][j].getPercentage() + "\t");
            }
            printWriter.print("\n");
        }

        printWriter.close ();
    }

}
