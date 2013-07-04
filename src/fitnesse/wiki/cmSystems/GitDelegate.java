package fitnesse.wiki.cmSystems;

import fitnesse.ComponentFactory;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;


public class GitDelegate {

    private String MACHINE_NAME = null;
    private static String COMMIT_TOKEN = null;
    private String ADD = null;
    private String DELETE = null;
    private String COMMIT = null;
    private String FETCH = null;
    private String PUSH = null;
    private String MERGE = null;
    private CommandExecutor executor = new CommandExecutor();

    public void update(String file) throws Exception {
        if (ADD == null) ADD = gitProperty("add","add");
        executor.voidExec(gitPath() + " "+ ADD +" " + file);
    }

    public void delete(String file) throws Exception {
        if (DELETE == null) DELETE = gitProperty("delete","rm -rf --cached");
    	executor.voidExec(gitPath() +  " " + DELETE + " " + file);
    }

    public void commit() throws Exception {
        if (COMMIT == null) COMMIT = gitProperty("commit","commit");
        String newToken = freshToken(); 
        executor.voidExec(gitPath() + " " + COMMIT + " " + amendOnSameToken(newToken) +"--message \"" + commitMessage() + "\"");
    }

    public void fetch() throws Exception {
    	if (FETCH == null) FETCH = gitProperty("fetch","");
    	if (FETCH.equals("")) {
    		return; // fetch is optional
    	}
    	else {
    		executor.voidExec(gitPath() +  " " + FETCH);
    	};
    }
    
    public void merge() throws Exception {
    	if (MERGE == null) MERGE = gitProperty("merge","");
    	if (MERGE.equals("")) {
    		return; // merge is optional
    	}
    	else {
    		executor.voidExec(gitPath() +  " " + MERGE);
    	};
    }

    public void push() throws Exception {
    	if (PUSH == null) PUSH = gitProperty("push","");
    	if (PUSH.equals("")) {
    		return; // merge is optional
    	}
    	else {
    		executor.voidExec(gitPath() +  " " + PUSH);
    	};
    }
    protected String amendOnSameToken(String newToken){
    	if (COMMIT_TOKEN == null) {
    		COMMIT_TOKEN = newToken;
    		return "";
    	}
    	else if (newToken.equals(COMMIT_TOKEN))
    		return "--amend ";
    	else
    		return "";
    }

    protected String commitMessage() {
        try {
            if (MACHINE_NAME == null) MACHINE_NAME = InetAddress.getLocalHost().getHostName();
            if (COMMIT_TOKEN == null) COMMIT_TOKEN = freshToken();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "FitNesse auto-commit from " + MACHINE_NAME + " with token [" + COMMIT_TOKEN + "]";
    }

    private String freshToken() {
        return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG).format(new Date());
    }

    // get git.<property> from plugins.config
    // if property is not found default value used
    protected String gitProperty(String property, String sDefault) {
        try {
            FileInputStream inputStream = new FileInputStream(ComponentFactory.PROPERTIES_FILE);
            Properties properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
            String gitPprop = properties.getProperty("git."+property);
            return gitPprop != null ? gitPprop : sDefault;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // use git.path property to overwrite git location
    // default value "/usr/local/bin/git"
    protected String gitPath() {
        try {
            String gitPath = this.gitProperty("path","/usr/local/bin/git"); 
            return gitPath;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
