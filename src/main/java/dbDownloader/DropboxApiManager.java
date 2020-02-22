package dbDownloader;

import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

//This class is used to manage the api calls on dropbox

public class DropboxApiManager {
    protected DbxClientV2 client;
    protected FullAccount account;

    public DropboxApiManager () {}

    //This method return a connection with my own dropbox account using my own authorization file
    public void openConnection()  {
        // Read auth info file.
        String argAuthFile = "/home/andrea/IdeaProjects/auth.json";
        DbxAuthInfo authInfo;
        try {
            authInfo = DbxAuthInfo.Reader.readFromFile(argAuthFile);
        }
        catch (JsonReader.FileLoadException ex) {
            System.err.println("Error loading <auth-file>: " + ex.getMessage());
            System.exit(1); return;
        }
        //Create client
        DbxRequestConfig requestConfig = new DbxRequestConfig("dropbox");
        this.client = new DbxClientV2(requestConfig, authInfo.getAccessToken());
        try {
            this.account = client.users().getCurrentAccount();
        } catch (DbxException e) {
            System.err.println("Error during connection to the server: check out your access token");
            System.exit(0);
        }
        System.out.println("Connected to the user ==> " + account.getName().getDisplayName());
    }
}
