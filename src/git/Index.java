package git;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Index {
	HashMap<String, Blob> objects;
	FileWriter fw;
	
	public Index (String fileName) throws IOException {
		initialize ();
	}
	
	public void initialize () throws IOException {
		fw = new FileWriter("index");//output file
		File theDir = new File("/path/objects");
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		objects = new HashMap <String, Blob>();
	}
	
	public void addBlob (String fileName) throws IOException {
		Blob newbie = new Blob (fileName);
		objects.put(fileName, newbie);
		fw.append(fileName);
		fw.append(" : " + newbie.name());
	}
	
	public void writeIndex () throws IOException {
		for (String str : objects.keySet()) {
			fw.append(str);
			fw.append(" : " + objects.get(str).name());
		}
	}
	
	public void removeBlob (String fileName) throws IOException {
		FileWriter fw2 = new FileWriter("./objects/"+objects.get(fileName).name());
		fw2.close();
		objects.remove(fileName);
		writeIndex ();
	}
}
