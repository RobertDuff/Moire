package moire.shapes;

import java.util.stream.Collectors;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import utility.geometry.Circle;
import utility.geometry.CircleBuilder;
import utility.geometry.Point;

public class OutscribedCircleShape extends ShapeBase
{
	@Override
	public Artist artist ()
	{
		final Color backgroundColor = backgroundColorProperty.get ();
		final Color color = colorProperty.get ();
		
		final Point[] points = pointProperties.stream ().map ( (p) -> p.get () ).collect ( Collectors.toList () ).toArray ( new Point[0] );

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
				Circle circle = null;
				
				switch ( points.length )
				{
					case 2:
						circle = CircleBuilder.outscribedCircle ( points[ 0 ], points[ 1 ] );
						break;
						
					case 3:
						circle = CircleBuilder.outscribedCircle ( points[ 0 ], points[ 1 ], points[ 2 ] );
						break;
				}
				
				double x = circle.center ().x () - circle.radius ();
				double y = circle.center ().y () - circle.radius ();
				double r = circle.radius () * 2;
				
				gc.strokeOval ( x, y, r, r );
			}
		};
	}
}
