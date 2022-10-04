import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Git {
	
	private Index index;
	
	public Git() throws IOException
	{
		index = new Index("");
		index.initialize();
	}
	
	
	
	public void add(String fileName) throws IOException
	{
		index.addBlob(fileName);
	}
	public void remove(String fileName) throws IOException {
		index.removeBlob(fileName);
	}
	public void clearIndex() throws IOException
	{
		File index = new File("Index");
		index.delete();
		index.createNewFile();
	}
	public void commit(String summary,String author, Commit parent) throws IOException
	{
		Commit newCommit = new Commit(summary,author,parent);
		clearIndex();
	}
	public static void main(String[] args)
	{
		
	}
}
