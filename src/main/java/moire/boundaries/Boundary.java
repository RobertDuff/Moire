package moire.boundaries;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Boundary
{
    protected DoubleProperty leftProperty   = new SimpleDoubleProperty ( 0.0 );
    protected DoubleProperty topProperty    = new SimpleDoubleProperty ( 0.0 );
    protected DoubleProperty rightProperty  = new SimpleDoubleProperty ( 0.0 );
    protected DoubleProperty bottomProperty = new SimpleDoubleProperty ( 0.0 );
    protected DoubleProperty widthProperty  = new SimpleDoubleProperty ();
    protected DoubleProperty heightProperty = new SimpleDoubleProperty ();

    // For Use by Sub Classes
    protected Boundary()
    {
        widthProperty.bind ( Bindings.createDoubleBinding ( () -> right() - left(), leftProperty, rightProperty ) );
        heightProperty.bind ( Bindings.createDoubleBinding ( () -> bottom() - top(), topProperty, bottomProperty ) );
    }

    public Boundary ( Boundary parent, Value left, Value top, Value right, Value bottom, Value width, Value height )
    {
        this();

        if ( parent != null )
        {        
            leftProperty.bind ( Bindings.createDoubleBinding ( () -> calcBorder ( parent.left(), left, parent.width () ),
                    parent.leftProperty (), parent.rightProperty() ) );

            topProperty.bind ( Bindings.createDoubleBinding ( () -> calcBorder ( parent.top (), top, parent.height () ),
                    parent.topProperty (), parent.bottomProperty () ) );

            if ( width != null )
                rightProperty.bind ( Bindings.createDoubleBinding ( () -> calcBorder ( left (), width, parent.width () ),
                        parent.leftProperty (), parent.rightProperty () ) );
            else
                rightProperty.bind ( Bindings.createDoubleBinding ( () -> calcBorder ( parent.right (), right.negate (), parent.width () ), 
                        parent.leftProperty (), parent.rightProperty () ) );

            if ( height != null )
                bottomProperty.bind ( Bindings.createDoubleBinding ( () -> calcBorder ( top (), height, parent.height () ),
                        parent.topProperty (), parent.bottomProperty () ) );
            else
                bottomProperty.bind ( Bindings.createDoubleBinding ( () -> calcBorder ( parent.bottom (), bottom.negate (), parent.height () ), 
                        parent.topProperty (), parent.bottomProperty () ) );
        }
    }

    private double calcBorder ( double base, Value offset, double scale )
    {
        if ( offset == null )
            return base;

        return base + offset.value * ( offset.isProportional? scale : 1.0 );
    }
    
    public ReadOnlyDoubleProperty leftProperty()
    {
        return leftProperty;
    }

    public ReadOnlyDoubleProperty topProperty()
    {
        return topProperty;
    }

    public ReadOnlyDoubleProperty rightProperty()
    {
        return rightProperty;
    }

    public ReadOnlyDoubleProperty bottomProperty()
    {
        return bottomProperty;
    }

    public ReadOnlyDoubleProperty widthProperty()
    {
        return widthProperty;
    }

    public ReadOnlyDoubleProperty heightProperty()
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
