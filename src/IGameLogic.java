/**
 * interface used by game instances
 */
public interface IGameLogic {
    /**
     * initilize the game
     * @throws Exception
     */
    void init() throws Exception;

    /**
     * manage all input
     * @param window
     */
    void input(Window window);

    /**
     * update the games physics
     * @param interval
     * @param tt
     */
    void update(float interval, float tt);

    /**
     * render the world
     * @param renderer
     * @param window
     * @throws Exception
     */
    void render(Renderer renderer, Window window) throws Exception;
}