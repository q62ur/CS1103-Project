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

            //execute table creation
            stmt.execute(shelterTable);
            stmt.execute(petTable);

            //success message
            System.out.println("Shelter and Pet tables created successfully.");

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
