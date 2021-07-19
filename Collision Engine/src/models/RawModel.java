package models;

public class RawModel {
	
	private int vaoID;			// VAO ID
	private int vertexCount;	// Number Of Vertices
	
	public RawModel( int vaoID, int vertexCount ){ // Default Constructor
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	public int getVaoID() {			// Get VAO ID
		return vaoID;
	}

	public int getVertexCount() {	// Get The Number Of Vertices
		return vertexCount;
	}
	
	

}
