package moire.builders;

import moire.ModelData;
import moire.shapes.Shape;

public interface ShapeBuilder
{
	public Shape build ( ModelData modelData ) throws BuilderException;
}
