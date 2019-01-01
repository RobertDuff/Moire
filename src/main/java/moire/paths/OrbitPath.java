package moire.paths;

import moire.boundaries.Boundary;
import utility.geometry.Circle;
import utility.geometry.PointBuilder;


public class OrbitPath extends Path
{
	protected Path path;
	protected double radius;
	protected double phi;
	protected double theta;
	
	public OrbitPath ( Boundary boundary, Path path, double radius, double phi )
	{
		super ( boundary );

		this.path = path;
		this.radius = radius;
		this.phi = phi;
		theta = 0;
	}

	@Override
	public void advance ()
	{
		theta += phi;
		locationProperty.set ( PointBuilder.onCircle ( new Circle ( path.locationProperty ().get (), radius ), theta ) );
	}

}
