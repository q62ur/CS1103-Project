//import required JDBC classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

//main class to setup database
public class DatabaseSetup
{
    //main method where program starts
    public static void main(String[] args)
    {
        //database file name
        String url = "jdbc:sqlite:petadoption.db";

        try
        {
            //connect to SQLite database
            Connection conn = DriverManager.getConnection(url);

            //create statement object
            Statement stmt = conn.createStatement();

            //SQL to create Shelter table
            String shelterTable = "CREATE TABLE IF NOT EXISTS Shelter ("
                    + "shelter_id INT PRIMARY KEY, "
                    + "name VARCHAR(100) NOT NULL, "
                    + "location VARCHAR(100) NOT NULL"
                    + ");";

            //SQL to create Pet table
            String petTable = "CREATE TABLE IF NOT EXISTS Pet ("
                    + "pet_id INT PRIMARY KEY, "
                    + "name VARCHAR(100) NOT NULL, "
                    + "species VARCHAR(50) NOT NULL, "
                    + "breed VARCHAR(50), "
                    + "age INT, "
                    + "adoption_status VARCHAR(20) NOT NULL, "
                    + "shelter_id INT, "
                    + "FOREIGN KEY (shelter_id) REFERENCES Shelter(shelter_id)"
                    + ");";

            //SQL to create Adopter table
            String adopterTable = "CREATE TABLE IF NOT EXISTS Adopter ("
                    + "adopter_id INT PRIMARY KEY, "
                    + "name VARCHAR(100) NOT NULL, "
                    + "phone VARCHAR(20), "
                    + "email VARCHAR(100), "
                    + "address VARCHAR(200)"
                    + ");";

            //SQL to create Staff table
            String staffTable = "CREATE TABLE IF NOT EXISTS Staff ("
                    + "staff_id INT PRIMARY KEY, "
                    + "name VARCHAR(100) NOT NULL, "
                    + "role VARCHAR(50), "
                    + "shelter_id INT, "
                    + "FOREIGN KEY (shelter_id) REFERENCES Shelter(shelter_id)"
                    + ");";

            //SQL to create AdoptionApplication table
            String applicationTable = "CREATE TABLE IF NOT EXISTS AdoptionApplication ("
                    + "application_id INT PRIMARY KEY, "
                    + "adopter_id INT, "
                    + "pet_id INT, "
                    + "application_date DATE, "
                    + "status VARCHAR(20), "
                    + "FOREIGN KEY (adopter_id) REFERENCES Adopter(adopter_id), "
                    + "FOREIGN KEY (pet_id) REFERENCES Pet(pet_id)"
                    + ");";

            //SQL to create AdoptionRecord table
            String recordTable = "CREATE TABLE IF NOT EXISTS AdoptionRecord ("
                    + "record_id INT PRIMARY KEY, "
                    + "adopter_id INT, "
                    + "pet_id INT, "
                    + "adoption_date DATE, "
                    + "FOREIGN KEY (adopter_id) REFERENCES Adopter(adopter_id), "
                    + "FOREIGN KEY (pet_id) REFERENCES Pet(pet_id)"
                    + ");";

            //sample data for Shelter table
            String insertShelter = "INSERT OR IGNORE INTO Shelter VALUES "
                    + "(1, 'Happy Tails Shelter', 'Saint John'), "
                    + "(2, 'Safe Paws Shelter', 'Fredericton');";

            //sample data for Pet table
            String insertPet = "INSERT OR IGNORE INTO Pet VALUES "
                    + "(1, 'Max', 'Dog', 'Labrador', 3, 'Available', 1), "
                    + "(2, 'Bella', 'Cat', 'Siamese', 2, 'Available', 1), "
                    + "(3, 'Charlie', 'Dog', 'Beagle', 4, 'Adopted', 2);";

            //sample data for Adopter table
            String insertAdopter = "INSERT OR IGNORE INTO Adopter VALUES "
                    + "(1, 'John Smith', '1234567890', 'john@example.com', 'Saint John'), "
                    + "(2, 'Emma Brown', '9876543210', 'emma@example.com', 'Fredericton');";

            //sample data for Staff table
            String insertStaff = "INSERT OR IGNORE INTO Staff VALUES "
                    + "(1, 'Alice Green', 'Manager', 1), "
                    + "(2, 'Bob White', 'Volunteer', 2);";

            //sample data for AdoptionApplication table
            String insertApplication = "INSERT OR IGNORE INTO AdoptionApplication VALUES "
                    + "(1, 1, 1, '2026-03-15', 'Pending'), "
                    + "(2, 2, 3, '2026-03-16', 'Approved');";

            //sample data for AdoptionRecord table
            String insertRecord = "INSERT OR IGNORE INTO AdoptionRecord VALUES "
                    + "(1, 2, 3, '2026-03-18');";
            
            //execute table creation
            stmt.execute(shelterTable);
            stmt.execute(petTable);

            stmt.execute(adopterTable);
            stmt.execute(staffTable);
            stmt.execute(applicationTable);
            stmt.execute(recordTable);

            stmt.execute(insertShelter);
            stmt.execute(insertPet);
            stmt.execute(insertAdopter);
            stmt.execute(insertStaff);
            stmt.execute(insertApplication);
            stmt.execute(insertRecord);

            //query to show all available pets
            String selectPets = "SELECT * FROM Pet WHERE adoption_status = 'Available'";

            //run the select query
            ResultSet rs = stmt.executeQuery(selectPets);

            //display available pets
            System.out.println("Available pets:");
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

            //close result set
            rs.close();

            //update query to approve an adoption application
            String updateApplication = "UPDATE AdoptionApplication "
                    + "SET status = 'Approved' "
                    + "WHERE application_id = 1;";

            //run the update query
            stmt.execute(updateApplication);

            //delete query to remove a sample adopter
            String deleteAdopter = "DELETE FROM Adopter "
                    + "WHERE adopter_id = 3;";

            //run the delete query
            stmt.execute(deleteAdopter);
            
            //success message
            System.out.println("All tables and sample data created successfully.");

            //close statement and connection
            stmt.close();
            conn.close();
        }
        catch(Exception e)
        {
            //print error message if something goes wrong
            System.out.println("Database setup failed.");
            e.printStackTrace();
        }
    }
}
