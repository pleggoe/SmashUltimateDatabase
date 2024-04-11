import java.util.*;
import java.io.*;

/**
 * Peyton Leggoe,
 * CEN 3042C-26663,
 * 2/19/2024.
 * This class houses the main method for the program. This program reads in the existing database
 * of competitors from a .txt file formatted as "playerTag,wins,losses,lastPlacement" and allows the user to remove a competitor
 * either by their tag or ID, update a player's data, generate current power rankings, and show all competitors in the database.
 */
public class SmashUltimateDatabase {

    public static void main(String[] args) throws IOException{
        String fileName;
        competitorDataBase instance = new competitorDataBase();
        Scanner input = new Scanner(System.in);
        boolean fileExitCon = true;

        while(fileExitCon)
        {
            System.out.print("Please enter name of file: ");
            fileName = input.nextLine();
            File checkFile = new File(fileName);
            if(checkFile.exists())
            {
                fileExitCon = false;
                System.out.println("Loading collection");
                instance.readPlayers(fileName);
                System.out.println("Done!\n");
                instance.printPlayers();
            }
            else
            {
                System.out.println("Error: file not found");
            }
        }

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
                        String playerRemoveTag;

                        System.out.print("Enter player tag for removal: ");
                        playerRemoveTag = input.nextLine();
                        instance.removeCompetitorPlayerTag(playerRemoveTag);
                    }
                    else if(removalOption == 50)
                    {
                        int playerRemoveId;
                        System.out.print("Enter player ID for removal: ");
                        try
                        {
                            playerRemoveId = Integer.parseInt(input.nextLine());
                            instance.removeCompetitorPlayerId(playerRemoveId);
                        }
                        catch (NumberFormatException nfe)
                        {
                            System.out.println("Number not inputted, returning to main menu\n");
                        }
                    }
                    else
                    {
                        System.out.println("Invalid option selected");
                    }
                    break;

                case 51:
                    System.out.println("Option 3 selected");
                    String playerTag;

                    System.out.print("Enter the tag of the player to update: ");
                    playerTag = input.nextLine();
                    boolean foundPlayer = false;

                    for(competitor i : instance.players)
                    {
                        if(i.playerTag.equals(playerTag))
                        {
                            foundPlayer = true;
                            String garbage;

                            String updatedPlayerTag;
                            int wins = 0;
                            int losses;
                            String lastPlacement;
                            System.out.println(i.playerTag + ", player ID: " + i.playerId + ", wins: " + i.wins + ", losses: " + i.losses + ", W/L ratio: " + i.ratio + ", active status: " + i.activeStatus + ", last placement: " + i.lastPlacement);

                            System.out.print("Enter updated player tag: ");
                            updatedPlayerTag = input.nextLine();
                            System.out.print("\nEnter wins: ");
                            try
                            {
                                wins = input.nextInt();
                            }
                            catch (InputMismatchException ime)
                            {
                                System.out.println("Number not inputted, returning to main menu\n");
                                garbage = input.nextLine();
                                break;
                            }
                            System.out.print("\nEnter losses: ");
                            try
                            {
                                losses = input.nextInt();
                            }
                            catch (InputMismatchException ime)
                            {
                                System.out.println("Number not inputted, returning to main menu\n");
                                garbage = input.nextLine();
                                break;
                            }
                            System.out.print("\nEnter last placement: ");
                            garbage = input.nextLine();
                            lastPlacement = input.nextLine();

                            competitor tempPlayer = new competitor(updatedPlayerTag, wins, losses, lastPlacement);
                            instance.updatePlayer(playerTag, tempPlayer);
                            instance.printPlayers();
                            break;
                        }
                    }
                    if(!foundPlayer)
                    {
                        System.out.println("PlayerTag not found\n");
                    }
                    break;

                case 52:
                    ArrayList<competitor> powerRankings = instance.generatePowerRankings();
                    System.out.println("Current Power Rankings\n________________________");
                    for(int i = 0; i < powerRankings.size(); i++)
                    {
                        System.out.println("#" + (i + 1) + ": " + powerRankings.get(i).playerTag);
                    }
                    System.out.println("_________________________");
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

}
