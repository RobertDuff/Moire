package moire.shapes;

import java.util.stream.Collectors;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineShape extends ShapeBase
{
	@Override
	public Artist artist ()
	{
		final Color backgroundColor = backgroundColorProperty.get ();
		final Color color = colorProperty.get ();
		
		final Double[] x = pointProperties.stream ().map ( (p) -> p.get ().x () ).collect ( Collectors.toList () ).toArray ( new Double[0] );
		final Double[] y = pointProperties.stream ().map ( (p) -> p.get ().y () ).collect ( Collectors.toList () ).toArray ( new Double[0] );		

		return new Artist()
		{
			@Override
			public void erase ( GraphicsContext gc )
			{
				gc.setLineWidth ( 2.0 );
				gc.setStroke ( backgroundColor );				
				gc.strokeLine ( x[0], y[0], x[1], y[1] );
			}
			
			@Override
			public void draw ( GraphicsContext gc )
			{
				gc.setLineWidth ( 1.0 );
				gc.setStroke ( color );
				gc.strokeLine ( x[0], y[0], x[1], y[1] );
			}
		};
	}

	@Override
	public String toString ()
	{
		return "LineShape []";
	}
}
