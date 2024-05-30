import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;

/**
 * runs our game instance for better modularity
 */
public class GameEngine implements Runnable {
    private final Thread gameLoopThread;
    private final Window window;
    private final IGameLogic gameLogic;
    private long lastTick;
    private final Renderer renderer;
    private final long firstTick;


    /**
     * creates a new gameengine instance which runs a game
     * @param windowTitle title of window
     * @param width width of window
     * @param height height of window
     * @param gameLogic the game which will be run
     * @throws Exception if anything goes wrong in the process
     */
    public GameEngine(String windowTitle, int width, int height, IGameLogic gameLogic) throws Exception {
        gameLoopThread = new Thread(this, "GAME_LOOP_THREAD");
        window = new Window(width, height, windowTitle);
        lastTick = System.currentTimeMillis();
        this.gameLogic = gameLogic;
        renderer = new Renderer();
        renderer.init(window);
        firstTick = System.currentTimeMillis();
    }

    /**
     * clear the screen
     */
    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    /**
     * handle all input
     */
    protected void input() {
        gameLogic.input(window);
    }

    /**
     * step the physics engine once
     * @param interval delta time
     * @param currentTime total time
     */
    protected void update(float interval, long currentTime) {
        //this will get you window dragging tryhards! no phasing through the ground anymore
        if(interval > 100) {
            interval = 16;
        }
        gameLogic.update(interval, currentTime - firstTick);
    }

    /**
     * renders the world
     * @throws Exception if there is an opengl error
     */
    protected void render() throws Exception {
        clear();
        gameLogic.render(renderer, window);
        window.update();
    }

    /**
     * the engines main loop
     * @throws Exception when any method throws an error
     */
    private void loop() throws Exception {
        while(!glfwWindowShouldClose(window.window) ) {
            input();
            long t = System.currentTimeMillis();
            long delta = t - lastTick;
            lastTick = t;
            update(delta,t);
            render();
        }
    }

    /**
     * run the game, after creating it.
     */
    @Override
    public void run(){
        try {
            gameLogic.init();
            loop();
        } catch (Exception excp) {
            excp.printStackTrace();
        }finally{
            renderer.cleanup();
        }
    }

}
