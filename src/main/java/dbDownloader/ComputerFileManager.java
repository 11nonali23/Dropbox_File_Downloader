package dbDownloader;

import java.io.File;
import java.io.IOException;

//NEED TO IMPLEMENT RELATIVE PATH

public class ComputerFileManager {
    protected final String seprator = File.separator;
    protected final String root_home = System.getProperty("user.home") + seprator;


    public File[] listDirectories(String relativePath) {
        File directories[];
        directories = new File(relativePath).listFiles(file -> file.isDirectory() && !file.isHidden());
        return directories;
    }

    //Create a new file specified a relative path -->
    //return true if file is created, false otherwise
    public boolean createNewFile (String relativePath)  {
        File file = new File(root_home + relativePath);

        try {
            if(file.createNewFile())
                return true;
            else
                return false;

        } catch (IOException e) {
            System.out.println("Error during the creation of the file");
            e.printStackTrace();
            return false;
        }
    }

    //Create a new directory specified a realtive path
    //return true if directory is created, false otherwise
    public boolean createDirectory(String relativePath, String directoryName) {
        String path = root_home + relativePath + File.separator + directoryName;
        File file = new File(path);

        if(file.mkdir()) {
            System.out.println("Directory " + directoryName + " created successfully at===> " + path);
            return true;
        }
        else {
            System.out.println("Directory " + path + "already exists");
            return false;
        }
    }
}
