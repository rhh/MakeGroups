package make_groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Controller {

    private Model myClassRoom;

    public Controller(Model myClassRoom) {
        this.myClassRoom = myClassRoom;
    }

    public void readStudentsFromFile(File studentFile) throws FileNotFoundException, IOException {
        String line, name;
        myClassRoom.clearStudents();

        Scanner sc = new Scanner(studentFile);
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.contains("\"") && line.indexOf("\"") != line.lastIndexOf("\"")) {  // "..."
                name = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));                
                myClassRoom.addStudent(name, true);     // default: active
            }
        }

    }

}
