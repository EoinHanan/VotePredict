package Database;

import Objects.Candidate;
import Objects.Constituency;
import Objects.Model;
import Objects.Round;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by EoinH on 17/10/2017.
 */
public class DBReader {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private Model model;
    private ArrayList<String> constituencyNames;

    public DBReader(){
        try{
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Election2016","root","");

            statement = connection.createStatement();
        }catch(Exception ex){
            System.out.println("Error:" + ex);
        }
    }

    public void readConstituency(String constituencyName){
        try {
            String query;

            query = "SELECT * from constituencies where name = '" + constituencyName + "'";

            resultSet = statement.executeQuery(query);

            resultSet.next();
            int conID = resultSet.getInt("ConID");
            int turnout = resultSet.getInt("Turnout");
            String name = resultSet.getString("Name");
            int seats = resultSet.getInt("Seats");
            int population = resultSet.getInt("Population");
            int spoiled = resultSet.getInt("Spoiled");

            Constituency constituency = new Constituency(name, conID, seats, population, turnout, spoiled);
            model = new Model(constituency);

//            System.out.println("Constituency ID: " + conID + " turnout: " + turnout + " name: " + name + " seats: " + seats + " population " + population + " spoiled: " + spoiled);

            query = "SELECT * FROM candidates where ConID = (SELECT ConID from constituencies where name = '" + constituencyName + "')";

            resultSet = statement.executeQuery(query);

//            System.out.println("Candidates data");
            Candidate candidate;

            while (resultSet.next()) {
                int canID = resultSet.getInt("CanID");
                name = resultSet.getString("Name");
                int pid = resultSet.getInt("PID");
                conID = resultSet.getInt("ConID");
                String gender = resultSet.getString("Gender");
                int votes = resultSet.getInt("Votes");

//                System.out.println("Candidate ID: " + canID + " name: " + name + " pid: " + pid + " conID: " + conID + " gender " + gender + " votes: " + votes);
                candidate = new Candidate(canID, conID, name, pid, gender, votes);
                model.addCandidate(candidate);
            }




//            System.out.println("Rounds data");
            Round round;
            for (int i = 0; i < model.getCandidatesSize(); i++){
//                query = "SELECT * FROM rounds where CanID in (select CanID from candidates where ConID = (select ConID from constituencies where name = '" + constituencyName + "' ))";
                query = "SELECT * FROM rounds where CanID in (select CanID from candidates where name = '" + model.getCandidate(i).getName()+ "')";

                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    int RID = resultSet.getInt("RID");
                    int CanID = resultSet.getInt("CanID");
                    int ConID = resultSet.getInt("ConID");
                    int votes = resultSet.getInt("Votes");
                    int number = resultSet.getInt("Number");
                    String status = resultSet.getString("Status");

//                    System.out.println("RID: " + RID + "Candidate ID: " + CanID + " conID: " + conID + " votes: " + votes + "Number: " + number + " Status: " + status);
                    round = new Round(RID, CanID, ConID, votes, number, status);
//                    System.out.println("Adding round " + i);
                    model.addRound(i, round);
                }
            }

        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    public ArrayList <String> getConstituencyNames() throws SQLException {
        constituencyNames = new ArrayList<>();
        String query;

        query = "SELECT `Name` FROM `constituencies`";

        resultSet = statement.executeQuery(query);

//            System.out.println("Candidates data");
        Candidate candidate;

        while (resultSet.next()) {
            String name = resultSet.getString("Name");

//                System.out.println("Candidate ID: " + canID + " name: " + name + " pid: " + pid + " conID: " + conID + " gender " + gender + " votes: " + votes);
            constituencyNames.add(name);
        }

        return constituencyNames;
    }

    public Model getModel(){
        return model;
    }
}