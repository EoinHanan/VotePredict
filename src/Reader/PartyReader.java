package Reader;

import Objects.Constituency;
import Objects.Party;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by EoinH on 04/06/2017.
 */
public class PartyReader extends Reader {
    private File file;
    private Scanner in;
    private ArrayList<Party> parties;

    public PartyReader(String name) throws FileNotFoundException, ArrayIndexOutOfBoundsException {
        file = new File (name +".csv");
        String line;
        String[] elements;
        Constituency constituency;
        parties = new ArrayList<Party>();

        if (file.exists()) {
            //System.out.println("Inside file");
            in = new Scanner(file);
            in.useDelimiter(";");
            int i =0;
            while (in.hasNextLine()){
                line = in.nextLine();
                elements = line.split(";");
                parties.add(new Party(i,elements[0],elements[1]));
                i++;
                //System.out.println(line);
            }
            in.close();
        }
        else
            System.out.println(name + " does not exist");
    }
    public void printAll(){
        System.out.println("ID\tName\tObjects.Constituency\tObjects.Party\tGender\tvotes");
        for (int i =0;i < parties.size();i++)
            System.out.println(parties.get(i).getPid() + "\t\t" +
                    parties.get(i).getName() + "\t\t" +
                    parties.get(i).getAbbreviation() + "\t\t\t");
    }

    public String[] givePartyList (){
        String [] list = new String[parties.size()];
        for (int i =0; i < list.length;i++)
            list[i]=parties.get(i).getAbbreviation();
        return list;
    }


    public ArrayList<Party> give(){
        return parties;
    }
}