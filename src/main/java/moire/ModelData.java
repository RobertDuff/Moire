package moire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moire.boundaries.Boundary;
import moire.boundaries.SimpleBoundary;
import moire.colors.ColorSequence;
import moire.paths.Path;
import moire.shapes.Shape;

public class ModelData
{
	public SimpleBoundary topBoundary = new SimpleBoundary();
	
	public Map<String,Boundary> namedBoundaries = new HashMap<>();
	
	public Map<String,ColorSequence> namedColorSequences = new HashMap<>();
	public List<ColorSequence> allColorSequences = new ArrayList<>();
	
	public Map<String,Path> namedPaths = new HashMap<>();
	public List<Path> allPaths = new ArrayList<>();
	
	public List<Shape> allShapes = new ArrayList<>();
	
	public Map<String,String> variables = new HashMap<>();
	
	//
	// Boundaries
	//
	
	public void addBoundary ( Boundary boundary, String name )
	{
		if ( name != null && !name.isEmpty () )
			namedBoundaries.put ( name, boundary );
	}
	
	public Boundary boundary ( String name )
	{
		return namedBoundaries.get ( name );
	}
	
	public Boundary topBoundary()
	{
		return topBoundary;
	}
	
	//
	// ColorSequences
	//
	
	public void addColorSequnce ( ColorSequence colorSequence )
	{
		addColorSequnce ( colorSequence, null );
	}
	
	public void addColorSequnce ( ColorSequence colorSequence, String name )
	{
		allColorSequences.add ( colorSequence );
		
		if ( name != null && !name.isEmpty () )
			namedColorSequences.put ( name, colorSequence );
	}
	
	public ColorSequence colorSequence ( String name )
	{
		return namedColorSequences.get ( name );
	}
	
	//
	// Paths
	//
	
	public void addPath ( Path path )
	{
		addPath ( path, null );
	}
	
	public void addPath ( Path path, String name )
	{
		allPaths.add ( path );
		
		if ( name != null && !name.isEmpty () )
			namedPaths.put ( name, path );
	}
	
	public Path path ( String name )
	{
		return namedPaths.get ( name );
	}
	
	//
	// Shapes
	//
	
	public void addShape ( Shape shape )
	{
		allShapes.add ( shape );
	}
	
	@Override
	public String toString ()
	{
		return "ModelData [namedBoundaries=" + namedBoundaries
				+ ", namedColorSequences=" + namedColorSequences + ", allColorSequences=" + allColorSequences + ", namedPaths="
				+ namedPaths + ", allPaths=" + allPaths + ", allShapes=" + allShapes + "]";
	}
}
