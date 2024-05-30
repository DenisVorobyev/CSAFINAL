import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

/**
 * this class handles loading of all the shaders, along with the creation of uniforms - memory where buffers will be put.
 */
public class ShaderProgram {

    private final int programId;

    private int vertexShaderId;

    private int fragmentShaderId;

    private final Map<String, Integer> uniforms;

    /**
     * creates a new shader
     * @throws Exception if there is an error
     */
    public ShaderProgram() throws Exception {
        programId = glCreateProgram();
        uniforms = new HashMap<>();
        if (programId == 0) {
            throw new Exception("Could not create Shader");
        }
    }

    /**
     * creates the vertex shader
     * @param shaderCode the code for the shader as a raw string
     * @throws Exception if file cannot be found
     */
    public void createVertexShader(String shaderCode) throws Exception {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    /**
     * creates the fragment shader
     * @param shaderCode the code for the shader as a raw string
     * @throws Exception if file not found
     */
    public void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    /**
     * allocate memory for the uniform, and put the uniform there
     * @param uniformName name for the uniform, as read in shaders
     * @throws Exception if could not allocate memory
     */
    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) {
            throw new Exception("Could not find uniform: " + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    /**
     * set the uniform, allowing for the shader to read data.
     * @param uniformName the uniform for which to write to
     * @param value the value to write to the uniform as a matrix
     */
    public void setUniform(String uniformName, Matrix4f value) {
        // Dump the matrix into a float buffer
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }

    /**
     * helper method to create the shaders
     * @param shaderCode raw text code of shader
     * @param shaderType what type of shader, as an enum
     * @return the id of the shader
     * @throws Exception if unable to create the shader
     */
    protected int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0) {
            throw new Exception("Error creating shader. Type: " + shaderType);
        }

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programId, shaderId);

        return shaderId;
    }

    /**
     * links this shader to the render pipeline
     * @throws Exception if unable to link
     */
    public void link() throws Exception {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
        }

        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId);
        }
        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId);
        }

        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
        }

    }

    /**
     * tells opengl to use this shader
     */
    public void bind() {
        glUseProgram(programId);
    }

    /**
     * tells opengl to stop using the shader
     */
    public void unbind() {
        glUseProgram(0);
    }

    /**
     * cleanup after shader, free memory.
     */
    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }
    }
}