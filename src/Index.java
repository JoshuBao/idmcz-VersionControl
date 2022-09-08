import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Index {
	HashMap<String, Blob> hashie;
	FileWriter fw;
	
	public Index (String fileName) throws IOException {
		fw = new FileWriter("index");//output file
		hashie = new HashMap <String, Blob>();
		
	}
	
	public void addBlob (String fileName) throws IOException {
		Blob newbie = new Blob (fileName);
		hashie.put(fileName, newbie);
		
		fw.append(fileName);
		fw.append(newbie.name());
	}
	
	public void removeBlob (String fileName) {
		hashie.remove(fileName);
	}
}
