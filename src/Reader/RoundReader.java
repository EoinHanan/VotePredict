package Reader;

import Objects.Constituency;
import Objects.Round;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by EoinH on 05/06/2017.
 */
public class RoundReader {
    private File file;
    private Scanner in;
    private ArrayList<Round> rounds;

    public RoundReader(String name, int election) throws FileNotFoundException, ArrayIndexOutOfBoundsException {
        file = new File(name + ".csv");

        String line;
        String[] elements;
        Constituency constituency;
        rounds = new ArrayList<Round>();

        if (file.exists()) {
            //System.out.println("Inside file");
            in = new Scanner(file);
            in.useDelimiter(";");
            int i = 0;
            while (in.hasNextLine()) {
                line = in.nextLine();
                elements = line.split(";");
                rounds.add(new Round(i, election, elements[0], elements[1],elements[2],Integer.parseInt(elements[3]),Integer.parseInt(elements[4])));
                i++;
                //System.out.println(line);
            }
            in.close();
        } else
            System.out.println(name + " does not exist");
    }
    public void printAll(){
        System.out.println("ID\tEID\tconID\tcanID\tObjects.Constituency\tObjects.Party\tGender\tvotes");
        for (int i =0;i < rounds.size();i++)
            System.out.println(rounds.get(i).getRid() + "\t\t" +
                    rounds.get(i).getEid() + "\t\t" +
                    rounds.get(i).getCanid() + "\t\t" +
                    rounds.get(i).getConid() + "\t\t" +
                    rounds.get(i).getConstituency() + "\t\t\t"+
                    rounds.get(i).getCandidate() + "\t\t" +
                    rounds.get(i).getStatus() + "\t\t" +
                    rounds.get(i).getVotes() + "\t\t");
    }
    public void setIDs (String [] conList, String[]canList){
        for(int i =0;i < rounds.size();i++) {
            rounds.get(i).setConid(conList);
            rounds.get(i).setCanid(canList);
        }
    }
    public ArrayList<Round> give(){
        return rounds;
    }
}