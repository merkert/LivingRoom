import com.sun.xml.internal.ws.api.ResourceLoader;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by merkelu on 24.04.2017.
 */
public class Logic {

    private double time = 0;
    private Renderer renderer;
    private List<Item> items;
    private Camera camera;
    private Vector3f cameraInc;
    private float cameraMove;
    private float mouseSensitivity;
    private PointLight pointLight;
    private Vector3f ambientLight;

    public Logic(){
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0,0,0);
        cameraMove = 0.02f;
        mouseSensitivity = 0.1f;
        items = new ArrayList<Item>();
    }

    public void init() throws Exception {
        renderer.init();
        Scanner scanner;
            InputStream in = Utils.class.getClass().getResourceAsStream("/model");
            scanner = new Scanner(in, "UTF-8");

            while(scanner.hasNext()){
                String filename = scanner.next();
                filename = filename.replace(".obj","");
                Mesh mesh = OBJLoader.loadMesh("/model/GRASS.obj");
                //Texture texture = Texture.loadTexture("./textures/grass.obj.PNG");
                //mesh.setTexture(texture);
                Item item = new Item(mesh);
                item.setScale(5000.0f);
                item.setPosition(new Vector3f(0,0,-2));
                items.add(item);
            }
        ambientLight = new Vector3f(0.5f, 0.5f, 0.5f);
        Vector3f lightColour = new Vector3f(1, 1, 1);
        Vector3f lightPosition = new Vector3f(0, 0, 1);
        float lightIntensity = 1.0f;
        pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);
    }

    public void render(Window window){
        window.input();
        update(window);
        renderer.render(window,items,camera, ambientLight, pointLight);
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
        float brightness = (float) Math.sin(Math.toRadians(time));
        ambientLight.set(brightness, brightness, brightness);
    }

    public void addtime(){
        time += 20;
        if(time >= 180){
            time = 180;
        }
    }

}
