package moire.builders;

import moire.ModelData;
import moire.boundaries.Boundary;

public interface BoundaryBuilder
{
	public Boundary build ( ModelData modelData ) throws BuilderException;
}
