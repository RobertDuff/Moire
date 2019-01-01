package moire.shapes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TargetShape extends ShapeBase
{
	protected static final double DEFAULT_LINE_LENGTH   = 10;
	protected static final double DEFAULT_CIRCLE_RADIUS = 15;
	
	protected DoubleProperty lineLengthProperty = new SimpleDoubleProperty ( DEFAULT_LINE_LENGTH );
	protected DoubleProperty circleRadiusProperty = new SimpleDoubleProperty ( DEFAULT_CIRCLE_RADIUS );
	
	public DoubleProperty lineLengthProperty ()
	{
		return lineLengthProperty;
	}
	
	public DoubleProperty circleRadiusProperty ()
	{
		return circleRadiusProperty;
	}

	@Override
	public Artist artist ()
	{
		final Color backgroundColor = backgroundColorProperty.get ();
		final Color color = colorProperty.get ();
		
		// Line parameters
		
		final double x = pointProperties.get ( 0 ).get ().x ();
		final double l = x - lineLengthProperty.get ();
		final double r = x + lineLengthProperty.get ();
		
		final double y = pointProperties.get ( 0 ).get ().y ();
		final double t = y - lineLengthProperty.get ();
		final double b = y + lineLengthProperty.get ();
		
		// Circle Parameters
		
		final double p = x - circleRadiusProperty.get ();
		final double q = y - circleRadiusProperty.get ();
		final double w = 2 * circleRadiusProperty.get ();
		final double h = 2 * circleRadiusProperty.get ();

		return new Artist()
		{
			@Override
			public void erase ( GraphicsContext gc )
			{
				gc.setLineWidth ( 2.0 );
				gc.setStroke ( backgroundColor );
				render ( gc );
			}
			
			@Override
			public void draw ( GraphicsContext gc )
			{				
				gc.setLineWidth ( 1.0 );
				gc.setStroke ( color );
				render ( gc );
			}
			
			private void render ( GraphicsContext gc )
			{
				gc.strokeLine ( x, t, x, b );
				gc.strokeLine ( l, y, r, y );
				
				gc.strokeOval ( p, q, w, h );
			}
		};
	}

}
