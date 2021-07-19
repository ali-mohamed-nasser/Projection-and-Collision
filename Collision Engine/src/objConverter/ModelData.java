package objConverter;

public class ModelData {

	private float[] vertices;		// Array of The Model Vertices
	private float[] textureCoords;	// Array of The Model Coordinates
	private float[] normals;		// Array Of The Model Normals
	private int[] indices;			// Array Of The Model Indices
	private float furthestPoint;	// Represent The Furthest Point

	public ModelData( float[] vertices, float[] textureCoords, float[] normals, int[] indices, float furthestPoint ) { // Default Constructor
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.indices = indices;
		this.furthestPoint = furthestPoint;
	}

	public float[] getVertices() {			// Get The Model Vertices
		return vertices;
	}

	public float[] getTextureCoords() {		// Get The Texture Coordinates
		return textureCoords;
	}

	public float[] getNormals() {			// Get The Model Normals
		return normals;
	}

	public int[] getIndices() {				// Get The Model Indices
		return indices;
	}

	public float getFarthestPoint() {		// Get The Furthest Point
		return furthestPoint;
	}

}
