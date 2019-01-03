package moire.paths;

import moire.boundaries.Boundary;
import utility.geometry.Point;


public class BouncingPath extends Path
{
	protected double xDelta;
	protected double yDelta;
	
	public BouncingPath ( Boundary boundary )
	{
		this ( boundary, 0, 0 );
	}

	public BouncingPath ( Boundary boundary, double xDelta, double yDelta )
	{
		super ( boundary );
			
		locationProperty.set ( new Point ( boundary.midX (), boundary.midY() ) );
		
		this.xDelta = xDelta;
		this.yDelta = yDelta;
	}

	@Override
	public void advance ()
	{
		double x = locationProperty.get ().x () + xDelta;
		double y = locationProperty.get ().y () + yDelta;
		
		if ( boundary.outOfBoundsLeft ( x ) )
		{	
			x = boundary.left ();
			updateXDelta ();
		}
		else if ( boundary.outOfBoundsRight ( x ) )
		{
			x = boundary.right ();
			updateXDelta ();
		}
		
		if ( boundary.outOfBoundsTop ( y ) )
		{
			y = boundary.top ();
			updateYDelta ();
		}
		else if ( boundary.outOfBoundsBottom ( y ) )
		{
			y = boundary.bottom ();
			updateYDelta ();
		}
		
		locationProperty.setValue ( new Point ( x, y ) );
	}
	
	protected void updateXDelta()
	{
		xDelta = -xDelta;
	}
	
	protected void updateYDelta()
	{
		yDelta = -yDelta;
	}
}
