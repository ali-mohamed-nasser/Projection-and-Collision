package mainApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import Physics.CollisionDetection;
import Physics.PhysicsData;
import Physics.Projectile;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import sound.PlaySound;
import surfaces.Surface;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;
import guis.GuiRenderer;
import guis.GuiTexture;

public class MainEngine {

	private static final float X_SCALE = 0.56222546f;
	
	private static float lastFrameTime;
	private static float lastChangeFrameTime;
	private static float lastFrameTimeDataChecker;
	
	private static List<GuiTexture> guis;
	private static GuiTexture blackBg, welcome, cameraTut, cameraTut2, projectile;
	private static boolean lightsStatus;
	private static boolean soundStatus;
	private static String material;
	
	public static void main(String[] args) {
		
		// Create The Display
		DisplayManager.createDisplay();
		
		// Models Loader
		Loader loader = new Loader();
		
		// Terrain Generation Code
		Surface floor = new Surface(0, 0, 0, loader, new ModelTexture( loader.loadTexture("floor3") ) );
		Surface roof = new Surface(0, 50, 0, loader, new ModelTexture( loader.loadTexture("wall2") ) );
		
		// Game Objects
		List<Entity> entities 	= new ArrayList<Entity>();
		
		// Light( Position, Colour )
		List<Light> lights = new ArrayList<Light>();
		lights.add( new Light( new Vector3f( -50, 100, -50 ), new Vector3f( 1, 1, 1 ) ) );
		
		MasterRenderer renderer = new MasterRenderer(loader);
		
		// Camera
		Camera camera = new Camera();
		
		// Load User Data
		 PhysicsData.setData();
		
		ModelData data = OBJFileLoader.loadOBJ("ball");
		RawModel rawModel = loader.loadToVAO( data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices() );
		ModelTexture texture = new ModelTexture( loader.loadTexture( "materials/" + material ) );
		if( !material.equals("rubber") ) {
			texture.setShineDamper(20);
			texture.setReflectivity(.3f);
		}
		TexturedModel model	= new TexturedModel( rawModel, texture );
		
		List<Entity> walls = createWalls( loader );
		for( Entity wall:walls ) {
			entities.add(wall);
		}
		
		blackBg 	= new GuiTexture( loader.loadTexture("trans"), new Vector2f(0, 0), new Vector2f( 1, 1 ) );
		welcome 	= new GuiTexture( loader.loadTexture("/Tutorials/welcome"), new Vector2f(0, 0), new Vector2f( X_SCALE, 1 ) );
		cameraTut 	= new GuiTexture( loader.loadTexture("/Tutorials/camera"), new Vector2f(0, 0), new Vector2f( X_SCALE, 1 ), 0 );
		cameraTut2	= new GuiTexture( loader.loadTexture("/Tutorials/camera2"), new Vector2f(0, 0), new Vector2f( X_SCALE, 1 ), 0 );
		projectile	= new GuiTexture( loader.loadTexture("/Tutorials/projectile"), new Vector2f(0, 0), new Vector2f( X_SCALE, 1 ), 0 );
		
		guis = new ArrayList<GuiTexture>();
		guis.add(blackBg);
		guis.add(welcome);
		guis.add(cameraTut);
		guis.add(cameraTut2);
		guis.add(projectile);
		
		lastFrameTime = getCurrentTime();
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		float xPos, yPos, zPos;
		
		List<Projectile> 	throwingObjects = new ArrayList<Projectile>();
		
		List<Vector3f> lightColors = new ArrayList<Vector3f>();
		lightColors.add( new Vector3f( 2, 0, 0 ) );
		lightColors.add( new Vector3f( 0, 2, 2 ) );
		lightColors.add( new Vector3f( 2, 2, 2 ) );
		lightColors.add( new Vector3f( 1, 1, .5f ) );
		
		while(!Display.isCloseRequested()){
			
			camera.move();
			
			if( savePeriod( .3f ) )
				PhysicsData.setData();
			
			// Handling Keyboard Actions
			if( Keyboard.isKeyDown( Keyboard.KEY_SPACE ) ) {
				
				if( period( .3f ) ) {
					xPos = camera.getPosition().x + ( -3 * (float) Math.sin( Math.toRadians( -camera.getYaw() ) ) );
					yPos = camera.getPosition().y + ( +3 * (float) Math.sin( Math.toRadians( -camera.getPitch() ) ) ) - 1;
					zPos = camera.getPosition().z + ( -3 * (float) Math.cos( Math.toRadians( -camera.getYaw() ) ) );
					Entity object = new Entity( model, new Vector3f( xPos, yPos, zPos ), 0, 0, 0, 1 );
					entities.add( object );
					
					Vector3f color = lightColors.get( new Random().nextInt(4) );
					
					Light light = new Light( new Vector3f( xPos, yPos, zPos ), color, new Vector3f( 5, .1f, .01f ) );
					if( lightsStatus )
						lights.add(light);
					
					throwingObjects.add( new Projectile( object, object.getPosition(), camera.getPitch(), camera.getYaw(), light ) );
					
					lastFrameTime = getCurrentTime();
				}
				
			}
			
			if( Keyboard.isKeyDown( Keyboard.KEY_C ) ) {
				if( materialPeriod( .3f ) ) {
					lastChangeFrameTime = getCurrentTime();
					material = PhysicsData.switchMaterial( material );
					data = OBJFileLoader.loadOBJ("ball");
					rawModel = loader.loadToVAO( data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices() );
					texture = new ModelTexture( loader.loadTexture( "materials/" + material ) );
					if( !material.equals("rubber") ) {
						texture.setShineDamper(20);
						texture.setReflectivity(.3f);
					}
					model	= new TexturedModel( rawModel, texture );
				}
			}
			
			if( Keyboard.isKeyDown( Keyboard.KEY_L ) ) {
				if( period(1) ) {
					lightsStatus = !lightsStatus;
					lastFrameTime = getCurrentTime();
				}
			}
			
			
			renderer.processTerrain(roof);
			renderer.processTerrain(floor);
			
			for( Entity entity : entities ) {
				renderer.processEntity( entity );
			}
			
			for( Projectile projectile : throwingObjects ) {
				if( !projectile.isStopped() )
					projectile.Shoot();
				else if( projectile.getObject().getPosition().y <= 1 )
					continue;
				
				if( projectile.isStopped() && projectile.getObject().getPosition().y > 1 )
					projectile.fallDown();
				
				if( collisionPeriod( .3f, projectile.getLastCollisionTime() ) ) {
					Projectile collidedProjectile = CollisionDetection.checkObjectsContact( throwingObjects, projectile.getObject().getPosition(), throwingObjects.indexOf(projectile) );
					
					if( collidedProjectile != null ) {
						
						projectile.setLastCollisionTime(getCurrentTime());
						
						float theta1 = -CollisionDetection.calculateRebounceAngle(projectile),
							  theta2 = projectile.getTheta2(),
							  theta3 = projectile.getTheta1() - 90,
							  theta4 = projectile.getTheta2() - 180;
						
						Vector2f velocity = CollisionDetection.calculateCollisionVelocity( collidedProjectile, projectile, theta1, theta2, theta3, theta4 );
						
						projectile.setFinalTime();
						
						collidedProjectile.newProjection( velocity.x, theta1, theta2 );
						projectile.newProjection( velocity.y, theta3, theta4 );
						
					}
				}
				
				if( CollisionDetection.checkRoom( projectile ) ) {
					
					projectile.setFinalTime();
					
					float pitch		 	= CollisionDetection.calculateRebounceAngle(projectile);
					Vector2f velocity 	= CollisionDetection.getRebounceVelocity(projectile);

					if( velocity.y > ( PhysicsData.getInitialVelocity() * .06f ) && soundStatus ) {
						float soundLevel = distanceFromCamera( projectile.getObject().getPosition(), camera.getPosition() ) / 10;
						PlaySound.playSound( "hit.wav", soundLevel * -2 );
					} else {
						velocity.x -= projectile.Drag();
					}
					if( velocity.x <= 0 ) {
						projectile.setStopped(true);
						continue;
					}
					
					projectile.newProjection( -pitch, velocity.x );

				}
				
			}
			
			renderer.render(lights, camera);
			
			guiRenderer.render(guis);
			
			learnGame();
			
			DisplayManager.updateDisplay();
			
			if( Keyboard.isKeyDown( Keyboard.KEY_ESCAPE ) )
				break;
			
		}
		
		// Clean UP
		renderer.cleanUp();
		guiRenderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}
	
