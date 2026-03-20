//import required JDBC classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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
            
            //execute table creation
            stmt.execute(shelterTable);
            stmt.execute(petTable);

            stmt.execute(adopterTable);
            stmt.execute(staffTable);
            stmt.execute(applicationTable);
            stmt.execute(recordTable);
            
            //success message
            System.out.println("All tables created successfully.");

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
