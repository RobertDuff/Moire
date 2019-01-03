package moire.boundaries;

import javafx.beans.property.DoubleProperty;

public class RootBoundary extends Boundary
{
    public RootBoundary()
    {
        super();
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
