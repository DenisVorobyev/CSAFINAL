package org.lwjglb.engine;
import org.lwjglb.engine.graph.Mesh;

/**
 * an instance which has a mesh associated with it
 */
public class GameObject extends Instance {
    private final Mesh mesh;

    /**
     * creates the instance with a mesh
     * @param mesh the mesh to be associated with the instance
     */
    public GameObject(Mesh mesh){
        super();
        this.mesh = mesh;
    }

    /**
     * get mesh
     * @return mesh
     */
    public Mesh getMesh(){
        return mesh;
    }




}