	private static List<Entity> createWalls( Loader loader ) {
		
		List<Entity> walls = new ArrayList<Entity>();
		RawModel wallModel;
		TexturedModel wall;
		
		int[] indices = {
				0, 1, 3,
				3, 1, 2
		};
		
		float[] normals 		= new float[ 128 * 128 * 3 ];
		float[] textureCoords 	= new float[ 128 * 128 * 2 ];
		
		int vertexPoiter = 0;
		for( int i = 0; i < 128; i++ ) {
			for( int j = 0; j < 128; j++ ) {
				
				normals[ vertexPoiter * 3 + 0 ] = 0;
				normals[ vertexPoiter * 3 + 1 ] = 1;
				normals[ vertexPoiter * 3 + 2 ] = 0;
				
				textureCoords[ vertexPoiter * 2 + 0 ] = i;
				textureCoords[ vertexPoiter * 2 + 1 ] = i;
				
				vertexPoiter++;
				
			}
		}
		
		// First Wall
		float[] firstWallVertices = {
				-100, 50, 0,			// V0
				-100, 0, 0,				// V1
				0, 0, 0,				// V2
				0, 50, 0				// V3
		};
		wallModel 	= loader.loadToVAO( firstWallVertices, textureCoords, normals, indices );
		wall	= new TexturedModel( wallModel, new ModelTexture( loader.loadTexture("wall2") ) );
		walls.add( new Entity( wall, new Vector3f( 0, 0, 0 ), 0, 0, 0, 1) );
		
		float[] firstWallFooterVertices = {
				-100, 1.3f, -.3f,		// V0
				-100, 0, -.3f,			// V1
				0, 0, -.3f,				// V2
				0, 1.3f, -.3f,			// V3
		};
		wallModel 	= loader.loadToVAO( firstWallFooterVertices, textureCoords, normals, indices );
		wall	= new TexturedModel( wallModel, new ModelTexture( loader.loadTexture("black") ) );
		walls.add( new Entity( wall, new Vector3f( 0, 0, 0 ), 0, 0, 0, 1) );
		
		float[] firstWallFooterLineVertices = {
				-100, 1.3f, 0,			// V0
				-100, 1.3f, -.3f,		// V1
				0, 1.3f, -.3f,			// V2
				0, 1.3f, 0,				// V3
		};
		wallModel 	= loader.loadToVAO( firstWallFooterLineVertices, textureCoords, normals, indices );
		wall	= new TexturedModel( wallModel, new ModelTexture( loader.loadTexture("wall4") ) );
		walls.add( new Entity( wall, new Vector3f( 0, 0, 0 ), 0, 0, 0, 1) );
		
		// Second Wall
		float[] secondWallVertices = {
				-100, 50, -100,			// V0
				-100, 0, -100,			// V1
				0, 0, -100,				// V2
				0, 50, -100				// V3
		};
		wallModel 	= loader.loadToVAO( secondWallVertices, textureCoords, normals, indices );
		wall	= new TexturedModel( wallModel, new ModelTexture( loader.loadTexture("wall2") ) );
		walls.add( new Entity( wall, new Vector3f( 0, 0, 0 ), 0, 0, 0, 1) );
		
		float[] secondWallFooterVertices = {
				-99.7f, 1.3f, -99.7f,	// V0
				-99.7f, 0, -99.7f,		// V1
				0, 0, -99.7f,			// V2
				0, 1.3f, -99.7f			// V3
		};
		wallModel 	= loader.loadToVAO( secondWallFooterVertices, textureCoords, normals, indices );
		wall	= new TexturedModel( wallModel, new ModelTexture( loader.loadTexture("black") ) );
		walls.add( new Entity( wall, new Vector3f( 0, 0, 0 ), 0, 0, 0, 1) );
		
		float[] secondWallFooterLineVertices = {
				-100f, 1.3f, -100,		// V0
				-100f, 1.3f, -99.7f,	// V1
				0, 1.3f, -99.7f,		// V2
				0, 1.3f, -100			// V3
		};
		wallModel 	= loader.loadToVAO( secondWallFooterLineVertices, textureCoords, normals, indices );
		wall	= new TexturedModel( wallModel, new ModelTexture( loader.loadTexture("wall4") ) );
		walls.add( new Entity( wall, new Vector3f( 0, 0, 0 ), 0, 0, 0, 1) );
		
		// Third Wall
		float[] thirdWallVertices = {
				0, 50, -100,			// V0
				0, 0, -100,				// V1
				0, 0, 0,				// V2
				0, 50, 0				// V3
		};
		wallModel 	= loader.loadToVAO( thirdWallVertices, textureCoords, normals, indices );
		wall	= new TexturedModel( wallModel, new ModelTexture( loader.loadTexture("wall2") ) );
		walls.add( new Entity( wall, new Vector3f( 0, 0, 0 ), 0, 0, 0, 1) );
		
		float[] thirdWallFooterVertices = {
				-.3f, 1.3f, -100,		// V0
				-.3f, 0, -100,			// V1
				-.3f, 0, 0,				// V2
				-.3f, 1.3f, 0			// V3
		};
		wallModel 	= loader.loadToVAO( thirdWallFooterVertices, textureCoords, normals, indices );
		wall	= new TexturedModel( wallModel, new ModelTexture( loader.loadTexture("black") ) );
		walls.add( new Entity( wall, new Vector3f( 0, 0, 0 ), 0, 0, 0, 1) );
		
		float[] thirdWallFooterLineVertices = {
				0, 1.3f, -100,			// V0
				-.3f, 1.3f, -100,		// V1
				-.3f, 1.3f, 0,			// V2
				0, 1.3f, 0				// V3
		};
		wallModel 	= loader.loadToVAO( thirdWallFooterLineVertices, textureCoords, normals, indices );
		wall	= new TexturedModel( wallModel, new ModelTexture( loader.loadTexture("wall4") ) );
		walls.add( new Entity( wall, new Vector3f( 0, 0, 0 ), 0, 0, 0, 1) );
		
		// Fourth Wall
		float[] fourthWallVertices = {
				-100, 50, -100,			// V0
				-100, 0, -100,			// V1
				-100, 0, 0,				// V2
				-100, 50, 0				// V3
		};
		wallModel 	= loader.loadToVAO( fourthWallVertices, textureCoords, normals, indices );
		wall	= new TexturedModel( wallModel, new ModelTexture( loader.loadTexture("wall2") ) );
		walls.add( new Entity( wall, new Vector3f( 0, 0, 0 ), 0, 0, 0, 1) );
		
		// Fourth Wall Footer
		float[] fourthWallFooterVertices = {
				-99.7f, 1.3f, -99.7f,	// V0
				-99.7f, 0, -99.7f,		// V1
				-99.7f, 0, 0,			// V2
				-99.7f, 1.3f, 0			// V3
		};
		wallModel 	= loader.loadToVAO( fourthWallFooterVertices, textureCoords, normals, indices );
		wall	= new TexturedModel( wallModel, new ModelTexture( loader.loadTexture("black") ) );
		walls.add( new Entity( wall, new Vector3f( 0, 0, 0 ), 0, 0, 0, 1) );
		
		float[] fourthFooterLineVertices = {
				-100, 1.3f, -99.7f,		// V0
				-100f, 1.3f, 0,			// V1
				-99.7f, 1.3f, 0,		// V2
				-99.7f, 1.3f, -99.7f,	// V3
		};
		
		wallModel 	= loader.loadToVAO( fourthFooterLineVertices, textureCoords, normals, indices );
		wall	= new TexturedModel( wallModel, new ModelTexture( loader.loadTexture("wall4") ) );
		walls.add( new Entity( wall, new Vector3f( 0, 0, 0 ), 0, 0, 0, 1) );
		
		return walls;
		
	}
	
