import java.sql.*;
import java.util.*;
public class JDBCFinal {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e){
            System.out.println("Can't load driver");
        }
      try{
        System.out.println("Starting Connection........");
        //connects to the database
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://161.35.177.175:3306/testuser", "testuser", "testuser");
        //calling the connection statement fucntion
        Statement stmt = con.createStatement();
        System.out.println("Connection Established");
        //execute a query, results have to go into a ResultSet object
        ResultSet result = stmt.executeQuery("SELECT sid from student");
        System.out.println("Processing Results");
        while (result.next()) { // process results one row at a time
            int key = result.getInt(1);  // 1 because first attribute and only attribute from our result
            // in the select clause
            System.out.println("Sid = " + key);
        }
    }
        catch (SQLException e){
        System.out.println(e.getMessage() + " Can't connect to database");
        while(e!=null){
            System.out.println("Message: "+e.getMessage());
            e= e.getNextException();
        }
    }
        catch (Exception e){
        System.out.println("Other Error");
    }
}
}

