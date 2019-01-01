package moire.builders.yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.yaml.snakeyaml.Yaml;

import moire.builders.ModelBuilder;
import moire.builders.ModelLoader;


public class YamlModelLoader implements ModelLoader
{	
	@Override
	public ModelBuilder load ( String yamlString )
	{
		return new YamlModelBuilder ( new Yaml().load ( yamlString ) );
	}
	
	@Override
	public ModelBuilder load ( File yamlFile ) throws IOException
	{
		InputStream is = new FileInputStream ( yamlFile );
		Object yaml = read ( is );
		is.close ();
		
		return new YamlModelBuilder ( yaml );
	}
	
	@Override
	public ModelBuilder load ( InputStream yamlStream )
	{
		return new YamlModelBuilder ( read ( yamlStream ) );
	}
	
	@Override
	public ModelBuilder load ( URL url ) throws IOException
	{
		InputStream is = url.openStream ();
		Object yaml = read ( is );
		is.close ();
		
		return new YamlModelBuilder ( yaml );
	}
	
	private Object read ( InputStream yamlStream )
	{
		return new Yaml().load ( yamlStream );
	}
}
