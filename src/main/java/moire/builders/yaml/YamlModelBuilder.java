package moire.builders.yaml;

import java.util.List;
import java.util.Map;

import moire.Model;
import moire.ModelData;
import moire.boundaries.RootBoundary;
import moire.builders.BuilderException;
import moire.builders.ModelBuilder;


public class YamlModelBuilder implements ModelBuilder
{
	protected Map<String,Object> params;
	
	@SuppressWarnings ( "unchecked" )
	public YamlModelBuilder ( Object top )
	{
		params = ( Map<String,Object> ) top;
	}

	@Override
	public Model build() throws BuilderException
	{
		ModelData modelData = new ModelData();
		
		try
		{

			//
			// Boundaries
			//

			modelData.addBoundary ( "__Top", new RootBoundary () );
			
			if ( params.containsKey ( "Boundaries" ) )
			{
				@SuppressWarnings ( "unchecked" )
				List<Object> bSpecs = ( List<Object> ) params.get ( "Boundaries" );

				for ( Object bSpec  : bSpecs )
					new YamlBoundaryBuilder ( bSpec ).build ( modelData );
			}

			//
			// ColorSequences
			//

			if ( params.containsKey ( "Colors" ) )
			{
				@SuppressWarnings ( "unchecked" )
				List<Object> cSpecs = ( List<Object> ) params.get ( "Colors" );

				for ( Object cSpec  : cSpecs )
					new YamlColorSequenceBuilder ( cSpec ).build ( modelData );
			}

			//
			// Paths
			//

			if ( params.containsKey ( "Paths" ) )
			{
				@SuppressWarnings ( "unchecked" )
				List<Object> pSpecs = ( List<Object> ) params.get ( "Paths" );

				for ( Object pSpec  : pSpecs )
					new YamlPathBuilder ( pSpec ).build ( modelData );
			}

			//
			// Shapes
			//

			if ( params.containsKey ( "Shape" ) )
				new YamlShapeBuilder ( params.get ( "Shape" ) ).build ( modelData );
			else
			{
				@SuppressWarnings ( "unchecked" )
				List<Object> sSpecs = ( List<Object> ) params.get ( "Shapes" );

				for ( Object sSpec  : sSpecs )
					new YamlShapeBuilder ( sSpec ).build ( modelData );
			}
		}
		catch ( BuilderException e )
		{
			throw e;
		}
		catch ( Exception e )
		{
			throw new BuilderException ( "Model Build Failed", e );
		}
		
		return new Model ( modelData );
	}
}
