package objConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class OBJFileLoader {
	
	private static final String RES_LOC = "res/";	// Models Folder
	
	public static ModelData loadOBJ( String objFileName ) {	// Load OBJ File Method
		
		// Read The File
		FileReader isr = null;
		File objFile = new File(RES_LOC + objFileName + ".obj");
		
		try {
			isr = new FileReader(objFile);	// Reading The File
		} catch (FileNotFoundException e) { // Catching The Errors
			System.err.println("File not found in res folder; don't use any extention");
		}
		
		BufferedReader reader = new BufferedReader(isr);
		
		String line; 											// String to store the lines - line by line
		List<Vertex> vertices 	= new ArrayList<Vertex>();		// List of The Model Vertices 
		List<Vector2f> textures = new ArrayList<Vector2f>();	// List of The Model Textures -- Texture Coordinates
		List<Vector3f> normals 	= new ArrayList<Vector3f>();	// List of The Model Normals
		List<Integer> indices 	= new ArrayList<Integer>();		// List of The Model Indices
		
		try {
			while ( true ) {	// While The File Have Lines
				
				line = reader.readLine();	// Read The Current Line
				
				if ( line.startsWith("v ") ) {	// If The Line Start with "v" Then It's Vertex
					
					String[] currentLine = line.split(" ");								// Represent The Position ( XYZ ) of The Vertex
					Vector3f vertexPosition = new Vector3f(
							(float) Float.valueOf( currentLine[ 1 ] ),					// The X Position Of The Vertex
							(float) Float.valueOf( currentLine[ 2 ] ),					// The Y Position Of The Vertex
							(float) Float.valueOf( currentLine[ 3 ] ) );				// The Z Position Of The Vertex
					
					Vertex newVertex = new Vertex( vertices.size(), vertexPosition );	// Create New Vertex - Vertex( Index, Position )
					vertices.add( newVertex );											// Add This Vertex To The Vertices List

				} else if ( line.startsWith("vt ") ) {	// Else If The Line Start with "vt" Then It's a Texture Coordinate
					
					String[] currentLine = line.split(" ");					// Represent The Texture Coordinate ( XY )
					Vector2f texture = new Vector2f(
							(float) Float.valueOf( currentLine[ 1 ] ),		// The X Position Of The Texture Coordinate		
							(float) Float.valueOf( currentLine[ 2 ] ) );	// The Y Position Of The Texture Coordinate
					textures.add( texture );								// Add This Coordinate To The Texture Coordinates List
					
				} else if ( line.startsWith("vn ") ) {	// Else If The Line Start with "vn" Then It's a Model Normal
					
					String[] currentLine = line.split(" ");					// Represent The Model Normal ( XYZ )
					Vector3f normal = new Vector3f(
							(float) Float.valueOf( currentLine[ 1 ] ),		// The X Position Of The Model Normal
							(float) Float.valueOf( currentLine[ 2 ] ),		// The Y Position Of The Model Normal
							(float) Float.valueOf( currentLine[ 3 ] ) );	// The Z Position Of The Model Normal
					normals.add(normal);									// Add This Normal Vector To The Normals List
					
				} else if ( line.startsWith("f ") ) {	// We'll Deal With The Connecting Method In The Next While Loop
					break;	// Break The While
				}
			}
			
			while ( line != null && line.startsWith("f ") ) {	// While It's Not The End Of The File
				
				String[] currentLine = line.split(" ");				// Represent The Triangle Connecting Vertices
				
				String[] vertex1 = currentLine[ 1 ].split("/");		// Triangle First Vertex Point
				String[] vertex2 = currentLine[ 2 ].split("/");		// Triangle Second Vertex Point
				String[] vertex3 = currentLine[ 3 ].split("/");		// Triangle Third Vertex Point
				
				processVertex( vertex1, vertices, indices );		// Process The First Vertex Or Deal With It If It's Processed Before 
				processVertex( vertex2, vertices, indices );		// Process The Second Vertex Or Deal With It If It's Processed Before
				processVertex( vertex3, vertices, indices );		// Process The Third Vertex Or Deal With It If It's Processed Before
				
				line = reader.readLine();							// Read The Next Line
				
			}
			
			reader.close(); // Close Object The File
			
		} catch (IOException e) {	// Catching The Errors
			System.err.println("Error reading the file");
		}
		
		// Remove Unused Vertices
		removeUnusedVertices( vertices );
		
		float[] verticesArray 	= new float[ vertices.size() * 3 ];		// Every Vertex Will Be A Vector of 3 Points (XYZ)
		float[] texturesArray 	= new float[ vertices.size() * 2 ];		// Every Texture Coordinate Will Be A Vector of 2 Points (XY)
		float[] normalsArray 	= new float[ vertices.size() * 3 ];		// Every Normal Will Be A Vector of 3 Points (XYZ)
		
		int[] indicesArray 	= convertIndicesListToArray( indices );		// Convert The Indices To Array
		float furthest 		= convertDataToArrays( vertices, textures,	// Convert The Vertices, Coordinates and Normals to Array
				normals, verticesArray, texturesArray, normalsArray );
		
		ModelData data = new ModelData( verticesArray, texturesArray,	// Define The Model With These Data
				normalsArray, indicesArray, furthest );
		
		return data;													// Return The Model Final Data
	}

	private static void processVertex( String[] vertex, List<Vertex> vertices, List<Integer> indices ) {
		
		// The Vertex String Will Be Something Like This "412/312/6" Converted To Array { 412, 312, 6 }
		int index 				= Integer.parseInt( vertex[ 0 ] ) - 1;	// Get The Vertex Index "it'll be 411 in this case"
		int textureIndex 		= Integer.parseInt( vertex[ 1 ] ) - 1;	// Get The Texture Coordinate Index "it'll be 311 in this case"
		int normalIndex 		= Integer.parseInt( vertex[ 2 ] ) - 1;	// Get The Normal Point Index "it'll be 5 in this case"
		
		Vertex currentVertex = vertices.get( index );					// Get The Vertex From Vertices List
		
		if ( !currentVertex.isSet() ) {	// If This Vertex Isn't Processed Before
			currentVertex.setTextureIndex( textureIndex );				// Set It's Texture
			currentVertex.setNormalIndex( normalIndex );				// Set It's Normal
			indices.add( index );										// Add This Vertex To The Indices
		} else { // Else
			dealWithProcessedVertex( currentVertex, textureIndex, normalIndex, indices, vertices );	// Deal With It
		}
		
	}

	private static int[] convertIndicesListToArray( List<Integer> indices ) {
		
		int[] indicesArray = new int[ indices.size() ];	
		for (int i = 0; i < indicesArray.length; i++) {	// For Every Induce in The Indices List
			indicesArray[i] = indices.get(i);			// Set It to Same Index In The Array
		}
		
		return indicesArray;							// Return The Final Array
	}

	private static float convertDataToArrays( List<Vertex> vertices, List<Vector2f> textures, List<Vector3f> normals, float[] verticesArray, float[] texturesArray, float[] normalsArray ) {
		
		float furthestPoint = 0;
		for ( int i = 0; i < vertices.size(); i++ ) {
			
			Vertex currentVertex = vertices.get(i);
			
			if ( currentVertex.getLength() > furthestPoint ) {
				furthestPoint = currentVertex.getLength();
			}
			
			Vector3f position 		= currentVertex.getPosition();
			Vector2f textureCoord 	= textures.get( currentVertex.getTextureIndex() );
			Vector3f normalVector 	= normals.get( currentVertex.getNormalIndex() );
			
			verticesArray[ i * 3 + 0 ] = position.x;
			verticesArray[ i * 3 + 1 ] = position.y;
			verticesArray[ i * 3 + 2 ] = position.z;
			
			texturesArray[ i * 2 + 0 ] = textureCoord.x;
			texturesArray[ i * 2 + 1 ] = 1 - textureCoord.y;
			
			normalsArray[ i * 3 + 0 ] = normalVector.x;
			normalsArray[ i * 3 + 1 ] = normalVector.y;
			normalsArray[ i * 3 + 2 ] = normalVector.z;
			
		}
		return furthestPoint;
		
	}

	private static void dealWithProcessedVertex( Vertex previousVertex, int newTextureIndex, int newNormalIndex, List<Integer> indices, List<Vertex> vertices ) {
		
		if ( previousVertex.hasSameTextureAndNormal( newTextureIndex, newNormalIndex ) ) { // If The Previous Vertex Has The Same Texture And Normal Values
			indices.add( previousVertex.getIndex() );	// Add The Previous Vertex Index To The Indices List
		} else { // Else
			
			Vertex anotherVertex = previousVertex.getDuplicateVertex();
			if ( anotherVertex != null ) { // If There's A Duplicated Vertex
				dealWithProcessedVertex( anotherVertex, newTextureIndex, newNormalIndex, indices, vertices ); // Deal With The Duplicated Vertex
			} else { // Else
				
				Vertex duplicateVertex = new Vertex( vertices.size(), previousVertex.getPosition() ); 	// Create a Duplicated Vertex
				duplicateVertex.setTextureIndex( newTextureIndex);										// Set The Texture Index as The New Texture
				duplicateVertex.setNormalIndex( newNormalIndex );										// Set The Normal Index as The new Normal
				previousVertex.setDuplicateVertex( duplicateVertex );									// Set The Duplicated Vertex
				vertices.add(duplicateVertex);															// Add The Duplicated Vertex To The Vertices List 
				indices.add(duplicateVertex.getIndex());												// Add The Duplicated Vertex To The Indices List
			}

		}
		
	}
	
	private static void removeUnusedVertices( List<Vertex> vertices ){
		
		for( Vertex vertex : vertices ) {	// For Every Vertex In The Vertices Array
			
			if( !vertex.isSet() ) {			// If The Vertex Is Set As Processed Already
				vertex.setTextureIndex(0);	// Remove It's Texture Index
				vertex.setNormalIndex(0);	// Remove It's Normal Index
			}
			
		}
		
	}

}