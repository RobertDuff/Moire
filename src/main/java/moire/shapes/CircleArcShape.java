package moire.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class CircleArcShape extends ShapeBase
{
	protected double radius;
	protected double phi;
	protected double length;
	protected ArcType type;
	protected double theta;
	
	public CircleArcShape ( double phi, double length, ArcType type )
	{
		this ( Double.NaN, phi, length, type );
	}
	
	public CircleArcShape ( double radius, double phi, double length, ArcType type )
	{
		this.radius = radius;
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
		
		double r = 0;
		
		if ( pointProperties.size () == 1 )
			r = radius;
		else
			r = pointProperties.get ( 0 ).get ().distanceTo ( pointProperties.get ( 1 ).get () );
		
		final double x = pointProperties.get ( 0 ).get ().x () - r;
		final double y = pointProperties.get ( 0 ).get ().y () - r;
		final double d = r*2;
		
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
				gc.strokeArc ( x, y, d, d, angle, length, type );
			}
		};
	}
}
