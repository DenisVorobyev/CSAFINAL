package org.lwjglb.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * a class used for handling the cameras location and rotation
 */
public class Camera extends Instance {
    /**
     * create an instance of the camera, an object with a position and the ability to get a viewmatrix from it.
     */
    public Camera(){
        super();
    }

    /**
     * get the viewmatrix from the camera from its current location
     * @return a viewmatrix
     */
    public Matrix4f getCameraMatrix(){
        Vector3f cameraPos = this.getPosition();
        Vector3f rotation = this.getRotation();
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        // First do the rotation so camera rotates over its position, x is negative so it acts right >:3
        viewMatrix.rotate((float)Math.toRadians(-rotation.x), new Vector3f(1, 0, 0)).rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return viewMatrix;
    }
}
