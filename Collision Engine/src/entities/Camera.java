package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position = new Vector3f( -1, 30, -1 );		// Camera Current Position
	private float pitch = 0;									// Camera Pitch "Vertical Angle"
	private float yaw = -45;									// Camera Yaw "Horizontal Angle"
	private float roll;											// Camera Roll "Tilted Amount"
	
	public Camera() {}

	public void move() { // Camera Movement Every Frame
		
		if( Keyboard.isKeyDown( Keyboard.KEY_W ) ) {
			increaseX( -.3f * (float) Math.sin( Math.toRadians(-yaw) ) );
			increaseY( +.3f * (float) Math.sin( Math.toRadians(-pitch) ) );
			increaseZ( -.3f * (float) Math.cos( Math.toRadians(-yaw) ) );
		}
		if( Keyboard.isKeyDown( Keyboard.KEY_S ) ) {
			increaseX( +.3f * (float) Math.sin( Math.toRadians(-yaw) ) );
			increaseY( -.3f * (float) Math.sin( Math.toRadians(-pitch) ) );
			increaseZ( +.3f * (float) Math.cos( Math.toRadians(-yaw) ) );
		}
		if( Keyboard.isKeyDown( Keyboard.KEY_D ) ) {
			increaseX( +.3f * (float) Math.cos( Math.toRadians(-yaw) ) );
			increaseZ( -.3f * (float) Math.sin( Math.toRadians(-yaw) ) );
		}
		if( Keyboard.isKeyDown( Keyboard.KEY_A ) ) {
			increaseX( -.3f * (float) Math.cos( Math.toRadians(-yaw) ) );
			increaseZ( +.3f * (float) Math.sin( Math.toRadians(-yaw) ) );
		}
		
		if( Mouse.isButtonDown(0) ) {
			pitch 	+= Mouse.getDY() / 2f;
			if( pitch >= 360 || pitch <= -360 )
				pitch = 0;
			yaw		-= Mouse.getDX() / 2f;
			if( yaw >= 360 || yaw <= -360 )
				yaw = 0;
			
		}
		
	}

	public Vector3f getPosition() { // Get The Camera Position
		return position;
	}

	public float getPitch() { // Get The Camera Pitch
		return pitch;
	}

	public float getYaw() {	// Get The Camera Yaw
		return yaw;
	}

	public float getRoll() { // Get The Camera Roll
		return roll;
	}
	
	private void increaseX( float offset ) {
		if( ( position.x <= -1 && position.x >= -99 ) ||
			( position.x > -1 && offset < 0 ) ||
			( position.x < -99 && offset > 0 ) ) {
			position.x += offset;
		}
	}
	
	private void increaseY( float offset ) {
		if( ( position.y >= 1 && position.y <= 49 ) ||
			( position.y < 1 && offset > 0 ) ||
			( position.y > 49 && offset < 0 ) ) {
			position.y += offset;
		}
	}
	
	private void increaseZ( float offset ) {
		if( ( position.z <= -1 && position.z >= -99 ) ||
			( position.z > -1 && offset < 0 ) ||
			( position.z < -99 && offset > 0 ) ) {
			position.z += offset;
		}
	}
	
	
}
