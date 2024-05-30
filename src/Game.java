import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjglb.engine.graph.Cube;

import static org.lwjgl.glfw.GLFW.*;

/**
 * defines the games functionality
 */
public class Game implements IGameLogic{
    //the intended tick time - hopefully the frames render at a reasonable rate
    private final float tickSpeed = 100f/6f;
    private final Vector2f movementSpeed;
    private final Vector2f cameraSpeed;
    private float jumpSpeed;
    private Cube hitbox;
    private boolean grounded;

    private final RenderScene scene;

    /**
     * initializes everything to a default value
     */
    public Game(){
        this.scene = new RenderScene();
        this.movementSpeed = new Vector2f();
        this.cameraSpeed = new Vector2f();
        //i hope you know this line brought me pain and suffering. 3 hours just to realize i had to wait to initialize it first
        //THREE HOURS of a fatal error message
        //all libraries reinstalled, just for it to be this stupid line.
        //i hate you, opengl.
        this.hitbox = null;
        this.grounded = false;
    }

    /**
     * initialize all game objects. this is done here to avoid a GPU error :)
     * @throws Exception when objects are unable to initialize
     */
    @Override
    public void init() throws Exception {
        this.hitbox = new Cube(1,new Vector3f(0,0.5f,0));
        scene.addObject("rect", new Cube(1, new Vector3f(0,0.5f,-5f)));
        scene.addObject("rect2", new Cube(2, new Vector3f(-2f,1f,-5f)));
        scene.addObject("rect3", new Cube(100, new Vector3f(0,-50f,0)));
        //this ones gonna move! how fun!
        scene.addObject("rect4", new Cube(3, new Vector3f(-5f,1.5f,-5f)));
        scene.addObject("rect5", new Cube(4, new Vector3f(-10f,2f,0)));
    }

    /**
     * check all inputs, and change the proper values associated.
     * @param window on which the game is running
     */
    @Override
    public void input(Window window) {
        boolean upkeyPressed = window.isKeyPressed(GLFW_KEY_UP);
        boolean downkeyPressed = window.isKeyPressed(GLFW_KEY_DOWN);
        boolean leftkeyPressed = window.isKeyPressed(GLFW_KEY_LEFT);
        boolean rightkeyPressed = window.isKeyPressed(GLFW_KEY_RIGHT);
        boolean spacePressed = window.isKeyPressed(GLFW_KEY_SPACE);
        boolean wKeyPressed = window.isKeyPressed(GLFW_KEY_W);
        boolean sKeyPressed = window.isKeyPressed(GLFW_KEY_S);
        boolean aKeyPressed = window.isKeyPressed(GLFW_KEY_A);
        boolean dKeyPressed = window.isKeyPressed(GLFW_KEY_D);
        //here we will calculate the movement speed/direction.
        if (upkeyPressed){
            cameraSpeed.x += 1f;
        }if (downkeyPressed){
            cameraSpeed.x -= 1f;
        }if (leftkeyPressed){
            cameraSpeed.y -= 1f;
        }if (rightkeyPressed){
            cameraSpeed.y += 1f;
        }if(spacePressed && grounded){
            jumpSpeed = 0.5f;
        } else{
            jumpSpeed -= 0.025f;
        }

        if(wKeyPressed){
            movementSpeed.add(new Vector2f(0,-0.5f));
        }
        if(sKeyPressed){
            movementSpeed.add(new Vector2f(0,0.5f));
        }
        if(aKeyPressed){
            movementSpeed.add(new Vector2f(-0.5f,0));
        }
        if(dKeyPressed){
            movementSpeed.add(new Vector2f(0.5f,0));
        }
        jumpSpeed /= 1.2f;
        cameraSpeed.div(1.5f);
        movementSpeed.div(6f);
    }

    /**
     * updates the games physics
     * @param dt delta time
     * @param tt total time
     */
    @Override
    public void update(float dt, float tt) {
        dt /= tickSpeed;
        System.out.println(tt);
        // yeah so this cube's getting groovyyyyyy
        scene.getObject("rect5").setPosition(-10f, 2f, (float)Math.sin(tt / 5000) * 10);


        scene.camera.rotate(cameraSpeed.x * dt, cameraSpeed.y * dt,0);
        double cameraDir = Math.toRadians(scene.camera.getRotation().y);

        //rotate our vector by cameraDir and then apply
        Vector3f oldPos = new Vector3f(hitbox.getPosition());

        hitbox.move((float)(Math.cos(cameraDir) * movementSpeed.x - Math.sin(cameraDir) * movementSpeed.y) * dt, jumpSpeed * dt,(float)(Math.sin(cameraDir) * movementSpeed.x + Math.cos(cameraDir) * movementSpeed.y)* dt);
        grounded = false;
        float yDiff = Math.abs(hitbox.getPosition().y - oldPos.y);
        scene.getObjectmap().forEach((key,object)->{
            Vector3f col = hitbox.isColliding((Cube)object);
            if (!col.equals(0,0,0)){
                //System.out.println(col.y);
                if(Math.abs(col.y) <= yDiff){
                    hitbox.move(0,col.y,0);
                    if(col.y > 0){
                        grounded = true;
                    }
                }
                else{
                    //this means we arent colliding with a ceiling or the floor, so we assume it is a wall. thank god for easy cube calculations.
                    if(Math.abs(col.x) < Math.abs(col.z)){
                        hitbox.move(col.x,0,0);
                    }else{
                        hitbox.move(0,0,col.z);
                    }
                }
                //System.out.println(key);
            }
        });
        scene.camera.setPosition(new Vector3f(hitbox.getPosition()).add(new Vector3f(0,0.7f,0)));
    }

    /**
     * render all the scene's object
     * @param renderer the renderer to be used
     * @param window the window to be used
     * @throws Exception a GPU error
     */
    @Override
    public void render(Renderer renderer, Window window) throws Exception {
        renderer.render(scene, window);
    }
}
