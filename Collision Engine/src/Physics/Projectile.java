package Physics;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entities.Light;

public class Projectile {
	
	private Entity object;
	private Vector3f initialPoint;
	private Vector3f highestPoint;
	private boolean stopped = false;
	private float startTime;
	private float theta1;
	private float theta2;
	private float velocity;
	private float velocityX;
	private float velocityY;
	private float velocityZ;
	private float finalTime;
	private float lastCollisionTime;
	private Light light;

	public Projectile( Entity object, Vector3f initialPoint, float theta1, float theta2, Light light ) {
		this.object 		= object;
		this.initialPoint 	= initialPoint;
		this.highestPoint	= initialPoint;
		this.theta1 		= theta1;
		this.theta2 		= theta2;
		this.velocity 		= PhysicsData.getInitialVelocity();
		this.light 			= light;
		this.startTime 		= (float) ( Sys.getTime() * 1000 / Sys.getTimerResolution() ) / 1000;
		this.velocityX		= velocity * (float) Math.cos( Math.toRadians(-theta1) ) * (float) Math.sin( Math.toRadians(-theta2) );
		this.velocityY		= velocity * (float) Math.sin( Math.toRadians(-theta1) );
		this.velocityZ		= velocity * (float) Math.cos( Math.toRadians(-theta1) ) * (float) Math.cos( Math.toRadians(-theta2) );
	}

	public Entity getObject() {
		return object;
	}

	public void setObject(Entity object) {
		this.object = object;
	}

	public boolean isStopped() {
		return stopped;
	}

	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

	public Vector3f getInitialPoint() {
		return initialPoint;
	}
	
	public Vector3f getHighestPoint() {
		return highestPoint;
	}
	
	public void setHighestPoint(Vector3f highestPoint) {
		this.highestPoint = highestPoint;
	}

	public float getFinalTime() {
		return finalTime;
	}

	public void reverseTheta2( float angle ) {
		this.theta2 = ( angle + this.theta2 ) * -1;
	}
	
	public float getVelocityValue() {
		return (float) Math.sqrt( this.velocityX * this.velocityX + this.velocityY * this.velocityY + this.velocityZ * this.velocityZ );
	}
	
	public float getTheta1() {
		return theta1;
	}
	
	public float getTheta2() {
		return theta2;
	}
	
	public float getVelocityX() {
		return velocityX;
	}

	public float getVelocityY() {
		return velocityY;
	}

	public float getVelocityZ() {
		return velocityZ;
	}

	public float getLastCollisionTime() {
		return lastCollisionTime;
	}

	public void setLastCollisionTime(float lastCollisionTime) {
		this.lastCollisionTime = lastCollisionTime;
	}

	public void Shoot() {

		float time = ( (float) ( Sys.getTime() * 1000 / Sys.getTimerResolution() ) / 1000 ) - startTime;

		velocity = getVelocityValue();
		float resist = velocity * velocity * PhysicsData.getAirresistcoefficient();
		velocityX += ( PhysicsData.getAX() - velocityX / velocity * resist ) * PhysicsData.getDeltaTime();
		velocityY += ( PhysicsData.getAY() - PhysicsData.getGravity() - velocityY / velocity * resist ) * PhysicsData.getDeltaTime();
		velocityZ += ( PhysicsData.getAZ() - velocityZ / velocity * resist ) * PhysicsData.getDeltaTime();
		
		float x = -velocityX * time + initialPoint.x;
		float z = -velocityZ * time + initialPoint.z;
		float y = 0.5f * PhysicsData.getGravity() * time * time * 2 + velocityY * time +  initialPoint.y;
		
		if( y > highestPoint.y )
			highestPoint = new Vector3f( x, y, z );
		
		object.setPosition( new Vector3f( x, y, z ) );
		light.setPosition( new Vector3f( x, y + 1, z ) );
	}
	
	public float Drag() {
		
		float friction = -PhysicsData.getGravity() * PhysicsData.getMass() * PhysicsData.getFrictioncoefficient();
		float dVX = ( -velocityX / velocity * friction ) * PhysicsData.getDeltaTime();
		float dVY = ( -velocityY / velocity * friction ) * PhysicsData.getDeltaTime();
		float dVZ = ( -velocityZ / velocity * friction ) * PhysicsData.getDeltaTime();
		float dV  = (float) Math.sqrt( dVX * dVX + dVY * dVY + dVZ * dVZ ) * (float) Math.pow( 10, 3);
		
		return dV;
		
	}
	
	public void newProjection( float theta1, float velocity ) {
		if( velocity <= 1 ) {
			stopped = true;
			return;
		}
		this.velocity 		= velocity;
		this.theta1 		= theta1 == 361 ? this.theta1 : theta1;
		this.velocityX		= velocity * (float) Math.cos( Math.toRadians(-theta1) ) * (float) Math.sin( Math.toRadians(-theta2) );
		this.velocityY		= velocity * (float) Math.sin( Math.toRadians(-theta1) );
		this.velocityZ		= velocity * (float) Math.cos( Math.toRadians(-theta1) ) * (float) Math.cos( Math.toRadians(-theta2) );
		this.initialPoint 	= this.object.getPosition();
		this.highestPoint	= initialPoint;
		this.startTime 		= (float) ( Sys.getTime() * 1000 / Sys.getTimerResolution() ) / 1000;
	}
	
	public void newProjection( float velocity, float theta1, float theta2 ) {
		this.stopped = false;
		if( velocity <= 1 ) {
			stopped = true;
			return;
		}
		
		this.velocity 		= velocity;
		this.theta1 		= theta1;
		this.theta2 		= theta2;
		this.velocityX		= velocity * (float) Math.cos( Math.toRadians(-theta1) ) * (float) Math.sin( Math.toRadians(-theta2) );
		this.velocityY		= velocity * (float) Math.sin( Math.toRadians(-theta1) );
		this.velocityZ		= velocity * (float) Math.cos( Math.toRadians(-theta1) ) * (float) Math.cos( Math.toRadians(-theta2) );
		this.initialPoint 	= this.object.getPosition();
		this.highestPoint	= initialPoint;
		this.startTime 		= (float) ( Sys.getTime() * 1000 / Sys.getTimerResolution() ) / 1000;
	}
	
	public void fallDown() {
		float time = ( (float) ( Sys.getTime() * 1000 / Sys.getTimerResolution() ) / 1000 ) - startTime;
		initialPoint		= this.object.getPosition();
		float y = 0.5f * PhysicsData.getGravity() * time * time * 2;
		float x = this.object.getPosition().x;
		float z = this.object.getPosition().z;
		object.setPosition( new Vector3f( x, y, z ) );
		light.setPosition( new Vector3f( x, y + 1, z ) );
	}
	
	
	public void setFinalTime() {
		finalTime = ( (float) ( Sys.getTime() * 1000 / Sys.getTimerResolution() ) / 1000 ) - startTime;
	}

}
