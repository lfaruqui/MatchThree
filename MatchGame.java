//Written by Matthew Myles & Hamed Faruqui

import java.util.Random;
import java.util.Scanner;

public class MatchGame
{
    private Scanner in= new Scanner(System.in);                             //Scanner to take input
    private Random rand;                                                    //Random to repopulate matrix
    private int turns;                                                      //Integer for the turns
    private int level = 1;                                                  //Integer for the current level
    private int range = 3;                                                  //Integer for the current range of numbers
    private int rowInp;                                                     //Integer for input of row to swap from
    private int colInp;                                                     //Input for column to swap from
    private String directInp;                                               //String for the direction to swap in
    private int[][] gameGrid;                                               //Actual 2d array for the game
    private int[][] matchesGrid;                                            //2d array  to use in finding values to replace

    public MatchGame(Grid grid)                                             //Constructor for match game
    {
        gameGrid= grid.getGrid();                                          //Sets grid for the actual game to grid built already
        matchesGrid= new int[grid.getRowsN()][grid.getColsM()];           //Initializes grid to test matches with to array of same size
        rand= new Random();
    }

    public void playEndlessGame(Player player)                            //Method to play endlessly
    {
        int input = 0;
        int replacedHorizontal = 0;                                      //Integer for checking if there was a horizontal match
        int replacedVertical  =0;                                       //Integer for checking if there was a vertical match

        while(replacedHorizontal != -1 && replacedVertical != -1)        //Checks and replaces matches before play, so player starts with clean slate
        {
            replacedHorizontal = replaceValues(checkHorizontalMatch());
            replacedVertical = replaceValues(checkVerticalMatch());
        }

        gamePrompt();                                                    //Prints the prompt

        while(input != -1)                                               //While player's input isn't -1, or "end", game will continue
        {
            printGrid();
            inputSwap();
            replacedHorizontal = 0;
            replacedVertical  =0;
            while(replacedHorizontal != -1 && replacedVertical != -1)                   //While loop to repeatedly check for matches
            {
                replacedHorizontal = replaceValues(checkHorizontalMatch());
                if (replacedHorizontal == 0)                                            //If there is a match, update points and notify player
                {
                    player.setLevelPoints(1);
                    player.setTotalPoints(1);
                    System.out.println("You got a match!");
                }
                replacedVertical = replaceValues(checkVerticalMatch());                //If there is a match, update points and notify player
                if (replacedVertical == 0)
                {
                    player.setLevelPoints(1);
                    player.setTotalPoints(1);
                    System.out.println("You got a match!");
                }
                printStats(player);
                if(player.getLevelPoints() >= 10)                                   //Breaks if they get 10 points, therefore reaching a new level and no need for checks
                {
                    break;
                }
            }
            printGrid();
            printStats(player);
            if(player.getLevelPoints() >= 10)                                       //If statement to check points and update grid and level
            {
                newGridLevel(level, player);
                updateLevel();
            }
            System.out.println("Enter 1 to continue the game, -1 to exit");         //Prompts player to continue
            input= in.nextInt();
        }
        System.out.println("Thanks for playing!");
    }


    public void playSingleGame(Player player)                                   //Method for playing a single game
    {
        int input = 0;
        int replacedHorizontal = 0;
        int replacedVertical  =0;

        while(replacedHorizontal != -1 && replacedVertical != -1)              //Checks for matches beforehand, so player starts with clean grid
        {
            replacedHorizontal = replaceValues(checkHorizontalMatch());
            replacedVertical = replaceValues(checkVerticalMatch());
        }

        gamePrompt();

        while(player.getTotalPoints() < 10)                                    //While player doesn't have 10 points, as it's only one game
        {
            printGrid();
            inputSwap();
            replacedHorizontal = 0;
            replacedVertical  =0;
            while(replacedHorizontal != -1 && replacedVertical != -1)          //Repeatedly checks and replaces matches, until there are none for player
            {
                replacedHorizontal = replaceValues(checkHorizontalMatch());
                if (replacedHorizontal == 0)                                  //If there is a match, update points and notify player
                {
                    player.setTotalPoints(1);
                    System.out.println("You got a match!");
                }
                replacedVertical = replaceValues(checkVerticalMatch());       //If there is a match, update points and notify player
                if (replacedVertical == 0)
                {
                    player.setTotalPoints(1);
                    System.out.println("You got a match!");
                }
            }
            printStats(player);
        }
        System.out.println("You won! Thanks for playing!");
    }



    public void gamePrompt()                                                    //Method to print welcome message
    {
        System.out.println("Welcome To The Match-Three Game!");
        System.out.println("Your goal is to beat each level by swapping the values of the cells, in order to match three values across or vertically.");
        System.out.println("Each time you create a match you gain 1 point, 10 points for a new level on endless. Good Luck!\n");
    }


    public void updateLevel()                                                   //Method to increase level and range, called when 10 points are reached
    {
        level++;
        range++;
    }

