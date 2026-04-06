import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PetAdoptionGUI 
{
    public static void main(String[] args) 
    {
        //create main window
        JFrame frame = new JFrame("Pet Adoption Management System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //text area to display results
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);

        //panel for buttons
        JPanel panel = new JPanel();

        JButton viewPetsBtn = new JButton("View Available Pets");
        JButton searchBtn = new JButton("Search Pets");
        JButton shelterBtn = new JButton("View Shelters");

        panel.add(viewPetsBtn);
        panel.add(searchBtn);
        panel.add(shelterBtn);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);

        String url = "jdbc:sqlite:petadoption.db";

        //VIEW PETS BUTTON
        viewPetsBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    Connection conn = DriverManager.getConnection(url);
                    Statement stmt = conn.createStatement();

                    String sql = "SELECT * FROM Pet WHERE adoption_status = 'Available'";
                    ResultSet rs = stmt.executeQuery(sql);

                    outputArea.setText("Available Pets:\n\n");

                    while (rs.next()) 
                    {
                        outputArea.append(
                            rs.getInt("pet_id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getString("species") + " | " +
                            rs.getString("breed") + " | " +
                            rs.getInt("age") + "\n"
                        );
                    }

                    rs.close();
                    stmt.close();
                    conn.close();
                } 
                catch (Exception ex) 
                {
                    outputArea.setText("Error loading pets.");
                }
            }
        });

        //SEARCH PETS BUTTON
        searchBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                String species = JOptionPane.showInputDialog("Enter species:");

                try 
                {
                    Connection conn = DriverManager.getConnection(url);
                    Statement stmt = conn.createStatement();

                    String sql = "SELECT * FROM Pet WHERE species = '" + species + "'";
                    ResultSet rs = stmt.executeQuery(sql);

                    outputArea.setText("Search Results:\n\n");

                    boolean found = false;

                    while (rs.next()) 
                    {
                        found = true;
                        outputArea.append(
                            rs.getInt("pet_id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getString("species") + " | " +
                            rs.getString("breed") + " | " +
                            rs.getInt("age") + " | " +
                            rs.getString("adoption_status") + "\n"
                        );
                    }

                    if (!found) 
                    {
                        outputArea.append("No pets found.\n");
                    }

                    rs.close();
                    stmt.close();
                    conn.close();
                } 
                catch (Exception ex) 
                {
                    outputArea.setText("Error searching pets.");
                }
            }
        });

        //VIEW SHELTERS BUTTON
        shelterBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                try 
                {
                    Connection conn = DriverManager.getConnection(url);
                    Statement stmt = conn.createStatement();

                    String sql = "SELECT * FROM Shelter";
                    ResultSet rs = stmt.executeQuery(sql);

                    outputArea.setText("Shelters:\n\n");

                    while (rs.next()) 
                    {
                        outputArea.append(
                            rs.getInt("shelter_id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getString("location") + "\n"
                        );
                    }

                    rs.close();
                    stmt.close();
                    conn.close();
                } 
                catch (Exception ex) 
                {
                    outputArea.setText("Error loading shelters.");
                }
            }
        });

        //show window
        frame.setVisible(true);
    }
}
