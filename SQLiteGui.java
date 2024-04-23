import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.*;
import java.util.*;

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
    private JButton removeByTag;
    private JButton removeById;
    private JButton genPowerRanks;
    private JTextArea prOutput;
    private JButton exit;

    /**
     * This method provides the functionality for each button and text box for the GUI.
     * @param connection contains the jdbc connection for the SQLite database used for the program
     */
    public SQLiteGui(Connection connection) {
        setContentPane(fileInPanel);
        setTitle("Smash Ultimate DataBase");
        setSize(1200, 800);
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
                            if(i == 1)
                            {
                                retString += "ID: " + result.getString(i) + " | ";
                            }
                            else if(i == 2)
                            {
                                retString += "Tag: " + result.getString(i) + " | ";
                            }
                            else if(i == 3)
                            {
                                retString += "Wins: " + result.getString(i) + " | ";
                            }
                            else if(i == 4)
                            {
                                retString += "Losses:" + result.getString(i) + " | ";
                            }
                            else if(i == 5)
                            {
                                retString += "W/L Ratio: " + result.getString(i) + " | ";
                            }
                            else if(i == 6)
                            {
                                retString += "Last Placement: " + result.getString(i) + " | ";
                            }
                            else if(i == 7)
                            {
                                boolean activeStatus = false;
                                if(result.getInt(i) == 1)
                                {
                                    activeStatus = true;
                                }
                                retString += "Active Status: " + activeStatus;
                            }
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
                    boolean foundPlayer = findPlayerTag(result, tagToUpdate);

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

        removeByTag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tagToRemove = JOptionPane.showInputDialog(null, "Enter player tag");

                try
                {
                    String query = "SELECT * FROM players";
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery(query);

                    boolean foundPlayer = findPlayerTag(result, tagToRemove);

                    if(foundPlayer)
                    {
                        removePlayerByTag(tagToRemove, connection);
                        JOptionPane.showMessageDialog(null, "Successfully removed player: " + tagToRemove);
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
        removeById.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stringIdToRemove = JOptionPane.showInputDialog(null, "Enter player ID");
                int idToRemove;

                try
                {
                    idToRemove = Integer.parseInt(stringIdToRemove);
                }
                catch(NumberFormatException nfe)
                {
                    JOptionPane.showMessageDialog(null, "Please enter a number");
                    return;
                }

                try
                {
                    String query = "SELECT * FROM players";
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery(query);

                    boolean foundPlayer = findPlayerId(result, idToRemove);

                    if(foundPlayer)
                    {
                        removePlayerById(idToRemove, connection);
                        JOptionPane.showMessageDialog(null, "Successfully removed player: " + idToRemove);
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
        genPowerRanks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    prOutput.setText(generatePowerRankings(connection));
                }
                catch (SQLException q)
                {
                }
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
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

    boolean findPlayerTag(ResultSet result, String playerTag) throws SQLException
    {
        boolean foundPlayer = false;

        while(result.next())
        {
            if(result.getString("playerTag").equals(playerTag))
            {
                foundPlayer = true;
            }
        }
        return foundPlayer;
    }

    boolean findPlayerId(ResultSet result, int playerId) throws SQLException
    {
        boolean foundPlayer = false;

        while(result.next())
        {
            if(result.getInt("playerId") == playerId)
            {
                foundPlayer = true;
            }
        }
        return foundPlayer;
    }

    void removePlayerByTag(String tagToRemove, Connection conn) throws SQLException
    {
        String query = "DELETE FROM players WHERE playerTag = ?";

        PreparedStatement prepState = conn.prepareStatement(query);
        prepState.setString(1, tagToRemove);
        prepState.executeUpdate();
    }

    void removePlayerById(int idToRemove, Connection conn) throws SQLException
    {
        String query = "DELETE FROM players WHERE playerId = ?";

        PreparedStatement prepState = conn.prepareStatement(query);
        prepState.setInt(1, idToRemove);
        prepState.executeUpdate();
    }

    String generatePowerRankings(Connection conn) throws SQLException
    {
        ArrayList<competitor> players = new ArrayList<competitor>();
        String query = "SELECT * FROM players";
        String powerRankings;

        try
        {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);

            while(result.next())
            {
                int wins = 0, losses = 0;
                String playerTag = "", lastPlacement = "";
                for(int i = 1; i <= 7; i++)
                {
                    if(i == 2)
                    {
                        playerTag += result.getString(i);
                    }
                    if(i == 3)
                    {
                        wins = Integer.parseInt(result.getString(i));
                    }
                    if(i == 4)
                    {
                        losses = Integer.parseInt(result.getString(i));
                    }
                    if(i == 6)
                    {
                        lastPlacement += result.getString(i);
                    }
                }
                competitor tempPlayer = new competitor(playerTag, wins, losses, lastPlacement);
                players.add(tempPlayer);
            }
        }
        catch(SQLException p)
        {
        }

        powerRankings = calculatePowerRankings(players);
        return powerRankings;
    }

    String calculatePowerRankings(ArrayList<competitor> players)
    {
        ArrayList<competitor> powerRanking = players;
        String retString = "";

        for(int i = 0; i < players.size(); i++)
        {
            for(int j = powerRanking.size() - 1; j > i; j--)
            {
                if(powerRanking.get(i).ratio < powerRanking.get(j).ratio)
                {
                    competitor temp = powerRanking.get(i);
                    powerRanking.set(i, powerRanking.get(j));
                    powerRanking.set(j, temp);
                }
            }
        }

        for(int i = 0; i < powerRanking.size(); i++)
        {
            retString += "\n" + "#" + (i + 1) + ": " + powerRanking.get(i).playerTag;
        }

        return retString;
    }

    public static void main(String [] args) throws SQLException
    {
        String url = "jdbc:sqlite:SUDataBase.db";
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
