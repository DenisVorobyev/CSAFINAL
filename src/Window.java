import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import java.nio.IntBuffer;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * A class used for handling the glfw window creation and input saving.
 */
public class Window {
    private final int height;
    private final int width;
    protected long window;
    private final boolean[] keys;;

    /**
     * Creates a window from basic information
     * @param width the width of the window
     * @param height the height of the window
     * @param title the title of the window
     */
    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() ) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE); // the window will NOT be resizable. we are striclers over here.

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL) {
            //god forbid this be thrown
            throw new RuntimeException("Failed to create the GLFW window");
        }
        //500 so we dont accidentally encounter a 问题。
        keys = new boolean[500];
        this.init();
    }

    /**
     * initializes the window. nothing should be called before doing this, unless you want a nasty C error.
     */
    private void init(){
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        //everything blows up without this!
        GL.createCapabilities();

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) {
                glfwSetWindowShouldClose(window, true);
            }else if (action == GLFW_PRESS){
                keys[key] = true;
            }else if (action == GLFW_RELEASE){
                keys[key] = false;
            }
        });
    }

    /**
     * check if a key is pressed
     * @param key the key you are checking
     * @return if the key is pressed
     */
    public boolean isKeyPressed(int key){
        return keys[key];
    }


    /**
     * update the frame buffer, and poll for input events.
     */
    public void update(){
        glfwSwapBuffers(window); // swap the color buffers

        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }

    /**
     *
     * @return window height
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @return window width
     */
    public int getWidth() {
        return width;
    }


}
