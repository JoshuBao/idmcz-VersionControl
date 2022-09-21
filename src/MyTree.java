import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class MyTree {
	public MyTree(String[] entries) {
		StringBuilder builder = new StringBuilder();
		for(String i : entries) {
			builder.append(i).append('\n');
		}
		String ret = builder.toString();
		String hash = Blob.encrypt(ret);
		try {
			Files.writeString(Paths.get("objects/" + hash), ret);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}