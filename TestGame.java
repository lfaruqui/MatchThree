//Written by Matthew Myles & Hamed Faruqui

import java.util.Scanner;

public class TestGame
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);                                         //Creates scanner for input
        int rows;                                                                    //Integer for row input
        int cols;                                                                    //Integer for column input
        String gameType;                                                             //String for game type / mode

        System.out.println("Do you want to play endlessly or a single game? (Enter endless or single)");        //Prompts for game type
        gameType= in.next();
        while(!gameType.toLowerCase().equals("endless") && !gameType.toLowerCase().equals("single"))            //Validity check on gameType
        {
            System.out.println("Please enter correct value");
            gameType= in.next();
        }
        System.out.println("How many rows would you like for your grid?");                                     //Prompts for rows
        rows= in.nextInt();
        while(rows <= 0)                                                                                      //Input validity check for non-usable values
        {
            System.out.println("Please enter correct value");
            rows= in.nextInt();
        }
        System.out.println("How many columns would you like for your grid?");                                //Prompts for columns
        cols = in.nextInt();
        while(cols <= 0)                                                                                    //Checks to be sure it's an actual value
        {
            System.out.println("Please enter correct value");
            cols= in.nextInt();
        }

        Grid matchGrid= new Grid(rows,cols);                                                              //Constructs grid based off of rows and columsn
        matchGrid.buildGrid();                                                                            //Fills the grid with numbers
        MatchGame matchGame= new MatchGame(matchGrid);                                                    //Constructs new instance of MatchGame and passes in grid
        Player player= new Player();                                                                      //Constructs new player

        if(gameType.toLowerCase().equals("single"))                                                       //Checks to see what type of game is being played with if statements
        {
            matchGame.playSingleGame(player);
        }
        else
        {
            matchGame.playEndlessGame(player);
        }
    }
}