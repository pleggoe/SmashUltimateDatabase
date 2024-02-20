import java.util.*;
import java.io.*;

/*
 * Peyton Leggoe
 * CEN 3042C-26663
 * 2/19/2024
 * SmashUltimateDatabase.java
 * This class houses the main method for the program. This program reads in the existing database
 * of competitors from a .txt file formatted as "playerTag,wins,losses,lastPlacement" and allows the user to remove a competitor
 * either by their tag or ID, update a player's data, generate current power rankings, and show all competitors in the database.
 */
public class SmashUltimateDatabase {
    ArrayList<competitor> players = new ArrayList<competitor>();

    public static void main(String[] args) throws IOException{
        SmashUltimateDatabase instance = new SmashUltimateDatabase();
        Scanner input = new Scanner(System.in);
        String fileName;

        System.out.print("Please enter name of file: ");
        fileName = input.nextLine();
        instance.readPlayers(fileName);
        instance.printPlayers();

        char menuOption;
        boolean exitCon = true;

        while(exitCon)
        {
            System.out.println("1:Show all competitors\n2:Remove competitor\n3:Update Player\n4:Generate Current Power Rankings\n5:Exit Program");
            menuOption = input.nextLine().charAt(0);

            switch(menuOption)
            {
                case 49:
                    System.out.println("Option 1 selected");
                    instance.printPlayers();
                    break;

                case 50:
                    System.out.println("Option 2 selected");
                    System.out.println("Enter method of removal\n1:Player Tag\n2:Player ID");
                    char removalOption = input.nextLine().charAt(0);
                    if(removalOption == 49)
                    {
                        instance.removeCompetitorPlayerTag();
                    }
                    else if(removalOption == 50)
                    {
                        instance.removeCompetitorPlayerId();
                    }
                    else
                    {
                        System.out.println("Invalid option selected");
                    }
                    break;

                case 51:
                    System.out.println("Option 3 selected");
                    instance.updatePlayer();
                    break;

                case 52:
                    instance.generatePowerRankings();
                    break;

                case 53:
                    System.out.println("Goodbye");
                    exitCon = false;
                    break;

                default:
                    System.out.println("Invalid option selected");
                    break;
            }
        }
    }

    /*
     * method: removeCompetitorPlayerTag
     * parameters: none
     * return: none
     * purpose: removes player from database based on player's tag
     */
    void removeCompetitorPlayerTag()
    {
        Scanner input = new Scanner(System.in);
        String playerRemoveTag;

        System.out.print("Enter player tag for removal: ");
        playerRemoveTag = input.nextLine();

        for(competitor i : players)
        {
            if(i.playerTag.equals(playerRemoveTag))
            {
                System.out.println(i.playerTag + ", player ID: " + i.playerId + ", wins: " + i.wins + ", losses: " + i.losses + ", W/L ratio: " + i.ratio + ", active status: " + i.activeStatus + ", last placement: " + i.lastPlacement);
                players.remove(i);
                printPlayers();
                return;
            }
        }
        System.out.println("PlayerTag not found\n");
    }

    /*
     * method: removeCompetitorPlayerId
     * parameters: none
     * return: none
     * purpose: removes player from database based on player's ID
     */
    void removeCompetitorPlayerId()
    {
        Scanner input = new Scanner(System.in);
        int playerRemoveId;

        System.out.print("Enter player ID for removal: ");
        playerRemoveId = input.nextInt();

        for(competitor i : players)
        {
            if(i.playerId == playerRemoveId)
            {
                System.out.println(i.playerTag + ", player ID: " + i.playerId + ", wins: " + i.wins + ", losses: " + i.losses + ", W/L ratio: " + i.ratio + ", active status: " + i.activeStatus + ", last placement: " + i.lastPlacement);
                players.remove(i);
                printPlayers();
                return;
            }
        }
        System.out.println("PlayerTag not found\n");
    }