   public void inputSwap()                                                      //Method for player to swap specific cell with another
   {
       int[][] tempArray= new int [gameGrid.length][gameGrid[0].length];                //Temp 2d array to use in swapping
       System.out.println("Which cell would you like to move?" + "\nEnter row:");       //Prompts for row of cell
       rowInp= in.nextInt()-1;
       while(rowInp < 0 || rowInp >= gameGrid.length)                                   //Validity of exact row check
       {
           System.out.println("Oops! Your value will take you out of the grid! Please re-enter correctly: ");
           System.out.println("Enter row: ");
           rowInp= in.nextInt()-1;
       }

       System.out.println("Enter column: ");                                             //Prompts for column of cell
       colInp= in.nextInt()-1;
       while(colInp < 0 || colInp >= gameGrid[0].length)                                   //Validity of exact column check
       {
           System.out.println("Oops! Your value will take you out of the grid! Please re-enter correctly: ");
           System.out.println("Enter column: ");
           colInp= in.nextInt()-1;
       }

       System.out.println("Enter directional button (W,A,S,D): ");                   //Prompts for direction of swap
       directInp= in.next();
       while(!directInp.toLowerCase().equals("w") && !directInp.toLowerCase().equals("a") && !directInp.toLowerCase().equals("s") && !directInp.toLowerCase().equals("d"))          //Valid input check
       {
           System.out.println("Oops! Your input isn't a direction, please input correctly: ");
           directInp= in.next();
       }
       while((rowInp == 0 && directInp.equals("W")) || (colInp == 0 && directInp.equals("A")) || (rowInp+1 >= gameGrid.length && directInp.equals("S")) || (colInp+1 >= gameGrid[0].length && directInp.equals("D")))   //Validity checks
       {
           System.out.println("Oops! Your values will take you out of the grid! Please re-enter correctly: ");
           System.out.println("Enter row: ");                                             //Prompts for column of cell
           rowInp= in.nextInt()-1;
           System.out.println("Enter column: ");                                             //Prompts for column of cell
           colInp= in.nextInt()-1;
           System.out.println("Enter directional button (W,A,S,D): ");                   //Prompts for direction of swap
           directInp= in.next();
       }

       switch (directInp)                                                       //Switch statement based on direction input
       {
           case "W":                                                           //When direction is "W", or up, it will swap as below
               tempArray[rowInp][colInp] = gameGrid[rowInp - 1][colInp];
               gameGrid[rowInp - 1][colInp] = gameGrid[rowInp][colInp];
               gameGrid[rowInp][colInp] = tempArray[rowInp][colInp];
               break;
           case "D":                                                          //When direction is "D", or right, it will swap as below
               tempArray[rowInp][colInp] = gameGrid[rowInp][colInp+1];
               gameGrid[rowInp][colInp+1] = gameGrid[rowInp][colInp];
               gameGrid[rowInp][colInp] = tempArray[rowInp][colInp];
               break;
           case "A":                                                         //When direction is "A", or left, it will swap as below
               tempArray[rowInp][colInp] = gameGrid[rowInp][colInp - 1];
               gameGrid[rowInp][colInp - 1] = gameGrid[rowInp][colInp];
               gameGrid[rowInp][colInp] = tempArray[rowInp][colInp];
               break;
           case "S":                                                         //When direction is "S", or down, it will swap as below
               tempArray[rowInp][colInp] = gameGrid[rowInp+1][colInp];
               gameGrid[rowInp+1][colInp] = gameGrid[rowInp][colInp];
               gameGrid[rowInp][colInp] = tempArray[rowInp][colInp];
               break;
       }
       turns++;                                                             //Increases turns each swap
   }



    public int checkHorizontalMatch()                                       //Checks rows for horizontal match
    {
        for(int i = 0; i < gameGrid.length ; i++)                           //For loop for rows
        {
            for(int j = 0; j < gameGrid[0].length; j++)                     //For loop for columns
            {
                if(j == 0)                                                  //If cell is first one or "0"
                {
                    if (gameGrid[i][j] == gameGrid[i][j + 1] &&  gameGrid[i][j + 1] == gameGrid[i][j + 2])       //Checks for match of 3 in next 2
                    {
                        matchesGrid[i][j] = gameGrid[i][j];                //Passes those values into matchesGrid, other array to compare gameGrid to later
                        matchesGrid[i][j+1]= gameGrid[i][j];
                        matchesGrid[i][j+2]= gameGrid[i][j];
                        return 0;                                           //Returns 0 to indicate match was found
                    }
                }
                else if(j== gameGrid[0].length-1)                           //If cell is the very last in a row, or "j == gameGrid[0].length"
                {
                    if (gameGrid[i][j] == gameGrid[i][j-1] &&  gameGrid[i][j - 1] == gameGrid[i][j - 2])      //Checks for match in 2 before it
                    {
                        matchesGrid[i][j] = gameGrid[i][j];
                        matchesGrid[i][j-1]= gameGrid[i][j];
                        matchesGrid[i][j-2]= gameGrid[i][j];
                        return 0;                                          //Returns 0 to indicate match was found
                    }
                }
                else                                                     //Else, if cell is somewhere between start and end of row
                {
                    if (gameGrid[i][j] == gameGrid[i][j - 1] &&  gameGrid[i][j] == gameGrid[i][j + 1])         //Checks for match in cell before and cell after
                    {
                        matchesGrid[i][j] = gameGrid[i][j];
                        matchesGrid[i][j+1]= gameGrid[i][j];
                        matchesGrid[i][j-1]= gameGrid[i][j];
                        return 0;                                       //Returns 0 to indicate match was found
                    }
                }
            }
        }
        return -1;                                                      //Otherwise, if there are no matches, return -1
    }


