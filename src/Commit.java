import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class Commit {
	public String pTree;
	public String summary;
	public String author;
	public String parent = null;
	public String date;
	public String sha;
	public String other;
	
	
	public Commit (String pStuff, String sum, String aut, String par) {
		pTree = pStuff;
		summary = sum;
		author = aut;
		parent = par;
		date = getDate ();
		other = null;
		sha = generate (pStuff,sum,aut,par);
		writer ();
	}
	
	public String generate (String p, String s, String a, String par) {
		String input = p + ", " + s + ", " + a + ", " + par; 
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
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
	}
	
	@SuppressWarnings("deprecation")
	public String getDate() {
		Date d = new Date ();
		String s = d.getMonth() + "/" + d.getDay() + "/" + d.getYear();
		return s;
	}
	
	public void writer () throws IOException {
		FileWriter fw = new FileWriter("./objects/"+ sha);//output file
		PrintWriter printW = new PrintWriter (fw);//writing stuff onto fw
		printW.write(pTree);
		printW.write(parent);
		printW.write(other);
		printW.write(author);
		printW.write(date);
		printW.write(summary);
		if(printW != null) {
		   printW.flush();
		   printW.close();
		}
	}
}
