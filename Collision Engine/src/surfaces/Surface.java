package surfaces;

import models.RawModel;
import renderEngine.Loader;
import textures.ModelTexture;

public class Surface {

	private static final float SIZE = 100;
	private static final int VERTEX_COUNT = 128;
	
	private float x;
	private float y;
	private float z;
	
	private RawModel model;
	private ModelTexture texture;
	
	public Surface( int gridX, int gridY, int gridZ, Loader loader, ModelTexture texture ) {
		
		this.texture = texture;
		this.x = gridX * SIZE;
		this.y = gridY;
		this.z = gridZ * SIZE;
		this.model = generateTerrian(loader);
		
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}

	public RawModel getModel() {
		return model;
	}

	public ModelTexture getTexture() {
		return texture;
	}


	public RawModel generateTerrian( Loader loader ) {
		
		int count = VERTEX_COUNT * VERTEX_COUNT;
		
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[ 6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT * 1) ];
		
		int vertexPoiter = 0;
		
		for( int i = 0; i < VERTEX_COUNT; i++ ) {
			for( int j = 0; j < VERTEX_COUNT; j++ ) {
				
				vertices[ vertexPoiter * 3 + 0 ] = -(float) j / ( (float) VERTEX_COUNT - 1 ) * SIZE;
				vertices[ vertexPoiter * 3 + 1 ] = 0;
				vertices[ vertexPoiter * 3 + 2 ] = -(float) i / ( (float) VERTEX_COUNT - 1 ) * SIZE;
				
				normals[ vertexPoiter * 3 + 0 ] = 0;
				normals[ vertexPoiter * 3 + 1 ] = 1;
				normals[ vertexPoiter * 3 + 2 ] = 0;
				
				textureCoords[ vertexPoiter * 2 + 0 ] = ( (float) j / ( (float) VERTEX_COUNT - 1 ) );
				textureCoords[ vertexPoiter * 2 + 1 ] = ( (float) i / ( (float) VERTEX_COUNT - 1 ) );
				
				vertexPoiter++;
				
			}
		}
		
		int pointer = 0;
		
		for( int gz = 0; gz < VERTEX_COUNT - 1; gz++ ) {
			for( int gx = 0; gx < VERTEX_COUNT - 1; gx++ ) {
				
				int topLeft 	= ( gz * VERTEX_COUNT ) + gx;
				int topRight 	= topLeft + 1; 
				int bottomLeft 	= ( ( gz + 1 ) * VERTEX_COUNT ) + gx;
				int bottomRight = bottomLeft + 1;
				
				indices[ pointer++ ] = topLeft;
				indices[ pointer++ ] = bottomLeft;
				indices[ pointer++ ] = topRight;
				indices[ pointer++ ] = topRight;
				indices[ pointer++ ] = bottomLeft;
				indices[ pointer++ ] = bottomRight;
				
			}
		}
		
		return loader.loadToVAO( vertices, textureCoords, normals, indices);
		
	}
	
}
