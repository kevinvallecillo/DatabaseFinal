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
         //exit condition
          boolean exit=false;
          //runs menu
          while(!exit){
              // show all houses
              String query1= "SELECT property.property_id,house.bedroom_count AS Number_Of_Bedroom,"+
              " house.bathroom_count AS Number_Of_Bathroom, house.sqft AS Size_SquareFoot,"+
              " property.street_number, property.street, city.name,city.zipcode,city.state"+
             " FROM house JOIN property ON house.property_id=property.property_id JOIN city"+
              " ON city.city_id=property.city_id";
              Statement stmt1 = con.createStatement();
              ResultSet result1 = stmt1.executeQuery(query1);
              System.out.printf("\nHouses");
              System.out.printf("\n|%-15s |%-15s |%-15s |%-15s |%-40s|","Property ID","# of Bedrooms",
                      "# of Bathrooms","Sqft","Address");
              System.out.print("\n--------------------------------------------------------------------------------------------------------------");

              while (result1.next()){
                  int pID= result1.getInt("property_id");
                  int bedrooms= result1.getInt("Number_Of_Bedroom");
                  int bathrooms= result1.getInt("Number_Of_Bathroom");
                  int sqft = result1.getInt("Size_SquareFoot");
                  int streetnum= result1.getInt("street_number");
                  String street = result1.getString("street");
                  String city= result1.getString("name");
                  int zipcode  =result1.getInt("zipcode");
                  String state= result1.getString("state");
                  String Address= streetnum +" "+ street+", "+city+", "+state+" "+ zipcode;
                  System.out.printf("\n|%-15s |%-15s |%-15s |%-15s |%-40s|",pID,bedrooms,
                          bathrooms,sqft,Address);
                  System.out.print("\n--------------------------------------------------------------------------------------------------------------");

              }
              //Show all land
              System.out.println();
              System.out.printf("Land");
              String query2 = "SELECT property.property_id, land.acres, land.status,"+
                      " property.street_number,property.street,city.name,city.zipcode,city.state FROM"+
              " land JOIN property ON land.property_id=property.property_id JOIN city ON"+
              " city.city_id=property.city_id";
              Statement stmt2 = con.createStatement();
              ResultSet result2 = stmt2.executeQuery(query2);
              System.out.printf("\n|%-15s |%-15s |%-15s |%-40s |","Property ID","Acres","Status","Address");
              System.out.print("\n----------------------------------------------------------------------------------------------");

              while (result2.next()){
                  int pId= result2.getInt("property_id");
                  int acres = result2.getInt("acres");
                  String status = result2.getString("status");
                  String Addr = result2.getInt("street_number")+" "+result2.getString("street")+
                          " "+result2.getString("name")+", "+result2.getString("state")+", "+result2.getInt("zipcode");
                  System.out.printf("\n|%-15s |%-15s |%-15s |%-40s |",pId,acres,status,Addr);
                  System.out.print("\n----------------------------------------------------------------------------------------------");

              }
              //menu output
              System.out.println("\nMenu");
              System.out.println("0. Exit");
              System.out.println("1. Who is selling a particular piece of Home or Land");
              System.out.println("2. Who are the realtors involved in selling or buying land or a home?");
              System.out.println("3. What land properties are available that match a particular buying customer's criteria?");
              System.out.println("Enter which Query you would like to run");

              //checks for exit condition
              int input= scan.nextInt();
                if(input==0){
                    exit=true;
                }

                //Query 1
              else if(input==1){
                  System.out.println("Please enter a property ID");
                  int ID = scan.nextInt();;
                  String query="SELECT person.first_name AS Owner_FirstName, person.last_name AS"+
                          " Owner_LastName, person.email FROM person JOIN property ON"+
                    " property.owner_id=person.person_id WHERE property_id = ?";
                  //run and get results from our query
                  PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setString(1, String.valueOf(ID));
                    ResultSet result = stmt.executeQuery();
                  // set up table format
                    System.out.printf("\n| %-20s | %-30s |","Name", "Email");
                    System.out.print("\n---------------------------------------------------------");
                  // loop through our resluts
                  while(result.next()){
                      // set variables equal to our tables columns
                      String name = result.getString("Owner_FirstName")+" "+result.getString("Owner_LastName");
                      String email= result.getString("email");
                      System.out.printf("\n| %-20s | %-30s |", name, email);
                      System.out.print("\n---------------------------------------------------------");
                  }

              }
              //run query 2
              else if(input==2){
                  System.out.println("Please enter a propetry ID");
                  int ID= scan.nextInt();
                  String query ="SELECT realtor.first_name AS Realtor_firstName, realtor.last_name AS"+
                    " Realtor_LastName, realtor.email, realtor.phone FROM realtor JOIN"+
                    " listing_realtor ON listing_realtor.realtor_id=realtor.realtor_id JOIN listing"+
                    " ON listing_realtor.listing_id=listing.listing_id JOIN property ON"+
                    " property.property_id=listing.property_id WHERE property.property_id=?";
                  PreparedStatement stmt = con.prepareStatement(query);
                  stmt.setString(1, String.valueOf(ID));
                  ResultSet result = stmt.executeQuery();
                    System.out.printf("\n| %-20s | %-30s | %-15s|","Name", "Email", "Phone Number");
                    System.out.print("\n--------------------------------------------------------------------------");
                  while(result.next()){
                      String Name = result.getString("Realtor_firstName")+" "+result.getString("Realtor_LastName");
                      String email =result.getString("email");
                      String Phone = result.getString("phone");
                      System.out.printf("\n| %-20s | %-30s | %-15s|",Name, email, Phone);
                      System.out.print("\n--------------------------------------------------------------------------");
                  }
              }
              else if(input==3) {
                    String query = "SELECT property.property_id, property.property_type,property.street_number," +
                    " property.street, city.name,city.zipcode, city.state, listing.asking_price,"+
                            " sale.price_sold FROM sale JOIN listing ON sale.listing_id = listing.listing_id"+
                    " JOIN property ON property.property_id=listing.property_id JOIN city ON"+
                    " city.city_id=property.city_id WHERE listing.asking_price<sale.price_sold";
                    Statement stmt = con.createStatement();
                    ResultSet result =stmt.executeQuery(query);
                    System.out.printf("\n| %-15s | %-15s | %-40s | %-15s | %-15s|","Property ID", "Type","Address","Asking Price","Sold Price");
                    System.out.print("\n-------------------------------------------------------------------------------------------------------------------");
                    while(result.next()){
                    int pID = result.getInt("property_id");
                    String type = result.getString("property_type");
                    String Addr = result.getInt("street_number")+" "+result.getString("street")+
                                " "+result.getString("name")+", "+result.getString("state")+", "+result.getInt("zipcode");
                    int Asking = result.getInt("asking_price");
                    int Sold = result.getInt("price_sold");
                        System.out.printf("\n| %-15s | %-15s | %-40s | %-15s | %-15s|",pID, type,Addr,Asking,Sold);
                        System.out.print("\n-------------------------------------------------------------------------------------------------------------------");
                    }
              }
              else{
                  System.out.println("Invalid input Try again");
              }

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

