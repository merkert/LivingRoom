import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by merkelu on 24.04.2017.
 */
public class Renderer {
    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;
    private ShaderProgram shaderProgram;
    private Transformation transformation;

    public Renderer(){
        transformation = new Transformation();
    }

    public void init() throws Exception{
        // Create shader
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/shader/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/shader/fragment.fs"));
        shaderProgram.link();

        shaderProgram.createUniform("projectionsMatrix");
        shaderProgram.createUniform("modelViewMatrix");
        shaderProgram.createUniform("texture_sampler");
        shaderProgram.createUniform("ambientLight");
        shaderProgram.createPointLightUniform("pointLight");
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, List<Item> items, Camera camera, Vector3f ambientLight,
                       PointLight pointLight){
        clear();
        glViewport(0, 0, window.getWidth(), window.getHeight());

        shaderProgram.bind();
        // Update projection Matrix
        Matrix4f projectionMatrix = transformation.getProjectionsMatrix(FOV, (float) window.getWidth()/window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setUniform("projectionsMatrix", projectionMatrix);

        Matrix4f viewMatrix = transformation.getViewMatrix(camera);
        shaderProgram.setUniform("ambientLight", ambientLight);
        shaderProgram.setUniform("texture_sampler", 0);
        PointLight currPointLight = new PointLight(pointLight);
        Vector3f lightPos = currPointLight.getPosition();
        Vector4f aux = new Vector4f(lightPos, 1);
        aux.mul(viewMatrix);
        lightPos.x = aux.x;
        lightPos.y = aux.y;
        lightPos.z = aux.z;
        shaderProgram.setUniform("pointLight", currPointLight);
        for (Item item : items) {
            Mesh mesh = item.getMesh();
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(item,viewMatrix);
            shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
            mesh.render();
        }

        shaderProgram.unbind();
    }
}
