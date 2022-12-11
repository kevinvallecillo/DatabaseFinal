import java.sql.*;
import java.util.*;
import java.util.Date;

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
          boolean exit=false;
          while(!exit){
              System.out.println("\nMenu");
              System.out.println("0. Exit");
              System.out.println("1. Who is selling a particular piece of Home or Land");
              System.out.println("2. Who are the realtors involved in selling or buying land or a home?");
              System.out.println("3. What land properties are available that match a particular buying customer's criteria?");
              System.out.println("Enter which Query you would like to run");
              int input= scan.nextInt();
                if(input==0){
                    exit=true;
                }

              else if(input==1){
                  String query="SELECT house.house_id, property.street_number AS House_Number, property.street"+
                  " AS Street_Name, bedroom_count AS Number_Of_Bedroom, bathroom_count AS"+
                  " Number_of_Bathroom, sqft AS size_in_sqft, build_date AS Built_Date,"+
                  " person.first_name AS Owner_FirstName, person.last_name AS Owner_LastName"+
                  " FROM property JOIN house ON property.property_id = house.property_id JOIN person"+
                  " ON person.person_id=house.owner_id";
                  Statement stmt = con.createStatement();
                  ResultSet result = stmt.executeQuery(query);
                  System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |","House ID","House Number","Street Name","# of Bedrooms", "# of Bathrooms",
                          "SQ.Feet", "Build Date", "Owners Name");
                  System.out.print("\n-------------------------------------------------------------------------------------------------------------------------------------------------");
                  while(result.next()){
                      int houseID= result.getInt("house_id");
                      int houseNumber = result.getInt("House_Number");
                      String street= result.getString("Street_Name");
                      int Bedrooms = result.getInt("Number_Of_Bedroom");
                      int Bathrooms= result.getInt("Number_of_Bathroom");
                      int sqft = result.getInt("size_in_sqft");
                      Date date = result.getDate("Built_Date");
                      String Name = result.getString("Owner_FirstName")+" "+result.getString("Owner_LastName");

                      System.out.printf("\n| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |",houseID,houseNumber,
                              street, Bedrooms, Bathrooms, sqft, date, Name);
                      System.out.print("\n-------------------------------------------------------------------------------------------------------------------------------------------------");


                  }

              } else if(input==2){
                  String query ="SELECT land_id,acres, status,realtor.first_name AS realtor_firstName, realtor.last_name AS\n" +
                          "realtor_lastName, realtor.phone AS realtor_PhoneNumber FROM land JOIN listing ON\n" +
                          "land.property_id = listing.property_id JOIN listing_realtor ON listing.listing_id =\n" +
                          "listing_realtor.listing_id JOIN realtor ON realtor.realtor_id=listing_realtor.realtor_id";
                  Statement stmt = con.createStatement();
                  ResultSet result = stmt.executeQuery(query);
                  System.out.printf("| %-17s | %-17s | %-17s | %-17s | %-17s |", "Realtor Name", "Phone Number","Land ID","Acres","Status");
                  System.out.print("\n-----------------------------------------------------------------------------------------------------");
                  while(result.next()){
                      String Name = result.getString("realtor_firstName")+" "+result.getString("realtor_lastName");
                      String Phone = result.getString("realtor_PhoneNumber");
                      String landID= result.getString("land_id");
                      int acres= result.getInt("acres");
                      String status = result.getString("status");
                      System.out.printf("\n| %-17s | %-17s | %-17s | %-17s | %-17s |", Name, Phone, landID, acres, status);
                      System.out.print("\n-----------------------------------------------------------------------------------------------------");


                  }
              }
              else if(input==3){
                  //get user input
                    int bedrooms;
                    System.out.println("Enter the minimun amount of bedrooms you would like a house to have: ");
                    bedrooms= scan.nextInt();
                    String query="SELECT house.house_id, property.street_number AS House_Number, property.street"+
                    " AS Street_Name, bedroom_count AS Number_Of_Bedroom, bathroom_count AS"+
                    " Number_of_Bathroom, sqft AS size_in_sqft, build_date AS Built_Date,"+
                    " person.first_name AS Owner_FirstName, person.last_name AS Owner_LastName"+
                    " FROM property JOIN house ON property.property_id = house.property_id JOIN person"+
                    " ON person.person_id=house.owner_id AND house.bedroom_count >= ?";
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1, String.valueOf(bedrooms));
                    ResultSet result = stmt.executeQuery();
                    System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |","House ID","House Number","Street Name","# of Bedrooms", "# of Bathrooms",
                            "SQ.Feet", "Build Date", "Owners Name");
                    System.out.print("\n-------------------------------------------------------------------------------------------------------------------------------------------------");
                    while(result.next()){
                        int houseID= result.getInt("house_id");
                        int houseNumber = result.getInt("House_Number");
                        String street= result.getString("Street_Name");
                        int Bedrooms = result.getInt("Number_Of_Bedroom");
                        int Bathrooms= result.getInt("Number_of_Bathroom");
                        int sqft = result.getInt("size_in_sqft");
                        Date date = result.getDate("Built_Date");
                        String Name = result.getString("Owner_FirstName")+" "+result.getString("Owner_LastName");

                        System.out.printf("\n| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |",houseID,houseNumber,
                                street, Bedrooms, Bathrooms, sqft, date, Name);
                        System.out.print("\n-------------------------------------------------------------------------------------------------------------------------------------------------");

                    }
              }
              else{
                  System.out.println("Invalid input Try again");
              }

          }
//        //calling the connection statement fucntion
//        Statement stmt = con.createStatement();
//        System.out.println("Connection Established");
//        //execute a query, results have to go into a ResultSet object
//        ResultSet result = stmt.executeQuery("SELECT * from listing");
//        System.out.println("Processing Results");
//        while (result.next()) { // process results one row at a time
//            //int key = result.getInt(1);  // 1 because first attribute and only attribute from our result
//            // in the select clause
//            System.out.println("Sid = "+ result.getString(1)+"/"+ result.getString(2)+ "/"+result.getString(3)+ "/"+result.getString(4));
//        }
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

