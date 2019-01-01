package moire.colors;

import javafx.scene.paint.Color;

public class SingleColorSequence extends ColorSequence
{
	public SingleColorSequence ( Color color )
	{
		colorProperty.setValue ( color );
	}

	@Override
	public void advance ()
	{
		// Do Nothing!  We ain't changin' for nut'in'!!
	}
}
