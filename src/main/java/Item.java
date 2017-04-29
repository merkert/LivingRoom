import org.joml.Vector3f;

/**
 * Created by merkelu on 25.04.2017.
 */
public class Item {

    private final Mesh mesh;
    private Vector3f position;
    private float scale;
    private Vector3f rotation;

    public Item(Mesh mesh){
        this.mesh = mesh;
        position = new Vector3f(0,0,0);
        scale = 1;
        rotation = new Vector3f(0,0,0);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
