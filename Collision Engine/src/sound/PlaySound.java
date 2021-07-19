package sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class PlaySound {

	public static synchronized void playSound( final String file, float level ) {
	
		new Thread(new Runnable() {
			
			public void run() {
				
				try {
					
					Clip clip = AudioSystem.getClip(); 
					
					File soundFile = new File( "res/audio/" + file );
			        AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundFile);
			        
			        clip.open(inputStream);
			        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			        gainControl.setValue( level );

					clip.start();
					
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
				
			}
			
		}).start();
	}
	
}