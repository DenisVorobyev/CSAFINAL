import org.lwjglb.engine.Camera;
import org.lwjglb.engine.GameObject;

import java.util.HashMap;
import java.util.Map;

/**
 * a class used for storing all rendered objects in the scene
 */
public class RenderScene {
    private final Map<String, GameObject> Objectmap;
    public final Camera camera;

    /**
     * create a new blank scene
     */
    public RenderScene() {
        Objectmap = new HashMap<String, GameObject>();
        camera = new Camera();
    }

    /**
     * adds an gameObject to the scene
     * @param objectName the gameObject which we want to add's name by which it will be referenced
     * @param object the gameObject
     */
    public void addObject(String objectName, GameObject object) {
        Objectmap.put(objectName, object);
    }

    /**
     * get all objects
     * @return all objects
     */
    public Map<String, GameObject> getObjectmap() {
        return Objectmap;
    }

    /**
     * gets a specific object
     * @param objectName the name of the object you want
     * @return object of that name
     */
    public GameObject getObject(String objectName) {
        return Objectmap.get(objectName);
    }
}
