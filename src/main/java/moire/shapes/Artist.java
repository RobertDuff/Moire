package moire.shapes;

import javafx.scene.canvas.GraphicsContext;

public interface Artist
{
	public void draw ( GraphicsContext gc );
	public void erase ( GraphicsContext gc );
}
