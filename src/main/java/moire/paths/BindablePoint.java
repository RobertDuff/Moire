package moire.paths;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import utility.geometry.Point;

public class BindablePoint extends Point
{ 
    protected DoubleProperty xProperty = new SimpleDoubleProperty ( 0.0 );
    protected DoubleProperty yProperty = new SimpleDoubleProperty ( 0.0 );
    
    public BindablePoint ()
    {
        super ( 0.0, 0.0 );
        
        xProperty.addListener ( ( a, o, n ) -> x = n.doubleValue () );
        yProperty.addListener ( ( a, o, n ) -> y = n.doubleValue () );
    }
    
    public DoubleProperty xProperty()
    {
        return xProperty;
    }
    
    public DoubleProperty yProperty()
    {
        return yProperty;
    }
}
