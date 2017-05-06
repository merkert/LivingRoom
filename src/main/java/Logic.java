import javafx.scene.effect.Light;
import org.joml.Vector2f;
import org.joml.Vector3f;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

/**
 * Created by merkelu on 24.04.2017.
 */
public class Logic {

    private double time = 0;
    private Renderer renderer;
    private List<Item> items;
    private SkyBox skyBox;
    private Camera camera;
    private Vector3f cameraInc;
    private float cameraMove;
    private float mouseSensitivity;
    private PointLight[] pointLights;
    private Vector3f ambientLight;
    private DirectionalLight sun;
    private SpotLight[] spotLights;
    private Fog fog;

    public Logic(){
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
        cameraMove = 0.02f;
        mouseSensitivity = 0.1f;
        items = new ArrayList<>();
    }

    public void init() throws Exception {
        renderer.init();
        Item item;

        Mesh mesh = OBJLoader.loadMesh("/model/couch.obj");
        item = new Item(mesh,new Vector3f(0.666f,0.023f,0.015f),0.0f);
        item.setScale(0.01f);
        items.add(item);

        mesh = OBJLoader.loadMesh("/model/grass.obj");
        item = new Item(mesh,"./Textures/grass.jpg",0.0f);
        item.setScale(0.01f);
        items.add(item);

        mesh = OBJLoader.loadMesh("/model/fireplace.obj");
        item = new Item(mesh,new Vector3f(0.350000f, 0.350000f, 0.350000f),0.0f);
        item.setScale(0.01f);
        items.add(item);

        mesh = OBJLoader.loadMesh("/model/lamp.obj");
        item = new Item(mesh,new Vector3f(0.588000f, 0.588000f, 0.588000f),0.0f);
        item.setScale(0.01f);
        items.add(item);

        mesh = OBJLoader.loadMesh("/model/tv.obj");
        item = new Item(mesh,new Vector3f(0.003900f, 0.003900f, 0.003900f),0.0f);
        item.setScale(0.01f);
        items.add(item);

        mesh = OBJLoader.loadMesh("/model/floor.obj");
        item = new Item(mesh,"./Textures/boden.jpg",0.0f);
        item.setScale(0.01f);
        items.add(item);

         mesh = OBJLoader.loadMesh("/model/house.obj");
        item = new Item(mesh,new Vector3f(0.640000f, 0.640000f, 0.640000f),0.0f);
        item.setScale(0.01f);
        items.add(item);

        mesh = OBJLoader.loadMesh("/model/table.obj");
        item = new Item(mesh,new Vector3f(0.8f, 0.8f, 0.8f),0.0f);
        item.setScale(0.01f);
        items.add(item);

        ambientLight = new Vector3f(0.5f,0.5f,0.5f);

        Vector3f lightColour = new Vector3f(0.5f, 0.5f, 0.5f);
        Vector3f lightPosition = new Vector3f(0,5000,0);
        float lightIntensity = 0.0f;

        PointLight pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0f, 0f, 0.0f);
        pointLight.setAttenuation(att);
        pointLights = new PointLight[]{pointLight};

        // Spot Light
        att = new PointLight.Attenuation(0.0f, 0.0f, 0.0f);
        pointLight.setAttenuation(att);
        Vector3f coneDir = new Vector3f(0, 0, -1);
        float cutoff = (float) Math.cos(Math.toRadians(140));
        SpotLight spotLight = new SpotLight(pointLight, coneDir, cutoff);
        spotLights = new SpotLight[]{spotLight};

        sun = new DirectionalLight(lightColour,new Vector3f(0,1,0), 1.0f);

        skyBox = new SkyBox("/model/world.obj","./Textures/skydome.jpg");
        skyBox.setScale(4);
        fog = new Fog(true, new Vector3f(0.3f, 0.3f, 0.3f), 0.15f);
    }

    public void render(Window window){
        window.input();
        update(window);
        renderer.render(window,items,camera, ambientLight, pointLights,spotLights,sun,fog);
        renderer.renderSkyBox(window,camera,ambientLight,skyBox);
    }

    public void update(Window window) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 1;
        }

        if (window.isKeyPressed(GLFW_KEY_0)) {
            cameraMove += 0.001f;
        }

        if (window.isKeyPressed(GLFW_KEY_1)) {
            cameraMove -= 0.001f;
        }

        if (window.isKeyPressed(GLFW_KEY_2)) {
            mouseSensitivity += 0.01f;
        }

        if (window.isKeyPressed(GLFW_KEY_3)) {
            mouseSensitivity -= 0.01f;
        }
        camera.updatePosition(cameraInc.x * cameraMove, cameraInc.y * cameraMove, cameraInc.z * cameraMove);
        if (window.isRightButtonPressed()) {
            Vector2f rotVec = window.getDisplVec();
            camera.moveRotation(new Vector3f(rotVec.x * mouseSensitivity, rotVec.y * mouseSensitivity, 0));
        }
    }
}
