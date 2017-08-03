package SnakeGame;

import java.awt.*;

public class Square
{
    private int x, y, size;

    public Square(int size)
    {
        this.x = 0;
        this.y = 0;
        this.size = size;
    }
    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    private int correctPosition(int argument, int nearTo)
    {
        return (argument / nearTo) * nearTo;
    }

    public void setX(int x)
    {
        this.x = correctPosition(x, size);
    }

    public void setY(int y)
    {
        this.y = correctPosition(y, size);
    }

    public void setPosition(int x, int y) {
        this.x = correctPosition(x, size);
        this.y = correctPosition(y, size);
    }

    public void move(int dx, int dy)
    {
        x += dx;
        x = correctPosition(x, size);
        y += dy;
        y = correctPosition(y, size);
    }

    public Rectangle getBound()
    {
        return new Rectangle(x, y, size, size);
    }

    public boolean isCollsion(Square o)
    {
        if(o == this)
        {
            return false;
        }
        if(o == null)
            return false;
        return getBound().intersects(o.getBound());
    }

    @Override
    /**
     *
     */
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

    public void render(Graphics2D graphics)
    {
        graphics.fillRect(x + 1, y +1, size - 2, size - 2);
    }


    @Override
    public String toString()
    {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }
}
