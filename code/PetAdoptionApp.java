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
            System.out.println("\n====================================");
            System.out.println("   Pet Adoption Management System");
            System.out.println("====================================");
            System.out.println("1. View available pets");
            System.out.println("2. View all adopters");
            System.out.println("3. Submit adoption application");
            System.out.println("4. View all adoption applications");
            System.out.println("5. View adopted pets");
            System.out.println("6. Reset all pets to Available");
            System.out.println("7. View shelters");
            System.out.println("8. Search pets by species");
            System.out.println("9. Exit");
            System.out.println("====================================");
            System.out.print("Enter your choice: ");

            //check if user input is not an integer
            while (!input.hasNextInt()) 
            {
                //check if user input is not an integer
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                //clear the invalid input from buffer
                input.next(); 
                //ask user again for input
                System.out.print("Enter your choice: ");
            }


            //read user choice
            choice = input.nextInt();

            //clear buffer
            input.nextLine(); 

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

                        System.out.println("\nAvailable Pets:");
                        System.out.printf("%-3s %-10s %-10s %-12s %-5s\n",
                        "ID", "Name", "Species", "Breed", "Age");
                        System.out.println("----------------------------------------");                        

                        boolean found = false;

                        while (rs.next())
                        {
                            found = true;
                            System.out.printf("%-3d %-10s %-10s %-12s %-5d\n",
                            rs.getInt("pet_id"),
                            rs.getString("name"),
                            rs.getString("species"),
                            rs.getString("breed"),
                            rs.getInt("age")
                            );
                        }

                        if (!found)
                        {
                            System.out.println("No available pets found.");
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
                    try
                    {
                        Connection conn = DriverManager.getConnection(url);
                        Statement stmt = conn.createStatement();

                        String sql = "SELECT * FROM Adopter";
                        ResultSet rs = stmt.executeQuery(sql);

                        System.out.println("\n========== Adopters ==========");
                        System.out.println("ID| Name       | Phone      | Email            | Address");
                        System.out.println("-----------------------------------------------------------");

                        while (rs.next())
                        {
                            System.out.println(
                                rs.getInt("adopter_id") + " | "
                                + rs.getString("name") + " | "
                                + rs.getString("phone") + " | "
                                + rs.getString("email") + " | "
                                + rs.getString("address")
                            );
                        }

                        rs.close();
                        stmt.close();
                        conn.close();
                    }
                    catch(Exception e)
                    {
                        System.out.println("Error fetching adopters.");
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    //submit adoption application into database
                    try
                    {
                        Connection conn = DriverManager.getConnection(url);
                        Statement stmt = conn.createStatement();

                        //get adopter ID with validation
                        System.out.print("Enter adopter ID: ");
                        int adopterId = input.nextInt();

                        //check if adopter exists
                        String adopterCheck = "SELECT * FROM Adopter WHERE adopter_id = " + adopterId;
                        ResultSet adopterRs = stmt.executeQuery(adopterCheck);

                        if (!adopterRs.next())
                        {
                            System.out.println("Invalid adopter ID.");
                            adopterRs.close();
                            stmt.close();
                            conn.close();
                            break;
                        }
                        adopterRs.close();

                        //get pet ID with validation
                        System.out.print("Enter pet ID: ");
                        int petId = input.nextInt();

                        //check if pet exists
                        String petCheck = "SELECT * FROM Pet WHERE pet_id = " + petId;
                        ResultSet petRs = stmt.executeQuery(petCheck);

                        if (!petRs.next())
                        {
                            System.out.println("Invalid pet ID.");
                            petRs.close();
                            stmt.close();
                            conn.close();
                            break;
                        }
                        petRs.close();

                        //check if pet exists and is available
                        String checkSql = "SELECT adoption_status FROM Pet WHERE pet_id = " + petId;
                        ResultSet checkRs = stmt.executeQuery(checkSql);

                        if (checkRs.next())
                        {
                            String status = checkRs.getString("adoption_status");
                            if (!status.equals("Available"))
                            {
                                System.out.println("This pet is not available for adoption.");
                                checkRs.close();
                                stmt.close();
                                conn.close();
                                break;
                            }
                        }
                        else
                        {
                            System.out.println("Pet ID not found.");
                            checkRs.close();
                            stmt.close();
                            conn.close();
                            break;
                        }
                        checkRs.close();

                        //get next available application ID
                        String idQuery = "SELECT MAX(application_id) AS max_id FROM AdoptionApplication";
                        ResultSet rs = stmt.executeQuery(idQuery);

                        int newId = 1;
                        if (rs.next())
                        {
                            if (rs.getInt("max_id") != 0)
                            {
                                newId = rs.getInt("max_id") + 1;
                            }
                        }
                        rs.close();

                        //SQL to insert a new adoption application
                        String sql = "INSERT INTO AdoptionApplication "
                                + "VALUES (" + newId + ", " + adopterId + ", " + petId
                                + ", '2026-03-25', 'Approved')";

                        //run insert query
                        stmt.execute(sql);

                        //update pet status to Adopted
                        String updateSql = "UPDATE Pet SET adoption_status = 'Adopted' WHERE pet_id = " + petId;
                        stmt.execute(updateSql);

                        System.out.println("Adoption application submitted successfully. Pet status updated to Adopted.");

                        stmt.close();
                        conn.close();
                    }
                    catch(Exception e)
                    {
                        System.out.println("Could not submit adoption application.");
                        e.printStackTrace();
                    }
                    break;

                case 4:
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

                        System.out.println("\n==== Adoption Applications ====");

                        boolean found = false; 

                        while(rs.next())
                        {
                            found = true;
                            System.out.println(
                                rs.getInt("application_id") + " | "
                                + rs.getString("adopter_name") + " | "
                                + rs.getString("pet_name") + " | "
                                + rs.getString("application_date") + " | "
                                + rs.getString("status")
                            );
                        }

                        if (!found)
                        {
                            System.out.println("No adoption applications found.");
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

                case 5:
                    //show adopted pets
                    try
                    {
                        Connection conn = DriverManager.getConnection(url);
                        Statement stmt = conn.createStatement();

                        String sql = "SELECT * FROM Pet WHERE adoption_status = 'Adopted'";
                        ResultSet rs = stmt.executeQuery(sql);

                        System.out.println("\n======== Adopted Pets ========");

                        boolean found = false;

                        while (rs.next())
                        {
                            found = true;
                            System.out.println(
                                rs.getInt("pet_id") + " | "
                                + rs.getString("name") + " | "
                                + rs.getString("species")
                            );
                        }

                        if (!found)
                        {
                            System.out.println("None");
                        }

                        rs.close();
                        stmt.close();
                        conn.close();
                    }
                    catch (Exception e)
                    {
                        System.out.println("Error fetching adopted pets.");
                        e.printStackTrace();
                    }
                    break;

                case 6:
                    try
                    {
                        Connection conn = DriverManager.getConnection(url);
                        Statement stmt = conn.createStatement();

                        String sql = "UPDATE Pet SET adoption_status = 'Available'";
                        stmt.execute(sql);

                        System.out.println("System reset complete. All pets are now marked as Available.");

                        stmt.close();
                        conn.close();
                    }
                    catch(Exception e)
                    {
                        System.out.println("Error resetting pets.");
                        e.printStackTrace();
                    }
                    break;


                case 7:
                    try
                    {
                        Connection conn = DriverManager.getConnection(url);
                        Statement stmt = conn.createStatement();

                        String sql = "SELECT * FROM Shelter";
                        ResultSet rs = stmt.executeQuery(sql);

                        System.out.println("\n========== Shelters ==========");
                        System.out.println("ID| Name                | Location");
                        System.out.println("--------------------------------");

                        boolean found = false;

                        while (rs.next())
                        {
                            found = true;
                            System.out.println(
                                rs.getInt("shelter_id") + " | "
                                + rs.getString("name") + " | "
                                + rs.getString("location")
                            );
                        }

                        if (!found)
                        {
                            System.out.println("None");
                        }

                        rs.close();
                        stmt.close();
                        conn.close();
                    }
                    catch(Exception e)
                    {
                        System.out.println("Error fetching shelters.");
                        e.printStackTrace();
                    }
                    break;

                case 8:
                    try
                    {
                        Connection conn = DriverManager.getConnection(url);
                        Statement stmt = conn.createStatement();

                        //ask user for species
                        System.out.print("Enter species to search (Dog/Cat/etc.): ");
                        String speciesInput = input.nextLine();

                        //SQL query to search pets by species
                        String sql = "SELECT * FROM Pet WHERE species = '" + speciesInput + "'";
                        ResultSet rs = stmt.executeQuery(sql);

                        System.out.println("\nMatching Pets:");
                        System.out.printf("%-3s %-10s %-10s %-12s %-5s %-12s\n",
                                "ID", "Name", "Species", "Breed", "Age", "Status");
                        System.out.println("------------------------------------------------------------");

                        boolean found = false;

                        while(rs.next())
                        {
                            found = true;
                            System.out.printf("%-3d %-10s %-10s %-12s %-5d %-12s\n",
                                    rs.getInt("pet_id"),
                                    rs.getString("name"),
                                    rs.getString("species"),
                                    rs.getString("breed"),
                                    rs.getInt("age"),
                                    rs.getString("adoption_status"));
                        }

                        if(!found)
                        {
                            System.out.println("No pets found for that species.");
                        }

                        rs.close();
                        stmt.close();
                        conn.close();
                    }
                    catch(Exception e)
                    {
                        System.out.println("Error searching pets.");
                        e.printStackTrace();
                    }
                    break;

                case 9:
                    System.out.println("Thank you for using the Pet Adoption Management System.");
                    break;

                default:
                    System.out.println("Invalid menu choice. Please select a number from 1 to 9.");
            }

        }while(choice != 9);

        //close scanner
        input.close();
    }
}
