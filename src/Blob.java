import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Blob {
//	String filePath = "";
	String sha1 = "";
	
	public Blob (String filePath) throws IOException {
		sha1 = encrypt (filePath);
		Scanner original = new Scanner(new File(filePath));//scanning filePath contents

		String s = "";
		while(original.hasNextLine())
	     {
	        s = original.nextLine();
//	        printW.write(s);
	     }
		if(original != null) {
		       original.close();  
		   }
		sha1 = encrypt (s);
		FileWriter fw = new FileWriter("./objects/"+ sha1);//output file
		PrintWriter printW = new PrintWriter (fw);//writing stuff onto fw
		printW.write(s);
		if(printW != null) {
		   printW.flush();
		   printW.close();
		}
		//create new file w stuff in it
	}
	
	public String name () {
		return sha1;
	}
	
	public static String encrypt(String input)
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
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	
	public void createNewfile (String encrypted, String stuffing) throws FileNotFoundException {
		PrintWriter file = new PrintWriter(encrypted); 
		file.print(stuffing);
		file.close();
		
	}

	//take in object, crazy computations, big number, modify buckets etc.
	//sha1 is just hash function takes in object then does stuff and produces string
}