    public int checkVerticalMatch()                                     //Checks columns for vertical match 
    {
        for(int i = 0; i < gameGrid.length ; i++)                       //For loop to step through rows
        {
            for(int j = 0; j < gameGrid[0].length; j++)                 //For loop to step through columns
            {
                if(i == 0)                                                              //If it's the first column
                {
                    if (gameGrid[i][j] == gameGrid[i+1][j] &&  gameGrid[i][j] == gameGrid[i+2][j])      //Checks for match the cells below it and adds them to matchGrid
                    {
                        matchesGrid[i][j] = gameGrid[i][j];
                        matchesGrid[i+1][j]= gameGrid[i+1][j];
                        matchesGrid[i+2][j]= gameGrid[i+2][j];
                        return 0;                                                                      //Returns that there was a match found
                    }
                }
                else if(i == gameGrid.length - 1)                                                      //Else if it's the last column
                {
                    if (gameGrid[i][j] == gameGrid[i-1][j] &&  gameGrid[i][j] == gameGrid[i-2][j])    //Checks for matches to 2 cells above it
                    {
                        matchesGrid[i][j] = gameGrid[i][j];
                        matchesGrid[i-1][j]= gameGrid[i-1][j];
                        matchesGrid[i-2][j]= gameGrid[i-2][j];
                        return 0;                                                                     //Returns that a match was found
                    }
                }
                else                                                                                  //Else if it's anywhere else, between first and last
                {
                    if (gameGrid[i][j] == gameGrid[i+1][j] &&  gameGrid[i][j] == gameGrid[i-1][j])      //Checks for match in cell above and cell below
                    {
                        matchesGrid[i][j] = gameGrid[i][j];
                        matchesGrid[i+1][j]= gameGrid[i+1][j];
                        matchesGrid[i-1][j]= gameGrid[i-1][j];
                        return 0;                                                                       //Returns that there was a match found, what 0 is used to represent
                    }
                }
            }
        }
        return -1;                                                                                     //Otherwise returns -1 for no match
    }


    public int replaceValues(int check)                                                 //Method to replace the match values
    {
        if(check == 0)                                                      //If there was a match found, passed in by one of the checkMatch methods, replace them
        {
            for (int i = 0; i < gameGrid.length; i++)                       //Nested for loop to step through gameGrid
            {
                for (int j = 0; j < gameGrid[0].length; j++)
                {
                    if(gameGrid[i][j] == matchesGrid[i][j])                 //If the value of a cell in gameGrid matches the value of a cell in matchesGrid, replace the values
                    {
                        gameGrid[i][j]= rand.nextInt(range)+1;              //Replaces value in gameGrid to new random value of 1 to the current range
                        matchesGrid[i][j]=0;                                //Resets cell in coordinate back to zero
                    }
                }
            }
            return 0;
        }
        return -1;                                                          //Otherwise, if nothing to replace, return -1
    }



    public void newGridLevel(int levelNum, Player player)                                   //Method to change the level
    {
        player.setLevelPoints(0);                                                           //Sets level points back to zero
        System.out.println("New level! Give it a try!");                                    //Notifies player of new level
        for (int i = 0; i < gameGrid.length; i++)                                           //Nested for loop to redo the values in game grid
        {
            for (int j = 0; j < gameGrid[0].length; j++)
            {
                gameGrid[i][j] =  rand.nextInt(range+levelNum)+1;                   //Sets cells to new values of an increased range
            }
        }
        turns= 0;                                                                         //Resets turns
    }


    public void printGrid()                                                                //Method to print the game grid
    {
        for (int i = 0; i < gameGrid.length; i++)                                          //Nested for loop to step throught the rows and columns
        {
            for (int j = 0; j < gameGrid[0].length; j++)
            {
                System.out.print("[ " + gameGrid[i][j] + " ]");
            }
            System.out.println();
        }
    }

    public void printStats(Player player)                                           //Method to print player's stats
    {
        System.out.println( "Level: " + level + "\tTurns taken this level: " + turns + "\tTotal Points: " + player.getTotalPoints());
    }
}
