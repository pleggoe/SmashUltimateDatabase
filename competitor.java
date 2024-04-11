
/**
 * Peyton Leggoe,
 * CEN 3042C-26663,
 * 2/19/2024.
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

    /**
     * Constructor method which generates object competitor
     * @param playerTag Tag of the competitor
     * @param wins Recorded wins of the competitor
     * @param losses Recorded losses of the competitor
     * @param lastPlacement Recorded lastPlacement of the competitor
     */
    competitor(String playerTag, int wins, int losses, String lastPlacement) {
        this.playerTag = playerTag;
        this.wins = wins;
        this.losses = losses;
        ratio = (wins * 100) / (wins + losses);
        this.lastPlacement = lastPlacement;
        activeStatus = true;
        playerId = generatePlayerId(playerTag);
    }

    /**
     * Generates a player ID for the given player tag
     * @param playerTag Tag of the player which is used for ID generation
     * @return Returns the generated ID for the player
     */
    int generatePlayerId(String playerTag)
    {
        int newPlayerId = 0;

        for(int i = 0; i < playerTag.length(); i++)
        {
            newPlayerId += (int) playerTag.charAt(i);
        }

        return newPlayerId;
    }

}
