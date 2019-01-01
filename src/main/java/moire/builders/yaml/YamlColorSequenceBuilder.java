package moire.builders.yaml;

import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import moire.ModelData;
import moire.builders.BuilderException;
import moire.builders.ColorSequenceBuilder;
import moire.colors.ColorSequence;
import moire.colors.SingleColorSequence;
import moire.colors.SmoothColorSequence;
import moire.colors.StepColorSequence;


public class YamlColorSequenceBuilder implements ColorSequenceBuilder
{
	protected Map<String,Object> params;
	
	@SuppressWarnings ( "unchecked" )
	public YamlColorSequenceBuilder ( Object spec )
	{
		params = ( Map<String,Object> ) spec;
	}

	@Override
	public ColorSequence build ( ModelData modelData ) throws BuilderException
	{
		ColorSequence colorSequence = null;
		
		String name = null;
		
		if ( params.containsKey ( "Name" ) )
			name = params.get ( "Name" ).toString ();
		
		if ( params.containsKey ( "Color" ) )
		{
			colorSequence = modelData.colorSequence ( params.get ( "Color" ).toString () );
			
			if ( colorSequence == null )
				colorSequence  = new SingleColorSequence ( Color.web ( params.get ( "Color" ).toString () ) );
		}
		else
		{
			String type = ( String ) params.get ( "Type" );

			if ( type.equals ( "Single" ) )
			{
				Color color = Color.web ( params.get ( "Color" ).toString () );
				colorSequence = new SingleColorSequence ( color );
			}
			else if ( type.equals ( "Step" ) )
			{
				@SuppressWarnings ( "unchecked" )
				List<Map<String,Object>> spans = ( List<Map<String,Object>> ) params.get ( "Spans" );

				StepColorSequence sc = new StepColorSequence ();
				colorSequence = sc;

				for ( Map<String,Object> span : spans )
					sc.addSpan ( Color.web ( span.get ( "Color" ).toString () ), ( ( Number ) span.get ( "Steps" ) ).intValue () );
			}
			else if ( type.equals ( "Smooth" ) )
			{
				@SuppressWarnings ( "unchecked" )
				List<Map<String,Object>> spans = ( List<Map<String,Object>> ) params.get ( "Spans" );

				SmoothColorSequence sc = new SmoothColorSequence ();
				colorSequence = sc;

				for ( Map<String,Object> span : spans )
				{
					Color start = Color.web ( span.get ( "Start" ).toString () );
					Color end = Color.web ( span.get ( "End" ).toString () );
					int steps = Integer.valueOf ( span.get ( "Steps" ).toString () );

					sc.addSpan ( start, end, steps );
				}
			}
			else
				throw new BuilderException ( "Unknown Color Sequence Type: " + type );
		}
		
		modelData.addColorSequnce ( colorSequence, name );
		return colorSequence;
	}
}
