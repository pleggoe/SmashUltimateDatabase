import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class competitorDataBase {
    ArrayList<competitor> players;

    /*
     * method: readPlayers
     * parameters: none
     * return: none
     * purpose: reads in user given .txt file and creates competitor objects for ArrayList players
     */
    void readPlayers(String fileName) throws IOException
    {
        players = new ArrayList<competitor>();
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
     * method: removeCompetitorPlayerTag
     * parameters: none
     * return: none
     * purpose: removes player from database based on player's tag
     */
    void removeCompetitorPlayerTag(String playerRemoveTag)
    {
        for(competitor i : players)
        {
            if(i.playerTag.equals(playerRemoveTag))
            {
                System.out.println("Player removed: " + i.playerTag + ", player ID: " + i.playerId + ", wins: " + i.wins + ", losses: " + i.losses + ", W/L ratio: " + i.ratio + ", active status: " + i.activeStatus + ", last placement: " + i.lastPlacement);
                players.remove(i);
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
    void removeCompetitorPlayerId(int playerRemoveId)
    {
        for(competitor i : players)
        {
            if(i.playerId == playerRemoveId)
            {
                System.out.println("Player removed: " + i.playerTag + ", player ID: " + i.playerId + ", wins: " + i.wins + ", losses: " + i.losses + ", W/L ratio: " + i.ratio + ", active status: " + i.activeStatus + ", last placement: " + i.lastPlacement);
                players.remove(i);
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
    void updatePlayer(String playerToUpdate, competitor updatedCompetitor)
    {
        for(competitor i : players)
        {
            if(i.playerTag.equals(playerToUpdate))
            {
                players.remove(i);
                players.add(updatedCompetitor);
                return;
            }
        }
    }

    /*
     * method: generatePowerRankings
     * parameters: none
     * return: none
     * purpose: Generates current power rankings based off of win/loss ratio
     */
    ArrayList<competitor> generatePowerRankings()
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

        return powerRanking;
    }
}
