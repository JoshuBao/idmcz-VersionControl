import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Index {
	HashMap<String, Blob> objects;
	FileWriter fw;
	
	public Index (String fileName) throws IOException {
		initialize ();
	}
	
	public void initialize () throws IOException {
		fw = new FileWriter("index");//output file
		File theDir = new File("objects");
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		objects = new HashMap <String, Blob>();
	}
	
	public void addBlob (String fileName) throws IOException {
		Blob newbie = new Blob (fileName);
		objects.put(fileName, newbie);
		writeIndex();
	}
	
	public void writeIndex () throws IOException {
		PrintWriter pw = new PrintWriter("index");
		for (String str : objects.keySet()) {
			pw.append(str + " : " + objects.get(str).name() + "\n");
		}
		pw.flush();
		pw.close();
	}
	
	public void removeBlob (String fileName) throws IOException {
		File fw2 = new File("objects/"+objects.get(fileName).name());
		fw2.delete();
		objects.remove(fileName);
		System.out.println(objects);
		writeIndex ();
	}
	public static void main(String[] args) throws IOException
	{
		Index indy = new Index("WeeWoo");
		indy.addBlob("BLOB1.txt");
//		indy.removeBlob("BLOB1.txt");
		indy.addBlob("BLOB2.txt");
		indy.removeBlob("BLOB2.txt");
		
		
	}
}
