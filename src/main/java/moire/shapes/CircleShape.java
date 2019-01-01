package moire.shapes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleShape extends ShapeBase
{
	protected DoubleProperty radiusProperty;

	public DoubleProperty radiusProperty()
	{
		if ( radiusProperty == null )
			radiusProperty = new SimpleDoubleProperty ();
		
		return radiusProperty;
	}
	@Override
	public Artist artist ()
	{
		final Color backgroundColor = backgroundColorProperty.get ();
		final Color color = colorProperty.get ();
		
		double r = 0;
		
		if ( radiusProperty != null )
			r = radiusProperty.get ();
		else
			r = pointProperties.get ( 0 ).get ().distanceTo ( pointProperties.get ( 1 ).get () );
		
		final double x = pointProperties.get ( 0 ).get ().x () - r;
		final double y = pointProperties.get ( 0 ).get ().y () - r;
		final double d = r*2;
		
		return new Artist()
		{
			@Override
			public void erase ( GraphicsContext gc )
			{
				gc.setLineWidth ( 2.0 );
				gc.setStroke ( backgroundColor );
				gc.strokeOval ( x, y, d, d );
			}
			
			@Override
			public void draw ( GraphicsContext gc )
			{
				gc.setLineWidth ( 1.0 );
				gc.setStroke ( color );
				gc.strokeOval ( x, y, d, d );
			}
		};
	}

}
