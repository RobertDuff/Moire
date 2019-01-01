package moire.shapes;

import java.util.stream.Collectors;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CurveShape extends ShapeBase
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
				render ( gc, x, y );
			}
			
			@Override
			public void draw ( GraphicsContext gc )
			{
				gc.setLineWidth ( 1.0 );
				gc.setStroke ( color );
				render ( gc, x, y );
			}
			
			private void render ( GraphicsContext gc, Double[] x, Double[] y )
			{
				gc.beginPath ();
				gc.moveTo ( x[0], y[0] );
				gc.bezierCurveTo ( x[1], y[1], x[2], y[2], x[3], y[3] );
				gc.stroke ();
			}
		};
	}

}
