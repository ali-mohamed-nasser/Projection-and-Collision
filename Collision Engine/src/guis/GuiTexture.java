package guis;

import org.lwjgl.util.vector.Vector2f;

public class GuiTexture {
	
	private int texture;
	private Vector2f position;
	private Vector2f scale;
	private float opacity = 1;
	
	public GuiTexture( int texture, Vector2f position, Vector2f scale, float opacity ) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
		this.opacity = opacity;
	}
	
	public GuiTexture( int texture, Vector2f position, Vector2f scale ) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
	}

	public int getTexture() {
		return texture;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}

	public float getOpacity() {
		return opacity;
	}
	
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}
	
}
