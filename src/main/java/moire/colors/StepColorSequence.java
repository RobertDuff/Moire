package moire.colors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterators;

import javafx.scene.paint.Color;

public class StepColorSequence extends ColorSequence
{
	protected class Span
	{
		public Span ( Color color, int length )
		{
			this.color = color;
			this.length = length;
		}
		
		protected Color color;
		protected int length;
	}
	
	protected List<Span> spans = new ArrayList<>();
	protected Iterator<Span> iterator = Iterators.cycle ( spans );
	protected int count = 0;
	
	public void addSpan ( Color color, int length )
	{
		spans.add ( new Span ( color, length ) );
	}
	
	@Override
	public void advance ()
	{
		if ( count > 0 )
			count--;
		else
		{
			Span span = iterator.next ();
			colorProperty.setValue ( span.color );
			count = span.length;
		}
	}

}
