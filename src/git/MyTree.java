package git;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MyTree {
	public String sha,con;
	public MyTree(ArrayList<String> entries) {
		StringBuilder builder = new StringBuilder();
		for(String i : entries) {
			builder.append(i).append('\n');
		}
		String ret = builder.toString();
		con=ret;
		String hash = Blob.encrypt(ret);
		sha = hash;
		try {
			Files.writeString(Paths.get("objects/" + hash), ret);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getName () { return sha; }
	public String contents () { return con; }
}