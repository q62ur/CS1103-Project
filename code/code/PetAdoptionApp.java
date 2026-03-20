//import scanner for user input
import java.util.Scanner;

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
                    System.out.println("Showing available pets...");
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
