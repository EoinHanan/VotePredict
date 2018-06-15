package Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by EoinH on 15/05/2017.
 */
public abstract class Reader {
    //private String fileName;
    private File file;
    private Scanner in;
    Reader(){

    }
    Reader (String name) throws FileNotFoundException {
        file = new File (name + ".csv");
        in = new Scanner(file);
    }
}
