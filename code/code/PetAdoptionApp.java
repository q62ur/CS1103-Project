//import scanner for user input
import java.util.Scanner;

//import JDBC classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//main class for pet adoption application
public class PetAdoptionApp
{
    //main method where program starts
    public static void main(String[] args)
    {
        //scanner object for reading user input
        Scanner input = new Scanner(System.in);

        //variable to store menu choice
        int choice;

        //database file
        String url = "jdbc:sqlite:petadoption.db";

        do
        {
            System.out.println("\nPet Adoption Management System");
            System.out.println("1. View available pets");
            System.out.println("2. Submit adoption application");
            System.out.println("3. View all adoption applications");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            //read user choice
            choice = input.nextInt();

            //process menu choice
            switch(choice)
            {
                case 1:
                    //show available pets from database
                    try
                    {
                        Connection conn = DriverManager.getConnection(url);
                        Statement stmt = conn.createStatement();

                        String sql = "SELECT * FROM Pet WHERE adoption_status = 'Available'";
                        ResultSet rs = stmt.executeQuery(sql);

                        System.out.println("\nAvailable pets:");
                        while (rs.next())
                        {
                            System.out.println(
                                rs.getInt("pet_id") + " | "
                                + rs.getString("name") + " | "
                                + rs.getString("species") + " | "
                                + rs.getString("breed") + " | "
                                + rs.getInt("age")
                            );
                        }

                        rs.close();
                        stmt.close();
                        conn.close();
                    }
                    catch(Exception e)
                    {
                        System.out.println("Could not fetch available pets.");
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    //submit adoption application into database
                    try
                    {
                        Connection conn = DriverManager.getConnection(url);
                        Statement stmt = conn.createStatement();

                        //read adopter and pet IDs from user
                        System.out.print("Enter adopter ID: ");
                        int adopterId = input.nextInt();
                
                        System.out.print("Enter pet ID: ");
                        int petId = input.nextInt();

                        //get next available application ID
                        String idQuery = "SELECT MAX(application_id) AS max_id FROM AdoptionApplication";
                        ResultSet rs = stmt.executeQuery(idQuery);
                        
                        int newId = 1;
                        if (rs.next())
                        {
                            newId = rs.getInt("max_id") + 1;
                        }
                        
                        rs.close();

                        //SQL to insert a new adoption application
                        String sql = "INSERT INTO AdoptionApplication "
                                + "VALUES (" + newId + ", " + adopterId + ", " + petId
                                + ", '2026-03-19', 'Pending')";

                        //run insert query
                        stmt.execute(sql);

                        //update pet status to Pending
                        String updateSql = "UPDATE Pet SET adoption_status = 'Pending' WHERE pet_id = " + petId;
                        stmt.execute(updateSql);
                        
                        System.out.println("Adoption application submitted successfully.");
                
                        stmt.close();
                        conn.close();
                    }
                    catch(Exception e)
                    {
                        System.out.println("Could not submit adoption application.");
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    //show all adoption applications
                    try
                    {
                        Connection conn = DriverManager.getConnection(url);
                        Statement stmt = conn.createStatement();
                
                        String sql = "SELECT a.application_id, d.name AS adopter_name, p.name AS pet_name, "
                                + "a.application_date, a.status "
                                + "FROM AdoptionApplication a "
                                + "JOIN Adopter d ON a.adopter_id = d.adopter_id "
                                + "JOIN Pet p ON a.pet_id = p.pet_id";
                        ResultSet rs = stmt.executeQuery(sql);

                        System.out.println("\nAdoption applications:");
                        while(rs.next())
                        {
                            System.out.println(
                                rs.getInt("application_id") + " | "
                                + rs.getString("adopter_name") + " | "
                                + rs.getString("pet_name") + " | "
                                + rs.getString("application_date") + " | "
                                + rs.getString("status")
                            );
                        }

                        rs.close();
                        stmt.close();
                        conn.close();
                    }
                    catch(Exception e)
                    {
                        System.out.println("Could not fetch adoption applications.");
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }while(choice != 4);

        //close scanner
        input.close();
    }
}
