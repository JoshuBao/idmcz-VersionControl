import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Commit {
	MyTree pTree;
    String pTreeFileName;
	String summary;
	String author;
	String date;
	Commit parent ;
	Commit child;
	String sha1Hash;
	String parentSha1Hash;
	String childSha1Hash;
	boolean isHead;
	public Commit(String summary,String author, Commit parent) throws FileNotFoundException
	{
		
		if (parent == null)
		{
			isHead = true;
		}
		else
			isHead = false;
		child = null;
		
		
		//Tree Time
		pTree = new MyTree(makeTreeEntries());
		this.pTreeFileName = pTree.getName();
		
		
		
		
		this.summary = summary;
		this.author = author;
		this.date = getDate();
		
		this.parent = parent;
	
	
		if (this.parent == null)
		{
			parentSha1Hash = null;
		}
		else {
	
			parentSha1Hash = "test/objects/" + parent.getCommitHash();
			setParent();
		}
	
		
		//generates the sha1 based on contents of commit
		sha1Hash = encryptThisString(getSubsetFileContents());
		
		
		writeCommitFile();
		
	}
	
	public String[] makeTreeEntries()
	{
	
		ArrayList<String> temp = new ArrayList<String>();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("index"));
			String line = reader.readLine();
			while (line != null) {
				//Put into TREE FORMAT
				
				temp.add("Blob " + line.substring(line.indexOf(":")) + " " + line.substring(0, line.indexOf(":")));
				System.out.println(line);
				// read next line
				line = reader.readLine();
				
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return  temp.toArray(new String[temp.size()]);
	}
	public String getSubsetFileContents()
	{
		return summary+ "\n" + date + "\n" + author + "\n" + parentSha1Hash;
	}
	
	public String getCommitHash()
	{
		sha1Hash = encryptThisString(getSubsetFileContents());
		return sha1Hash;
	}
	public void writeCommitFile() throws FileNotFoundException
	{
		if (child == null)
		{
			childSha1Hash = null;
		}
		else {
			childSha1Hash = "objects/" + child.getCommitHash();
			System.out.println("doasfjsOK" +  child);
		}
		PrintWriter pw = new PrintWriter("objects/" + sha1Hash);
		pw.append(pTreeFileName + "\n");
		pw.append(parentSha1Hash + "\n");
		pw.append(childSha1Hash + "\n");
		pw.append(author + "\n");
		pw.append(date + "\n");
		pw.append(summary);
		pw.close();
	}
	private void setParent () throws FileNotFoundException{
			parent.setChild(this);	
			parent.writeCommitFile();
	
	}
	
	private void setChild (Commit child) {
		this.child = child;
	//	System.out.println("i ran");
	}
	
	
	
	
	//sha 1 creator
	public static String encryptThisString(String input)
	{
		try {	         
			MessageDigest md = MessageDigest.getInstance("SHA-1");	      
			byte[] messageDigest = md.digest(input.getBytes());	     
			BigInteger no = new BigInteger(1, messageDigest);         
			String hashtext = no.toString(16); 
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}	       
			return hashtext;
		}	 
		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	//gets the date duh
	public String getDate()
	{
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(new Date());
		
	}
	public static void main(String[] args) throws FileNotFoundException {
		
		Commit c1 = new Commit ("first commit", "JBao", null);
		Commit c2 = new Commit ("WEEEEE commit", "JBao", c1);
		System.out.println("first commit child is" + c1.childSha1Hash);
		Commit c3 = new Commit ("good mesure", "JBAO", c2);
		
		
	}

}
















// big L

//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.math.BigInteger;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.Date;
//
//public class Commit {
//	public MyTree pTree;
//	public String summary;
//	public String author;
//	public String parent = null;
//	public String date;
//	public String sha;
//	public String other;
//	
//	
//	public Commit (String sum, String aut, String par) throws IOException {
//		pTree = new MyTree(makeTreeEntries());
//		summary = sum;
//		author = aut;
//		parent = par;
//		date = getDate ();
//		other = null;
//		sha = generate("treeDataAsAString",sum,aut,par);
//		writer();
//	}
//	//makes treeEntries
//	public String[] makeTreeEntries()
//	{
//	
//		ArrayList<String> temp = new ArrayList<String>();
//		BufferedReader reader;
//		try {
//			reader = new BufferedReader(new FileReader("index"));
//			String line = reader.readLine();
//			while (line != null) {
//				temp.add(line);
//				System.out.println(line);
//				// read next line
//				line = reader.readLine();
//				
//			}
//			reader.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		return  temp.toArray(new String[temp.size()]);
//	}
//	public String generate (String p, String s, String a, String par) {
//		String input = p + ", " + s + ", " + a + ", " + par; 
//		try {
//            MessageDigest md = MessageDigest.getInstance("SHA-1");
//            byte[] messageDigest = md.digest(input.getBytes());
//            BigInteger no = new BigInteger(1, messageDigest);
//            String hashtext = no.toString(16);
//            while (hashtext.length() < 32) {
//                hashtext = "0" + hashtext;
//            }
//            return hashtext;
//        }
//        catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//	}
//	
//	@SuppressWarnings("deprecation")
//	public String getDate() {
//		Date d = new Date ();
//		String s = d.getMonth() + "/" + d.getDay() + "/" + d.getYear();
//		return s;
//	}
//	
//	public void writer () throws IOException {
//		FileWriter fw = new FileWriter("./objects/"+ sha);//output file
//		PrintWriter printW = new PrintWriter (fw);//writing stuff onto fw
//		printW.write(sha);
//		printW.write(parent);
//		printW.write(other);
//		printW.write(author);
//		printW.write(date);
//		printW.write(summary);
//		if(printW != null) {
//		   printW.flush();
//		   printW.close();
//		}
//	}
//	public static void main(String[] args) throws IOException
//	{
//		Commit committee = new Commit("summary", "author", "pointer");
//	}
//}
//
