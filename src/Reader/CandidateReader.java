package Reader;

import Objects.Candidate;
import Objects.Constituency;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by EoinH on 15/05/2017.
 */
public class CandidateReader extends Reader {
    private File file;
    private Scanner in;
    private ArrayList <Candidate> candidates;

    public CandidateReader(String name) throws FileNotFoundException, ArrayIndexOutOfBoundsException {
        file = new File (name +".csv");
        String line;
        String[] elements;
        Constituency constituency;
        candidates = new ArrayList<Candidate>();

        if (file.exists()) {
            System.out.println("Inside file");
            in = new Scanner(file);
            in.useDelimiter(",");
            int i =0;
            while (in.hasNextLine()){
                line = in.nextLine();
                elements = line.split(",");
//                System.out.println("Creating " + elements[1]);
                candidates.add(new Candidate(i,elements[0],elements[1],elements[2],elements[3],Integer.parseInt(elements[4])));
               // System.out.println(i + line);
                i++;

            }
            in.close();
        }
        else
            System.out.println(name + " does not exist");
    }
    public void printAll(){
        System.out.println("conID\tcanID\tpid\tName\tObjects.Constituency\tObjects.Party\tGender\tvotes");
        for (int i =0;i < candidates.size();i++)
            System.out.println(candidates.get(i).getCanid() + "\t\t" +
                    candidates.get(i).getConid() + "\t\t" +
                    candidates.get(i).getPid() + "\t\t" +
                    candidates.get(i).getName() + "\t\t" +
                    candidates.get(i).getConstituency() + "\t\t\t" +
                    candidates.get(i).getParty() + "\t\t\t" +
                    candidates.get(i).getGender() + "\t\t" +
                    candidates.get(i).getVotes());
    }
    public ArrayList<Candidate> giveList(){
        return candidates;
    }

    public void setIDs(String partyList[], String constituencyList[]){
        for(int i =0;i < candidates.size();i++) {
            candidates.get(i).setConid(constituencyList);
            candidates.get(i).setPid(partyList);
        }
    }

    public String[] give(){
        String[] canList = new String[candidates.size()];
        for (int i =0;i < canList.length;i++)
            canList[i] = candidates.get(i).getName();
        return canList;
    }
}
