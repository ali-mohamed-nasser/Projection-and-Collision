package entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	
	private Vector3f position;		// Light Position
	private Vector3f colour;		// Light Color
	private Vector3f attenuation	// Light Attenuation
		= new Vector3f( 1, 0, 0 );
	
	public Light( Vector3f position, Vector3f colour, Vector3f attenuation ) { // Default Constructor - With Attenuation
		this.position = position;
		this.colour = colour;
		this.attenuation = attenuation;
	}
	
	public Light( Vector3f position, Vector3f colour ) { // Default Constructor - Without Attenuation
		this.position = position;
		this.colour = colour;
	}

	public Vector3f getPosition() {					// Get The Light Position
		return position;
	}

	public void setPosition(Vector3f position) {	// Set The Light Position
		this.position = position;
	}

	public Vector3f getColour() {					// Get The Light Color
		return colour;
	}

	public void setColour(Vector3f colour) {		// Set The Light Color
		this.colour = colour;
	}
	
	public Vector3f getAttenuation() {				// Get The Light Attenuation
		return attenuation;
	}
	

}
