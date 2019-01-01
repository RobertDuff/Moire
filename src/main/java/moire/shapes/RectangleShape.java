package moire.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectangleShape extends ShapeBase
{
	@Override
	public Artist artist ()
	{
		final Color backgroundColor = backgroundColorProperty.get ();
		final Color color = colorProperty.get ();
		
		final double x = Math.min ( pointProperties.get ( 0 ).get ().x (), pointProperties.get ( 1 ).get ().x () );
		final double y = Math.min ( pointProperties.get ( 0 ).get ().y (), pointProperties.get ( 1 ).get ().y () );

		final double w = Math.abs ( pointProperties.get ( 0 ).get ().x () - pointProperties.get ( 1 ).get ().x () );
		final double h = Math.abs ( pointProperties.get ( 0 ).get ().y () - pointProperties.get ( 1 ).get ().y () );
		
		return new Artist()
		{
			@Override
			public void erase ( GraphicsContext gc )
			{
				gc.setLineWidth ( 2.0 );
				gc.setStroke ( backgroundColor );
				gc.strokeRect ( x, y, w, h );				
			}
			
			@Override
			public void draw ( GraphicsContext gc )
			{
				gc.setLineWidth ( 1.0 );
				gc.setStroke ( color );
				gc.strokeRect ( x, y, w, h );				
			}
		};
	}
}
