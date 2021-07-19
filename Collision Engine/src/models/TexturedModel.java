package models;

import textures.ModelTexture;

public class TexturedModel {
	
	private RawModel rawModel;		// Raw Model - RawModel( vaoID, vertexCounter )
	private ModelTexture texture;	// Model Texture - ModelTexture( Texture )

	
	public TexturedModel( RawModel model, ModelTexture texture ) { // Default Constructor
		this.rawModel = model;
		this.texture = texture;
	}

	public RawModel getRawModel() {		// Get The Raw Model
		return rawModel;
	}

	public ModelTexture getTexture() {	// Get The Model Texture
		return texture;
	}

}
