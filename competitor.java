
/*
 * Peyton Leggoe
 * competitor.java
 * 2/19/2024
 * The class competitor is used to create an object competitor for use in the Smash Ultimate Database program.
 * Competitor houses data members playerTag, wins, losses, ratio, activeStatus, lastPlacement, and playerId.
 * A default constructor was made with empty strings and sets the id to 0 by default.
 * Data members ratio, activeStatus, and playerId are generated/calculated in its constructor method.
 */
public class competitor {
    String playerTag;
    int wins, losses;
    float ratio;
    boolean activeStatus;
    String lastPlacement;
    int playerId;

    competitor() {
        playerTag = "";
        wins = 0;
        losses = 0;
        ratio = 0;
        activeStatus = false;
        lastPlacement = "";
        playerId = 0;
    }

    competitor(String playerTag, int wins, int losses, String lastPlacement) {
        this.playerTag = playerTag;
        this.wins = wins;
        this.losses = losses;
        ratio = (wins * 100) / (wins + losses);
        this.lastPlacement = lastPlacement;
        activeStatus = true;
        playerId = generatePlayerId(playerTag);
    }

    int generatePlayerId(String playerTag)
    {
        int newPlayerId = 0;

        for(int i = 0; i < playerTag.length(); i++)
        {
            newPlayerId += (int) playerTag.charAt(i);
        }

        return newPlayerId;
    }

    String getPlayerTag()
    {
        return playerTag;
    }

    int getWins()
    {
        return wins;
    }

    int getLosses()
    {
        return losses;
    }

    float getRatio()
    {
        return ratio;
    }

    boolean isActiveStatus()
    {
        return activeStatus;
    }

    String getLastPlacement()
    {
        return lastPlacement;
    }

    int getPlayerId()
    {
        return playerId;
    }

}
