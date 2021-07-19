package textures;

public class SurfaceTexturePack {

	private SurfaceTexture backgroundTexture;
	private SurfaceTexture rTexture;
	private SurfaceTexture gTexture;
	private SurfaceTexture bTexture;
	
	public SurfaceTexturePack(SurfaceTexture backgroundTexture, SurfaceTexture rTexture, SurfaceTexture gTexture, SurfaceTexture bTexture) {
		this.backgroundTexture = backgroundTexture;
		this.rTexture = rTexture;
		this.gTexture = gTexture;
		this.bTexture = bTexture;
	}

	public SurfaceTexture getBackgroundTexture() {
		return backgroundTexture;
	}

	public SurfaceTexture getrTexture() {
		return rTexture;
	}

	public SurfaceTexture getgTexture() {
		return gTexture;
	}

	public SurfaceTexture getbTexture() {
		return bTexture;
	}
	
}
