package moire.boundaries;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class SimpleBoundary extends Boundary
{
	protected DoubleProperty widthProperty = new SimpleDoubleProperty ();
	protected DoubleProperty heightProperty = new SimpleDoubleProperty ();

	public SimpleBoundary()
	{
		leftBorderProperty.set ( 0 );
		topBorderProperty.set ( 0 );
		
		rightBorderProperty.bind ( widthProperty );
		bottomBorderProperty.bind ( heightProperty );
	}
	
	public DoubleProperty widthProperty()
	{
		return widthProperty;
	}
	
	public DoubleProperty heightProperty()
	{
		return heightProperty;
	}
}
