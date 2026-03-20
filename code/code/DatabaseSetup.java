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

            //simple message to confirm connection
            System.out.println("Connected to database successfully.");

            //close statement and connection
            stmt.close();
            conn.close();
        }
        catch (Exception e)
        {
            //print error message if connection fails
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }
}
