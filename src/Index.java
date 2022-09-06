import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Index {
	HashMap<String, Blob> hashie;
	
	public Index (String fileName) throws IOException {
		FileWriter fw = new FileWriter("index");//output file
		hashie = new HashMap <String, Blob>();
		hashie.put(fileName, createBlob(fileName));
	}
	
	public Blob createBlob (String fileName) throws IOException {
		Blob newbie = new Blob (fileName);
		return newbie;
	}
}
