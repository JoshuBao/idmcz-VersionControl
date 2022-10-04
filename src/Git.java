import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Git {
	
	private Index index;
	private String headFileName;
	
	
	public Git() throws IOException
	{
		headFileName = "";
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
	
		index.clearIndex();
	}
	public void createCommit(String summary,String author, String parent) throws IOException
	{
		if (headFileName.equals(""))
		{
			Commit c1 = new Commit(summary, author, null);
			headFileName = c1.getCommitHash();
			File storeHead = new File("HEAD");
			PrintWriter pw = new PrintWriter(storeHead);
			pw.append(headFileName);
			pw.close();
		}
		else
		{
			System.out.println("waaaah");
			File head = new File("HEAD");
			Scanner scan = new Scanner(head);
			String storeSha = scan.nextLine();
			//storeSha stores sha of the parent
			Commit c1 = new Commit(summary, author, storeSha);
			FileWriter fw = new FileWriter("HEAD");
			fw.write(c1.getCommitHash());
			fw.close();
			linkParentChild(storeSha,c1.getCommitHash());
			
			
		}
		index.clearIndex();
	
	}
	//purpose of method is to put current commit sha1 into parent commit file
	public void linkParentChild(String parentFileName, String childFileName) throws IOException
	{
		File parentCommit = new File("objects/" + parentFileName);
		Scanner scan = new Scanner(parentCommit);
		String tree = scan.nextLine();
		String parent = scan.nextLine();
		scan.nextLine();
		String author = scan.nextLine();
		String date = scan.nextLine();
		String summary = scan.nextLine();
		FileWriter fw = new FileWriter(parentCommit);
		fw.append(tree + "\n" + parent + "\n" + childFileName + "\n" + author  + "\n" + date + "\n" + summary);
		fw.close();
		
		File currentPerson = new File("objects/" + childFileName);
		Scanner scan2 = new Scanner(currentPerson);
		appendTree(tree,scan2.nextLine());
	}
	public void appendTree(String parentTree,String currentTree) throws IOException
	{
		File parento = new File("objects/" + currentTree);
		Scanner scan = new Scanner(parento);
		String tree = scan.nextLine();
		FileWriter fw = new FileWriter(parento);
	
	
		BufferedReader reader;
		String special = "Tree : " + parentTree + "\n";
		try {
			reader = new BufferedReader(new FileReader("index"));
			String line = reader.readLine();
			while (line != null) {
				//Put into TREE FORMAT
				
				special += "Blob " + line.substring(line.indexOf(":")) + " " + line.substring(0, line.indexOf(":")) + "\n";
				
				// read next line
				line = reader.readLine();
				
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fw.append(special);
		fw.flush();
		fw.close();
		
		
		
	}
	
	public static void main(String[] args) throws IOException
	{
		Git git = new Git();
		git.add("BLOB1.txt");

		git.createCommit("first commit", "JBao", null);
		//System.out.println("after first commit");
		git.add("BLOB2.txt");
	
		git.createCommit("seccond commit", "JBao", "");
		git.add("BLOB3.txt");
		git.createCommit("third commit", "JBao", "");
		git.add("BLOB4.txt");
		git.createCommit("fourth commit", "JBao", "");
		
	//	System.out.println("after second commit");

	}
}
