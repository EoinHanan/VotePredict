package Database;

import Objects.Candidate;
import Objects.Transfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBCandidateReader {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ArrayList<Candidate> candidates;

    public DBCandidateReader(){
        try{
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Election2016","root","");

            statement = connection.createStatement();


            String query = "SELECT * from `candidates`";

            resultSet = statement.executeQuery(query);

            candidates = new ArrayList<>();

            while(resultSet.next()) {
                int canID = resultSet.getInt("CanID");
                String name = resultSet.getString("Name");
                int pid = resultSet.getInt("PID");
                int conID = resultSet.getInt("ConID");
                String gender = resultSet.getString("Gender");
                int votes = resultSet.getInt("Votes");

                candidates.add(new Candidate(canID, conID, name, pid, gender, votes));
            }


        }catch(Exception ex){
            System.out.println("Error:" + ex);
        }
    }

    public ArrayList<Candidate> getCandidates (){
        return  candidates;
    }
}
