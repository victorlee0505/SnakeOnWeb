package SnakeGame;

/**
 * Created by Jay C on 7/7/2017.
 */
public class Square
{
    //attributes
    private int x, y, side;

    public Square(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.side = 50;
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public int getSide(){return this.side;}

    @Override
    public String toString()
    {
        return "(" + this.x + " , " + this.y + ")";
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(this == null)
            return false;
        Square square = (Square)obj;

        if(this.getClass() == obj.getClass())
        {
            if(this.getX() == square.getX() && this.getY() == square.getY())
                return true;
        }
        return false;
    }



}
