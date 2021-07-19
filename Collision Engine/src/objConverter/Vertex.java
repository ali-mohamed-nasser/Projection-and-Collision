package objConverter;

import org.lwjgl.util.vector.Vector3f;

public class Vertex {
	
	private static final int NO_INDEX = -1;		// Vertex Dosn't Have An Index Yet
	
	private Vector3f position;					// Vertex Position
	private int textureIndex = NO_INDEX;		// Vertex Texture Index
	private int normalIndex = NO_INDEX;			// Vertex Normal Index
	private Vertex duplicateVertex = null;		// The Duplicated Vertex
	private int index;							// Vertex Index
	private float length;						// Vertex Length
	
	public Vertex( int index,Vector3f position ){	// Default Constructor
		this.index = index;
		this.position = position;
		this.length = position.length();
	}
	
	public int getIndex() {																	// Get The Vertex Index
		return index;
	}
	
	public float getLength() {																// Get The Vertex Length
		return length;
	}
	
	public boolean isSet() {																// Return If The Vertex Has Processed Already
		return textureIndex != NO_INDEX && normalIndex != NO_INDEX;
	}
	
	public boolean hasSameTextureAndNormal( int textureIndexOther,int normalIndexOther ) {	// Return If Two Vertices Has The Same Texture & Normal
		return textureIndexOther == textureIndex && normalIndexOther == normalIndex;
	}
	
	public void setTextureIndex( int textureIndex ) {										// Set The Vertex Texture Index
		this.textureIndex = textureIndex;
	}
	
	public void setNormalIndex( int normalIndex ) {											// Set The Vertex Normal Index
		this.normalIndex = normalIndex;
	}

	public Vector3f getPosition() {															// Get The Vertex Position
		return position;
	}

	public int getTextureIndex() {															// Get The Vertex Texture Index
		return textureIndex;
	}

	public int getNormalIndex() {															// Get The Vertex Normal Index
		return normalIndex;
	}

	public Vertex getDuplicateVertex() {													// Get The Duplicated Vertex
		return duplicateVertex;
	}

	public void setDuplicateVertex( Vertex duplicateVertex ) {								// Set The Duplicated Vertex
		this.duplicateVertex = duplicateVertex;
	}

}
