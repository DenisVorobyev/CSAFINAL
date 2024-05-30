package org.lwjglb.engine.graph;

import org.joml.Vector3f;
import org.lwjglb.engine.GameObject;

public class Cube extends GameObject {
    private final int size;
    private static final int[] indices = {
            0, 1, 3,
            3, 1, 2,
            0, 4, 7,
            7, 0, 3,
            0, 4, 5,
            5, 1, 0,
            2, 5, 1,
            5, 6, 2,
            2, 6, 7,
            7, 2, 3,
            4, 5, 6,
            7, 4, 6
    };
    private static final float[] colors = {
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
    };
    private static float[] positions(int size){
        float[] temp ={
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
        };
        for (int i = 0; i < temp.length; i++){
            temp[i] *= size;
        }
        return temp;
    }
    public Cube(int size){
        super(new Mesh(positions(size), colors, indices));
        this.size = size;
    }

    public Cube(int size, Vector3f position){
        super(new Mesh(positions(size), colors, indices));
        this.size = size;
        this.setPosition(position);
    }

    public Vector3f isColliding(Cube other){
        double halfSideA = this.size / 2.0;
        double halfSideB = other.size / 2.0;

        Vector3f thisPos = this.getPosition();

        double minXA = thisPos.x - halfSideA;
        double maxXA = thisPos.x + halfSideA;
        double minYA = thisPos.y - halfSideA;
        double maxYA = thisPos.y + halfSideA;
        double minZA = thisPos.z - halfSideA;
        double maxZA = thisPos.z + halfSideA;

        Vector3f otherPos = other.getPosition();

        double minXB = otherPos.x - halfSideB;
        double maxXB = otherPos.x + halfSideB;
        double minYB = otherPos.y - halfSideB;
        double maxYB = otherPos.y + halfSideB;
        double minZB = otherPos.z - halfSideB;
        double maxZB = otherPos.z + halfSideB;

        if (minXA >= maxXB || maxXA <= minXB ||
                minYA >= maxYB || maxYA <= minYB ||
                minZA >= maxZB || maxZA <= minZB) {
            // No overlap
            return new Vector3f(0f);
        }

        double overlapX = Math.min(maxXA, maxXB) - Math.max(minXA, minXB);
        double overlapY = Math.min(maxYA, maxYB) - Math.max(minYA, minYB);
        double overlapZ = Math.min(maxZA, maxZB) - Math.max(minZA, minZB);

        if (thisPos.x < otherPos.x) overlapX = -overlapX;
        if (thisPos.y < otherPos.y) overlapY = -overlapY;
        if (thisPos.z < otherPos.z) overlapZ = -overlapZ;

        return new Vector3f((float)(overlapX),(float)(overlapY),(float)(overlapZ));
    }



    public int getSize(){
        return size;
    }
}
