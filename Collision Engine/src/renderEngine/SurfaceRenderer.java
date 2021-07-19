package renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import shaders.SurfaceShader;
import surfaces.Surface;
import textures.ModelTexture;
import toolbox.Maths;

public class SurfaceRenderer {

	private SurfaceShader shader;
	
	public SurfaceRenderer( SurfaceShader shader, Matrix4f projectionMatrix ) {
		
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix( projectionMatrix );
		shader.stop();
		
	}
	
	public void render( List<Surface> terrians ) {
		for( Surface terrian:terrians ) {
			prepareTerrian(terrian);
			loadModelMatrix(terrian);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrian.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindTexturedModel();
		}
	}
	
	public void prepareTerrian( Surface terrian ) {
		RawModel model = terrian.getModel();
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture texture = terrian.getTexture();
		shader.loadShineVariables( texture.getShineDamper(), texture.getReflectivity() );
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
	}
	
	public void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	public void loadModelMatrix( Surface terrian ) {
		Matrix4f transforamtionMatrix = Maths.createTransformationMatrix( new Vector3f( terrian.getX(), terrian.getY(), terrian.getZ() ), 0, 0, 0, 1 );
		shader.loadTransformationMatrix( transforamtionMatrix );
	}
	
}
