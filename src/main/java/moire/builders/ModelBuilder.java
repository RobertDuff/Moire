package moire.builders;

import moire.Model;

public interface ModelBuilder
{
	public Model build() throws BuilderException;
}
