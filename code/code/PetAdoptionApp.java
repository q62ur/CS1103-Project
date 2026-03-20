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
            //display menu options
            System.out.println("\nPet Adoption Management System");
            System.out.println("1. View available pets");
            System.out.println("2. Submit adoption application");
            System.out.println("3. Exit");
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
                    catch (Exception e)
                    {
                        System.out.println("Could not fetch available pets.");
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    System.out.println("Submitting adoption application...");
                    break;

                case 3:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while(choice != 3);

        //close scanner
        input.close();
    }
}
