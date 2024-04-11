import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Peyton Leggoe,
 * CEN 3042C-26663,
 * 2/28/2024.
 * This class contains all test cases for the Smash Ultimate Database. Each test case is designed to ensure
 * functionality using the test case results1.txt.
 */
class SmashUltimateDatabaseTest {

    competitor testPlayer;
    competitorDataBase dataBase = new competitorDataBase();

    boolean findPlayerTag(String playerTag)
    {
        boolean retVal = false;

        for(competitor i: dataBase.players)
        {
            if(i.playerTag.equals(playerTag))
            {
                retVal = true;
                break;
            }
        }

        return retVal;
    }

    boolean findPlayerId(int playerId)
    {
        boolean retVal = false;

        for(competitor i: dataBase.players)
        {
            if(i.playerId == playerId)
            {
                retVal = true;
                break;
            }
        }

        return retVal;
    }

    @BeforeEach
    void setUp() throws IOException
    {
        testPlayer = new competitor("testPlayer", 14, 32, "23/54");
        dataBase.readPlayers("results1.txt");
    }

    @Test
    @DisplayName("Add player to Data Base")
    void addPlayer()
    {
        dataBase.players.add(testPlayer);
        assertTrue(findPlayerTag(testPlayer.playerTag), "Error: Player not added");
    }

    @Test
    @DisplayName("Generate Player ID")
    void generatePlayerId() {
        int expectedID = 0;

        for(int i = 0; i < testPlayer.playerTag.length(); i++)
        {
            expectedID += testPlayer.playerTag.charAt(i);
        }

        assertEquals(expectedID, testPlayer.playerId, "Error: Incorrect ID generated");
    }

    @Test
    @DisplayName("Remove Player by Tag")
    void removeCompetitorPlayerTag() {
        dataBase.removeCompetitorPlayerTag("player1");
        assertFalse(findPlayerTag("player1"), "Error: Player not removed");
    }

    @Test
    @DisplayName("Remove Player by ID")
    void removeCompetitorPlayerId() {
        dataBase.removeCompetitorPlayerId(702);
        assertFalse(findPlayerId(702), "Error: Player not removed");
    }

    @Test
    @DisplayName("Update Player")
    void updatePlayer() {
        dataBase.updatePlayer("player1", testPlayer);
        assertTrue(findPlayerTag(testPlayer.playerTag), "Error: Updated Player not added");
    }

    @Test
    @DisplayName("Generate Power Rankings")
    void generatePowerRankings() {
        ArrayList<competitor> powerRanking = dataBase.generatePowerRankings();

        ArrayList<competitor> testPowerRanking = dataBase.players;

        for(int i = 0; i < dataBase.players.size(); i++)
        {
            for(int j = testPowerRanking.size() - 1; j > i; j--)
            {
                if(testPowerRanking.get(i).ratio < testPowerRanking.get(j).ratio)
                {
                    competitor temp = testPowerRanking.get(i);
                    testPowerRanking.set(i, testPowerRanking.get(j));
                    testPowerRanking.set(j, temp);
                }
            }
        }

        assertEquals(testPowerRanking, powerRanking, "Error: Power Rankings do not match");
    }
}