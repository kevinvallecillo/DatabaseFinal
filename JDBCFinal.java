import java.sql.*;
import java.util.*;
public class JDBCFinal {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e){
            System.out.println("Can't load driver");
        }
      try{
        System.out.println("Starting Connection........");
        //connects to the database
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://161.35.177.175:3306/project1", "kevin", "p1kv");
          System.out.println("Connected");
          int input=1;
          while(input!=0){
              System.out.println("Menu");
//              Who is buying a particular home?
//              Who are the realtors involved in selling or buying a particular land or home property?
//                      What home properties are available that match a particular buying customer's criteria?
//              What land properties are available that match a particular buying customer's criteria?
//              Which properties have sold over their asking price?
//              Which realtor(s) made the most money in commission?
              System.out.println("0. Exit");
              System.out.println("1. Who is selling a particular piece of land");
              System.out.println("2. Who are the realtors involved in selling or buying a particular land or home property?");
              System.out.println("3. What home properties are available that match a particular buying customer's criteria? ");
              System.out.println("4. What land properties are available that match a particular buying customer's criteria?");
              System.out.println("Enter which Query you would like to run");
              input = scan.nextInt();
              System.out.println(input);
          }
        //calling the connection statement fucntion
        Statement stmt = con.createStatement();
        System.out.println("Connection Established");
        //execute a query, results have to go into a ResultSet object
        ResultSet result = stmt.executeQuery("SELECT * from listing");
        System.out.println("Processing Results");
        while (result.next()) { // process results one row at a time
            //int key = result.getInt(1);  // 1 because first attribute and only attribute from our result
            // in the select clause
            System.out.println("Sid = "+ result.getString(1)+"/"+ result.getString(2)+ "/"+result.getString(3)+ "/"+result.getString(4));
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

