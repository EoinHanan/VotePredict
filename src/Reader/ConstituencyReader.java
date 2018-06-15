package Reader;

import Objects.Constituency;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by EoinH on 15/05/2017.
 */
public class ConstituencyReader extends Reader {

    private File file;
    private Scanner in;
    private ArrayList<Constituency> constituencies;

    public ConstituencyReader(String name) throws FileNotFoundException {
        file = new File (name +".csv");
        String line;
        String[] elements;
        Constituency constituency;
        constituencies = new ArrayList<Constituency>();

        if (file.exists()) {
            //System.out.println("Inside file");
            in = new Scanner(file);
            in.useDelimiter(";");

            while (in.hasNextLine()){
                line = in.nextLine();
                elements = line.split(";");
                constituencies.add(new Constituency(elements[0],Integer.parseInt(elements[1]),Integer.parseInt(elements[2]),Integer.parseInt(elements[3]),Integer.parseInt(elements[4]),Integer.parseInt(elements[5])));
                //System.out.println(line);
            }
            in.close();
        }
        else
            System.out.println(name + " does not exist");
    }

    public void printAll(){
        System.out.println("Number\tSeats\tCandidates\tElectorate\tPoll\tSpoiled\t\tName");
        for (int i =0;i < constituencies.size();i++)
            System.out.println(i + 1 + "\t\t" +
                            constituencies.get(i).getSeats() + "\t\t" +
                            constituencies.get(i).getCandidatesNumber() + "\t\t\t" +
                            constituencies.get(i).getElectorate2016() + "\t\t" +
                            constituencies.get(i).getPoll2016() + "\t\t" +
                            constituencies.get(i).getSpoiled2016() + "\t\t" +
                            constituencies.get(i).getName() + "\t\t");
    }

    public ArrayList<Constituency> give(){
        return constituencies;
    }

    public String[] giveConList(){
        String[] conList = new String[constituencies.size()];
        for (int i =0;i < conList.length;i++)
            conList[i] = constituencies.get(i).getName();
        return conList;
    }

}
