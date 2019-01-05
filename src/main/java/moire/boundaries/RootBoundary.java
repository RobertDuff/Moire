package moire.boundaries;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;

public class RootBoundary extends Boundary
{
    public RootBoundary()
    {
        leftProperty.set ( 0.0 );
        topProperty.set ( 0.0 );
        
        widthProperty.bind ( Bindings.createDoubleBinding ( () -> rightProperty.doubleValue () - leftProperty.doubleValue (), leftProperty, rightProperty ) );
        heightProperty.bind ( Bindings.createDoubleBinding ( () -> bottomProperty.doubleValue () - topProperty.doubleValue (), topProperty, bottomProperty ) );
    }
    
    public RootBoundary bindWidth ( DoubleProperty prop )
    {
        rightProperty.bind ( prop );
        return this;
    }
    
    public RootBoundary bindHeight ( DoubleProperty prop )
    {
        bottomProperty.bind ( prop );
        
        return this;
    }
}
