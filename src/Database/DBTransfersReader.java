package Database;

import Objects.Constituency;
import Objects.Model;
import Objects.Transfer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DBTransfersReader {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ArrayList<Transfer> transfers;

    public DBTransfersReader(){
        try{
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Election2016","root","");

            statement = connection.createStatement();


            String query = "SELECT * from `transfers`";

            resultSet = statement.executeQuery(query);

            transfers = new ArrayList<>();

            while(resultSet.next()) {
                int fromCanID = resultSet.getInt("fromCanID");
                int toCanID = resultSet.getInt("toCanID");
                double percentage  = resultSet.getDouble("percentage");
                boolean found = resultSet.getInt("found") == 1;
                int weight = resultSet.getInt("weight");;
                int options  = resultSet.getInt("options");;

                transfers.add(new Transfer(fromCanID,toCanID,percentage,found,weight,options));
            }


        }catch(Exception ex){
            System.out.println("Error:" + ex);
        }
    }

    public ArrayList<Transfer> getTransfers (){
        return  transfers;
    }

}
