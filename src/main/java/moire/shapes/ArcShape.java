package moire.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class ArcShape extends ShapeBase
{
	protected double phi;
	protected double length;
	protected ArcType type;
	protected double theta;
	
	public ArcShape ( double phi, double length, ArcType type )
	{
		this.phi = phi;
		this.length = length;
		this.type = type;
		theta = 0;
	}

	@Override
	public Artist artist ()
	{
		theta += phi;
		
		final Color backgroundColor = backgroundColorProperty.get ();
		final Color color = colorProperty.get ();
		
		final double x = Math.min ( pointProperties.get ( 0 ).get ().x (), pointProperties.get ( 1 ).get ().x () );
		final double y = Math.min ( pointProperties.get ( 0 ).get ().y (), pointProperties.get ( 1 ).get ().y () );

		final double w = Math.abs ( pointProperties.get ( 0 ).get ().x () - pointProperties.get ( 1 ).get ().x () );
		final double h = Math.abs ( pointProperties.get ( 0 ).get ().y () - pointProperties.get ( 1 ).get ().y () );
		
		final double angle = theta;
		
		return new Artist()
		{
			@Override
			public void erase ( GraphicsContext gc )
			{
				System.out.println ( "Erasing: " + backgroundColor );
				
				gc.setLineWidth ( 2.0 );
				gc.setStroke ( backgroundColor );
				render ( gc );
			}
			
			@Override
			public void draw ( GraphicsContext gc )
			{
				System.out.println ( "Drawing: " + color );
				gc.setLineWidth ( 1.0 );
				gc.setStroke ( color );
				render ( gc );
			}
			
			private void render ( GraphicsContext gc )
			{
				gc.strokeArc ( x, y, w, h, angle, length, type );
			}
		};
	}
}
