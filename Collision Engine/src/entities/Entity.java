package entities;

import models.TexturedModel;

import org.lwjgl.util.vector.Vector3f;

public class Entity {

	private TexturedModel model;		// Entity Object With It's Texture
	private Vector3f position;			// Entity Position
	private float rotX, rotY, rotZ;		// Entity Rotation
	private float scale;				// Entity Scale
	
	private int textureIndex = 0;		// Texture Index For Grid Texture - Texture Atlas Image
	
	private boolean stop = false;

	public Entity( TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale ) { // Default Constructor - With Texture Atlas
		this.model = model;
		this.textureIndex = textureIndex;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public Entity( TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale ) { // Default Constructor - Without Texture Atlas
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}
	
	public float getTextureXOffset() {	// Texture Column Offset In Grid
		int column = textureIndex % model.getTexture().getNumberOfRows();		//	Represent In Which Column Of Grid The Needed Texture
		return (float) column / (float) model.getTexture().getNumberOfRows();	// Return The Float Value
	}
	
	public float getTextureYOffset() {
		int row = textureIndex / model.getTexture().getNumberOfRows();			//	Represent In Which Row Of Grid The Needed Texture
		return (float) row / (float) model.getTexture().getNumberOfRows();		// Return The Float Value
	}

	public void increasePosition( float dx, float dy, float dz ) { // Change The Entity Position ( Translate )
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}

	public void increaseRotation( float dx, float dy, float dz ) { // Change The Entity Rotation ( Rotate )
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	public TexturedModel getModel() {				// Get The Entity Object
		return model;
	}

	public void setModel(TexturedModel model) {		// Set The Entity Object
		this.model = model;
	}

	public Vector3f getPosition() {					// Get The Entity Position
		return position;
	}

	public void setPosition(Vector3f position) {	// Set The Entity Position
		this.position = position;
	}

	public float getRotX() {						// Get X Rotation Value
		return rotX;
	}

	public void setRotX(float rotX) {				// Set X Rotation Value
		this.rotX = rotX;
	}

	public float getRotY() {						// Get Y Rotation Value
		return rotY;
	}

	public void setRotY(float rotY) {				// Set Y Rotation Value
		this.rotY = rotY;
	}

	public float getRotZ() {						// Get Z Rotation Value
		return rotZ;
	}

	public void setRotZ(float rotZ) {				// Set Z Rotation Value
		this.rotZ = rotZ;
	}

	public float getScale() {						// Get Scale Value
		return scale;
	}

	public void setScale(float scale) {				// Set Scale Value
		this.scale = scale;
	}
	
	public void fallDown() {
		if( !stop ) {
			this.position.y -= .2f;
			
		}
		this.position.z -= .3f;
		if( position.y - 1 <= 0 ) {
			stop = true;
		}
	}

}
