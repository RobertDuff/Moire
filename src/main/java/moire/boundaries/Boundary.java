package moire.boundaries;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Boundary
{
    protected DoubleProperty leftProperty   = new SimpleDoubleProperty ( 0.0 );
    protected DoubleProperty topProperty    = new SimpleDoubleProperty ( 0.0 );
    protected DoubleProperty rightProperty  = new SimpleDoubleProperty ( 0.0 );
    protected DoubleProperty bottomProperty = new SimpleDoubleProperty ( 0.0 );
    protected DoubleProperty widthProperty  = new SimpleDoubleProperty ();
    protected DoubleProperty heightProperty = new SimpleDoubleProperty ();
    
    public DoubleProperty leftProperty()
    {
        return leftProperty;
    }

    public DoubleProperty topProperty()
    {
        return topProperty;
    }

    public DoubleProperty rightProperty()
    {
        return rightProperty;
    }

    public DoubleProperty bottomProperty()
    {
        return bottomProperty;
    }

    public DoubleProperty widthProperty()
    {
        return widthProperty;
    }

    public DoubleProperty heightProperty()
    {
        return heightProperty;
    }

    public double left()
    {
        return leftProperty.get ();
    }

    public double top()
    {
        return topProperty.get ();
    }

    public double right()
    {
        return rightProperty.get ();
    }

    public double bottom()
    {
        return bottomProperty.get ();
    }

    public double width()
    {
        return widthProperty.get ();
    }

    public double height()
    {
        return heightProperty.get ();
    }

    public double midX()
    {
        return ( left() + right() ) / 2;
    }

    public double midY()
    {
        return ( top() + bottom() ) / 2;
    }

    public boolean outOfBoundsLeft ( double x )
    {
        return x < left();
    }

    public boolean outOfBoundsTop ( double y )
    {
        return y < top();
    }

    public boolean outOfBoundsRight ( double x )
    {
        return x > right();
    }

    public boolean outOfBoundsBottom ( double y )
    {
        return y > bottom();
    }
}
