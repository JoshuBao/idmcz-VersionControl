import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Git {
	
	private Index index;
	private ArrayList<Commit> commits;
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
		this.index.clearIndex();
	}
	public String commit(String summary) throws IOException
	{
		clearIndex();
		return summary;
	}
	public static void main(String[] args) throws IOException
	{
		Git git = new Git();
		git.add("BLOB1.txt");
		Commit c1 = new Commit(git.commit("first commit"), "JBao", null);
		git.add("BLOB2.txt");
		Commit c2 = new Commit(git.commit("seccond commit"), "JBao", c1);
	}
}
