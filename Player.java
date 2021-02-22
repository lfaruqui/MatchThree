//Written by Matthew Myles & Hamed Faruqui


public class Player
{
    private int levelPoints;                                            //Private variable for points needed for next level
    private int totalPoints;                                            //Private variable for total points

    public Player()                                                     //Constructor for player
    {
        levelPoints= 0;
        totalPoints= 0;
    }

    public void setLevelPoints(int numPoints)                       //Method to set level points to new value
    {
        levelPoints+= numPoints;
    }

    public int getLevelPoints()                                     //Method to return level points
    {
        return levelPoints;
    }

    public void setTotalPoints(int numPoints)                       //Method to set total points
    {
        totalPoints+= numPoints;
    }

    public int getTotalPoints()                                     //Method to return total points
    {
        return totalPoints;
    }
}
