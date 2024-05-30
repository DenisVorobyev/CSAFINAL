import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * a static class of utilities, so far its only for reading files.
 */
public class Utils {
    /**
     * returns the file as a string
     * @param fileName the file directory
     * @return the file string
     */
    public static String loadResource(String fileName) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = reader.readLine()) !=null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
            return shaderSource.toString();
        }catch(IOException e){
            System.err.println("Can't read file");
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
}
