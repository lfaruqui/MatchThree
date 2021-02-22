//Written by Matthew Myles & Hamed Faruqui

import java.util.Random;

public class Grid
{
    private int [][] grid;                                              //Private variable for the 2d integer array
    private int rowsN;                                                  //Private variable for the integer number of rows
    private int colsM;                                                  //Private variable for the integer number of columns
    private Random rand= new Random();                                  //Private variable for the random number generator


    public Grid(int rows, int cols)                                     //Filled constructor for grid, sets rows and columns
    {
        rowsN= rows;
        colsM= cols;
    }

    public Grid()                                                       //Non-filled constructor for grid
    {
        rowsN=0;
        colsM=0;
    }

    public int getRowsN()                                               //Method to return number of rows
    {
        return rowsN;
    }

    public int getColsM()                                              //Method to return number of columns
    {
        return colsM;
    }

    public void buildGrid()                                             //Method to fill the grid with random numbers
    {
        grid= new int [rowsN][colsM];                                   //Sets grid to be of user specified size
        for (int i = 0; i < grid.length; i++)                           //Nested for loop to step through the grid
        {
            for (int j = 0; j < grid[0].length; j++)
            {
                grid[i][j] = rand.nextInt(3)+1;                 //Sets each cell to a random number between 1 and 3, to start the game out with
            }
        }
    }

    public int[][] getGrid()                                           //Method to return the array created by buildGrid
    {
        return grid;
    }

}
