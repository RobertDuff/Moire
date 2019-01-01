package moire.colors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterators;

import javafx.scene.paint.Color;

public class SmoothColorSequence extends ColorSequence
{
	protected class Span
	{
		protected Color start;
		protected Color end;
		protected int steps;
		
		public Span ( Color start, Color end, int steps )
		{
			this.start = start;
			this.end = end;
			this.steps = steps;
		}
	}
	
	protected List<Span> spans = new ArrayList<>();
	protected Iterator<Span> iterator = Iterators.cycle ( spans );
	protected Span currentSpan;
	protected int count = 0;
	
	public void addSpan ( Color start, Color end, int steps )
	{
		spans.add ( new Span ( start, end, steps ) );
	}
	
	@Override
	public void advance ()
	{
		if ( currentSpan == null )
		{
			currentSpan = iterator.next ();
			count = 0;
		}
		
		if ( count < currentSpan.steps )
		{
			colorProperty.setValue ( currentSpan.start.interpolate ( currentSpan.end, Integer.valueOf ( count ).doubleValue () / Integer.valueOf ( currentSpan.steps ).doubleValue () ) );
			count++;
		}
		else
		{
			colorProperty.setValue ( currentSpan.end );
			currentSpan = iterator.next ();
			count = 0;
		}
	}

}
