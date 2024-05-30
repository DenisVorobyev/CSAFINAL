import org.joml.Matrix4f;
import static org.lwjgl.opengl.GL30.*;
import org.lwjglb.engine.graph.Transformation;

/**
 * a class which handles the rendering of the world every frame
 */
public class Renderer {
    private ShaderProgram shaderProgram;

    private static final float FOV = (float) Math.toRadians(80.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private Matrix4f projectionMatrix;

    private final Transformation transformation;

    /**
     * create a new renderer
     */
    public Renderer() {
        transformation = new Transformation();
    }

    /**
     * initialize the renderer, getting it ready for rendering.
     * @param window the window on which we will render
     * @throws Exception typically from shader error
     */
    public void init(Window window) throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("src\\vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("src\\fragment.fs"));
        shaderProgram.link();
        float aspectRatio = (float) window.getWidth() / window.getHeight();
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("worldMatrix");
        shaderProgram.createUniform("viewMatrix");
        glEnable(GL_DEPTH_TEST);
    }

    /**
     * Render the current frame
     * @param scene the scene which we want to render
     * @param window the window on which we are rendering
     */
    public void render(RenderScene scene, Window window) {
        shaderProgram.bind();

        // Update projection Matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);

        // Render each gameItem
        scene.getObjectmap().forEach((key,object) ->{
            /**
             * hey mr turner if you're reading this, i hope you know blood sweat and tears went into this.
             * z buffering more like sadness buffering
             * is it even worth it? i dont know anymore
             * z buffering is the end of me
             * why must it exist?
             * why is it?
             * why is?
             * why?
             * why cant the pixels understand what i want them to be?
             * is it done in the shader? today on confusion with denis!
             * HEYYYYYY OPENGL DOES IT FOR ME <33333
             * I LOVE MODERN TECHNOLOGY
             * NO BRUTE FORCE ALGORITHM TODAY
             */

            //System.out.println(object.getRotation());
            //this code below was poopy and doesnt work! must recode!
            //Matrix4f worldMatrix = scene.camera.getWorldMatrix(object, transformation);
            // Set world matrix for this item
            Matrix4f worldMatrix = transformation.getWorldMatrix(
                            object.getPosition(),
                            object.getRotation(),
                            object.getScale());
            //hey it works!
            Matrix4f viewMatrix = scene.camera.getCameraMatrix();
            shaderProgram.setUniform("viewMatrix", viewMatrix);
            shaderProgram.setUniform("worldMatrix", worldMatrix);
            // Render the mes for this game item
            object.getMesh().render();
        });

        shaderProgram.unbind();
    }

    /**
     * cleans up after the renderer on the gpu in case we want to delete it
     */
    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }

        glDisableVertexAttribArray(0);

        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);


        // Delete the VAO
        glBindVertexArray(0);
    }
}
