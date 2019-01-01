package moire.paths;

import java.util.Random;

import moire.boundaries.Boundary;


public class RandomBouncingPath extends BouncingPath
{
	protected static Random random = new Random();
	
	protected double xMaxDelta;
	protected double yMaxDelta;
	
	public RandomBouncingPath ( Boundary boundary, double xMaxDelta, double yMaxDelta )
	{
		super ( boundary );
	
		this.xMaxDelta = Math.abs ( xMaxDelta );
		this.yMaxDelta = Math.abs ( yMaxDelta );
		
		nextXDelta ( xMaxDelta );
		nextYDelta ( yMaxDelta );
	}
	
	@Override
	protected void updateXDelta ()
	{
		nextXDelta();
	}

	@Override
	protected void updateYDelta ()
	{
		nextYDelta();
	}

	private void nextXDelta ()
	{
		nextXDelta ( -xDelta );
	}
	
	private void nextXDelta ( double sign )
	{
		xDelta = Math.copySign ( ( xMaxDelta-1 ) * random.nextDouble () + 1, sign );
	}
	
	private void nextYDelta ()
	{
		nextYDelta ( -yDelta );
	}
	
	private void nextYDelta ( double sign )
	{
		yDelta = Math.copySign ( ( yMaxDelta-1 ) * random.nextDouble () + 1, sign );		
	}
}