	private static boolean 	tut1 	= false,
							tut2 	= false,
							tut3 	= false,
							finish 	= false;
	
	private static void learnGame() {
		
		if( finish ) {
			if(projectile.getOpacity() == 0) {
				guis.clear();
				return;
			}
		}
		
		if( Mouse.isButtonDown(0) ) {
			tut1 = true;
			lastFrameTime = getCurrentTime();
			decreaseOpacity( cameraTut2, .02f );
			decreaseOpacity( projectile, .02f );
		}
		
		if( 	Keyboard.isKeyDown( Keyboard.KEY_W ) || Keyboard.isKeyDown( Keyboard.KEY_S ) ||
				Keyboard.isKeyDown( Keyboard.KEY_D ) || Keyboard.isKeyDown( Keyboard.KEY_A ) ) {
			tut2 = tut1;
			decreaseOpacity( projectile, .02f );
			if( tut2 )
				lastFrameTime = getCurrentTime();
		}
		
		if( Keyboard.isKeyDown( Keyboard.KEY_SPACE ) || Keyboard.isKeyDown( Keyboard.KEY_C ) ) {
			tut3 = tut2;
			finish = tut3;
		}
		
		if( tut1 && !tut2 ) {
			if( !period( 1 ) ) {
				decreaseOpacity( blackBg, .02f );
				decreaseOpacity( welcome, .02f );
				decreaseOpacity( cameraTut, .02f );
			} else {
				increaseOpacity( blackBg, .02f );
				if( blackBg.getOpacity() >= .8f )
					increaseOpacity( cameraTut2, .02f );
			}
		}
		
		if( tut2 && !tut3 ) {
			if( !period( 1 ) ) {
				decreaseOpacity( blackBg, .02f );
				decreaseOpacity( cameraTut2, .02f );
			} else {
				increaseOpacity( blackBg, .02f );
				if( blackBg.getOpacity() >= .8f )
					increaseOpacity( projectile, .02f );
			}
		}
		
		if( tut3 ) {
			decreaseOpacity( blackBg, .02f );
			decreaseOpacity( projectile, .02f );
		}
		
		if( period(3) && !tut1 ) {
			decreaseOpacity( welcome, .02f );
		}
		
		if( period( 4 ) && !tut1 ) {
			increaseOpacity( cameraTut, .02f );
		}
		
	}

