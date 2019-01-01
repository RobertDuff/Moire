package moire.builders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface ModelLoader
{
	public ModelBuilder load ( String src );
	public ModelBuilder load ( File file ) throws IOException;
	public ModelBuilder load ( InputStream inputStream );
	public ModelBuilder load ( URL url ) throws IOException;
}
