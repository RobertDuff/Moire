package moire.builders;

import moire.ModelData;
import moire.paths.Path;

public interface PathBuilder
{
	public Path build ( ModelData modelData ) throws BuilderException;
}