    /*
     * method: updatePlayer
     * parameters: none
     * return: none
     * purpose: updates an existing player in the database, will ask user for data members
     * playerTag, wins, losses, and lastPlacement
     */
    void updatePlayer()
    {
        Scanner input = new Scanner(System.in);
        String playerTag;

        System.out.print("Enter the tag of the player to update: ");
        playerTag = input.nextLine();

        for(competitor i : players)
        {
            if(i.playerTag.equals(playerTag))
            {
                String garbage;

                String updatedPlayerTag;
                int wins;
                int losses;
                String lastPlacement;
                System.out.println(i.playerTag + ", player ID: " + i.playerId + ", wins: " + i.wins + ", losses: " + i.losses + ", W/L ratio: " + i.ratio + ", active status: " + i.activeStatus + ", last placement: " + i.lastPlacement);

                System.out.print("Enter player tag: ");
                updatedPlayerTag = input.nextLine();
                System.out.print("\nEnter wins: ");
                wins = input.nextInt();
                System.out.print("\nEnter losses: ");
                losses = input.nextInt();
                System.out.print("\nEnter last placement: ");
                garbage = input.nextLine();
                lastPlacement = input.nextLine();

                competitor tempPlayer = new competitor(updatedPlayerTag, wins, losses, lastPlacement);
                players.remove(i);
                players.add(tempPlayer);
                printPlayers();
                return;
            }
        }
        System.out.println("PlayerTag not found\n");
    }

    /*
     * method: generatePowerRankings
     * parameters: none
     * return: none
     * purpose: Generates current power rankings based off of win/loss ratio
     */
    void generatePowerRankings()
    {
        ArrayList<competitor> powerRanking = players;

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

        System.out.println("Current Power Rankings\n________________________");
        for(int i = 0; i < powerRanking.size(); i++)
        {
            System.out.println("#" + (i + 1) + ": " + powerRanking.get(i).playerTag);
        }
        System.out.println("_________________________");
    }

    /*
     * method: printPlayers
     * parameters: none
     * return: none
     * purpose: Prints current database of all players showing all data members for each player
     */
    void printPlayers()
    {
        System.out.println("Printing current Data base.");
        System.out.println("---------------------------------------------");
        for(competitor i: players)
        {
            System.out.println(i.playerTag + ", player ID: " + i.playerId + ", wins: " + i.wins + ", losses: " + i.losses + ", W/L ratio: " + i.ratio + ", active status: " + i.activeStatus + ", last placement: " + i.lastPlacement);
        }
        System.out.println("---------------------------------------------");
        System.out.println("Done!");
    }


    /*
     * method: readPlayers
     * parameters: none
     * return: none
     * purpose: reads in user given .txt file and creates competitor objects for ArrayList players
     */
    void readPlayers(String fileName) throws IOException
    {
        Scanner fileIn = new Scanner(new File("C:\\Users\\Vipertoo\\Desktop\\CEN 3024\\projects\\SmashUltiDataBase\\src\\" + fileName));

        System.out.println("Loading collection");

        while(fileIn.hasNextLine())
        {
            String playerTag;
            int wins;
            int losses;
            String lastPlacement;

            int inputLength;
            int i = 0;
            String forLoopInput = "";

            String collectionInput = fileIn.nextLine();
            inputLength = collectionInput.length();

            for(; collectionInput.charAt(i) != ','; i++)
            {
                forLoopInput = forLoopInput + collectionInput.charAt(i);
            }
            playerTag = forLoopInput;
            forLoopInput = "";
            i++;

            for(; collectionInput.charAt(i) != ','; i++)
            {
                forLoopInput = forLoopInput + collectionInput.charAt(i);
            }
            wins = Integer.parseInt(forLoopInput);
            forLoopInput = "";
            i++;

            for(; collectionInput.charAt(i) != ','; i++)
            {
                forLoopInput = forLoopInput + collectionInput.charAt(i);
            }
            losses = Integer.parseInt(forLoopInput);
            forLoopInput = "";
            i++;

            for(; i < inputLength; i++)
            {
                forLoopInput = forLoopInput + collectionInput.charAt(i);
            }
            lastPlacement = forLoopInput;
            forLoopInput = "";
            i++;

            competitor tempPlayer = new competitor(playerTag, wins, losses, lastPlacement);
            players.add(tempPlayer);
        }

        fileIn.close();

        System.out.println("Done!\n");

    }
}
