package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager {
	
	private static final int WIDTH = 1366;		// Window Width
	private static final int HEIGHT = 768;		// Window Height
	private static final int FPS_CAP = 120;		// Frame Per Second Capacity
	
	private static long lastFrameTime;			// Last Frame Time
	private static float delta;					// Represent The Frame Seconds
	
	public static void createDisplay()	{	// Create The Window Method		
		
		ContextAttribs attribs = new ContextAttribs(3,2)				// Major Version - V3.2 
		.withForwardCompatible(true)
		.withProfileCore(true);
		
		try {
			
//			Display.setDisplayMode( new DisplayMode(WIDTH,HEIGHT) );	// Set The Window Parameters
			Display.create(new PixelFormat(), attribs);					// Create The Window
			Display.setTitle("Our First Display!");						// Set The Window Title
			Display.setFullscreen(true);
			
		} catch (LWJGLException e) {									// Catching The Errors
			e.printStackTrace();
		}
		
		GL11.glViewport(0,0, WIDTH, HEIGHT);							// Create The Window In The Position ( 0, 0 ) Of The Screen "Bottom Left"
		
		lastFrameTime = getCurrentTime();								// Calculate The Last Frame Time For Each Frame
		
	}
	
	public static void updateDisplay(){
		
		Display.sync(FPS_CAP);											// Sync The Display Depending On Frame Per Second Rate
		Display.update();												// Update The Window Stuffs For Each Frame
		long currentFrameTime = getCurrentTime();						// Calculate The Current Time
		delta = (currentFrameTime - lastFrameTime) / 1000f; 			// Calculate The Frame Seconds Value - ( Current - Last ) => Frame Per Seconds
		lastFrameTime = currentFrameTime;								// Calculate The New Last Frame Time
		
	}
	
	public static float getFrameTimeSeconds() {							// Get The Frame Seconds Value
		return delta;
	}
	
	public static void closeDisplay(){									// Close The Window
		Display.destroy();
	}
	
	private static long getCurrentTime() {								// Calculate The Current Time
		return Sys.getTime()*1000 / Sys.getTimerResolution();
	}

}
