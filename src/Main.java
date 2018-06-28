import Database.DBConnector;
import Database.DBReader;
import Objects.*;
import Reader.CandidateReader;
import Reader.ConstituencyReader;
import Reader.PartyReader;
import Reader.RoundReader;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]) throws FileNotFoundException {
//        createDatabase();
//        readDatabase();
    }

    private static void readDatabase() {
        DBReader reader = new DBReader();
        reader.readConstituency("Laois");
        Model model = reader.getModel();
//        model.printAll();
        model.calculateTransfers();
//        model.printAll();
    }

    public static void createDatabase()throws FileNotFoundException{
        PartyReader partyReader = new PartyReader("Data/PartyDetails");
        String[] partyList = partyReader.givePartyList();
        ArrayList<Party> parties= partyReader.give();

        ConstituencyReader conReader = new ConstituencyReader("Data/ConstDetails");
        String[] conList = conReader.giveConList();
        ArrayList<Constituency> constituencies = conReader.give();

        CandidateReader canReader = new CandidateReader("Data/CandidateDetails");
        canReader.setIDs(partyList,conList);
        ArrayList<Candidate> candidates = canReader.giveList();
        String[] canList = canReader.give();

        RoundReader roundReader = new RoundReader("Data/CountDetails", 2016);
        roundReader.setIDs(conList,canList);
        //roundReader.printAll();
        ArrayList<Round> rounds = roundReader.give();

//        System.out.println("Number of parties: " + parties.size());
//        System.out.println("Number of constituencies: " + constituencies.size());
//        System.out.println("Number of candidates: " + candidates.size());
//        System.out.println("Number of rounds: " + rounds.size());

        DBConnector connector = new DBConnector();
//        System.out.println("Parties");
//        connector.writeParties(parties);
//        System.out.println("Candidates");
//        connector.writeCandidates(candidates);
//        System.out.println("Constituencies");
//        connector.writeConstituencies(constituencies);
        System.out.println("Rounds");
        connector.writeRounds(rounds);

        System.out.print("About to end.");
    }
}
