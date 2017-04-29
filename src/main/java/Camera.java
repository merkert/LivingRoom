import org.joml.Vector3f;

/**
 * Created by merkelu on 26.04.2017.
 */
public class Camera {

    public Camera(){
        position = new Vector3f(0,0,0);
        rotation = new Vector3f(0,0,0);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void updatePosition(float x, float y,float z){
        if(z != 0){
            position.x += (float)Math.sin(Math.toRadians(rotation.y))*-1.0f*z;
            position.z += (float)Math.cos(Math.toRadians(rotation.y))*z;
        }
        if(x != 0){
            position.x += (float)Math.sin(Math.toRadians(rotation.y-90))*-1.0f*x;
            position.z += (float)Math.cos(Math.toRadians(rotation.y-90))*x;
        }
        position.y += y;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void moveRotation(Vector3f rotation){
        this.rotation.add(rotation);
    }

    private Vector3f position;
    private Vector3f rotation;
}
