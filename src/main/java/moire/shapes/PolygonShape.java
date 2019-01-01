package moire.shapes;

import java.util.stream.Collectors;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.arrays.ArrayConverter;

public class PolygonShape extends ShapeBase
{
	@Override
	public Artist artist ()
	{
		final Color backgroundColor = backgroundColorProperty.get ();
		final Color color = colorProperty.get ();
		
		final double[] x = ArrayConverter.doubleArray ( pointProperties.stream ().map ( (p) -> p.get ().x () ).collect ( Collectors.toList () ).toArray ( new Double[0] ) );
		final double[] y = ArrayConverter.doubleArray ( pointProperties.stream ().map ( (p) -> p.get ().y () ).collect ( Collectors.toList () ).toArray ( new Double[0] ) );		
		
		return new Artist()
		{
			@Override
			public void erase ( GraphicsContext gc )
			{	
				gc.setLineWidth ( 2.0 );
				gc.setStroke ( backgroundColor );
				gc.strokePolyline ( x, y, x.length );
			}
			
			@Override
			public void draw ( GraphicsContext gc )
			{
				gc.setLineWidth ( 1.0 );
				gc.setStroke ( color );
				gc.strokePolyline ( x, y, x.length );
			}
		};
	}
}
