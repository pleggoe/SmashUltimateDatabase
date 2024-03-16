import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class dataBaseGui extends JFrame{

    competitorDataBase instance;
    private JTextField userFileNameInput;
    private JLabel enterFileNameText;
    private JLabel fileEcho;
    private JButton enterFileNameButton;
    private JPanel fileInPanel;
    private JButton printDB;
    private JTextArea dataBaseOutput;
    private JButton removeByTag;
    private JButton removeById;
    private JButton updatePlayer;
    private JButton generatePR;
    private JTextArea prOutput;
    private JButton exit;

    public dataBaseGui() {
        competitorDataBase instance = new competitorDataBase();

        setContentPane(fileInPanel);
        setTitle("Smash Ultimate Data Base");
        setSize(1200, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        enterFileNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = userFileNameInput.getText();
                File checkFile = new File(fileName);
                if(checkFile.exists())
                {
                    try {
                        instance.readPlayers(fileName);
                        fileEcho.setText("Loaded Data Base from: " + fileName);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else
                {
                    fileEcho.setText("Error: File not found");
                }

            }
        });
        printDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataBaseOutput.setText(instance.printGUIPlayers());
            }
        });
        removeByTag.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tagToRemove = JOptionPane.showInputDialog(null,"Enter player tag");
                for(competitor i : instance.players)
                {
                    if(i.playerTag.equals(tagToRemove))
                    {
                        JOptionPane.showMessageDialog(null, "Removed Player: " + tagToRemove);
                        instance.removeCompetitorPlayerTag(tagToRemove);
                        dataBaseOutput.setText(instance.printGUIPlayers());
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Player not found");
            }
        });
        removeById.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idToRemove = 0;
                String idInput = JOptionPane.showInputDialog(null,"Enter player ID");
                try
                {
                    idToRemove = Integer.parseInt(idInput);
                }
                catch (NumberFormatException nfe)
                {
                    JOptionPane.showMessageDialog(null,"Please enter a number");
                    return;
                }
                for(competitor i : instance.players)
                {
                    if(i.playerId == idToRemove)
                    {
                        JOptionPane.showMessageDialog(null, "Removed Player: " + idToRemove);
                        instance.removeCompetitorPlayerId(idToRemove);
                        dataBaseOutput.setText(instance.printGUIPlayers());
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Player not found");
            }
        });
        updatePlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerTag, stringWins, stringLosses, lastPlacement;
                int wins, losses;
                competitor updatedPlayer;

                String tagToUpdate = JOptionPane.showInputDialog(null,"Enter player tag");
                for(competitor i : instance.players)
                {
                    if(i.playerTag.equals(tagToUpdate))
                    {
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
                        lastPlacement = JOptionPane.showInputDialog(null, "Enter updated player last placemnet");
                        updatedPlayer = new competitor(playerTag, wins, losses, lastPlacement);
                        instance.updatePlayer(tagToUpdate, updatedPlayer);
                        dataBaseOutput.setText(instance.printGUIPlayers());
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Player not found");
            }
        });
        generatePR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prOutput.setText(instance.generatePowerRankingsGUI());
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String [] args) throws IOException
    {
        dataBaseGui myGui = new dataBaseGui();
    }
}
