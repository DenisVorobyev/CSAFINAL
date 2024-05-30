package org.lwjglb.engine;

import org.joml.Vector3f;

/**
 * an instance in the world
 */
public class Instance {
    private final Vector3f position;
    private final Vector3f rotation;
    private float scale;

    /**
     * creates a blank instance
     */
    public Instance(){
        position = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
        scale = 1;
    }

    /**
     * get position of instance
     * @return position
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * returns rotation of instance
     * @return rotation
     */
    public Vector3f getRotation() {
        return rotation;
    }

    /**
     * returns scale of instance
     * @return scale
     */
    public float getScale() {
        return scale;
    }

    /**
     * sets the position manually
     * @param x x value
     * @param y y value
     * @param z z value
     */
    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    /**
     * set position with a vector
     * @param pos vector of position
     */
    public void setPosition(Vector3f pos) {
        position.set(pos);
    }

    /**
     * increments the position manually
     * @param x x value
     * @param y y value
     * @param z z value
     */
    public void move(float x, float y, float z) {
        position.x += x;
        position.y += y;
        position.z += z;

    }

    /**
     * increments the position by a vector
     * @param pos vector
     */
    public void move(Vector3f pos) {
        move(pos.x, pos.y, pos.z);
    }

    /**
     * incriment the rotation of the instance manually
     * @param x value
     * @param y value
     * @param z value
     */
    public void rotate(float x, float y, float z) {
        rotation.x += x ;
        rotation.x %= 360;
        rotation.y += y;
        rotation.y %= 360;
        rotation.z += z;
        rotation.z %= 360;
    }

    /**
     * set the scale of the instance
     * @param scale scale to be set
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * manually set the rotation of the instance
     * @param x value
     * @param y value
     * @param z value
     */
    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }
}
