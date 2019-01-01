package moire.builders;

import moire.ModelData;
import moire.colors.ColorSequence;

public interface ColorSequenceBuilder
{
	public ColorSequence build ( ModelData modelData ) throws BuilderException;
}
