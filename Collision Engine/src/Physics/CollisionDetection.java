package Physics;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class CollisionDetection {

	public static Projectile checkObjectsContact( List<Projectile> projectiles, Vector3f position, int currentIndex ) {
		
		int count = -1;
		
		for( Projectile projectile : projectiles ) {
			
			count++;
			if( count == currentIndex )
				continue;
			
			Vector3f center = new Vector3f( projectile.getObject().getPosition().x, projectile.getObject().getPosition().y - 1, projectile.getObject().getPosition().z );
			
			for( float i = -1; i <= 1; i += .1f ) {
				for( float j = -1; j <= 1; j += .1f ) {
					for( float k = -1; k <= 1; k += .1f ) {
						
						if( checkPointContact( center, new Vector3f( position.x + i, position.y + j, position.z + k ) ) ) {
							return projectile;
						}
					}
				}
			}
			
		}
		
		return null;
	}
	
	private static boolean checkPointContact( Vector3f center, Vector3f point ) {
		if( point.x <= center.x + 1 && point.x >= center.x - 1 )
			if( point.y <= center.y + 1 && point.y >= center.y - 1 )
				if( point.z <= center.z + 1 && point.z >= center.z - 1 )
					return true;
		
		return false;
	}
	
	public static boolean checkRoom( Projectile projectile ) {
		
		float x = projectile.getObject().getPosition().x;
		float y = projectile.getObject().getPosition().y;
		float z = projectile.getObject().getPosition().z;
		
		if( x <= -99 ) { // Left Wall Contact
			x = -98.99f;
			projectile.getObject().setPosition( new Vector3f( x, y, z ) );
			projectile.reverseTheta2( 360 );
			projectile.setHighestPoint(projectile.getInitialPoint());
			return true;
		} else if ( x >= -1 ) { // Right Wall Contact
			x = -1.01f;
			projectile.getObject().setPosition( new Vector3f( x, y, z ) );
			projectile.reverseTheta2( 360 );
			projectile.setHighestPoint(projectile.getInitialPoint());
			return true;
		}
		
		if( z <= -99 ) { // Front Wall Contact
			z = -98.99f;
			projectile.getObject().setPosition( new Vector3f( x, y, z ) );
			projectile.reverseTheta2( 180 );
			projectile.setHighestPoint(projectile.getInitialPoint());
			return true;
		} else if ( z >= -1 ) { // Back Wall Contact
			z = -1.01f;
			projectile.getObject().setPosition( new Vector3f( x, y, z ) );
			projectile.reverseTheta2( 180 );
			projectile.setHighestPoint(projectile.getInitialPoint());
			return true;
		}
		
		if( y <= 0 ) { // Floor Contact
			y = 0.01f;
			projectile.getObject().setPosition( new Vector3f( x, y, z ) );
			return true;
		} else if ( y >= 48 ) { // Roof Contact
			y = 47.99f;
			projectile.setHighestPoint( projectile.getInitialPoint() );
			projectile.getObject().setPosition( new Vector3f( x, y, z ) );
			return true;
		}
		
		
		return false;
		
	}
	
	public static float calculateRebounceAngle( Projectile projectile ) {
		
		Vector3f 	p1 = projectile.getHighestPoint(),
					p2 = projectile.getObject().getPosition(),
					p3 = new Vector3f( p1.x, p2.y, p1.z );
		
		if( p1.equals( p2 ) )
			p1 = projectile.getInitialPoint();
		
		float 		d1 = (float) Math.sqrt( Math.pow( p1.x - p2.x, 2 ) + Math.pow( p1.y - p2.y, 2 ) + Math.pow( p1.z - p2.z, 2 ) ),
					d2 = (float) Math.sqrt( Math.pow( p2.x - p3.x, 2 ) + Math.pow( p2.y - p3.y, 2 ) + Math.pow( p2.z - p3.z, 2 ) );
		
		float 	Angle = d2 / d1;
				Angle = (float) Math.acos( Angle );
				Angle = (float) Math.toDegrees( Angle );
				
		if( Float.isNaN( Angle ) ) {
			Angle = -10;
		}
				
		return Angle;
		
	}
	
	public static Vector2f getRebounceVelocity( Projectile projectile ) {
		
		Vector3f 	p1 = projectile.getInitialPoint(),
					p2 = projectile.getObject().getPosition();
		
		float 		d  = (float) Math.sqrt( Math.pow( p1.x - p2.x, 2 ) + Math.pow( p1.y - p2.y, 2 ) + Math.pow( p1.z - p2.z, 2 ) );
		
		float time = projectile.getFinalTime() == 0 ? .01f : projectile.getFinalTime();
		
		float velcotiy = ( d / time ) <= projectile.getVelocityValue() ? ( d / time ) : projectile.getVelocityValue();
		
		return new Vector2f( velcotiy, d );
		
	}
	
	public static Vector2f calculateCollisionVelocity( Projectile object1, Projectile object2, float theta1, float theta2, float theta3, float theta4 ) {
		
		float 	Vx1 	= 0.0f,
				Vy1 	= 0.0f,
	  			Vx2 	= object2.getVelocityX(),
				Vy2 	= object2.getVelocityY(),
				m   	= PhysicsData.getMass(),
				sinT1 	= (float) Math.sin( Math.toRadians(theta1) ),
				cosT1 	= (float) Math.cos( Math.toRadians(theta1) ),
				sinT2 	= (float) Math.sin( Math.toRadians(theta2) ),
				sinT3 	= (float) Math.sin( Math.toRadians(theta3) ),
				tanT3 	= (float) Math.tan( Math.toRadians(theta3) ),
				sinT4 	= (float) Math.sin( Math.toRadians(theta4) );
		

		double factor = - ( sinT4 / ( tanT3 ) );
		double firstSide = (m * Vx1 + m * Vx2) + ( factor * (m * Vy1 + m * Vy2) );
		double secondSide = m * cosT1 * sinT2 + ( factor * (m * sinT1 ));
		
		double V1 = firstSide / secondSide;
		double V2 = ( (m * Vy1 + m * Vy2) - (m * V1 * sinT1 ) ) / m * sinT3;
		double totalV = object2.getVelocityValue();
		V1 = totalV / 1.5d;
		V2 = totalV - V1;
		
		return new Vector2f( (float) Math.abs(V1), (float) Math.abs(V2) );
		
	}
	
}
