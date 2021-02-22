import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class MatchGameFix
{
    private int turns=0;
    private int level = 1;
    private int range = 3;
    private Scanner in= new Scanner(System.in);
    private Random rand;
    private int rowInp;
    private int colInp;
    private String directInp;
    private int[][] gameGrid;
    private int [][] tempGrid;
    private int[][] coordinates = new int[3][2];

    private boolean horizMatch;
    private boolean vertiMatch;

    public MatchGameFix(Grid grid)
    {
        gameGrid= grid.getGrid();
        tempGrid= new int[grid.getRowsN()][grid.getColsM()];
        rand= new Random();
        horizMatch= false;
        vertiMatch= false;
    }

    public void game()
    {
        test();
       /* printGrid();
        inputSwap();
        checkHorizontalMatch();
        replaceValues();
        resetMatchChecks();
        printGrid();
        inputSwap();
        checkHorizontalMatch();
        resetMatchChecks();
        printGrid();*/
    }


    public void gamePrompt()
    {
        System.out.println("Welcome To The Match-Three Game!");
        System.out.println("Your goal is to beat each level by swapping the values of the cells, in order to match three values across or vertically.");
        System.out.println("Each time you create a match you gain 1 point, 10 points for a new level. Good Luck!\n");
    }


    public void updateLevel()
    {
        level++;
        range++;
    }

    public void resetMatchChecks()
    {
        horizMatch= false;
        vertiMatch= false;
    }

    public void inputSwap()
    {
        System.out.println("Which cell would you like to move?" + "\nEnter row:");
        rowInp= in.nextInt()-1;
        while(rowInp-1 == 0 || rowInp+1 >= gameGrid.length)
        {
            System.out.println("Oops! Your input will take you out of the game, please enter correctly by the grid size: ");
            rowInp= in.nextInt();
        }

        System.out.println("Enter column: ");
        colInp= in.nextInt()-1;
        while(colInp-1 == 0 || colInp+1 >= gameGrid[0].length)
        {
            System.out.println("Oops! Your input will take you out of the game, please enter correctly by the grid size: ");
            rowInp= in.nextInt();
        }

        System.out.println("Enter directional button (W,A,S,D): ");
        directInp= in.next();
        while(!directInp.toUpperCase().equals("W") || !directInp.toUpperCase().equals("A") || !directInp.toUpperCase().equals("S") || !directInp.toUpperCase().equals("D"))
        {
            System.out.println("Oops! Your input isn't a direction, please input correctly: ");
            rowInp= in.nextInt();
        }

        switch (directInp)
        {
            case "W":
                tempGrid[rowInp][colInp] = gameGrid[rowInp - 1][colInp];
                gameGrid[rowInp - 1][colInp] = gameGrid[rowInp][colInp];
                gameGrid[rowInp][colInp] = tempGrid[rowInp][colInp];
                break;
            case "D":
                tempGrid[rowInp][colInp] = gameGrid[rowInp][colInp+1];
                gameGrid[rowInp][colInp+1] = gameGrid[rowInp][colInp];
                gameGrid[rowInp][colInp] = tempGrid[rowInp][colInp];
                break;
            case "A":
                tempGrid[rowInp][colInp] = gameGrid[rowInp][colInp - 1];
                gameGrid[rowInp][colInp - 1] = gameGrid[rowInp][colInp];
                gameGrid[rowInp][colInp] = tempGrid[rowInp][colInp];
                break;
            case "S":
                tempGrid[rowInp][colInp] = gameGrid[rowInp+1][colInp];
                gameGrid[rowInp+1][colInp] = gameGrid[rowInp][colInp];
                gameGrid[rowInp][colInp] = tempGrid[rowInp][colInp];
                break;
        }
        turns++;
    }



    public void checkHorizontalMatch()//checks rows for horizontal match//
    {
        for(int i = 0; i < gameGrid.length; i++) //for loop for rows
        {
            for(int j = 0; j < gameGrid[0].length-3; j++)// for loop for columns
            {
                if (gameGrid[i][j] == gameGrid[i][j + 1] && gameGrid[i][j + 1] == gameGrid[i][j + 2])
                {
                    coordinates[0][0] = j;
                    coordinates[0][1] = i;
                    coordinates[1][0] = j - 1;
                    coordinates[1][1] = i;
                    coordinates[2][0] = j - 2;
                    coordinates[2][1] = i;
                    horizMatch= true;
                }
            }
        }
    }


    public void checkVerticalMatch()
    {
        for(int i = 0; i < gameGrid[0].length; i++)
        {
            for(int j = 0; j < gameGrid.length - 3; j++)
            {
                if(gameGrid[j][i] == gameGrid[j + 1][i] && gameGrid[j + 1][i] == gameGrid[j + 2][i] && gameGrid[j][i] == gameGrid[j + 2][i] )
                {
                    coordinates[0][0] = j;
                    coordinates[0][1] = i;
                    coordinates[1][0] = j - 1;
                    coordinates[1][1] = i;
                    coordinates[2][0] = j - 2;
                    coordinates[2][1] = i;
                    vertiMatch= true;
                }
            }
        }
    }


    public void replaceValues()
    {
        if(horizMatch)// && vertiMatch)
        {
            gameGrid[coordinates[0][0]][coordinates[0][1]] = rand.nextInt(range) + 1 ;
            gameGrid[coordinates[1][0]][coordinates[1][1]] = rand.nextInt(range) + 1 ;
            gameGrid[coordinates[2][0]][coordinates[2][1]] = rand.nextInt(range) + 1 ;
        }
    }



    public void newGridLevel(int levelNum, Player player)
    {
        player.setLevelPoints(0);
        System.out.println("New level! Give it a try!");
        for (int i = 0; i < gameGrid.length; i++)
        {
            for (int j = 0; j < gameGrid[0].length; j++)
            {
                gameGrid[i][j] =  rand.nextInt(range+levelNum)+1;
            }
        }
    }


    public void printGrid()
    {
        for (int i = 0; i < gameGrid.length; i++)
        {
            for (int j = 0; j < gameGrid[0].length; j++)
            {
                System.out.print("[ " + gameGrid[i][j] + " ]");
            }
            System.out.println();
        }
    }

    public void test()
    {


    }

    public void printStats(Player player)
    {
        System.out.println( "Level: " + level + "\tTurns taken this level: " + turns + "\tTotal Points: " + player.getTotalPoints());
    }
}
