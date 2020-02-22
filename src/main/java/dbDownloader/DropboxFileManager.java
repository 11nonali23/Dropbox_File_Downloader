package dbDownloader;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// This class is used to manage the file on dropbox

public class DropboxFileManager {
    private DropboxApiManager dropboxApiManager;
    private ComputerFileManager computerFileManager;

    public DropboxFileManager(DropboxApiManager dropboxApiManager) {
        this.dropboxApiManager = dropboxApiManager;
        dropboxApiManager.openConnection();

        this.computerFileManager = new ComputerFileManager();
    }

    //This method download files that has only an extension
    public void download(String dropboxPath, String localRelativePath){

        ArrayList<Metadata> files = (ArrayList<Metadata>) getFiles(dropboxPath);

        Stack<String> directories = null;
        String newFolder = null;

        if(files != null) {

            //This stack will store all the new directories to make the recursive call
            directories = new Stack<>();

            //getting the dropbox parent directory
            newFolder = StringParser.parseDirectoryName(dropboxPath);
            //create the new folder in which it will store files
            computerFileManager.createDirectory(localRelativePath, newFolder);

            //change the download location file to the new directory
            localRelativePath += File.separator + newFolder;

            //download files into new folder
            for (Metadata m : files) {
                if (StringParser.hasExtension(m.getName())) {

                    if (downloadFile(m.getPathLower(), localRelativePath + File.separator + m.getName()))
                        System.out.println( ANSI.GREEN + "File " + m.getName() +
                                " Created at ==> " + localRelativePath + ANSI.RESET);
                    else
                        System.out.println(ANSI.RED + "File " + localRelativePath + File.separator +
                                m.getName() + " already exists" + ANSI.RESET);

                }
                else
                    directories.push(m.getName());
            }
        }
        //if there is some directories it will call recursively the method on them
        if (directories != null && newFolder != null)
            while (!directories.isEmpty()){
                String newDirectory = directories.pop();
                System.out.println();

                System.out.println("Program will recursively go into directory: " + newDirectory);

                System.out.println();

                download(dropboxPath + "/" + newDirectory,
                localRelativePath);
            }

    }

    //This method is used to download files from dropbox and create them locally-->
    //returns true if file is created properly, false otherwise.

    private synchronized boolean downloadFile(String dbxPath, String localRelativePath) {
        //ask to computer file manager to create a new file
        if(!computerFileManager.createNewFile(localRelativePath))
            return false;

        //Create a stream that will carry data into the new file
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream(computerFileManager.root_home + localRelativePath);
        } catch (FileNotFoundException e) {
            System.err.println("Error creating output stream: check if the path is correct");
            return false;
        }

        //Download the dropbox file and carry data into the new file created via output stream
        try {
            dropboxApiManager.client.files().downloadBuilder(dbxPath).download(outputStream);
        } catch (DbxException | IOException e) {
            System.err.println("Error during the file download: check that dropbox path is correct");
            return false;
        }
        return true;
    }

    //This method retrive paths from the relative path. If the path is incorrect returns null.
    private synchronized List<Metadata> getFiles(String relativePath)  {
        ListFolderResult result = null;
        try {
            result = dropboxApiManager.client.files().listFolder("/" + relativePath);
        } catch (DbxException e) {
            System.err.println("Error getting the files from dropbox " +
                    "make sure that the path " + ANSI.GREEN + relativePath +ANSI.RESET +  " is correct");
        }
        if(result != null)
            return result.getEntries();
        else
            return null;
    }
}
