package moire.boundaries;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Boundary
{
	protected DoubleProperty leftBorderProperty = new SimpleDoubleProperty();
	protected DoubleProperty topBorderProperty = new SimpleDoubleProperty();
	protected DoubleProperty rightBorderProperty = new SimpleDoubleProperty();
	protected DoubleProperty bottomBorderProperty = new SimpleDoubleProperty();
	
	public DoubleProperty leftBorderProperty()
	{
		return leftBorderProperty;
	}
	
	public DoubleProperty topBorderProperty()
	{
		return topBorderProperty;
	}
	
	public DoubleProperty rightBorderProperty()
	{
		return rightBorderProperty;
	}
	
	public DoubleProperty bottomBorderProperty()
	{
		return bottomBorderProperty;
	}
	
	public double leftBorder()
	{
		return leftBorderProperty.get ();
	}
	
	public double topBorder()
	{
		return topBorderProperty.get ();
	}
	
	public double rightBorder()
	{
		return rightBorderProperty.get ();
	}
	
	public double bottomBorder()
	{
		return bottomBorderProperty.get ();
	}
	
	public double width()
	{
		return rightBorder() - leftBorder();
	}
	
	public double height()
	{
		return bottomBorder() - topBorder();
	}
	
	public double midX()
	{
		return ( leftBorder() + rightBorder() ) / 2;
	}
	
	public double midY()
	{
		return ( topBorder() + bottomBorder() ) / 2;
	}
	
	public boolean outOfBoundsLeft ( double x )
	{
		return x < leftBorder();
	}
	
	public boolean outOfBoundsTop ( double y )
	{
		return y < topBorder();
	}
	
	public boolean outOfBoundsRight ( double x )
	{
		return x > rightBorder();
	}
	
	public boolean outOfBoundsBottom ( double y )
	{
		return y > bottomBorder();
	}
}
