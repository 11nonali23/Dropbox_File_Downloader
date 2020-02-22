package dbDownloader;

public class StringParser {

    //This method check if a file has an extension
    public static synchronized boolean hasExtension (String s) {
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == '.')
                return true;
        return false;
    }

    //This method parse the dropBox path and take the last directory
    public static synchronized String parseDirectoryName(String dropboxPath) {

        StringBuilder directoryName = new StringBuilder();
        int iter = dropboxPath.length() - 1;

        while (dropboxPath.charAt(iter) != '/') {
            directoryName.append(dropboxPath.charAt(iter));
            iter--;
            if (iter == 0) {
                directoryName.append(dropboxPath.charAt(0));
                break;
            }
        }
        return directoryName.reverse().toString();
    }

}
