package Database;

import Objects.Candidate;
import Objects.Constituency;
import Objects.Party;
import Objects.Round;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by EoinH on 16/10/2017.
 */
public class DBConnector {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public DBConnector(){
        try{
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Election2016","root","");

            statement = connection.createStatement();
        }catch(Exception ex){
            System.out.println("Error:" + ex);
        }
    }

    public void writeParties(ArrayList<Party> parties){
        for (int i =0;i < parties.size();i++)
        {
            write(parties.get(i));
        }
    }
    public void writeConstituencies(ArrayList<Constituency> constituencies){
        for (int i =0;i < constituencies.size();i++)
        {
            write(constituencies.get(i));
        }
    }
    public void writeCandidates(ArrayList<Candidate> candidates){
        for (int i =0;i < candidates.size();i++)
        {
            write(candidates.get(i));
        }
    }
    public void writeRounds(ArrayList<Round> rounds){
        for (int i =0;i < rounds.size();i++)
        {
            write(rounds.get(i));
        }
    }

/*
    public void getData(){
        try{
            String query = "select * from persons";
            resultSet = statement.executeQuery(query);

            System.out.println("Records from Database");

            while (resultSet.next()){
                String name = resultSet.getString("name");
                String age = resultSet.getString("age");
                System.out.println("Name: " + name + " age: " + age);

            }

        }catch(Exception ex){
            System.out.println(ex);
        }
    }*/

    private void write(Candidate candidate){
        try{
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO `candidates` (`CanID`, `Name`, `PID`, `ConID`, `Gender`, `Votes`) VALUES (?, ?, ?, ?, ?, ?)");
            pstmt.setInt(1, candidate.getCanid());
            pstmt.setString(2, candidate.getName());
            pstmt.setInt(3, candidate.getPid());
            pstmt.setInt(4, candidate.getConid());
            pstmt.setString(5, candidate.getGender());
            pstmt.setInt(6, candidate.getVotes());
            pstmt.executeUpdate();
        }catch (Exception ex){
            System.out.println("Error: " + ex);
        }
    }
    private void write(Constituency constituency){
        try{
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO `constituencies` (`ConID`, `Turnout`, `Name`, `Seats`, `Population`, `Spoiled`) VALUES (NULL,?,?,?,?,?)");
            pstmt.setInt(1, constituency.getPoll2016());
            pstmt.setString(2, constituency.getName());
            pstmt.setInt(3, constituency.getSeats());
            pstmt.setInt(4, constituency.getElectorate2016());
            pstmt.setInt(5, constituency.getSpoiled2016());
            pstmt.executeUpdate();
        }catch (Exception ex){
            System.out.println("Error: " + ex);
        }
    }
    private void write(Party party){
        try{
            int major= 0;
            if (party.getName().equals("Sinn Fein") ||party.getName().equals("The Labour Party") ||party.getName().equals("Fianna Fail") ||party.getName().equals("Fine Gael"))
                major = 1;
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO `parties` (`PID`, `Name`, `Major`) VALUES (?,?,?)");
            pstmt.setInt(1, party.getPid());
            pstmt.setString(2, party.getName());
            pstmt.setInt(3, major);
            pstmt.executeUpdate();
        }catch (Exception ex){
            System.out.println("Error: " + ex);
        }
    }
    private void write(Round round){
        try{
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO `rounds` (`RID`, `CanID`, `ConID`, `Votes`, `Number`,`Status`) VALUES (NULL, ?, ?, ?, ?,?)");
            pstmt.setInt(1, round.getCanid());
            pstmt.setInt(2, round.getConid());
            pstmt.setInt(3, round.getVotes());
            pstmt.setInt(4, round.getNumber());
            pstmt.setString(5, round.getStatus());

            pstmt.executeUpdate();

        }catch (Exception ex){
            System.out.println("Error: " + ex);
        }
    }
}