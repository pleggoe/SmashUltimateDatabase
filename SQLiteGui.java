import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.*;

/**
 * Peyton Leggoe,
 * CEN 3042C-26663,
 * 4/4/2024.
 * This class houses the GUI for the Smash Ultimate database with the use of SQLite.
 */
public class SQLiteGui extends JFrame{

    private JTextArea dataBaseOutput;
    private JButton printDB;
    private JPanel fileInPanel;
    private JButton updatePlayer;

    /**
     * This method provides the functionality for each button and text box for the GUI.
     * @param connection contains the jdbc connection for the SQLite database used for the program
     */
    public SQLiteGui(Connection connection) {
        setContentPane(fileInPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        printDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "SELECT * FROM players";
                String retString = "";
                try
                {
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery(query);

                    while(result.next())
                    {
                        for(int i = 1; i <= 7; i++)
                        {
                            retString += result.getString(i) + ":";
                        }
                        retString += "\n";
                    }
                }
                catch(SQLException p)
                {
                }

                dataBaseOutput.setText(retString);
            }
        });
        updatePlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerTag, stringWins, stringLosses, lastPlacement;
                int wins, losses;
                String query = "SELECT * FROM players";
                try
                {
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery(query);
                    String tagToUpdate = JOptionPane.showInputDialog(null, "Enter player tag");
                    boolean foundPlayer = false;

                    while(result.next())
                    {
                        if(result.getString("playerTag").equals(tagToUpdate))
                        {
                            foundPlayer = true;
                        }
                    }
                    if(foundPlayer)
                    {
                        JOptionPane.showMessageDialog(null, "Found player");
                        playerTag = JOptionPane.showInputDialog(null, "Enter updated player tag");
                        stringWins = JOptionPane.showInputDialog(null, "Enter updated player wins");
                        try
                        {
                            wins = Integer.parseInt(stringWins);
                        }
                        catch (NumberFormatException nfe)
                        {
                            JOptionPane.showMessageDialog(null, "Please enter a number");
                            return;
                        }
                        stringLosses = JOptionPane.showInputDialog(null, "Enter updated player losses");
                        try
                        {
                            losses = Integer.parseInt(stringLosses);
                        }
                        catch (NumberFormatException nfe)
                        {
                            JOptionPane.showMessageDialog(null, "Please enter a number");
                            return;
                        }
                        lastPlacement = JOptionPane.showInputDialog(null, "Enter updated player last placement");
                        update(tagToUpdate, playerTag, wins, losses, lastPlacement, connection);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Did not find player");
                    }
                }
                catch(SQLException p)
                {
                }
            }
        });
    }

    void update(String playerToUpdate, String newTag, int wins, int losses, String lastPlacement, Connection conn) throws SQLException
    {
        String query = "UPDATE players SET playerId = ?, "
                + "playerTag = ?, "
                + "wins = ?, "
                + "losses = ?, "
                + "ratio = ?, "
                + "lastPlacement = ?, "
                + "activeStatus = ? "
                + "WHERE playerTag = ?";
        competitor newPlayer = new competitor(newTag, wins, losses, lastPlacement);

        PreparedStatement prepState = conn.prepareStatement(query);
        prepState.setInt(1, newPlayer.playerId);
        prepState.setString(2, newPlayer.playerTag);
        prepState.setInt(3, newPlayer.wins);
        prepState.setInt(4, newPlayer.losses);
        prepState.setFloat(5, newPlayer.ratio);
        prepState.setString(6, newPlayer.lastPlacement);
        prepState.setInt(7, 1);
        prepState.setString(8, playerToUpdate);

        prepState.executeUpdate();
    }

    public static void main(String [] args) throws SQLException
    {
        String url = "jdbc:sqlite:SUDataBaseTest.db";
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(url);
        }
        catch (SQLException e)
        {
            System.out.println("Error connection to database");
            e.printStackTrace();
        }

        SQLiteGui myGui = new SQLiteGui(connection);
    }
}
