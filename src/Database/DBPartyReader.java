package Database;

import Objects.Candidate;
import Objects.Party;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBPartyReader {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ArrayList<Party> parties;

    public DBPartyReader(){
        try{
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Election2016","root","");

            statement = connection.createStatement();

            String query = "SELECT * from `parties`";

            resultSet = statement.executeQuery(query);

            parties = new ArrayList<>();

            while(resultSet.next()) {
                int PID = resultSet.getInt("PID");
                String name = resultSet.getString("Name");


                parties.add(new Party(PID, name));
            }


        }catch(Exception ex){
            System.out.println("Error:" + ex);
        }
    }

    public ArrayList<Party> getParties (){
        return parties;
    }
}
