package Physics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import dashboard.FXMLDocumentController;
import mainApp.MainEngine;

public class PhysicsData {
	
//	private static final float VI 					= 30.0f;					// Initial Velocity
//	private static final float G					= -9.81f;					// Gravity Constant
//	private static final float AX					= 0;						// Wind on Axis X
//	private static final float AY					= 0;						// Wind on Axis Y
//	private static final float AZ					= 0;						// Wind on Axis Z
//	private static final float AIRRESISTCOEFFICIENT	= .1f;						// Air Resistance Drag Parameter (b/m)
//	private static final float FRICTIONCOEFFICIENT 	= .5f;						// Friction Coefficient
//	private static final float MASS 				= 4;						// Shape Mass
//	private static final float GROUNDMASS 			= 10000;					// Ground Mass
//	private static final float DELTATIME 			= VI / 100 * 0.0001f;		// Very Small Value
	
	private static float VI;						// Initial Velocity
	private static float G;							// Gravity Constant
	private static float AX;						// Wind on Axis X
	private static float AY;						// Wind on Axis Y
	private static float AZ;						// Wind on Axis Z
	private static float AIRRESISTCOEFFICIENT;		// Air Resistance Drag Parameter (b/m)
	private static float FRICTIONCOEFFICIENT;		// Friction Coefficient
	private static float MASS; 						// Shape Mass
	private static float GROUNDMASS; 				// Ground Mass
	private static float DELTATIME; 				// Very Small Value
	
	private static float FRICTIONCOEFFICIENTARR[] = { 0.45f, 0.64f, 0.7f, 0.76f, 0.78f, 0.35f, 0.7f };
	private static ArrayList<String> materials = new ArrayList<>( Arrays.asList( "wood", "glass", "iron", "gold", "copper", "rubber", "steel" ) );
	
	public static void setData() {
		
		String material;
		
		VI 						= (float) FXMLDocumentController.getInitialVelocity();
		G 						= (float) FXMLDocumentController.getGravity();
		AX 						= (float) FXMLDocumentController.getAX();
		AY 						= (float) FXMLDocumentController.getAY();
		AZ 						= (float) FXMLDocumentController.getAZ();
		AIRRESISTCOEFFICIENT 	= (float) FXMLDocumentController.getAirresistcoefficient();
		FRICTIONCOEFFICIENT 	= (float) FXMLDocumentController.getFrictioncoefficient();
		MASS 					= (float) FXMLDocumentController.getMass();
		GROUNDMASS 				= (float) FXMLDocumentController.getGROUNDMASS();
		DELTATIME 				= VI / 100 * 0.0001f;
		
		switch ( FXMLDocumentController.getMaterial() ) {
		case 1:
			material = "wood";
			break;
		case 2:
			material = "glass";
			break;
		case 3:
			material = "iron";
			break;
		case 4:
			material = "gold";
			break;
		case 5:
			material = "copper";
			break;
		case 6:
			material = "rubber";
			break;
		case 7:
			material = "steel";
			break;
		default:
			material = "wood";
			break;
		}
		
		switch ( FXMLDocumentController.getSound() ) {
		case 0:
			MainEngine.setSoundStatus(false);
			break;
		case 1:
			MainEngine.setSoundStatus(true);
			break;
		default:
			MainEngine.setSoundStatus(true);
			break;
		}
		
		switch ( FXMLDocumentController.getLight() ) {
		case 0:
			MainEngine.setLightStatus(false);
			break;
		case 1:
			MainEngine.setLightStatus(true);
			break;
		default:
			MainEngine.setLightStatus(false);
			break;
		}
		
		MainEngine.setMaterial( material );

		
	}
	
	public static String switchMaterial( String current ) {
		
		int index = materials.indexOf( current );
		index++;
		if( index >= 6 )
			index = 0;
		FRICTIONCOEFFICIENT = FRICTIONCOEFFICIENTARR[index];
		return materials.get(index);
		
	}
	
	public static float getInitialVelocity() {
		return VI;
	}
	
	public static float getGravity() {
		return G;
	}
	
	public static float getAX() {
		return AX;
	}
	
	public static float getAY() {
		return AY;
	}
	
	public static float getAZ() {
		return AZ;
	}

	public static float getAirresistcoefficient() {
		return AIRRESISTCOEFFICIENT;
	}
	
	public static float getFrictioncoefficient() {
		return FRICTIONCOEFFICIENT;
	}

	public static float getMass() {
		return MASS;
	}
	
	public static float getGROUNDMASS() {
		return GROUNDMASS;
	}
	
	public static float getDeltaTime() {
		return DELTATIME;
	}

}
