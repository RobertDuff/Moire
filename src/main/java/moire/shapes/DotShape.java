package moire.shapes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DotShape extends ShapeBase
{
    protected static final double DEFAULT_RADIUS = 3;

    protected DoubleProperty radiusProperty = new SimpleDoubleProperty ( DEFAULT_RADIUS );
    
    public DoubleProperty radiusProperty()
    {
        return radiusProperty;
    }
    
    @Override
    public Artist artist ()
    {
        final Color backgroundColor = backgroundColorProperty.get ();
        final Color color = colorProperty.get ();
        
        final double x = pointProperties.get ( 0 ).get ().x ();
        final double y = pointProperties.get ( 0 ).get ().y ();
        
        final double p = x - radiusProperty.get ();
        final double q = y - radiusProperty.get ();
        final double w = 2 * radiusProperty.get ();
        final double h = 2 * radiusProperty.get ();

        return new Artist()
        {
            @Override
            public void erase ( GraphicsContext gc )
            {
                gc.setLineWidth ( 2.0 );
                gc.setStroke ( backgroundColor );
                gc.setFill ( backgroundColor );
                
                render ( gc );
            }
            
            @Override
            public void draw ( GraphicsContext gc )
            {               
                gc.setLineWidth ( 1.0 );
                gc.setStroke ( color );
                gc.setFill ( color );
                
                render ( gc );
            }
            
            private void render ( GraphicsContext gc )
            {
                gc.fillOval ( p, q, w, h );
                gc.strokeOval ( p, q, w, h );
            }
        };

    }
}
