package moire.paths;

import moire.boundaries.Boundary;
import utility.geometry.Ellipse;
import utility.geometry.PointBuilder;


public class OvalPath extends Path
{
	protected double phi;
	protected double theta;
	
	public OvalPath ( Boundary boundary, double phi )
	{
		super ( boundary );
		
		this.phi = phi;
		this.theta = 0;
		
		update();
	}

	@Override
	public void advance ()
	{
		theta += phi;
		update();
	}
	
	private void update()
	{
		locationProperty.setValue ( PointBuilder.onEllipse ( new Ellipse ( boundary.midX (), boundary.midY (), boundary.width () / 2, boundary.height () / 2 ), theta ) );
	}
}
