package dbDownloader;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class mainDbDownloader {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println();
            System.out.println();
            //Presentation
            System.out.println(ANSI.GREEN + "*************************************");
            System.out.println("****" + ANSI.RESET + ANSI.PURPLE + "Welcome to Dropbox Downloader" +
                    ANSI.RESET + ANSI.GREEN + "****");
            System.out.println("*************************************" + ANSI.RESET);
            System.out.println();

            //Explanation
            System.out.println("By specifying a " + ANSI.RED + " RELATIVE " + ANSI.RESET + "path to your " +
                    "dropbox fyle system and your local manchine file system this program will download " +
                    "to the specified path all the" +
                    " files you don't have yet in it");
            System.out.println();

            /*System.out.println(ANSI.PURPLE + "===> " + ANSI.RESET
                    + " Insert your access token to dropbox:  ");

            String access_token = reader.readLine();

            System.out.println();*/


            System.out.println(ANSI.PURPLE + "===> " + ANSI.RESET
                    + " Insert the RELATIVE path to the dropbox folder you want to download:  ");

            String dropboxPath = reader.readLine().toLowerCase();

            System.out.println();

            System.out.println(ANSI.PURPLE + "===>" + ANSI.RESET
                            + " Insert the RELATIVE path to the machine " +
                    "folder where you want the downloaded files to go:  ");

            String localManchinePath = reader.readLine();

            System.out.println();

            System.out.println(ANSI.YELLOW + "Program will start running" + ANSI.RESET);
            System.out.println();

            DropboxApiManager dropboxApiManager = new DropboxApiManager();
            DropboxFileManager dropboxFileManager = new DropboxFileManager(dropboxApiManager);
            dropboxFileManager.download(dropboxPath, localManchinePath);

            System.out.println();
            System.out.println( ANSI.CYAN + "Program ended" + ANSI.RESET);
            System.out.println();
            System.exit(0);
        }
    }
}