	private static void decreaseOpacity( GuiTexture gui, float value ) {
		gui.setOpacity( gui.getOpacity() > 0 ? ( gui.getOpacity() - value ) : 0 );
	}
	private static void increaseOpacity( GuiTexture gui, float value ) {
		gui.setOpacity( gui.getOpacity() < 1 ? ( gui.getOpacity() + value ) : 1 );
	}
	
	private static float getCurrentTime() {
		return ( (float) ( Sys.getTime() * 1000 / Sys.getTimerResolution() ) / 1000 );
	}
	
	private static boolean period( float seconds ) {
		if( getCurrentTime() - lastFrameTime >= seconds )
			return true;
		return false;
	}
	
	private static boolean materialPeriod( float seconds ) {
		if( getCurrentTime() - lastChangeFrameTime >= seconds )
			return true;
		return false;
	}
	
	private static boolean savePeriod( float seconds ) {
		if( getCurrentTime() - lastFrameTimeDataChecker >= seconds )
			return true;
		return false;
	}
	
	private static boolean collisionPeriod( float seconds, float lastCollisionTime ) {
		
		if( getCurrentTime() - lastCollisionTime >= seconds )
			return true;
		return false;
	}
	
	private static float distanceFromCamera( Vector3f p1, Vector3f p2 ) {
		return (float) ( Math.sqrt( Math.pow( p1.x - p2.x , 2 ) + Math.pow( p1.y - p2.y , 2 ) + Math.pow( p1.z - p2.z , 2 ) ) );
	}
	
	public static void setLightStatus( boolean status ) {
		lightsStatus = status;
	}
	
	public static void setSoundStatus( boolean status ) {
		soundStatus = status;
	}
	
	public static void setMaterial( String objectMaterial ) {
		material = objectMaterial;
	}

}
